package content.global.handlers.item.equipment.special;

import cfg.consts.Sounds;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.combat.MeleeSwingHandler;
import core.game.node.entity.impl.Animator.Priority;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.playAudio;

/**
 * Rampage special handler.
 */
@Initializable
public final class RampageSpecialHandler extends MeleeSwingHandler implements Plugin<Object> {

    private static final int SPECIAL_ENERGY = 100;

    private static final Animation ANIMATION = new Animation(1056, Priority.HIGH);

    private static final Graphic GRAPHIC = new Graphic(246);

    @Override
    public Object fireEvent(String identifier, Object... args) {
        switch (identifier) {
            case "instant_spec":
            case "ncspec":
                return true;
        }
        return null;
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        CombatStyle.MELEE.getSwingHandler().register(1377, this);
        return this;
    }

    @Override
    public int swing(Entity entity, Entity victim, BattleState state) {
        Player p = (Player) entity;
        if (!p.getSettings().drainSpecial(SPECIAL_ENERGY)) {
            return -1;
        }
        p.sendChat("Raarrrrrgggggghhhhhhh!");
        playAudio(entity.asPlayer(), Sounds.RAMPAGE_2538);
        p.visualize(ANIMATION, GRAPHIC);
        @SuppressWarnings("unused")
        int boost = 0;
        for (int i = 0; i < 6; i++) {
            if (i == 2 || i == 3 || i == 5) {
                continue;
            }
            double drain = p.getSkills().getLevel(i) * 0.1;
            boost += drain;
            p.getSkills().updateLevel(i, (int) -drain, (int) (p.getSkills().getStaticLevel(i) - drain));
        }
        p.getSkills().updateLevel(Skills.STRENGTH, (int) (p.getSkills().getStaticLevel(Skills.STRENGTH) * 0.20));
        return -1;
    }

}
