package content.global.skill.production.cooking.plugins;

import cfg.consts.Items;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.item.Item;
import core.plugin.Initializable;
import core.plugin.Plugin;


/**
 * Cake making plugin.
 */
@Initializable
public final class CakeMakingPlugin extends UseWithHandler {

    private static final Item BUCKET_OF_MILK = new Item(Items.BUCKET_OF_MILK_1927);
    private static final Item EGG = new Item(Items.EGG_1944);
    private static final Item CAKE_TIN = new Item(Items.CAKE_TIN_1887);
    private static final Item POT_OF_FLOUR = new Item(Items.POT_OF_FLOUR_1933);
    private static final Item UNCOOKED_CAKE = new Item(Items.UNCOOKED_CAKE_1889);


    /**
     * Instantiates a new Cake making plugin.
     */
    public CakeMakingPlugin() {
        super(Items.POT_OF_FLOUR_1933);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        addHandler(Items.CAKE_TIN_1887, ITEM_TYPE, this);
        return this;
    }

    @Override
    public boolean handle(NodeUsageEvent event) {
        if (event.getUsedItem().getId() == Items.CAKE_TIN_1887 && event.getUsedWith().getId() == Items.POT_OF_FLOUR_1933 || event.getUsedWith().getName().equalsIgnoreCase("cake tin") && event.getUsedItem().getName().equalsIgnoreCase("pot of flour")) {
            if (event.getPlayer().getInventory().contains(Items.POT_OF_FLOUR_1933, 1) && event.getPlayer().getInventory().contains(Items.BUCKET_OF_MILK_1927, 1) && event.getPlayer().getInventory().contains(Items.EGG_1944, 1)) {
                if (event.getPlayer().getInventory().remove(BUCKET_OF_MILK, EGG, CAKE_TIN, POT_OF_FLOUR)) {
                    event.getPlayer().getInventory().add(UNCOOKED_CAKE);
                    event.getPlayer().getPacketDispatch().sendMessage("You mix the milk, flour and egg together to make a raw cake mix.");
                    return true;
                }
            }
        }
        return false;
    }

}
