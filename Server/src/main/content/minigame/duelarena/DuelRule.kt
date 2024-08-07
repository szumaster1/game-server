package content.minigame.duelarena

import core.game.container.impl.EquipmentContainer

/**
 * Duel rule.
 */
enum class DuelRule {
    /**
     * No Range
     *
     * @constructor No Range
     */
    NO_RANGE(4, "You cannot use Ranged attacks."),

    /**
     * No Melee
     *
     * @constructor No Melee
     */
    NO_MELEE(5, "You cannot use Melee attacks."),

    /**
     * No Magic
     *
     * @constructor No Magic
     */
    NO_MAGIC(6, "You cannot use Magic attacks."),

    /**
     * Fun Weapons
     *
     * @constructor Fun Weapons
     */
    FUN_WEAPONS(12, "You can only attack with 'fun' weapons."),

    /**
     * No Forfeit
     *
     * @constructor No Forfeit
     */
    NO_FORFEIT(0, "You cannot forfeit the duel."),

    /**
     * No Drinks
     *
     * @constructor No Drinks
     */
    NO_DRINKS(7, "You cannot use potions."),

    /**
     * No Food
     *
     * @constructor No Food
     */
    NO_FOOD(8, "You cannot use food."),

    /**
     * No Prayer
     *
     * @constructor No Prayer
     */
    NO_PRAYER(9, "You cannot use Prayer."),

    /**
     * No Movement
     *
     * @constructor No Movement
     */
    NO_MOVEMENT(1, "You cannot move."),

    /**
     * Obstacles
     *
     * @constructor Obstacles
     */
    OBSTACLES(10, "There will be obstacles in the arena."),

    /**
     * Enable Summoning
     *
     * @constructor Enable Summoning
     */
    ENABLE_SUMMONING(28, "Familiars will be allowed in the arena."),

    /**
     * No Special Attacks
     *
     * @constructor No Special Attacks
     */
    NO_SPECIAL_ATTACKS(13, "You cannot use special attacks."),

    /**
     * No Hats
     *
     * @constructor No Hats
     */
    NO_HATS(14, "", EquipmentContainer.SLOT_HAT),

    /**
     * No Capes
     *
     * @constructor No Capes
     */
    NO_CAPES(15, "", EquipmentContainer.SLOT_CAPE),

    /**
     * No Amulet
     *
     * @constructor No Amulet
     */
    NO_AMULET(16, "", EquipmentContainer.SLOT_AMULET),

    /**
     * No Weapon
     *
     * @constructor No Weapon
     */
    NO_WEAPON(17, "You can't use 2H weapons such as bows.", EquipmentContainer.SLOT_WEAPON),

    /**
     * No Shield
     *
     * @constructor No Shield
     */
    NO_SHIELD(19, "You can't use 2H weapons such as bows.", EquipmentContainer.SLOT_SHIELD),

    /**
     * No Body
     *
     * @constructor No Body
     */
    NO_BODY(18, "", EquipmentContainer.SLOT_CHEST),

    /**
     * No Legs
     *
     * @constructor No Legs
     */
    NO_LEGS(21, "", EquipmentContainer.SLOT_LEGS),

    /**
     * No Gloves
     *
     * @constructor No Gloves
     */
    NO_GLOVES(23, "", EquipmentContainer.SLOT_HANDS),

    /**
     * No Rings
     *
     * @constructor No Rings
     */
    NO_RINGS(26, "", EquipmentContainer.SLOT_RING),

    /**
     * No Boots
     *
     * @constructor No Boots
     */
    NO_BOOTS(24, "", EquipmentContainer.SLOT_FEET),

    /**
     * No Arrows
     *
     * @constructor No Arrows
     */
    NO_ARROWS(27, "", EquipmentContainer.SLOT_ARROWS);

    val configIndex: Int
    val info: String
    var equipmentSlot = -1

    constructor(configIndex: Int, info: String) {
        this.configIndex = configIndex
        this.info = info
    }

    constructor(configIndex: Int, info: String, equipmentSlot: Int) {
        this.configIndex = configIndex
        this.info = info
        this.equipmentSlot = equipmentSlot
    }
}