package core.game.consumable;


import core.game.node.entity.player.Player;

/**
 * The type Consumable effect.
 */
public abstract class ConsumableEffect {
    /**
     * Activate.
     *
     * @param p the p
     */
    public abstract void activate(Player p);

    /**
     * Gets health effect value.
     *
     * @param player the player
     * @return the health effect value
     */
    public int getHealthEffectValue(Player player) {
		return 0;
	}
}
