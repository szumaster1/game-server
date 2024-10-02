package content.global.skill.crafting.items.armour.leather

import org.rs.consts.Items

enum class DragonLeather(val leather: Int, val amount: Int, val product: Int, val level: Int, val experience: Double) {
    GREEN_D_HIDE_VAMBS(
        leather = Items.GREEN_D_LEATHER_1745,
        amount = 1,
        product = Items.GREEN_DHIDE_VAMB_1065,
        level = 57,
        experience = 62.0
    ),
    GREEN_D_HIDE_CHAPS(
        leather = Items.GREEN_D_LEATHER_1745,
        amount = 2,
        product = Items.GREEN_DHIDE_CHAPS_1099,
        level = 60,
        experience = 124.0
    ),
    GREEN_D_HIDE_BODY(
        leather = Items.GREEN_D_LEATHER_1745,
        amount = 3,
        product = Items.GREEN_DHIDE_BODY_1135,
        level = 63,
        experience = 186.0
    ),
    BLUE_D_HIDE_VAMBS(
        leather = Items.BLUE_D_LEATHER_2505,
        amount = 1,
        product = Items.BLUE_DHIDE_VAMB_2487,
        level = 66,
        experience = 70.0
    ),
    BLUE_D_HIDE_CHAPS(
        leather = Items.BLUE_D_LEATHER_2505,
        amount = 2,
        product = Items.BLUE_DHIDE_CHAPS_2493,
        level = 68,
        experience = 140.0
    ),
    BLUE_D_HIDE_BODY(
        leather = Items.BLUE_D_LEATHER_2505,
        amount = 3,
        product = Items.BLUE_DHIDE_BODY_2499,
        level = 71,
        experience = 210.0
    ),
    RED_D_HIDE_VAMBS(
        leather = Items.RED_DRAGON_LEATHER_2507,
        amount = 1,
        product = Items.RED_DHIDE_VAMB_2489,
        level = 73,
        experience = 78.0
    ),
    RED_D_HIDE_CHAPS(
        leather = Items.RED_DRAGON_LEATHER_2507,
        amount = 2,
        product = Items.RED_DHIDE_CHAPS_2495,
        level = 75,
        experience = 156.0
    ),
    RED_D_HIDE_BODY(
        leather = Items.RED_DRAGON_LEATHER_2507,
        amount = 3,
        product = Items.RED_DHIDE_BODY_2501,
        level = 77,
        experience = 234.0
    ),
    BLACK_D_HIDE_VAMBS(
        leather = Items.BLACK_D_LEATHER_2509,
        amount = 1,
        product = Items.BLACK_DHIDE_VAMB_2491,
        level = 79,
        experience = 86.0
    ),
    BLACK_D_HIDE_CHAPS(
        leather = Items.BLACK_D_LEATHER_2509,
        amount = 2,
        product = Items.BLACK_DHIDE_CHAPS_2497,
        level = 82,
        experience = 172.0
    ),
    BLACK_D_HIDE_BODY(
        leather = Items.BLACK_D_LEATHER_2509,
        amount = 3,
        product = Items.BLACK_DHIDE_BODY_2503,
        level = 84,
        experience = 258.0
    );

    companion object {
        fun forId(itemId: Int): DragonLeather? {
            return values().find { it.product == itemId }
        }
    }
}