package content.region.kandarin.dialogue.observatory

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Smellytoes dialogue.
 */
@Initializable
class SmellytoesDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.FRIENDLY, "Hi there.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.OLD_NORMAL, "Hey, ids me matesh!").also { stage++ }
            1 -> player(FacialExpression.HALF_GUILTY, "Sorry, have we met?").also { stage++ }
            2 -> npcl(FacialExpression.OLD_NORMAL,"Yeah! you wazsh wiv me in dat pub overy by hill!").also { stage++ }
            3 -> player(FacialExpression.HALF_GUILTY, "I have no idea what you're going on about.").also { stage++ }
            4 -> npcl(FacialExpression.OLD_NORMAL, "Glad yeeash remembers.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SMELLYTOES_6128)
    }

}