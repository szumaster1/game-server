package content.region.kandarin.ardougne.necromancer_tower.handlers

import org.rs.consts.NPCs
import core.api.getAttribute
import core.api.getPathableRandomLocalCoordinate
import core.game.node.entity.Entity
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Represents the [Summoned zombie NPC](https://runescape.wiki/w/Summoned_Zombie?oldid=1887450).
 */
@Initializable
class SummonedZombiesNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    var clearTime = 0

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return SummonedZombiesNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SUMMONED_ZOMBIE_77)
    }

    companion object {
        fun summonZombie(player: Player) {
            val summonedZombie = SummonedZombiesNPC(NPCs.SUMMONED_ZOMBIE_77)
            summonedZombie.location = getPathableRandomLocalCoordinate(summonedZombie, 1, player.location, 2)
            summonedZombie.isWalks = true
            summonedZombie.isAggressive = true
            summonedZombie.isActive = false
            summonedZombie.isRespawn = false

            if (summonedZombie.asNpc() != null && summonedZombie.isActive) {
                summonedZombie.properties.teleportLocation = summonedZombie.properties.spawnLocation
            }

            summonedZombie.isActive = true

            GameWorld.Pulser.submit(object : Pulse(0, summonedZombie) {
                override fun pulse(): Boolean {
                    if (getAttribute(player, "necro:zombie_alive", 0) >= 2) {
                        return true
                    }
                    summonedZombie.init()
                    summonedZombie.attack(player)
                    return true
                }
            })
        }
    }

    override fun finalizeDeath(killer: Entity?) {
        super.finalizeDeath(killer)
        if (killer is Player) {
            val player = killer.asPlayer()
            val counter = player.getAttribute("necro:zombie_alive", -1)
            if (counter >= 3) {
                core.api.removeAttribute(player, "necro:zombie_alive")
                return
            }
        }
        clear()
    }

}