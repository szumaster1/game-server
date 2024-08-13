package core.game.node.entity.combat.equipment

import core.game.node.item.Item

/**
 * Weapon
 *
 * @param item
 * @param ammunitionSlot
 * @param ammunition
 * @constructor
 *
 * @param type
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
     * Weapon type
     *
     * @constructor Weapon type
     */
    enum class WeaponType {
        /**
         * Default
         *
         * @constructor Default
         */
        DEFAULT,

        /**
         * Crossbow
         *
         * @constructor Crossbow
         */
        CROSSBOW,

        /**
         * Double Shot
         *
         * @constructor Double Shot
         */
        DOUBLE_SHOT,

        /**
         * Thrown
         *
         * @constructor Thrown
         */
        THROWN,

        /**
         * Degrading
         *
         * @constructor Degrading
         */
        DEGRADING,

        /**
         * Staff
         *
         * @constructor Staff
         */
        STAFF,

        /**
         * Chinchompa
         *
         * @constructor Chinchompa
         */
        CHINCHOMPA
    }

}