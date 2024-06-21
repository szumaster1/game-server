package content.region.misthalin.quest.free.demonslayer.plugin;

import content.region.misthalin.quest.free.demonslayer.DemonSlayer;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.quest.Quest;
import core.game.node.item.Item;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.setAttribute;
import static core.api.ContentAPIKt.setVarp;

/**
 * The D slayer drain plugin.
 */
public final class DSlayerDrainPlugin extends UseWithHandler {

    private static final Animation ANIMATION = new Animation(827);

    private static final Item BUCKET_OF_WATER = new Item(1929);

    private static final Item BUCKET = new Item(1925);

    /**
     * Instantiates a new D slayer drain plugin.
     */
    public DSlayerDrainPlugin() {
        super(1929);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        addHandler(17424, OBJECT_TYPE, this);
        return this;
    }

    @Override
    public boolean handle(NodeUsageEvent event) {
        final Player player = event.getPlayer();
        final Quest quest = player.getQuestRepository().getQuest("Demon Slayer");
        if (player.getInventory().remove(BUCKET_OF_WATER)) {
            player.getInventory().add(BUCKET);
            player.animate(ANIMATION);
            player.getPacketDispatch().sendMessage("You pour the liquid down the drain.");
            if (quest.getStage(player) != 20) {

                return true;
            }
            if (player.getAttribute("demon-slayer:just-poured", false)) {

                return true;
            }
            if (!player.hasItem(DemonSlayer.FIRST_KEY)) {

                player.getSavedData().getQuestData().getDemonSlayer()[0] = false;
            }
            if (quest.getStage(player) == 20 && !player.hasItem(DemonSlayer.FIRST_KEY) && !player.getSavedData().getQuestData().getDemonSlayer()[0]) {

                player.getDialogueInterpreter().sendDialogues(player, null, "OK, I think I've washed the key down into the sewer.", "I'd better go down and get it!");
                player.getSavedData().getQuestData().getDemonSlayer()[0] = true;// poured
                setVarp(player, 222, 2660610, true);
                setAttribute(player, "demon-slayer:just-poured", true);
                return true;
            }
        }
        return true;
    }

    @Override
    public Location getDestination(Player player, Node with) {
        return new Location(3225, 3495);

    }

}
