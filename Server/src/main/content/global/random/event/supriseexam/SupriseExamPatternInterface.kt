package content.global.random.event.supriseexam

import core.api.closeInterface
import core.api.consts.Components
import core.api.consts.NPCs
import core.api.getAttribute
import core.api.openDialogue
import core.game.interaction.InterfaceListener
import core.game.node.entity.npc.NPC

class SupriseExamPatternInterface : InterfaceListener {

    val COMPONENT = Components.PATTERN_NEXT_103

    override fun defineInterfaceListeners() {

        on(COMPONENT) { player, _, _, buttonID, _, _ ->
            val index = buttonID - 10
            val correctIndex = getAttribute(player, SupriseExamUtils.SE_KEY_INDEX, 0)
            closeInterface(player)

            if (index == correctIndex) {
                player.incrementAttribute(SupriseExamUtils.SE_KEY_CORRECT)
                val done = getAttribute(player, SupriseExamUtils.SE_KEY_CORRECT, 0) == 3
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
            SupriseExamUtils.generateInterface(player)
            return@onOpen true
        }

    }

}