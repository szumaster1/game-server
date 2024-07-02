package content.global.skill.gathering.woodcutting;

import core.ServerConstants;
import core.game.world.repository.Repository;

import java.util.HashMap;

/**
 * The enum Woodcutting node.
 */
public enum WoodcuttingNode {
    /**
     * The Standard tree 1.
     */
    STANDARD_TREE_1(1276, 1342, (byte) 1),
    /**
     * Standard tree 2 woodcutting node.
     */
    STANDARD_TREE_2(1277, 1343, (byte) 1),
    /**
     * Standard tree 3 woodcutting node.
     */
    STANDARD_TREE_3(1278, 1342, (byte) 1),
    /**
     * Standard tree 4 woodcutting node.
     */
    STANDARD_TREE_4(1279, 1345, (byte) 1),
    /**
     * Standard tree 5 woodcutting node.
     */
    STANDARD_TREE_5(1280, 1343, (byte) 1),
    /**
     * Standard tree 6 woodcutting node.
     */
    STANDARD_TREE_6(1330, 1341, (byte) 1),
    /**
     * Standard tree 7 woodcutting node.
     */
    STANDARD_TREE_7(1331, 1341, (byte) 1),
    /**
     * Standard tree 8 woodcutting node.
     */
    STANDARD_TREE_8(1332, 1341, (byte) 1),
    /**
     * Standard tree 9 woodcutting node.
     */
    STANDARD_TREE_9(2409, 1342, (byte) 1),
    /**
     * Standard tree 10 woodcutting node.
     */
    STANDARD_TREE_10(3033, 1345, (byte) 1),
    /**
     * Standard tree 11 woodcutting node.
     */
    STANDARD_TREE_11(3034, 1345, (byte) 1),
    /**
     * Standard tree 12 woodcutting node.
     */
    STANDARD_TREE_12(3035, 1347, (byte) 1),
    /**
     * Standard tree 13 woodcutting node.
     */
    STANDARD_TREE_13(3036, 1351, (byte) 1),
    /**
     * Standard tree 14 woodcutting node.
     */
    STANDARD_TREE_14(3879, 3880, (byte) 1),
    /**
     * Standard tree 15 woodcutting node.
     */
    STANDARD_TREE_15(3881, 3880, (byte) 1),
    /**
     * Standard tree 16 woodcutting node.
     */
    STANDARD_TREE_16(3882, 3880, (byte) 1),
    /**
     * Standard tree 17 woodcutting node.
     */
    STANDARD_TREE_17(3883, 3884, (byte) 1),
    /**
     * Standard tree 18 woodcutting node.
     */
    STANDARD_TREE_18(10041, 1342, (byte) 1),
    /**
     * Standard tree 19 woodcutting node.
     */
    STANDARD_TREE_19(14308, 1342, (byte) 1),
    /**
     * Standard tree 20 woodcutting node.
     */
    STANDARD_TREE_20(14309, 1342, (byte) 1),
    /**
     * Standard tree 21 woodcutting node.
     */
    STANDARD_TREE_21(16264, 1342, (byte) 1),
    /**
     * Standard tree 22 woodcutting node.
     */
    STANDARD_TREE_22(16265, 1342, (byte) 1),
    /**
     * Standard tree 23 woodcutting node.
     */
    STANDARD_TREE_23(30132, 1342, (byte) 1),
    /**
     * Standard tree 24 woodcutting node.
     */
    STANDARD_TREE_24(30133, 1342, (byte) 1),
    /**
     * Standard tree 25 woodcutting node.
     */
    STANDARD_TREE_25(37477, 1342, (byte) 1),
    /**
     * Standard tree 26 woodcutting node.
     */
    STANDARD_TREE_26(37478, 37653, (byte) 1),
    /**
     * Standard tree 27 woodcutting node.
     */
    STANDARD_TREE_27(37652, 37653, (byte) 1),

