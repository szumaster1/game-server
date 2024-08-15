package content.global.skill.gathering.woodcutting

import core.ServerConstants
import core.game.world.repository.Repository.players

/**
 * Woodcutting node.
 */
enum class WoodcuttingNode {
    /**
     * Standard Tree 1
     *
     * @constructor Standard Tree 1
     */
    STANDARD_TREE_1(1276, 1342, 1.toByte()),

    /**
     * Standard Tree 2
     *
     * @constructor Standard Tree 2
     */
    STANDARD_TREE_2(1277, 1343, 1.toByte()),

    /**
     * Standard Tree 3
     *
     * @constructor Standard Tree 3
     */
    STANDARD_TREE_3(1278, 1342, 1.toByte()),

    /**
     * Standard Tree 4
     *
     * @constructor Standard Tree 4
     */
    STANDARD_TREE_4(1279, 1345, 1.toByte()),

    /**
     * Standard Tree 5
     *
     * @constructor Standard Tree 5
     */
    STANDARD_TREE_5(1280, 1343, 1.toByte()),

    /**
     * Standard Tree 6
     *
     * @constructor Standard Tree 6
     */
    STANDARD_TREE_6(1330, 1341, 1.toByte()),

    /**
     * Standard Tree 7
     *
     * @constructor Standard Tree 7
     */
    STANDARD_TREE_7(1331, 1341, 1.toByte()),

    /**
     * Standard Tree 8
     *
     * @constructor Standard Tree 8
     */
    STANDARD_TREE_8(1332, 1341, 1.toByte()),

    /**
     * Standard Tree 9
     *
     * @constructor Standard Tree 9
     */
    STANDARD_TREE_9(2409, 1342, 1.toByte()),

    /**
     * Standard Tree 10
     *
     * @constructor Standard Tree 10
     */
    STANDARD_TREE_10(3033, 1345, 1.toByte()),

    /**
     * Standard Tree 11
     *
     * @constructor Standard Tree 11
     */
    STANDARD_TREE_11(3034, 1345, 1.toByte()),

    /**
     * Standard Tree 12
     *
     * @constructor Standard Tree 12
     */
    STANDARD_TREE_12(3035, 1347, 1.toByte()),

    /**
     * Standard Tree 13
     *
     * @constructor Standard Tree 13
     */
    STANDARD_TREE_13(3036, 1351, 1.toByte()),

    /**
     * Standard Tree 14
     *
     * @constructor Standard Tree 14
     */
    STANDARD_TREE_14(3879, 3880, 1.toByte()),

    /**
     * Standard Tree 15
     *
     * @constructor Standard Tree 15
     */
    STANDARD_TREE_15(3881, 3880, 1.toByte()),

    /**
     * Standard Tree 16
     *
     * @constructor Standard Tree 16
     */
    STANDARD_TREE_16(3882, 3880, 1.toByte()),

    /**
     * Standard Tree 17
     *
     * @constructor Standard Tree 17
     */
    STANDARD_TREE_17(3883, 3884, 1.toByte()),

    /**
     * Standard Tree 18
     *
     * @constructor Standard Tree 18
     */
    STANDARD_TREE_18(10041, 1342, 1.toByte()),

    /**
     * Standard Tree 19
     *
     * @constructor Standard Tree 19
     */
    STANDARD_TREE_19(14308, 1342, 1.toByte()),

    /**
     * Standard Tree 20
     *
     * @constructor Standard Tree 20
     */
    STANDARD_TREE_20(14309, 1342, 1.toByte()),

    /**
     * Standard Tree 21
     *
     * @constructor Standard Tree 21
     */
    STANDARD_TREE_21(16264, 1342, 1.toByte()),

    /**
     * Standard Tree 22
     *
     * @constructor Standard Tree 22
     */
    STANDARD_TREE_22(16265, 1342, 1.toByte()),

    /**
     * Standard Tree 23
     *
     * @constructor Standard Tree 23
     */
    STANDARD_TREE_23(30132, 1342, 1.toByte()),

