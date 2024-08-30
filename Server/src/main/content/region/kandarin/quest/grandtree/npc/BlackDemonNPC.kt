package content.region.kandarin.quest.grandtree.npc

import cfg.consts.NPCs
import core.api.location
import core.api.setQuestStage
import core.game.node.entity.Entity
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Direction
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Black demon NPC.
 */
@Initializable
class BlackDemonNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun construct(id: Int, location: Location?, vararg objects: Any?): AbstractNPC {
        return BlackDemonNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BLACK_DEMON_4702)
    }

    companion object {
        fun spawnBlackDemon(player: Player) {
            val demon = BlackDemonNPC(NPCs.BLACK_DEMON_4702)
            demon.location = location(2476, 9864, 0).transform(Direction.EAST, 0)
            demon.isWalks = false
            demon.isAggressive = true
            demon.isActive = false

            if (demon.asNpc() != null && demon.isActive) {
                demon.properties.teleportLocation = demon.properties.spawnLocation
            }
            demon.isActive = true
            GameWorld.Pulser.submit(object : Pulse(0, demon) {
                override fun pulse(): Boolean {
                    demon.init()
                    demon.attack(player)
                    return true
                }
            })
        }
    }

    override fun finalizeDeath(killer: Entity?) {
        // In the event that this npcID is used somewhere else...
        if (killer!!.asPlayer().location.regionId == 9882) {
            setQuestStage(killer.asPlayer(), "The Grand Tree", 98)
            this.isRespawn = false
        }
        clear()
        super.finalizeDeath(killer)
    }
}
