import os

import servertools.driver.playersave as playersave

def get_player_files(save_directory: str) -> list:
    filenames = os.listdir(save_directory)
    return [f for f in filenames if f.endswith('.json')]

def find_player_files_with_items(save_directory: str, search_item_ids: list) -> list:
    matches = []
    for filename in get_player_files(save_directory):
        player = playersave.Player(save_directory, filename)
        for item in player.get_all_items():
            if item.id in search_item_ids:
                matches.append(filename)
                break
    return matches