    /**
     * Standard Tree 24
     *
     * @constructor Standard Tree 24
     */
    STANDARD_TREE_24(30133, 1342, 1.toByte()),

    /**
     * Standard Tree 25
     *
     * @constructor Standard Tree 25
     */
    STANDARD_TREE_25(37477, 1342, 1.toByte()),

    /**
     * Standard Tree 26
     *
     * @constructor Standard Tree 26
     */
    STANDARD_TREE_26(37478, 37653, 1.toByte()),

    /**
     * Standard Tree 27
     *
     * @constructor Standard Tree 27
     */
    STANDARD_TREE_27(37652, 37653, 1.toByte()),

    /**
     * Dead Tree 1
     *
     * @constructor Dead Tree 1
     */
    DEAD_TREE_1(1282, 1347, 2.toByte()),

    /**
     * Dead Tree 2
     *
     * @constructor Dead Tree 2
     */
    DEAD_TREE_2(1283, 1347, 2.toByte()),

    /**
     * Dead Tree 3
     *
     * @constructor Dead Tree 3
     */
    DEAD_TREE_3(1284, 1348, 2.toByte()),

    /**
     * Dead Tree 4
     *
     * @constructor Dead Tree 4
     */
    DEAD_TREE_4(1285, 1349, 2.toByte()),

    /**
     * Dead Tree 5
     *
     * @constructor Dead Tree 5
     */
    DEAD_TREE_5(1286, 1351, 2.toByte()),

    /**
     * Dead Tree 6
     *
     * @constructor Dead Tree 6
     */
    DEAD_TREE_6(1289, 1353, 2.toByte()),

    /**
     * Dead Tree 7
     *
     * @constructor Dead Tree 7
     */
    DEAD_TREE_7(1290, 1354, 2.toByte()),

    /**
     * Dead Tree 8
     *
     * @constructor Dead Tree 8
     */
    DEAD_TREE_8(1291, 23054, 2.toByte()),

    /**
     * Dead Tree 9
     *
     * @constructor Dead Tree 9
     */
    DEAD_TREE_9(1365, 1352, 2.toByte()),

    /**
     * Dead Tree 10
     *
     * @constructor Dead Tree 10
     */
    DEAD_TREE_10(1383, 1358, 2.toByte()),

    /**
     * Dead Tree 11
     *
     * @constructor Dead Tree 11
     */
    DEAD_TREE_11(1384, 1359, 2.toByte()),

    /**
     * Dead Tree 12
     *
     * @constructor Dead Tree 12
     */
    DEAD_TREE_12(5902, 1347, 2.toByte()),

    /**
     * Dead Tree 13
     *
     * @constructor Dead Tree 13
     */
    DEAD_TREE_13(5903, 1353, 2.toByte()),

    /**
     * Dead Tree 14
     *
     * @constructor Dead Tree 14
     */
    DEAD_TREE_14(5904, 1353, 2.toByte()),

    /**
     * Dead Tree 15
     *
     * @constructor Dead Tree 15
     */
    DEAD_TREE_15(32294, 1353, 2.toByte()),

    /**
     * Dead Tree 16
     *
     * @constructor Dead Tree 16
     */
    DEAD_TREE_16(37481, 1347, 2.toByte()),

    /**
     * Dead Tree 17
     *
     * @constructor Dead Tree 17
     */
    DEAD_TREE_17(37482, 1351, 2.toByte()),

    /**
     * Dead Tree 18
     *
     * @constructor Dead Tree 18
     */
    DEAD_TREE_18(37483, 1358, 2.toByte()),

    /**
     * Dead Tree 19
     *
     * @constructor Dead Tree 19
     */
    DEAD_TREE_19(24168, 24169, 2.toByte()),

    /**
     * Evergreen 1
     *
     * @constructor Evergreen 1
     */
    EVERGREEN_1(1315, 1342, 3.toByte()),

    /**
     * Evergreen 2
     *
     * @constructor Evergreen 2
     */
    EVERGREEN_2(1316, 1355, 3.toByte()),

