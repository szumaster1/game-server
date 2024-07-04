package content.region.kandarin.dialogue.piscatoris

import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.plugin.Initializable
import core.tools.END_DIALOGUE


@Initializable
class KathyCorkatEaglesPeakDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Swan Song") && npc.id == 3830) {
            npcl(FacialExpression.WORRIED, "Oh dear, oh dear, oh dear...").also { stage = 0 }
        } else {
            npcl(FacialExpression.FRIENDLY, "Hello dearie. Heading up north to the Fishing Colony, are we?").also { stage = 18 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Oh dear?").also { stage++ }
            1 -> npcl(FacialExpression.WORRIED, "It's terrible!").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "What's terrible?").also { stage++ }
            3 -> npcl(FacialExpression.HALF_ASKING, "Oh, never mind, dearie. What can I do for you?").also { stage++ }
            4 -> options("Can you take me downstream in your boat?", "I don't think I want anything, thanks.").also { stage++ }
            5 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Can you take me downstream in your boat?").also { stage = 8 }
                2 -> playerl(FacialExpression.NEUTRAL, "I don't think I want anything, thanks.").also { stage = 6 }
            }
            6 -> npcl(FacialExpression.FRIENDLY, "Everybody wants something, dearie!").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "Huh? Oh, forget it!").also { stage = END_DIALOGUE }
            8 -> npcl(FacialExpression.FRIENDLY, "Ooh, it's dangerous up there at the moment!").also { stage++ }
            9 -> {
                setTitle(player, 2)
                sendDialogueOptions(player!!, "What would you like to say?", "I'm not afraid - let's go!", "Dangerous? Nah, forget it!")
                stage++
            }
            10 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "I'm not afraid - let's go!").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Dangerous? Nah, forget it!").also { stage = 6 }
            }
            11 -> npcl(FacialExpression.FRIENDLY, "It's dangerous up there for me too, dearie.").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "Would it help if I gave you some money?").also { stage++ }
            13 -> npcl(FacialExpression.FRIENDLY, "Ooh, that's better! I'll take you up north for 50 shiny coins.").also { stage++ }
            14 -> options("Alright, I'll pay.", "Not a chance!").also { stage++ }
            15 -> when (buttonId) {
                1 -> if (!removeItem(player, Item(Items.COINS_995, 50))) {
                    playerl(FacialExpression.NEUTRAL, "Sorry, I don't have enough coins for that.").also { stage = END_DIALOGUE }
                } else {
                    playerl(FacialExpression.FRIENDLY, "Alright, I'll pay.").also { stage = 17 }
                }
                2 -> playerl(FacialExpression.FRIENDLY, "Not a chance!").also { stage = 16 }
            }

            16 -> npcl(FacialExpression.FRIENDLY, "Then you can find your own way north, dearie!").also { stage = END_DIALOGUE }
            17 -> {
                end()
                lock(player, 1000)
                lockInteractions(player, 1000)
                GameWorld.Pulser.submit(object : Pulse() {
                    var counter = 0
                    override fun pulse(): Boolean {
                        when (counter++) {
                            0 -> {
                                openInterface(player, Components.FADE_TO_BLACK_120)
                                player.dialogueInterpreter.sendPlainMessage(true, "", "", "Kathy Corkat rows you down the river to the sea...", "")
                            }
                            3 -> teleport(player, location(2357, 3641, 0))
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

            18 -> options("Yes please.", "No thanks.").also { stage++ }
            19 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Yes please.").also { stage = 17 }
                2 -> playerl(FacialExpression.FRIENDLY, "No thanks.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KATHY_CORKAT_3830)
    }

}
