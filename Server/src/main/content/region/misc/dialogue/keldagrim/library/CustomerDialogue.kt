package content.region.misc.dialogue.keldagrim.library

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Customer dialogue.
 */
@Initializable
class CustomerDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: Woman currently visiting the dwarven capital city of Keldagrim in pursuit of knowledge.
     * This has brought her to the city's library, where she hopes to learn about the dwarves' ways
     * which she finds very interesting. She is the only human NPC in all of Keldagrim.
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.FRIENDLY, "Oh, that's nice, another human visitor to Keldagrim!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Indeed!").also { stage++ }
            1 -> npcl(FacialExpression.FRIENDLY, "And what brings you to this city? Come to seek knowledge as well?").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "I think it's mostly fame and riches I'm after.").also { stage++ }
            3 -> npcl(FacialExpression.FRIENDLY, "To each " + (if (player.isMale) "his" else "her") + " own, I suppose.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CUSTOMER_2168)
    }

}