    /**
     * Evergreen 3
     *
     * @constructor Evergreen 3
     */
    EVERGREEN_3(1318, 1355, 3.toByte()),

    /**
     * Evergreen 4
     *
     * @constructor Evergreen 4
     */
    EVERGREEN_4(1319, 1355, 3.toByte()),

    /**
     * Jungle Tree 1
     *
     * @constructor Jungle Tree 1
     */
    JUNGLE_TREE_1(2887, 0, 4.toByte()),

    /**
     * Jungle Tree 2
     *
     * @constructor Jungle Tree 2
     */
    JUNGLE_TREE_2(2889, 0, 4.toByte()),

    /**
     * Jungle Tree 3
     *
     * @constructor Jungle Tree 3
     */
    JUNGLE_TREE_3(2890, 0, 4.toByte()),

    /**
     * Jungle Tree 4
     *
     * @constructor Jungle Tree 4
     */
    JUNGLE_TREE_4(4818, 0, 4.toByte()),

    /**
     * Jungle Tree 5
     *
     * @constructor Jungle Tree 5
     */
    JUNGLE_TREE_5(4820, 0, 4.toByte()),

    /**
     * Jungle Bush 1
     *
     * @constructor Jungle Bush 1
     */
    JUNGLE_BUSH_1(2892, 2894, 5.toByte()),

    /**
     * Jungle Bush 2
     *
     * @constructor Jungle Bush 2
     */
    JUNGLE_BUSH_2(2893, 2895, 5.toByte()),

    /**
     * Achey Tree
     *
     * @constructor Achey Tree
     */
    ACHEY_TREE(2023, 3371, 6.toByte()),

    /**
     * Oak Tree 1
     *
     * @constructor Oak Tree 1
     */
    OAK_TREE_1(1281, 1356, 7.toByte()),

    /**
     * Oak Tree 2
     *
     * @constructor Oak Tree 2
     */
    OAK_TREE_2(3037, 1357, 7.toByte()),

    /**
     * Oak Tree 3
     *
     * @constructor Oak Tree 3
     */
    OAK_TREE_3(37479, 1356, 7.toByte()),

    /**
     * Oak Tree 4
     *
     * @constructor Oak Tree 4
     */
    OAK_TREE_4(8467, 1356, 19.toByte(), true),

    /**
     * Willow Tree 1
     *
     * @constructor Willow Tree 1
     */
    WILLOW_TREE_1(1308, 7399, 8.toByte()),

    /**
     * Willow Tree 2
     *
     * @constructor Willow Tree 2
     */
    WILLOW_TREE_2(5551, 5554, 8.toByte()),

    /**
     * Willow Tree 3
     *
     * @constructor Willow Tree 3
     */
    WILLOW_TREE_3(5552, 5554, 8.toByte()),

    /**
     * Willow Tree 4
     *
     * @constructor Willow Tree 4
     */
    WILLOW_TREE_4(5553, 5554, 8.toByte()),

    /**
     * Willow Tree 5
     *
     * @constructor Willow Tree 5
     */
    WILLOW_TREE_5(37480, 7399, 8.toByte()),

    /**
     * Willow Tree 6
     *
     * @constructor Willow Tree 6
     */
    WILLOW_TREE_6(8488, 7399, 20.toByte(), true),

    /**
     * Teak 1
     *
     * @constructor Teak 1
     */
    TEAK_1(9036, 9037, 9.toByte()),

    /**
     * Teak 2
     *
     * @constructor Teak 2
     */
    TEAK_2(15062, 9037, 9.toByte()),

    /**
     * Maple Tree 1
     *
     * @constructor Maple Tree 1
     */
    MAPLE_TREE_1(1307, 7400, 10.toByte()),

    /**
     * Maple Tree 2
     *
     * @constructor Maple Tree 2
     */
    MAPLE_TREE_2(4674, 7400, 10.toByte()),

    /**
     * Maple Tree 3
     *
     * @constructor Maple Tree 3
     */
    MAPLE_TREE_3(8444, 7400, 21.toByte(), true),

