package content.global.skill.combat.summoning.scenery;

import content.global.skill.combat.summoning.SummoningCreator;
import core.api.consts.Sounds;
import core.cache.def.impl.SceneryDefinition;
import core.game.event.SummoningPointsRechargeEvent;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.playAudio;

/**
 * The Obelisk option plugin.
 */
@Initializable
public final class ObeliskOptionPlugin extends OptionHandler {

    @Override
    public boolean handle(Player player, Node node, String option) {
        switch (option) {
            case "infuse-pouch":
                SummoningCreator.open(player, true);
                return true;
            case "renew-points":
                if (player.getSkills().getLevel(Skills.SUMMONING) == player.getSkills().getStaticLevel(Skills.SUMMONING)) {
                    player.getPacketDispatch().sendMessage("You already have full summoning points.");
                    return true;
                }
                player.visualize(Animation.create(8502), Graphic.create(1308));
                playAudio(player, Sounds.DREADFOWL_BOOST_4214);
                player.getSkills().setLevel(Skills.SUMMONING, player.getSkills().getStaticLevel(Skills.SUMMONING));
                player.dispatch(new SummoningPointsRechargeEvent(node));
                player.getPacketDispatch().sendMessage("You renew your summoning points.");
                return true;
        }
        return false;
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        SceneryDefinition.setOptionHandler("infuse-pouch", this);
        SceneryDefinition.setOptionHandler("renew-points", this);
        return this;
    }

}
