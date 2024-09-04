package content.region.misc.tutorial.dialogue

import cfg.consts.NPCs
import content.region.misc.tutorial.handlers.TutorialStage
import core.api.sendDialogueOptions
import core.api.setAttribute
import core.api.teleport
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager
import core.game.world.map.Location
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Skippy dialogue
 */
@Initializable
class SkippyDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Skippy can be found on Tutorial Island near the <SERVER_NAME> Guide,
     * and will allow players to skip the tutorial.
     */

    override fun open(vararg args: Any?): Boolean {
        npcl(FacialExpression.HALF_ASKING, "Do you wanna skip the Tutorial?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendDialogueOptions(player, "What would you like to say?", "Yes, please.", "Who are you?", "Can I decide later?", "I'll stay here for the Tutorial.").also { stage++ }
            1 -> when (buttonId) {
                1 -> {
                    end()
                    npc(FacialExpression.HAPPY, "Prepare yourself!")
                    setAttribute(player, "/save:tutorial:stage", 71)
                    TutorialStage.load(player, 71)
                    teleport(player, Location.create(3141, 3089, 0), TeleportManager.TeleportType.NORMAL)
                }
                2 -> player(FacialExpression.HALF_ASKING, "Who are you?").also { stage++ }
                3 -> player(FacialExpression.HALF_ASKING, "Can I decide later?").also { stage = 6 }
                4 -> player("I'll stay here for the Tutorial.").also { stage = 10 }
            }

            3 -> npcl(FacialExpression.NEUTRAL, "My name's Skippy. Normally I live down by Rimmington. You should come and see me when you're passing.").also { stage++ }
            4 -> npcl(FacialExpression.NEUTRAL, "Just lately the Council wants to let people skip the Tutorial, so - ha ha ha - they dump the job on Skippy. Bah!").also { stage++ }
            5 -> npcl(FacialExpression.HALF_ASKING, "So, anyway, do you want to skip the Tutorial?").also { stage = 1 }
            6 -> npcl(FacialExpression.NOD_NO, "Unfortunately, so far there is no such possibility.").also { stage = 1 }
            7 -> npcl(FacialExpression.NOD_YES, "Good choice.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun newInstance(player: Player?): Dialogue {
        return SkippyDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SKIPPY_2796)
    }
}
