package content.data.consumables.effects

import core.api.setAttribute
import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.game.world.GameWorld.ticks

/**
 * Set attribute effect.
 */
class SetAttributeEffect : ConsumableEffect {

    private var attrString: String  // Attribute name
    private var attrValue: Any  // Attribute value
    private var isTicks: Boolean = false  // Flag to check if the value is in ticks

    constructor(attr: String, value: Any, isTicks: Boolean) {
        this.attrString = attr
        this.attrValue = value
        this.isTicks = isTicks
    }

    constructor(attr: String, value: Any) {
        this.attrString = attr
        this.attrValue = value
        this.isTicks = value is Int
    }

    override fun activate(player: Player) {
        if (isTicks) {
            val value = attrValue as Int + ticks  // Calculate value in ticks
            setAttribute(player, attrString, value)  // Set attribute with the calculated value
            return
        }
        setAttribute(player, attrString, attrValue)  // Set attribute with the provided value
    }

}
