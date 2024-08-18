package content.region.kandarin.quest.fightarena.dialogue.guards

import core.api.allInEquipment
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Khazard guard2 dialogue.
 */
@Initializable
class KhazardGuard2Dialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (isQuestComplete(player, "Fight Arena")) {
            npcl(FacialExpression.FRIENDLY, "It's you! I don't believe it. You beat the General! You are a traitor to the uniform!").also { stage = END_DIALOGUE }
        } else if (allInEquipment(player, Items.KHAZARD_HELMET_74, Items.KHAZARD_ARMOUR_75)) {
            playerl(FacialExpression.FRIENDLY, "Hello.").also { stage = 0 }
        } else {
            playerl(FacialExpression.FRIENDLY, "Hi.").also { stage = 7 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.FRIENDLY, "I've never seen you around here before!").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "Long live General Khazard!").also { stage++ }
            2 -> npcl(FacialExpression.FRIENDLY, "Erm.. yes.. soldier, I take it you're new around here?").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "You could say that.").also { stage++ }
            4 -> npcl(FacialExpression.FRIENDLY, "Khazard died two hundred years ago. However his dark spirit remains in the form of the undead maniac General Khazard. Remember he is your master, always watching.").also { stage++ }
            5 -> npcl(FacialExpression.FRIENDLY, "Got that newbie?").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "Undead, maniac, master. Got it, loud and clear.").also { stage = END_DIALOGUE }
            7 -> npcl(FacialExpression.ANNOYED, "This area is restricted. Leave now! OUT! And don't come back.").also { stage++ }
            8 -> options("I apologise, I stumbled in here by mistake.", "You have no right to tell me where I can and cannot go.").also { stage++ }
            9 -> when (buttonId) {
                1 -> playerl(FacialExpression.HALF_WORRIED, "I apologise, I stumbled in here by mistake.").also { stage++ }
                2 -> playerl(FacialExpression.HALF_WORRIED, "You have no right to tell me where I can and cannot go.").also { stage = 11 }
            }
            10 -> npcl(FacialExpression.FRIENDLY, "Well, don't just stand there - get out of here!").also { stage = END_DIALOGUE }
            11 -> npcl(FacialExpression.FRIENDLY, "Fair enough. Let's do this the hard way.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KHAZARD_GUARD_254)
    }
}
