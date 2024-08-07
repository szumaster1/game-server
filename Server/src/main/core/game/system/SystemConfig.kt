package core.game.system

import core.game.node.entity.player.Player
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * System config
 *
 * @constructor System config
 */
class SystemConfig {
    /*
     * The system-object configurations.
     */
    private val configs: MutableMap<String, Any?> = HashMap()
    val betaUsers: List<String> = ArrayList(20)

    /**
     * Parse
     *
     */
    /*
     * Parses system configurations from the SQL database.
     */
    fun parse() {}

    /*
     * Parses a config using the SQL info given.
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
     * Valid login
     *
     * @param player
     * @return
     *//*
     * Checks if the player passes the system configuration validation.
     */
    fun validLogin(player: Player?): Boolean {
        return true
    }

    val isDoubleExp: Boolean
        /*
         * Checks if double experience is active.
         */
        get() {
            val date = getConfig<Date?>("dxp", null) ?: return false
            return date.after(Date())
        }

    /**
     * Split
     *
     * @param data
     * @param regex
     * @return
     *//*
     * Splits the data into an array list using a regex.
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
     * Is beta user
     *
     * @param name
     * @return
     *//*
     * Checks if a username is a beta user.
     */
    fun isBetaUser(name: String): Boolean {
        return betaUsers.contains(name)
    }

    /**
     * Get config
     *
     * @param T
     * @param key
     * @return
     *//*
     * Gets an attribute.
     */
    fun <T> getConfig(key: String): T? {
        return if (!configs.containsKey(key)) {
            null
        } else configs[key] as T?
    }

    /**
     * Get config
     *
     * @param T
     * @param string
     * @param fail
     * @return
     *//*
     * Gets an attribute.
     */
    fun <T> getConfig(string: String, fail: T): T {
        val `object` = configs[string]
        return if (`object` != null) {
            `object` as T
        } else fail
    }

    /**
     * Get configs
     *
     * @return
     *//*
     * Gets the configs.
     */
    fun getConfigs(): Map<String, Any?> {
        return configs
    }
}