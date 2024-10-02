package content.global.skill.combat.special;

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
import org.rs.consts.Animations;
import org.rs.consts.Graphics;
import org.rs.consts.Items;
import org.rs.consts.Sounds;

import static core.api.ContentAPIKt.playAudio;

/**
 * Handles the Rampage special attack.
 * @author Emperor
 */
@Initializable
public final class RampageSpecialHandler extends MeleeSwingHandler implements Plugin<Object> {

    /**
     * The special energy required.
     */
    private static final int SPECIAL_ENERGY = 100;

    /**
     * The attack animation.
     */
    private static final Animation ANIMATION = new Animation(Animations.DRAGON_BATTLEAXE_SPECIAL_ATTACK_1056, Priority.HIGH);

    /**
     * The graphic.
     */
    private static final Graphic GRAPHIC = new Graphic(Graphics.DRAGON_BATTLE_AXE_SPECIAL_ORIGINAL_ANIMATION_246);

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
        CombatStyle.MELEE.getSwingHandler().register(Items.DRAGON_BATTLEAXE_1377, this);
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
