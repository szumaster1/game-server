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
import org.rs.consts.Animations;
import org.rs.consts.Items;
import org.rs.consts.Sounds;

import static core.api.ContentAPIKt.playGlobalAudio;

/**
 * Handles the puncture special attack combat swing.
 * @author Emperor
 */
@Initializable
public final class PunctureSpecialHandler extends MeleeSwingHandler implements Plugin<Object> {

	/**
	 * The special energy required.
	 */
	private static final int SPECIAL_ENERGY = 25;

	/**
	 * The attack animation.
	 */
	private static final Animation ANIMATION = new Animation(Animations.SMITHING_WITH_SC_HAMMER_11062, Priority.HIGH);

	/**
	 * The graphic.
	 */
	private static final Graphic GRAPHIC = new Graphic(252, 96);

	@Override
	public Object fireEvent(String identifier, Object... args) {
		return null;
	}

	@Override
	public void impact(Entity entity, Entity victim, BattleState state) {
		victim.getImpactHandler().handleImpact(entity, state.getEstimatedHit(), CombatStyle.MELEE, state);
		victim.getImpactHandler().handleImpact(entity, state.getSecondaryHit(), CombatStyle.MELEE, state, null, true);
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		if (CombatStyle.MELEE.getSwingHandler().register(Items.DRAGON_DAGGER_1215, this) && CombatStyle.MELEE.getSwingHandler().register(1231, this) && CombatStyle.MELEE.getSwingHandler().register(Items.DRAGON_DAGGERP_PLUS_5680, this) && CombatStyle.MELEE.getSwingHandler().register(Items.DRAGON_DAGGERP_PLUS_PLUS_5698, this) && CombatStyle.MELEE.getSwingHandler().register(Items.DRAGON_DAGGER_13465, this) && CombatStyle.MELEE.getSwingHandler().register(Items.DRAGON_DAGGERP_13466, this) && CombatStyle.MELEE.getSwingHandler().register(Items.DRAGON_DAGGERP_PLUS_13467, this) && CombatStyle.MELEE.getSwingHandler().register(Items.DRAGON_DAGGERP_PLUS_PLUS_13468, this))
			;
		return this;
	}

	@Override
	public int swing(Entity entity, Entity victim, BattleState state) {
		if (!((Player) entity).getSettings().drainSpecial(SPECIAL_ENERGY)) {
			return -1;
		}
        state.setStyle(CombatStyle.MELEE);
		// First hit
		//double accuracyMod, double defenceMod
		int hit = 0;
		// 											 		accuracyMod defenceMod
		if (isAccurateImpact(entity, victim, CombatStyle.MELEE, 1.05, 1.0)) {
			hit = RandomFunction.random(calculateHit(entity, victim, 1.1306));
		}
		state.setEstimatedHit(hit);
		// Second hit
		// 											 		accuracyMod defenceMod
		if (isAccurateImpact(entity, victim, CombatStyle.MELEE, 1.05, 1.0)) {
			hit = RandomFunction.random(calculateHit(entity, victim, 1.1306));
		} else {
			hit = 0;
		}
		state.setSecondaryHit(hit);
		return 1;
	}

	@Override
	public void visualize(Entity entity, Entity victim, BattleState state) {
		playGlobalAudio(entity.getLocation(), Sounds.PUNCTURE_2537);
		entity.visualize(ANIMATION, GRAPHIC);
	}
}
