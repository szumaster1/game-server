package content.region.misthalin.dialogue.varrock.museum

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class NaturalHistorianDialogue(player: Player? = null) : Dialogue(player) {

    /*
     *  Info: Natural Historians are biologists that have been hired by the Varrock Museum
     *  to educate visitors and gather further information regarding Gielinor's diverse animal kingdom.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.HALF_GUILTY, "Hello there and welcome to the Natural History exhibit of the Varrock Museum!").also { stage++ }
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.HALF_GUILTY, "Hello. So what is it you do here?").also { stage++ }
            1 -> npcl(FacialExpression.HALF_GUILTY, "Well, we look after all of the research in this section.").also { stage++ }
            2 -> npcl(FacialExpression.HALF_GUILTY, "When I'm not doing that, I'm teaching people like yourself about how wonderful the natural world is.").also { stage++ }
            3 -> playerl(FacialExpression.HALF_GUILTY, "Nice. So then, I've got a few questions for you.").also { stage++ }
            4 -> npcl(FacialExpression.HALF_GUILTY, "Well, fire away and I'll give you an insight into the exciting world of animals.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.NATURAL_HISTORIAN_5970)
    }

}
