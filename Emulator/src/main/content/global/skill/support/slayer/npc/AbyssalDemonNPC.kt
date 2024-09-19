package content.global.skill.support.slayer.npc

import content.global.skill.support.slayer.data.Tasks
import org.rs.consts.Graphics
import core.api.getPathableRandomLocalCoordinate
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatSwingHandler
import core.game.node.entity.combat.MeleeSwingHandler
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Represents the Abyssal demon NPC.
 */
@Initializable
class AbyssalDemonNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun getSwingHandler(swing: Boolean): CombatSwingHandler {
        return SWING_HANDLER
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return AbyssalDemonNPC(id, location)
    }

    override fun getIds(): IntArray {
        return Tasks.ABYSSAL_DEMONS.npcs
    }

    companion object {
        private val SWING_HANDLER: MeleeSwingHandler = object : MeleeSwingHandler() {
            override fun swing(entity: Entity?, victim: Entity?, state: BattleState?): Int {
                if (victim is Player && RandomFunction.random(8) <= 2) {
                    val npc = RandomFunction.random(100) <= 50
                    val source: Entity = if (npc) victim else entity!!
                    val teleported = if (npc) entity else victim
                    val loc = getPathableRandomLocalCoordinate(teleported!!, 1, source.location, 3)
                    teleported.graphics(Graphic.create(Graphics.ABYSSAL_DEMONS_TELEPORT_409))
                    teleported.teleport(loc, 1)
                }
                return super.swing(entity, victim, state)
            }
        }
    }
}
