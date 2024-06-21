package content.region.asgarnia.handlers.guilds;

import core.api.consts.NPCs;
import core.cache.def.impl.SceneryDefinition;
import core.game.dialogue.FacialExpression;
import core.game.global.action.ClimbActionHandler;
import core.game.global.action.DoorActionHandler;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.scenery.Scenery;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.getDynLevel;
import static core.api.ContentAPIKt.sendNPCDialogue;

/**
 * The Mining guild plugin.
 */
@Initializable
public final class MiningGuildPlugin extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        SceneryDefinition.forId(2113).getHandlers().put("option:climb-down", this);
        SceneryDefinition.forId(30941).getHandlers().put("option:climb-up", this);
        SceneryDefinition.forId(2112).getHandlers().put("option:open", this);
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        if (option.equals("climb-down")) {
            if (player.getLocation().withinDistance(Location.create(3019, 3339, 0), 4)) {
                if (getDynLevel(player, Skills.MINING) < 60) {
                    player.getDialogueInterpreter().open(382, NPC.create(382, Location.create(0, 0, 0)), 1);
                    return true;
                }
                ClimbActionHandler.climb(player, new Animation(828), Location.create(3021, 9739, 0));
                return true;
            }
            ClimbActionHandler.climbLadder(player, (Scenery) node, option);
            return true;
        }
        if (option.equals("open")) {
            if (getDynLevel(player, Skills.MINING) < 60) {
                sendNPCDialogue(player, NPCs.DWARF_382, "Sorry, but you need level 60 Mining to go in there.", FacialExpression.OLD_NORMAL);
                return true;
            }
            DoorActionHandler.handleAutowalkDoor(player, (Scenery) node);
        }
        if (option.equals("climb-up")) {
            if (player.getLocation().withinDistance(new Location(3019, 9739, 0))) {
                ClimbActionHandler.climb(player, new Animation(828), new Location(3017, 3339, 0));
            } else {
                ClimbActionHandler.climbLadder(player, (Scenery) node, "climb-up");
            }
        }
        return true;
    }

}
