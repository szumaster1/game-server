package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.BurdenBeast
import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import core.api.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.impl.Animator.Priority
import core.game.node.entity.impl.Projectile
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.tools.RandomFunction
import kotlin.math.floor

/**
 * Thorny snail familiar.
 */
@Initializable
class ThornySnailNPC(owner: Player? = null, id: Int = NPCs.THORNY_SNAIL_6806) : BurdenBeast(owner, id, 1600, 12019, 3, 3) {

    override fun construct(owner: Player, id: Int): Familiar {
        return ThornySnailNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        val target = special.node as Entity
        if (!canCombatSpecial(target)) {
            return false
        }
        visualizeSpecialMove()
        visualize(Animation(8148, Priority.HIGH), Graphic.create(1385))
        Projectile.magic(this, target, 1386, 40, 36, 51, 10).send()
        val ticks = 2 + floor(getLocation().getDistance(target.location) * 0.5).toInt()
        properties.combatPulse.setNextAttack(4)
        faceTemporary(target, 2)
        Pulser.submit(object : Pulse(ticks, this, target) {
            override fun pulse(): Boolean {
                val state = BattleState(this@ThornySnailNPC, target)
                var hit = 0
                if (CombatStyle.MAGIC.swingHandler.isAccurateImpact(this@ThornySnailNPC, target)) {
                    hit = RandomFunction.randomize(8)
                }
                state.estimatedHit = hit
                target.impactHandler.handleImpact(owner, hit, CombatStyle.MAGIC, state)
                target.graphics(Graphic(1387))
                return true
            }
        })
        return true
    }

    override fun isPoisonImmune(): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.THORNY_SNAIL_6806, NPCs.THORNY_SNAIL_6807)
    }
}
