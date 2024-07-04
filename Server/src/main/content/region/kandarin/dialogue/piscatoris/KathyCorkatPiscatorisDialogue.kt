package content.region.kandarin.dialogue.piscatoris

import core.api.*
import core.api.consts.Components
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class KathyCorkatPiscatorisDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Swan Song")) {
            npcl(FacialExpression.FRIENDLY, "I'll be glad to get away from this place, dearie! Fancy a lift up the river?").also { stage = 0 }
        } else {
            npcl(FacialExpression.FRIENDLY, "Are ye wantin' a lift up the river, dearie?").also { stage = 3 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes please.", "No thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Yes please.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "No thanks.").also { stage = END_DIALOGUE }
            }

            2 -> {
                end()
                lock(player, 1000)
                lockInteractions(player, 1000)
                GameWorld.Pulser.submit(object : Pulse() {
                    var counter = 0
                    override fun pulse(): Boolean {
                        when (counter++) {
                            0 -> {
                                player.dialogueInterpreter.sendPlainMessage(true, "", "", "Kathy Corkat rows you up the river...", "")
                                openInterface(player, Components.FADE_TO_BLACK_120)
                            }
                            3 -> teleport(player, location(2369, 3484, 0))
                            4 -> openInterface(player, Components.FADE_FROM_BLACK_170)
                            8 -> {
                                player.interfaceManager.closeChatbox()
                                openInterface(player, Components.CHATDEFAULT_137)
                                unlock(player)
                            }
                        }
                        return false
                    }
                })
            }

            3 -> {
                setTitle(player, 3)
                sendDialogueOptions(player!!, "What would you like to say?", "Why don't you row right into the Colony?", "Yes please.", "No thanks.")
                stage++
            }
            4 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "What would you like to say?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Yes please.").also { stage = 2 }
                3 -> playerl(FacialExpression.FRIENDLY, "No thanks.").also { stage = END_DIALOGUE }
            }
            5 -> playerl(FacialExpression.FRIENDLY, "Why don't you row right into the Colony? It'd make it much easier to get in there.").also { stage++ }
            6 -> npcl(FacialExpression.FRIENDLY, "Oh, Franklin doesn't like it if I take my boat too close. He says I'll damage the nets and scare the fish. But were you wanting a lift up to the river?").also { stage = 0 }
        }
        return true
    }


    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KATHY_CORKAT_3831)
    }

}
