package content.miniquest.knightwave.npc

import cfg.consts.NPCs
import content.miniquest.knightwave.KnightWaves
import content.miniquest.knightwave.dialogue.MerlinDialogue
import core.api.*
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Merlin NPC.
 */
@Initializable
class MerlinNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    /*
     * NPC spawn after complete Knight Waves training ground.
     */

    private var cleanTime = 0
    private val player: Player? = null

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return MerlinNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MERLIN_213)
    }

    override fun handleTickActions() {
        super.handleTickActions()
        if (player != null) {
            if (player.location.getDistance(getLocation()) > 8 || !player.isActive || cleanTime++ > 60 || !player.interfaceManager.isOpened) {
                removeAttributes(player, KnightWaves.KW_TIER, KnightWaves.KW_BEGIN)
                poofClear(this)
            }
        }
    }

    companion object {
        fun spawnMerlin(player: Player) {
            val merlin = MerlinNPC(NPCs.MERLIN_213)
            merlin.location = Location.create(2750, 3505, 2)
            merlin.isWalks = false
            merlin.isAggressive = false
            merlin.isActive = false

            if (merlin.asNpc() != null && merlin.isActive) {
                merlin.properties.teleportLocation = merlin.properties.spawnLocation
            }
            merlin.isActive = true
            GameWorld.Pulser.submit(object : Pulse(1, merlin) {
                override fun pulse(): Boolean {
                    player.lock()
                    merlin.init()
                    face(findLocalNPC(player, NPCs.MERLIN_213)!!, player, 3)
                    setAttribute(player, KnightWaves.KW_COMPLETE, true)
                    openDialogue(player, MerlinDialogue())
                    return true
                }
            })
        }
    }
}