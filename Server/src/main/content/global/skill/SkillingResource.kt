package content.global.skill

import core.ServerConstants
import core.cache.def.impl.SceneryDefinition
import core.game.node.entity.skill.Skills
import core.game.world.repository.Repository.players
import core.game.world.update.flag.context.Animation

/**
 * Skilling resource.
 */
enum class SkillingResource {
    /**
     * Standard Tree 1
     *
     * @constructor Standard Tree 1
     */
    STANDARD_TREE_1(1276, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),

    /**
     * Standard Tree 2
     *
     * @constructor Standard Tree 2
     */
    STANDARD_TREE_2(1277, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1343, Skills.WOODCUTTING),

    /**
     * Standard Tree 3
     *
     * @constructor Standard Tree 3
     */
    STANDARD_TREE_3(1278, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),

    /**
     * Standard Tree 4
     *
     * @constructor Standard Tree 4
     */
    STANDARD_TREE_4(1279, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1345, Skills.WOODCUTTING),

    /**
     * Standard Tree 5
     *
     * @constructor Standard Tree 5
     */
    STANDARD_TREE_5(1280, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1343, Skills.WOODCUTTING),

    /**
     * Standard Tree 6
     *
     * @constructor Standard Tree 6
     */
    STANDARD_TREE_6(1330, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1341, Skills.WOODCUTTING),

    /**
     * Standard Tree 7
     *
     * @constructor Standard Tree 7
     */
    STANDARD_TREE_7(1331, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1341, Skills.WOODCUTTING),

    /**
     * Standard Tree 8
     *
     * @constructor Standard Tree 8
     */
    STANDARD_TREE_8(1332, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1341, Skills.WOODCUTTING),

    /**
     * Standard Tree 9
     *
     * @constructor Standard Tree 9
     */
    STANDARD_TREE_9(2409, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),

    /**
     * Standard Tree 10
     *
     * @constructor Standard Tree 10
     */
    STANDARD_TREE_10(3033, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1345, Skills.WOODCUTTING),

    /**
     * Standard Tree 11
     *
     * @constructor Standard Tree 11
     */
    STANDARD_TREE_11(3034, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1345, Skills.WOODCUTTING),

    /**
     * Standard Tree 12
     *
     * @constructor Standard Tree 12
     */
    STANDARD_TREE_12(3035, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1347, Skills.WOODCUTTING),

    /**
     * Standard Tree 13
     *
     * @constructor Standard Tree 13
     */
    STANDARD_TREE_13(3036, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1351, Skills.WOODCUTTING),

    /**
     * Standard Tree 14
     *
     * @constructor Standard Tree 14
     */
    STANDARD_TREE_14(3879, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 3880, Skills.WOODCUTTING),

    /**
     * Standard Tree 15
     *
     * @constructor Standard Tree 15
     */
    STANDARD_TREE_15(3881, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 3880, Skills.WOODCUTTING),

    /**
     * Standard Tree 16
     *
     * @constructor Standard Tree 16
     */
    STANDARD_TREE_16(3882, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 3880, Skills.WOODCUTTING),

    /**
     * Standard Tree 17
     *
     * @constructor Standard Tree 17
     */
    STANDARD_TREE_17(3883, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 3884, Skills.WOODCUTTING),

    /**
     * Standard Tree 18
     *
     * @constructor Standard Tree 18
     */
    STANDARD_TREE_18(10041, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),

    /**
     * Standard Tree 19
     *
     * @constructor Standard Tree 19
     */
    STANDARD_TREE_19(14308, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),

    /**
     * Standard Tree 20
     *
     * @constructor Standard Tree 20
     */
    STANDARD_TREE_20(14309, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),

    /**
     * Standard Tree 21
     *
     * @constructor Standard Tree 21
     */
    STANDARD_TREE_21(16264, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),

    /**
     * Standard Tree 22
     *
     * @constructor Standard Tree 22
     */
    STANDARD_TREE_22(16265, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),

    /**
     * Standard Tree 23
     *
     * @constructor Standard Tree 23
     */
    STANDARD_TREE_23(30132, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),

    /**
     * Standard Tree 24
     *
     * @constructor Standard Tree 24
     */
    STANDARD_TREE_24(30133, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),

    /**
     * Standard Tree 25
     *
     * @constructor Standard Tree 25
     */
    STANDARD_TREE_25(37477, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),

    /**
     * Standard Tree 26
     *
     * @constructor Standard Tree 26
     */
    STANDARD_TREE_26(37478, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 37653, Skills.WOODCUTTING),

    /**
     * Standard Tree 27
     *
     * @constructor Standard Tree 27
     */
    STANDARD_TREE_27(37652, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "tree", null, 37653, Skills.WOODCUTTING),

    /**
     * Apple Tree
     *
     * @constructor Apple Tree
     */
    APPLE_TREE(7941, 1, 0.05, 50 or (100 shl 16), 25.0, -1, 1, "tree", null, 37653, Skills.WOODCUTTING, true),

    /**
     * Banana Tree
     *
     * @constructor Banana Tree
     */
    BANANA_TREE(8000, 1, 0.05, 50 or (100 shl 16), 25.0, -1, 1, "tree", null, 37653, Skills.WOODCUTTING, true),

    /**
     * Orange Tree
     *
     * @constructor Orange Tree
     */
    ORANGE_TREE(8057, 1, 0.05, 50 or (100 shl 16), 25.0, -1, 1, "tree", null, 37653, Skills.WOODCUTTING, true),

    /**
     * Curry Tree
     *
     * @constructor Curry Tree
     */
    CURRY_TREE(8026, 1, 0.05, 50 or (100 shl 16), 25.0, -1, 1, "tree", null, 37653, Skills.WOODCUTTING, true),

    /**
     * Pineapple Tree
     *
     * @constructor Pineapple Tree
     */
    PINEAPPLE_TREE(7972, 1, 0.05, 50 or (100 shl 16), 25.0, -1, 1, "tree", null, 37653, Skills.WOODCUTTING, true),

    /**
     * Papaya Tree
     *
     * @constructor Papaya Tree
     */
    PAPAYA_TREE(8111, 1, 0.05, 50 or (100 shl 16), 25.0, -1, 1, "tree", null, 37653, Skills.WOODCUTTING, true),

    /**
     * Palm Tree
     *
     * @constructor Palm Tree
     */
    PALM_TREE(8084, 1, 0.05, 50 or (100 shl 16), 25.0, -1, 1, "tree", null, 37653, Skills.WOODCUTTING, true),

    /**
     * Dead Tree 1
     *
     * @constructor Dead Tree 1
     */
    DEAD_TREE_1(1282, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1347, Skills.WOODCUTTING),

    /**
     * Dead Tree 2
     *
     * @constructor Dead Tree 2
     */
    DEAD_TREE_2(1283, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1347, Skills.WOODCUTTING),

    /**
     * Dead Tree 3
     *
     * @constructor Dead Tree 3
     */
    DEAD_TREE_3(1284, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1348, Skills.WOODCUTTING),

    /**
     * Dead Tree 4
     *
     * @constructor Dead Tree 4
     */
    DEAD_TREE_4(1285, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1349, Skills.WOODCUTTING),

    /**
     * Dead Tree 5
     *
     * @constructor Dead Tree 5
     */
    DEAD_TREE_5(1286, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1351, Skills.WOODCUTTING),

    /**
     * Dead Tree 6
     *
     * @constructor Dead Tree 6
     */
    DEAD_TREE_6(1289, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1353, Skills.WOODCUTTING),

    /**
     * Dead Tree 7
     *
     * @constructor Dead Tree 7
     */
    DEAD_TREE_7(1290, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1354, Skills.WOODCUTTING),

    /**
     * Dead Tree 8
     *
     * @constructor Dead Tree 8
     */
    DEAD_TREE_8(1291, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 23054, Skills.WOODCUTTING),

    /**
     * Dead Tree 9
     *
     * @constructor Dead Tree 9
     */
    DEAD_TREE_9(1365, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1352, Skills.WOODCUTTING),

    /**
     * Dead Tree 10
     *
     * @constructor Dead Tree 10
     */
    DEAD_TREE_10(1383, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1358, Skills.WOODCUTTING),

    /**
     * Dead Tree 11
     *
     * @constructor Dead Tree 11
     */
    DEAD_TREE_11(1384, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1359, Skills.WOODCUTTING),

    /**
     * Dead Tree 12
     *
     * @constructor Dead Tree 12
     */
    DEAD_TREE_12(5902, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1347, Skills.WOODCUTTING),

    /**
     * Dead Tree 13
     *
     * @constructor Dead Tree 13
     */
    DEAD_TREE_13(5903, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1353, Skills.WOODCUTTING),

    /**
     * Dead Tree 14
     *
     * @constructor Dead Tree 14
     */
    DEAD_TREE_14(5904, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1353, Skills.WOODCUTTING),

    /**
     * Dead Tree 15
     *
     * @constructor Dead Tree 15
     */
    DEAD_TREE_15(32294, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1353, Skills.WOODCUTTING),

    /**
     * Dead Tree 16
     *
     * @constructor Dead Tree 16
     */
    DEAD_TREE_16(37481, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1347, Skills.WOODCUTTING),

    /**
     * Dead Tree 17
     *
     * @constructor Dead Tree 17
     */
    DEAD_TREE_17(37482, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1351, Skills.WOODCUTTING),

