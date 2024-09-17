package content.global.skill.support.firemaking.data

import org.rs.consts.Items
import org.rs.consts.Scenery

/**
 * Representing different types of logs.
 */
enum class Log(val logId: Int, val defaultLevel: Int, val barbarianLevel: Int, val life: Int, val fireId: Int, val xp: Double) {
    NORMAL(
        logId = Items.LOGS_1511,
        defaultLevel = 1,
        barbarianLevel = 21,
        life = 180,
        fireId = Scenery.FIRE_2732,
        xp = 40.0
    ),
    PURPLE(
        logId = Items.PURPLE_LOGS_10329,
        defaultLevel = 1,
        barbarianLevel = 21,
        life = 200,
        fireId = Scenery.FIRE_20001,
        xp = 50.0
    ),
    WHITE(
        logId = Items.WHITE_LOGS_10328,
        defaultLevel = 1,
        barbarianLevel = 21,
        life = 200,
        fireId = Scenery.FIRE_20000,
        xp = 50.0
    ),
    BLUE(
        logId = Items.BLUE_LOGS_7406,
        defaultLevel = 1,
        barbarianLevel = 21,
        life = 200,
        fireId = Scenery.FIRE_11406,
        xp = 50.0
    ),
    GREEN(
        logId = Items.GREEN_LOGS_7405,
        defaultLevel = 1,
        barbarianLevel = 21,
        life = 200,
        fireId = Scenery.FIRE_11405,
        xp = 50.0
    ),
    RED(
        logId = Items.RED_LOGS_7404,
        defaultLevel = 1,
        barbarianLevel = 21,
        life = 200,
        fireId = Scenery.FIRE_11404,
        xp = 50.0
    ),
    JOGRE(
        logId = Items.JOGRE_BONES_3125,
        defaultLevel = 1,
        barbarianLevel = 21,
        life = 200,
        fireId = Scenery.BURNING_BONES_3862,
        xp = 50.0
    ),
    ACHEY(
        logId = Items.ACHEY_TREE_LOGS_2862,
        defaultLevel = 1,
        barbarianLevel = 21,
        life = 180,
        fireId = Scenery.FIRE_2732,
        xp = 40.0
    ),
    OAK(
        logId = Items.OAK_LOGS_1521,
        defaultLevel = 15,
        barbarianLevel = 35,
        life = 200,
        fireId = Scenery.FIRE_2732,
        xp = 60.0
    ),
    WILLOW(
        logId = Items.WILLOW_LOGS_1519,
        defaultLevel = 30,
        barbarianLevel = 50,
        life = 250,
        fireId = Scenery.FIRE_2732,
        xp = 90.0
    ),
    TEAK(
        logId = Items.TEAK_LOGS_6333,
        defaultLevel = 35,
        barbarianLevel = 55,
        life = 300,
        fireId = Scenery.FIRE_2732,
        xp = 105.0
    ),
    ARCTIC_PINE(
        logId = Items.ARCTIC_PINE_LOGS_10810,
        defaultLevel = 42,
        barbarianLevel = 62,
        life = 500,
        fireId = Scenery.FIRE_2732,
        xp = 125.0
    ),
    MAPLE(
        logId = Items.MAPLE_LOGS_1517,
        defaultLevel = 45,
        barbarianLevel = 65,
        life = 300,
        fireId = Scenery.FIRE_2732,
        xp = 135.0
    ),
    MAHOGANY(
        logId = Items.MAHOGANY_LOGS_6332,
        defaultLevel = 50,
        barbarianLevel = 70,
        life = 300,
        fireId = Scenery.FIRE_2732,
        xp = 157.5
    ),
    EUCALYPTUS(
        logId = Items.EUCALYPTUS_LOGS_12581,
        defaultLevel = 58,
        barbarianLevel = 68,
        life = 300,
        fireId = Scenery.FIRE_2732,
        xp = 193.5
    ),
    YEW(
        logId = Items.YEW_LOGS_1515,
        defaultLevel = 60,
        barbarianLevel = 80,
        life = 400,
        fireId = Scenery.FIRE_2732,
        xp = 202.5
    ),
    MAGIC(
        logId = Items.MAGIC_LOGS_1513,
        defaultLevel = 75,
        barbarianLevel = 95,
        life = 450,
        fireId = Scenery.FIRE_2732,
        xp = 303.8
    ),
    CURSED_MAGIC(
        logId = Items.CURSED_MAGIC_LOGS_13567,
        defaultLevel = 82,
        barbarianLevel = 97,
        life = 650,
        fireId = Scenery.FIRE_2732,
        xp = 303.8
    );

    companion object {
        var logMap: HashMap<Int, Log> = HashMap()

        init {
            val logArray = values()
            val logLength = logArray.size
            for (log in logArray) {
                logMap.putIfAbsent(log.logId, log)
            }
        }

        @JvmStatic
        fun forId(id: Int): Log? {
            return logMap[id]
        }
    }
}