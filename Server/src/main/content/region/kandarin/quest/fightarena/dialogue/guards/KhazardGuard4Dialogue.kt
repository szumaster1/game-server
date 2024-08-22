package content.region.kandarin.quest.fightarena.dialogue.guards

import core.api.allInEquipment
import cfg.consts.Items
import cfg.consts.NPCs
import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Khazard guard4 dialogue.
 */
@Initializable
class KhazardGuard4Dialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (isQuestComplete(player, "Fight Arena")) {
            npcl(FacialExpression.FRIENDLY, "It's you! I don't believe it. You beat the General! You are a traitor to the uniform!").also { stage = END_DIALOGUE }
        } else if (allInEquipment(player, Items.KHAZARD_HELMET_74, Items.KHAZARD_ARMOUR_75)) {
            playerl(FacialExpression.FRIENDLY, "Hello.")
        } else {
            playerl(FacialExpression.FRIENDLY, "Hi.").also { stage = 3 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.ANGRY, "Despicable thieving scum, that was good armour. Did you see anyone around here soldier?").also { stage++ }
            1 -> playerl(FacialExpression.SILENT, "Me? No, no one!").also { stage++ }
            2 -> npcl(FacialExpression.SUSPICIOUS, "Hmmmm").also { stage = END_DIALOGUE }
            3 -> npcl(FacialExpression.ANNOYED, "I don't know who you are. Get out of my house stranger.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KHAZARD_GUARD_256)
    }
}
