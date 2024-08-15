package content.data

import core.api.consts.Items
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
     * Genie lamp.
     */
    GENIE_LAMP(item = Items.LAMP_2528, experience = 10),

    /**
     * Stronghold lamp.
     */
    STRONGHOLD_LAMP(item = Items.ANTIQUE_LAMP_4447, experience = 500),

    /**
     * Blessed lamp.
     */
    BLESSED_LAMP(item = Items.BLESSED_LAMP_10889, experience = 5000, levelRequired = 30),

    /**
     * Unassigned lamp 0.
     */
    UNASSIGNED_0(item = Items.ANTIQUE_LAMP_11189, experience = 1),

    /**
     * Unassigned lamp 1.
     */
    UNASSIGNED_1(item = Items.ANTIQUE_LAMP_12627, experience = 1),

    /**
     * Combat lamp.
     */
    COMBAT_LAMP(item = Items.COMBAT_LAMP_10586, experience = 7000),

    /**
     * Dreamy lamp.
     */
    DREAMY_LAMP(item = Items.DREAMY_LAMP_11157, experience = 15000),

    /**
     * Mysterious lamp.
     */
    MYSTERIOUS_LAMP(Items.MYSTERIOUS_LAMP_13227, experience = 10000, levelRequired = 30),

    /**
     * Quest reward lamp 1.
     */
    QUEST_REWARD_LAMP_1(item = Items.ANTIQUE_LAMP_7498, experience = 2500, levelRequired = 30),

    /**
     * Quest reward lamp 2.
     */
    QUEST_REWARD_LAMP_2(item = Items.ANTIQUE_LAMP_13446, experience = 600, levelRequired = 1),

    /**
     * Quest reward lamp 3.
     */
    QUEST_REWARD_LAMP_3(item = Items.ANTIQUE_LAMP_13447, experience = 5000, levelRequired = 30),

    /**
     * Quest reward lamp 4.
     */
    QUEST_REWARD_LAMP_4(item = Items.ANTIQUE_LAMP_13448, experience = 7000, levelRequired = 50),

    /**
     * Quest reward lamp 5.
     */
    QUEST_REWARD_LAMP_5(item = Items.ANTIQUE_LAMP_13463, experience = 20000, 50),

    /**
     * Karamja Achievement lamp 1.
     */
    K_ACHIEVEMENT_1(item = Items.ANTIQUE_LAMP_11137, experience = 1000, levelRequired = 30),

    /**
     * Karamja Achievement lamp 2.
     */
    K_ACHIEVEMENT_2(item = Items.ANTIQUE_LAMP_11139, experience = 5000, levelRequired = 40),

    /**
     * Karamja Achievement lamp 3.
     */
    K_ACHIEVEMENT_3(item = Items.ANTIQUE_LAMP_11141, experience = 10000, levelRequired = 50),

    /**
     * Varrock Achievement lamp 1.
     */
    V_ACHIEVEMENT_1(item = Items.ANTIQUE_LAMP_11753, experience = 1000, levelRequired = 30),

    /**
     * Varrock Achievement lamp 2.
     */
    V_ACHIEVEMENT_2(item = Items.ANTIQUE_LAMP_11754, experience = 5000, levelRequired = 40),

    /**
     * Varrock Achievement lamp 3.
     */
    V_ACHIEVEMENT_3(item = Items.ANTIQUE_LAMP_11755, experience = 10000, levelRequired = 50),

    /**
     * Lumbridge Achievement lamp 1.
     */
    L_ACHIEVEMENT_1(item = Items.ANTIQUE_LAMP_11185, experience = 500, levelRequired = 1),

    /**
     * Lumbridge Achievement lamp 2.
     */
    L_ACHIEVEMENT_2(item = Items.ANTIQUE_LAMP_11186, experience = 1000, levelRequired = 30),

    /**
     * Lumbridge Achievement diary lamp 3.
     */
    L_ACHIEVEMENT_3(item = Items.ANTIQUE_LAMP_11187, experience = 1500, levelRequired = 35),

    /**
     * Falador Achievement 1 lamp.
     */
    FALLY_ACHIEVEMENT_1(item = Items.ANTIQUE_LAMP_14580, experience = 1000, levelRequired = 30),

    /**
     * Falador Achievement 2 lamp.
     */
    FALLY_ACHIEVEMENT_2(item = Items.ANTIQUE_LAMP_14581, experience = 5000, levelRequired = 40),

    /**
     * Falador Achievement 3 lamp.
     */
    FALLY_ACHIEVEMENT_3(item = Items.ANTIQUE_LAMP_14582, experience = 10000, levelRequired = 50),

    /**
     * Fremennik Province Achievement 1 lamp.
     */
    FREM_ACHIEVEMENT_1(item = Items.ANTIQUE_LAMP_14574, experience = 5000, levelRequired = 30),

    /**
     * Fremennik Province Achievement 2 lamp.
     */
    FREM_ACHIEVEMENT_2(item = Items.ANTIQUE_LAMP_14575, experience = 10000, levelRequired = 40),

    /**
     * Fremennik Province Achievement 3 lamp.
     */
    FREM_ACHIEVEMENT_3(item = Items.ANTIQUE_LAMP_14576, experience = 15000, levelRequired = 50),

    /**
     * Seers Achievement 1.
     */
    SEERS_ACHIEVEMENT_1(item = Items.ANTIQUE_LAMP_14633, experience = 1000, levelRequired = 30),

    /**
     * Seers Achievement 2.
     */
    SEERS_ACHIEVEMENT_2(item = Items.ANTIQUE_LAMP_14634, experience = 5000, levelRequired = 40),

    /**
     * Seers Achievement 3.
     */
    SEERS_ACHIEVEMENT_3(item = Items.ANTIQUE_LAMP_14635, experience = 10000, levelRequired = 50),

    /**
     * Exp Book 3.
     */
    EXP_BOOK_3(item = Items.TOME_OF_XP_3_9656, experience = 2000, levelRequired = 30),

    /**
     * Exp Book 2.
     */
    EXP_BOOK_2(item = Items.TOME_OF_XP_2_9657, experience = 2000, levelRequired = 30),

    /**
     * Exp Book 1.
     */
    EXP_BOOK_1(item = Items.TOME_OF_XP_1_9658, experience = 2000, levelRequired = 30),

    /**
     * Exp Book 2nd 3.
     */
    EXP_BOOK_2ND_3(item = Items.TOME_OF_XP_2ND_ED_3_13160, experience = 2500, levelRequired = 35),

    /**
     * Exp Book 2nd 2.
     */
    EXP_BOOK_2ND_2(item = Items.TOME_OF_XP_2ND_ED_2_13161, experience = 2500, levelRequired = 35),

    /**
     * Exp Book 2nd 1.
     */
    EXP_BOOK_2ND_1(item = Items.TOME_OF_XP_2ND_ED_1_13162, experience = 2500, levelRequired = 35);

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
