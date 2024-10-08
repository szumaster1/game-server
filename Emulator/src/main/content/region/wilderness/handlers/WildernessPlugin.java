package content.region.wilderness.handlers;

import core.cache.def.impl.SceneryDefinition;
import core.game.component.Component;
import core.game.global.action.ClimbActionHandler;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.world.map.Location;
import core.plugin.Initializable;
import core.plugin.Plugin;
import org.rs.consts.QuestName;

import static core.api.ContentAPIKt.*;

@Initializable
public final class WildernessPlugin extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        SceneryDefinition.forId(37749).getHandlers().put("option:go-through", this);
        SceneryDefinition.forId(37928).getHandlers().put("option:go-through", this);
        SceneryDefinition.forId(37929).getHandlers().put("option:go-through", this);
        SceneryDefinition.forId(38811).getHandlers().put("option:go-through", this);
        SceneryDefinition.forId(39191).getHandlers().put("option:climb-up", this);
        SceneryDefinition.forId(39188).getHandlers().put("option:open", this);
        SceneryDefinition.forId(39200).getHandlers().put("option:open", this);
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        switch (node.getId()) {
            case 37749:
                player.teleport(Location.create(2885, 4372, 2));
                break;
            case 39191:
                ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_UP, Location.create(3239, 3606, 0), "You climb up the ladder to the surface.");
                break;
            case 39188:
                if (!hasRequirement(player, QuestName.DEFENDER_OF_VARROCK))
                    break;
                ClimbActionHandler.climb(player, ClimbActionHandler.CLIMB_DOWN, Location.create(3241, 9991, 0), "You descend into the cavern below.");
                break;
            case 39200:
                player.getPacketDispatch().sendMessage("The door is locked.");
                break;
            case 37928:
                player.teleport(Location.create(3214, 3782, 0));
                break;
            case 38811:
            case 37929:
                if (player.getLocation().getX() < node.getLocation().getX()) {
                    player.getInterfaceManager().open(new Component(650));
                } else {
                    player.teleport(player.getLocation().transform(player.getLocation().getX() < node.getLocation().getX() ? 4 : -4, 0, 0));
                }
                break;
        }
        return true;
    }

    @Override
    public Location getDestination(Node node, Node n) {
        return null;
    }
}
