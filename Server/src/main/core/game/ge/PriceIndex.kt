package core.game.ge

import core.api.itemDefinition
import java.sql.ResultSet

/**
 * Price index.
 */
object PriceIndex {

    fun canTrade(id: Int): Boolean {
        var canTrade = false

        GEDB.run { conn ->
            val stmt = conn.prepareStatement(EXISTS_QUERY)
            stmt.setInt(1, id)
            val res = stmt.executeQuery()
            if (res.next()) {
                canTrade = res.getInt(1) == 1
            }
        }

        return canTrade
    }

    fun banItem(id: Int) {
        GEDB.run { conn ->
            val stmt = conn.prepareStatement(REMOVE_QUERY)
            stmt.setInt(1, id)
            stmt.execute()
        }
    }

    fun allowItem(id: Int) {
        if(canTrade(id)) return

        GEDB.run { conn ->
            val stmt = conn.prepareStatement(INSERT_QUERY)
            stmt.setInt(1, id)
            stmt.setInt(2, itemDefinition(id).getAlchemyValue(true))
            stmt.setInt(3, itemDefinition(id).getAlchemyValue(true))
            stmt.setInt(4, 0)
            stmt.setInt(5, 0)
            stmt.execute()
        }
    }

    fun getValue(id: Int): Int {
        var value = itemDefinition(id).getAlchemyValue(true)
        GEDB.run { conn ->
            val stmt = conn.prepareStatement(GET_VALUE_QUERY)
            stmt.setInt(1, id)
            val res = stmt.executeQuery()
            if (res.next()) {
                value = res.getInt(1)
            }
        }
        return value
    }

    fun addTrade(id: Int, amount: Int, pricePerUnit: Int) {
        val oldInfo = getInfo(id) ?: return
        val newInfo = oldInfo.copy()

        val volumeResetThreshold =
            if (pricePerUnit >= 1000000) 500
            else if (pricePerUnit >= 100000) 1000
            else if (pricePerUnit >= 25000) 2500
            else 10000

        newInfo.totalValue += (amount * pricePerUnit)
        newInfo.uniqueTrades += amount
        newInfo.lastUpdate = System.currentTimeMillis()
        newInfo.currentValue = (newInfo.totalValue / newInfo.uniqueTrades).toInt()

        if (newInfo.uniqueTrades > volumeResetThreshold) {
            val newAmt = (0.1 * volumeResetThreshold).toInt()
            newInfo.uniqueTrades = newAmt
            newInfo.totalValue = newAmt.toLong() * newInfo.currentValue
        }

        updateInfo(newInfo)
    }

    private fun updateInfo(newInfo: PriceInfo) {
        GEDB.run { conn ->
            val stmt = conn.prepareStatement(UPDATE_QUERY)
            stmt.setInt(1, newInfo.currentValue)
            stmt.setLong(2, newInfo.totalValue)
            stmt.setInt(3, newInfo.uniqueTrades)
            stmt.setLong(4, newInfo.lastUpdate)
            stmt.setInt(5, newInfo.itemId)
            stmt.execute()
        }
    }

    private fun getInfo(id: Int): PriceInfo? {
        var priceInfo: PriceInfo? = null

        GEDB.run { conn ->
            val stmt = conn.prepareStatement(SELECT_QUERY)
            stmt.setInt(1, id)
            val res = stmt.executeQuery()
            if (res.next()) {
                priceInfo = PriceInfo.fromQuery(res)
            }
        }

        return priceInfo
    }

    const val SELECT_QUERY = "SELECT * FROM price_index WHERE item_id = ?;"
    const val UPDATE_QUERY = "UPDATE price_index SET value = ?, total_value = ?, unique_trades = ?, last_update = ? WHERE item_id = ?;"
    const val EXISTS_QUERY = "SELECT EXISTS(SELECT 1 FROM price_index WHERE item_id = ?);"
    const val REMOVE_QUERY = "DELETE FROM price_index WHERE item_id = ?;"
    const val INSERT_QUERY = "INSERT INTO price_index (item_id, value, total_value, unique_trades, last_update) VALUES (?,?,?,?,?);"
    const val GET_VALUE_QUERY = "SELECT value FROM price_index WHERE item_id = ?;"
}
/**
 * Price info
 *
 * @param itemId Unique identifier for the item
 * @param currentValue Current market value of the item
 * @param totalValue Total value of all trades for the item
 * @param uniqueTrades Number of unique trades for the item
 * @param lastUpdate Timestamp of the last update for the item's price
 * @constructor Price info
 */
data class PriceInfo(
    var itemId: Int, // Unique identifier for the item
    var currentValue: Int, // Current market value of the item
    var totalValue: Long, // Total value of all trades for the item
    var uniqueTrades: Int, // Number of unique trades for the item
    var lastUpdate: Long // Timestamp of the last update for the item's price
) {
    /**
     * Copy
     *
     * @return A new instance of PriceInfo with the same values
     */
    fun copy() : PriceInfo {
        return PriceInfo(itemId, currentValue, totalValue, uniqueTrades, lastUpdate) // Returns a new PriceInfo object with the same properties
    }
    companion object {
        /**
         * Creates a PriceInfo instance from a ResultSet
         *
         * @param result ResultSet containing the data
         * @return A new PriceInfo object populated with data from the ResultSet
         */
        fun fromQuery(result: ResultSet) : PriceInfo {
            return PriceInfo(
                result.getInt(1), // Retrieves the itemId from the ResultSet
                result.getInt(2), // Retrieves the currentValue from the ResultSet
                result.getLong(3), // Retrieves the totalValue from the ResultSet
                result.getInt(4), // Retrieves the uniqueTrades from the ResultSet
                result.getLong(5) // Retrieves the lastUpdate from the ResultSet
            )
        }
    }
}