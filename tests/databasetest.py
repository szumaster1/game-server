"""Unit tests for database driver."""
import unittest

import servertools.driver.database as dbdriver


class DatabaseTest(unittest.TestCase):
    def test_execute(self):
        """Should be able to connect, read a row, disconnect without errors. (Yes this is an integration test)"""
        database = dbdriver.get_database()
        database.connect()
        rows_read = database.execute("SELECT 1")
        self.assertEqual(1, rows_read, "Should have read 1 row.")
        database.disconnect()
