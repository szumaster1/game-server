package content.global.skill;

import core.ServerConstants;
import core.game.node.entity.skill.Skills;
import core.game.world.repository.Repository;
import core.game.world.update.flag.context.Animation;

import java.util.HashMap;
import java.util.Map;

/**
 * The enum Skilling resource.
 */
public enum SkillingResource {
    /**
     * Standard tree 1 skilling resource.
     */
    STANDARD_TREE_1(1276, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),
    /**
     * Standard tree 2 skilling resource.
     */
    STANDARD_TREE_2(1277, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1343, Skills.WOODCUTTING),
    /**
     * Standard tree 3 skilling resource.
     */
    STANDARD_TREE_3(1278, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),
    /**
     * Standard tree 4 skilling resource.
     */
    STANDARD_TREE_4(1279, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1345, Skills.WOODCUTTING),
    /**
     * Standard tree 5 skilling resource.
     */
    STANDARD_TREE_5(1280, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1343, Skills.WOODCUTTING),
    /**
     * Standard tree 6 skilling resource.
     */
    STANDARD_TREE_6(1330, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1341, Skills.WOODCUTTING),
    /**
     * Standard tree 7 skilling resource.
     */
    STANDARD_TREE_7(1331, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1341, Skills.WOODCUTTING),
    /**
     * Standard tree 8 skilling resource.
     */
    STANDARD_TREE_8(1332, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1341, Skills.WOODCUTTING),
    /**
     * Standard tree 9 skilling resource.
     */
    STANDARD_TREE_9(2409, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),
    /**
     * Standard tree 10 skilling resource.
     */
    STANDARD_TREE_10(3033, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1345, Skills.WOODCUTTING),
    /**
     * Standard tree 11 skilling resource.
     */
    STANDARD_TREE_11(3034, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1345, Skills.WOODCUTTING),
    /**
     * Standard tree 12 skilling resource.
     */
    STANDARD_TREE_12(3035, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1347, Skills.WOODCUTTING),
    /**
     * Standard tree 13 skilling resource.
     */
    STANDARD_TREE_13(3036, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1351, Skills.WOODCUTTING),
    /**
     * Standard tree 14 skilling resource.
     */
    STANDARD_TREE_14(3879, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 3880, Skills.WOODCUTTING),
    /**
     * Standard tree 15 skilling resource.
     */
    STANDARD_TREE_15(3881, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 3880, Skills.WOODCUTTING),
    /**
     * Standard tree 16 skilling resource.
     */
    STANDARD_TREE_16(3882, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 3880, Skills.WOODCUTTING),
    /**
     * Standard tree 17 skilling resource.
     */
    STANDARD_TREE_17(3883, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 3884, Skills.WOODCUTTING),
    /**
     * Standard tree 18 skilling resource.
     */
    STANDARD_TREE_18(10041, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),
    /**
     * Standard tree 19 skilling resource.
     */
    STANDARD_TREE_19(14308, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),
    /**
     * Standard tree 20 skilling resource.
     */
    STANDARD_TREE_20(14309, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),
    /**
     * Standard tree 21 skilling resource.
     */
    STANDARD_TREE_21(16264, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),
    /**
     * Standard tree 22 skilling resource.
     */
    STANDARD_TREE_22(16265, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),
    /**
     * Standard tree 23 skilling resource.
     */
    STANDARD_TREE_23(30132, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),
    /**
     * Standard tree 24 skilling resource.
     */
    STANDARD_TREE_24(30133, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),
    /**
     * Standard tree 25 skilling resource.
     */
    STANDARD_TREE_25(37477, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 1342, Skills.WOODCUTTING),
    /**
     * Standard tree 26 skilling resource.
     */
    STANDARD_TREE_26(37478, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 37653, Skills.WOODCUTTING),
    /**
     * Standard tree 27 skilling resource.
     */
    STANDARD_TREE_27(37652, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "tree", null, 37653, Skills.WOODCUTTING),

    /**
     * Apple tree skilling resource.
     */
    APPLE_TREE(7941, 1, 0.05, 50 | 100 << 16, 25.0, -1, 1, "tree", null, 37653, Skills.WOODCUTTING, true),
    /**
     * Banana tree skilling resource.
     */
    BANANA_TREE(8000, 1, 0.05, 50 | 100 << 16, 25.0, -1, 1, "tree", null, 37653, Skills.WOODCUTTING, true),
    /**
     * Orange tree skilling resource.
     */
    ORANGE_TREE(8057, 1, 0.05, 50 | 100 << 16, 25.0, -1, 1, "tree", null, 37653, Skills.WOODCUTTING, true),
    /**
     * Curry tree skilling resource.
     */
    CURRY_TREE(8026, 1, 0.05, 50 | 100 << 16, 25.0, -1, 1, "tree", null, 37653, Skills.WOODCUTTING, true),
    /**
     * Pineapple tree skilling resource.
     */
    PINEAPPLE_TREE(7972, 1, 0.05, 50 | 100 << 16, 25.0, -1, 1, "tree", null, 37653, Skills.WOODCUTTING, true),
    /**
     * Papaya tree skilling resource.
     */
    PAPAYA_TREE(8111, 1, 0.05, 50 | 100 << 16, 25.0, -1, 1, "tree", null, 37653, Skills.WOODCUTTING, true),
    /**
     * Palm tree skilling resource.
     */
    PALM_TREE(8084, 1, 0.05, 50 | 100 << 16, 25.0, -1, 1, "tree", null, 37653, Skills.WOODCUTTING, true),

