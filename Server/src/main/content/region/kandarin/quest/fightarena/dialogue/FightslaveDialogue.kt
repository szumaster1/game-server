package content.region.kandarin.quest.fightarena.dialogue

import core.api.allInEquipment
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Fightslave dialogue.
 */
@Initializable
class FightslaveDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.FRIENDLY, "Do you know of a Justin or Jeremy in this arena?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> if (allInEquipment(player, Items.KHAZARD_HELMET_74, Items.KHAZARD_ARMOUR_75)) {
                npcl(FacialExpression.FRIENDLY, "Please leave me alone.").also { stage = END_DIALOGUE }
            } else {
                npcl(FacialExpression.AFRAID, "I've not met anybody in here by that name.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FIGHTSLAVE_262)
    }
}
