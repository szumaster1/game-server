package content.region.kandarin.dialogue

import content.region.kandarin.quest.thegrandtree.dialogue.GloughDialogueFile
import core.api.consts.NPCs
import core.api.getQuestStage
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class GloughDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (getQuestStage(player, "The Grand Tree") >= 40) {
            openDialogue(player, GloughDialogueFile())
        } else {
            playerl(FacialExpression.FRIENDLY, "Hello there!").also { stage++ }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.OLD_NORMAL, "You shouldn't be here human!").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "What do you mean?").also { stage++ }
            2 -> npcl(FacialExpression.OLD_NORMAL, "The Gnome Stronghold is for gnomes alone!").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "Surely not!").also { stage++ }
            4 -> npcl(FacialExpression.OLD_NORMAL, "We don't need your sort round here!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GLOUGH_671)
    }
}
