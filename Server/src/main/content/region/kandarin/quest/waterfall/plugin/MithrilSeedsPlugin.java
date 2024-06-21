package content.region.kandarin.quest.waterfall.plugin;

import core.cache.def.impl.ItemDefinition;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.node.scenery.SceneryBuilder;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.RegionManager;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.utilities.RandomFunction;

import static core.api.ContentAPIKt.setAttribute;

/**
 * The Mithril seeds plugin.
 */
@Initializable
public final class MithrilSeedsPlugin extends OptionHandler {

    private static final Item ITEM = new Item(299, 1);

    private static final int[] FLOWERS = {2980, 2981, 2982, 2983, 2984, 2985, 2986};

    private static final int[] RARE = new int[]{2987, 2988};

    private static final Animation ANIMATION = new Animation(827);

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ItemDefinition.forId(299).getHandlers().put("option:plant", this);
        return this;
    }

    @Override
    public boolean handle(final Player player, Node node, String option) {
        if (player.getAttribute("delay:plant", -1) > GameWorld.getTicks()) {
            return true;
        }
        if (RegionManager.getObject(player.getLocation()) != null) {
            player.getPacketDispatch().sendMessage("You can't plant a flower here.");
            return true;
        }
        player.animate(ANIMATION);
        player.getInventory().remove(ITEM);
        final Scenery object = SceneryBuilder.add(new Scenery(getFlower(RandomFunction.random(100) == 1 ? RARE : FLOWERS), player.getLocation()), 100);
        player.moveStep();
        player.lock(3);
        player.getPulseManager().run(new Pulse(1, player) {
            @Override
            public boolean pulse() {
                player.faceLocation(object.getFaceLocation(player.getLocation()));
                player.getDialogueInterpreter().open(1 << 16 | 1, object);
                return true;
            }
        });
        setAttribute(player, "delay:plant", GameWorld.getTicks() + 3);
        player.getPacketDispatch().sendMessage("You open the small mithril case and drop a seed by your feet.");
        return true;
    }

    @Override
    public boolean isWalk() {
        return false;
    }

    /**
     * Gets flower.
     *
     * @param array the array
     * @return the flower
     */
    public static int getFlower(int[] array) {
        return array[RandomFunction.random(array.length)];
    }

}
