package content.region.fremennik.dialogue.waterbrith

import content.region.fremennik.handlers.waterbirth.BardurExchangeListener
import core.api.*
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Bardur dialogue.
 */
@Initializable
class BardurDialogue(player: Player? = null): Dialogue(player) {

    /*
     * Bardur is a Fremennik warrior found deep inside the
     * Waterbirth Island Dungeon, fighting a group of
     * Dagannoth fledgelings.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Fremennik Trials")) {
            npcl(FacialExpression.FRIENDLY, "Ah! Outerlander! Do not interrupt me in my business! I must cull these fiends!").also { stage = 0 }
        } else if(player.username.toString().equals("Bardur", true)) {
            if(!player.isMale) {
                npcl(FacialExpression.HAPPY, "Ah, Bardur my sister-in-name! It is good fortune that we meet here like this!").also { stage = 4 }
            } else {
                npcl(FacialExpression.HAPPY, "Ah, Bardur my brother-in-name! It is good fortune that we meet here like this!").also { stage = 4 }
            }
        }
        else {
            npcl(FacialExpression.FRIENDLY, "Hello there Bardur. How's it going?").also { stage = 4 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "What are you doing down here?").also { stage++ }
            1 -> npcl(FacialExpression.FRIENDLY, "Only an outerlander would ask such a question! Is it not obvious what I am doing?").also { stage++ }
            2 -> npcl(FacialExpression.FRIENDLY, "I kill the daggermouths for glory and for sport! When I have had my fill, I move on to the daggermouths' lair!").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "Erm... okay then. I'll leave you to it.").also { stage = END_DIALOGUE }
            5 -> npcl(FacialExpression.FRIENDLY, "Listen, ${getAttribute(player, "fremennikname", "name")}. I'm sorry about the way I acted while you were exiled.").also { stage++ }
            6 -> sendDialogue(player!!, "Bardur gets hostile around outerlanders.").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "Don't worry about it, I understand! All's well that ends well! How've you been?").also { stage++ }
            8 -> npcl(FacialExpression.FRIENDLY, "I have been here all the long day, slaughtering the daggermouth spawns so that none may pass!").also { stage++ }
            9 -> npcl(FacialExpression.FRIENDLY, "My sword arm grows weary, and the blood of my foes hangs heavy upon my clothing!").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "Can you tell me anything about the caves down here?").also { stage++ }
            11 -> npcl(FacialExpression.FRIENDLY, "Aye, that I can $${getAttribute(player, "fremennikname", "name")}! Yonder lies the lair of the daggermouth kings, the three beasts of legend!").also { stage++ }
            12 -> npcl(FacialExpression.FRIENDLY, "I train my skills upon its foul brood, to prepare myself for the fight ahead!").also { stage++ }
            13 -> playerl(FacialExpression.FRIENDLY, "Okay, thanks Bardur. Do you have any food with you?").also { stage++ }
            14 -> npcl(FacialExpression.FRIENDLY, "Ah, you did not come prepared $${getAttribute(player, "fremennikname", "name")}?").also { stage++ }
            15 -> npcl(FacialExpression.FRIENDLY, "I have nothing to spare, but my equipment grows weaker under the onslaught of the dagger-mouth spawns!").also { stage++ }
            16 -> npcl(FacialExpression.FRIENDLY, "I will trade you a finely cooked shark if you have a replacement for my helm, my shield or my blade...").also { stage++ }
            17 -> if(!anyInInventory(player, *BardurExchangeListener.FREMENNIK_EQUIPMENT)) {
                playerl(FacialExpression.SAD, "Sorry, I don't have anything you'd be after...").also { stage = END_DIALOGUE }
            } else {
                npcl(FacialExpression.FRIENDLY, "Give me any equipment you wish to trade, and I will pay you a shark for it.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BARDUR_2879)
    }

}

/**
 * Represents the Bardur exchange dialogue.
 */
class BardurExchangeDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.BARDUR_2879)
        when (stage) {
            0 -> npcl(FacialExpression.FRIENDLY, "Ah, just what I was looking for! You wish to trade me that for a cooked shark?").also { stage++ }
            1 -> sendDialogueOptions(player!!, "Trade with Bardur?", "YES", "NO").also { stage++ }
            2 -> when (buttonID) {
                1 -> if((inInventory(player!!, Items.FREMENNIK_HELM_3748) && removeItem(player!!, Items.FREMENNIK_HELM_3748)) || (inInventory(player!!, Items.FREMENNIK_BLADE_3757) && removeItem(player!!, Items.FREMENNIK_BLADE_3757)) || (inInventory(player!!, Items.FREMENNIK_SHIELD_3758) && removeItem(player!!, Items.FREMENNIK_SHIELD_3758))) {
                    end()
                    addItemOrDrop(player!!, Items.SHARK_385)
                    npcl(FacialExpression.FRIENDLY, "Ah, this will serve me well as a backup! My thanks to you ${getAttribute(player!!, "fremennikname", "name")}, I trust we will one day sing songs together of glorious battles in the Rellekka longhall!")
                }
                2 -> {
                    end()
                    npcl(FacialExpression.FRIENDLY, "As you wish, ${getAttribute(player!!, "fremennikname", "name")}. My weapons have lasted me this long, I will keep my trust in them yet.")
                }
            }
        }
    }
}