    /**
     * The Dead tree 1.
     */
    DEAD_TREE_1(1282, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1347, Skills.WOODCUTTING),
    /**
     * The Dead tree 2.
     */
    DEAD_TREE_2(1283, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1347, Skills.WOODCUTTING),
    /**
     * The Dead tree 3.
     */
    DEAD_TREE_3(1284, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1348, Skills.WOODCUTTING),
    /**
     * The Dead tree 4.
     */
    DEAD_TREE_4(1285, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1349, Skills.WOODCUTTING),
    /**
     * The Dead tree 5.
     */
    DEAD_TREE_5(1286, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1351, Skills.WOODCUTTING),
    /**
     * The Dead tree 6.
     */
    DEAD_TREE_6(1289, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1353, Skills.WOODCUTTING),
    /**
     * The Dead tree 7.
     */
    DEAD_TREE_7(1290, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1354, Skills.WOODCUTTING),
    /**
     * The Dead tree 8.
     */
    DEAD_TREE_8(1291, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 23054, Skills.WOODCUTTING),
    /**
     * The Dead tree 9.
     */
    DEAD_TREE_9(1365, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1352, Skills.WOODCUTTING),
    /**
     * The Dead tree 10.
     */
    DEAD_TREE_10(1383, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1358, Skills.WOODCUTTING),
    /**
     * The Dead tree 11.
     */
    DEAD_TREE_11(1384, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1359, Skills.WOODCUTTING),
    /**
     * The Dead tree 12.
     */
    DEAD_TREE_12(5902, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1347, Skills.WOODCUTTING),
    /**
     * The Dead tree 13.
     */
    DEAD_TREE_13(5903, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1353, Skills.WOODCUTTING),
    /**
     * The Dead tree 14.
     */
    DEAD_TREE_14(5904, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1353, Skills.WOODCUTTING),
    /**
     * The Dead tree 15.
     */
    DEAD_TREE_15(32294, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1353, Skills.WOODCUTTING),
    /**
     * The Dead tree 16.
     */
    DEAD_TREE_16(37481, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1347, Skills.WOODCUTTING),
    /**
     * The Dead tree 17.
     */
    DEAD_TREE_17(37482, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1351, Skills.WOODCUTTING),
    /**
     * The Dead tree 18.
     */
    DEAD_TREE_18(37483, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dead tree", null, 1358, Skills.WOODCUTTING),
    /**
     * The Dead tree 19.
     */
    DEAD_TREE_19(24168, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "dying tree", null, 24169, Skills.WOODCUTTING),

    /**
     * The Dramen tree.
     */
    DRAMEN_TREE(1292, 36, 0.05, -1, 25.0, 771, Integer.MAX_VALUE, "dramen tree", null, -1, Skills.WOODCUTTING),

    /**
     * Evergreen 1 skilling resource.
     */
    EVERGREEN_1(1315, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "evergreen", null, 1342, Skills.WOODCUTTING),
    /**
     * Evergreen 2 skilling resource.
     */
    EVERGREEN_2(1316, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "evergreen", null, 1355, Skills.WOODCUTTING),
    /**
     * Evergreen 3 skilling resource.
     */
    EVERGREEN_3(1318, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "evergreen", null, 1355, Skills.WOODCUTTING),
    /**
     * Evergreen 4 skilling resource.
     */
    EVERGREEN_4(1319, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 1, "evergreen", null, 1355, Skills.WOODCUTTING),

    /**
     * The Jungle tree 1.
     */
    JUNGLE_TREE_1(2887, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 2, "jungle tree", null, 0, Skills.WOODCUTTING),
    /**
     * The Jungle tree 2.
     */
    JUNGLE_TREE_2(2889, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 2, "jungle tree", null, 0, Skills.WOODCUTTING),
    /**
     * The Jungle tree 3.
     */
    JUNGLE_TREE_3(2890, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 2, "jungle tree", null, 0, Skills.WOODCUTTING),
    /**
     * The Jungle tree 4.
     */
    JUNGLE_TREE_4(4818, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 2, "jungle tree", null, 0, Skills.WOODCUTTING),
    /**
     * The Jungle tree 5.
     */
    JUNGLE_TREE_5(4820, 1, 0.05, 50 | 100 << 16, 25.0, 1511, 2, "jungle tree", null, 0, Skills.WOODCUTTING),

    /**
     * The Jungle bush 1.
     */
    JUNGLE_BUSH_1(2892, 1, 0.15, 50 | 100 << 16, 100.0, 1511, 1, "jungle bush", null, 2894, Skills.WOODCUTTING),
    /**
     * The Jungle bush 2.
     */
    JUNGLE_BUSH_2(2893, 1, 0.15, 50 | 100 << 16, 100.0, 1511, 1, "jungle bush", null, 2895, Skills.WOODCUTTING),

    /**
     * The Achey tree.
     */
    ACHEY_TREE(2023, 1, 0.05, 50 | 100 << 16, 25.0, 2862, 1, "achey tree", null, 3371, Skills.WOODCUTTING),

    /**
     * The Oak tree 1.
     */
    OAK_TREE_1(1281, 15, 0.15, 14 | 22 << 16, 37.5, 1521, 10, "oak tree", null, 1356, Skills.WOODCUTTING),
    /**
     * The Oak tree 2.
     */
    OAK_TREE_2(3037, 15, 0.15, 14 | 22 << 16, 37.5, 1521, 10, "oak tree", null, 1357, Skills.WOODCUTTING),
    /**
     * The Oak tree 3.
     */
    OAK_TREE_3(37479, 15, 0.15, 14 | 22 << 16, 37.5, 1521, 10, "oak tree", null, 1356, Skills.WOODCUTTING),
    /**
     * The Oak tree 4.
     */
    OAK_TREE_4(8467, 15, 0.15, 14 | 22 << 16, 37.5, 1521, 10, "oak tree", null, 1356, Skills.WOODCUTTING, true),

    /**
     * The Willow tree 1.
     */
    WILLOW_TREE_1(1308, 30, 0.3, 14 | 22 << 16, 67.8, 1519, 20, "willow tree", null, 7399, Skills.WOODCUTTING),
    /**
     * The Willow tree 2.
     */
    WILLOW_TREE_2(5551, 30, 0.3, 14 | 22 << 16, 67.8, 1519, 20, "willow tree", null, 5554, Skills.WOODCUTTING),
    /**
     * The Willow tree 3.
     */
    WILLOW_TREE_3(5552, 30, 0.3, 14 | 22 << 16, 67.8, 1519, 20, "willow tree", null, 5554, Skills.WOODCUTTING),
    /**
     * The Willow tree 4.
     */
    WILLOW_TREE_4(5553, 30, 0.3, 14 | 22 << 16, 67.8, 1519, 20, "willow tree", null, 5554, Skills.WOODCUTTING),
    /**
     * The Willow tree 5.
     */
    WILLOW_TREE_5(37480, 30, 0.3, 14 | 22 << 16, 67.8, 1519, 20, "willow tree", null, 7399, Skills.WOODCUTTING),
    /**
     * The Willow tree 6.
     */
    WILLOW_TREE_6(8488, 30, 0.3, 14 | 22 << 16, 67.8, 1519, 20, "willow tree", null, 7399, Skills.WOODCUTTING, true),

    /**
     * Teak 1 skilling resource.
     */
    TEAK_1(9036, 35, 0.7, 35 | 60 << 16, 85.0, 6333, 25, "teak", null, 9037, Skills.WOODCUTTING),
    /**
     * Teak 2 skilling resource.
     */
    TEAK_2(15062, 35, 0.7, 35 | 60 << 16, 85.0, 6333, 25, "teak", null, 9037, Skills.WOODCUTTING),

    /**
     * The Maple tree 1.
     */
    MAPLE_TREE_1(1307, 45, 0.65, 58 | 100 << 16, 100.0, 1517, 30, "maple tree", null, 7400, Skills.WOODCUTTING),
    /**
     * The Maple tree 2.
     */
    MAPLE_TREE_2(4674, 45, 0.65, 58 | 100 << 16, 100.0, 1517, 30, "maple tree", null, 7400, Skills.WOODCUTTING),
    /**
     * The Maple tree 3.
     */
    MAPLE_TREE_3(8444, 45, 0.65, 58 | 100 << 16, 100.0, 1517, 30, "maple tree", null, 7400, Skills.WOODCUTTING, true),

    /**
     * The Hollow tree 1.
     */
    HOLLOW_TREE_1(2289, 45, 0.6, 58 | 100 << 16, 82.5, 3239, 30, "hollow tree", null, 2310, Skills.WOODCUTTING),
    /**
     * The Hollow tree 2.
     */
    HOLLOW_TREE_2(4060, 45, 0.6, 58 | 100 << 16, 82.5, 3239, 30, "hollow tree", null, 4061, Skills.WOODCUTTING),

    /**
     * Mahogany skilling resource.
     */
    MAHOGANY(9034, 50, 0.7, 62 | 115 << 16, 125.0, 6332, 35, "mahogany", null, 9035, Skills.WOODCUTTING),

    /**
     * The Arctic pine.
     */
    ARCTIC_PINE(21273, 54, 0.73, 75 | 130 << 16, 140.2, 10810, 35, "arctic pine", null, 21274, Skills.WOODCUTTING),

    /**
     * The Eucalyptus 1.
     */
    EUCALYPTUS_1(28951, 58, 0.77, 80 | 140 << 16, 165.0, 12581, 35, "eucalyptus tree", null, 28954, Skills.WOODCUTTING),
    /**
     * The Eucalyptus 2.
     */
    EUCALYPTUS_2(28952, 58, 0.77, 80 | 140 << 16, 165.0, 12581, 35, "eucalyptus tree", null, 28955, Skills.WOODCUTTING),
    /**
     * The Eucalyptus 3.
     */
    EUCALYPTUS_3(28953, 58, 0.77, 80 | 140 << 16, 165.0, 12581, 35, "eucalyptus tree", null, 28956, Skills.WOODCUTTING),

    /**
     * Yew skilling resource.
     */
    YEW(1309, 60, 0.8, 100 | 162 << 16, 175.0, 1515, 40, "yew", null, 7402, Skills.WOODCUTTING),
    /**
     * Yew 1 skilling resource.
     */
    YEW_1(8513, 60, 0.8, 100 | 162 << 16, 175.0, 1515, 40, "yew", null, 7402, Skills.WOODCUTTING, true),

    /**
     * The Magic tree 1.
     */
    MAGIC_TREE_1(1306, 75, 0.9, 200 | 317 << 16, 250.0, 1513, 50, "magic tree", null, 7401, Skills.WOODCUTTING),
    /**
     * The Magic tree 2.
     */
    MAGIC_TREE_2(37823, 75, 0.9, 200 | 317 << 16, 250.0, 1513, 50, "magic tree", null, 37824, Skills.WOODCUTTING),
    /**
     * The Magic tree 3.
     */
    MAGIC_TREE_3(8409, 75, 0.9, 200 | 317 << 16, 250.0, 1513, 50, "magic tree", null, 37824, Skills.WOODCUTTING, true),

    /**
     * The Cursed magic tree.
     */
    CURSED_MAGIC_TREE(37821, 82, 0.95, 200 | 317 << 16, 275.0, 1513, 50, "magic tree", null, 37822, Skills.WOODCUTTING),

    /**
     * The Copper ore 0.
     */
    COPPER_ORE_0(2090, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 450, Skills.MINING),
    /**
     * The Copper ore 1.
     */
    COPPER_ORE_1(2091, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 452, Skills.MINING),
    /**
     * The Copper ore 2.
     */
    COPPER_ORE_2(4976, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 4994, Skills.MINING),
    /**
     * The Copper ore 3.
     */
    COPPER_ORE_3(4977, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 4995, Skills.MINING),
    /**
     * The Copper ore 4.
     */
    COPPER_ORE_4(4978, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 4996, Skills.MINING),
    /**
     * The Copper ore 5.
     */
    COPPER_ORE_5(9710, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 18954, Skills.MINING),
    /**
     * The Copper ore 6.
     */
    COPPER_ORE_6(9709, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 32448, Skills.MINING),
    /**
     * The Copper ore 7.
     */
    COPPER_ORE_7(9708, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 32447, Skills.MINING),
    /**
     * The Copper ore 8.
     */
    COPPER_ORE_8(11960, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 11555, Skills.MINING),
    /**
     * The Copper ore 9.
     */
    COPPER_ORE_9(11961, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 11556, Skills.MINING),
    /**
     * The Copper ore 10.
     */
    COPPER_ORE_10(11962, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 11557, Skills.MINING),
    /**
     * The Copper ore 11.
     */
    COPPER_ORE_11(11937, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 11553, Skills.MINING),
    /**
     * The Copper ore 12.
     */
    COPPER_ORE_12(11936, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 11552, Skills.MINING),
    /**
     * The Copper ore 13.
     */
    COPPER_ORE_13(11938, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 11554, Skills.MINING),
    /**
     * The Copper ore 14.
     */
    COPPER_ORE_14(12746, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 450, Skills.MINING),
    /**
     * The Copper ore 15.
     */
    COPPER_ORE_15(14906, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 14894, Skills.MINING),
    /**
     * The Copper ore 16.
     */
    COPPER_ORE_16(14907, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 14895, Skills.MINING),
    /**
     * The Copper ore 17.
     */
    COPPER_ORE_17(20448, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 20445, Skills.MINING),
    /**
     * The Copper ore 18.
     */
    COPPER_ORE_18(20451, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 20445, Skills.MINING),
    /**
     * The Copper ore 19.
     */
    COPPER_ORE_19(20446, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 20443, Skills.MINING),
    /**
     * The Copper ore 20.
     */
    COPPER_ORE_20(20447, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 20444, Skills.MINING),
    /**
     * The Copper ore 21.
     */
    COPPER_ORE_21(20408, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 20407, Skills.MINING),
    /**
     * The Copper ore 22.
     */
    COPPER_ORE_22(18993, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 19005, Skills.MINING),
    /**
     * The Copper ore 23.
     */
    COPPER_ORE_23(18992, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 19004, Skills.MINING),
    /**
     * The Copper ore 24.
     */
    COPPER_ORE_24(19007, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 19016, Skills.MINING),
    /**
     * The Copper ore 25.
     */
    COPPER_ORE_25(19006, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 19021, Skills.MINING),
    /**
     * The Copper ore 26.
     */
    COPPER_ORE_26(18991, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 19003, Skills.MINING),
    /**
     * The Copper ore 27.
     */
    COPPER_ORE_27(19008, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 19017, Skills.MINING),
    /**
     * The Copper ore 28.
     */
    COPPER_ORE_28(21285, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 21297, Skills.MINING),
    /**
     * The Copper ore 29.
     */
    COPPER_ORE_29(21284, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 21296, Skills.MINING),
    /**
     * The Copper ore 30.
     */
    COPPER_ORE_30(21286, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 21298, Skills.MINING),
    /**
     * The Copper ore 31.
     */
    COPPER_ORE_31(29231, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 29219, Skills.MINING),
    /**
     * The Copper ore 32.
     */
    COPPER_ORE_32(29230, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 29218, Skills.MINING),
    /**
     * The Copper ore 33.
     */
    COPPER_ORE_33(29232, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 29220, Skills.MINING),
    /**
     * The Copper ore 34.
     */
    COPPER_ORE_34(31082, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 37650, Skills.MINING),
    /**
     * The Copper ore 35.
     */
    COPPER_ORE_35(31081, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 37649, Skills.MINING),
    /**
     * The Copper ore 36.
     */
    COPPER_ORE_36(31080, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 37639, Skills.MINING),
    /**
     * The Copper ore 37.
     */
    COPPER_ORE_37(37647, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 37650, Skills.MINING),
    /**
     * The Copper ore 38.
     */
    COPPER_ORE_38(37646, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 37649, Skills.MINING),
    /**
     * The Copper ore 39.
     */
    COPPER_ORE_39(37645, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 37639, Skills.MINING),
    /**
     * The Copper ore 40.
     */
    COPPER_ORE_40(37637, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 37639, Skills.MINING),
    /**
     * The Copper ore 41.
     */
    COPPER_ORE_41(37688, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 21298, Skills.MINING),
    /**
     * The Copper ore 42.
     */
    COPPER_ORE_42(37686, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 21296, Skills.MINING),
    /**
     * The Copper ore 43.
     */
    COPPER_ORE_43(37687, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 21297, Skills.MINING),
    /**
     * The Copper ore 44.
     */
    COPPER_ORE_44(3042, 1, 0.05, 4 | 8 << 16, 17.5, 436, 1, "copper rocks", null, 11552, Skills.MINING),

    /**
     * The Tin ore 0.
     */
    TIN_ORE_0(2094, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 450, Skills.MINING),
    /**
     * The Tin ore 1.
     */
    TIN_ORE_1(2095, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 452, Skills.MINING),
    /**
     * The Tin ore 2.
     */
    TIN_ORE_2(3043, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 11552, Skills.MINING),
    /**
     * The Tin ore 3.
     */
    TIN_ORE_3(4979, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 4994, Skills.MINING),
    /**
     * The Tin ore 4.
     */
    TIN_ORE_4(4980, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 4995, Skills.MINING),
    /**
     * The Tin ore 5.
     */
    TIN_ORE_5(4981, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 4996, Skills.MINING),
    /**
     * The Tin ore 6.
     */
    TIN_ORE_6(11957, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 11555, Skills.MINING),
    /**
     * The Tin ore 7.
     */
    TIN_ORE_7(11958, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 11556, Skills.MINING),
    /**
     * The Tin ore 8.
     */
    TIN_ORE_8(11959, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 11557, Skills.MINING),
    /**
     * The Tin ore 9.
     */
    TIN_ORE_9(11934, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 11553, Skills.MINING),
    /**
     * The Tin ore 10.
     */
    TIN_ORE_10(11935, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 11554, Skills.MINING),
    /**
     * The Tin ore 11.
     */
    TIN_ORE_11(11933, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 11552, Skills.MINING),
    /**
     * The Tin ore 12.
     */
    TIN_ORE_12(14902, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 14894, Skills.MINING),
    /**
     * The Tin ore 13.
     */
    TIN_ORE_13(14903, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 14895, Skills.MINING),
    /**
     * The Tin ore 14.
     */
    TIN_ORE_14(18995, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 19004, Skills.MINING),
    /**
     * The Tin ore 15.
     */
    TIN_ORE_15(18994, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 19003, Skills.MINING),
    /**
     * The Tin ore 16.
     */
    TIN_ORE_16(18996, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 19005, Skills.MINING),
    /**
     * The Tin ore 17.
     */
    TIN_ORE_17(19025, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 19016, Skills.MINING),
    /**
     * The Tin ore 18.
     */
    TIN_ORE_18(19024, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 19021, Skills.MINING),
    /**
     * The Tin ore 19.
     */
    TIN_ORE_19(19026, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 19017, Skills.MINING),
    /**
     * The Tin ore 20.
     */
    TIN_ORE_20(21293, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 21296, Skills.MINING),
    /**
     * The Tin ore 21.
     */
    TIN_ORE_21(21295, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 21298, Skills.MINING),
    /**
     * The Tin ore 22.
     */
    TIN_ORE_22(21294, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 21297, Skills.MINING),
    /**
     * The Tin ore 23.
     */
    TIN_ORE_23(29227, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 29218, Skills.MINING),
    /**
     * The Tin ore 24.
     */
    TIN_ORE_24(29229, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 29220, Skills.MINING),
    /**
     * The Tin ore 25.
     */
    TIN_ORE_25(29228, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 29219, Skills.MINING),
    /**
     * The Tin ore 26.
     */
    TIN_ORE_26(31079, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 37650, Skills.MINING),
    /**
     * The Tin ore 27.
     */
    TIN_ORE_27(31078, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 37649, Skills.MINING),
    /**
     * The Tin ore 28.
     */
    TIN_ORE_28(31077, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 37639, Skills.MINING),
    /**
     * The Tin ore 29.
     */
    TIN_ORE_29(37644, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 37650, Skills.MINING),
    /**
     * The Tin ore 30.
     */
    TIN_ORE_30(37643, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 37649, Skills.MINING),
    /**
     * The Tin ore 31.
     */
    TIN_ORE_31(37642, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 37639, Skills.MINING),
    /**
     * The Tin ore 32.
     */
    TIN_ORE_32(37638, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 37639, Skills.MINING),
    /**
     * The Tin ore 33.
     */
    TIN_ORE_33(37685, 1, 0.05, 4 | 8 << 16, 17.5, 438, 1, "tin rocks", null, 21298, Skills.MINING),

    /**
     * The Rune essence 0 skilling resource.
     */
    RUNE_ESSENCE_0(2491, 1, 0.1, 1 | 1 << 16, 5.0, 1436, Integer.MAX_VALUE, "rune essence", null, -1, Skills.MINING),
    /**
     * The Rune essence 1 skilling resource.
     */
    RUNE_ESSENCE_1(16684, 1, 0.1, 1 | 1 << 16, 5.0, 1436, Integer.MAX_VALUE, "rune essence", null, -1, Skills.MINING),

    /**
     * Clay 0 skilling resource.
     */
    CLAY_0(2109, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 452, Skills.MINING),
    /**
     * Clay 1 skilling resource.
     */
    CLAY_1(2108, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 450, Skills.MINING),
    /**
     * Clay 2 skilling resource.
     */
    CLAY_2(9712, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 32448, Skills.MINING),
    /**
     * Clay 3 skilling resource.
     */
    CLAY_3(9713, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 18954, Skills.MINING),
    /**
     * Clay 4 skilling resource.
     */
    CLAY_4(9711, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 32447, Skills.MINING),
    /**
     * Clay 5 skilling resource.
     */
    CLAY_5(10949, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 10945, Skills.MINING),
    /**
     * Clay 6 skilling resource.
     */
    CLAY_6(11190, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 21297, Skills.MINING),
    /**
     * Clay 7 skilling resource.
     */
    CLAY_7(11191, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 21298, Skills.MINING),
    /**
     * Clay 8 skilling resource.
     */
    CLAY_8(11189, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 21296, Skills.MINING),
    /**
     * Clay 9 skilling resource.
     */
    CLAY_9(12942, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 4995, Skills.MINING),
    /**
     * Clay 10 skilling resource.
     */
    CLAY_10(12943, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 4996, Skills.MINING),
    /**
     * Clay 11 skilling resource.
     */
    CLAY_11(12941, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 4994, Skills.MINING),
    /**
     * Clay 12 skilling resource.
     */
    CLAY_12(14904, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 14894, Skills.MINING),
    /**
     * Clay 13 skilling resource.
     */
    CLAY_13(14905, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 14895, Skills.MINING),
    /**
     * Clay 14 skilling resource.
     */
    CLAY_14(15505, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 11557, Skills.MINING),
    /**
     * Clay 15 skilling resource.
     */
    CLAY_15(15504, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 11556, Skills.MINING),
    /**
     * Clay 16 skilling resource.
     */
    CLAY_16(15503, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 11555, Skills.MINING),
    /**
     * Clay 17 skilling resource.
     */
    CLAY_17(20449, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 20443, Skills.MINING),
    /**
     * Clay 18 skilling resource.
     */
    CLAY_18(20450, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 20444, Skills.MINING),
    /**
     * Clay 19 skilling resource.
     */
    CLAY_19(20409, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 20407, Skills.MINING),
    /**
     * Clay 20 skilling resource.
     */
    CLAY_20(32429, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 33400, Skills.MINING),
    /**
     * Clay 21 skilling resource.
     */
    CLAY_21(32430, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 33401, Skills.MINING),
    /**
     * Clay 22 skilling resource.
     */
    CLAY_22(32431, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 33402, Skills.MINING),
    /**
     * Clay 23 skilling resource.
     */
    CLAY_23(31062, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 37639, Skills.MINING),
    /**
     * Clay 24 skilling resource.
     */
    CLAY_24(31063, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 37649, Skills.MINING),
    /**
     * Clay 25 skilling resource.
     */
    CLAY_25(31064, 1, 0.1, 1 | 1 << 16, 5.0, 434, 1, "clay", null, 37650, Skills.MINING),

    /**
     * Limestone 0 skilling resource.
     */
    LIMESTONE_0(4027, 10, 0.2, 10 | 20 << 16, 26.5, 3211, 1, "limestone", null, 12564, Skills.MINING),
    /**
     * Limestone 1 skilling resource.
     */
    LIMESTONE_1(4028, 10, 0.2, 10 | 20 << 16, 26.5, 3211, 1, "limestone", null, 12565, Skills.MINING),
    /**
     * Limestone 2 skilling resource.
     */
    LIMESTONE_2(4029, 10, 0.2, 10 | 20 << 16, 26.5, 3211, 1, "limestone", null, 12566, Skills.MINING),
    /**
     * Limestone 3 skilling resource.
     */
    LIMESTONE_3(4030, 10, 0.2, 10 | 20 << 16, 26.5, 3211, 1, "limestone", null, 12567, Skills.MINING),

    /**
     * The Blurite ore 0.
     */
    BLURITE_ORE_0(33220, 10, 0.2, 10 | 20 << 16, 17.5, 668, 1, "blurite rocks", null, 33222, Skills.MINING),
    /**
     * The Blurite ore 1.
     */
    BLURITE_ORE_1(33221, 10, 0.2, 10 | 20 << 16, 17.5, 668, 1, "blurite rocks", null, 33223, Skills.MINING),

    /**
     * The Iron ore 0.
     */
    IRON_ORE_0(2092, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 450, Skills.MINING),
    /**
     * The Iron ore 1.
     */
    IRON_ORE_1(2093, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 452, Skills.MINING),
    /**
     * The Iron ore 2.
     */
    IRON_ORE_2(4982, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 4994, Skills.MINING),
    /**
     * The Iron ore 3.
     */
    IRON_ORE_3(4983, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 4995, Skills.MINING),
    /**
     * The Iron ore 4.
     */
    IRON_ORE_4(4984, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 4996, Skills.MINING),
    /**
     * The Iron ore 5.
     */
    IRON_ORE_5(6943, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 21296, Skills.MINING),
    /**
     * The Iron ore 6.
     */
    IRON_ORE_6(6944, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 21297, Skills.MINING),
    /**
     * The Iron ore 7.
     */
    IRON_ORE_7(9718, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 32448, Skills.MINING),
    /**
     * The Iron ore 8.
     */
    IRON_ORE_8(9719, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 18954, Skills.MINING),
    /**
     * The Iron ore 9.
     */
    IRON_ORE_9(9717, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 32447, Skills.MINING),
    /**
     * The Iron ore 10.
     */
    IRON_ORE_10(11956, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 11557, Skills.MINING),
    /**
     * The Iron ore 11.
     */
    IRON_ORE_11(11954, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 11555, Skills.MINING),
    /**
     * The Iron ore 12.
     */
    IRON_ORE_12(11955, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 11556, Skills.MINING),
    /**
     * The Iron ore 13.
     */
    IRON_ORE_13(14914, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 14895, Skills.MINING),
    /**
     * The Iron ore 14.
     */
    IRON_ORE_14(14913, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 14894, Skills.MINING),
    /**
     * The Iron ore 15.
     */
    IRON_ORE_15(14858, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 25373, Skills.MINING),
    /**
     * The Iron ore 16.
     */
    IRON_ORE_16(14857, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 25372, Skills.MINING),
    /**
     * The Iron ore 17.
     */
    IRON_ORE_17(14856, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 25371, Skills.MINING),
    /**
     * The Iron ore 18.
     */
    IRON_ORE_18(14900, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 14894, Skills.MINING),
    /**
     * The Iron ore 19.
     */
    IRON_ORE_19(14901, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 14895, Skills.MINING),
    /**
     * The Iron ore 20.
     */
    IRON_ORE_20(20423, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 20444, Skills.MINING),
    /**
     * The Iron ore 21.
     */
    IRON_ORE_21(20422, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 20443, Skills.MINING),
    /**
     * The Iron ore 22.
     */
    IRON_ORE_22(20425, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 20407, Skills.MINING),
    /**
     * The Iron ore 23.
     */
    IRON_ORE_23(20424, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 20445, Skills.MINING),
    /**
     * The Iron ore 24.
     */
    IRON_ORE_24(19002, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 19005, Skills.MINING),
    /**
     * The Iron ore 25.
     */
    IRON_ORE_25(19001, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 19004, Skills.MINING),
    /**
     * The Iron ore 26.
     */
    IRON_ORE_26(19000, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 19003, Skills.MINING),
    /**
     * The Iron ore 27.
     */
    IRON_ORE_27(21281, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 21296, Skills.MINING),
    /**
     * The Iron ore 28.
     */
    IRON_ORE_28(21283, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 21298, Skills.MINING),
    /**
     * The Iron ore 29.
     */
    IRON_ORE_29(21282, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 21297, Skills.MINING),
    /**
     * The Iron ore 30.
     */
    IRON_ORE_30(29221, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 29218, Skills.MINING),
    /**
     * The Iron ore 31.
     */
    IRON_ORE_31(29223, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 29220, Skills.MINING),
    /**
     * The Iron ore 32.
     */
    IRON_ORE_32(29222, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 29219, Skills.MINING),
    /**
     * The Iron ore 33.
     */
    IRON_ORE_33(32441, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 33400, Skills.MINING),
    /**
     * The Iron ore 34.
     */
    IRON_ORE_34(32443, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 33402, Skills.MINING),
    /**
     * The Iron ore 35.
     */
    IRON_ORE_35(32442, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 33401, Skills.MINING),
    /**
     * The Iron ore 36.
     */
    IRON_ORE_36(32452, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 32448, Skills.MINING),
    /**
     * The Iron ore 37.
     */
    IRON_ORE_37(32451, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 32447, Skills.MINING),
    /**
     * The Iron ore 38.
     */
    IRON_ORE_38(31073, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 37650, Skills.MINING),
    /**
     * The Iron ore 39.
     */
    IRON_ORE_39(31072, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 37649, Skills.MINING),
    /**
     * The Iron ore 40.
     */
    IRON_ORE_40(31071, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 37639, Skills.MINING),
    /**
     * The Iron ore 41.
     */
    IRON_ORE_41(37307, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 11552, Skills.MINING),
    /**
     * The Iron ore 42.
     */
    IRON_ORE_42(37309, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 11554, Skills.MINING),
    /**
     * The Iron ore 43.
     */
    IRON_ORE_43(37308, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 11553, Skills.MINING),
    /**
     * The Iron ore 49.
     */
    IRON_ORE_49(42034, 15, 0.2, 15 | 25 << 16, 35.0, 440, 1, "iron rocks", null, 450, Skills.MINING),

    /**
     * The Silver ore 0.
     */
    SILVER_ORE_0(2101, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 452, Skills.MINING),
    /**
     * The Silver ore 1.
     */
    SILVER_ORE_1(2100, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 450, Skills.MINING),
    /**
     * The Silver ore 2.
     */
    SILVER_ORE_2(6945, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 21296, Skills.MINING),
    /**
     * The Silver ore 3.
     */
    SILVER_ORE_3(6946, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 21297, Skills.MINING),
    /**
     * The Silver ore 4.
     */
    SILVER_ORE_4(9716, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 18954, Skills.MINING),
    /**
     * The Silver ore 5.
     */
    SILVER_ORE_5(9714, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 32447, Skills.MINING),
    /**
     * The Silver ore 6.
     */
    SILVER_ORE_6(9715, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 32448, Skills.MINING),
    /**
     * The Silver ore 7.
     */
    SILVER_ORE_7(11188, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 21298, Skills.MINING),
    /**
     * The Silver ore 8.
     */
    SILVER_ORE_8(11186, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 21296, Skills.MINING),
    /**
     * The Silver ore 9.
     */
    SILVER_ORE_9(11187, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 21297, Skills.MINING),
    /**
     * The Silver ore 10.
     */
    SILVER_ORE_10(15581, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 14834, Skills.MINING),
    /**
     * The Silver ore 11.
     */
    SILVER_ORE_11(15580, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 14833, Skills.MINING),
    /**
     * The Silver ore 12.
     */
    SILVER_ORE_12(15579, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 14832, Skills.MINING),
    /**
     * The Silver ore 13.
     */
    SILVER_ORE_13(16998, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 14915, Skills.MINING),
    /**
     * The Silver ore 14.
     */
    SILVER_ORE_14(16999, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 14916, Skills.MINING),
    /**
     * The Silver ore 15.
     */
    SILVER_ORE_15(17007, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 14915, Skills.MINING),
    /**
     * The Silver ore 16.
     */
    SILVER_ORE_16(17000, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 31061, Skills.MINING),
    /**
     * The Silver ore 17.
     */
    SILVER_ORE_17(17009, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 31061, Skills.MINING),
    /**
     * The Silver ore 18.
     */
    SILVER_ORE_18(17008, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 14916, Skills.MINING),
    /**
     * The Silver ore 19.
     */
    SILVER_ORE_19(17385, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 32447, Skills.MINING),
    /**
     * The Silver ore 20.
     */
    SILVER_ORE_20(17387, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 18954, Skills.MINING),
    /**
     * The Silver ore 21.
     */
    SILVER_ORE_21(17386, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 32448, Skills.MINING),
    /**
     * The Silver ore 22.
     */
    SILVER_ORE_22(29225, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 29219, Skills.MINING),
    /**
     * The Silver ore 23.
     */
    SILVER_ORE_23(29224, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 29218, Skills.MINING),
    /**
     * The Silver ore 24.
     */
    SILVER_ORE_24(29226, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 29220, Skills.MINING),
    /**
     * The Silver ore 25.
     */
    SILVER_ORE_25(32445, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 33401, Skills.MINING),
    /**
     * The Silver ore 26.
     */
    SILVER_ORE_26(32444, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 33400, Skills.MINING),
    /**
     * The Silver ore 27.
     */
    SILVER_ORE_27(32446, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 33402, Skills.MINING),
    /**
     * The Silver ore 28.
     */
    SILVER_ORE_28(31075, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 37649, Skills.MINING),
    /**
     * The Silver ore 29.
     */
    SILVER_ORE_29(31074, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 37639, Skills.MINING),
    /**
     * The Silver ore 30.
     */
    SILVER_ORE_30(31076, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 37650, Skills.MINING),
    /**
     * The Silver ore 31.
     */
    SILVER_ORE_31(37305, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 11553, Skills.MINING),
    /**
     * The Silver ore 32.
     */
    SILVER_ORE_32(37304, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 11552, Skills.MINING),
    /**
     * The Silver ore 33.
     */
    SILVER_ORE_33(37306, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 11554, Skills.MINING),
    /**
     * The Silver ore 34.
     */
    SILVER_ORE_34(37670, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 11552, Skills.MINING),
    /**
     * The Silver ore 35.
     */
    SILVER_ORE_35(11948, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 11555, Skills.MINING),
    /**
     * The Silver ore 36.
     */
    SILVER_ORE_36(11949, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 11556, Skills.MINING),
    /**
     * The Silver ore 37.
     */
    SILVER_ORE_37(11950, 20, 0.3, 100 | 200 << 16, 40.0, 442, 1, "silver rocks", null, 11557, Skills.MINING),

    /**
     * Coal 0 skilling resource.
     */
    COAL_0(2097, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 452, Skills.MINING),
    /**
     * Coal 1 skilling resource.
     */
    COAL_1(2096, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 450, Skills.MINING),
    /**
     * Coal 2 skilling resource.
     */
    COAL_2(4985, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 4994, Skills.MINING),
    /**
     * Coal 3 skilling resource.
     */
    COAL_3(4986, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 4995, Skills.MINING),
    /**
     * Coal 4 skilling resource.
     */
    COAL_4(4987, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 4996, Skills.MINING),
    /**
     * Coal 5 skilling resource.
     */
    COAL_5(4676, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 450, Skills.MINING),
    /**
     * Coal 6 skilling resource.
     */
    COAL_6(10948, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 10944, Skills.MINING),
    /**
     * Coal 7 skilling resource.
     */
    COAL_7(11964, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 11556, Skills.MINING),
    /**
     * Coal 8 skilling resource.
     */
    COAL_8(11965, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 11557, Skills.MINING),
    /**
     * Coal 9 skilling resource.
     */
    COAL_9(11963, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 11555, Skills.MINING),
    /**
     * Coal 10 skilling resource.
     */
    COAL_10(11932, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 11554, Skills.MINING),
    /**
     * Coal 11 skilling resource.
     */
    COAL_11(11930, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 11552, Skills.MINING),
    /**
     * Coal 12 skilling resource.
     */
    COAL_12(11931, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 11553, Skills.MINING),
    /**
     * Coal 13 skilling resource.
     */
    COAL_13(15246, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 15249, Skills.MINING),
    /**
     * Coal 14 skilling resource.
     */
    COAL_14(15247, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 15250, Skills.MINING),
    /**
     * Coal 15 skilling resource.
     */
    COAL_15(15248, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 15251, Skills.MINING),
    /**
     * Coal 16 skilling resource.
     */
    COAL_16(14852, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 25373, Skills.MINING),
    /**
     * Coal 17 skilling resource.
     */
    COAL_17(14851, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 25372, Skills.MINING),
    /**
     * Coal 18 skilling resource.
     */
    COAL_18(14850, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 25371, Skills.MINING),
    /**
     * Coal 19 skilling resource.
     */
    COAL_19(20410, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 20443, Skills.MINING),
    /**
     * Coal 20 skilling resource.
     */
    COAL_20(20411, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 20444, Skills.MINING),
    /**
     * Coal 21 skilling resource.
     */
    COAL_21(20412, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 20445, Skills.MINING),
    /**
     * Coal 22 skilling resource.
     */
    COAL_22(20413, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 20407, Skills.MINING),
    /**
     * Coal 23 skilling resource.
     */
    COAL_23(18999, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 19005, Skills.MINING),
    /**
     * Coal 24 skilling resource.
     */
    COAL_24(18998, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 19004, Skills.MINING),
    /**
     * Coal 25 skilling resource.
     */
    COAL_25(18997, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 19003, Skills.MINING),
    /**
     * Coal 26 skilling resource.
     */
    COAL_26(21287, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 21296, Skills.MINING),
    /**
     * Coal 27 skilling resource.
     */
    COAL_27(21289, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 21298, Skills.MINING),
    /**
     * Coal 28 skilling resource.
     */
    COAL_28(21288, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 21297, Skills.MINING),
    /**
     * Coal 29 skilling resource.
     */
    COAL_29(23565, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 21298, Skills.MINING),
    /**
     * Coal 30 skilling resource.
     */
    COAL_30(23564, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 21297, Skills.MINING),
    /**
     * Coal 31 skilling resource.
     */
    COAL_31(23563, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 21296, Skills.MINING),
    /**
     * Coal 32 skilling resource.
     */
    COAL_32(29215, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 29218, Skills.MINING),
    /**
     * Coal 33 skilling resource.
     */
    COAL_33(29217, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 29220, Skills.MINING),
    /**
     * Coal 34 skilling resource.
     */
    COAL_34(29216, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 29219, Skills.MINING),
    /**
     * Coal 35 skilling resource.
     */
    COAL_35(32426, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 33400, Skills.MINING),
    /**
     * Coal 36 skilling resource.
     */
    COAL_36(32427, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 33401, Skills.MINING),
    /**
     * Coal 37 skilling resource.
     */
    COAL_37(32428, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 33402, Skills.MINING),
    /**
     * Coal 38 skilling resource.
     */
    COAL_38(32450, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 32448, Skills.MINING),
    /**
     * Coal 39 skilling resource.
     */
    COAL_39(32449, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 32447, Skills.MINING),
    /**
     * Coal 40 skilling resource.
     */
    COAL_40(31068, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 37639, Skills.MINING),
    /**
     * Coal 41 skilling resource.
     */
    COAL_41(31069, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 37649, Skills.MINING),
    /**
     * Coal 42 skilling resource.
     */
    COAL_42(31070, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 37650, Skills.MINING),
    /**
     * Coal 43 skilling resource.
     */
    COAL_43(31168, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 14833, Skills.MINING),
    /**
     * Coal 44 skilling resource.
     */
    COAL_44(31169, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 14834, Skills.MINING),
    /**
     * Coal 45 skilling resource.
     */
    COAL_45(31167, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 14832, Skills.MINING),
    /**
     * Coal 46 skilling resource.
     */
    COAL_46(37699, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 21298, Skills.MINING),
    /**
     * Coal 47 skilling resource.
     */
    COAL_47(37698, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 21297, Skills.MINING),
    /**
     * Coal 48 skilling resource.
     */
    COAL_48(37697, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 21296, Skills.MINING),
    /**
     * Coal 49 skilling resource.
     */
    COAL_49(42035, 30, 0.4, 50 | 100 << 16, 50.0, 453, 1, "coal", null, 452, Skills.MINING),

    /**
     * Sandstone skilling resource.
     */
    SANDSTONE(10946, 35, 0.2, 30 | 60 << 16, 30.0, 6971, 1, "sandstone", null, 10944, Skills.MINING),

    /**
     * The Gold ore 0.
     */
    GOLD_ORE_0(2099, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 452, Skills.MINING),
    /**
     * The Gold ore 1.
     */
    GOLD_ORE_1(2098, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 450, Skills.MINING),
    /**
     * The Gold ore 2.
     */
    GOLD_ORE_2(2611, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 21298, Skills.MINING),
    /**
     * The Gold ore 3.
     */
    GOLD_ORE_3(2610, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 21297, Skills.MINING),
    /**
     * The Gold ore 4.
     */
    GOLD_ORE_4(2609, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 21296, Skills.MINING),
    /**
     * The Gold ore 5.
     */
    GOLD_ORE_5(9722, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 18954, Skills.MINING),
    /**
     * The Gold ore 6.
     */
    GOLD_ORE_6(9720, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 32447, Skills.MINING),
    /**
     * The Gold ore 7.
     */
    GOLD_ORE_7(9721, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 32448, Skills.MINING),
    /**
     * The Gold ore 8.
     */
    GOLD_ORE_8(11183, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 21296, Skills.MINING),
    /**
     * The Gold ore 9.
     */
    GOLD_ORE_9(11184, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 21297, Skills.MINING),
    /**
     * The Gold ore 10.
     */
    GOLD_ORE_10(11185, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 21298, Skills.MINING),
    /**
     * The Gold ore 11.
     */
    GOLD_ORE_11(11952, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 11556, Skills.MINING),
    /**
     * The Gold ore 12.
     */
    GOLD_ORE_12(11953, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 11557, Skills.MINING),
    /**
     * The Gold ore 13.
     */
    GOLD_ORE_13(11951, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 11555, Skills.MINING),
    /**
     * The Gold ore 14.
     */
    GOLD_ORE_14(15578, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 14834, Skills.MINING),
    /**
     * The Gold ore 15.
     */
    GOLD_ORE_15(15577, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 14833, Skills.MINING),
    /**
     * The Gold ore 16.
     */
    GOLD_ORE_16(15576, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 14832, Skills.MINING),
    /**
     * The Gold ore 17.
     */
    GOLD_ORE_17(17002, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 14916, Skills.MINING),
    /**
     * The Gold ore 18.
     */
    GOLD_ORE_18(17003, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 31061, Skills.MINING),
    /**
     * The Gold ore 19.
     */
    GOLD_ORE_19(17001, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 14915, Skills.MINING),
    /**
     * The Gold ore 20.
     */
    GOLD_ORE_20(21291, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 21297, Skills.MINING),
    /**
     * The Gold ore 21.
     */
    GOLD_ORE_21(21290, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 21296, Skills.MINING),
    /**
     * The Gold ore 22.
     */
    GOLD_ORE_22(21292, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 21298, Skills.MINING),
    /**
     * The Gold ore 23.
     */
    GOLD_ORE_23(32433, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 33401, Skills.MINING),
    /**
     * The Gold ore 24.
     */
    GOLD_ORE_24(32432, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 33400, Skills.MINING),
    /**
     * The Gold ore 25.
     */
    GOLD_ORE_25(32434, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 33402, Skills.MINING),
    /**
     * The Gold ore 26.
     */
    GOLD_ORE_26(31065, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 37639, Skills.MINING),
    /**
     * The Gold ore 27.
     */
    GOLD_ORE_27(31066, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 37649, Skills.MINING),
    /**
     * The Gold ore 28.
     */
    GOLD_ORE_28(31067, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 37650, Skills.MINING),
    /**
     * The Gold ore 29.
     */
    GOLD_ORE_29(37311, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 11553, Skills.MINING),
    /**
     * The Gold ore 30.
     */
    GOLD_ORE_30(37310, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 11552, Skills.MINING),
    /**
     * The Gold ore 31.
     */
    GOLD_ORE_31(37312, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 11554, Skills.MINING),
    /**
     * The Gold ore 32.
     */
    GOLD_ORE_32(37471, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 15249, Skills.MINING),
    /**
     * The Gold ore 33.
     */
    GOLD_ORE_33(37473, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 15251, Skills.MINING),
    /**
     * The Gold ore 34.
     */
    GOLD_ORE_34(37472, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 15250, Skills.MINING),
    /**
     * The Gold ore 49.
     */
    GOLD_ORE_49(42033, 40, 0.6, 100 | 200 << 16, 65.0, 444, 1, "gold rocks", null, 452, Skills.MINING),

    /**
     * Granite skilling resource.
     */
    GRANITE(10947, 45, 0.2, 10 | 20 << 16, 50.0, 6979, 1, "granite", null, 10945, Skills.MINING),

    /**
     * Rubium skilling resource.
     */
    RUBIUM(29746, 46, 0.6, 50 | 100 << 16, 17.5, 12630, 1, "rubium", null, 29747, Skills.MINING),

    /**
     * The Mithril ore 0.
     */
    MITHRIL_ORE_0(2103, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 452, Skills.MINING),
    /**
     * The Mithril ore 1.
     */
    MITHRIL_ORE_1(2102, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 450, Skills.MINING),
    /**
     * The Mithril ore 2.
     */
    MITHRIL_ORE_2(4988, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 4994, Skills.MINING),
    /**
     * The Mithril ore 3.
     */
    MITHRIL_ORE_3(4989, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 4995, Skills.MINING),
    /**
     * The Mithril ore 4.
     */
    MITHRIL_ORE_4(4990, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 4996, Skills.MINING),
    /**
     * The Mithril ore 5.
     */
    MITHRIL_ORE_5(11943, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 11553, Skills.MINING),
    /**
     * The Mithril ore 6.
     */
    MITHRIL_ORE_6(11942, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 11552, Skills.MINING),
    /**
     * The Mithril ore 7.
     */
    MITHRIL_ORE_7(11945, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 11555, Skills.MINING),
    /**
     * The Mithril ore 8.
     */
    MITHRIL_ORE_8(11944, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 11554, Skills.MINING),
    /**
     * The Mithril ore 9.
     */
    MITHRIL_ORE_9(11947, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 11557, Skills.MINING),
    /**
     * The Mithril ore 10.
     */
    MITHRIL_ORE_10(11946, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 11556, Skills.MINING),
    /**
     * The Mithril ore 11.
     */
    MITHRIL_ORE_11(14855, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 25373, Skills.MINING),
    /**
     * The Mithril ore 12.
     */
    MITHRIL_ORE_12(14854, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 25372, Skills.MINING),
    /**
     * The Mithril ore 13.
     */
    MITHRIL_ORE_13(14853, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 25371, Skills.MINING),
    /**
     * The Mithril ore 14.
     */
    MITHRIL_ORE_14(16687, 50, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 450, Skills.MINING),
    /**
     * The Mithril ore 15.
     */
    MITHRIL_ORE_15(20421, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 20407, Skills.MINING),
    /**
     * The Mithril ore 16.
     */
    MITHRIL_ORE_16(20420, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 20445, Skills.MINING),
    /**
     * The Mithril ore 17.
     */
    MITHRIL_ORE_17(20419, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 20444, Skills.MINING),
    /**
     * The Mithril ore 18.
     */
    MITHRIL_ORE_18(20418, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 20443, Skills.MINING),
    /**
     * The Mithril ore 19.
     */
    MITHRIL_ORE_19(19012, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 19021, Skills.MINING),
    /**
     * The Mithril ore 20.
     */
    MITHRIL_ORE_20(19013, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 19016, Skills.MINING),
    /**
     * The Mithril ore 21.
     */
    MITHRIL_ORE_21(19014, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 19017, Skills.MINING),
    /**
     * The Mithril ore 22.
     */
    MITHRIL_ORE_22(21278, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 21296, Skills.MINING),
    /**
     * The Mithril ore 23.
     */
    MITHRIL_ORE_23(21279, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 21297, Skills.MINING),
    /**
     * The Mithril ore 24.
     */
    MITHRIL_ORE_24(21280, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 21298, Skills.MINING),
    /**
     * The Mithril ore 25.
     */
    MITHRIL_ORE_25(25369, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 10586, Skills.MINING),
    /**
     * The Mithril ore 26.
     */
    MITHRIL_ORE_26(25368, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 10585, Skills.MINING),
    /**
     * The Mithril ore 27.
     */
    MITHRIL_ORE_27(25370, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 10587, Skills.MINING),
    /**
     * The Mithril ore 28.
     */
    MITHRIL_ORE_28(29236, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 29218, Skills.MINING),
    /**
     * The Mithril ore 29.
     */
    MITHRIL_ORE_29(29237, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 29219, Skills.MINING),
    /**
     * The Mithril ore 30.
     */
    MITHRIL_ORE_30(29238, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 29220, Skills.MINING),
    /**
     * The Mithril ore 31.
     */
    MITHRIL_ORE_31(32439, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 33401, Skills.MINING),
    /**
     * The Mithril ore 32.
     */
    MITHRIL_ORE_32(32438, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 33400, Skills.MINING),
    /**
     * The Mithril ore 33.
     */
    MITHRIL_ORE_33(32440, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 33402, Skills.MINING),
    /**
     * The Mithril ore 34.
     */
    MITHRIL_ORE_34(31087, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 37649, Skills.MINING),
    /**
     * The Mithril ore 35.
     */
    MITHRIL_ORE_35(31086, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 37639, Skills.MINING),
    /**
     * The Mithril ore 36.
     */
    MITHRIL_ORE_36(31088, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 37650, Skills.MINING),
    /**
     * The Mithril ore 37.
     */
    MITHRIL_ORE_37(31170, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 14832, Skills.MINING),
    /**
     * The Mithril ore 38.
     */
    MITHRIL_ORE_38(31171, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 14833, Skills.MINING),
    /**
     * The Mithril ore 39.
     */
    MITHRIL_ORE_39(31172, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 14834, Skills.MINING),
    /**
     * The Mithril ore 40.
     */
    MITHRIL_ORE_40(37692, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 21296, Skills.MINING),
    /**
     * The Mithril ore 41.
     */
    MITHRIL_ORE_41(37693, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 21297, Skills.MINING),
    /**
     * The Mithril ore 42.
     */
    MITHRIL_ORE_42(37694, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 21298, Skills.MINING),
    /**
     * The Mithril ore 49.
     */
    MITHRIL_ORE_49(42036, 55, 0.70, 200 | 400 << 16, 80.0, 447, 1, "mithril rocks", null, 452, Skills.MINING),

    /**
     * The Adamantite ore 0.
     */
    ADAMANTITE_ORE_0(2105, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 452, Skills.MINING),
    /**
     * The Adamantite ore 1.
     */
    ADAMANTITE_ORE_1(2104, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 450, Skills.MINING),
    /**
     * The Adamantite ore 2.
     */
    ADAMANTITE_ORE_2(4991, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 4994, Skills.MINING),
    /**
     * The Adamantite ore 3.
     */
    ADAMANTITE_ORE_3(4992, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 4995, Skills.MINING),
    /**
     * The Adamantite ore 4.
     */
    ADAMANTITE_ORE_4(4993, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 4996, Skills.MINING),
    /**
     * The Adamantite ore 5.
     */
    ADAMANTITE_ORE_5(11941, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 11554, Skills.MINING),
    /**
     * The Adamantite ore 6.
     */
    ADAMANTITE_ORE_6(11940, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 11553, Skills.MINING),
    /**
     * The Adamantite ore 7.
     */
    ADAMANTITE_ORE_7(11939, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 11552, Skills.MINING),
    /**
     * The Adamantite ore 8.
     */
    ADAMANTITE_ORE_8(14864, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 25373, Skills.MINING),
    /**
     * The Adamantite ore 9.
     */
    ADAMANTITE_ORE_9(14863, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 25372, Skills.MINING),
    /**
     * The Adamantite ore 10.
     */
    ADAMANTITE_ORE_10(14862, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 25371, Skills.MINING),
    /**
     * The Adamantite ore 11.
     */
    ADAMANTITE_ORE_11(20417, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 20407, Skills.MINING),
    /**
     * The Adamantite ore 12.
     */
    ADAMANTITE_ORE_12(20416, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 20445, Skills.MINING),
    /**
     * The Adamantite ore 13.
     */
    ADAMANTITE_ORE_13(20414, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 20443, Skills.MINING),
    /**
     * The Adamantite ore 14.
     */
    ADAMANTITE_ORE_14(20415, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 20444, Skills.MINING),
    /**
     * The Adamantite ore 15.
     */
    ADAMANTITE_ORE_15(19020, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 19017, Skills.MINING),
    /**
     * The Adamantite ore 16.
     */
    ADAMANTITE_ORE_16(19018, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 19021, Skills.MINING),
    /**
     * The Adamantite ore 17.
     */
    ADAMANTITE_ORE_17(19019, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 19016, Skills.MINING),
    /**
     * The Adamantite ore 18.
     */
    ADAMANTITE_ORE_18(21275, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 21296, Skills.MINING),
    /**
     * The Adamantite ore 19.
     */
    ADAMANTITE_ORE_19(21276, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 21297, Skills.MINING),
    /**
     * The Adamantite ore 20.
     */
    ADAMANTITE_ORE_20(21277, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 21298, Skills.MINING),
    /**
     * The Adamantite ore 21.
     */
    ADAMANTITE_ORE_21(29233, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 29218, Skills.MINING),
    /**
     * The Adamantite ore 22.
     */
    ADAMANTITE_ORE_22(29234, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 29219, Skills.MINING),
    /**
     * The Adamantite ore 23.
     */
    ADAMANTITE_ORE_23(29235, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 29220, Skills.MINING),
    /**
     * The Adamantite ore 24.
     */
    ADAMANTITE_ORE_24(32435, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 33400, Skills.MINING),
    /**
     * The Adamantite ore 25.
     */
    ADAMANTITE_ORE_25(32437, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 33402, Skills.MINING),
    /**
     * The Adamantite ore 26.
     */
    ADAMANTITE_ORE_26(32436, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 33401, Skills.MINING),
    /**
     * The Adamantite ore 27.
     */
    ADAMANTITE_ORE_27(31083, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 37639, Skills.MINING),
    /**
     * The Adamantite ore 28.
     */
    ADAMANTITE_ORE_28(31085, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 37650, Skills.MINING),
    /**
     * The Adamantite ore 29.
     */
    ADAMANTITE_ORE_29(31084, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 37649, Skills.MINING),
    /**
     * The Adamantite ore 30.
     */
    ADAMANTITE_ORE_30(31173, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 14832, Skills.MINING),
    /**
     * The Adamantite ore 31.
     */
    ADAMANTITE_ORE_31(31174, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 14833, Skills.MINING),
    /**
     * The Adamantite ore 32.
     */
    ADAMANTITE_ORE_32(31175, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 14834, Skills.MINING),
    /**
     * The Adamantite ore 33.
     */
    ADAMANTITE_ORE_33(37468, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 15249, Skills.MINING),
    /**
     * The Adamantite ore 34.
     */
    ADAMANTITE_ORE_34(37469, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 15250, Skills.MINING),
    /**
     * The Adamantite ore 35.
     */
    ADAMANTITE_ORE_35(37470, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 15251, Skills.MINING),
    /**
     * The Adamantite ore 36.
     */
    ADAMANTITE_ORE_36(37689, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 21296, Skills.MINING),
    /**
     * The Adamantite ore 37.
     */
    ADAMANTITE_ORE_37(37690, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 21297, Skills.MINING),
    /**
     * The Adamantite ore 38.
     */
    ADAMANTITE_ORE_38(37691, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 21298, Skills.MINING),
    /**
     * The Adamantite ore 39.
     */
    ADAMANTITE_ORE_39(42037, 70, 0.85, 400 | 800 << 16, 95.0, 449, 1, "adamant rocks", null, 452, Skills.MINING),

    /**
     * The Runite ore 0.
     */
    RUNITE_ORE_0(2107, 85, 0.95, 1250 | 2500 << 16, 125.0, 451, 1, "runite rocks", null, 452, Skills.MINING),
    /**
     * The Runite ore 1.
     */
    RUNITE_ORE_1(2106, 85, 0.95, 1250 | 2500 << 16, 125.0, 451, 1, "runite rocks", null, 450, Skills.MINING),
    /**
     * The Runite ore 2.
     */
    RUNITE_ORE_2(6669, 85, 0.95, 1250 | 2500 << 16, 125.0, 451, 1, "runite rocks", null, 21296, Skills.MINING),
    /**
     * The Runite ore 3.
     */
    RUNITE_ORE_3(6671, 85, 0.95, 1250 | 2500 << 16, 125.0, 451, 1, "runite rocks", null, 21298, Skills.MINING),
    /**
     * The Runite ore 4.
     */
    RUNITE_ORE_4(6670, 85, 0.95, 1250 | 2500 << 16, 125.0, 451, 1, "runite rocks", null, 21297, Skills.MINING),
    /**
     * The Runite ore 5.
     */
    RUNITE_ORE_5(14861, 85, 0.95, 1250 | 2500 << 16, 125.0, 451, 1, "runite rocks", null, 25373, Skills.MINING),
    /**
     * The Runite ore 6.
     */
    RUNITE_ORE_6(14860, 85, 0.95, 1250 | 2500 << 16, 125.0, 451, 1, "runite rocks", null, 25372, Skills.MINING),
    /**
     * The Runite ore 7.
     */
    RUNITE_ORE_7(14859, 85, 0.95, 1250 | 2500 << 16, 125.0, 451, 1, "runite rocks", null, 25371, Skills.MINING),
    /**
     * The Runite ore 8.
     */
    RUNITE_ORE_8(33079, 85, 0.95, 1250 | 2500 << 16, 125.0, 451, 1, "runite rocks", null, 33401, Skills.MINING),
    /**
     * The Runite ore 9.
     */
    RUNITE_ORE_9(33078, 85, 0.95, 1250 | 2500 << 16, 125.0, 451, 1, "runite rocks", null, 33400, Skills.MINING),
    /**
     * The Runite ore 10.
     */
    RUNITE_ORE_10(37208, 85, 0.95, 1250 | 2500 << 16, 125.0, 451, 1, "runite rocks", null, 21296, Skills.MINING),
    /**
     * The Runite ore 11.
     */
    RUNITE_ORE_11(37465, 85, 0.95, 1250 | 2500 << 16, 125.0, 451, 1, "runite rocks", null, 15249, Skills.MINING),
    /**
     * The Runite ore 12.
     */
    RUNITE_ORE_12(37466, 85, 0.95, 1250 | 2500 << 16, 125.0, 451, 1, "runite rocks", null, 15250, Skills.MINING),
    /**
     * The Runite ore 13.
     */
    RUNITE_ORE_13(37467, 85, 0.95, 1250 | 2500 << 16, 125.0, 451, 1, "runite rocks", null, 15251, Skills.MINING),
    /**
     * The Runite ore 14.
     */
    RUNITE_ORE_14(37695, 85, 0.95, 1250 | 2500 << 16, 125.0, 451, 1, "runite rocks", null, 21297, Skills.MINING),
    /**
     * The Runite ore 15.
     */
    RUNITE_ORE_15(37696, 85, 0.95, 1250 | 2500 << 16, 125.0, 451, 1, "runite rocks", null, 21298, Skills.MINING),

    /**
     * The Gem rock 0.
     */
    GEM_ROCK_0(23567, 40, 0.95, 166 | 175 << 16, 65, 1625, 1, "gem rocks", null, 21297, Skills.MINING),
    /**
     * The Gem rock 1.
     */
    GEM_ROCK_1(23566, 40, 0.95, 166 | 175 << 16, 65, 1625, 1, "gem rocks", null, 21296, Skills.MINING),
    /**
     * The Gem rock 2.
     */
    GEM_ROCK_2(23568, 40, 0.95, 166 | 175 << 16, 65, 1625, 1, "gem rocks", null, 21298, Skills.MINING);
    private static final Map<Integer, SkillingResource> consts = new HashMap<>();
    static {
        for (SkillingResource resource : SkillingResource.values()) {
            if (consts.containsKey(resource.id)) {
            }
            consts.put(resource.id, resource);
        }
    }
    private final int id;
    private final int level;
    private final double rate;
    private final int respawnRate;
    private final double experience;
    private final int reward;
    private final int rewardAmount;
    private final String name;
    private final Animation animation;
    private final int emptyId;
    private final int skillId;
    private final boolean farming;

    SkillingResource(int id, int level, double rate, int respawnRate, double experience, int reward, int rewardAmount, String name, Animation animation, int emptyId, int skillId) {
        this.id = id;
        this.level = level;
        this.rate = rate;
        this.respawnRate = respawnRate;
        this.experience = experience;
        this.reward = reward;
        this.rewardAmount = rewardAmount;
        this.name = name;
        this.animation = animation;
        this.emptyId = emptyId;
        this.skillId = skillId;
        this.farming = false;
    }

    SkillingResource(int id, int level, double rate, int respawnRate, double experience, int reward, int rewardAmount, String name, Animation animation, int emptyId, int skillId, boolean farming) {
        this.id = id;
        this.level = level;
        this.rate = rate;
        this.respawnRate = respawnRate;
        this.experience = experience;
        this.reward = reward;
        this.rewardAmount = rewardAmount;
        this.name = name;
        this.animation = animation;
        this.emptyId = emptyId;
        this.skillId = skillId;
        this.farming = farming;
    }

    /**
     * For id skilling resource.
     *
     * @param id the id
     * @return the skilling resource
     */
    public static SkillingResource forId(int id) {
        return consts.get(id);
    }

    /**
     * Is empty boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public static boolean isEmpty(int id) {
        for (SkillingResource r : SkillingResource.values()) {
            if (r.getEmptyId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets respawn duration.
     *
     * @return the respawn duration
     */
    public int getRespawnDuration() {
        int minimum = respawnRate & 0xFFFF;
        int maximum = (respawnRate >> 16) & 0xFFFF;
        double playerRatio = (double) ServerConstants.MAX_PLAYERS / Repository.getPlayers().size();
        return (int) (minimum + ((maximum - minimum) / playerRatio));
    }

    /**
     * Gets maximum respawn.
     *
     * @return the maximum respawn
     */
    public int getMaximumRespawn() {
        return (respawnRate >> 16) & 0xFFFF;
    }

    /**
     * Gets minimum respawn.
     *
     * @return the minimum respawn
     */
    public int getMinimumRespawn() {
        return respawnRate & 0xFFFF;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets rate.
     *
     * @return the rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * Gets respawn rate.
     *
     * @return the respawn rate
     */
    public int getRespawnRate() {
        return respawnRate;
    }

    /**
     * Gets experience.
     *
     * @return the experience
     */
    public double getExperience() {
        return experience;
    }

    /**
     * Gets reward.
     *
     * @return the reward
     */
    public int getReward() {
        return reward;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets animation.
     *
     * @return the animation
     */
    public Animation getAnimation() {
        return animation;
    }

    /**
     * Gets empty id.
     *
     * @return the empty id
     */
    public int getEmptyId() {
        return emptyId;
    }

    /**
     * Gets skill id.
     *
     * @return the skill id
     */
    public int getSkillId() {
        return skillId;
    }

    /**
     * Gets reward amount.
     *
     * @return the reward amount
     */
    public int getRewardAmount() {
        return rewardAmount;
    }

    /**
     * Is farming boolean.
     *
     * @return the boolean
     */
    public boolean isFarming() {
        return farming;
    }
}