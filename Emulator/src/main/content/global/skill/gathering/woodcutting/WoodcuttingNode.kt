package content.global.skill.gathering.woodcutting

import core.Configuration
import core.game.world.repository.Repository.players

enum class WoodcuttingNode {
    STANDARD_TREE_1(
        full = 1276,
        empty = 1342,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_2(
        full = 1277,
        empty = 1343,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_3(
        full = 1278,
        empty = 1342,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_4(
        full = 1279,
        empty = 1345,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_5(
        full = 1280,
        empty = 1343,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_6(
        full = 1330,
        empty = 1341,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_7(
        full = 1331,
        empty = 1341,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_8(
        full = 1332,
        empty = 1341,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_9(
        full = 2409,
        empty = 1342,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_10(
        full = 3033,
        empty = 1345,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_11(
        full = 3034,
        empty = 1345,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_12(
        full = 3035,
        empty = 1347,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_13(
        full = 3036,
        empty = 1351,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_14(
        full = 3879,
        empty = 3880,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_15(
        full = 3881,
        empty = 3880,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_16(
        full = 3882,
        empty = 3880,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_17(
        full = 3883,
        empty = 3884,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_18(
        full = 10041,
        empty = 1342,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_19(
        full = 14308,
        empty = 1342,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_20(
        full = 14309,
        empty = 1342,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_21(
        full = 16264,
        empty = 1342,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_22(
        full = 16265,
        empty = 1342,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_23(
        full = 30132,
        empty = 1342,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_24(
        full = 30133,
        empty = 1342,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_25(
        full = 37477,
        empty = 1342,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_26(
        full = 37478,
        empty = 37653,
        identifier = 1.toByte()
    ),
    STANDARD_TREE_27(
        full = 37652,
        empty = 37653,
        identifier = 1.toByte()
    ),
    DEAD_TREE_1(
        full = 1282,
        empty = 1347,
        identifier = 2.toByte()
    ),
    DEAD_TREE_2(
        full = 1283,
        empty = 1347,
        identifier = 2.toByte()
    ),
    DEAD_TREE_3(
        full = 1284,
        empty = 1348,
        identifier = 2.toByte()
    ),
    DEAD_TREE_4(
        full = 1285,
        empty = 1349,
        identifier = 2.toByte()
    ),
    DEAD_TREE_5(
        full = 1286,
        empty = 1351,
        identifier = 2.toByte()
    ),
    DEAD_TREE_6(
        full = 1289,
        empty = 1353,
        identifier = 2.toByte()
    ),
    DEAD_TREE_7(
        full = 1290,
        empty = 1354,
        identifier = 2.toByte()
    ),
    DEAD_TREE_8(
        full = 1291,
        empty = 23054,
        identifier = 2.toByte()
    ),
    DEAD_TREE_9(
        full = 1365,
        empty = 1352,
        identifier = 2.toByte()
    ),
    DEAD_TREE_10(
        full = 1383,
        empty = 1358,
        identifier = 2.toByte()
    ),
    DEAD_TREE_11(
        full = 1384,
        empty = 1359,
        identifier = 2.toByte()
    ),
    DEAD_TREE_12(
        full = 5902,
        empty = 1347,
        identifier = 2.toByte()
    ),
    DEAD_TREE_13(
        full = 5903,
        empty = 1353,
        identifier = 2.toByte()
    ),
    DEAD_TREE_14(
        full = 5904,
        empty = 1353,
        identifier = 2.toByte()
    ),
    DEAD_TREE_15(
        full = 32294,
        empty = 1353,
        identifier = 2.toByte()
    ),
    DEAD_TREE_16(
        full = 37481,
        empty = 1347,
        identifier = 2.toByte()
    ),
    DEAD_TREE_17(
        full = 37482,
        empty = 1351,
        identifier = 2.toByte()
    ),
    DEAD_TREE_18(
        full = 37483,
        empty = 1358,
        identifier = 2.toByte()
    ),
    DEAD_TREE_19(
        full = 24168,
        empty = 24169,
        identifier = 2.toByte()
    ),
    EVERGREEN_1(
        full = 1315,
        empty = 1342,
        identifier = 3.toByte()
    ),
    EVERGREEN_2(
        full = 1316,
        empty = 1355,
        identifier = 3.toByte()
    ),
    EVERGREEN_3(
        full = 1318,
        empty = 1355,
        identifier = 3.toByte()
    ),
    EVERGREEN_4(
        full = 1319,
        empty = 1355,
        identifier = 3.toByte()
    ),
    JUNGLE_TREE_1(
        full = 2887,
        empty = 0,
        identifier = 4.toByte()
    ),
    JUNGLE_TREE_2(
        full = 2889,
        empty = 0,
        identifier = 4.toByte()
    ),
    JUNGLE_TREE_3(
        full = 2890,
        empty = 0,
        identifier = 4.toByte()
    ),
    JUNGLE_TREE_4(
        full = 4818,
        empty = 0,
        identifier = 4.toByte()
    ),
    JUNGLE_TREE_5(
        full = 4820,
        empty = 0,
        identifier = 4.toByte()
    ),
    JUNGLE_BUSH_1(
        full = 2892,
        empty = 2894,
        identifier = 5.toByte()
    ),
    JUNGLE_BUSH_2(
        full = 2893,
        empty = 2895,
        identifier = 5.toByte()
    ),
    ACHEY_TREE(
        full = 2023,
        empty = 3371,
        identifier = 6.toByte()
    ),
    OAK_TREE_1(
        full = 1281,
        empty = 1356,
        identifier = 7.toByte()
    ),
    OAK_TREE_2(
        full = 3037,
        empty = 1357,
        identifier = 7.toByte()
    ),
    OAK_TREE_3(
        full = 37479,
        empty = 1356,
        identifier = 7.toByte()
    ),
    OAK_TREE_4(
        full = 8467,
        empty = 1356,
        identifier = 19.toByte(),
        farming = true
    ),
    WILLOW_TREE_1(
        full = 1308,
        empty = 7399,
        identifier = 8.toByte()
    ),
    WILLOW_TREE_2(
        full = 5551,
        empty = 5554,
        identifier = 8.toByte()
    ),
    WILLOW_TREE_3(
        full = 5552,
        empty = 5554,
        identifier = 8.toByte()
    ),
    WILLOW_TREE_4(
        full = 5553,
        empty = 5554,
        identifier = 8.toByte()
    ),
    WILLOW_TREE_5(
        full = 37480,
        empty = 7399,
        identifier = 8.toByte()
    ),
    WILLOW_TREE_6(
        full = 8488,
        empty = 7399,
        identifier = 20.toByte(),
        farming = true
    ),
    TEAK_1(
        full = 9036,
        empty = 9037,
        identifier = 9.toByte()
    ),
    TEAK_2(
        full = 15062,
        empty = 9037,
        identifier = 9.toByte()
    ),
    MAPLE_TREE_1(
        full = 1307,
        empty = 7400,
        identifier = 10.toByte()
    ),
    MAPLE_TREE_2(
        full = 4674,
        empty = 7400,
        identifier = 10.toByte()
    ),
    MAPLE_TREE_3(
        full = 8444,
        empty = 7400,
        identifier = 21.toByte(),
        farming = true
    ),
    HOLLOW_TREE_1(
        full = 2289,
        empty = 2310,
        identifier = 11.toByte()
    ),
    HOLLOW_TREE_2(
        full = 4060,
        empty = 4061,
        identifier = 11.toByte()
    ),
    MAHOGANY(
        full = 9034,
        empty = 9035,
        identifier = 12.toByte()
    ),
    SWAYING_TREE(
        full = 4142,
        empty = -1,
        identifier = 30.toByte()
    ),
    ARCTIC_PINE(
        full = 21273,
        empty = 21274,
        identifier = 13.toByte()
    ),
    EUCALYPTUS_1(
        full = 28951,
        empty = 28954,
        identifier = 14.toByte()
    ),
    EUCALYPTUS_2(
        full = 28952,
        empty = 28955,
        identifier = 14.toByte()
    ),
    EUCALYPTUS_3(
        full = 28953,
        empty = 28956,
        identifier = 14.toByte()
    ),
    YEW(
        full = 1309,
        empty = 7402,
        identifier = 15.toByte()
    ),
    YEW_1(
        full = 8513,
        empty = 7402,
        identifier = 22.toByte(),
        farming = true
    ),
    MAGIC_TREE_1(
        full = 1306,
        empty = 7401,
        identifier = 16.toByte()
    ),
    MAGIC_TREE_2(
        full = 37823,
        empty = 37824,
        identifier = 16.toByte()
    ),
    MAGIC_TREE_3(
        full = 8409,
        empty = 37824,
        identifier = 23.toByte(),
        farming = true
    ),
    CURSED_MAGIC_TREE(
        full = 37821,
        empty = 37822,
        identifier = 17.toByte()
    ),
    DRAMEN_TREE(
        full = 1292,
        empty = -1,
        identifier = 18.toByte()
    ),
    WINDSWEPT_TREE(
        full = 18137,
        empty = 1353,
        identifier = 19.toByte()
    ),
    LIGHT_JUNGLE_1(
        full = 9010,
        empty = 9010,
        identifier = 31.toByte()
    ),
    LIGHT_JUNGLE_2(
        full = 9011,
        empty = 9010,
        identifier = 31.toByte()
    ),
    LIGHT_JUNGLE_3(
        full = 9012,
        empty = 9010,
        identifier = 31.toByte()
    ),
    LIGHT_JUNGLE_4(
        full = 9013,
        empty = 9010,
        identifier = 31.toByte()
    ),
    MEDIUM_JUNGLE_1(
        full = 9015,
        empty = 9015,
        identifier = 32.toByte()
    ),
    MEDIUM_JUNGLE_2(
        full = 9016,
        empty = 9015,
        identifier = 32.toByte()
    ),
    MEDIUM_JUNGLE_3(
        full = 9017,
        empty = 9015,
        identifier = 32.toByte()
    ),
    MEDIUM_JUNGLE_4(
        full = 9018,
        empty = 9015,
        identifier = 32.toByte()
    ),
    DENSE_JUNGLE_1(
        full = 9020,
        empty = 9020,
        identifier = 33.toByte()
    ),
    DENSE_JUNGLE_2(
        full = 9021,
        empty = 9020,
        identifier = 33.toByte()
    ),
    DENSE_JUNGLE_3(
        full = 9022,
        empty = 9020,
        identifier = 33.toByte()
    ),
    DENSE_JUNGLE_4(
        full = 9023,
        empty = 9020,
        identifier = 33.toByte()
    );

    var id: Int
    var emptyId: Int

    @JvmField
    var reward: Int = 0

    @JvmField
    var respawnRate: Int = 0

    @JvmField
    var level: Int = 0
    var rewardAmount: Int = 0

    @JvmField
    var experience: Double = 0.0
    var rate: Double = 0.0

    @JvmField
    var identifier: Byte
    var isFarming: Boolean

    @JvmField
    var baseLow: Double = 2.0

    @JvmField
    var baseHigh: Double = 6.0

    @JvmField
    var tierModLow: Double = 1.0

    @JvmField
    var tierModHigh: Double = 3.0

    constructor(full: Int, empty: Int, identifier: Byte) {
        this.id = full
        this.emptyId = empty
        this.identifier = identifier
        this.isFarming = false
        this.rewardAmount = 1
        when (identifier.toInt() and 0xFF) {
            1, 2, 3, 4 -> {
                reward = 1511
                respawnRate = 50 or (100 shl 16)
                rate = 0.05
                experience = 25.0
                level = 1
                baseLow = 64.0
                baseHigh = 200.0
                tierModLow = 32.0
                tierModHigh = 100.0
            }

            5 -> {
                reward = 1511
                respawnRate = 50 or (100 shl 16)
                rate = 0.15
                experience = 100.0
                level = 1
                this.rewardAmount = 2
                baseLow = 64.0
                baseHigh = 200.0
                tierModLow = 32.0
                tierModHigh = 100.0
            }

            6 -> {
                reward = 2862
                respawnRate = 50 or (100 shl 16)
                rate = 0.05
                experience = 25.0
                level = 1
                baseLow = 64.0
                baseHigh = 200.0
                tierModLow = 32.0
                tierModHigh = 100.0
            }

            7 -> {
                reward = 1521
                respawnRate = 14 or (22 shl 16)
                rate = 0.15
                experience = 37.5
                level = 15
                rewardAmount = 10
                baseLow = 32.0
                baseHigh = 100.0
                tierModLow = 16.0
                tierModHigh = 50.0
            }

            8 -> {
                reward = 1519
                respawnRate = 14 or (22 shl 16)
                rate = 0.3
                experience = 67.8
                level = 30
                rewardAmount = 20
                baseLow = 16.0
                baseHigh = 50.0
                tierModLow = 8.0
                tierModHigh = 25.0
            }

            9 -> {
                reward = 6333
                respawnRate = 35 or (60 shl 16)
                rate = 0.7
                experience = 85.0
                level = 35
                rewardAmount = 25
                baseLow = 15.0
                baseHigh = 46.0
                tierModLow = 8.0
                tierModHigh = 23.5
            }

            10 -> {
                reward = 1517
                respawnRate = 58 or (100 shl 16)
                rate = 0.65
                experience = 100.0
                level = 45
                rewardAmount = 30
                baseLow = 8.0
                baseHigh = 25.0
                tierModLow = 4.0
                tierModHigh = 12.5
            }

            11 -> {
                reward = 3239
                respawnRate = 58 or (100 shl 16)
                rate = 0.6
                experience = 82.5
                level = 45
                rewardAmount = 30
                baseLow = 18.0
                baseHigh = 26.0
                tierModLow = 10.0
                tierModHigh = 14.0
            }

            12 -> {
                reward = 6332
                respawnRate = 62 or (115 shl 16)
                rate = 0.7
                experience = 125.0
                level = 50
                rewardAmount = 35
                baseLow = 8.0
                baseHigh = 25.0
                tierModLow = 4.0
                tierModHigh = 12.5
            }

            13 -> {
                reward = 10810
                respawnRate = 75 or (130 shl 16)
                rate = 0.73
                experience = 140.2
                level = 54
                rewardAmount = 35
                baseLow = 6.0
                baseHigh = 30.0
                tierModLow = 3.0
                tierModHigh = 13.5
            }

            14 -> {
                reward = 12581
                respawnRate = 80 or (140 shl 16)
                rate = 0.77
                experience = 165.0
                level = 58
                rewardAmount = 35
            }

            15 -> {
                reward = 1515
                respawnRate = 100 or (162 shl 16)
                rate = 0.8
                experience = 175.0
                level = 60
                rewardAmount = 40
                baseLow = 4.0
                baseHigh = 12.5
                tierModLow = 2.0
                tierModHigh = 6.25
            }

            16 -> {
                reward = 1513
                respawnRate = 200 or (317 shl 16)
                rate = 0.9
                experience = 250.0
                level = 75
                rewardAmount = 50
                baseLow = 2.0
                baseHigh = 6.0
                tierModLow = 1.0
                tierModHigh = 3.0
            }

            17 -> {
                reward = 1513
                respawnRate = 200 or (317 shl 16)
                rate = 0.95
                experience = 275.0
                level = 82
                rewardAmount = 50
            }

            18 -> {
                reward = 771
                respawnRate = -1
                rate = 0.05
                experience = 25.0
                level = 36
                rewardAmount = Int.MAX_VALUE
                baseLow = 255.0
                baseHigh = 255.0
                tierModLow = 0.0
                tierModHigh = 0.0
            }

            19 -> {
                reward = 11035
                respawnRate = 50 or (100 shl 16)
                rate = 0.05
                experience = 1.0
                level = 50
                this.rewardAmount = 1
            }

            30 -> {
                reward = 3692
                respawnRate = -1
                rate = 0.05
                experience = 1.0
                level = 40
                rewardAmount = Int.MAX_VALUE
            }
        }
    }

    constructor(full: Int, empty: Int, identifier: Byte, farming: Boolean) {
        this.id = full
        this.emptyId = empty
        this.identifier = identifier
        this.isFarming = farming
        when (identifier.toInt() and 0xFF) {
            19 -> {
                reward = 1521
                respawnRate = 14 or (22 shl 16)
                rate = 0.15
                experience = 37.5
                level = 15
                rewardAmount = 10
            }

            20 -> {
                reward = 1519
                respawnRate = 14 or (22 shl 16)
                rate = 0.3
                experience = 67.8
                level = 30
                rewardAmount = 20
            }

            21 -> {
                reward = 1517
                respawnRate = 58 or (100 shl 16)
                rate = 0.65
                experience = 100.0
                level = 45
                rewardAmount = 30
            }

            22 -> {
                reward = 1515
                respawnRate = 100 or (162 shl 16)
                rate = 0.8
                experience = 175.0
                level = 60
                rewardAmount = 40
            }

            23 -> {
                reward = 1513
                respawnRate = 200 or (317 shl 16)
                rate = 0.9
                experience = 250.0
                level = 75
                rewardAmount = 50
            }

            31 -> {
                reward = 6281
                respawnRate = 200 or (317 shl 16)
                rate = 0.2
                experience = 32.0
                level = 10
                rewardAmount = 50
                baseLow = 0.0
                baseHigh = 9.5
                tierModLow = 0.065
                tierModHigh = 0.25
            }

            32 -> {
                reward = 6283
                respawnRate = 200 or (317 shl 16)
                rate = 0.2
                experience = 55.0
                level = 20
                rewardAmount = 50
                baseLow = 0.0
                baseHigh = 8.0
                tierModLow = 0.065
                tierModHigh = 0.25
            }

            33 -> {
                reward = 6285
                respawnRate = 200 or (317 shl 16)
                rate = 0.2
                experience = 80.0
                level = 35
                rewardAmount = 50
                baseLow = 0.0
                baseHigh = 6.0
                tierModLow = 0.06
                tierModHigh = 0.25
            }
        }
    }

    val minimumRespawn: Int
        get() = respawnRate and 0xFFFF
    val maximumRespawn: Int
        get() = (respawnRate shr 16) and 0xFFFF
    val respawnDuration: Int
        get() {
            val minimum = respawnRate and 0xFFFF
            val maximum = (respawnRate shr 16) and 0xFFFF
            val playerRatio = Configuration.MAX_PLAYERS.toDouble() / players.size
            return (minimum + ((maximum - minimum) / playerRatio)).toInt()
        }

    companion object {
        private val NODE_MAP = HashMap<Int, WoodcuttingNode>()
        private val EMPTY_MAP = HashMap<Int, Int?>()

        init {
            for (node in values()) {
                NODE_MAP.putIfAbsent(node.id, node)
                EMPTY_MAP.putIfAbsent(node.emptyId, node.id)
            }
        }

        @JvmStatic
        fun forId(id: Int): WoodcuttingNode? {
            return NODE_MAP[id] as WoodcuttingNode
        }

        fun isEmpty(id: Int): Boolean {
            return EMPTY_MAP[id] != null
        }
    }
}