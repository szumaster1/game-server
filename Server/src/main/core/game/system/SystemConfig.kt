package core.game.system

import core.game.node.entity.player.Player
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Holds the system configurations from the database.
 * @author Vexia
 */
class SystemConfig {
    /**
     * The system-object configurations.
     */
    private val configs: MutableMap<String, Any?> = HashMap()
    val betaUsers: List<String> = ArrayList(20)

    /**
     * Parses system configurations from the SQL database.
     */
    fun parse() {}

    /**
     * Parses a config using the SQL info given.
     *
     * @param key      The key identity.
     * @param value    The value.
     * @param dataType The data type to parse.
     */
    private fun parseConfig(key: String, value: String?, dataType: String?) {
        if (dataType == null) {
            configs[key] = value
            return
        }
        if (value == null || value === "") {
            return
        }
        when (dataType) {
            "int" -> configs[key] = Integer.valueOf(value)
            "double" -> {}
            "boolean" -> {}
            "date" -> {
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                var parsed: Date? = null
                try {
                    parsed = format.parse(value)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                configs[key] = parsed
            }

            else -> configs[key] = value
        }
    }

    /**
     * Checks if the player passes the system configuration validation.
     *
     * @param player The player.
     * @return `True` if successfull.
     */
    fun validLogin(player: Player?): Boolean {
        return true
    }

    val isDoubleExp: Boolean
        /**
         * Checks if double experience is active.
         *
         * @return `True` if active.
         */
        get() {
            val date = getConfig<Date?>("dxp", null) ?: return false
            return date.after(Date())
        }

    /**
     * Splits the data into an array list using a regex.
     *
     * @param data  the data.
     * @param regex the regex to split.
     * @return the list of data.
     */
    fun split(data: String, regex: String): List<String> {
        if (!data.contains(regex)) {
            val split: MutableList<String> = ArrayList(20)
            split.add(data)
            return split
        }
        val split: MutableList<String> = ArrayList(20)
        val tokens = data.trim { it <= ' ' }.split(regex.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (s in tokens) {
            split.add(s)
        }
        return split
    }

    /**
     * Checks if a username is a beta user.
     *
     * @param name The name.
     * @return `True` if so.
     */
    fun isBetaUser(name: String): Boolean {
        return betaUsers.contains(name)
    }

    /**
     * Gets an attribute.
     *
     * @param <T> the type parameter
     * @param key The attribute name.
     * @return The attribute value.
    </T> */
    fun <T> getConfig(key: String): T? {
        return if (!configs.containsKey(key)) {
            null
        } else configs[key] as T?
    }

    /**
     * Gets an attribute.
     *
     * @param <T>    the type parameter
     * @param string The attribute name.
     * @param fail   The value to return if the attribute is null.
     * @return The attribute value, or the fail argument when null.
    </T> */
    fun <T> getConfig(string: String, fail: T): T {
        val `object` = configs[string]
        return if (`object` != null) {
            `object` as T
        } else fail
    }

    /**
     * Gets the configs.
     *
     * @return the configs.
     */
    fun getConfigs(): Map<String, Any?> {
        return configs
    }
}