package core.integration.mysql

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
 * @param [path] The path to the database file
 * @param [expectedTables] A map of expected tables and their creation SQL statements
 * @constructor Creates a [DatabaseManager] instance
 */
class DatabaseManager(private val path: String, private val expectedTables: HashMap<String, String>? = null) {
    private var connection: Connection? = null
    private var connectionRefs = 0
    private var obtainConnectionLock = ReentrantLock()
    private var dbRunLock = ReentrantLock()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private lateinit var sqlitePath: String

    /**
     * Represents the expected tables in the database
     *
     * @param [tableCreatedCallback] A callback invoked when a table is created
     */
    fun initTables(tableCreatedCallback: ((String) -> Unit)? = null) {
        val pathTokens = File(path).absolutePath.split(File.separator)
        val file = pathTokens.last()
        val parentDir = pathTokens.dropLast(1).joinToString(File.separator)

        if (!File(parentDir).exists()) File(parentDir).mkdirs()
        sqlitePath = parentDir + File.separator + file
        if (expectedTables?.isEmpty() != false) return
        run {
            with(it.prepareStatement(TABLE_CHECK)) {
                for ((table, create) in expectedTables) {
                    setString(1, table)
                    val res = executeQuery()
                    if (!res.next()) with(it.createStatement()) {
                        execute(create)
                        tableCreatedCallback?.invoke(table)
                    }
                }
            }
        }
    }

    /**
     * Prunes old data from the database
     *
     * @param [daysToKeep] The number of days to keep data
     * @param [timestampFieldName] The name of the timestamp field
     */
    fun pruneOldData(daysToKeep: Int, timestampFieldName: String = "ts") {
        if (expectedTables?.isEmpty() != false) return
        val timeDiff = daysToKeep * 24 * 60 * 60
        val nowTime = System.currentTimeMillis() / 1000
        run {
            with(it.createStatement()) {
                for ((table, _) in expectedTables) {
                    execute("DELETE FROM $table WHERE $timestampFieldName <= ${nowTime - timeDiff};") // Deletes old data
                }
            }
        }
    }

    /**
     * Runs a closure asynchronously
     *
     * @param [closure] The operation to perform with the database connection
     * @receiver The database connection
     * @return A Job representing the coroutine
     */
    fun runAsync(closure: (conn: Connection) -> Unit): Job {
        return coroutineScope.launch { run(closure) }
    }

    /**
     * Runs a closure with a database connection
     *
     * @param [closure] The operation to perform with the database connection
     * @receiver The database connection
     */
    fun run(closure: (conn: Connection) -> Unit) {
        dbRunLock.tryLock(10000L, TimeUnit.MILLISECONDS)

        connectionRefs++
        val con = connect()
        closure.invoke(con)
        connectionRefs--

        if (connectionRefs == 0) {
            con.close()
        }

        dbRunLock.unlock()
    }

    private fun connect(): Connection {
        obtainConnectionLock.tryLock(10000L, TimeUnit.MILLISECONDS)

        if (connection == null || connection!!.isClosed) {
            val ds = SQLiteDataSource()
            ds.url = "jdbc:sqlite:$sqlitePath"
            connection = ds.connection
        }

        obtainConnectionLock.unlock()
        return connection!!
    }

    companion object {
        const val TABLE_CHECK = "SELECT name FROM sqlite_master WHERE type='table' AND name=?;" // SQL query to check for table existence
    }
}