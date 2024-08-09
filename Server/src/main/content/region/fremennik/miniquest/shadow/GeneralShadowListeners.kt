package content.region.fremennik.miniquest.shadow

import core.api.*
import core.api.consts.Scenery
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location

/**
 * General shadow listeners.
 */
class GeneralShadowListeners : InteractionListener {

    companion object {
        private const val CAVERN_ENTRANCE = Scenery.CRACK_21800
    }

    override fun defineListeners() {

        on(CAVERN_ENTRANCE, IntType.SCENERY, "Enter") { player, node ->
            if (getAttribute(player, GSUtils.GS_SEVERED_LEG, false)) {
                openDialogue(player, object : DialogueFile() {
                    override fun handle(componentID: Int, buttonID: Int) {
                        when (stage) {
                            0 -> player.dialogueInterpreter.sendDialogue("You have a bad feeling about crawling into the next cavern.").also { stage++ }
                            1 -> {
                                setTitle(player, 2)
                                sendDialogueOptions(player, "Do you want to enter the cavern?", "Yes", "No")
                                stage++
                            }
                            2 -> when (buttonID) {
                                1 -> {
                                    end()
                                    lock(player, 1000)
                                    lockInteractions(player, 1000)
                                    CavernCutscene(player).start()
                                }

                                2 -> end()
                            }
                        }
                    }
                }, node)
            } else if (player.location == location(1759, 4711, 0)) {
                teleport(player, location(2617, 9828, 0))
            } else if (getAttribute(player, GSUtils.GS_COMPLETE, false)) {
                teleport(player, Location(1759, 4711, 0))
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@on true
        }
    }
}
