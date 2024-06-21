package content.region.kandarin.quest.merlinsquest;

import core.api.consts.Items;
import core.cache.def.impl.SceneryDefinition;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.OptionHandler;
import core.game.interaction.UseWithHandler;
import core.game.node.Node;
import core.game.node.entity.combat.ImpactHandler;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.Direction;
import core.game.world.map.Location;
import core.plugin.ClassScanner;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * The Beehive plugin.
 */
@Initializable
public class BeehivePlugin extends OptionHandler {
    private static final Item REPELLANT = new Item(Items.INSECT_REPELLENT_28);
    private static final Item BUCKET = new Item(Items.BUCKET_1925);
    private static final Item BUCKET_OF_WAX = new Item(Items.BUCKET_OF_WAX_30);
    private static final Item HONEYCOMB = new Item(12156);

    @Override
    public boolean handle(Player player, Node node, String option) {
        if (!player.getInventory().containsItem(REPELLANT)) {
            player.getPacketDispatch().sendMessage("The bees fly out of the hive and sting you!");
            player.getImpactHandler().manualHit(player, 2, ImpactHandler.HitsplatType.NORMAL, 1);
            GameWorld.getPulser().submit(new Pulse(2, player) {
                @Override
                public boolean pulse() {
                    player.getPacketDispatch().sendMessage("Maybe you can clear them out somehow.");
                    return true;
                }
            });
        } else {
            switch (option) {
                case "take-from":
                    if (!player.getInventory().containsItem(BUCKET)) {
                        player.getPacketDispatch().sendMessage("You need a bucket to do that.");
                    } else {
                        player.getInventory().remove(BUCKET);
                        player.getInventory().add(BUCKET_OF_WAX);
                        player.getPacketDispatch().sendMessage("You fill your bucket with wax from the hive.");
                    }
                    break;
                case "take-honey":
                    if (!player.getInventory().hasSpaceFor(HONEYCOMB)) {
                        player.getPacketDispatch().sendMessage("You don't have enough space in your inventory.");
                    } else {
                        player.getInventory().add(HONEYCOMB);
                        player.getPacketDispatch().sendMessage("You take a chunk of honeycomb from the hive.");
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        SceneryDefinition.forId(68).getHandlers().put("option:take-from", this);
        SceneryDefinition.forId(68).getHandlers().put("option:take-honey", this);
        ClassScanner.definePlugin(new MerlinCrystalItemHandler());
        return this;
    }

    @Override
    public Location getDestination(Node mover, Node node) {
        Location west = node.getCenterLocation().transform(Direction.WEST, 1);
        Location east = node.getCenterLocation().transform(Direction.EAST, 1);
        if (mover.getLocation().getDistance(east) <= mover.getLocation().getDistance(west)) {
            return east;
        } else {
            return west;
        }
    }

    private class MerlinCrystalItemHandler extends UseWithHandler {

        /**
         * The object id of the beehives
         */
        private final int[] OBJECTS = new int[]{68};

        /**
         * Instantiates a new Merlin crystal item handler.
         */
        public MerlinCrystalItemHandler() {
            super(REPELLANT.getId(), BUCKET.getId());
        }

        @Override
        public Plugin<Object> newInstance(Object arg) throws Throwable {
            for (int id : OBJECTS) {
                addHandler(id, OBJECT_TYPE, this);
            }
            return this;
        }

        @Override
        public boolean handle(NodeUsageEvent event) {
            Player player = event.getPlayer();
            Item useditem = event.getUsedItem();
            final Scenery object = (Scenery) event.getUsedWith();

            if (useditem != null && player.getAttribute("cleared-beehives", false) && useditem.getId() == REPELLANT.getId() && object.getId() == 68) {
                player.getDialogueInterpreter().sendDialogue("You have already cleared the hive of its bees. You can now safely collect wax from the hive.");
            }

            if (useditem != null && useditem.getId() == REPELLANT.getId() && object.getId() == 68 && !player.getAttribute("cleared-beehives", false)) {
                player.getDialogueInterpreter().sendDialogue("You pour insect repellent on the beehive. You see the bees leaving the", "hive.");
                player.setAttribute("cleared-beehives", true);
            }

            if (useditem != null && useditem.getId() == BUCKET.getId() && player.getAttribute("cleared-beehives", false)) {
                player.getDialogueInterpreter().sendDialogue("You get some wax from the beehive.");
                player.getInventory().remove(new Item(BUCKET.getId(), 1));
                player.getInventory().add(new Item(BUCKET_OF_WAX.getId(), 1));
            } else if (useditem != null && useditem.getId() == BUCKET.getId() && !player.getAttribute("cleared-beehives", false)) {
                player.getDialogueInterpreter().sendDialogue("It would be dangerous to stick the bucket into the hive while the bees", "are still in it. Perhaps you can clear them out somehow.");
            }

            return true;
        }

    }
}