    /**
     * The Dead tree 1.
     */
//Dead trees
    DEAD_TREE_1(1282, 1347, (byte) 2),
    /**
     * Dead tree 2 woodcutting node.
     */
    DEAD_TREE_2(1283, 1347, (byte) 2),
    /**
     * Dead tree 3 woodcutting node.
     */
    DEAD_TREE_3(1284, 1348, (byte) 2),
    /**
     * Dead tree 4 woodcutting node.
     */
    DEAD_TREE_4(1285, 1349, (byte) 2),
    /**
     * Dead tree 5 woodcutting node.
     */
    DEAD_TREE_5(1286, 1351, (byte) 2),
    /**
     * Dead tree 6 woodcutting node.
     */
    DEAD_TREE_6(1289, 1353, (byte) 2),
    /**
     * Dead tree 7 woodcutting node.
     */
    DEAD_TREE_7(1290, 1354, (byte) 2),
    /**
     * Dead tree 8 woodcutting node.
     */
    DEAD_TREE_8(1291, 23054, (byte) 2),
    /**
     * Dead tree 9 woodcutting node.
     */
    DEAD_TREE_9(1365, 1352, (byte) 2),
    /**
     * Dead tree 10 woodcutting node.
     */
    DEAD_TREE_10(1383, 1358, (byte) 2),
    /**
     * Dead tree 11 woodcutting node.
     */
    DEAD_TREE_11(1384, 1359, (byte) 2),
    /**
     * Dead tree 12 woodcutting node.
     */
    DEAD_TREE_12(5902, 1347, (byte) 2),
    /**
     * Dead tree 13 woodcutting node.
     */
    DEAD_TREE_13(5903, 1353, (byte) 2),
    /**
     * Dead tree 14 woodcutting node.
     */
    DEAD_TREE_14(5904, 1353, (byte) 2),
    /**
     * Dead tree 15 woodcutting node.
     */
    DEAD_TREE_15(32294, 1353, (byte) 2),
    /**
     * Dead tree 16 woodcutting node.
     */
    DEAD_TREE_16(37481, 1347, (byte) 2),
    /**
     * Dead tree 17 woodcutting node.
     */
    DEAD_TREE_17(37482, 1351, (byte) 2),
    /**
     * Dead tree 18 woodcutting node.
     */
    DEAD_TREE_18(37483, 1358, (byte) 2),
    /**
     * Dead tree 19 woodcutting node.
     */
    DEAD_TREE_19(24168, 24169, (byte) 2),

    /**
     * Evergreen 1 woodcutting node.
     */
//Evergreen
    EVERGREEN_1(1315, 1342, (byte) 3),
    /**
     * Evergreen 2 woodcutting node.
     */
    EVERGREEN_2(1316, 1355, (byte) 3),
    /**
     * Evergreen 3 woodcutting node.
     */
    EVERGREEN_3(1318, 1355, (byte) 3),
    /**
     * Evergreen 4 woodcutting node.
     */
    EVERGREEN_4(1319, 1355, (byte) 3),

    /**
     * The Jungle tree 1.
     */
//Jungle stuff
    JUNGLE_TREE_1(2887, 0, (byte) 4),
    /**
     * Jungle tree 2 woodcutting node.
     */
    JUNGLE_TREE_2(2889, 0, (byte) 4),
    /**
     * Jungle tree 3 woodcutting node.
     */
    JUNGLE_TREE_3(2890, 0, (byte) 4),
    /**
     * Jungle tree 4 woodcutting node.
     */
    JUNGLE_TREE_4(4818, 0, (byte) 4),
    /**
     * Jungle tree 5 woodcutting node.
     */
    JUNGLE_TREE_5(4820, 0, (byte) 4),

    /**
     * Jungle bush 1 woodcutting node.
     */
    JUNGLE_BUSH_1(2892, 2894, (byte) 5),
    /**
     * Jungle bush 2 woodcutting node.
     */
    JUNGLE_BUSH_2(2893, 2895, (byte) 5),

    /**
     * Achey tree woodcutting node.
     */
//Achey
    ACHEY_TREE(2023, 3371, (byte) 6),

