package content.region.kandarin.dialogue.stronghold

import core.api.*
import core.api.consts.Animations
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Gerald dialogue.
 */
@Initializable
class GeraldDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if(getQuestStage(player, "Waterfall Quest") >= 1) {
            player("Hello.").also { stage = 5 }
        } else {
            player("Hello there.")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.NEUTRAL,"Good day to you traveller, are you here to fish or just looking around?").also { stage++ }
            1 -> npcl(FacialExpression.NEUTRAL,"I've caught some beauties down here.").also { stage++ }
            2 -> player("Really?").also { stage++ }
            3 -> npcl(FacialExpression.FRIENDLY, "The last one was this big!").also { stage++ }
            4 -> {
                stopWalk(player)
                runTask(player, 1){
                    animate(npc, Animations.STEP_BACK_AND_WAVE_ARM_1240)
                    stage = END_DIALOGUE
                }
            }
            5 -> npcl(FacialExpression.NEUTRAL, "Hello traveller.").also { stage++ }
            6 -> npcl(FacialExpression.HALF_ASKING, "Are you here to fish or to hunt for treasure?").also { stage++ }
            7 -> player("Why do you say that?").also { stage++ }
            8 -> npcl(FacialExpression.NEUTRAL, "Adventurers pass through here every week, they never find anything though.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GERALD_303)
    }

}
