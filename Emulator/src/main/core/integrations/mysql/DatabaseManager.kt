package core.integrations.mysql

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.sqlite.SQLiteDataSource
import java.io.File
import java.sql.Connection
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

/**
 * Database manager
 *
 * @param path The path to the database file
 * @param expectedTables A map of expected tables and their creation SQL statements
 * @constructor Creates a DatabaseManager instance
 */
class DatabaseManager(private val path: String, private val expectedTables: HashMap<String, String>? = null) {
    private var connection: Connection? = null // Holds the database connection
    private var connectionRefs = 0 // Tracks the number of active connections
    private var obtainConnectionLock = ReentrantLock() // Lock for obtaining a connection
    private var dbRunLock = ReentrantLock() // Lock for running database operations
    private val coroutineScope = CoroutineScope(Dispatchers.IO) // Coroutine scope for asynchronous operations
    private lateinit var sqlitePath: String // Path for the SQLite database

    /**
     * Represents the expected tables in the database
     *
     * @param tableCreatedCallback A callback invoked when a table is created
     */
    fun initTables(tableCreatedCallback: ((String) -> Unit)? = null) {
        val pathTokens = File(path).absolutePath.split(File.separator) // Splits the path into tokens
        val file = pathTokens.last() // Gets the last token as the file name
        val parentDir = pathTokens.dropLast(1).joinToString(File.separator) // Gets the parent directory

        if (!File(parentDir).exists()) File(parentDir).mkdirs() // Creates the parent directory if it doesn't exist
        sqlitePath = parentDir + File.separator + file // Constructs the SQLite path
        if (expectedTables?.isEmpty() != false) return // Returns if there are no expected tables
        run { // Executes the database operation
            with(it.prepareStatement(TABLE_CHECK)) { // Prepares a statement to check for table existence
                for ((table, create) in expectedTables) { // Iterates over expected tables
                    setString(1, table) // Sets the table name in the prepared statement
                    val res = executeQuery() // Executes the query to check if the table exists
                    if (!res.next()) with(it.createStatement()) { // If the table does not exist
                        execute(create); // Creates the table
                        tableCreatedCallback?.invoke(table) // Invokes the callback with the table name
                    }
                }
            }
        }
    }

    /**
     * Prunes old data from the database
     *
     * @param daysToKeep The number of days to keep data
     * @param timestampFieldName The name of the timestamp field
     */
    fun pruneOldData(daysToKeep: Int, timestampFieldName: String = "ts") {
        if (expectedTables?.isEmpty() != false) return // Returns if there are no expected tables
        val timeDiff = daysToKeep * 24 * 60 * 60 // Calculates the time difference in seconds
        val nowTime = System.currentTimeMillis() / 1000 // Gets the current time in seconds
        run { // Executes the database operation
            with(it.createStatement()) { // Creates a statement for executing SQL commands
                for ((table, _) in expectedTables) { // Iterates over expected tables
                    execute("DELETE FROM $table WHERE $timestampFieldName <= ${nowTime - timeDiff};") // Deletes old data
                }
            }
        }
    }

    /**
     * Runs a closure asynchronously
     *
     * @param closure The operation to perform with the database connection
     * @receiver The database connection
     * @return A Job representing the coroutine
     */
    fun runAsync(closure: (conn: Connection) -> Unit): Job {
        return coroutineScope.launch { run(closure) } // Launches a coroutine to run the closure
    }

    /**
     * Runs a closure with a database connection
     *
     * @param closure The operation to perform with the database connection
     * @receiver The database connection
     */
    fun run(closure: (conn: Connection) -> Unit) {
        dbRunLock.tryLock(10000L, TimeUnit.MILLISECONDS) // Attempts to acquire the database run lock

        connectionRefs++ // Increments the connection reference count
        val con = connect() // Obtains a database connection
        closure.invoke(con) // Invokes the closure with the connection
        connectionRefs-- // Decrements the connection reference count

        if (connectionRefs == 0) { // If there are no more references
            con.close() // Closes the connection
        }

        dbRunLock.unlock() // Releases the database run lock
    }

    private fun connect(): Connection {
        obtainConnectionLock.tryLock(10000L, TimeUnit.MILLISECONDS) // Attempts to acquire the connection lock

        if (connection == null || connection!!.isClosed) { // Checks if the connection is null or closed
            val ds = SQLiteDataSource() // Creates a new SQLite data source
            ds.url = "jdbc:sqlite:$sqlitePath" // Sets the database URL
            connection = ds.connection // Obtains a new connection
        }

        obtainConnectionLock.unlock() // Releases the connection lock
        return connection!! // Returns the database connection
    }

    companion object {
        const val TABLE_CHECK = "SELECT name FROM sqlite_master WHERE type='table' AND name=?;" // SQL query to check for table existence
    }
}