package content.global.random.event.surpriseexam

import core.api.closeInterface
import cfg.consts.Components
import cfg.consts.NPCs
import core.api.getAttribute
import core.api.openDialogue
import core.api.sendMessage
import core.game.interaction.InterfaceListener
import core.game.node.entity.npc.NPC

/**
 * Surprise exam interface.
 */
class SurpriseExamInterface : InterfaceListener {

    val COMPONENT = Components.PATTERN_NEXT_103

    override fun defineInterfaceListeners() {

        on(COMPONENT) { player, _, _, buttonID, _, _ ->
            val index = buttonID - 10
            val correctIndex = getAttribute(player, SurpriseExamUtils.SE_KEY_INDEX, 0)
            sendMessage(player, "Pick the object that should come next in the pattern.")
            closeInterface(player)
            if (index == correctIndex) {
                player.incrementAttribute(SurpriseExamUtils.SE_KEY_CORRECT)
                val done = getAttribute(player, SurpriseExamUtils.SE_KEY_CORRECT, 0) == 3
                openDialogue(
                    player, MordautDialogue(examComplete = done, questionCorrect = true, fromInterface = true), NPC(
                        NPCs.MR_MORDAUT_6117
                    )
                )
            } else {
                openDialogue(
                    player, MordautDialogue(examComplete = false, questionCorrect = false, fromInterface = true), NPC(
                        NPCs.MR_MORDAUT_6117
                    )
                )
            }
            return@on true
        }

        onOpen(COMPONENT) { player, _ ->
            SurpriseExamUtils.generateInterface(player)
            return@onOpen true
        }

    }

}
