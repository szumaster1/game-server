package content.region.kandarin.quest.makinghistory.dialogue

import content.region.kandarin.quest.biohazard.dialogue.KingLathasDialogueFile
import content.region.kandarin.quest.makinghistory.MHUtils
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class KingLathasDialogue(player: Player? = null) : Dialogue(player) {
    
    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (getQuestStage(player, "Biohazard") in 16..100) {
            openDialogue(player, KingLathasDialogueFile())
            return true
        } else if (getVarbit(player, MHUtils.PROGRESS) == 3 && inInventory(player, Items.LETTER_6756)) {
            npcl(FacialExpression.FRIENDLY, "What would you like to talk about?").also { stage = 1 }
            return true
        } else if (inInventory(player, Items.LETTER_6757)) {
            npcl(FacialExpression.FRIENDLY, "Have you taken that letter to Jorral yet?").also { stage = 11 }
            return true
        } else if (!inInventory(player, Items.LETTER_6757)) {
            playerl(FacialExpression.FRIENDLY, "Excuse me sire, but I seem to have lost that letter you gave me.").also { stage = 14 }
            return true
        } else {
            sendMessage(player, "The King Lathas is not interested in talking.")
            return true
        }
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        npc = NPC(NPCs.KING_LATHAS_364)
        when (stage) {
            1 -> options("Jorral and the outpost", "West Ardougne").also { stage++ }
            2 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Jorral and the outpost").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "West Ardougne").also { stage++ }
            }
            3 -> playerl(FacialExpression.FRIENDLY, "Excuse me. I have been asked to hand you this from Jorral at the outpost.").also { stage++ }
            4 -> npcl(FacialExpression.FRIENDLY, "I see.").also { stage++ }
            5 -> sendDoubleItemDialogue(player, -1,Items.LETTER_6756, "The King reads the letter.").also { stage++ }
            6 -> npcl(FacialExpression.FRIENDLY, "I had no idea that place had any value at all! All this about my great-grandfather and Jorral's plans to make it into a museum makes for a convincing case.").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "I am sure he only wants what is best.").also { stage++ }
            8 -> npcl(FacialExpression.FRIENDLY, "Very well, I will comply with his request. Take this letter back to him with my kind regards.").also { stage++ }
            9 -> playerl(FacialExpression.FRIENDLY, "Thank you.").also { stage++ }
            10 -> {
                if (removeItem(player, Items.LETTER_6756)) {
                    end()
                    addItemOrDrop(player, Items.LETTER_6757)
                    sendDoubleItemDialogue(player, -1,Items.LETTER_6757, "Player receives Letter.")
                    setQuestStage(player, "Making History", 99)
                }
            }
            11 -> playerl(FacialExpression.FRIENDLY, "I'm working on it!").also { stage = END_DIALOGUE }
            14 -> npcl(FacialExpression.FRIENDLY, "Very well, take another.").also { stage++ }
            15 -> {
                end()
                addItemOrDrop(player, Items.LETTER_6757)
                sendDoubleItemDialogue(player, -1,Items.LETTER_6757, "Player receives Letter.")
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KING_LATHAS_364)
    }
}

