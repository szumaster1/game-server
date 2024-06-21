package content.global.skill.support.agility.shortcuts;

import content.global.skill.support.agility.AgilityHandler;
import core.api.consts.Scenery;
import core.cache.def.impl.SceneryDefinition;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.diary.DiaryType;
import core.game.node.entity.skill.Skills;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.sendMessage;

@Initializable
public class MossGiantRopeShortcut extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        SceneryDefinition.forId(Scenery.ROPESWING_2322).getHandlers().put("option:swing-on", this);
        SceneryDefinition.forId(Scenery.ROPESWING_2323).getHandlers().put("option:swing-on", this);
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        if (!player.getLocation().withinDistance(node.getLocation(), 4)) {
            player.sendMessage("I can't reach that.");
            return true;
        }
        if (player.getSkills().getStaticLevel(Skills.AGILITY) < 10) {
            sendMessage(player, "You need an agility level of at least 10 in order to do that.");
            return true;
        }
        Location end = node.getId() == Scenery.ROPESWING_2322 ? Location.create(2704, 3209, 0) : Location.create(2709, 3205, 0);
        player.getPacketDispatch().sendSceneryAnimation(node.asScenery(), Animation.create(497), true);
        AgilityHandler.forceWalk(player, 0, player.getLocation(), end, Animation.create(751), 50, 22, "You skillfully swing across.", 1);
        player.getAchievementDiaryManager().finishTask(player, DiaryType.KARAMJA, 0, 1);
        return true;
    }

    @Override
    public Location getDestination(Node node, Node n) {
        return n.getId() == Scenery.ROPESWING_2322 ? Location.create(2709, 3209, 0) : Location.create(2705, 3205, 0);
    }
}
