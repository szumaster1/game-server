package content.region.kandarin.guild.ranging;

import core.cache.def.impl.SceneryDefinition;
import core.game.component.Component;
import core.game.container.impl.EquipmentContainer;
import core.game.global.action.ClimbActionHandler;
import core.game.global.action.DoorActionHandler;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.world.map.Location;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.setAttribute;

@Initializable
public final class RangingGuildPlugin extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        SceneryDefinition.forId(2514).getHandlers().put("option:open", this);
        SceneryDefinition.forId(2511).getHandlers().put("option:climb-up", this);
        SceneryDefinition.forId(2512).getHandlers().put("option:climb-down", this);
        SceneryDefinition.forId(2513).getHandlers().put("option:fire-at", this);
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        final int id = node instanceof Scenery ? node.getId() : 0;
        switch (option) {
            case "fire-at":
                if (player.getArcheryTargets() <= 0) {
                    player.getDialogueInterpreter().sendDialogues(693, null, "Sorry, you may only use the targets for the", "competition, not for practicing.");
                    return true;
                }
                if (!player.getEquipment().containsItem(new Item(882))
                        || player.getEquipment().get(EquipmentContainer.SLOT_WEAPON) == null
                        || (!player.getEquipment().get(EquipmentContainer.SLOT_WEAPON).getDefinition().getName().toLowerCase().contains("shortbow")
                        && !player.getEquipment().get(EquipmentContainer.SLOT_WEAPON).getDefinition().getName().toLowerCase().contains("longbow"))) {
                    player.sendMessage("You must have bronze arrows and a bow equipped.");
                    return true;
                }
                player.getPulseManager().run(new ArcheryCompetitionPulse(player, (Scenery) node));
                break;
            case "open":
                switch (id) {
                    case 2514:
                        if (player.getLocation().getY() >= 3438) {
                            if (player.getSkills().getStaticLevel(Skills.RANGE) < 40) {
                                player.getDialogueInterpreter().sendDialogue("You need a Ranging level of 40 to enter here.");
                                return true;
                            }
                        }
                        DoorActionHandler.handleAutowalkDoor(player, (Scenery) node, player.getLocation().getY() >= 3438 ? Location.create(2659, 3437, 0) : Location.create(2657, 3439, 0));
                        break;
                }
                break;
            case "climb-up":
                switch (id) {
                    case 2511:
                        setAttribute(player, "ladder", node);
                        player.getInterfaceManager().open(new Component(564));
                        break;
                }
                break;
            case "climb-down":
                switch (id) {
                    case 2512:
                        ClimbActionHandler.climb(player, null, Location.create(2668, 3427, 0));
                        break;
                }
                break;
        }
        return true;
    }

    @Override
    public Location getDestination(Node node, Node n) {
        if (n instanceof Scenery) {
            if (((Scenery) n).getDefinition().hasAction("open")) {
                if (n.getId() == 2514) {
                    if (node.getLocation().getY() >= 3438) {
                        return Location.create(2657, 3439, 0);
                    } else {
                        return Location.create(2659, 3437, 0);
                    }
                }
                return DoorActionHandler.getDestination((Player) node, (Scenery) n);
            }
            if (n.getId() == 2513)
                return Location.create(2673, 3420, 0);
        }
        return null;
    }

}
