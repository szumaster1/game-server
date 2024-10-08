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
import core.tools.RandomFunction;
import org.rs.consts.Graphics;
import org.rs.consts.Items;
import org.rs.consts.Sounds;

import static core.api.ContentAPIKt.playGlobalAudio;

/**
 * Handles Darklight's special attack, Weaken.
 * @author Crash
 */
@Initializable
public final class WeakenSpecialHandler extends MeleeSwingHandler implements Plugin<Object> {

    /**
     * The special energy required.
     */
    private static final int SPECIAL_ENERGY = 50;

    /**
     * The attack animation.
     */
    private static final Animation ANIMATION = new Animation(2890, Priority.HIGH);

    /**
     * The graphic.
     */
    private static final Graphic GRAPHIC = new Graphic(Graphics.DARK_LIGHT_SPECIAL_483);

    @Override
    public Object fireEvent(String identifier, Object... args) {
        return null;
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        CombatStyle.MELEE.getSwingHandler().register(Items.DARKLIGHT_6746, this);
        return this;
    }

    @Override
    public int swing(Entity entity, Entity victim, BattleState state) {
        if (!((Player) entity).getSettings().drainSpecial(SPECIAL_ENERGY)) {
            return -1;
        }
        state.setStyle(CombatStyle.MELEE);
        int hit = 0;
        if (isAccurateImpact(entity, victim, CombatStyle.MELEE, 1.0, 1.0)) {
            hit = RandomFunction.random(calculateHit(entity, victim, 1.0));
        }
        state.setEstimatedHit(hit);
        if (victim instanceof Player) {
            ((Player) victim).getPacketDispatch().sendMessage("You have been drained.");
        }
        int lower = (int) (victim.getSkills().getLevel(Skills.DEFENCE) * 0.05);
        victim.getSkills().updateLevel(Skills.DEFENCE, -lower, 0);
        int lower2 = (int) (victim.getSkills().getLevel(Skills.ATTACK) * 0.05);
        victim.getSkills().updateLevel(Skills.ATTACK, -lower2, 0);
        int lower3 = (int) (victim.getSkills().getLevel(Skills.STRENGTH) * 0.05);
        victim.getSkills().updateLevel(Skills.STRENGTH, -lower3, 0);
        return hit;
    }

    @Override
    public void visualize(Entity entity, Entity victim, BattleState state) {
        entity.visualize(ANIMATION, GRAPHIC);
        playGlobalAudio(entity.getLocation(), Sounds.DARKLIGHT_WEAKEN_225);
    }
}
