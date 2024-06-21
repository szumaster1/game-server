package content.global.skill.production.cooking;

import core.cache.def.impl.ItemDefinition;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;

/**
 * The Pie cooking pulse.
 */
public class PieCookingPulse extends StandardCookingPulse {
    private final Scenery object;
    private final Player player;

    /**
     * Instantiates a new Pie cooking pulse.
     *
     * @param player  the player
     * @param object  the object
     * @param initial the initial
     * @param product the product
     * @param amount  the amount
     */
    public PieCookingPulse(Player player, Scenery object, int initial, int product, int amount) {
        super(player, object, initial, product, amount);
        this.object = object;
        this.player = player;
    }

    @Override
    public boolean checkRequirements() {
        if (!object.getName().toLowerCase().contains("range")) {
            player.getPacketDispatch().sendMessage("This can only be cooked on a range.");
            return false;
        }
        return super.checkRequirements();
    }

    @Override
    public String getMessage(Item food, Item product, boolean burned) {
        if (burned) {
            return "You accidentally burn the pie.";
        } else {
            return "You successfully bake a delicious " + ItemDefinition.forId(product.getId()).getName().toLowerCase() + ".";
        }
    }
}
