package content.global.skill.support.firemaking

import core.api.consts.Items
import core.api.consts.Scenery

enum class Log(val logId: Int, val level: Int, val life: Int, val fireId: Int, val xp: Double) {
    NORMAL(
        logId = Items.LOGS_1511,
        level = 1,
        life = 180,
        fireId = Scenery.FIRE_2732,
        xp = 40.0
    ),
    PURPLE(
        logId = Items.PURPLE_LOGS_10329,
        level = 1,
        life = 200,
        fireId = Scenery.FIRE_20001,
        xp = 50.0
    ),
    WHITE(
        logId = Items.WHITE_LOGS_10328,
        level = 1,
        life = 200,
        fireId = Scenery.FIRE_20000,
        xp = 50.0
    ),
    BLUE(
        logId = Items.BLUE_LOGS_7406,
        level = 1,
        life = 200,
        fireId = Scenery.FIRE_11406,
        xp = 50.0
    ),
    GREEN(
        logId = Items.GREEN_LOGS_7405,
        level = 1,
        life = 200,
        fireId = Scenery.FIRE_11405,
        xp = 50.0
    ),
    RED(
        logId = Items.RED_LOGS_7404,
        level = 1,
        life = 200,
        fireId = Scenery.FIRE_11404,
        xp = 50.0
    ),
    JOGRE(
        logId = Items.JOGRE_BONES_3125,
        level = 1,
        life = 200,
        fireId = Scenery.BURNING_BONES_3862,
        xp = 50.0
    ),
    ACHEY(
        logId = Items.ACHEY_TREE_LOGS_2862,
        level = 1,
        life = 180,
        fireId = Scenery.FIRE_2732,
        xp = 40.0
    ),
    OAK(
        logId = Items.OAK_LOGS_1521,
        level = 15,
        life = 200,
        fireId = Scenery.FIRE_2732,
        xp = 60.0
    ),
    WILLOW(
        logId = Items.WILLOW_LOGS_1519,
        level = 30,
        life = 250,
        fireId = Scenery.FIRE_2732,
        xp = 90.0
    ),
    TEAK(
        logId = Items.TEAK_LOGS_6333,
        level = 35,
        life = 300,
        fireId = Scenery.FIRE_2732,
        xp = 105.0
    ),
    ARCTIC_PINE(
        logId = Items.ARCTIC_PINE_LOGS_10810,
        level = 42,
        life = 500,
        fireId = Scenery.FIRE_2732,
        xp = 125.0
    ),
    MAPLE(
        logId = Items.MAPLE_LOGS_1517,
        level = 45,
        life = 300,
        fireId = Scenery.FIRE_2732,
        xp = 135.0
    ),
    MAHOGANY(
        logId = Items.MAHOGANY_LOGS_6332,
        level = 50,
        life = 300,
        fireId = Scenery.FIRE_2732,
        xp = 157.5
    ),
    EUCALYPTUS(
        logId = Items.EUCALYPTUS_LOGS_12581,
        level = 58,
        life = 300,
        fireId = Scenery.FIRE_2732,
        xp = 193.5
    ),
    YEW(
        logId = Items.YEW_LOGS_1515,
        level = 60,
        life = 400,
        fireId = Scenery.FIRE_2732,
        xp = 202.5
    ),
    MAGIC(
        logId = Items.MAGIC_LOGS_1513,
        level = 75,
        life = 450,
        fireId = Scenery.FIRE_2732,
        xp = 303.8
    ),
    CURSED_MAGIC(
        logId = Items.CURSED_MAGIC_LOGS_13567,
        level = 82,
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
