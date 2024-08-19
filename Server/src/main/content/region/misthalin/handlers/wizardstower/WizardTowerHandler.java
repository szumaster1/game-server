package content.region.misthalin.handlers.wizardstower;

import core.cache.def.impl.SceneryDefinition;
import core.game.global.action.DoorActionHandler;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.scenery.Scenery;
import core.game.world.map.Location;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * Represents the Wizard tower handler.
 */
@Initializable
public final class WizardTowerHandler extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        SceneryDefinition.forId(11993).getHandlers().put("option:open", this);
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        switch (option) {
            case "open":
                if (node.getLocation().equals(new Location(3107, 3162, 0))) {
                    DoorActionHandler.handleAutowalkDoor(player, (Scenery) node, player.getLocation().getX() >= 3107 ? Location.create(3106, 3161, 0) : Location.create(3108, 3163, 0));
                } else {
                    DoorActionHandler.handleDoor(player, (Scenery) node);
                }
                break;
        }
        return true;
    }

    @Override
    public Location getDestination(Node node, Node n) {
        if (n instanceof Scenery) {
            final Scenery object = (Scenery) n;
            if (object.getId() == 11993 && object.getLocation().equals(new Location(3107, 3162, 0))) {
                return node.getLocation().getX() >= 3107 ? Location.create(3108, 3163, 0) : Location.create(3106, 3161, 0);
            }
        }
        return null;
    }

}

