package content.global.skill.combat.special;

import core.game.node.entity.Entity;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.combat.MeleeSwingHandler;
import core.game.node.entity.impl.Animator.Priority;
import core.game.node.entity.player.Player;
import core.game.world.GameWorld;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.tools.RandomFunction;
import org.rs.consts.Animations;
import org.rs.consts.Graphics;
import org.rs.consts.Items;
import org.rs.consts.Sounds;

import static core.api.ContentAPIKt.playGlobalAudio;

/**
 * Handles the granite maul special attack.
 * @author Emperor
 */
@Initializable
public final class QuickSmashSpecialHandler extends MeleeSwingHandler implements Plugin<Object> {

	/**
	 * The special energy required.
	 */
	private static final int SPECIAL_ENERGY = 50;

	/**
	 * The attack animation.
	 */
	private static final Animation ANIMATION = new Animation(Animations.ATTACK_WITH_MAUL_1667, Priority.HIGH);

	/**
	 * The graphic.
	 */
	private static final Graphic GRAPHIC = new Graphic(Graphics.GRANITE_MAUL_SPECIAL_340, 96);

	@Override
	public Object fireEvent(String identifier, Object... args) {
		switch (identifier) {
		case "instant_spec":
			return true;
		}
		return null;
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		CombatStyle.MELEE.getSwingHandler().register(Items.GRANITE_MAUL_4153, this);
		return this;
	}

	@Override
	public int swing(Entity entity, Entity victim, BattleState state) {
		Player p = (Player) entity;
		if (victim == null) {
			victim = p.getProperties().getCombatPulse().getLastVictim();
			if (victim == null || GameWorld.getTicks() - p.getAttribute("combat-stop", -1) > 2 || !MeleeSwingHandler.Companion.canMelee(p, victim, 1)) {
				p.getPacketDispatch().sendMessage("Warning: Since the maul's special is an instant attack, it will be wasted when used ");
				p.getPacketDispatch().sendMessage("on a first strike.");
				return -1;
			}
		}
		if (!p.getSettings().drainSpecial(SPECIAL_ENERGY)) {
			return -1;
		}
        // TODO: apply protection prayers/experience manually (since this is bypassing normal BattleState machinery)
		visualize(entity, victim, null);
		int hit = 0;
		if (isAccurateImpact(entity, victim)) {
			hit = RandomFunction.random(calculateHit(entity, victim, 1.));
		}
		victim.getImpactHandler().handleImpact(entity, hit, CombatStyle.MELEE);
		return 1;
	}

	@Override
	public void visualize(Entity entity, Entity victim, BattleState state) {
		playGlobalAudio(entity.getLocation(), Sounds.QUICKSMASH_2715);
		entity.visualize(ANIMATION, GRAPHIC);
		victim.animate(victim.getProperties().getDefenceAnimation());
	}

}
