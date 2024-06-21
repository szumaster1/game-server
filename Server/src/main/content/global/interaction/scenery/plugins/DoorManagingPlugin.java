package content.global.interaction.scenery.plugins;

import content.region.kandarin.quest.holygrail.HolyGrail;
import core.api.consts.Animations;
import core.api.consts.Sounds;
import core.cache.def.impl.SceneryDefinition;
import core.game.global.action.DoorActionHandler;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.scenery.Scenery;
import core.game.node.scenery.SceneryBuilder;
import core.game.world.map.Location;
import core.game.world.map.RegionManager;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.animate;
import static core.api.ContentAPIKt.playAudio;

@Initializable
public final class DoorManagingPlugin extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        SceneryDefinition.setOptionHandler("open", this);
        SceneryDefinition.setOptionHandler("close", this);
        SceneryDefinition.setOptionHandler("shut", this);
        SceneryDefinition.setOptionHandler("go-through", this);
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        Scenery object = (Scenery) node;
        if (object.getType() != 9 && !player.getLocation().equals(node.getLocation()) && !player.getLocation().isNextTo(object) && object.getName().contains("cupboard")) {
            return true;
        }
        String name = object.getName().toLowerCase();
        if (name.contains("drawers") || name.contains("wardrobe") || name.contains("cupboard")) {
            switch (option) {
                case "open":
                    if (name.contains("drawers")) {
                        playAudio(player, Sounds.DRAWER_OPEN_64);
                    }
                    if (name.contains("wardrobe")) {
                        animate(player, Animations.OPEN_WARDROBE_545, false);
                        playAudio(player, Sounds.WARDROBE_OPEN_96);
                    }
                    if (name.contains("cupboard")) {
                        playAudio(player, Sounds.CUPBOARD_OPEN_58);
                    }
                case "go-through":
                    if (object.isActive()) {
                        SceneryBuilder.replace(object, object.transform(object.getId() + 1), 80);
                    }
                    return true;
                case "close":
                case "shut":
                    if (name.contains("drawers")) {
                        playAudio(player, Sounds.DRAWER_CLOSE_63);
                    }
                    if (name.contains("wardrobe")) {
                        playAudio(player, Sounds.WARDROBE_CLOSE_95);
                        animate(player, Animations.CLOSE_WARDROBE_544, false);
                    }
                    if (name.contains("cupboard")) {
                        playAudio(player, Sounds.CUPBOARD_CLOSE_57);
                    }
                    SceneryBuilder.replace(object, object.transform(object.getId() - 1));
                    return true;
            }
            return true;
        }
        if (name.contains("trapdoor") || name.contains("trap door")) {
            Location destination = object.getLocation().transform(0, 6400, 0);
            if (!RegionManager.isTeleportPermitted(destination)) {
                player.getPacketDispatch().sendMessage("This doesn't seem to go anywhere.");
                return true;
            }
            player.getProperties().setTeleportLocation(destination);
            return true;
        }
        if (!name.contains("door") && !name.contains("gate") && !name.contains("fence") && !name.contains("wall") && !name.contains("exit") && !name.contains("entrance")) {
            return false;
        }
        if (HolyGrail.Companion.getMERLIN_DOOR_ID() == object.getId() && !player.getQuestRepository().hasStarted("Holy Grail")) {
            if (object.getLocation().equals(HolyGrail.Companion.getMERLIN_DOOR_LOCATION_OPEN()) || object.getLocation().equals(HolyGrail.Companion.getMERLIN_DOOR_LOCATION_CLOSED()))
                return false;
        }
        DoorActionHandler.handleDoor(player, object);
        return true;
    }

    @Override
    public Location getDestination(Node n, Node node) {
        Scenery o = (Scenery) node;
        if (o.getType() < 4 || o.getType() == 9) {
            return DoorActionHandler.getDestination((Player) n, o);
        }
        return null;
    }

}
