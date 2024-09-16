import json
import os


class Item:
    def __init__(self, id: int, quantity: int = 1):
        if not isinstance(id, int) or not isinstance(quantity, int):
            raise TypeError('Arguments must be integers.')
        self.id = id
        self.quantity = quantity

    def __eq__(self, __value: object) -> bool:
        if not isinstance(__value, Item):
            return False
        return __value.id == self.id and __value.quantity == self.quantity

    def __repr__(self) -> str:
        return f"Item({self.id}, {self.quantity})"

    def singular(self) -> object:
        return Item(self.id)


class Player:
    container_types = ('inventory', 'bank', 'bankSecondary', 'equipment')

    def __init__(self, save_directory: str, player_file: str):
        self._save_directory = save_directory
        self._player_file = player_file
        self._player_path = os.path.join(save_directory, player_file)
        self._player_data = self._load()

    def _load(self) -> dict:
        with open(self._player_path, 'rt') as player_handle:
            return json.load(player_handle)

    def get_all_items(self) -> list:
        """fetch all items from save file"""
        all_items = []
        for container in self.container_types:
            all_items.extend(self._get_items(container))
        return all_items

    def purge_items(self, *purge_items) -> list:
        purged = []
        for container in self.container_types:
            item_dicts = self._player_data.get('core_data', {}).get(container, {})
            to_remove = []
            for item_dict in item_dicts:
                item_id = int(item_dict['id'])
                for purge_item in purge_items:
                    if purge_item.id == item_id:
                        purged.append(Item(item_id, int(item_dict['amount'])))
                        to_remove.append(item_dict)
            for item in to_remove:
                item_dicts.remove(item)
        return purged

    def _get_items(self, container: str) -> list:
        items = []
        item_dicts = self._player_data.get('core_data', {}).get(container, {})
        if not item_dicts:
            return []
        for item_dict in item_dicts:
            item = Item(int(item_dict['id']), int(item_dict['amount']))
            items.append(item)
        return items