    /**
     * Oak tree 1 woodcutting node.
     */
//Oak
    OAK_TREE_1(1281, 1356, (byte) 7),
    /**
     * Oak tree 2 woodcutting node.
     */
    OAK_TREE_2(3037, 1357, (byte) 7),
    /**
     * Oak tree 3 woodcutting node.
     */
    OAK_TREE_3(37479, 1356, (byte) 7),
    /**
     * Oak tree 4 woodcutting node.
     */
    OAK_TREE_4(8467, 1356, (byte) 19, true),

    /**
     * Willow tree 1 woodcutting node.
     */
//Willow
    WILLOW_TREE_1(1308, 7399, (byte) 8),
    /**
     * Willow tree 2 woodcutting node.
     */
    WILLOW_TREE_2(5551, 5554, (byte) 8),
    /**
     * Willow tree 3 woodcutting node.
     */
    WILLOW_TREE_3(5552, 5554, (byte) 8),
    /**
     * Willow tree 4 woodcutting node.
     */
    WILLOW_TREE_4(5553, 5554, (byte) 8),
    /**
     * Willow tree 5 woodcutting node.
     */
    WILLOW_TREE_5(37480, 7399, (byte) 8),
    /**
     * Willow tree 6 woodcutting node.
     */
    WILLOW_TREE_6(8488, 7399, (byte) 20, true),

    /**
     * Teak 1 woodcutting node.
     */
//Teak
    TEAK_1(9036, 9037, (byte) 9),
    /**
     * Teak 2 woodcutting node.
     */
    TEAK_2(15062, 9037, (byte) 9),

    /**
     * Maple tree 1 woodcutting node.
     */
//Maple
    MAPLE_TREE_1(1307, 7400, (byte) 10),
    /**
     * Maple tree 2 woodcutting node.
     */
    MAPLE_TREE_2(4674, 7400, (byte) 10),
    /**
     * Maple tree 3 woodcutting node.
     */
    MAPLE_TREE_3(8444, 7400, (byte) 21, true),

    /**
     * Hollow tree 1 woodcutting node.
     */
//Hollow
    HOLLOW_TREE_1(2289, 2310, (byte) 11),
    /**
     * Hollow tree 2 woodcutting node.
     */
    HOLLOW_TREE_2(4060, 4061, (byte) 11),

    /**
     * Mahogany woodcutting node.
     */
//Mahogany
    MAHOGANY(9034, 9035, (byte) 12),

    /**
     * The Swaying tree.
     */
//Swaying Tree
    SWAYING_TREE(4142, -1, (byte) 30),

    /**
     * The Arctic pine.
     */
//Arctic pine
    ARCTIC_PINE(21273, 21274, (byte) 13),

    /**
     * Eucalyptus 1 woodcutting node.
     */
//Eucalyptus
    EUCALYPTUS_1(28951, 28954, (byte) 14),
    /**
     * Eucalyptus 2 woodcutting node.
     */
    EUCALYPTUS_2(28952, 28955, (byte) 14),
    /**
     * Eucalyptus 3 woodcutting node.
     */
    EUCALYPTUS_3(28953, 28956, (byte) 14),

    /**
     * Yew woodcutting node.
     */
//Yew
    YEW(1309, 7402, (byte) 15),
    /**
     * Yew 1 woodcutting node.
     */
    YEW_1(8513, 7402, (byte) 22, true),

    /**
     * Magic tree 1 woodcutting node.
     */
//Magic
    MAGIC_TREE_1(1306, 7401, (byte) 16),
    /**
     * Magic tree 2 woodcutting node.
     */
    MAGIC_TREE_2(37823, 37824, (byte) 16),
    /**
     * Magic tree 3 woodcutting node.
     */
    MAGIC_TREE_3(8409, 37824, (byte) 23, true),

    /**
     * The Cursed magic tree.
     */
//Cursed Magic
    CURSED_MAGIC_TREE(37821, 37822, (byte) 17),

    /**
     * Dramen tree woodcutting node.
     */
//Dramen
    DRAMEN_TREE(1292, -1, (byte) 18),

