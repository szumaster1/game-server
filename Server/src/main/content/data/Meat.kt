package content.data

import core.api.consts.Items

/**
 * Meat state.
 */
enum class MeatState {
    /**
     * Inedible Raw.
     */
    INEDIBLE_RAW,

    /**
     * Inedible Burnt.
     */
    INEDIBLE_BURNT,

    /**
     * Inedible Special.
     */
    INEDIBLE_SPECIAL,

    /**
     * Edible Cooked.
     */
    EDIBLE_COOKED,
}

/**
 * Enum class representing different types of meat.
 *
 * @property id The id for the meat type.
 * @property state The state of the meat (e.g., raw, cooked).
 */
enum class Meat(val id: Int, val state: MeatState) {
    /**
     * Enchanted Beef.
     */
    ENCHANTED_BEEF(
        id = Items.ENCHANTED_BEEF_522,
        state = MeatState.INEDIBLE_SPECIAL
    ),

    /**
     * Enchanted Rat Meat.
     */
    ENCHANTED_RAT_MEAT(
        id = Items.ENCHANTED_RAT_MEAT_523,
        state = MeatState.INEDIBLE_SPECIAL
    ),

    /**
     * Enchanted Bear Meat.
     */
    ENCHANTED_BEAR_MEAT(
        id = Items.ENCHANTED_BEAR_MEAT_524,
        state = MeatState.INEDIBLE_SPECIAL
    ),

    /**
     * Enchanted Chicken.
     */
    ENCHANTED_CHICKEN(
        id = Items.ENCHANTED_CHICKEN_525,
        state = MeatState.INEDIBLE_SPECIAL
    ),

    /**
     * Raw Ugthanki Meat.
     */
    RAW_UGTHANKI_MEAT(
        Items.RAW_UGTHANKI_MEAT_1859,
        state = MeatState.INEDIBLE_RAW
    ),

    /**
     * Ugthanki Meat.
     */
    UGTHANKI_MEAT(
        id = Items.UGTHANKI_MEAT_1861,
        state = MeatState.EDIBLE_COOKED
    ),

    /**
     * Raw Beef.
     */
    RAW_BEEF(
        id = Items.RAW_BEEF_2132,
        state = MeatState.INEDIBLE_RAW
    ),

    /**
     * Raw Rat Meat.
     */
    RAW_RAT_MEAT(
        id = Items.RAW_RAT_MEAT_2134,
        state = MeatState.INEDIBLE_RAW
    ),

    /**
     * Raw Bear Meat.
     */
    RAW_BEAR_MEAT(
        id = Items.RAW_BEAR_MEAT_2136,
        state = MeatState.INEDIBLE_RAW
    ),

    /**
     * Raw Chicken.
     */
    RAW_CHICKEN(
        id = Items.RAW_CHICKEN_2138,
        state = MeatState.INEDIBLE_RAW
    ),

    /**
     * Cooked Chicken.
     */
    COOKED_CHICKEN(
        id = Items.COOKED_CHICKEN_2140,
        state = MeatState.EDIBLE_COOKED
    ),

    /**
     * Burnt Chicken.
     */
    BURNT_CHICKEN(
        id = Items.BURNT_CHICKEN_2144,
        state = MeatState.INEDIBLE_BURNT
    ),

    /**
     * Cooked Meat.
     */
    COOKED_MEAT(
        id = Items.COOKED_MEAT_2142,
        state = MeatState.EDIBLE_COOKED
    ),

    /**
     * Burnt Meat.
     */
    BURNT_MEAT(
        id = Items.BURNT_MEAT_2146,
        state = MeatState.INEDIBLE_BURNT
    ),

    /**
     * Thin Snail Meat.
     */
    THIN_SNAIL_MEAT(
        id = Items.THIN_SNAIL_MEAT_3369,
        state = MeatState.EDIBLE_COOKED
    ),

    /**
     * Lean Snail Meat.
     */
    LEAN_SNAIL_MEAT(
        id = Items.LEAN_SNAIL_MEAT_3371,
        state = MeatState.EDIBLE_COOKED
    ),

    /**
     * Fat Snail Meat.
     */
    FAT_SNAIL_MEAT(
        id = Items.FAT_SNAIL_MEAT_3373,
        state = MeatState.EDIBLE_COOKED
    ),

    /**
     * Raw Beef Undead.
     */
    RAW_BEEF_UNDEAD(
        id = Items.RAW_BEEF_4287,
        state = MeatState.INEDIBLE_RAW
    ),

    /**
     * Raw Chicken Undead.
     */
    RAW_CHICKEN_UNDEAD(
        id = Items.RAW_CHICKEN_4289,
        state = MeatState.INEDIBLE_RAW
    ),

