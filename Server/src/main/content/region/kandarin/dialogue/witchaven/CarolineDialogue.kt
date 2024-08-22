package content.region.kandarin.dialogue.witchaven

import content.region.kandarin.quest.seaslug.dialogue.CarolineDialogueFile
import cfg.consts.NPCs
import core.api.isQuestInProgress
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Caroline dialogue.
 */
@Initializable
class CarolineDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when {
            // Talk during Sea slug.
            isQuestInProgress(player, "Sea Slug", 0, 50) -> end().also { openDialogue(player, CarolineDialogueFile()) }
            // Talk before Sea Slug.
            else -> player(FacialExpression.FRIENDLY, "Hello")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.FRIENDLY, "Hello traveller, how are you?").also { stage++ }
            1 -> player(FacialExpression.FRIENDLY, "Not bad thanks, yourself?").also { stage++ }
            2 -> npcl(FacialExpression.FRIENDLY, "I'm good. Busy as always looking after Kent and Kennith but no complaints.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CAROLINE_696)
    }

}
