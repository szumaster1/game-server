package core.game.node.entity.combat.equipment

import core.game.node.item.Item

/**
 * Represents the type of weapon used.
 * @author Emperor
 *
 * @param item The item associated with the weapon.
 * @param ammunitionSlot The slot for ammunition, default is -1 indicating no slot.
 * @param ammunition The ammunition item, default is null indicating no ammunition.
 * @param type The type of weapon, default is WeaponType.DEFAULT.
 * @constructor Initializes a weapon with the specified parameters.
 */
class Weapon @JvmOverloads constructor(
    val item: Item?,
    val ammunitionSlot: Int = -1,
    val ammunition: Item? = null,
    type: WeaponType? = WeaponType.DEFAULT
) {

    val id: Int = item?.id ?: -1
    val name: String = if (item == null) "null" else item.name
    var type: WeaponType? = null

    /**
     * Represents the different types of weapons.
     */
    enum class WeaponType {
        DEFAULT,
        CROSSBOW,
        DOUBLE_SHOT,
        THROWN,
        DEGRADING,
        STAFF,
        CHINCHOMPA
    }

}
