package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

/**
 * Restore summoning special.
 *
 * This class represents a consumable effect that restores the summoning special points of a player's familiar.
 */
class RestoreSummoningSpecial : ConsumableEffect(){

    /**
     * Activate the effect to restore summoning special points.
     *
     * @param player The player whose familiar's special points need to be restored.
     */
    override fun activate(player: Player) {
        val f = player.familiarManager.familiar
        f?.updateSpecialPoints(-15) // Decrease the special points of the familiar by 15.
    }

}