    /**
     * Hollow Tree 1
     *
     * @constructor Hollow Tree 1
     */
    HOLLOW_TREE_1(2289, 2310, 11.toByte()),

    /**
     * Hollow Tree 2
     *
     * @constructor Hollow Tree 2
     */
    HOLLOW_TREE_2(4060, 4061, 11.toByte()),

    /**
     * Mahogany
     *
     * @constructor Mahogany
     */
    MAHOGANY(9034, 9035, 12.toByte()),

    /**
     * Swaying Tree
     *
     * @constructor Swaying Tree
     */
    SWAYING_TREE(4142, -1, 30.toByte()),

    /**
     * Arctic Pine
     *
     * @constructor Arctic Pine
     */
    ARCTIC_PINE(21273, 21274, 13.toByte()),

    /**
     * Eucalyptus 1
     *
     * @constructor Eucalyptus 1
     */
    EUCALYPTUS_1(28951, 28954, 14.toByte()),

    /**
     * Eucalyptus 2
     *
     * @constructor Eucalyptus 2
     */
    EUCALYPTUS_2(28952, 28955, 14.toByte()),

    /**
     * Eucalyptus 3
     *
     * @constructor Eucalyptus 3
     */
    EUCALYPTUS_3(28953, 28956, 14.toByte()),

    /**
     * Yew
     *
     * @constructor Yew
     */
    YEW(1309, 7402, 15.toByte()),

    /**
     * Yew 1
     *
     * @constructor Yew 1
     */
    YEW_1(8513, 7402, 22.toByte(), true),

    /**
     * Magic Tree 1
     *
     * @constructor Magic Tree 1
     */
    MAGIC_TREE_1(1306, 7401, 16.toByte()),

    /**
     * Magic Tree 2
     *
     * @constructor Magic Tree 2
     */
    MAGIC_TREE_2(37823, 37824, 16.toByte()),

    /**
     * Magic Tree 3
     *
     * @constructor Magic Tree 3
     */
    MAGIC_TREE_3(8409, 37824, 23.toByte(), true),

    /**
     * Cursed Magic Tree
     *
     * @constructor Cursed Magic Tree
     */
    CURSED_MAGIC_TREE(37821, 37822, 17.toByte()),

    /**
     * Dramen Tree
     *
     * @constructor Dramen Tree
     */
    DRAMEN_TREE(1292, -1, 18.toByte()),

    /**
     * Windswept Tree
     *
     * @constructor Windswept Tree
     */
    WINDSWEPT_TREE(18137, 1353, 19.toByte()),

    /**
     * Light Jungle 1
     *
     * @constructor Create empty Light Jungle 1
     */
    LIGHT_JUNGLE_1(9010, 9010, 31.toByte()),

    /**
     * Light Jungle 2
     *
     * @constructor Create empty Light Jungle 2
     */
    LIGHT_JUNGLE_2(9011, 9010, 31.toByte()),

    /**
     * Light Jungle 3
     *
     * @constructor Create empty Light Jungle 3
     */
    LIGHT_JUNGLE_3(9012, 9010, 31.toByte()),

    /**
     * Light Jungle 4
     *
     * @constructor Create empty Light Jungle 4
     */
    LIGHT_JUNGLE_4(9013, 9010, 31.toByte()),

    /**
     * Medium Jungle 1
     *
     * @constructor Create empty Medium Jungle 1
     */
    MEDIUM_JUNGLE_1(9015, 9015, 32.toByte()),

    /**
     * Medium Jungle 2
     *
     * @constructor Create empty Medium Jungle 2
     */
    MEDIUM_JUNGLE_2(9016, 9015, 32.toByte()),

    /**
     * Medium Jungle 3
     *
     * @constructor Create empty Medium Jungle 3
     */
    MEDIUM_JUNGLE_3(9017, 9015, 32.toByte()),

    /**
     * Medium Jungle 4
     *
     * @constructor Create empty Medium Jungle 4
     */
    MEDIUM_JUNGLE_4(9018, 9015, 32.toByte()),

