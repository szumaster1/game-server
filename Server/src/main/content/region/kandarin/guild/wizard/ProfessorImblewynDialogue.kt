package content.region.kandarin.guild.wizard

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Professor imblewyn dialogue.
 */
@Initializable
class ProfessorImblewynDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: Gnome wizard found in the Magic Guild.
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player("I didn't realise gnomes were interested in magic.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.OLD_NORMAL, "Gnomes are interested in everything, lad.").also { stage++ }
            1 -> player("Of course.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PROFESSOR_IMBLEWYN_4586)
    }

}
