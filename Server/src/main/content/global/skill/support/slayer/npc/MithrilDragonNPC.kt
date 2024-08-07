package content.global.skill.support.slayer.npc

import content.global.handlers.item.equipment.special.DragonfireSwingHandler
import content.global.skill.support.slayer.data.Tasks
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.combat.CombatSwingHandler
import core.game.node.entity.combat.MultiSwingHandler
import core.game.node.entity.combat.equipment.SwitchAttack
import core.game.node.entity.impl.Animator.Priority
import core.game.node.entity.impl.Projectile
import core.game.node.entity.npc.AbstractNPC
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Mithril dragon NPC.
 */
@Initializable
class MithrilDragonNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    private val combatAction: CombatSwingHandler = MultiSwingHandler(
        true,
        SwitchAttack(CombatStyle.MELEE.swingHandler, Animation(80, Priority.HIGH)),
        SwitchAttack(CombatStyle.MELEE.swingHandler, Animation(80, Priority.HIGH)),
        SwitchAttack(CombatStyle.MAGIC.swingHandler, Animation(81, Priority.HIGH), null, null, Projectile.create(null as Entity?, null, 500, 20, 20, 41, 40, 18, 255)),
        DRAGONFIRE,
        SwitchAttack(CombatStyle.RANGE.swingHandler, Animation(81, Priority.HIGH), null, null, Projectile.create(null as Entity?, null, 16, 20, 20, 41, 40, 18, 255))
    )

    override fun sendImpact(state: BattleState) {
        val style = state.style ?: return
        var maxHit = state.estimatedHit
        if (maxHit < 1) {
            return
        }
        maxHit = when (style) {
            CombatStyle.MELEE -> 28
            CombatStyle.MAGIC -> 18
            CombatStyle.RANGE -> 18
        }
        if (state.estimatedHit > maxHit) {
            state.estimatedHit = RandomFunction.random(maxHit - 5, maxHit)
        }
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return MithrilDragonNPC(id, location)
    }

    override fun getSwingHandler(swing: Boolean): CombatSwingHandler {
        return combatAction
    }

    override fun getDragonfireProtection(fire: Boolean): Int {
        return 0x2 or 0x4 or 0x8
    }

    override fun getIds(): IntArray {
        return Tasks.MITHRIL_DRAGONS.npcs
    }

    companion object {
        private val DRAGONFIRE: SwitchAttack = DragonfireSwingHandler.get(false, 52, Animation(81, Priority.HIGH), Graphic.create(1), null, null)
    }
}