    /**
     * Dense Jungle 1
     *
     * @constructor Create empty Medium Jungle 2
     */
    DENSE_JUNGLE_1(9020, 9020, 33.toByte()),

    /**
     * Dense Jungle 2
     *
     * @constructor Create empty Dense Jungle 1
     */

    DENSE_JUNGLE_2(9021, 9020, 33.toByte()),

    /**
     * Dense Jungle 3
     *
     * @constructor Create empty Dense Jungle 2
     */
    DENSE_JUNGLE_3(9022, 9020, 33.toByte()),

    /**
     * Dense Jungle 4
     *
     * @constructor Create empty Dense Jungle 3
     */
    DENSE_JUNGLE_4(9023, 9020, 33.toByte());

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

            31 -> { // Just some initial values for testing
                reward = 6281 // This should be correct
                respawnRate = 200 or (317 shl 16)
                rate = 0.2
                experience = 32.0 // This should be correct
                level = 10 // This should be correct
                rewardAmount = 50
                baseLow = 0.0
                baseHigh = 9.5
                tierModLow = 0.065
                tierModHigh = 0.25
            }

            32 -> { // Just some initial values for testing
                reward = 6283 // This should be correct
                respawnRate = 200 or (317 shl 16)
                rate = 0.2
                experience = 55.0 // This should be correct
                level = 20 // This should be correct
                rewardAmount = 50
                baseLow = 0.0
                baseHigh = 8.0
                tierModLow = 0.065
                tierModHigh = 0.25
            }

            33 -> { // Just some initial values for testing
                reward = 6285 // This should be correct
                respawnRate = 200 or (317 shl 16)
                rate = 0.2
                experience = 80.0 // This should be correct
                level = 35 // This should be correct
                rewardAmount = 50
                baseLow = 0.0
                baseHigh = 6.0
                tierModLow = 0.06
                tierModHigh = 0.25
            }
        }
    }

    // Property to get the minimum respawn time by applying a bitwise AND operation with 0xFFFF
    val minimumRespawn: Int
        get() = respawnRate and 0xFFFF

    // Property to get the maximum respawn time by shifting respawnRate 16 bits to the right and applying a bitwise AND operation with 0xFFFF
    val maximumRespawn: Int
        get() = (respawnRate shr 16) and 0xFFFF

    // Property to calculate the respawn duration based on minimum and maximum respawn times and the player ratio
    val respawnDuration: Int
        get() {
            // Calculate minimum respawn time
            val minimum = respawnRate and 0xFFFF
            // Calculate maximum respawn time
            val maximum = (respawnRate shr 16) and 0xFFFF
            // Calculate the ratio of maximum players to current players
            val playerRatio = ServerConstants.MAX_PLAYERS.toDouble() / players.size
            // Return the calculated respawn duration as an integer
            return (minimum + ((maximum - minimum) / playerRatio)).toInt()
        }

    companion object {
        // HashMap to store WoodcuttingNode instances by their IDs
        private val NODE_MAP = HashMap<Int, WoodcuttingNode>()
        // HashMap to store empty node IDs and their corresponding node IDs
        private val EMPTY_MAP = HashMap<Int, Int?>()

        // Initialization block to populate NODE_MAP and EMPTY_MAP with node data
        init {
            for (node in values()) {
                // Add node to NODE_MAP if it is not already present
                NODE_MAP.putIfAbsent(node.id, node)
                // Add empty node ID to EMPTY_MAP if it is not already present
                EMPTY_MAP.putIfAbsent(node.emptyId, node.id)
            }
        }

        // Static method to retrieve a WoodcuttingNode by its ID
        @JvmStatic
        fun forId(id: Int): WoodcuttingNode? {
            return NODE_MAP[id] as WoodcuttingNode
        }

        // Function to check if a node ID corresponds to an empty node
        fun isEmpty(id: Int): Boolean {
            return EMPTY_MAP[id] != null
        }
    }

}
