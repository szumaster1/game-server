package content.region.kandarin.quest.fightarena.dialogue

import core.api.allInEquipment
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.sendNPCDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Joe dialogue.
 */
@Initializable
class JoeDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.FRIENDLY, "Do you know of a Justin or Jeremy in this arena?").also { stage++ }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                if (!allInEquipment(player, Items.KHAZARD_HELMET_74, Items.KHAZARD_ARMOUR_75)) {
                    sendNPCDialogue(player, NPCs.KELVIN_260, "I have heard of those of whom you speak. But I fear it may be too late.", FacialExpression.HALF_GUILTY).also { stage = 3 }
                } else {
                    sendNPCDialogue(player, NPCs.JOE_261, "If we did, why would we tell you guard?", FacialExpression.ANNOYED).also { stage++ }
                }
            }
            1 -> sendNPCDialogue(player, NPCs.KELVIN_260, "Until our last breath, our every action will be against Khazard. Never will we help you.", FacialExpression.ANNOYED).also { stage++ }
            2 -> sendNPCDialogue(player, NPCs.JOE_261, "I spit on Khazard's grave, and all who do his bidding.", FacialExpression.ANNOYED).also { stage = END_DIALOGUE }
            3 -> sendNPCDialogue(player, NPCs.JOE_261, "It is said that Khazard has a personal vendetta against those two, their time is therefore short.", FacialExpression.FRIENDLY).also { stage++ }
            4 -> sendNPCDialogue(player, NPCs.KELVIN_260, "You're a brave " + (if (player!!.isMale) "Sir" else "Madam") + ". If the guards get you, you'll be in here next.", FacialExpression.HALF_WORRIED).also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KELVIN_260, NPCs.JOE_261)
    }
}
