"""Unit tests for playersave driver."""
import unittest

import servertools.driver.playersave as playersave

class PlayerTest(unittest.TestCase):
    def test_player_init(self):
        player = playersave.Player('tests/testdata', 'testplayerinit.json')
        self.assertIsNotNone(player)

    def test_player_get_all_items(self):
        player = playersave.Player('tests/testdata', 'testplayeritems.json')

        items = player.get_all_items()

        self.assertIsInstance(items, list)
        self.assertIn(playersave.Item(69), items)   # inventory
        self.assertIn(playersave.Item(420), items)  # bank
        self.assertIn(playersave.Item(42), items)   # bank 2
        self.assertIn(playersave.Item(1333), items) # equipment

    def test_player_purge_items_single(self):
        player = playersave.Player('tests/testdata', 'testplayeritems.json')
        start_items = player.get_all_items()
        item_to_remove = playersave.Item(69)
        self.assertIn(item_to_remove, start_items)

        removed = player.purge_items(item_to_remove)

        end_items = player.get_all_items()
        self.assertNotIn(item_to_remove, end_items)
        self.assertEqual([item_to_remove], removed)

    def test_player_purge_items_many(self):
        player = playersave.Player('tests/testdata', 'testplayeritems.json')
        start_items = player.get_all_items()
        items_to_remove = [playersave.Item(69), playersave.Item(42)]
        for item in items_to_remove:
            self.assertIn(item, start_items)

        removed = player.purge_items(*items_to_remove)

        end_items = player.get_all_items()
        for item in items_to_remove:
            self.assertNotIn(item, end_items)
            self.assertIn(item, removed)
        self.assertEqual(len(removed), 2)

    def test_player_purge_items_mutates(self):
        player = playersave.Player('tests/testdata', 'testplayeritems.json')
        item_to_remove = playersave.Item(69)

        removed1 = player.purge_items(item_to_remove)
        removed2 = player.purge_items(item_to_remove)

        self.assertEqual(removed1, [item_to_remove])
        self.assertEqual(removed2, [])


class ItemTest(unittest.TestCase):
    def test_item_init_id(self):
        item = playersave.Item(69)
        self.assertEqual(item.id, 69)

    def test_item_init_id_quantity(self):
        item = playersave.Item(69, 420)
        self.assertEqual(item.id, 69)
        self.assertEqual(item.quantity, 420)

    def test_item_equals(self):
        item1 = playersave.Item(69)
        item2 = playersave.Item(69)
        self.assertEqual(item1, item2)
        self.assertIsNot(item1, item2)

    def test_item_id_type(self):
        self.assertRaises(TypeError, playersave.Item, ["1"])

    def test_item_quantity_type(self):
        self.assertRaises(TypeError, playersave.Item, [1, "1"])

    def test_item_singular(self):
        item = playersave.Item(69, 420)

        singular = item.singular()

        self.assertEqual(singular.quantity, 1)
        self.assertEqual(singular.id, item.id)
