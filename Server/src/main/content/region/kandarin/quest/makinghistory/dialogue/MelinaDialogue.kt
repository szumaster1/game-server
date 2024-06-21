package content.region.kandarin.quest.makinghistory.dialogue

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
class MelinaDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        if (!inEquipment(player, Items.GHOSTSPEAK_AMULET_552)) {
            npcl(FacialExpression.FRIENDLY, "wooo wooo")
            stage = 0
            return true
        }
        if (getQuestStage(player, "Making History") >= 1 && inInventory(player, Items.SAPPHIRE_AMULET_1694) && getVarbit(player,
                MHUtils.DROALAK_PROGRESS
            ) == 2) {
            playerl(FacialExpression.FRIENDLY, "If you don't mind me asking, are you Melina?")
            stage = 4
            return true
        }
        if (getQuestStage(player, "Making History") >= 1) {
            playerl(FacialExpression.FRIENDLY, "Hi.")
            stage = 1
            return true
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendDialogue(player, "You cannot understand the ghost.").also { stage = END_DIALOGUE }
            1 -> npcl(FacialExpression.FRIENDLY, "Oh why did he leave me? Did he truly love me?").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Erm. I think you're talking about Droalak. I believe he did love you and he's very sorry for leaving you!").also { stage++ }
            3 -> npcl(FacialExpression.FRIENDLY, "You're giving me empty words. That is all.").also { stage = END_DIALOGUE }
            4 -> npcl(FacialExpression.FRIENDLY, "That I am. What's it to you?").also { stage++ }
            5 -> playerl(FacialExpression.FRIENDLY, "I've been talking to Droalak. I believe he left you but never returned.").also { stage++ }
            6 -> npcl(FacialExpression.FRIENDLY, "He did. I suppose he has asked you to tell me he's sorry. What an empty gesture!").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "Well actually he told me to give you this amulet.").also { stage++ }
            8 -> npcl(FacialExpression.FRIENDLY, "A sapphire amulet! He remembers! It's just like the one he gave me before he left.").also { stage++ }
            9 -> playerl(FacialExpression.FRIENDLY, "I honestly believe he's sorry.").also { stage++ }
            10 -> npcl(FacialExpression.FRIENDLY, "I'm so glad. Please, tell him I forgive him!").also { stage++ }
            11 -> playerl(FacialExpression.FRIENDLY, "I will.").also { stage++ }
            12 -> npcl(FacialExpression.FRIENDLY, "At last I feel complete. Farewell.").also { stage++ }
            13 -> playerl(FacialExpression.FRIENDLY, "Goodbye.").also { stage++ }
            14 -> {
                end()
                if(removeItem(player, Items.SAPPHIRE_AMULET_1694)) {
                    setVarbit(player, MHUtils.DROALAK_PROGRESS, 4, true)
                    transformNpc(NPC(NPCs.MELINA_2935), NPCs.MELINA_2934, 10)
                }

            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MELINA_2935)
    }

}

