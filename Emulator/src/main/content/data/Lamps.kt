package content.data

import cfg.consts.Items
import core.game.node.item.Item

/**
 * Represents an experience lamp.
 * @author Vexia
 *
 * @param item The item ID of the lamp.
 * @param experience The amount of experience gained from using the lamp.
 * @param levelRequired The minimum level required to use the lamp.
 */
enum class Lamps(val item: Int, val experience: Int, val levelRequired: Int = 0) {
    /**
     * The genie reward lamp.
     */
    GENIE_LAMP(
        item = Items.LAMP_2528,
        experience = 10
    ),

    /**
     * The stronghold reward lamp.
     */
    STRONGHOLD_LAMP(
        item = Items.ANTIQUE_LAMP_4447,
        experience = 500
    ),

    /**
     * The blessed reward lamp.
     */
    BLESSED_LAMP(
        item = Items.BLESSED_LAMP_10889,
        experience = 5000,
        levelRequired = 30
    ),

    /**
     * The unassigned lamp 0.
     */
    UNASSIGNED_0(
        item = Items.ANTIQUE_LAMP_11189,
        experience = 1
    ),

    /**
     * The unassigned lamp 1.
     */
    UNASSIGNED_1(
        item = Items.ANTIQUE_LAMP_12627,
        experience = 1
    ),

    /**
     * The combat reward lamp.
     */
    COMBAT_LAMP(
        item = Items.COMBAT_LAMP_10586,
        experience = 7000
    ),

    /**
     * The dreamy reward lamp.
     */
    DREAMY_LAMP(
        item = Items.DREAMY_LAMP_11157,
        experience = 15000
    ),

    /**
     * The mysterious reward lamp.
     */
    MYSTERIOUS_LAMP(
        Items.MYSTERIOUS_LAMP_13227,
        experience = 10000,
        levelRequired = 30
    ),

    /**
     * The quest reward lamp (1).
     */
    QUEST_REWARD_LAMP_1(
        item = Items.ANTIQUE_LAMP_7498,
        experience = 2500,
        levelRequired = 30
    ),

    /**
     * The quest reward lamp (2).
     */
    QUEST_REWARD_LAMP_2(
        item = Items.ANTIQUE_LAMP_13446,
        experience = 600,
        levelRequired = 1
    ),

    /**
     * The quest reward lamp (3).
     */
    QUEST_REWARD_LAMP_3(
        item = Items.ANTIQUE_LAMP_13447,
        experience = 5000,
        levelRequired = 30
    ),

    /**
     * The quest reward lamp (4).
     */
    QUEST_REWARD_LAMP_4(
        item = Items.ANTIQUE_LAMP_13448,
        experience = 7000,
        levelRequired = 50
    ),

    /**
     * The quest reward lamp (5).
     */
    QUEST_REWARD_LAMP_5(
        item = Items.ANTIQUE_LAMP_13463,
        experience = 20000,
        50
    ),

    /**
     * The Karamja achievement lamp reward (easy).
     */
    K_ACHIEVEMENT_1(
        item = Items.ANTIQUE_LAMP_11137,
        experience = 1000,
        levelRequired = 30
    ),

    /**
     * The Karamja achievement lamp reward (medium).
     */
    K_ACHIEVEMENT_2(
        item = Items.ANTIQUE_LAMP_11139,
        experience = 5000,
        levelRequired = 40
    ),

    /**
     * The Karamja achievement lamp reward (hard).
     */
    K_ACHIEVEMENT_3(
        item = Items.ANTIQUE_LAMP_11141,
        experience = 10000,
        levelRequired = 50
    ),

    /**
     * The Varrock achievement lamp reward (easy).
     */
    V_ACHIEVEMENT_1(
        item = Items.ANTIQUE_LAMP_11753,
        experience = 1000,
        levelRequired = 30
    ),

    /**
     * The Varrock achievement lamp reward (medium).
     */
    V_ACHIEVEMENT_2(
        item = Items.ANTIQUE_LAMP_11754,
        experience = 5000,
        levelRequired = 40
    ),

    /**
     * The Varrock achievement lamp reward (hard).
     */
    V_ACHIEVEMENT_3(
        item = Items.ANTIQUE_LAMP_11755,
        experience = 10000,
        levelRequired = 50
    ),

