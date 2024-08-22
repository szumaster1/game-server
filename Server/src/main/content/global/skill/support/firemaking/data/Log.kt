package content.global.skill.support.firemaking.data

import cfg.consts.Items
import cfg.consts.Scenery

/**
 * Enum class representing different types of logs with their associated properties.
 *
 * @param logId Unique identifier for the log type.
 * @param defaultLevel The default level required to use this log.
 * @param barbarianLevel The level required for a barbarian to use this log.
 * @param life The life points associated with this log.
 * @param fireId Identifier for the fire type associated with this log.
 * @param xp Experience points gained from using this log.
 */
enum class Log(
    val logId: Int,          // Unique identifier for the log type.
    val defaultLevel: Int,   // Default level required to use this log.
    val barbarianLevel: Int, // Level required for a barbarian to use this log.
    val life: Int,           // Life points associated with this log.
    val fireId: Int,         // Identifier for the fire type associated with this log.
    val xp: Double           // Experience points gained from using this log.
) {/**
     * Normal.
     */
    NORMAL(
        logId = Items.LOGS_1511,
        defaultLevel = 1,
        barbarianLevel = 21,
        life = 180,
        fireId = Scenery.FIRE_2732,
        xp = 40.0
    ),

    /**
     * Purple.
     */
    PURPLE(
        logId = Items.PURPLE_LOGS_10329,
        defaultLevel = 1,
        barbarianLevel = 21,
        life = 200,
        fireId = Scenery.FIRE_20001,
        xp = 50.0
    ),

    /**
     * White.
     */
    WHITE(
        logId = Items.WHITE_LOGS_10328,
        defaultLevel = 1,
        barbarianLevel = 21,
        life = 200,
        fireId = Scenery.FIRE_20000,
        xp = 50.0
    ),

    /**
     * Blue.
     */
    BLUE(
        logId = Items.BLUE_LOGS_7406,
        defaultLevel = 1,
        barbarianLevel = 21,
        life = 200,
        fireId = Scenery.FIRE_11406,
        xp = 50.0
    ),

    /**
     * Green.
     */
    GREEN(
        logId = Items.GREEN_LOGS_7405,
        defaultLevel = 1,
        barbarianLevel = 21,
        life = 200,
        fireId = Scenery.FIRE_11405,
        xp = 50.0
    ),

    /**
     * Red.
     */
    RED(
        logId = Items.RED_LOGS_7404,
        defaultLevel = 1,
        barbarianLevel = 21,
        life = 200,
        fireId = Scenery.FIRE_11404,
        xp = 50.0
    ),

    /**
     * Jogre.
     */
    JOGRE(
        logId = Items.JOGRE_BONES_3125,
        defaultLevel = 1,
        barbarianLevel = 21,
        life = 200,
        fireId = Scenery.BURNING_BONES_3862,
        xp = 50.0
    ),

    /**
     * Achey.
     */
    ACHEY(
        logId = Items.ACHEY_TREE_LOGS_2862,
        defaultLevel = 1,
        barbarianLevel = 21,
        life = 180,
        fireId = Scenery.FIRE_2732,
        xp = 40.0
    ),

    /**
     * Oak.
     */
    OAK(
        logId = Items.OAK_LOGS_1521,
        defaultLevel = 15,
        barbarianLevel = 35,
        life = 200,
        fireId = Scenery.FIRE_2732,
        xp = 60.0
    ),

    /**
     * Willow.
     */
    WILLOW(
        logId = Items.WILLOW_LOGS_1519,
        defaultLevel = 30,
        barbarianLevel = 50,
        life = 250,
        fireId = Scenery.FIRE_2732,
        xp = 90.0
    ),

    /**
     * Teak.
     */
    TEAK(
        logId = Items.TEAK_LOGS_6333,
        defaultLevel = 35,
        barbarianLevel = 55,
        life = 300,
        fireId = Scenery.FIRE_2732,
        xp = 105.0
    ),

    /**
     * Arctic Pine.
     */
    ARCTIC_PINE(
        logId = Items.ARCTIC_PINE_LOGS_10810,
        defaultLevel = 42,
        barbarianLevel = 62,
        life = 500,
        fireId = Scenery.FIRE_2732,
        xp = 125.0
    ),

    /**
     * Maple.
     */
    MAPLE(
        logId = Items.MAPLE_LOGS_1517,
        defaultLevel = 45,
        barbarianLevel = 65,
        life = 300,
        fireId = Scenery.FIRE_2732,
        xp = 135.0
    ),

    /**
     * Mahogany.
     */
    MAHOGANY(
        logId = Items.MAHOGANY_LOGS_6332,
        defaultLevel = 50,
        barbarianLevel = 70,
        life = 300,
        fireId = Scenery.FIRE_2732,
        xp = 157.5
    ),

    /**
     * Eucalyptus.
     */
    EUCALYPTUS(
        logId = Items.EUCALYPTUS_LOGS_12581,
        defaultLevel = 58,
        barbarianLevel = 68,
        life = 300,
        fireId = Scenery.FIRE_2732,
        xp = 193.5
    ),

    /**
     * Yew.
     */
    YEW(
        logId = Items.YEW_LOGS_1515,
        defaultLevel = 60,
        barbarianLevel = 80,
        life = 400,
        fireId = Scenery.FIRE_2732,
        xp = 202.5
    ),

    /**
     * Magic.
     */
    MAGIC(
        logId = Items.MAGIC_LOGS_1513,
        defaultLevel = 75,
        barbarianLevel = 95,
        life = 450,
        fireId = Scenery.FIRE_2732,
        xp = 303.8
    ),

    /**
     * Cursed Magic.
     */
    CURSED_MAGIC(
        logId = Items.CURSED_MAGIC_LOGS_13567,
        defaultLevel = 82,
        barbarianLevel = 97,
        life = 650,
        fireId = Scenery.FIRE_2732,
        xp = 303.8
    );

    companion object {
        var logMap: HashMap<Int, Log> = HashMap() // Map to store log IDs and their corresponding Log objects

        init {
            val logArray = values() // Retrieve all enum values
            val logLength = logArray.size // Get the number of log types
            for (log in logArray) {
                logMap.putIfAbsent(log.logId, log) // Populate the logMap with log IDs and Log objects
            }
        }

        @JvmStatic
        fun forId(id: Int): Log? {
            return logMap[id] // Retrieve the Log object associated with the given ID
        }
    }
}