package content.dialogue.feldip.gutanoth

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Dal ogre dialogue.
 */
@Initializable
class DalOgreDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_DEFAULT, "What the human be wantin'?").also { stage = END_DIALOGUE }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }


    override fun getIds(): IntArray {
        return intArrayOf(NPCs.OGRE_TRADER_876)
    }
}