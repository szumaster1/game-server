package content.global.skill.prayer

import org.rs.consts.Items

/**
 * Represents the type of bones.
 */
enum class Bones(val itemId: Int, val experience: Double, val bonemealId: Int? = null) {
    BONES(itemId = Items.BONES_2530, experience = 4.5, bonemealId = Items.BONEMEAL_4255),
    BONES_2(itemId = Items.BONES_526, experience = 4.5, bonemealId = Items.BONEMEAL_4256),
    WOLF_BONES(itemId = Items.WOLF_BONES_2859, experience = 4.5, bonemealId = Items.BONEMEAL_4257),
    BURNT_BONES(itemId = Items.BURNT_BONES_528, experience = 4.5, bonemealId = Items.BONEMEAL_4258),
    MONKEY_BONES(itemId = Items.MONKEY_BONES_3183, experience = 5.0, bonemealId = Items.BONEMEAL_4260),
    MONKEY_BONES2(itemId = Items.MONKEY_BONES_3179, experience = 5.0, bonemealId = Items.BONEMEAL_4260),
    BAT_BONES(itemId = Items.BAT_BONES_530, experience = 5.3, bonemealId = Items.BONEMEAL_4261),
    BIG_BONES(itemId = Items.BIG_BONES_532, experience = 15.0, bonemealId = Items.BONEMEAL_4262),
    JOGRE_BONES(itemId = Items.JOGRE_BONES_3125, experience = 15.0, bonemealId = Items.BONEMEAL_4263),
    ZOGRE_BONES(itemId = Items.ZOGRE_BONES_4812, experience = 12.5, bonemealId = Items.BONEMEAL_4264),
    SHAIKAHAN_BONES(itemId = Items.SHAIKAHAN_BONES_3123, experience = 25.0, bonemealId = Items.BONEMEAL_4265),
    BABY_DRAGON_BONES(itemId = Items.BABYDRAGON_BONES_534, experience = 30.0, bonemealId = Items.BONEMEAL_4266),
    WYVERN_BONES(itemId = Items.WYVERN_BONES_6812, experience = 50.0, bonemealId = Items.BONEMEAL_6810),
    DRAGON_BONES(itemId = Items.DRAGON_BONES_536, experience = 72.0, bonemealId = Items.BONEMEAL_4268),
    FAYRG(itemId = Items.FAYRG_BONES_4830, experience = 84.0, bonemealId = Items.BONEMEAL_4852),
    RAURG_BONES(itemId = Items.RAURG_BONES_4832, experience = 96.0, bonemealId = Items.BONEMEAL_4853),
    DAGANNOTH(itemId = Items.DAGANNOTH_BONES_6729, experience = 125.0, bonemealId = Items.BONEMEAL_6728),
    OURG_BONES(itemId = Items.OURG_BONES_4834, experience = 140.0, bonemealId = Items.BONEMEAL_4854),
    BURNT_JOGRE_BONES(itemId = Items.BURNT_JOGRE_BONES_3127, experience = 16.0, bonemealId = Items.BONEMEAL_4259),
    BURNT_RAW_PASTY_JOGRE_BONES(itemId = Items.PASTY_JOGRE_BONES_3128, experience = 17.0),
    BURNT_COOKED_PASTY_JOGRE_BONES(itemId = Items.PASTY_JOGRE_BONES_3129, experience = 17.0),
    MARINATED_JOGRE_BONES(itemId = Items.MARINATED_J_BONES_3130, experience = 18.0),
    RAW_PASTY_JOGRE_BONES(itemId = Items.PASTY_JOGRE_BONES_3131, experience = 17.0),
    COOKED_PASTY_JOGRE_BONES(itemId = Items.PASTY_JOGRE_BONES_3132, experience = 17.0),
    MARINATED_JOGRE_BONES_BAD(itemId = Items.MARINATED_J_BONES_3133, experience = 18.0);

    companion object {
        /**
         * Holds all bones.
         */
        private val bones = HashMap<Int, Bones>()

        /**
         * Gets the bones for the bone meal.
         * @param itemId the item.
         * @return the bones.
         */
        @JvmStatic
        fun forBoneMeal(itemId: Int): Bones? {
            for (bone in values()) {
                if (bone.bonemealId == itemId) {
                    return bone
                }
            }
            return null
        }

        /**
         * Gets the bone ids.
         * @return the ids.
         */
        val array: IntArray
            @JvmStatic get() {
                val list: MutableList<Int> = ArrayList(20)
                for (i in bones.keys) {
                    list.add(i)
                }
                return list.toIntArray()
            }

        /**
         * Get the bone.
         * @param itemId The item id.
         * @return The bone.
         */
        @JvmStatic
        fun forId(itemId: Int): Bones? {
            return bones[itemId]
        }

        /**
         * Construct the bones.
         */
        init {
            for (bone in values()) {
                bones[bone.itemId] = bone
            }
        }
    }
}