    WINDSWEPT_TREE(18137, 1353, (byte) 19);

    int full,
    empty,
    reward,
    respawnRate,
    level,
    rewardAmount;
    double experience,
    rate;

    public byte identifier;
    boolean farming;
    public double baseLow = 2;
    public double baseHigh = 6;
    public double tierModLow = 1;
    public double tierModHigh = 3;

    WoodcuttingNode(int full, int empty, byte identifier) {
        this.full = full;
        this.empty = empty;
        this.identifier = identifier;
        this.farming = false;
        this.rewardAmount = 1;
        switch (identifier & 0xFF) {
            case 1:
            case 2:
            case 3:
            case 4:
                reward = 1511;
                respawnRate = 50 | 100 << 16;
                rate = 0.05;
                experience = 25.0;
                level = 1;
                baseLow = 64;
                baseHigh = 200;
                tierModLow = 32;
                tierModHigh = 100;
                break;
            case 5:
                reward = 1511;
                respawnRate = 50 | 100 << 16;
                rate = 0.15;
                experience = 100;
                level = 1;
                this.rewardAmount = 2;
                baseLow = 64;
                baseHigh = 200;
                tierModLow = 32;
                tierModHigh = 100;
                break;
            case 6:
                reward = 2862;
                respawnRate = 50 | 100 << 16;
                rate = 0.05;
                experience = 25.0;
                level = 1;
                baseLow = 64;
                baseHigh = 200;
                tierModLow = 32;
                tierModHigh = 100;
                break;
            case 7:
                reward = 1521;
                respawnRate = 14 | 22 << 16;
                rate = 0.15;
                experience = 37.5;
                level = 15;
                rewardAmount = 10;
                baseLow = 32;
                baseHigh = 100;
                tierModLow = 16;
                tierModHigh = 50;
                break;
            case 8:
                reward = 1519;
                respawnRate = 14 | 22 << 16;
                rate = 0.3;
                experience = 67.8;
                level = 30;
                rewardAmount = 20;
                baseLow = 16;
                baseHigh = 50;
                tierModLow = 8;
                tierModHigh = 25;
                break;
            case 9:
                reward = 6333;
                respawnRate = 35 | 60 << 16;
                rate = 0.7;
                experience = 85.0;
                level = 35;
                rewardAmount = 25;
                baseLow = 15;
                baseHigh = 46;
                tierModLow = 8;
                tierModHigh = 23.5;
                break;
            case 10:
                reward = 1517;
                respawnRate = 58 | 100 << 16;
                rate = 0.65;
                experience = 100.0;
                level = 45;
                rewardAmount = 30;
                baseLow = 8;
                baseHigh = 25;
                tierModLow = 4;
                tierModHigh = 12.5;
                break;
            case 11:
                reward = 3239;
                respawnRate = 58 | 100 << 16;
                rate = 0.6;
                experience = 82.5;
                level = 45;
                rewardAmount = 30;
                baseLow = 18;
                baseHigh = 26;
                tierModLow = 10;
                tierModHigh = 14;
                break;
            case 12:
                reward = 6332;
                respawnRate = 62 | 115 << 16;
                rate = 0.7;
                experience = 125.0;
                level = 50;
                rewardAmount = 35;
                baseLow = 8;
                baseHigh = 25;
                tierModLow = 4;
                tierModHigh = 12.5;
                break;
            case 13:
                reward = 10810;
                respawnRate = 75 | 130 << 16;
                rate = 0.73;
                experience = 140.2;
                level = 54;
                rewardAmount = 35;
                baseLow = 6;
                baseHigh = 30;
                tierModLow = 3;
                tierModHigh = 13.5;
                break;
            case 14:
                reward = 12581;
                respawnRate = 80 | 140 << 16;
                rate = 0.77;
                experience = 165.0;
                level = 58;
                rewardAmount = 35;
                break;
            case 15:
                reward = 1515;
                respawnRate = 100 | 162 << 16;
                rate = 0.8;
                experience = 175.0;
                level = 60;
                rewardAmount = 40;
                baseLow = 4;
                baseHigh = 12.5;
                tierModLow = 2;
                tierModHigh = 6.25;
                break;
            case 16:
                reward = 1513;
                respawnRate = 200 | 317 << 16;
                rate = 0.9;
                experience = 250.0;
                level = 75;
                rewardAmount = 50;
                baseLow = 2;
                baseHigh = 6;
                tierModLow = 1;
                tierModHigh = 3;
                break;
            case 17:
                reward = 1513;
                respawnRate = 200 | 317 << 16;
                rate = 0.95;
                experience = 275.0;
                level = 82;
                rewardAmount = 50;
                break;
            case 18:
                reward = 771;
                respawnRate = -1;
                rate = 0.05;
                experience = 25.0;
                level = 36;
                rewardAmount = Integer.MAX_VALUE;
                baseLow = 255;
                baseHigh = 255;
                tierModLow = 0;
                tierModHigh = 0;
                break;
            case 19:
                reward = 11035;
                respawnRate = 50 | 100 << 16;
                rate = 0.05;
                experience = 1;
                level = 50;
                this.rewardAmount = 1;
                break;
            case 30:
                reward = 3692;
                respawnRate = -1;
                rate = 0.05;
                experience = 1;
                level = 40;
                rewardAmount = Integer.MAX_VALUE;
                break;
        }
    }

