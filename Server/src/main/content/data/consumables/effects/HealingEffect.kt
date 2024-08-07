package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

/**
 * Healing effect
 *
 * @property amt The amount of healing to be applied
 * @constructor Initializes a HealingEffect object with a specified amount
 */
class HealingEffect(var amt: Int) : ConsumableEffect() {

    /**
     * Activates the healing effect on the player
     *
     * @param player The player to apply the healing effect to
     */
    override fun activate(player: Player) {
        player.getSkills().heal(amt)
    }

    /**
     * Retrieves the value of the health effect
     *
     * @param player The player to get the health effect value for
     * @return The amount of healing effect
     */
    override fun getHealthEffectValue(player: Player): Int {
        return amt
    }

}
