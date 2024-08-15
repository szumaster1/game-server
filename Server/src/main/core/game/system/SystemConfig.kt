package core.game.system

import core.game.node.entity.player.Player
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * System config.
 */
class SystemConfig {

    /**
     * The system-object configurations.
     */
    private val configs: MutableMap<String, Any?> = HashMap()
    val betaUsers: List<String> = ArrayList(20)

    /**
     * Parses system configurations from the SQL database.
     * This function is responsible for initiating the parsing process
     * of configurations stored in the database.
     */
    fun parse() {}

    /**
     * Parses a config using the SQL info given.
     *
     * @param key The identifier for the configuration setting.
     * @param value The value associated with the configuration setting, can be null.
     * @param dataType The type of data for the configuration setting, can be null.
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
     * @param player The player object containing login information.
     * @return Boolean value indicating if the login is valid.
     */
    fun validLogin(player: Player?): Boolean {
        return true
    }

    val isDoubleExp: Boolean
        /**
         * Checks if double experience is active.
         */
        get() {
            val date = getConfig<Date?>("dxp", null) ?: return false
            return date.after(Date())
        }

    /**
     * Splits the data into an array list using a regex.
     *
     * @param data the string to be split.
     * @param regex the regular expression to split the string.
     * @return a list of strings after splitting.
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
     * @param name the name to check.
     * @return Boolean indicating if the name is in the beta users list.
     */
    fun isBetaUser(name: String): Boolean {
        return betaUsers.contains(name)
    }

    /**
     * Gets an attribute.
     *
     * @param T The type of the attribute to retrieve.
     * @param key The key for the attribute.
     * @return The value associated with the key, or null if not found.
     */
    fun <T> getConfig(key: String): T? {
        return if (!configs.containsKey(key)) {
            null
        } else configs[key] as T?
    }

    /**
     * Gets an attribute.
     *
     * @param T The type of the attribute to retrieve.
     * @param string The key for the attribute.
     * @param fail The default value to return if the key is not found.
     * @return The value associated with the key, or the default value if not found.
     */
    fun <T> getConfig(string: String, fail: T): T {
        val `object` = configs[string]
        return if (`object` != null) {
            `object` as T
        } else fail
    }

    /**
     * Gets the configs.
     *
     * @return A map of all configurations.
     */
    fun getConfigs(): Map<String, Any?> {
        return configs
    }
}