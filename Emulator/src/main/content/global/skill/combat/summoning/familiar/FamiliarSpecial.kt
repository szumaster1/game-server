package content.global.skill.combat.summoning.familiar

import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.item.Item

/**
 * Familiar special.
 */
class FamiliarSpecial
/**
 * Instantiates a new Familiar special.
 *
 * @param node the node
 */ @JvmOverloads constructor(
    /**
     * Sets node.
     *
     * @param node the node
     */
    @JvmField var node: Node,
    /**
     * Sets interface id.
     *
     * @param interfaceID the interface id
     */
    var interfaceID: Int = -1,
    /**
     * Sets component.
     *
     * @param component the component
     */
    var component: Int = -1,
    /**
     * Sets item.
     *
     * @param item the item
     */
    var item: Item? = null
) {
    /**
     * Gets node.
     *
     * @return the node
     */
    /**
     * Gets interface id.
     *
     * @return the interface id
     */
    /**
     * Gets component.
     *
     * @return the component
     */
    /**
     * Gets item.
     *
     * @return the item
     */

    /**
     * Instantiates a new Familiar special.
     *
     * @param node        the node
     * @param interfaceID the interface id
     * @param component   the component
     * @param item        the item
     */

    val target: Entity
        /**
         * Gets target.
         *
         * @return the target
         */
        get() = node as Entity
}