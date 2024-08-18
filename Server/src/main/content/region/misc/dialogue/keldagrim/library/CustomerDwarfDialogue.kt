package content.region.misc.dialogue.keldagrim.library

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Customer dwarf dialogue.
 */
@Initializable
class CustomerDwarfDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: She is currently searching the shelves upstairs in the city's library.
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.FRIENDLY, "Hello, can you help me?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.OLD_NORMAL, "Ssshhh, don't talk...").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "But I just want to ask you something.").also { stage++ }
            2 -> npcl(FacialExpression.OLD_NORMAL, "Sssshhhh! Can't you read the sign?").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "What sign??").also { stage++ }
            4 -> npcl(FacialExpression.OLD_NORMAL, "Don't be a cheeky human!").also { stage++ }
            5 -> playerl(FacialExpression.FRIENDLY, "Ssshhhh.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CUSTOMER_2167)
    }

}
