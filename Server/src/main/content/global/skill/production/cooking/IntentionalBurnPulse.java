package content.global.skill.production.cooking;

import core.api.consts.Sounds;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;

import static core.api.ContentAPIKt.playAudio;

/**
 * The Intentional burn pulse.
 */
public class IntentionalBurnPulse extends StandardCookingPulse {
    /**
     * The Initial.
     */
    int initial, /**
     * The Product.
     */
    product, /**
     * The Amount.
     */
    amount;
    /**
     * The Player.
     */
    Player player;
    /**
     * The Object.
     */
    Scenery object;

    /**
     * Instantiates a new Intentional burn pulse.
     *
     * @param player  the player
     * @param object  the object
     * @param initial the initial
     * @param product the product
     * @param amount  the amount
     */
    IntentionalBurnPulse(Player player, Scenery object, int initial, int product, int amount) {
        super(player, object, initial, product, amount);
        this.initial = initial;
        this.product = product;
        this.amount = amount;
        this.player = player;
        this.object = object;
    }

    @Override
    public boolean checkRequirements() {
        return object.isActive();
    }

    @Override
    public boolean reward() {
        if (getDelay() == 1) {
            setDelay(object.getName().equalsIgnoreCase("range") ? 5 : 4);
            return false;
        }
        if (cook(player, null, false, initial, product)) {
            amount--;
        } else {
            return true;
        }
        // we are always one off a normal cooking pulse because
        // the first tick is handled outside of this class.
        return amount <= 1;
    }

    @Override
    public boolean cook(Player player, Scenery object, boolean burned, int initial, int product) {
        super.animate();
        Item initialItem = new Item(initial);
        Item productItem = new Item(product);

        if (player.getInventory().remove(initialItem)) {
            player.getInventory().add(productItem);
            player.getPacketDispatch().sendMessage(getMessage(initialItem, productItem, burned));
            playAudio(player, Sounds.FRY_2577);
            return true;
        }
        return false;
    }
}
