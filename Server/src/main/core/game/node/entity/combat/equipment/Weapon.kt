package core.game.node.entity.combat.equipment

import core.game.node.item.Item

/**
 * Represents a weapon in the game.
 *
 * @param item The item associated with the weapon.
 * @param ammunitionSlot The slot for ammunition, default is -1 indicating no slot.
 * @param ammunition The ammunition item, default is null indicating no ammunition.
 * @param type The type of weapon, default is WeaponType.DEFAULT.
 * @constructor Initializes a weapon with the specified parameters.
 */
class Weapon @JvmOverloads constructor(
    val item: Item?, // The item representing the weapon, can be null.
    val ammunitionSlot: Int = -1, // The index of the ammunition slot, -1 means no slot.
    val ammunition: Item? = null, // The ammunition item, can be null if not applicable.
    type: WeaponType? = WeaponType.DEFAULT // The type of weapon, defaults to DEFAULT.
) {

    val id: Int = item?.id ?: -1 // The unique identifier for the weapon, -1 if item is null.
    val name: String = if (item == null) "null" else item.name // The name of the weapon, "null" if item is null.
    var type: WeaponType? = null // The type of weapon, initially set to null.

    /**
     * Enum representing the different types of weapons.
     */
    enum class WeaponType {
        /**
         * Represents the default weapon type.
         */
        DEFAULT,

        /**
         * Represents a crossbow weapon type.
         */
        CROSSBOW,

        /**
         * Represents a double shot weapon type.
         */
        DOUBLE_SHOT,

        /**
         * Represents a thrown weapon type.
         */
        THROWN,

        /**
         * Represents a degrading weapon type.
         */
        DEGRADING,

        /**
         * Represents a staff weapon type.
         */
        STAFF,

        /**
         * Represents a chinchompa weapon type.
         */
        CHINCHOMPA
    }

}
