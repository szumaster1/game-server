import json
import os
import sys

import mariadb

CRED_FILE = "credentials.json"
_database = []


def _get_connection_params() -> dict:
    if not os.path.exists(CRED_FILE):
        with open(CRED_FILE, "wt", encoding="utf8") as cred_file:
            cred_file.write('''{
    "user": "",
    "password": "",
    "host": "",
    "database": "",
}''')
        print("First run detected, credentials template created.")
        sys.exit(0)
    with open(CRED_FILE, "rt", encoding="utf8") as cred_file:
        credentials =  json.load(cred_file)
    assert "user" in credentials
    assert "password" in credentials
    assert "host" in credentials
    assert "database" in credentials
    return credentials


class _Database:
    def __init__(self, connection_params: dict):
        self._connection_params = connection_params
        self._connection = None
        self._cursors = []

    def connect(self):
        """Connect to database."""
        if self._connection is not None:
            raise RuntimeError("Already in the middle of a connection.")
        self._connection = mariadb.connect(**self._connection_params)

    def disconnect(self):
        """Close cursors and disconnect from database."""
        for cursor in self._cursors:
            cursor.close()
        if self._connection is None:
            raise RuntimeError("Connection not open.")
        self._connection.close()
        self._connection = None

    def _get_cursor(self):
        if self._connection is None:
            raise RuntimeError("Connection not open.")
        cursor = self._connection.cursor(buffered=True)
        self._cursors.append(cursor)
        return cursor

    def _close_cursor(self, cursor):
        self._cursors.remove(cursor)
        cursor.close()

    def execute(self, query: str, parameters: tuple = None) -> int:
        """Returns number of rows affected."""
        cursor = self._get_cursor()
        if parameters is not None:
            cursor.execute(query, parameters)
        else:
            cursor.execute(query)
        n_rows = cursor.rowcount
        self._close_cursor(cursor)
        return n_rows

    def read_single(self, query: str, parameters: tuple):
        """Executes a query and returns a single row."""
        cursor = self._get_cursor()
        cursor.execute(query, parameters)
        row = cursor.fetchone()
        self._close_cursor(cursor)
        return row


def get_database() -> _Database:
    """Database factory."""
    if not _database:
        _database.append(_Database(_get_connection_params()))
    return _database[0]
