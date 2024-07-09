package content.global.skill.gathering.woodcutting

import core.ServerConstants
import core.game.world.repository.Repository.players

enum class WoodcuttingNode {
    STANDARD_TREE_1(1276, 1342, 1.toByte()),
    STANDARD_TREE_2(1277, 1343, 1.toByte()),
    STANDARD_TREE_3(1278, 1342, 1.toByte()),
    STANDARD_TREE_4(1279, 1345, 1.toByte()),
    STANDARD_TREE_5(1280, 1343, 1.toByte()),
    STANDARD_TREE_6(1330, 1341, 1.toByte()),
    STANDARD_TREE_7(1331, 1341, 1.toByte()),
    STANDARD_TREE_8(1332, 1341, 1.toByte()),
    STANDARD_TREE_9(2409, 1342, 1.toByte()),
    STANDARD_TREE_10(3033, 1345, 1.toByte()),
    STANDARD_TREE_11(3034, 1345, 1.toByte()),
    STANDARD_TREE_12(3035, 1347, 1.toByte()),
    STANDARD_TREE_13(3036, 1351, 1.toByte()),
    STANDARD_TREE_14(3879, 3880, 1.toByte()),
    STANDARD_TREE_15(3881, 3880, 1.toByte()),
    STANDARD_TREE_16(3882, 3880, 1.toByte()),
    STANDARD_TREE_17(3883, 3884, 1.toByte()),
    STANDARD_TREE_18(10041, 1342, 1.toByte()),
    STANDARD_TREE_19(14308, 1342, 1.toByte()),
    STANDARD_TREE_20(14309, 1342, 1.toByte()),
    STANDARD_TREE_21(16264, 1342, 1.toByte()),
    STANDARD_TREE_22(16265, 1342, 1.toByte()),
    STANDARD_TREE_23(30132, 1342, 1.toByte()),
    STANDARD_TREE_24(30133, 1342, 1.toByte()),
    STANDARD_TREE_25(37477, 1342, 1.toByte()),
    STANDARD_TREE_26(37478, 37653, 1.toByte()),
    STANDARD_TREE_27(37652, 37653, 1.toByte()),

    DEAD_TREE_1(1282, 1347, 2.toByte()),
    DEAD_TREE_2(1283, 1347, 2.toByte()),
    DEAD_TREE_3(1284, 1348, 2.toByte()),
    DEAD_TREE_4(1285, 1349, 2.toByte()),
    DEAD_TREE_5(1286, 1351, 2.toByte()),
    DEAD_TREE_6(1289, 1353, 2.toByte()),
    DEAD_TREE_7(1290, 1354, 2.toByte()),
    DEAD_TREE_8(1291, 23054, 2.toByte()),
    DEAD_TREE_9(1365, 1352, 2.toByte()),
    DEAD_TREE_10(1383, 1358, 2.toByte()),
    DEAD_TREE_11(1384, 1359, 2.toByte()),
    DEAD_TREE_12(5902, 1347, 2.toByte()),
    DEAD_TREE_13(5903, 1353, 2.toByte()),
    DEAD_TREE_14(5904, 1353, 2.toByte()),
    DEAD_TREE_15(32294, 1353, 2.toByte()),
    DEAD_TREE_16(37481, 1347, 2.toByte()),
    DEAD_TREE_17(37482, 1351, 2.toByte()),
    DEAD_TREE_18(37483, 1358, 2.toByte()),
    DEAD_TREE_19(24168, 24169, 2.toByte()),

    EVERGREEN_1(1315, 1342, 3.toByte()),
    EVERGREEN_2(1316, 1355, 3.toByte()),
    EVERGREEN_3(1318, 1355, 3.toByte()),
    EVERGREEN_4(1319, 1355, 3.toByte()),

    JUNGLE_TREE_1(2887, 0, 4.toByte()),
    JUNGLE_TREE_2(2889, 0, 4.toByte()),
    JUNGLE_TREE_3(2890, 0, 4.toByte()),
    JUNGLE_TREE_4(4818, 0, 4.toByte()),
    JUNGLE_TREE_5(4820, 0, 4.toByte()),

    JUNGLE_BUSH_1(2892, 2894, 5.toByte()),
    JUNGLE_BUSH_2(2893, 2895, 5.toByte()),

    ACHEY_TREE(2023, 3371, 6.toByte()),

    OAK_TREE_1(1281, 1356, 7.toByte()),
    OAK_TREE_2(3037, 1357, 7.toByte()),
    OAK_TREE_3(37479, 1356, 7.toByte()),
    OAK_TREE_4(8467, 1356, 19.toByte(), true),

    WILLOW_TREE_1(1308, 7399, 8.toByte()),
    WILLOW_TREE_2(5551, 5554, 8.toByte()),
    WILLOW_TREE_3(5552, 5554, 8.toByte()),
    WILLOW_TREE_4(5553, 5554, 8.toByte()),
    WILLOW_TREE_5(37480, 7399, 8.toByte()),
    WILLOW_TREE_6(8488, 7399, 20.toByte(), true),

    TEAK_1(9036, 9037, 9.toByte()),
    TEAK_2(15062, 9037, 9.toByte()),

    MAPLE_TREE_1(1307, 7400, 10.toByte()),
    MAPLE_TREE_2(4674, 7400, 10.toByte()),
    MAPLE_TREE_3(8444, 7400, 21.toByte(), true),

    HOLLOW_TREE_1(2289, 2310, 11.toByte()),
    HOLLOW_TREE_2(4060, 4061, 11.toByte()),

    MAHOGANY(9034, 9035, 12.toByte()),

    SWAYING_TREE(4142, -1, 30.toByte()),

    ARCTIC_PINE(21273, 21274, 13.toByte()),

    EUCALYPTUS_1(28951, 28954, 14.toByte()),
    EUCALYPTUS_2(28952, 28955, 14.toByte()),
    EUCALYPTUS_3(28953, 28956, 14.toByte()),

    YEW(1309, 7402, 15.toByte()),
    YEW_1(8513, 7402, 22.toByte(), true),

    MAGIC_TREE_1(1306, 7401, 16.toByte()),
    MAGIC_TREE_2(37823, 37824, 16.toByte()),
    MAGIC_TREE_3(8409, 37824, 23.toByte(), true),

    CURSED_MAGIC_TREE(37821, 37822, 17.toByte()),

    DRAMEN_TREE(1292, -1, 18.toByte()),

    WINDSWEPT_TREE(18137, 1353, 19.toByte());


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
            val playerRatio = ServerConstants.MAX_PLAYERS.toDouble() / players.size
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
