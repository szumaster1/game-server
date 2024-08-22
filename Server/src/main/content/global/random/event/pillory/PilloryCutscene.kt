package content.global.random.event.pillory

import cfg.consts.Components
import cfg.consts.NPCs
import core.api.location
import core.api.openInterface
import core.api.sendChat
import core.api.teleport
import core.game.activity.Cutscene
import core.game.node.entity.player.Player

/**
 * Pillory cutscene.
 */
class PilloryCutscene(player: Player) : Cutscene(player) {

    override fun setup() {
        setExit(player.location.transform(0, 0, 0))
        loadRegion(10288)
        getNPC(TRUMP)
    }

    companion object {
        const val TRUMP = NPCs.TRAMP_2794
        val COMPLETE_RANDOM = "pillory:complete"
    }

    override fun runStage(stage: Int) {
        when (stage) {
            0 -> {
                teleport(getNPC(TRUMP)!!, 44, 29)
                teleport(player, location(46, 33, 0))
                sendChat(getNPC(TRUMP)!!, "Take that, you thief!")
                timedUpdate(1)
            }

            1 -> {
                openInterface(player, Components.MACRO_PILLORY_GUARD_188)
            }

            2 -> {
                end()
            }
        }
    }
}