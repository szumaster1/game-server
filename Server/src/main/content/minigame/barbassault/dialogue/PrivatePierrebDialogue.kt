package content.minigame.barbassault.dialogue

import content.global.random.event.drilldemon.DrillDemonUtils
import core.api.consts.NPCs
import core.api.consts.Sounds
import core.api.playAudio
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Private pierreb dialogue.
 * @author Szumaster
 */
@Initializable
class PrivatePierrebDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.FRIENDLY, "Hello. So you're just a private?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.FRIENDLY, "Show some respect! It's more than you'll achieve.").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "I beg to differ. I'm in perfect shape!").also { stage++ }
            2 -> npcl(FacialExpression.FRIENDLY, "Prove it!").also { stage++ }
            3 -> playerl(FacialExpression.HALF_GUILTY, "How?").also { stage++ }
            5 -> npcl(FacialExpression.FRIENDLY, "Give me five star jumps!").also { stage++ }
            6 -> {
                DrillDemonUtils.animationForTask(DrillDemonUtils.DD_SIGN_JUMP)
                playAudio(player, Sounds.STAR_JUMP_2492, 0, 5)
                stage++
            }
            7 -> npcl(FacialExpression.FRIENDLY, "Five sit-ups!").also { stage++ }
            8 -> {
                DrillDemonUtils.animationForTask(DrillDemonUtils.DD_SIGN_PUSHUP)
                playAudio(player, Sounds.PRESSUPS_2481, 25, 5)
                stage++
            }
            9 -> npcl(FacialExpression.FRIENDLY, "Run on the spot!").also { stage++ }
            10 -> {
                DrillDemonUtils.animationForTask(DrillDemonUtils.DD_SIGN_JOG)
                playAudio(player, Sounds.RUNONSPOT_2484, 0, 5)
                stage++
            }
            11 -> npcl(FacialExpression.NEUTRAL,"Okay. Maybe you have what it takes. Best you speak with the captain.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PRIVATE_PIERREB_5033)
    }

}
