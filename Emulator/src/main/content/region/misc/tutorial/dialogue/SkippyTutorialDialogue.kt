package content.region.misc.tutorial.dialogue

import cfg.consts.NPCs
import content.region.misc.tutorial.handlers.TutorialStage
import core.api.sendDialogueOptions
import core.api.setAttribute
import core.api.teleport
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.world.map.Location
import core.tools.END_DIALOGUE

/**
 * Represents the Skippy dialogue on tutorial island.
 */
class SkippyTutorialDialogue : DialogueFile() {

    /*
     * Allow players to skip the tutorial.
     */
    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SKIPPY_2796)
        when (stage) {
            0 -> npcl(FacialExpression.HALF_ASKING, "Do you wanna skip the Tutorial?").also { stage++ }
            1 -> sendDialogueOptions(player!!, "What would you like to say?", "Yes, please.", "Who are you?", "Can I decide later?", "I'll stay here for the Tutorial.").also { stage++ }
            2 -> when (buttonID) {
                1 -> npc(FacialExpression.HAPPY, "Prepare yourself!").also { stage = 10 }
                2 -> player(FacialExpression.HALF_ASKING, "Who are you?").also { stage++ }
                3 -> player(FacialExpression.HALF_ASKING, "Can I decide later?").also { stage = 6 }
                4 -> player("I'll stay here for the Tutorial.").also { stage = 7 }
            }
            3 -> npcl(FacialExpression.NEUTRAL, "My name's Skippy. Normally I live down by Rimmington. You should come and see me when you're passing.").also { stage++ }
            4 -> npcl(FacialExpression.LAUGH, "Just lately the Council wants to let people skip the Tutorial, so - ha ha ha - they dump the job on Skippy. Bah!").also { stage++ }
            5 -> npcl(FacialExpression.HALF_ASKING, "So, anyway, do you want to skip the Tutorial?").also { stage = 8 }
            6 -> npcl(FacialExpression.NOD_NO, "Unfortunately, so far there is no such possibility.").also { stage = 5 }
            7 -> npcl(FacialExpression.NOD_YES, "Good choice.").also { stage = END_DIALOGUE }
            8 -> sendDialogueOptions(player!!, "What would you like to say?", "Yes, please.", "Can I decide later?", "I'll stay here for the Tutorial.").also { stage++ }
            9 -> when (buttonID) {
                1 -> npc(FacialExpression.HAPPY, "Prepare yourself!").also { stage = 10 }
                2 -> player(FacialExpression.HALF_ASKING, "Can I decide later?").also { stage = 6 }
                3 -> player("I'll stay here for the Tutorial.").also { stage = 7 }
            }
            10 -> {
                end()
                setAttribute(player!!, "/save:tutorial:stage", 71)
                TutorialStage.load(player!!, 71)
                teleport(player!!, Location.create(3141, 3089, 0))
            }
        }
    }
}