    /**
     * Cooked Chicken Undead.
     */
    COOKED_CHICKEN_UNDEAD(
        id = Items.COOKED_CHICKEN_4291,
        state = MeatState.EDIBLE_COOKED
    ),

    /**
     * Cooked Meat Undead.
     */
    COOKED_MEAT_UNDEAD(
        id = Items.COOKED_MEAT_4293,
        state = MeatState.EDIBLE_COOKED
    ),

    /**
     * Raw Crab Meat.
     */
    RAW_CRAB_MEAT(
        id = Items.CRAB_MEAT_7518,
        state = MeatState.INEDIBLE_RAW
    ),

    /**
     * Burnt Crab Meat.
     */
    BURNT_CRAB_MEAT(
        id = Items.BURNT_CRAB_MEAT_7520,
        state = MeatState.INEDIBLE_BURNT
    ),

    /**
     * Cooked Crab Meat 5.
     */
    COOKED_CRAB_MEAT_5(
        id = Items.COOKED_CRAB_MEAT_7521,
        state = MeatState.EDIBLE_COOKED
    ),

    /**
     * Cooked Crab Meat 4.
     */
    COOKED_CRAB_MEAT_4(
        id = Items.COOKED_CRAB_MEAT_7523,
        state = MeatState.EDIBLE_COOKED
    ),

    /**
     * Cooked Crab Meat 3.
     */
    COOKED_CRAB_MEAT_3(
        id = Items.COOKED_CRAB_MEAT_7524,
        state = MeatState.EDIBLE_COOKED
    ),

    /**
     * Cooked Crab Meat 2.
     */
    COOKED_CRAB_MEAT_2(
        id = Items.COOKED_CRAB_MEAT_7525,
        state = MeatState.EDIBLE_COOKED
    ),

    /**
     * Cooked Crab Meat 1.
     */
    COOKED_CRAB_MEAT_1(
        id = Items.COOKED_CRAB_MEAT_7526,
        state = MeatState.EDIBLE_COOKED
    ),

    /**
     * Ground Crab Meat.
     */
    GROUND_CRAB_MEAT(
        id = Items.GROUND_CRAB_MEAT_7527,
        state = MeatState.INEDIBLE_RAW
    ),

    /**
     * Locust Meat.
     */
    LOCUST_MEAT(
        id = Items.LOCUST_MEAT_9052,
        state = MeatState.EDIBLE_COOKED
    ),

    /**
     * Raw Bird Meat.
     */
    RAW_BIRD_MEAT(
        id = Items.RAW_BIRD_MEAT_9978,
        state = MeatState.INEDIBLE_RAW
    ),

    /**
     * Roast Bird Meat.
     */
    ROAST_BIRD_MEAT(
        Items.ROAST_BIRD_MEAT_9980,
        state = MeatState.EDIBLE_COOKED
    ),

    /**
     * Burnt Bird Meat.
     */
    BURNT_BIRD_MEAT(
        id = Items.BURNT_BIRD_MEAT_9982,
        state = MeatState.INEDIBLE_BURNT
    ),

    /**
     * Skewered Bird Meat.
     */
    SKEWERED_BIRD_MEAT(
        id = Items.SKEWERED_BIRD_MEAT_9984,
        state = MeatState.INEDIBLE_RAW
    ),

    /**
     * Raw Beast Meat.
     */
    RAW_BEAST_MEAT(
        id = Items.RAW_BEAST_MEAT_9986,
        state = MeatState.INEDIBLE_RAW
    ),

    /**
     * Roast Beast Meat.
     */
    ROAST_BEAST_MEAT(
        id = Items.ROAST_BEAST_MEAT_9988,
        state = MeatState.EDIBLE_COOKED
    ),

    /**
     * Burnt Beast Meat.
     */
    BURNT_BEAST_MEAT(
        id = Items.BURNT_BEAST_MEAT_9990,
        state = MeatState.INEDIBLE_BURNT
    ),

    /**
     * Raw Yak Meat.
     */
    RAW_YAK_MEAT(
        id = Items.RAW_YAK_MEAT_10816,
        state = MeatState.INEDIBLE_RAW
    ),

    /**
     * Raw Pawya Meat.
     */
    RAW_PAWYA_MEAT(
        id = Items.RAW_PAWYA_MEAT_12535,
        state = MeatState.INEDIBLE_RAW
    ),

    /**
     * Enchanted Pawya Meat.
     */
    ENCHANTED_PAWYA_MEAT(
        id = Items.ENCHANTED_PAWYA_MEAT_12546,
        state = MeatState.INEDIBLE_SPECIAL
    );
}
