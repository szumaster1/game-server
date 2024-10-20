package content.region.misc.tutorial.dialogue

import content.region.misc.tutorial.handlers.TutorialStage
import core.api.lockMovement
import core.api.setAttribute
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.NPCs

/**
 * Represents the [SERVER_NAME][core.Configuration.SERVER_NAME] Guide dialogue.
 */
@Initializable
class RSGuideDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        val tutStage = player?.getAttribute("tutorial:stage", 0) ?: 0
        lockMovement(npc, 1000)
        if (tutStage == 39) {
            npc(FacialExpression.NEUTRAL, "Greetings! I see you are a new arrival to this land. My", "job is to welcome all new visitors. So welcome!")
            stage = 0
            return true
        }
        if (tutStage == 2) {
            npc(FacialExpression.NEUTRAL, "I'm glad you're making progress!")
            stage = 6
            return true
        }
        if (tutStage < 2) {
            end()
            npc(FacialExpression.NEUTRAL, "You will notice a flashing icon of a spanner; please click", "on this to continue the tutorial.")
            return false
        }
        else {
            end()
            npc(FacialExpression.HALF_GUILTY, "Please follow the onscreen instructions!")
            return false
        }
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.NEUTRAL, "You have already learned the first thing needed to", "succeed in this world: talking to other people!").also { stage++ }
            1 -> npc(FacialExpression.NEUTRAL, "You will find many inhabitants of this world have useful", "things to say to you. By clicking on them with your", "mouse you can talk to them.").also { stage++ }
            2 -> npc(FacialExpression.NEUTRAL, "I would also suggest reading through some of the", "supporting information on the website. There you can", "find the Game Guide, which contain all the additional", "information you're ever likely to need. It also contains").also { stage++ }
            3 -> npc(FacialExpression.NEUTRAL, "maps and helpful tips to help you on your journey.").also { stage++ }
            4 -> npc(FacialExpression.NEUTRAL, "You will notice a flashing icon of a spanner; please click", "on this to continue the tutorial.").also { stage++ }
            5 -> {
                end()
                setAttribute(player, "tutorial:stage", 1)
                TutorialStage.load(player, 1)
            }
            6 -> npc(FacialExpression.NEUTRAL, "To continue the tutorial go through that door over", "there and speak to your first instructor!").also { stage++ }
            7 -> {
                end()
                npc.unlock()
                setAttribute(player, "tutorial:stage", 3)
                TutorialStage.load(player, 3)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.RUNESCAPE_GUIDE_945)
    }
}