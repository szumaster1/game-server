package content.global.skill.support.firemaking.data

import core.api.consts.Items
import core.api.consts.Scenery

/**
 * Log
 *
 * @param logId Unique identifier for the log type
 * @param defaultLevel Required level to use the log
 * @param barbarianLevel Required level for barbarian use
 * @param life Durability of the log
 * @param fireId Identifier for the fire associated with the log
 * @param xp Experience points gained from using the log
 * @constructor Log
 */
enum class Log(val logId: Int, val defaultLevel: Int, val barbarianLevel: Int, val life: Int, val fireId: Int, val xp: Double) {
    /**
     * Normal.
     */
    NORMAL(
        logId = Items.LOGS_1511, // Unique ID for Normal log
        defaultLevel = 1, // Level required to use Normal log
        barbarianLevel = 21, // Level required for barbarian use of Normal log
        life = 180, // Durability of Normal log
        fireId = Scenery.FIRE_2732, // Fire ID associated with Normal log
        xp = 40.0 // Experience points gained from Normal log
    ),

    /**
     * Purple.
     */
    PURPLE(
        logId = Items.PURPLE_LOGS_10329, // Unique ID for Purple log
        defaultLevel = 1, // Level required to use Purple log
        barbarianLevel = 21, // Level required for barbarian use of Purple log
        life = 200, // Durability of Purple log
        fireId = Scenery.FIRE_20001, // Fire ID associated with Purple log
        xp = 50.0 // Experience points gained from Purple log
    ),

    /**
     * White.
     */
    WHITE(
        logId = Items.WHITE_LOGS_10328, // Unique ID for White log
        defaultLevel = 1, // Level required to use White log
        barbarianLevel = 21, // Level required for barbarian use of White log
        life = 200, // Durability of White log
        fireId = Scenery.FIRE_20000, // Fire ID associated with White log
        xp = 50.0 // Experience points gained from White log
    ),

    /**
     * Blue.
     */
    BLUE(
        logId = Items.BLUE_LOGS_7406, // Unique ID for Blue log
        defaultLevel = 1, // Level required to use Blue log
        barbarianLevel = 21, // Level required for barbarian use of Blue log
        life = 200, // Durability of Blue log
        fireId = Scenery.FIRE_11406, // Fire ID associated with Blue log
        xp = 50.0 // Experience points gained from Blue log
    ),

    /**
     * Green.
     */
    GREEN(
        logId = Items.GREEN_LOGS_7405, // Unique ID for Green log
        defaultLevel = 1, // Level required to use Green log
        barbarianLevel = 21, // Level required for barbarian use of Green log
        life = 200, // Durability of Green log
        fireId = Scenery.FIRE_11405, // Fire ID associated with Green log
        xp = 50.0 // Experience points gained from Green log
    ),

    /**
     * Red.
     */
    RED(
        logId = Items.RED_LOGS_7404, // Unique ID for Red log
        defaultLevel = 1, // Level required to use Red log
        barbarianLevel = 21, // Level required for barbarian use of Red log
        life = 200, // Durability of Red log
        fireId = Scenery.FIRE_11404, // Fire ID associated with Red log
        xp = 50.0 // Experience points gained from Red log
    ),

    /**
     * Jogre.
     */
    JOGRE(
        logId = Items.JOGRE_BONES_3125, // Unique ID for Jogre log
        defaultLevel = 1, // Level required to use Jogre log
        barbarianLevel = 21, // Level required for barbarian use of Jogre log
        life = 200, // Durability of Jogre log
        fireId = Scenery.BURNING_BONES_3862, // Fire ID associated with Jogre log
        xp = 50.0 // Experience points gained from Jogre log
    ),

    /**
     * Achey.
     */
    ACHEY(
        logId = Items.ACHEY_TREE_LOGS_2862, // Unique ID for Achey log
        defaultLevel = 1, // Level required to use Achey log
        barbarianLevel = 21, // Level required for barbarian use of Achey log
        life = 180, // Durability of Achey log
        fireId = Scenery.FIRE_2732, // Fire ID associated with Achey log
        xp = 40.0 // Experience points gained from Achey log
    ),

    /**
     * Oak.
     */
    OAK(
        logId = Items.OAK_LOGS_1521, // Unique ID for Oak log
        defaultLevel = 15, // Level required to use Oak log
        barbarianLevel = 35, // Level required for barbarian use of Oak log
        life = 200, // Durability of Oak log
        fireId = Scenery.FIRE_2732, // Fire ID associated with Oak log
        xp = 60.0 // Experience points gained from Oak log
    ),

