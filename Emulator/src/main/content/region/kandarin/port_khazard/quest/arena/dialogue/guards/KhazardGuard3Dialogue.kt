package content.region.kandarin.port_khazard.quest.arena.dialogue.guards

import core.api.allInEquipment
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Khazard guard3 dialogue.
 */
@Initializable
class KhazardGuard3Dialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (isQuestComplete(player, "Fight Arena")) {
            npcl(FacialExpression.FRIENDLY, "It's you! I don't believe it. You beat the General! You are a traitor to the uniform!").also { stage = END_DIALOGUE }
        } else if (allInEquipment(player, Items.KHAZARD_HELMET_74, Items.KHAZARD_ARMOUR_75)) {
            playerl(FacialExpression.FRIENDLY, "Hello.")
        } else {
            playerl(FacialExpression.FRIENDLY, "Hi.").also { stage = 2 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.ASKING, "Can I help you stranger?").also { stage++ }
            1 -> npcl(FacialExpression.FRIENDLY, "Oh, you're a guard as well. That's ok then. We don't like strangers around here.").also { stage = END_DIALOGUE }
            2 -> npcl(FacialExpression.ANNOYED, "This area is restricted, leave now!").also { stage++ }
            3 -> npcl(FacialExpression.ANGRY, "OUT! And don't come back!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KHAZARD_GUARD_255)
    }
}
