package content.minigame.duelarena

import core.game.container.impl.EquipmentContainer

/**
 * Duel rule.
 */
enum class DuelRule {
    /**
     * No Range.
     */
    NO_RANGE(4, "You cannot use Ranged attacks."),

    /**
     * No Melee.
     */
    NO_MELEE(5, "You cannot use Melee attacks."),

    /**
     * No Magic.
     */
    NO_MAGIC(6, "You cannot use Magic attacks."),

    /**
     * Fun Weapons.
     */
    FUN_WEAPONS(12, "You can only attack with 'fun' weapons."),

    /**
     * No Forfeit.
     */
    NO_FORFEIT(0, "You cannot forfeit the duel."),

    /**
     * No Drinks.
     */
    NO_DRINKS(7, "You cannot use potions."),

    /**
     * No Food.
     */
    NO_FOOD(8, "You cannot use food."),

    /**
     * No Prayer.
     */
    NO_PRAYER(9, "You cannot use Prayer."),

    /**
     * No Movement.
     */
    NO_MOVEMENT(1, "You cannot move."),

    /**
     * Obstacles.
     */
    OBSTACLES(10, "There will be obstacles in the arena."),

    /**
     * Enable Summoning.
     */
    ENABLE_SUMMONING(28, "Familiars will be allowed in the arena."),

    /**
     * No Special Attacks.
     */
    NO_SPECIAL_ATTACKS(13, "You cannot use special attacks."),

    /**
     * No Hats.
     */
    NO_HATS(14, "", EquipmentContainer.SLOT_HAT),

    /**
     * No Capes.
     */
    NO_CAPES(15, "", EquipmentContainer.SLOT_CAPE),

    /**
     * No Amulet.
     */
    NO_AMULET(16, "", EquipmentContainer.SLOT_AMULET),

    /**
     * No Weapon.
     */
    NO_WEAPON(17, "You can't use 2H weapons such as bows.", EquipmentContainer.SLOT_WEAPON),

    /**
     * No Shield.
     */
    NO_SHIELD(19, "You can't use 2H weapons such as bows.", EquipmentContainer.SLOT_SHIELD),

    /**
     * No Body.
     */
    NO_BODY(18, "", EquipmentContainer.SLOT_CHEST),

    /**
     * No Legs.
     */
    NO_LEGS(21, "", EquipmentContainer.SLOT_LEGS),

    /**
     * No Gloves.
     */
    NO_GLOVES(23, "", EquipmentContainer.SLOT_HANDS),

    /**
     * No Rings.
     */
    NO_RINGS(26, "", EquipmentContainer.SLOT_RING),

    /**
     * No Boots.
     */
    NO_BOOTS(24, "", EquipmentContainer.SLOT_FEET),

    /**
     * No Arrows.
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