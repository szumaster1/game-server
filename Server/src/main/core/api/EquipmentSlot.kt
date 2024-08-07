package core.api

/**
 * Equipment slot
 *
 * @constructor Equipment slot
 */
enum class EquipmentSlot {
    /**
     * Head
     *
     * @constructor Head
     */
    HEAD,

    /**
     * Cape
     *
     * @constructor Cape
     */
    CAPE,

    /**
     * Neck
     *
     * @constructor Neck
     */
    NECK,

    /**
     * Weapon
     *
     * @constructor Weapon
     */
    WEAPON,

    /**
     * Chest
     *
     * @constructor Chest
     */
    CHEST,

    /**
     * Shield
     *
     * @constructor Shield
     */
    SHIELD,

    /**
     * Hidden 1
     *
     * @constructor Hidden 1
     */
    HIDDEN_1,

    /**
     * Legs
     *
     * @constructor Legs
     */
    LEGS,

    /**
     * Hidden 2
     *
     * @constructor Hidden 2
     */
    HIDDEN_2,

    /**
     * Hands
     *
     * @constructor Hands
     */
    HANDS,

    /**
     * Feet
     *
     * @constructor Feet
     */
    FEET,

    /**
     * Hidden 3
     *
     * @constructor Hidden 3
     */
    HIDDEN_3,

    /**
     * Ring
     *
     * @constructor Ring
     */
    RING,

    /**
     * Ammo
     *
     * @constructor Ammo
     */
    AMMO;

    companion object {
        private val slotMap = HashMap<String, EquipmentSlot>()

        init {
            slotMap["head"] = HEAD; slotMap["helm"] = HEAD; slotMap["helmet"] = HEAD
            slotMap["cape"] = CAPE; slotMap["back"] = CAPE
            slotMap["neck"] = NECK; slotMap["amulet"] = NECK
            slotMap["weapon"] = WEAPON; slotMap["main"] = WEAPON
            slotMap["chest"] = CHEST; slotMap["body"] = CHEST; slotMap["torso"] = CHEST
            slotMap["shield"] = SHIELD; slotMap["off"] = SHIELD
            slotMap["legs"] = LEGS; slotMap["leg"] = LEGS
            slotMap["hands"] = HANDS; slotMap["hand"] = HANDS; slotMap["brace"] = HANDS; slotMap["bracelet"] = HANDS
            slotMap["feet"] = FEET
            slotMap["ring"] = RING
            slotMap["ammo"] = AMMO; slotMap["ammunition"] = AMMO
        }

        /*
         * Return the equipment slot by name. Return null if matching no slot.
         */
        fun slotByName(input: String): EquipmentSlot? {
            return slotMap[input.lowercase()]
        }
    }
}