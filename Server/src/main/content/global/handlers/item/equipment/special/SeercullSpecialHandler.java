package content.global.handlers.item.equipment.special;

import core.api.consts.Sounds;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.combat.RangeSwingHandler;
import core.game.node.entity.impl.Projectile;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.utilities.RandomFunction;

import static core.api.ContentAPIKt.playGlobalAudio;

/**
 * The Seercull special handler.
 */
@Initializable
public final class SeercullSpecialHandler extends RangeSwingHandler implements Plugin<Object> {

    private static final int SPECIAL_ENERGY = 100;

    private static final Graphic DRAWBACK_GFX = new Graphic(472, 96);

    @Override
    public Object fireEvent(String identifier, Object... args) {
        return null;
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        CombatStyle.RANGE.getSwingHandler().register(6724, this);
        return this;
    }

    @Override
    public int swing(Entity entity, Entity victim, BattleState state) {
        Player p = (Player) entity;
        configureRangeData(p, state);
        if (state.getWeapon() == null || !Companion.hasAmmo(entity, state)) {
            entity.getProperties().getCombatPulse().stop();
            p.getSettings().toggleSpecialBar();
            return -1;
        }
        if (!((Player) entity).getSettings().drainSpecial(SPECIAL_ENERGY)) {
            return -1;
        }
        int hit = 0;
        if (isAccurateImpact(entity, victim, CombatStyle.RANGE, 1.05, 1.0)) {
            hit = RandomFunction.random(calculateHit(entity, victim, 1.0));
            victim.getSkills().updateLevel(Skills.MAGIC, -hit, 0);
        }
        Companion.useAmmo(entity, state, victim.getLocation());
        state.setEstimatedHit(hit);
        return 1;
    }

    @Override
    public void visualize(Entity entity, Entity victim, BattleState state) {
        playGlobalAudio(entity.getLocation(), Sounds.SOULSHOT_2546);
        victim.graphics(new Graphic(474));
        int speed = (int) (35 + (entity.getLocation().getDistance(victim.getLocation()) * 10));
        entity.visualize(entity.getProperties().getAttackAnimation(), DRAWBACK_GFX);
        Projectile.create(entity, victim, 473, 40, 40, 40, speed, 15, 11).send();
    }

    @Override
    public void impact(final Entity entity, final Entity victim, final BattleState state) {
        int hit = state.getEstimatedHit();
        victim.getImpactHandler().handleImpact(entity, hit, CombatStyle.RANGE, state);
    }
}