    /**
     * Willow.
     */
    WILLOW(
        logId = Items.WILLOW_LOGS_1519, // Unique ID for Willow log
        defaultLevel = 30, // Level required to use Willow log
        barbarianLevel = 50, // Level required for barbarian use of Willow log
        life = 250, // Durability of Willow log
        fireId = Scenery.FIRE_2732, // Fire ID associated with Willow log
        xp = 90.0 // Experience points gained from Willow log
    ),

    /**
     * Teak.
     */
    TEAK(
        logId = Items.TEAK_LOGS_6333, // Unique ID for Teak log
        defaultLevel = 35, // Level required to use Teak log
        barbarianLevel = 55, // Level required for barbarian use of Teak log
        life = 300, // Durability of Teak log
        fireId = Scenery.FIRE_2732, // Fire ID associated with Teak log
        xp = 105.0 // Experience points gained from Teak log
    ),

    /**
     * Arctic Pine.
     */
    ARCTIC_PINE(
        logId = Items.ARCTIC_PINE_LOGS_10810, // Unique ID for Arctic Pine log
        defaultLevel = 42, // Level required to use Arctic Pine log
        barbarianLevel = 62, // Level required for barbarian use of Arctic Pine log
        life = 500, // Durability of Arctic Pine log
        fireId = Scenery.FIRE_2732, // Fire ID associated with Arctic Pine log
        xp = 125.0 // Experience points gained from Arctic Pine log
    ),

    /**
     * Maple.
     */
    MAPLE(
        logId = Items.MAPLE_LOGS_1517, // Unique ID for Maple log
        defaultLevel = 45, // Level required to use Maple log
        barbarianLevel = 65, // Level required for barbarian use of Maple log
        life = 300, // Durability of Maple log
        fireId = Scenery.FIRE_2732, // Fire ID associated with Maple log
        xp = 135.0 // Experience points gained from Maple log
    ),

    /**
     * Mahogany.
     */
    MAHOGANY(
        logId = Items.MAHOGANY_LOGS_6332, // Unique ID for Mahogany log
        defaultLevel = 50, // Level required to use Mahogany log
        barbarianLevel = 70, // Level required for barbarian use of Mahogany log
        life = 300, // Durability of Mahogany log
        fireId = Scenery.FIRE_2732, // Fire ID associated with Mahogany log
        xp = 157.5 // Experience points gained from Mahogany log
    ),

    /**
     * Eucalyptus.
     */
    EUCALYPTUS(
        logId = Items.EUCALYPTUS_LOGS_12581, // Unique ID for Eucalyptus log
        defaultLevel = 58, // Level required to use Eucalyptus log
        barbarianLevel = 68, // Level required for barbarian use of Eucalyptus log
        life = 300, // Durability of Eucalyptus log
        fireId = Scenery.FIRE_2732, // Fire ID associated with Eucalyptus log
        xp = 193.5 // Experience points gained from Eucalyptus log
    ),

    /**
     * Yew.
     */
    YEW(
        logId = Items.YEW_LOGS_1515, // Unique ID for Yew log
        defaultLevel = 60, // Level required to use Yew log
        barbarianLevel = 80, // Level required for barbarian use of Yew log
        life = 400, // Durability of Yew log
        fireId = Scenery.FIRE_2732, // Fire ID associated with Yew log
        xp = 202.5 // Experience points gained from Yew log
    ),

    /**
     * Magic.
     */
    MAGIC(
        logId = Items.MAGIC_LOGS_1513, // Unique ID for Magic log
        defaultLevel = 75, // Level required to use Magic log
        barbarianLevel = 95, // Level required for barbarian use of Magic log
        life = 450, // Durability of Magic log
        fireId = Scenery.FIRE_2732, // Fire ID associated with Magic log
        xp = 303.8 // Experience points gained from Magic log
    ),

    /**
     * Cursed Magic.
     */
    CURSED_MAGIC(
        logId = Items.CURSED_MAGIC_LOGS_13567, // Unique ID for Cursed Magic log
        defaultLevel = 82, // Level required to use Cursed Magic log
        barbarianLevel = 97, // Level required for barbarian use of Cursed Magic log
        life = 650, // Durability of Cursed Magic log
        fireId = Scenery.FIRE_2732, // Fire ID associated with Cursed Magic log
        xp = 303.8 // Experience points gained from Cursed Magic log
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