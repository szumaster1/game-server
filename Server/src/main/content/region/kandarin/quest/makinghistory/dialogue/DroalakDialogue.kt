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
import core.tools.END_DIALOGUE

@Initializable
class DroalakDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        if (!inEquipment(player, Items.GHOSTSPEAK_AMULET_552)) {
            npcl(FacialExpression.FRIENDLY, "wooo wooo")
            stage = 31
            return true
        } else if (inEquipment(player, Items.GHOSTSPEAK_AMULET_552) && getQuestStage(player, "Making History") < 1) {
            npcl(FacialExpression.FRIENDLY, "Please, leave me alone.")
            stage = 24
            return true
        }
        if (getVarbit(player, MHUtils.DROALAK_PROGRESS) == 0 || getQuestStage(player, "Making History") >= 1) {
            playerl(FacialExpression.FRIENDLY, "Hello. Are you Droalak?")
            stage = 1
            return true
        } else if (!inInventory(player, Items.SAPPHIRE_AMULET_1694) && getVarbit(player, MHUtils.DROALAK_PROGRESS) == 2) {
            playerl(FacialExpression.FRIENDLY, "What do you want me to do again?")
            stage = 17
            return true
        } else if (inInventory(player, Items.SAPPHIRE_AMULET_1694) && getVarbit(player, MHUtils.DROALAK_PROGRESS) == 2) {
            playerl(FacialExpression.FRIENDLY, "I have a sapphire amulet!")
            stage = 16
            return true
        }
        if (getVarbit(player, MHUtils.DROALAK_PROGRESS) == 4) {
            playerl(FacialExpression.FRIENDLY, "I've given her the amulet. She was very pleased and said she just wanted to know you still cared.")
            stage = 19
            return true
        }
        if(getVarbit(player, MHUtils.DROALAK_PROGRESS) == 5 && !hasAnItem(player, Items.SCROLL_6758).exists()) {
            playerl(FacialExpression.FRIENDLY, "Thanks for the scroll, but I seem to have lost it.")
            stage = 25
            return true
        }
        if(getVarbit(player, MHUtils.DROALAK_PROGRESS) == 5 && hasAnItem(player, Items.SCROLL_6758).exists()) {
            npcl(FacialExpression.FRIENDLY, "Take that scroll to Jorral in the outpost.")
            stage = END_DIALOGUE
            return true
        }
        if(getVarbit(player, MHUtils.DROALAK_PROGRESS) == 6) {
            playerl(FacialExpression.FRIENDLY, "I have delivered the scroll; you can rest in peace now.")
            stage = 27
            return true
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendDialogue(player, "You cannot understand the ghost.")
            1 -> npcl(FacialExpression.FRIENDLY, "Wow. I haven't spoken to the living for... for... I don't remember how long.").also { stage = 2 }
            2 -> playerl(FacialExpression.FRIENDLY, "So your name IS Droalak?").also { stage++ }
            3 -> npcl(FacialExpression.FRIENDLY, "Sorry, yes. I am he.").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "Great. Do you know anything about the outpost north of Ardougne?").also { stage++ }
            5 -> npcl(FacialExpression.FRIENDLY, "I don't really like to talk about it, but I died there.").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "Oh dear.").also { stage++ }
            7 -> npcl(FacialExpression.FRIENDLY, "I do have a scroll which might interest you that describes the timeline of the outpost. But first I wonder if I could ask you to tie up a problem?").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Like what?").also { stage++ }
            9 -> npcl(FacialExpression.FRIENDLY, "Well, I left to go to the outpost against the wishes of my wife. I promised I would return to her, but obviously I did not as I died there. She's a ghost nearby, but won't listen to my apologies.").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "You want me to patch things up?").also { stage++ }
            11 -> npcl(FacialExpression.FRIENDLY, "Yes, how'd you guess?").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "Call it 'traveller's intuition'.").also { stage++ }
            13 -> npcl(FacialExpression.FRIENDLY, "OK, well perhaps you could give her a strung sapphire amulet, because this is what I gave her the day I left. Her name is Melina by the way.").also { stage++ }
            14 -> playerl(FacialExpression.FRIENDLY, "No problem.").also { stage++ }
            15 -> {
                end()
                setVarbit(player, MHUtils.DROALAK_PROGRESS, 2, true)
            }
            16 -> npcl(FacialExpression.FRIENDLY, "Good work. Just give it to Melina, who's wandering somewhere nearby.").also { stage = END_DIALOGUE }

            17 -> npcl(FacialExpression.FRIENDLY, "Make a strung sapphire amulet and give it to Melina!").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "Ok. Ok.").also { stage = END_DIALOGUE }
            19 -> npcl(FacialExpression.FRIENDLY, "Excellent! I am so glad she believes me. I can finally rest in peace.").also { stage++ }
            20 -> playerl(FacialExpression.FRIENDLY, "Could I have that scroll you mentioned first?").also { stage++ }
            21 -> npcl(FacialExpression.FRIENDLY, "Of course. Let me know if it was of any use and then I can be forever free.").also { stage++ }
            22 -> playerl(FacialExpression.FRIENDLY, "Thank you.").also { stage++ }
            23 -> {
                end()
                addItemOrDrop(player, Items.SCROLL_6758)
                setVarbit(player, MHUtils.DROALAK_PROGRESS, 5, true)
                setAttribute(player, "/save:${MHUtils.ATTRIBUTE_DROALAK_PROGRESS}", true)
            }
            24 -> playerl(FacialExpression.FRIENDLY, "Your loss!").also { stage = END_DIALOGUE }
            25 -> npcl(FacialExpression.FRIENDLY, "It's a good job I stuck around then, isn't it! Have another copy.").also { stage++ }
            26 -> {
                end()
                addItemOrDrop(player, Items.SCROLL_6758)
            }
            27 -> npcl(FacialExpression.FRIENDLY, "Thanks for telling me! I've been waiting for ages!").also { stage++ }
            28 -> playerl(FacialExpression.FRIENDLY, "Goodbye.").also { stage++ }
            29 -> npcl(FacialExpression.FRIENDLY, "Bye!").also { stage++ }
            30 -> {
                end()
                transformNpc(NPC(NPCs.DROALAK_2938), NPCs.DROALAK_2937, 10)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DROALAK_2938)
    }
}

