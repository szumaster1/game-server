package core.game.world.update

import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.GroundItemManager
import core.game.world.map.RegionManager
import core.game.world.repository.InitializingNodeList
import core.game.world.repository.Repository
import core.network.packet.PacketRepository
import core.network.packet.context.PlayerContext
import core.network.packet.outgoing.ClearMinimapFlag
import core.integration.grafana.Grafana

/**
 * The entity update sequence.
 * @author Emperor
 */
class UpdateSequence {
    var lobbyList: List<Player>? = null
    var playersList: List<Player>? = null
    var npcList: List<NPC>? = null

    /**
     * Starts the update sequence.
     * @return `True` if we should continue.
     */
    fun start() {
        lobbyList = Repository.lobbyPlayers
        playersList = rendererPlayers
        npcList = Repository.renderableNpcs
        lobbyList!!.map { PacketRepository.send(ClearMinimapFlag::class.java, PlayerContext(it)) }

        var npcTickStart = System.currentTimeMillis()
        npcList!!.forEach(NPC::tick)
        Grafana.npcTickTime = (System.currentTimeMillis() - npcTickStart).toInt()

        var playerTickStart = System.currentTimeMillis()
        rendererPlayers.forEach(Player::tick)
        Grafana.playerTickTime = (System.currentTimeMillis() - playerTickStart).toInt()
    }

    /**
     * Runs the updating part of the sequence.
     */
    fun run() {
        var playerRenderStart = System.currentTimeMillis()
        rendererPlayers.forEach(Player::update)
        Grafana.playerRenderTime = (System.currentTimeMillis() - playerRenderStart).toInt()
    }

    fun end() {
        playersList!!.forEach(Player::reset)
        npcList!!.forEach(NPC::reset)
        rendererPlayers.sync()
        RegionManager.pulse()
        GroundItemManager.pulse()
    }

    /**
     * Terminates the update sequence.
     */
    fun terminate() {
    }

    companion object {

        @JvmStatic
        val rendererPlayers = InitializingNodeList<Player>()

    }
}
