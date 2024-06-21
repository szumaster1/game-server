package content.region.misthalin.dialogue.varrock

import core.api.consts.NPCs
import core.api.getQuestStage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class GuidorsWifeDialogue(player: Player? = null) : Dialogue(player) {

    /*
     *  Info: Wife of Elena's former teacher, Guidor.
     *  Location: 3281,3382
     */

    override fun open(vararg args: Any): Boolean {
        if (getQuestStage(player, "Biohazard") >= 16) {
            player("Hello again.").also { stage = 2 }
        } else {
            player(FacialExpression.HALF_GUILTY, "Hello.").also { stage = 0 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_GUILTY, "Oh hello, I can't chat now. I have to keep an eye on my", "husband. He's very ill!").also { stage++ }
            1 -> playerl(FacialExpression.HALF_GUILTY, "I'm sorry to hear that!").also { stage = END_DIALOGUE }
            2 -> npc("Hello there. I fear Guidor may not be long for", "this world!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return GuidorsWifeDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GUIDORS_WIFE_342)
    }

}
