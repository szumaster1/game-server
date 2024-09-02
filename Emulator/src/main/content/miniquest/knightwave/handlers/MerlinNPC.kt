package content.miniquest.knightwave.handlers

import cfg.consts.NPCs
import content.miniquest.knightwave.dialogue.MerlinDialogue
import core.api.*
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Represents the Merlin NPC.
 */
@Initializable
class MerlinNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    private var cleanTime = 0
    private var player: Player? = null

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return MerlinNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MERLIN_213)
    }

    override fun handleTickActions() {
        super.handleTickActions()
        player?.let {
            if (isPlayerInactive(it)) {
                removeAttributes(it, KWUtils.KW_TIER, KWUtils.KW_BEGIN)
                poofClear(this)
            }
        }
    }

    private fun isPlayerInactive(player: Player): Boolean {
        return player.location.getDistance(getLocation()) > 8 ||
                !player.isActive ||
                cleanTime++ > 60 ||
                !player.interfaceManager.isOpened
    }

    companion object {
        fun spawnMerlin(player: Player) {
            val merlin = MerlinNPC(NPCs.MERLIN_213).apply {
                location = Location.create(2750, 3505, 2)
                isWalks = false
                isAggressive = false
                isActive = false
            }

            if (merlin.asNpc() != null && merlin.isActive) {
                merlin.properties.teleportLocation = merlin.properties.spawnLocation
            }
            merlin.isActive = true
            GameWorld.Pulser.submit(object : Pulse(1, merlin) {
                override fun pulse(): Boolean {
                    player.lock()
                    merlin.init()
                    face(findLocalNPC(player, NPCs.MERLIN_213)!!, player, 3)
                    setAttribute(player, KWUtils.KW_COMPLETE, true)
                    openDialogue(player, MerlinDialogue())
                    return true
                }
            })
        }
    }
}