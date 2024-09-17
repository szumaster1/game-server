package content.region.wilderness.handlers.npc

import org.rs.consts.NPCs
import core.api.impact
import core.api.isPoisoned
import core.api.isStunned
import core.api.sendMessage
import core.game.node.entity.Entity
import core.game.node.entity.combat.ImpactHandler.HitsplatType
import core.game.node.entity.impl.Projectile
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.npc.NPC
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Represents the Dark energy core NPC.
 */
@Initializable
class DarkEnergyCoreNPC @JvmOverloads constructor(id: Int = 8127, location: Location? = null) : AbstractNPC(id, location, false) {

    private var master: NPC? = null
    private var ticks = 0
    private var fails = 0

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        val core = DarkEnergyCoreNPC(id, location)
        if (objects.isNotEmpty()) {
            core.master = objects[0] as NPC
        }
        return core
    }

    override fun canStartCombat(victim: Entity): Boolean {
        return false //No combat needed.
    }

    override fun handleTickActions() {
        ticks++
        val poisoned = isPoisoned(this)
        if (isStunned(this) || isInvisible) {
            return
        }
        if (fails == 0 && poisoned && (ticks % 100) != 0) {
            return
        }
        if (ticks % 2 == 0) {
            var jump = true
            for (player in viewport.currentPlane.players) {
                if (player.location.withinDistance(getLocation(), 1)) {
                    jump = false
                    val hit = 5 + RandomFunction.random(6)
                    impact(master!!, hit, HitsplatType.NORMAL)
                    master!!.getSkills().heal(hit)
                    sendMessage(player, "The dark core creature steals some life from you for its master.")
                }
            }
            if (jump) {
                val victim = master!!.properties.combatPulse.getVictim()
                if (++fails >= 3 && victim != null && victim.viewport.currentPlane == viewport.currentPlane) {
                    jump(victim.location)
                    fails = 0
                }
            } else {
                fails = 0
            }
        }
    }

    private fun jump(location: Location) {
        isInvisible = true
        Projectile.create(getLocation(), location, 1828, 0, 0, 0, 60, 20, 0).send()
        Pulser.submit(object : Pulse(2, this) {
            override fun pulse(): Boolean {
                properties.teleportLocation = location
                isInvisible = false
                return true
            }
        })
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DARK_ENERGY_CORE_8127)
    }
}
