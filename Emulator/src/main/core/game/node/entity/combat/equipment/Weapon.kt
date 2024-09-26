package core.game.node.entity.combat.equipment

import core.game.node.item.Item

/**
 * Represents the type of weapon used.
 * @author Emperor
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
