package content.data.consumable.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

/**
 * Energy effect.
 */
class EnergyEffect(amt: Int) : ConsumableEffect() {

    // Convert the amount to a Double for precision
    var amt: Double = amt.toDouble()

    // Override the activate method to update the player's run energy
    override fun activate(player: Player) {
        player.settings.updateRunEnergy(-amt)
    }

}