    /**
     * The Lumbridge achievement lamp reward (easy).
     */
    L_ACHIEVEMENT_1(
        item = Items.ANTIQUE_LAMP_11185,
        experience = 500,
        levelRequired = 1
    ),

    /**
     * The Lumbridge achievement lamp reward (medium).
     */
    L_ACHIEVEMENT_2(
        item = Items.ANTIQUE_LAMP_11186,
        experience = 1000,
        levelRequired = 30
    ),

    /**
     * The Lumbridge achievement lamp reward (hard).
     */
    L_ACHIEVEMENT_3(
        item = Items.ANTIQUE_LAMP_11187,
        experience = 1500,
        levelRequired = 35
    ),

    /**
     * The Falador achievement lamp reward (easy).
     */
    FALLY_ACHIEVEMENT_1(
        item = Items.ANTIQUE_LAMP_14580,
        experience = 1000,
        levelRequired = 30
    ),

    /**
     * The Falador achievement lamp reward (medium).
     */
    FALLY_ACHIEVEMENT_2(
        item = Items.ANTIQUE_LAMP_14581,
        experience = 5000,
        levelRequired = 40
    ),

    /**
     * The Falador achievement lamp reward (hard).
     */
    FALLY_ACHIEVEMENT_3(
        item = Items.ANTIQUE_LAMP_14582,
        experience = 10000,
        levelRequired = 50
    ),

    /**
     * The Fremennik Province achievement lamp reward (easy).
     */
    FREM_ACHIEVEMENT_1(
        item = Items.ANTIQUE_LAMP_14574,
        experience = 5000,
        levelRequired = 30
    ),

    /**
     * The Fremennik Province achievement lamp reward (medium).
     */
    FREM_ACHIEVEMENT_2(
        item = Items.ANTIQUE_LAMP_14575,
        experience = 10000,
        levelRequired = 40
    ),

    /**
     * The Fremennik Province achievement lamp reward (hard).
     */
    FREM_ACHIEVEMENT_3(
        item = Items.ANTIQUE_LAMP_14576,
        experience = 15000,
        levelRequired = 50
    ),

    /**
     * The Seers achievement lamp reward (easy).
     */
    SEERS_ACHIEVEMENT_1(
        item = Items.ANTIQUE_LAMP_14633,
        experience = 1000,
        levelRequired = 30
    ),

    /**
     * The Seers achievement lamp reward (medium).
     */
    SEERS_ACHIEVEMENT_2(
        item = Items.ANTIQUE_LAMP_14634,
        experience = 5000,
        levelRequired = 40
    ),

    /**
     * The Seers achievement lamp reward (hard).
     */
    SEERS_ACHIEVEMENT_3(
        item = Items.ANTIQUE_LAMP_14635,
        experience = 10000,
        levelRequired = 50
    ),

    /**
     * The exp book (3).
     */
    EXP_BOOK_3(
        item = Items.TOME_OF_XP_3_9656,
        experience = 2000,
        levelRequired = 30
    ),

    /**
     * The exp book (2).
     */
    EXP_BOOK_2(
        item = Items.TOME_OF_XP_2_9657,
        experience = 2000,
        levelRequired = 30
    ),

    /**
     * The exp book (1).
     */
    EXP_BOOK_1(
        item = Items.TOME_OF_XP_1_9658,
        experience = 2000,
        levelRequired = 30
    ),

    /**
     * The exp book 2nd (3).
     */
    EXP_BOOK_2ND_3(
        item = Items.TOME_OF_XP_2ND_ED_3_13160,
        experience = 2500,
        levelRequired = 35
    ),

    /**
     * The exp book 2nd (2).
     */
    EXP_BOOK_2ND_2(
        item = Items.TOME_OF_XP_2ND_ED_2_13161,
        experience = 2500,
        levelRequired = 35
    ),

    /**
     * The exp book 2nd (1).
     */
    EXP_BOOK_2ND_1(
        item = Items.TOME_OF_XP_2ND_ED_1_13162,
        experience = 2500,
        levelRequired = 35
    );

    companion object {
        /**
         * Returns the Lamps enum value for the given item.
         *
         * @param item The item to search for.
         * @return The corresponding Lamps enum value, or null if not found.
         */
        fun forItem(item: Item): Lamps? {
            for (x in values()) {
                if (x.item == item.id) {
                    return x
                }
            }
            return null
        }
    }
}
