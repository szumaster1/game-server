package core.game.consumable

import core.game.node.entity.player.Player

/**
 * Consumable effect.
 */
abstract class ConsumableEffect {
    /**
     * Activate.
     *
     * @param player the player
     */
    abstract fun activate(player: Player)

    /**
     * Gets health effect value.
     *
     * @param player the player
     * @return the health effect value
     */
    open fun getHealthEffectValue(player: Player): Int {
        return 0
    }
}
