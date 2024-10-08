package content.global.skill.combat.special;

import core.game.node.entity.Entity;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.combat.MeleeSwingHandler;
import core.game.node.entity.impl.Animator.Priority;
import core.game.node.entity.player.Player;
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
 * Handles the healing blade special attack.
 * @author Emperor
 */
@Initializable
public final class HealingBladeSpecialHandler extends MeleeSwingHandler implements Plugin<Object> {

    /**
     * The special energy required.
     */
    private static final int SPECIAL_ENERGY = 50;

    /**
     * The attack animation.
     */
    private static final Animation ANIMATION = new Animation(7071, Priority.HIGH);

    /**
     * The graphic.
     */
    private static final Graphic GRAPHIC = new Graphic(Graphics.SARADOMIN_GODSWORD_SPECIAL_ATTACK_1220);

    @Override
    public Object fireEvent(String identifier, Object... args) {
        return null;
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        CombatStyle.MELEE.getSwingHandler().register(Items.SARADOMIN_GODSWORD_11698, this);
        CombatStyle.MELEE.getSwingHandler().register(Items.SARADOMIN_GODSWORD_13452, this);
        return this;
    }

    @Override
    public int swing(Entity entity, Entity victim, BattleState state) {
        if (!((Player) entity).getSettings().drainSpecial(SPECIAL_ENERGY)) {
            return -1;
        }
        state.setStyle(CombatStyle.MELEE);
        int hit = 0;
        if (isAccurateImpact(entity, victim, CombatStyle.MELEE, 1.12, 0.98)) {
            hit = RandomFunction.random(calculateHit(entity, victim, 1.005));
        }
        state.setEstimatedHit(hit);
        int healthRestore = hit / 2;
        double prayerRestore = hit * 0.25;
        if (healthRestore < 10) {
            healthRestore = 10;
        }
        if (prayerRestore < 5) {
            prayerRestore = 5;
        }
        entity.getSkills().heal(healthRestore);
        entity.getSkills().incrementPrayerPoints(prayerRestore);
        return 1;
    }

    @Override
    public void visualize(Entity entity, Entity victim, BattleState state) {
        playGlobalAudio(entity.getLocation(), Sounds.GODWARS_SARADOMIN_SPECIAL_3857);
        entity.visualize(ANIMATION, GRAPHIC);
    }

}
