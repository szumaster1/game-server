package content.location.ooglog

import core.api.*
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.START_DIALOGUE

/**
 * Represents the Kringk dialogue.
 */
@Initializable
class KringkDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        npc = NPC(NPCs.KRINGK_7058)
        when (stage) {
            START_DIALOGUE -> {
                if (!isQuestComplete(player, "As a First Resort")) {
                    playerl(FacialExpression.FRIENDLY, "What's going on here?").also { stage++ }
                } else {
                    npcl(FacialExpression.CHILD_NORMAL, "I would offer haircut, but it hard to do style for puny human with puny head. Me know. You want wig intead? Me give you big-big discount!").also { stage = 5 }
                }
            }

            1 -> npcl(FacialExpression.CHILD_NORMAL, "Me very busy. No have time to talk to puny creature like you.").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Well, excuse me!").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "No excuse for you - you in my way.").also { stage++ }
            4 -> end()
            5 -> sendDialogueOptions(player, "Would you like to buy an ogre wig for 50 gp?", "Yes, please.", "No, thank you.").also { stage++ }
            6 -> when (buttonId) {
                1 -> player("Yes, please.").also { stage++ }
                2 -> player("No, thank you.").also { stage = 4 }
            }
            7 -> {
                if (!removeItem(player, Item(Items.COINS_995, 50)) && freeSlots(player) > 1) {
                    npcl(FacialExpression.CHILD_NORMAL, "You no have enough shiny pretties, human.").also { stage = 4 }
                } else if (freeSlots(player) == 0) {
                    npcl(FacialExpression.CHILD_NORMAL, "You no have enough room to hold it, human. Come back when you have space.").also { stage = 4 }
                } else {
                    npcl(FacialExpression.CHILD_NORMAL, "There you go. Nice wig for you, made from de freshest wolfsie bones.").also { stage = 8 }
                }
            }

            8 -> {
                end()
                addItemOrDrop(player, Items.OGRE_WIG_12559)
            }
        }
        return true
    }

    override fun getIds(): IntArray = intArrayOf(7099) // NPCs.KRINGK_7058

}