    /**
     * Dead Tree 18
     *
     * @constructor Dead Tree 18
     */
    DEAD_TREE_18(37483, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dead tree", null, 1358, Skills.WOODCUTTING),

    /**
     * Dead Tree 19
     *
     * @constructor Dead Tree 19
     */
    DEAD_TREE_19(24168, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "dying tree", null, 24169, Skills.WOODCUTTING),

    /**
     * Dramen Tree
     *
     * @constructor Dramen Tree
     */
    DRAMEN_TREE(1292, 36, 0.05, -1, 25.0, 771, Int.MAX_VALUE, "dramen tree", null, -1, Skills.WOODCUTTING),

    /**
     * Evergreen 1
     *
     * @constructor Evergreen 1
     */
    EVERGREEN_1(1315, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "evergreen", null, 1342, Skills.WOODCUTTING),

    /**
     * Evergreen 2
     *
     * @constructor Evergreen 2
     */
    EVERGREEN_2(1316, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "evergreen", null, 1355, Skills.WOODCUTTING),

    /**
     * Evergreen 3
     *
     * @constructor Evergreen 3
     */
    EVERGREEN_3(1318, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "evergreen", null, 1355, Skills.WOODCUTTING),

    /**
     * Evergreen 4
     *
     * @constructor Evergreen 4
     */
    EVERGREEN_4(1319, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 1, "evergreen", null, 1355, Skills.WOODCUTTING),

    /**
     * Jungle Tree 1
     *
     * @constructor Jungle Tree 1
     */
    JUNGLE_TREE_1(2887, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 2, "jungle tree", null, 0, Skills.WOODCUTTING),

    /**
     * Jungle Tree 2
     *
     * @constructor Jungle Tree 2
     */
    JUNGLE_TREE_2(2889, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 2, "jungle tree", null, 0, Skills.WOODCUTTING),

    /**
     * Jungle Tree 3
     *
     * @constructor Jungle Tree 3
     */
    JUNGLE_TREE_3(2890, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 2, "jungle tree", null, 0, Skills.WOODCUTTING),

    /**
     * Jungle Tree 4
     *
     * @constructor Jungle Tree 4
     */
    JUNGLE_TREE_4(4818, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 2, "jungle tree", null, 0, Skills.WOODCUTTING),

    /**
     * Jungle Tree 5
     *
     * @constructor Jungle Tree 5
     */
    JUNGLE_TREE_5(4820, 1, 0.05, 50 or (100 shl 16), 25.0, 1511, 2, "jungle tree", null, 0, Skills.WOODCUTTING),

    /**
     * Jungle Bush 1
     *
     * @constructor Jungle Bush 1
     */
    JUNGLE_BUSH_1(2892, 1, 0.15, 50 or (100 shl 16), 100.0, 1511, 1, "jungle bush", null, 2894, Skills.WOODCUTTING),

    /**
     * Jungle Bush 2
     *
     * @constructor Jungle Bush 2
     */
    JUNGLE_BUSH_2(2893, 1, 0.15, 50 or (100 shl 16), 100.0, 1511, 1, "jungle bush", null, 2895, Skills.WOODCUTTING),

    /**
     * Achey Tree
     *
     * @constructor Achey Tree
     */
    ACHEY_TREE(2023, 1, 0.05, 50 or (100 shl 16), 25.0, 2862, 1, "achey tree", null, 3371, Skills.WOODCUTTING),

    /**
     * Oak Tree 1
     *
     * @constructor Oak Tree 1
     */
    OAK_TREE_1(1281, 15, 0.15, 14 or (22 shl 16), 37.5, 1521, 10, "oak tree", null, 1356, Skills.WOODCUTTING),

    /**
     * Oak Tree 2
     *
     * @constructor Oak Tree 2
     */
    OAK_TREE_2(3037, 15, 0.15, 14 or (22 shl 16), 37.5, 1521, 10, "oak tree", null, 1357, Skills.WOODCUTTING),

    /**
     * Oak Tree 3
     *
     * @constructor Oak Tree 3
     */
    OAK_TREE_3(37479, 15, 0.15, 14 or (22 shl 16), 37.5, 1521, 10, "oak tree", null, 1356, Skills.WOODCUTTING),

    /**
     * Oak Tree 4
     *
     * @constructor Oak Tree 4
     */
    OAK_TREE_4(8467, 15, 0.15, 14 or (22 shl 16), 37.5, 1521, 10, "oak tree", null, 1356, Skills.WOODCUTTING, true),

    /**
     * Willow Tree 1
     *
     * @constructor Willow Tree 1
     */
    WILLOW_TREE_1(1308, 30, 0.3, 14 or (22 shl 16), 67.8, 1519, 20, "willow tree", null, 7399, Skills.WOODCUTTING),

    /**
     * Willow Tree 2
     *
     * @constructor Willow Tree 2
     */
    WILLOW_TREE_2(5551, 30, 0.3, 14 or (22 shl 16), 67.8, 1519, 20, "willow tree", null, 5554, Skills.WOODCUTTING),

    /**
     * Willow Tree 3
     *
     * @constructor Willow Tree 3
     */
    WILLOW_TREE_3(5552, 30, 0.3, 14 or (22 shl 16), 67.8, 1519, 20, "willow tree", null, 5554, Skills.WOODCUTTING),

    /**
     * Willow Tree 4
     *
     * @constructor Willow Tree 4
     */
    WILLOW_TREE_4(5553, 30, 0.3, 14 or (22 shl 16), 67.8, 1519, 20, "willow tree", null, 5554, Skills.WOODCUTTING),

    /**
     * Willow Tree 5
     *
     * @constructor Willow Tree 5
     */
    WILLOW_TREE_5(37480, 30, 0.3, 14 or (22 shl 16), 67.8, 1519, 20, "willow tree", null, 7399, Skills.WOODCUTTING),

    /**
     * Willow Tree 6
     *
     * @constructor Willow Tree 6
     */
    WILLOW_TREE_6(8488, 30, 0.3, 14 or (22 shl 16), 67.8, 1519, 20, "willow tree", null, 7399, Skills.WOODCUTTING, true),

    /**
     * Teak 1
     *
     * @constructor Teak 1
     */
    TEAK_1(9036, 35, 0.7, 35 or (60 shl 16), 85.0, 6333, 25, "teak", null, 9037, Skills.WOODCUTTING),

    /**
     * Teak 2
     *
     * @constructor Teak 2
     */
    TEAK_2(15062, 35, 0.7, 35 or (60 shl 16), 85.0, 6333, 25, "teak", null, 9037, Skills.WOODCUTTING),

    /**
     * Maple Tree 1
     *
     * @constructor Maple Tree 1
     */
    MAPLE_TREE_1(1307, 45, 0.65, 58 or (100 shl 16), 100.0, 1517, 30, "maple tree", null, 7400, Skills.WOODCUTTING),

    /**
     * Maple Tree 2
     *
     * @constructor Maple Tree 2
     */
    MAPLE_TREE_2(4674, 45, 0.65, 58 or (100 shl 16), 100.0, 1517, 30, "maple tree", null, 7400, Skills.WOODCUTTING),

    /**
     * Maple Tree 3
     *
     * @constructor Maple Tree 3
     */
    MAPLE_TREE_3(8444, 45, 0.65, 58 or (100 shl 16), 100.0, 1517, 30, "maple tree", null, 7400, Skills.WOODCUTTING, true),

    /**
     * Hollow Tree 1
     *
     * @constructor Hollow Tree 1
     */
    HOLLOW_TREE_1(2289, 45, 0.6, 58 or (100 shl 16), 82.5, 3239, 30, "hollow tree", null, 2310, Skills.WOODCUTTING),

    /**
     * Hollow Tree 2
     *
     * @constructor Hollow Tree 2
     */
    HOLLOW_TREE_2(4060, 45, 0.6, 58 or (100 shl 16), 82.5, 3239, 30, "hollow tree", null, 4061, Skills.WOODCUTTING),

    /**
     * Mahogany
     *
     * @constructor Mahogany
     */
    MAHOGANY(9034, 50, 0.7, 62 or (115 shl 16), 125.0, 6332, 35, "mahogany", null, 9035, Skills.WOODCUTTING),

    /**
     * Arctic Pine
     *
     * @constructor Arctic Pine
     */
    ARCTIC_PINE(21273, 54, 0.73, 75 or (130 shl 16), 140.2, 10810, 35, "arctic pine", null, 21274, Skills.WOODCUTTING),

    /**
     * Eucalyptus 1
     *
     * @constructor Eucalyptus 1
     */
    EUCALYPTUS_1(28951, 58, 0.77, 80 or (140 shl 16), 165.0, 12581, 35, "eucalyptus tree", null, 28954, Skills.WOODCUTTING),

    /**
     * Eucalyptus 2
     *
     * @constructor Eucalyptus 2
     */
    EUCALYPTUS_2(28952, 58, 0.77, 80 or (140 shl 16), 165.0, 12581, 35, "eucalyptus tree", null, 28955, Skills.WOODCUTTING),

    /**
     * Eucalyptus 3
     *
     * @constructor Eucalyptus 3
     */
    EUCALYPTUS_3(28953, 58, 0.77, 80 or (140 shl 16), 165.0, 12581, 35, "eucalyptus tree", null, 28956, Skills.WOODCUTTING),

    /**
     * Yew
     *
     * @constructor Yew
     */
    YEW(1309, 60, 0.8, 100 or (162 shl 16), 175.0, 1515, 40, "yew", null, 7402, Skills.WOODCUTTING),

    /**
     * Yew 1
     *
     * @constructor Yew 1
     */
    YEW_1(8513, 60, 0.8, 100 or (162 shl 16), 175.0, 1515, 40, "yew", null, 7402, Skills.WOODCUTTING, true),

    /**
     * Magic Tree 1
     *
     * @constructor Magic Tree 1
     */
    MAGIC_TREE_1(1306, 75, 0.9, 200 or (317 shl 16), 250.0, 1513, 50, "magic tree", null, 7401, Skills.WOODCUTTING),

    /**
     * Magic Tree 2
     *
     * @constructor Magic Tree 2
     */
    MAGIC_TREE_2(37823, 75, 0.9, 200 or (317 shl 16), 250.0, 1513, 50, "magic tree", null, 37824, Skills.WOODCUTTING),

    /**
     * Magic Tree 3
     *
     * @constructor Magic Tree 3
     */
    MAGIC_TREE_3(8409, 75, 0.9, 200 or (317 shl 16), 250.0, 1513, 50, "magic tree", null, 37824, Skills.WOODCUTTING, true),

    /**
     * Cursed Magic Tree
     *
     * @constructor Cursed Magic Tree
     */
    CURSED_MAGIC_TREE(37821, 82, 0.95, 200 or (317 shl 16), 275.0, 1513, 50, "magic tree", null, 37822, Skills.WOODCUTTING),

    /**
     * Copper Ore 0
     *
     * @constructor Copper Ore 0
     */
    COPPER_ORE_0(2090, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 450, Skills.MINING),

    /**
     * Copper Ore 1
     *
     * @constructor Copper Ore 1
     */
    COPPER_ORE_1(2091, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 452, Skills.MINING),

    /**
     * Copper Ore 2
     *
     * @constructor Copper Ore 2
     */
    COPPER_ORE_2(4976, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 4994, Skills.MINING),

    /**
     * Copper Ore 3
     *
     * @constructor Copper Ore 3
     */
    COPPER_ORE_3(4977, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 4995, Skills.MINING),

    /**
     * Copper Ore 4
     *
     * @constructor Copper Ore 4
     */
    COPPER_ORE_4(4978, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 4996, Skills.MINING),

    /**
     * Copper Ore 5
     *
     * @constructor Copper Ore 5
     */
    COPPER_ORE_5(9710, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 18954, Skills.MINING),

    /**
     * Copper Ore 6
     *
     * @constructor Copper Ore 6
     */
    COPPER_ORE_6(9709, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 32448, Skills.MINING),

    /**
     * Copper Ore 7
     *
     * @constructor Copper Ore 7
     */
    COPPER_ORE_7(9708, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 32447, Skills.MINING),

    /**
     * Copper Ore 8
     *
     * @constructor Copper Ore 8
     */
    COPPER_ORE_8(11960, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 11555, Skills.MINING),

    /**
     * Copper Ore 9
     *
     * @constructor Copper Ore 9
     */
    COPPER_ORE_9(11961, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 11556, Skills.MINING),

    /**
     * Copper Ore 10
     *
     * @constructor Copper Ore 10
     */
    COPPER_ORE_10(11962, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 11557, Skills.MINING),

    /**
     * Copper Ore 11
     *
     * @constructor Copper Ore 11
     */
    COPPER_ORE_11(11937, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 11553, Skills.MINING),

    /**
     * Copper Ore 12
     *
     * @constructor Copper Ore 12
     */
    COPPER_ORE_12(11936, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 11552, Skills.MINING),

    /**
     * Copper Ore 13
     *
     * @constructor Copper Ore 13
     */
    COPPER_ORE_13(11938, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 11554, Skills.MINING),

    /**
     * Copper Ore 14
     *
     * @constructor Copper Ore 14
     */
    COPPER_ORE_14(12746, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 450, Skills.MINING),

    /**
     * Copper Ore 15
     *
     * @constructor Copper Ore 15
     */
    COPPER_ORE_15(14906, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 14894, Skills.MINING),

    /**
     * Copper Ore 16
     *
     * @constructor Copper Ore 16
     */
    COPPER_ORE_16(14907, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 14895, Skills.MINING),

    /**
     * Copper Ore 17
     *
     * @constructor Copper Ore 17
     */
    COPPER_ORE_17(20448, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 20445, Skills.MINING),

    /**
     * Copper Ore 18
     *
     * @constructor Copper Ore 18
     */
    COPPER_ORE_18(20451, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 20445, Skills.MINING),

    /**
     * Copper Ore 19
     *
     * @constructor Copper Ore 19
     */
    COPPER_ORE_19(20446, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 20443, Skills.MINING),

    /**
     * Copper Ore 20
     *
     * @constructor Copper Ore 20
     */
    COPPER_ORE_20(20447, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 20444, Skills.MINING),

    /**
     * Copper Ore 21
     *
     * @constructor Copper Ore 21
     */
    COPPER_ORE_21(20408, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 20407, Skills.MINING),

    /**
     * Copper Ore 22
     *
     * @constructor Copper Ore 22
     */
    COPPER_ORE_22(18993, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 19005, Skills.MINING),

    /**
     * Copper Ore 23
     *
     * @constructor Copper Ore 23
     */
    COPPER_ORE_23(18992, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 19004, Skills.MINING),

    /**
     * Copper Ore 24
     *
     * @constructor Copper Ore 24
     */
    COPPER_ORE_24(19007, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 19016, Skills.MINING),

    /**
     * Copper Ore 25
     *
     * @constructor Copper Ore 25
     */
    COPPER_ORE_25(19006, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 19021, Skills.MINING),

    /**
     * Copper Ore 26
     *
     * @constructor Copper Ore 26
     */
    COPPER_ORE_26(18991, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 19003, Skills.MINING),

    /**
     * Copper Ore 27
     *
     * @constructor Copper Ore 27
     */
    COPPER_ORE_27(19008, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 19017, Skills.MINING),

    /**
     * Copper Ore 28
     *
     * @constructor Copper Ore 28
     */
    COPPER_ORE_28(21285, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 21297, Skills.MINING),

    /**
     * Copper Ore 29
     *
     * @constructor Copper Ore 29
     */
    COPPER_ORE_29(21284, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 21296, Skills.MINING),

    /**
     * Copper Ore 30
     *
     * @constructor Copper Ore 30
     */
    COPPER_ORE_30(21286, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 21298, Skills.MINING),

    /**
     * Copper Ore 31
     *
     * @constructor Copper Ore 31
     */
    COPPER_ORE_31(29231, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 29219, Skills.MINING),

    /**
     * Copper Ore 32
     *
     * @constructor Copper Ore 32
     */
    COPPER_ORE_32(29230, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 29218, Skills.MINING),

    /**
     * Copper Ore 33
     *
     * @constructor Copper Ore 33
     */
    COPPER_ORE_33(29232, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 29220, Skills.MINING),

    /**
     * Copper Ore 34
     *
     * @constructor Copper Ore 34
     */
    COPPER_ORE_34(31082, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 37650, Skills.MINING),

    /**
     * Copper Ore 35
     *
     * @constructor Copper Ore 35
     */
    COPPER_ORE_35(31081, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 37649, Skills.MINING),

    /**
     * Copper Ore 36
     *
     * @constructor Copper Ore 36
     */
    COPPER_ORE_36(31080, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 37639, Skills.MINING),

    /**
     * Copper Ore 37
     *
     * @constructor Copper Ore 37
     */
    COPPER_ORE_37(37647, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 37650, Skills.MINING),

    /**
     * Copper Ore 38
     *
     * @constructor Copper Ore 38
     */
    COPPER_ORE_38(37646, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 37649, Skills.MINING),

    /**
     * Copper Ore 39
     *
     * @constructor Copper Ore 39
     */
    COPPER_ORE_39(37645, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 37639, Skills.MINING),

    /**
     * Copper Ore 40
     *
     * @constructor Copper Ore 40
     */
    COPPER_ORE_40(37637, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 37639, Skills.MINING),

    /**
     * Copper Ore 41
     *
     * @constructor Copper Ore 41
     */
    COPPER_ORE_41(37688, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 21298, Skills.MINING),

    /**
     * Copper Ore 42
     *
     * @constructor Copper Ore 42
     */
    COPPER_ORE_42(37686, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 21296, Skills.MINING),

    /**
     * Copper Ore 43
     *
     * @constructor Copper Ore 43
     */
    COPPER_ORE_43(37687, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 21297, Skills.MINING),

    /**
     * Copper Ore 44
     *
     * @constructor Copper Ore 44
     */
    COPPER_ORE_44(3042, 1, 0.05, 4 or (8 shl 16), 17.5, 436, 1, "copper rocks", null, 11552, Skills.MINING),

    /**
     * Tin Ore 0
     *
     * @constructor Tin Ore 0
     */
    TIN_ORE_0(2094, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 450, Skills.MINING),

    /**
     * Tin Ore 1
     *
     * @constructor Tin Ore 1
     */
    TIN_ORE_1(2095, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 452, Skills.MINING),

    /**
     * Tin Ore 2
     *
     * @constructor Tin Ore 2
     */
    TIN_ORE_2(3043, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 11552, Skills.MINING),

    /**
     * Tin Ore 3
     *
     * @constructor Tin Ore 3
     */
    TIN_ORE_3(4979, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 4994, Skills.MINING),

    /**
     * Tin Ore 4
     *
     * @constructor Tin Ore 4
     */
    TIN_ORE_4(4980, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 4995, Skills.MINING),

    /**
     * Tin Ore 5
     *
     * @constructor Tin Ore 5
     */
    TIN_ORE_5(4981, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 4996, Skills.MINING),

    /**
     * Tin Ore 6
     *
     * @constructor Tin Ore 6
     */
    TIN_ORE_6(11957, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 11555, Skills.MINING),

    /**
     * Tin Ore 7
     *
     * @constructor Tin Ore 7
     */
    TIN_ORE_7(11958, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 11556, Skills.MINING),

    /**
     * Tin Ore 8
     *
     * @constructor Tin Ore 8
     */
    TIN_ORE_8(11959, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 11557, Skills.MINING),

    /**
     * Tin Ore 9
     *
     * @constructor Tin Ore 9
     */
    TIN_ORE_9(11934, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 11553, Skills.MINING),

    /**
     * Tin Ore 10
     *
     * @constructor Tin Ore 10
     */
    TIN_ORE_10(11935, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 11554, Skills.MINING),

    /**
     * Tin Ore 11
     *
     * @constructor Tin Ore 11
     */
    TIN_ORE_11(11933, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 11552, Skills.MINING),

    /**
     * Tin Ore 12
     *
     * @constructor Tin Ore 12
     */
    TIN_ORE_12(14902, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 14894, Skills.MINING),

    /**
     * Tin Ore 13
     *
     * @constructor Tin Ore 13
     */
    TIN_ORE_13(14903, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 14895, Skills.MINING),

    /**
     * Tin Ore 14
     *
     * @constructor Tin Ore 14
     */
    TIN_ORE_14(18995, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 19004, Skills.MINING),

    /**
     * Tin Ore 15
     *
     * @constructor Tin Ore 15
     */
    TIN_ORE_15(18994, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 19003, Skills.MINING),

    /**
     * Tin Ore 16
     *
     * @constructor Tin Ore 16
     */
    TIN_ORE_16(18996, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 19005, Skills.MINING),

    /**
     * Tin Ore 17
     *
     * @constructor Tin Ore 17
     */
    TIN_ORE_17(19025, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 19016, Skills.MINING),

    /**
     * Tin Ore 18
     *
     * @constructor Tin Ore 18
     */
    TIN_ORE_18(19024, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 19021, Skills.MINING),

    /**
     * Tin Ore 19
     *
     * @constructor Tin Ore 19
     */
    TIN_ORE_19(19026, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 19017, Skills.MINING),

    /**
     * Tin Ore 20
     *
     * @constructor Tin Ore 20
     */
    TIN_ORE_20(21293, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 21296, Skills.MINING),

    /**
     * Tin Ore 21
     *
     * @constructor Tin Ore 21
     */
    TIN_ORE_21(21295, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 21298, Skills.MINING),

    /**
     * Tin Ore 22
     *
     * @constructor Tin Ore 22
     */
    TIN_ORE_22(21294, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 21297, Skills.MINING),

    /**
     * Tin Ore 23
     *
     * @constructor Tin Ore 23
     */
    TIN_ORE_23(29227, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 29218, Skills.MINING),

    /**
     * Tin Ore 24
     *
     * @constructor Tin Ore 24
     */
    TIN_ORE_24(29229, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 29220, Skills.MINING),

    /**
     * Tin Ore 25
     *
     * @constructor Tin Ore 25
     */
    TIN_ORE_25(29228, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 29219, Skills.MINING),

    /**
     * Tin Ore 26
     *
     * @constructor Tin Ore 26
     */
    TIN_ORE_26(31079, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 37650, Skills.MINING),

    /**
     * Tin Ore 27
     *
     * @constructor Tin Ore 27
     */
    TIN_ORE_27(31078, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 37649, Skills.MINING),

    /**
     * Tin Ore 28
     *
     * @constructor Tin Ore 28
     */
    TIN_ORE_28(31077, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 37639, Skills.MINING),

    /**
     * Tin Ore 29
     *
     * @constructor Tin Ore 29
     */
    TIN_ORE_29(37644, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 37650, Skills.MINING),

    /**
     * Tin Ore 30
     *
     * @constructor Tin Ore 30
     */
    TIN_ORE_30(37643, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 37649, Skills.MINING),

    /**
     * Tin Ore 31
     *
     * @constructor Tin Ore 31
     */
    TIN_ORE_31(37642, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 37639, Skills.MINING),

    /**
     * Tin Ore 32
     *
     * @constructor Tin Ore 32
     */
    TIN_ORE_32(37638, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 37639, Skills.MINING),

    /**
     * Tin Ore 33
     *
     * @constructor Tin Ore 33
     */
    TIN_ORE_33(37685, 1, 0.05, 4 or (8 shl 16), 17.5, 438, 1, "tin rocks", null, 21298, Skills.MINING),

    /**
     * Rune Essence 0
     *
     * @constructor Rune Essence 0
     */
    RUNE_ESSENCE_0(2491, 1, 0.1, 1 or (1 shl 16), 5.0, 1436, Int.MAX_VALUE, "rune essence", null, -1, Skills.MINING),

    /**
     * Rune Essence 1
     *
     * @constructor Rune Essence 1
     */
    RUNE_ESSENCE_1(16684, 1, 0.1, 1 or (1 shl 16), 5.0, 1436, Int.MAX_VALUE, "rune essence", null, -1, Skills.MINING),

    /**
     * Clay 0
     *
     * @constructor Clay 0
     */
    CLAY_0(2109, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 452, Skills.MINING),

    /**
     * Clay 1
     *
     * @constructor Clay 1
     */
    CLAY_1(2108, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 450, Skills.MINING),

    /**
     * Clay 2
     *
     * @constructor Clay 2
     */
    CLAY_2(9712, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 32448, Skills.MINING),

    /**
     * Clay 3
     *
     * @constructor Clay 3
     */
    CLAY_3(9713, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 18954, Skills.MINING),

    /**
     * Clay 4
     *
     * @constructor Clay 4
     */
    CLAY_4(9711, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 32447, Skills.MINING),

    /**
     * Clay 5
     *
     * @constructor Clay 5
     */
    CLAY_5(10949, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 10945, Skills.MINING),

    /**
     * Clay 6
     *
     * @constructor Clay 6
     */
    CLAY_6(11190, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 21297, Skills.MINING),

    /**
     * Clay 7
     *
     * @constructor Clay 7
     */
    CLAY_7(11191, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 21298, Skills.MINING),

    /**
     * Clay 8
     *
     * @constructor Clay 8
     */
    CLAY_8(11189, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 21296, Skills.MINING),

    /**
     * Clay 9
     *
     * @constructor Clay 9
     */
    CLAY_9(12942, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 4995, Skills.MINING),

    /**
     * Clay 10
     *
     * @constructor Clay 10
     */
    CLAY_10(12943, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 4996, Skills.MINING),

    /**
     * Clay 11
     *
     * @constructor Clay 11
     */
    CLAY_11(12941, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 4994, Skills.MINING),

    /**
     * Clay 12
     *
     * @constructor Clay 12
     */
    CLAY_12(14904, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 14894, Skills.MINING),

    /**
     * Clay 13
     *
     * @constructor Clay 13
     */
    CLAY_13(14905, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 14895, Skills.MINING),

    /**
     * Clay 14
     *
     * @constructor Clay 14
     */
    CLAY_14(15505, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 11557, Skills.MINING),

    /**
     * Clay 15
     *
     * @constructor Clay 15
     */
    CLAY_15(15504, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 11556, Skills.MINING),

    /**
     * Clay 16
     *
     * @constructor Clay 16
     */
    CLAY_16(15503, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 11555, Skills.MINING),

    /**
     * Clay 17
     *
     * @constructor Clay 17
     */
    CLAY_17(20449, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 20443, Skills.MINING),

    /**
     * Clay 18
     *
     * @constructor Clay 18
     */
    CLAY_18(20450, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 20444, Skills.MINING),

    /**
     * Clay 19
     *
     * @constructor Clay 19
     */
    CLAY_19(20409, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 20407, Skills.MINING),

    /**
     * Clay 20
     *
     * @constructor Clay 20
     */
    CLAY_20(32429, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 33400, Skills.MINING),

    /**
     * Clay 21
     *
     * @constructor Clay 21
     */
    CLAY_21(32430, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 33401, Skills.MINING),

    /**
     * Clay 22
     *
     * @constructor Clay 22
     */
    CLAY_22(32431, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 33402, Skills.MINING),

    /**
     * Clay 23
     *
     * @constructor Clay 23
     */
    CLAY_23(31062, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 37639, Skills.MINING),

    /**
     * Clay 24
     *
     * @constructor Clay 24
     */
    CLAY_24(31063, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 37649, Skills.MINING),

    /**
     * Clay 25
     *
     * @constructor Clay 25
     */
    CLAY_25(31064, 1, 0.1, 1 or (1 shl 16), 5.0, 434, 1, "clay", null, 37650, Skills.MINING),

    /**
     * Limestone 0
     *
     * @constructor Limestone 0
     */
    LIMESTONE_0(4027, 10, 0.2, 10 or (20 shl 16), 26.5, 3211, 1, "limestone", null, 4028, Skills.MINING),

    /**
     * Limestone 1
     *
     * @constructor Limestone 1
     */
    LIMESTONE_1(4028, 10, 0.2, 10 or (20 shl 16), 26.5, 3211, 1, "limestone", null, 4029, Skills.MINING),

    /**
     * Limestone 2
     *
     * @constructor Limestone 2
     */
    LIMESTONE_2(4029, 10, 0.2, 10 or (20 shl 16), 26.5, 3211, 1, "limestone", null, 4030, Skills.MINING),

    /**
     * Limestone 3
     *
     * @constructor Limestone 3
     */
    LIMESTONE_3(4030, 10, 0.2, 10 or (20 shl 16), 26.5, 3211, 1, "limestone", null, 4027, Skills.MINING),

    /**
     * Blurite Ore 0
     *
     * @constructor Blurite Ore 0
     */
    BLURITE_ORE_0(33220, 10, 0.2, 10 or (20 shl 16), 17.5, 668, 1, "blurite rocks", null, 33222, Skills.MINING),

    /**
     * Blurite Ore 1
     *
     * @constructor Blurite Ore 1
     */
    BLURITE_ORE_1(33221, 10, 0.2, 10 or (20 shl 16), 17.5, 668, 1, "blurite rocks", null, 33223, Skills.MINING),

    /**
     * Iron Ore 0
     *
     * @constructor Iron Ore 0
     */
    IRON_ORE_0(2092, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 450, Skills.MINING),

    /**
     * Iron Ore 1
     *
     * @constructor Iron Ore 1
     */
    IRON_ORE_1(2093, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 452, Skills.MINING),

    /**
     * Iron Ore 2
     *
     * @constructor Iron Ore 2
     */
    IRON_ORE_2(4982, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 4994, Skills.MINING),

    /**
     * Iron Ore 3
     *
     * @constructor Iron Ore 3
     */
    IRON_ORE_3(4983, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 4995, Skills.MINING),

    /**
     * Iron Ore 4
     *
     * @constructor Iron Ore 4
     */
    IRON_ORE_4(4984, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 4996, Skills.MINING),

    /**
     * Iron Ore 5
     *
     * @constructor Iron Ore 5
     */
    IRON_ORE_5(6943, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 21296, Skills.MINING),

    /**
     * Iron Ore 6
     *
     * @constructor Iron Ore 6
     */
    IRON_ORE_6(6944, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 21297, Skills.MINING),

    /**
     * Iron Ore 7
     *
     * @constructor Iron Ore 7
     */
    IRON_ORE_7(9718, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 32448, Skills.MINING),

    /**
     * Iron Ore 8
     *
     * @constructor Iron Ore 8
     */
    IRON_ORE_8(9719, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 18954, Skills.MINING),

    /**
     * Iron Ore 9
     *
     * @constructor Iron Ore 9
     */
    IRON_ORE_9(9717, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 32447, Skills.MINING),

    /**
     * Iron Ore 10
     *
     * @constructor Iron Ore 10
     */
    IRON_ORE_10(11956, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 11557, Skills.MINING),

    /**
     * Iron Ore 11
     *
     * @constructor Iron Ore 11
     */
    IRON_ORE_11(11954, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 11555, Skills.MINING),

    /**
     * Iron Ore 12
     *
     * @constructor Iron Ore 12
     */
    IRON_ORE_12(11955, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 11556, Skills.MINING),

    /**
     * Iron Ore 13
     *
     * @constructor Iron Ore 13
     */
    IRON_ORE_13(14914, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 14895, Skills.MINING),

    /**
     * Iron Ore 14
     *
     * @constructor Iron Ore 14
     */
    IRON_ORE_14(14913, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 14894, Skills.MINING),

    /**
     * Iron Ore 15
     *
     * @constructor Iron Ore 15
     */
    IRON_ORE_15(14858, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 25373, Skills.MINING),

    /**
     * Iron Ore 16
     *
     * @constructor Iron Ore 16
     */
    IRON_ORE_16(14857, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 25372, Skills.MINING),

    /**
     * Iron Ore 17
     *
     * @constructor Iron Ore 17
     */
    IRON_ORE_17(14856, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 25371, Skills.MINING),

    /**
     * Iron Ore 18
     *
     * @constructor Iron Ore 18
     */
    IRON_ORE_18(14900, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 14894, Skills.MINING),

    /**
     * Iron Ore 19
     *
     * @constructor Iron Ore 19
     */
    IRON_ORE_19(14901, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 14895, Skills.MINING),

    /**
     * Iron Ore 20
     *
     * @constructor Iron Ore 20
     */
    IRON_ORE_20(20423, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 20444, Skills.MINING),

    /**
     * Iron Ore 21
     *
     * @constructor Iron Ore 21
     */
    IRON_ORE_21(20422, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 20443, Skills.MINING),

    /**
     * Iron Ore 22
     *
     * @constructor Iron Ore 22
     */
    IRON_ORE_22(20425, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 20407, Skills.MINING),

    /**
     * Iron Ore 23
     *
     * @constructor Iron Ore 23
     */
    IRON_ORE_23(20424, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 20445, Skills.MINING),

    /**
     * Iron Ore 24
     *
     * @constructor Iron Ore 24
     */
    IRON_ORE_24(19002, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 19005, Skills.MINING),

    /**
     * Iron Ore 25
     *
     * @constructor Iron Ore 25
     */
    IRON_ORE_25(19001, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 19004, Skills.MINING),

    /**
     * Iron Ore 26
     *
     * @constructor Iron Ore 26
     */
    IRON_ORE_26(19000, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 19003, Skills.MINING),

    /**
     * Iron Ore 27
     *
     * @constructor Iron Ore 27
     */
    IRON_ORE_27(21281, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 21296, Skills.MINING),

    /**
     * Iron Ore 28
     *
     * @constructor Iron Ore 28
     */
    IRON_ORE_28(21283, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 21298, Skills.MINING),

    /**
     * Iron Ore 29
     *
     * @constructor Iron Ore 29
     */
    IRON_ORE_29(21282, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 21297, Skills.MINING),

    /**
     * Iron Ore 30
     *
     * @constructor Iron Ore 30
     */
    IRON_ORE_30(29221, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 29218, Skills.MINING),

    /**
     * Iron Ore 31
     *
     * @constructor Iron Ore 31
     */
    IRON_ORE_31(29223, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 29220, Skills.MINING),

    /**
     * Iron Ore 32
     *
     * @constructor Iron Ore 32
     */
    IRON_ORE_32(29222, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 29219, Skills.MINING),

    /**
     * Iron Ore 33
     *
     * @constructor Iron Ore 33
     */
    IRON_ORE_33(32441, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 33400, Skills.MINING),

    /**
     * Iron Ore 34
     *
     * @constructor Iron Ore 34
     */
    IRON_ORE_34(32443, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 33402, Skills.MINING),

    /**
     * Iron Ore 35
     *
     * @constructor Iron Ore 35
     */
    IRON_ORE_35(32442, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 33401, Skills.MINING),

    /**
     * Iron Ore 36
     *
     * @constructor Iron Ore 36
     */
    IRON_ORE_36(32452, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 32448, Skills.MINING),

    /**
     * Iron Ore 37
     *
     * @constructor Iron Ore 37
     */
    IRON_ORE_37(32451, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 32447, Skills.MINING),

    /**
     * Iron Ore 38
     *
     * @constructor Iron Ore 38
     */
    IRON_ORE_38(31073, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 37650, Skills.MINING),

    /**
     * Iron Ore 39
     *
     * @constructor Iron Ore 39
     */
    IRON_ORE_39(31072, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 37649, Skills.MINING),

    /**
     * Iron Ore 40
     *
     * @constructor Iron Ore 40
     */
    IRON_ORE_40(31071, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 37639, Skills.MINING),

    /**
     * Iron Ore 41
     *
     * @constructor Iron Ore 41
     */
    IRON_ORE_41(37307, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 11552, Skills.MINING),

    /**
     * Iron Ore 42
     *
     * @constructor Iron Ore 42
     */
    IRON_ORE_42(37309, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 11554, Skills.MINING),

    /**
     * Iron Ore 43
     *
     * @constructor Iron Ore 43
     */
    IRON_ORE_43(37308, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 11553, Skills.MINING),

    /**
     * Iron Ore 49
     *
     * @constructor Iron Ore 49
     */
    IRON_ORE_49(42034, 15, 0.2, 15 or (25 shl 16), 35.0, 440, 1, "iron rocks", null, 450, Skills.MINING),

    /**
     * Silver Ore 0
     *
     * @constructor Silver Ore 0
     */
    SILVER_ORE_0(2101, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 452, Skills.MINING),

    /**
     * Silver Ore 1
     *
     * @constructor Silver Ore 1
     */
    SILVER_ORE_1(2100, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 450, Skills.MINING),

    /**
     * Silver Ore 2
     *
     * @constructor Silver Ore 2
     */
    SILVER_ORE_2(6945, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 21296, Skills.MINING),

    /**
     * Silver Ore 3
     *
     * @constructor Silver Ore 3
     */
    SILVER_ORE_3(6946, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 21297, Skills.MINING),

    /**
     * Silver Ore 4
     *
     * @constructor Silver Ore 4
     */
    SILVER_ORE_4(9716, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 18954, Skills.MINING),

    /**
     * Silver Ore 5
     *
     * @constructor Silver Ore 5
     */
    SILVER_ORE_5(9714, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 32447, Skills.MINING),

    /**
     * Silver Ore 6
     *
     * @constructor Silver Ore 6
     */
    SILVER_ORE_6(9715, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 32448, Skills.MINING),

    /**
     * Silver Ore 7
     *
     * @constructor Silver Ore 7
     */
    SILVER_ORE_7(11188, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 21298, Skills.MINING),

    /**
     * Silver Ore 8
     *
     * @constructor Silver Ore 8
     */
    SILVER_ORE_8(11186, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 21296, Skills.MINING),

    /**
     * Silver Ore 9
     *
     * @constructor Silver Ore 9
     */
    SILVER_ORE_9(11187, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 21297, Skills.MINING),

    /**
     * Silver Ore 10
     *
     * @constructor Silver Ore 10
     */
    SILVER_ORE_10(15581, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 14834, Skills.MINING),

    /**
     * Silver Ore 11
     *
     * @constructor Silver Ore 11
     */
    SILVER_ORE_11(15580, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 14833, Skills.MINING),

    /**
     * Silver Ore 12
     *
     * @constructor Silver Ore 12
     */
    SILVER_ORE_12(15579, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 14832, Skills.MINING),

    /**
     * Silver Ore 13
     *
     * @constructor Silver Ore 13
     */
    SILVER_ORE_13(16998, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 14915, Skills.MINING),

    /**
     * Silver Ore 14
     *
     * @constructor Silver Ore 14
     */
    SILVER_ORE_14(16999, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 14916, Skills.MINING),

    /**
     * Silver Ore 15
     *
     * @constructor Silver Ore 15
     */
    SILVER_ORE_15(17007, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 14915, Skills.MINING),

    /**
     * Silver Ore 16
     *
     * @constructor Silver Ore 16
     */
    SILVER_ORE_16(17000, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 31061, Skills.MINING),

    /**
     * Silver Ore 17
     *
     * @constructor Silver Ore 17
     */
    SILVER_ORE_17(17009, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 31061, Skills.MINING),

    /**
     * Silver Ore 18
     *
     * @constructor Silver Ore 18
     */
    SILVER_ORE_18(17008, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 14916, Skills.MINING),

    /**
     * Silver Ore 19
     *
     * @constructor Silver Ore 19
     */
    SILVER_ORE_19(17385, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 32447, Skills.MINING),

    /**
     * Silver Ore 20
     *
     * @constructor Silver Ore 20
     */
    SILVER_ORE_20(17387, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 18954, Skills.MINING),

    /**
     * Silver Ore 21
     *
     * @constructor Silver Ore 21
     */
    SILVER_ORE_21(17386, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 32448, Skills.MINING),

    /**
     * Silver Ore 22
     *
     * @constructor Silver Ore 22
     */
    SILVER_ORE_22(29225, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 29219, Skills.MINING),

    /**
     * Silver Ore 23
     *
     * @constructor Silver Ore 23
     */
    SILVER_ORE_23(29224, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 29218, Skills.MINING),

    /**
     * Silver Ore 24
     *
     * @constructor Silver Ore 24
     */
    SILVER_ORE_24(29226, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 29220, Skills.MINING),

    /**
     * Silver Ore 25
     *
     * @constructor Silver Ore 25
     */
    SILVER_ORE_25(32445, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 33401, Skills.MINING),

    /**
     * Silver Ore 26
     *
     * @constructor Silver Ore 26
     */
    SILVER_ORE_26(32444, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 33400, Skills.MINING),

    /**
     * Silver Ore 27
     *
     * @constructor Silver Ore 27
     */
    SILVER_ORE_27(32446, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 33402, Skills.MINING),

    /**
     * Silver Ore 28
     *
     * @constructor Silver Ore 28
     */
    SILVER_ORE_28(31075, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 37649, Skills.MINING),

    /**
     * Silver Ore 29
     *
     * @constructor Silver Ore 29
     */
    SILVER_ORE_29(31074, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 37639, Skills.MINING),

    /**
     * Silver Ore 30
     *
     * @constructor Silver Ore 30
     */
    SILVER_ORE_30(31076, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 37650, Skills.MINING),

    /**
     * Silver Ore 31
     *
     * @constructor Silver Ore 31
     */
    SILVER_ORE_31(37305, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 11553, Skills.MINING),

    /**
     * Silver Ore 32
     *
     * @constructor Silver Ore 32
     */
    SILVER_ORE_32(37304, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 11552, Skills.MINING),

    /**
     * Silver Ore 33
     *
     * @constructor Silver Ore 33
     */
    SILVER_ORE_33(37306, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 11554, Skills.MINING),

    /**
     * Silver Ore 34
     *
     * @constructor Silver Ore 34
     */
    SILVER_ORE_34(37670, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 11552, Skills.MINING),

    /**
     * Silver Ore 35
     *
     * @constructor Silver Ore 35
     */
    SILVER_ORE_35(11948, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 11555, Skills.MINING),

    /**
     * Silver Ore 36
     *
     * @constructor Silver Ore 36
     */
    SILVER_ORE_36(11949, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 11556, Skills.MINING),

    /**
     * Silver Ore 37
     *
     * @constructor Silver Ore 37
     */
    SILVER_ORE_37(11950, 20, 0.3, 100 or (200 shl 16), 40.0, 442, 1, "silver rocks", null, 11557, Skills.MINING),

    /**
     * Coal 0
     *
     * @constructor Coal 0
     */
    COAL_0(2097, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 452, Skills.MINING),

    /**
     * Coal 1
     *
     * @constructor Coal 1
     */
    COAL_1(2096, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 450, Skills.MINING),

    /**
     * Coal 2
     *
     * @constructor Coal 2
     */
    COAL_2(4985, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 4994, Skills.MINING),

    /**
     * Coal 3
     *
     * @constructor Coal 3
     */
    COAL_3(4986, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 4995, Skills.MINING),

    /**
     * Coal 4
     *
     * @constructor Coal 4
     */
    COAL_4(4987, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 4996, Skills.MINING),

    /**
     * Coal 5
     *
     * @constructor Coal 5
     */
    COAL_5(4676, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 450, Skills.MINING),

    /**
     * Coal 6
     *
     * @constructor Coal 6
     */
    COAL_6(10948, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 10944, Skills.MINING),

    /**
     * Coal 7
     *
     * @constructor Coal 7
     */
    COAL_7(11964, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 11556, Skills.MINING),

    /**
     * Coal 8
     *
     * @constructor Coal 8
     */
    COAL_8(11965, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 11557, Skills.MINING),

    /**
     * Coal 9
     *
     * @constructor Coal 9
     */
    COAL_9(11963, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 11555, Skills.MINING),

    /**
     * Coal 10
     *
     * @constructor Coal 10
     */
    COAL_10(11932, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 11554, Skills.MINING),

    /**
     * Coal 11
     *
     * @constructor Coal 11
     */
    COAL_11(11930, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 11552, Skills.MINING),

    /**
     * Coal 12
     *
     * @constructor Coal 12
     */
    COAL_12(11931, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 11553, Skills.MINING),

    /**
     * Coal 13
     *
     * @constructor Coal 13
     */
    COAL_13(15246, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 15249, Skills.MINING),

    /**
     * Coal 14
     *
     * @constructor Coal 14
     */
    COAL_14(15247, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 15250, Skills.MINING),

    /**
     * Coal 15
     *
     * @constructor Coal 15
     */
    COAL_15(15248, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 15251, Skills.MINING),

    /**
     * Coal 16
     *
     * @constructor Coal 16
     */
    COAL_16(14852, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 25373, Skills.MINING),

    /**
     * Coal 17
     *
     * @constructor Coal 17
     */
    COAL_17(14851, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 25372, Skills.MINING),

    /**
     * Coal 18
     *
     * @constructor Coal 18
     */
    COAL_18(14850, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 25371, Skills.MINING),

    /**
     * Coal 19
     *
     * @constructor Coal 19
     */
    COAL_19(20410, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 20443, Skills.MINING),

    /**
     * Coal 20
     *
     * @constructor Coal 20
     */
    COAL_20(20411, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 20444, Skills.MINING),

    /**
     * Coal 21
     *
     * @constructor Coal 21
     */
    COAL_21(20412, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 20445, Skills.MINING),

    /**
     * Coal 22
     *
     * @constructor Coal 22
     */
    COAL_22(20413, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 20407, Skills.MINING),

    /**
     * Coal 23
     *
     * @constructor Coal 23
     */
    COAL_23(18999, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 19005, Skills.MINING),

    /**
     * Coal 24
     *
     * @constructor Coal 24
     */
    COAL_24(18998, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 19004, Skills.MINING),

    /**
     * Coal 25
     *
     * @constructor Coal 25
     */
    COAL_25(18997, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 19003, Skills.MINING),

    /**
     * Coal 26
     *
     * @constructor Coal 26
     */
    COAL_26(21287, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 21296, Skills.MINING),

    /**
     * Coal 27
     *
     * @constructor Coal 27
     */
    COAL_27(21289, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 21298, Skills.MINING),

    /**
     * Coal 28
     *
     * @constructor Coal 28
     */
    COAL_28(21288, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 21297, Skills.MINING),

    /**
     * Coal 29
     *
     * @constructor Coal 29
     */
    COAL_29(23565, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 21298, Skills.MINING),

    /**
     * Coal 30
     *
     * @constructor Coal 30
     */
    COAL_30(23564, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 21297, Skills.MINING),

    /**
     * Coal 31
     *
     * @constructor Coal 31
     */
    COAL_31(23563, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 21296, Skills.MINING),

    /**
     * Coal 32
     *
     * @constructor Coal 32
     */
    COAL_32(29215, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 29218, Skills.MINING),

    /**
     * Coal 33
     *
     * @constructor Coal 33
     */
    COAL_33(29217, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 29220, Skills.MINING),

    /**
     * Coal 34
     *
     * @constructor Coal 34
     */
    COAL_34(29216, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 29219, Skills.MINING),

    /**
     * Coal 35
     *
     * @constructor Coal 35
     */
    COAL_35(32426, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 33400, Skills.MINING),

    /**
     * Coal 36
     *
     * @constructor Coal 36
     */
    COAL_36(32427, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 33401, Skills.MINING),

    /**
     * Coal 37
     *
     * @constructor Coal 37
     */
    COAL_37(32428, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 33402, Skills.MINING),

    /**
     * Coal 38
     *
     * @constructor Coal 38
     */
    COAL_38(32450, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 32448, Skills.MINING),

    /**
     * Coal 39
     *
     * @constructor Coal 39
     */
    COAL_39(32449, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 32447, Skills.MINING),

    /**
     * Coal 40
     *
     * @constructor Coal 40
     */
    COAL_40(31068, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 37639, Skills.MINING),

    /**
     * Coal 41
     *
     * @constructor Coal 41
     */
    COAL_41(31069, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 37649, Skills.MINING),

    /**
     * Coal 42
     *
     * @constructor Coal 42
     */
    COAL_42(31070, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 37650, Skills.MINING),

    /**
     * Coal 43
     *
     * @constructor Coal 43
     */
    COAL_43(31168, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 14833, Skills.MINING),

    /**
     * Coal 44
     *
     * @constructor Coal 44
     */
    COAL_44(31169, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 14834, Skills.MINING),

    /**
     * Coal 45
     *
     * @constructor Coal 45
     */
    COAL_45(31167, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 14832, Skills.MINING),

    /**
     * Coal 46
     *
     * @constructor Coal 46
     */
    COAL_46(37699, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 21298, Skills.MINING),

    /**
     * Coal 47
     *
     * @constructor Coal 47
     */
    COAL_47(37698, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 21297, Skills.MINING),

    /**
     * Coal 48
     *
     * @constructor Coal 48
     */
    COAL_48(37697, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 21296, Skills.MINING),

    /**
     * Coal 49
     *
     * @constructor Coal 49
     */
    COAL_49(42035, 30, 0.4, 50 or (100 shl 16), 50.0, 453, 1, "coal", null, 452, Skills.MINING),

    /**
     * Sandstone
     *
     * @constructor Sandstone
     */
    SANDSTONE(10946, 35, 0.2, 30 or (60 shl 16), 30.0, 6971, 1, "sandstone", null, 10944, Skills.MINING),

    /**
     * Gold Ore 0
     *
     * @constructor Gold Ore 0
     */
    GOLD_ORE_0(2099, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 452, Skills.MINING),

    /**
     * Gold Ore 1
     *
     * @constructor Gold Ore 1
     */
    GOLD_ORE_1(2098, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 450, Skills.MINING),

    /**
     * Gold Ore 2
     *
     * @constructor Gold Ore 2
     */
    GOLD_ORE_2(2611, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 21298, Skills.MINING),

    /**
     * Gold Ore 3
     *
     * @constructor Gold Ore 3
     */
    GOLD_ORE_3(2610, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 21297, Skills.MINING),

    /**
     * Gold Ore 4
     *
     * @constructor Gold Ore 4
     */
    GOLD_ORE_4(2609, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 21296, Skills.MINING),

    /**
     * Gold Ore 5
     *
     * @constructor Gold Ore 5
     */
    GOLD_ORE_5(9722, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 18954, Skills.MINING),

    /**
     * Gold Ore 6
     *
     * @constructor Gold Ore 6
     */
    GOLD_ORE_6(9720, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 32447, Skills.MINING),

    /**
     * Gold Ore 7
     *
     * @constructor Gold Ore 7
     */
    GOLD_ORE_7(9721, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 32448, Skills.MINING),

    /**
     * Gold Ore 8
     *
     * @constructor Gold Ore 8
     */
    GOLD_ORE_8(11183, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 21296, Skills.MINING),

    /**
     * Gold Ore 9
     *
     * @constructor Gold Ore 9
     */
    GOLD_ORE_9(11184, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 21297, Skills.MINING),

    /**
     * Gold Ore 10
     *
     * @constructor Gold Ore 10
     */
    GOLD_ORE_10(11185, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 21298, Skills.MINING),

    /**
     * Gold Ore 11
     *
     * @constructor Gold Ore 11
     */
    GOLD_ORE_11(11952, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 11556, Skills.MINING),

    /**
     * Gold Ore 12
     *
     * @constructor Gold Ore 12
     */
    GOLD_ORE_12(11953, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 11557, Skills.MINING),

    /**
     * Gold Ore 13
     *
     * @constructor Gold Ore 13
     */
    GOLD_ORE_13(11951, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 11555, Skills.MINING),

    /**
     * Gold Ore 14
     *
     * @constructor Gold Ore 14
     */
    GOLD_ORE_14(15578, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 14834, Skills.MINING),

    /**
     * Gold Ore 15
     *
     * @constructor Gold Ore 15
     */
    GOLD_ORE_15(15577, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 14833, Skills.MINING),

    /**
     * Gold Ore 16
     *
     * @constructor Gold Ore 16
     */
    GOLD_ORE_16(15576, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 14832, Skills.MINING),

    /**
     * Gold Ore 17
     *
     * @constructor Gold Ore 17
     */
    GOLD_ORE_17(17002, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 14916, Skills.MINING),

    /**
     * Gold Ore 18
     *
     * @constructor Gold Ore 18
     */
    GOLD_ORE_18(17003, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 31061, Skills.MINING),

    /**
     * Gold Ore 19
     *
     * @constructor Gold Ore 19
     */
    GOLD_ORE_19(17001, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 14915, Skills.MINING),

    /**
     * Gold Ore 20
     *
     * @constructor Gold Ore 20
     */
    GOLD_ORE_20(21291, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 21297, Skills.MINING),

    /**
     * Gold Ore 21
     *
     * @constructor Gold Ore 21
     */
    GOLD_ORE_21(21290, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 21296, Skills.MINING),

    /**
     * Gold Ore 22
     *
     * @constructor Gold Ore 22
     */
    GOLD_ORE_22(21292, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 21298, Skills.MINING),

    /**
     * Gold Ore 23
     *
     * @constructor Gold Ore 23
     */
    GOLD_ORE_23(32433, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 33401, Skills.MINING),

    /**
     * Gold Ore 24
     *
     * @constructor Gold Ore 24
     */
    GOLD_ORE_24(32432, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 33400, Skills.MINING),

    /**
     * Gold Ore 25
     *
     * @constructor Gold Ore 25
     */
    GOLD_ORE_25(32434, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 33402, Skills.MINING),

    /**
     * Gold Ore 26
     *
     * @constructor Gold Ore 26
     */
    GOLD_ORE_26(31065, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 37639, Skills.MINING),

    /**
     * Gold Ore 27
     *
     * @constructor Gold Ore 27
     */
    GOLD_ORE_27(31066, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 37649, Skills.MINING),

    /**
     * Gold Ore 28
     *
     * @constructor Gold Ore 28
     */
    GOLD_ORE_28(31067, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 37650, Skills.MINING),

    /**
     * Gold Ore 29
     *
     * @constructor Gold Ore 29
     */
    GOLD_ORE_29(37311, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 11553, Skills.MINING),

    /**
     * Gold Ore 30
     *
     * @constructor Gold Ore 30
     */
    GOLD_ORE_30(37310, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 11552, Skills.MINING),

    /**
     * Gold Ore 31
     *
     * @constructor Gold Ore 31
     */
    GOLD_ORE_31(37312, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 11554, Skills.MINING),

    /**
     * Gold Ore 32
     *
     * @constructor Gold Ore 32
     */
    GOLD_ORE_32(37471, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 15249, Skills.MINING),

    /**
     * Gold Ore 33
     *
     * @constructor Gold Ore 33
     */
    GOLD_ORE_33(37473, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 15251, Skills.MINING),

    /**
     * Gold Ore 34
     *
     * @constructor Gold Ore 34
     */
    GOLD_ORE_34(37472, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 15250, Skills.MINING),

    /**
     * Gold Ore 49
     *
     * @constructor Gold Ore 49
     */
    GOLD_ORE_49(42033, 40, 0.6, 100 or (200 shl 16), 65.0, 444, 1, "gold rocks", null, 452, Skills.MINING),

    /**
     * Granite
     *
     * @constructor Granite
     */
    GRANITE(10947, 45, 0.2, 10 or (20 shl 16), 50.0, 6979, 1, "granite", null, 10945, Skills.MINING),

    /**
     * Rubium
     *
     * @constructor Rubium
     */
    RUBIUM(29746, 46, 0.6, 50 or (100 shl 16), 17.5, 12630, 1, "rubium", null, 29747, Skills.MINING),

    /**
     * Mithril Ore 0
     *
     * @constructor Mithril Ore 0
     */
    MITHRIL_ORE_0(2103, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 452, Skills.MINING),

    /**
     * Mithril Ore 1
     *
     * @constructor Mithril Ore 1
     */
    MITHRIL_ORE_1(2102, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 450, Skills.MINING),

    /**
     * Mithril Ore 2
     *
     * @constructor Mithril Ore 2
     */
    MITHRIL_ORE_2(4988, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 4994, Skills.MINING),

    /**
     * Mithril Ore 3
     *
     * @constructor Mithril Ore 3
     */
    MITHRIL_ORE_3(4989, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 4995, Skills.MINING),

    /**
     * Mithril Ore 4
     *
     * @constructor Mithril Ore 4
     */
    MITHRIL_ORE_4(4990, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 4996, Skills.MINING),

    /**
     * Mithril Ore 5
     *
     * @constructor Mithril Ore 5
     */
    MITHRIL_ORE_5(11943, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 11553, Skills.MINING),

    /**
     * Mithril Ore 6
     *
     * @constructor Mithril Ore 6
     */
    MITHRIL_ORE_6(11942, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 11552, Skills.MINING),

    /**
     * Mithril Ore 7
     *
     * @constructor Mithril Ore 7
     */
    MITHRIL_ORE_7(11945, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 11555, Skills.MINING),

    /**
     * Mithril Ore 8
     *
     * @constructor Mithril Ore 8
     */
    MITHRIL_ORE_8(11944, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 11554, Skills.MINING),

    /**
     * Mithril Ore 9
     *
     * @constructor Mithril Ore 9
     */
    MITHRIL_ORE_9(11947, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 11557, Skills.MINING),

    /**
     * Mithril Ore 10
     *
     * @constructor Mithril Ore 10
     */
    MITHRIL_ORE_10(11946, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 11556, Skills.MINING),

    /**
     * Mithril Ore 11
     *
     * @constructor Mithril Ore 11
     */
    MITHRIL_ORE_11(14855, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 25373, Skills.MINING),

    /**
     * Mithril Ore 12
     *
     * @constructor Mithril Ore 12
     */
    MITHRIL_ORE_12(14854, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 25372, Skills.MINING),

    /**
     * Mithril Ore 13
     *
     * @constructor Mithril Ore 13
     */
    MITHRIL_ORE_13(14853, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 25371, Skills.MINING),

    /**
     * Mithril Ore 14
     *
     * @constructor Mithril Ore 14
     */
    MITHRIL_ORE_14(16687, 50, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 450, Skills.MINING),

    /**
     * Mithril Ore 15
     *
     * @constructor Mithril Ore 15
     */
    MITHRIL_ORE_15(20421, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 20407, Skills.MINING),

    /**
     * Mithril Ore 16
     *
     * @constructor Mithril Ore 16
     */
    MITHRIL_ORE_16(20420, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 20445, Skills.MINING),

    /**
     * Mithril Ore 17
     *
     * @constructor Mithril Ore 17
     */
    MITHRIL_ORE_17(20419, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 20444, Skills.MINING),

    /**
     * Mithril Ore 18
     *
     * @constructor Mithril Ore 18
     */
    MITHRIL_ORE_18(20418, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 20443, Skills.MINING),

    /**
     * Mithril Ore 19
     *
     * @constructor Mithril Ore 19
     */
    MITHRIL_ORE_19(19012, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 19021, Skills.MINING),

    /**
     * Mithril Ore 20
     *
     * @constructor Mithril Ore 20
     */
    MITHRIL_ORE_20(19013, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 19016, Skills.MINING),

    /**
     * Mithril Ore 21
     *
     * @constructor Mithril Ore 21
     */
    MITHRIL_ORE_21(19014, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 19017, Skills.MINING),

    /**
     * Mithril Ore 22
     *
     * @constructor Mithril Ore 22
     */
    MITHRIL_ORE_22(21278, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 21296, Skills.MINING),

    /**
     * Mithril Ore 23
     *
     * @constructor Mithril Ore 23
     */
    MITHRIL_ORE_23(21279, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 21297, Skills.MINING),

    /**
     * Mithril Ore 24
     *
     * @constructor Mithril Ore 24
     */
    MITHRIL_ORE_24(21280, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 21298, Skills.MINING),

    /**
     * Mithril Ore 25
     *
     * @constructor Mithril Ore 25
     */
    MITHRIL_ORE_25(25369, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 10586, Skills.MINING),

    /**
     * Mithril Ore 26
     *
     * @constructor Mithril Ore 26
     */
    MITHRIL_ORE_26(25368, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 10585, Skills.MINING),

    /**
     * Mithril Ore 27
     *
     * @constructor Mithril Ore 27
     */
    MITHRIL_ORE_27(25370, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 10587, Skills.MINING),

    /**
     * Mithril Ore 28
     *
     * @constructor Mithril Ore 28
     */
    MITHRIL_ORE_28(29236, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 29218, Skills.MINING),

    /**
     * Mithril Ore 29
     *
     * @constructor Mithril Ore 29
     */
    MITHRIL_ORE_29(29237, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 29219, Skills.MINING),

    /**
     * Mithril Ore 30
     *
     * @constructor Mithril Ore 30
     */
    MITHRIL_ORE_30(29238, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 29220, Skills.MINING),

    /**
     * Mithril Ore 31
     *
     * @constructor Mithril Ore 31
     */
    MITHRIL_ORE_31(32439, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 33401, Skills.MINING),

    /**
     * Mithril Ore 32
     *
     * @constructor Mithril Ore 32
     */
    MITHRIL_ORE_32(32438, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 33400, Skills.MINING),

    /**
     * Mithril Ore 33
     *
     * @constructor Mithril Ore 33
     */
    MITHRIL_ORE_33(32440, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 33402, Skills.MINING),

    /**
     * Mithril Ore 34
     *
     * @constructor Mithril Ore 34
     */
    MITHRIL_ORE_34(31087, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 37649, Skills.MINING),

    /**
     * Mithril Ore 35
     *
     * @constructor Mithril Ore 35
     */
    MITHRIL_ORE_35(31086, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 37639, Skills.MINING),

    /**
     * Mithril Ore 36
     *
     * @constructor Mithril Ore 36
     */
    MITHRIL_ORE_36(31088, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 37650, Skills.MINING),

    /**
     * Mithril Ore 37
     *
     * @constructor Mithril Ore 37
     */
    MITHRIL_ORE_37(31170, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 14832, Skills.MINING),

    /**
     * Mithril Ore 38
     *
     * @constructor Mithril Ore 38
     */
    MITHRIL_ORE_38(31171, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 14833, Skills.MINING),

    /**
     * Mithril Ore 39
     *
     * @constructor Mithril Ore 39
     */
    MITHRIL_ORE_39(31172, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 14834, Skills.MINING),

    /**
     * Mithril Ore 40
     *
     * @constructor Mithril Ore 40
     */
    MITHRIL_ORE_40(37692, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 21296, Skills.MINING),

    /**
     * Mithril Ore 41
     *
     * @constructor Mithril Ore 41
     */
    MITHRIL_ORE_41(37693, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 21297, Skills.MINING),

    /**
     * Mithril Ore 42
     *
     * @constructor Mithril Ore 42
     */
    MITHRIL_ORE_42(37694, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 21298, Skills.MINING),

    /**
     * Mithril Ore 49
     *
     * @constructor Mithril Ore 49
     */
    MITHRIL_ORE_49(42036, 55, 0.70, 200 or (400 shl 16), 80.0, 447, 1, "mithril rocks", null, 452, Skills.MINING),

    /**
     * Adamantite Ore 0
     *
     * @constructor Adamantite Ore 0
     */
    ADAMANTITE_ORE_0(2105, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 452, Skills.MINING),

    /**
     * Adamantite Ore 1
     *
     * @constructor Adamantite Ore 1
     */
    ADAMANTITE_ORE_1(2104, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 450, Skills.MINING),

    /**
     * Adamantite Ore 2
     *
     * @constructor Adamantite Ore 2
     */
    ADAMANTITE_ORE_2(4991, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 4994, Skills.MINING),

    /**
     * Adamantite Ore 3
     *
     * @constructor Adamantite Ore 3
     */
    ADAMANTITE_ORE_3(4992, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 4995, Skills.MINING),

    /**
     * Adamantite Ore 4
     *
     * @constructor Adamantite Ore 4
     */
    ADAMANTITE_ORE_4(4993, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 4996, Skills.MINING),

    /**
     * Adamantite Ore 5
     *
     * @constructor Adamantite Ore 5
     */
    ADAMANTITE_ORE_5(11941, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 11554, Skills.MINING),

    /**
     * Adamantite Ore 6
     *
     * @constructor Adamantite Ore 6
     */
    ADAMANTITE_ORE_6(11940, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 11553, Skills.MINING),

    /**
     * Adamantite Ore 7
     *
     * @constructor Adamantite Ore 7
     */
    ADAMANTITE_ORE_7(11939, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 11552, Skills.MINING),

    /**
     * Adamantite Ore 8
     *
     * @constructor Adamantite Ore 8
     */
    ADAMANTITE_ORE_8(14864, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 25373, Skills.MINING),

    /**
     * Adamantite Ore 9
     *
     * @constructor Adamantite Ore 9
     */
    ADAMANTITE_ORE_9(14863, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 25372, Skills.MINING),

    /**
     * Adamantite Ore 10
     *
     * @constructor Adamantite Ore 10
     */
    ADAMANTITE_ORE_10(14862, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 25371, Skills.MINING),

    /**
     * Adamantite Ore 11
     *
     * @constructor Adamantite Ore 11
     */
    ADAMANTITE_ORE_11(20417, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 20407, Skills.MINING),

    /**
     * Adamantite Ore 12
     *
     * @constructor Adamantite Ore 12
     */
    ADAMANTITE_ORE_12(20416, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 20445, Skills.MINING),

    /**
     * Adamantite Ore 13
     *
     * @constructor Adamantite Ore 13
     */
    ADAMANTITE_ORE_13(20414, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 20443, Skills.MINING),

    /**
     * Adamantite Ore 14
     *
     * @constructor Adamantite Ore 14
     */
    ADAMANTITE_ORE_14(20415, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 20444, Skills.MINING),

    /**
     * Adamantite Ore 15
     *
     * @constructor Adamantite Ore 15
     */
    ADAMANTITE_ORE_15(19020, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 19017, Skills.MINING),

    /**
     * Adamantite Ore 16
     *
     * @constructor Adamantite Ore 16
     */
    ADAMANTITE_ORE_16(19018, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 19021, Skills.MINING),

    /**
     * Adamantite Ore 17
     *
     * @constructor Adamantite Ore 17
     */
    ADAMANTITE_ORE_17(19019, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 19016, Skills.MINING),

    /**
     * Adamantite Ore 18
     *
     * @constructor Adamantite Ore 18
     */
    ADAMANTITE_ORE_18(21275, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 21296, Skills.MINING),

    /**
     * Adamantite Ore 19
     *
     * @constructor Adamantite Ore 19
     */
    ADAMANTITE_ORE_19(21276, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 21297, Skills.MINING),

    /**
     * Adamantite Ore 20
     *
     * @constructor Adamantite Ore 20
     */
    ADAMANTITE_ORE_20(21277, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 21298, Skills.MINING),

    /**
     * Adamantite Ore 21
     *
     * @constructor Adamantite Ore 21
     */
    ADAMANTITE_ORE_21(29233, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 29218, Skills.MINING),

    /**
     * Adamantite Ore 22
     *
     * @constructor Adamantite Ore 22
     */
    ADAMANTITE_ORE_22(29234, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 29219, Skills.MINING),

    /**
     * Adamantite Ore 23
     *
     * @constructor Adamantite Ore 23
     */
    ADAMANTITE_ORE_23(29235, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 29220, Skills.MINING),

    /**
     * Adamantite Ore 24
     *
     * @constructor Adamantite Ore 24
     */
    ADAMANTITE_ORE_24(32435, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 33400, Skills.MINING),

    /**
     * Adamantite Ore 25
     *
     * @constructor Adamantite Ore 25
     */
    ADAMANTITE_ORE_25(32437, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 33402, Skills.MINING),

    /**
     * Adamantite Ore 26
     *
     * @constructor Adamantite Ore 26
     */
    ADAMANTITE_ORE_26(32436, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 33401, Skills.MINING),

    /**
     * Adamantite Ore 27
     *
     * @constructor Adamantite Ore 27
     */
    ADAMANTITE_ORE_27(31083, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 37639, Skills.MINING),

    /**
     * Adamantite Ore 28
     *
     * @constructor Adamantite Ore 28
     */
    ADAMANTITE_ORE_28(31085, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 37650, Skills.MINING),

    /**
     * Adamantite Ore 29
     *
     * @constructor Adamantite Ore 29
     */
    ADAMANTITE_ORE_29(31084, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 37649, Skills.MINING),

    /**
     * Adamantite Ore 30
     *
     * @constructor Adamantite Ore 30
     */
    ADAMANTITE_ORE_30(31173, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 14832, Skills.MINING),

    /**
     * Adamantite Ore 31
     *
     * @constructor Adamantite Ore 31
     */
    ADAMANTITE_ORE_31(31174, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 14833, Skills.MINING),

    /**
     * Adamantite Ore 32
     *
     * @constructor Adamantite Ore 32
     */
    ADAMANTITE_ORE_32(31175, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 14834, Skills.MINING),

    /**
     * Adamantite Ore 33
     *
     * @constructor Adamantite Ore 33
     */
    ADAMANTITE_ORE_33(37468, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 15249, Skills.MINING),

    /**
     * Adamantite Ore 34
     *
     * @constructor Adamantite Ore 34
     */
    ADAMANTITE_ORE_34(37469, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 15250, Skills.MINING),

    /**
     * Adamantite Ore 35
     *
     * @constructor Adamantite Ore 35
     */
    ADAMANTITE_ORE_35(37470, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 15251, Skills.MINING),

    /**
     * Adamantite Ore 36
     *
     * @constructor Adamantite Ore 36
     */
    ADAMANTITE_ORE_36(37689, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 21296, Skills.MINING),

    /**
     * Adamantite Ore 37
     *
     * @constructor Adamantite Ore 37
     */
    ADAMANTITE_ORE_37(37690, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 21297, Skills.MINING),

    /**
     * Adamantite Ore 38
     *
     * @constructor Adamantite Ore 38
     */
    ADAMANTITE_ORE_38(37691, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 21298, Skills.MINING),

    /**
     * Adamantite Ore 39
     *
     * @constructor Adamantite Ore 39
     */
    ADAMANTITE_ORE_39(42037, 70, 0.85, 400 or (800 shl 16), 95.0, 449, 1, "adamant rocks", null, 452, Skills.MINING),

    /**
     * Runite Ore 0
     *
     * @constructor Runite Ore 0
     */
    RUNITE_ORE_0(2107, 85, 0.95, 1250 or (2500 shl 16), 125.0, 451, 1, "runite rocks", null, 452, Skills.MINING),

    /**
     * Runite Ore 1
     *
     * @constructor Runite Ore 1
     */
    RUNITE_ORE_1(2106, 85, 0.95, 1250 or (2500 shl 16), 125.0, 451, 1, "runite rocks", null, 450, Skills.MINING),

    /**
     * Runite Ore 2
     *
     * @constructor Runite Ore 2
     */
    RUNITE_ORE_2(6669, 85, 0.95, 1250 or (2500 shl 16), 125.0, 451, 1, "runite rocks", null, 21296, Skills.MINING),

    /**
     * Runite Ore 3
     *
     * @constructor Runite Ore 3
     */
    RUNITE_ORE_3(6671, 85, 0.95, 1250 or (2500 shl 16), 125.0, 451, 1, "runite rocks", null, 21298, Skills.MINING),

    /**
     * Runite Ore 4
     *
     * @constructor Runite Ore 4
     */
    RUNITE_ORE_4(6670, 85, 0.95, 1250 or (2500 shl 16), 125.0, 451, 1, "runite rocks", null, 21297, Skills.MINING),

    /**
     * Runite Ore 5
     *
     * @constructor Runite Ore 5
     */
    RUNITE_ORE_5(14861, 85, 0.95, 1250 or (2500 shl 16), 125.0, 451, 1, "runite rocks", null, 25373, Skills.MINING),

    /**
     * Runite Ore 6
     *
     * @constructor Runite Ore 6
     */
    RUNITE_ORE_6(14860, 85, 0.95, 1250 or (2500 shl 16), 125.0, 451, 1, "runite rocks", null, 25372, Skills.MINING),

    /**
     * Runite Ore 7
     *
     * @constructor Runite Ore 7
     */
    RUNITE_ORE_7(14859, 85, 0.95, 1250 or (2500 shl 16), 125.0, 451, 1, "runite rocks", null, 25371, Skills.MINING),

    /**
     * Runite Ore 8
     *
     * @constructor Runite Ore 8
     */
    RUNITE_ORE_8(33079, 85, 0.95, 1250 or (2500 shl 16), 125.0, 451, 1, "runite rocks", null, 33401, Skills.MINING),

    /**
     * Runite Ore 9
     *
     * @constructor Runite Ore 9
     */
    RUNITE_ORE_9(33078, 85, 0.95, 1250 or (2500 shl 16), 125.0, 451, 1, "runite rocks", null, 33400, Skills.MINING),

    /**
     * Runite Ore 10
     *
     * @constructor Runite Ore 10
     */
    RUNITE_ORE_10(37208, 85, 0.95, 1250 or (2500 shl 16), 125.0, 451, 1, "runite rocks", null, 21296, Skills.MINING),

    /**
     * Runite Ore 11
     *
     * @constructor Runite Ore 11
     */
    RUNITE_ORE_11(37465, 85, 0.95, 1250 or (2500 shl 16), 125.0, 451, 1, "runite rocks", null, 15249, Skills.MINING),

    /**
     * Runite Ore 12
     *
     * @constructor Runite Ore 12
     */
    RUNITE_ORE_12(37466, 85, 0.95, 1250 or (2500 shl 16), 125.0, 451, 1, "runite rocks", null, 15250, Skills.MINING),

    /**
     * Runite Ore 13
     *
     * @constructor Runite Ore 13
     */
    RUNITE_ORE_13(37467, 85, 0.95, 1250 or (2500 shl 16), 125.0, 451, 1, "runite rocks", null, 15251, Skills.MINING),

    /**
     * Runite Ore 14
     *
     * @constructor Runite Ore 14
     */
    RUNITE_ORE_14(37695, 85, 0.95, 1250 or (2500 shl 16), 125.0, 451, 1, "runite rocks", null, 21297, Skills.MINING),

    /**
     * Runite Ore 15
     *
     * @constructor Runite Ore 15
     */
    RUNITE_ORE_15(37696, 85, 0.95, 1250 or (2500 shl 16), 125.0, 451, 1, "runite rocks", null, 21298, Skills.MINING),

    /**
     * Gem Rock 0
     *
     * @constructor Gem Rock 0
     */
    GEM_ROCK_0(23567, 40, 0.95, 166 or (175 shl 16), 65.0, 1625, 1, "gem rocks", null, 21297, Skills.MINING),

    /**
     * Gem Rock 1
     *
     * @constructor Gem Rock 1
     */
    GEM_ROCK_1(23566, 40, 0.95, 166 or (175 shl 16), 65.0, 1625, 1, "gem rocks", null, 21296, Skills.MINING),

    /**
     * Gem Rock 2
     *
     * @constructor Gem Rock 2
     */
    GEM_ROCK_2(23568, 40, 0.95, 166 or (175 shl 16), 65.0, 1625, 1, "gem rocks", null, 21298, Skills.MINING);


    val id: Int
    val level: Int
    val rate: Double
    val respawnRate: Int
    val experience: Double
    val reward: Int
    val rewardAmount: Int

    /**
     * Get name
     *
     * @return
     */
    fun getName(): String { return name!! }
    val animation: Animation?
    val emptyId: Int
    val skillId: Int
    val isFarming: Boolean

    constructor(
        id: Int,
        level: Int,
        rate: Double,
        respawnRate: Int,
        experience: Double,
        reward: Int,
        rewardAmount: Int,
        name: String,
        animation: Animation?,
        emptyId: Int,
        skillId: Int
    ) {
        this.id = id
        this.level = level
        this.rate = rate
        this.respawnRate = respawnRate
        this.experience = experience
        this.reward = reward
        this.rewardAmount = rewardAmount
        var name = SceneryDefinition.forId(id).name
        this.animation = animation
        this.emptyId = emptyId
        this.skillId = skillId
        this.isFarming = false
    }

    constructor(
        id: Int,
        level: Int,
        rate: Double,
        respawnRate: Int,
        experience: Double,
        reward: Int,
        rewardAmount: Int,
        name: String,
        animation: Animation?,
        emptyId: Int,
        skillId: Int,
        farming: Boolean
    ) {
        this.id = id
        this.level = level
        this.rate = rate
        this.respawnRate = respawnRate
        this.experience = experience
        this.reward = reward
        this.rewardAmount = rewardAmount
        var name = name
        this.animation = animation
        this.emptyId = emptyId
        this.skillId = skillId
        this.isFarming = farming
    }

    val respawnDuration: Int

        get() {
            val minimum = respawnRate and 0xFFFF
            val maximum = (respawnRate shr 16) and 0xFFFF
            val playerRatio = ServerConstants.MAX_PLAYERS.toDouble() / players.size
            return (minimum + ((maximum - minimum) / playerRatio)).toInt()
        }

    val maximumRespawn: Int

        get() = (respawnRate shr 16) and 0xFFFF

    val minimumRespawn: Int

        get() = respawnRate and 0xFFFF

    companion object {

        private val consts: MutableMap<Int, SkillingResource> = HashMap()

        init {
            for (resource in values()) {
                if (consts.containsKey(resource.id)) {
                }
                consts[resource.id] = resource
            }
        }

        fun forId(id: Int): SkillingResource? {
            return consts[id]
        }

        fun isEmpty(id: Int): Boolean {
            for (r in values()) {
                if (r.emptyId == id) {
                    return true
                }
            }
            return false
        }
    }
}