    WoodcuttingNode(int full, int empty, byte identifier, boolean farming) {
        this.full = full;
        this.empty = empty;
        this.identifier = identifier;
        this.farming = farming;
        switch (identifier & 0xFF) {
            case 19:
                reward = 1521;
                respawnRate = 14 | 22 << 16;
                rate = 0.15;
                experience = 37.5;
                level = 15;
                rewardAmount = 10;
                break;
            case 20:
                reward = 1519;
                respawnRate = 14 | 22 << 16;
                rate = 0.3;
                experience = 67.8;
                level = 30;
                rewardAmount = 20;
                break;
            case 21:
                reward = 1517;
                respawnRate = 58 | 100 << 16;
                rate = 0.65;
                experience = 100.0;
                level = 45;
                rewardAmount = 30;
                break;
            case 22:
                reward = 1515;
                respawnRate = 100 | 162 << 16;
                rate = 0.8;
                experience = 175.0;
                level = 60;
                rewardAmount = 40;
                break;
            case 23:
                reward = 1513;
                respawnRate = 200 | 317 << 16;
                rate = 0.9;
                experience = 250.0;
                level = 75;
                rewardAmount = 50;
                break;
        }
    }

    private static final HashMap<Integer, WoodcuttingNode> NODE_MAP = new HashMap<>();
    private static final HashMap<Integer, Integer> EMPTY_MAP = new HashMap<>();

    static {
        for (WoodcuttingNode node : WoodcuttingNode.values()) {
            NODE_MAP.putIfAbsent(node.full, node);
            EMPTY_MAP.putIfAbsent(node.empty, node.full);
        }
    }

    /**
     * For id woodcutting node.
     *
     * @param id the id
     * @return the woodcutting node
     */
    public static WoodcuttingNode forId(int id) {
        return NODE_MAP.get(id);
    }

    /**
     * Is empty boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public static boolean isEmpty(int id) {
        return EMPTY_MAP.get(id) != null;
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
     * Gets empty id.
     *
     * @return the empty id
     */
    public int getEmptyId() {
        return empty;
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
     * Gets experience.
     *
     * @return the experience
     */
    public double getExperience() {
        return experience;
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
     * Gets rate.
     *
     * @return the rate
     */
    public double getRate() {
        return rate;
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
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return full;
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
     * Gets maximum respawn.
     *
     * @return the maximum respawn
     */
    public int getMaximumRespawn() {
        return (respawnRate >> 16) & 0xFFFF;
    }

    /**
     * Is farming boolean.
     *
     * @return the boolean
     */
    public boolean isFarming() {
        return farming;
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
}
