package content.global.skill.production.cooking;

import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;

/**
 * The Sinew cooking pulse.
 */
public class SinewCookingPulse extends StandardCookingPulse {

    /**
     * Instantiates a new Sinew cooking pulse.
     *
     * @param player  the player
     * @param object  the object
     * @param initial the initial
     * @param product the product
     * @param amount  the amount
     */
    public SinewCookingPulse(Player player, Scenery object, int initial, int product, int amount) {
        super(player, object, initial, product, amount);
    }

    @Override
    public boolean checkRequirements() {
        properties = CookableItems.SINEW;
        return super.checkRequirements();
    }

    @Override
    public boolean isBurned(Player player, Scenery object, int food) {
        return false;
    }

    @Override
    public String getMessage(Item food, Item product, boolean burned) {
        return "You dry a piece of beef and extract the sinew.";
    }
}
