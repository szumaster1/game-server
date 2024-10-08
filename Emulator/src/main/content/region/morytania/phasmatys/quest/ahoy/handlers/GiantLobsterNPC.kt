package content.region.morytania.phasmatys.quest.ahoy.handlers

import core.api.*
import org.rs.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location

/**
 * Represents the Giant lobster NPC.
 */
class GiantLobsterNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return GiantLobsterNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GIANT_LOBSTER_1693)
    }

    companion object {
        fun spawnGiantLobster(player: Player) {
            val giantLobster = GiantLobsterNPC(NPCs.GIANT_LOBSTER_1693)
            giantLobster.location = location(3617, 3543, 0)
            giantLobster.isWalks = true
            giantLobster.isAggressive = true
            giantLobster.isActive = false

            if (giantLobster.asNpc() != null && giantLobster.isActive) {
                giantLobster.properties.teleportLocation = giantLobster.properties.spawnLocation
            }
            giantLobster.isActive = true
            GameWorld.Pulser.submit(object : Pulse(1, giantLobster) {
                override fun pulse(): Boolean {
                    giantLobster.init()
                    sendMessage(player, "You are attacked by a giant lobster!!")
                    registerHintIcon(player, giantLobster)
                    giantLobster.attack(player)
                    return true
                }
            })
        }
    }

    override fun finalizeDeath(killer: Entity?) {
        if (killer is Player) {
            clearHintIcon(killer)
            setAttribute(killer, GhostsAhoyUtils.lastMapScrap, true)
        }
        clear()
        super.finalizeDeath(killer)
    }

}