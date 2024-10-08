package content.region.asgarnia.falador.quest.rd.dialogue

import content.region.asgarnia.falador.quest.rd.handlers.RecruitmentDriveUtils
import content.region.asgarnia.falador.quest.rd.RecruitmentDrive
import content.region.asgarnia.falador.quest.rd.handlers.RecruitmentDriveListener
import core.api.getAttribute
import core.api.removeAttribute
import core.api.setAttribute
import core.api.setVarbit
import core.game.dialogue.DialogueBuilder
import core.game.dialogue.DialogueBuilderFile
import core.game.dialogue.FacialExpression

/**
 * Represents the Sir Spishyus dialogue file.
 * @author Ovenbread
 */
class SirSpishyusDialogueFile(private val dialogueNum: Int = 0) : DialogueBuilderFile() {

    override fun create(b: DialogueBuilder) {
        b.onPredicate { player -> getAttribute(player, RecruitmentDrive.stagePass, false) }
            .npc(FacialExpression.HAPPY, "Excellent work, @name.", "Please step through the portal to meet your next", "challenge.")
            .end()

        b.onPredicate { player -> dialogueNum == 2 || getAttribute(player, RecruitmentDrive.stageFail, false) }
            .betweenStage { _, player, _, _ ->
                setAttribute(player, RecruitmentDrive.stageFail, true)
            }
            .npc(FacialExpression.SAD, "No... I am very sorry.", "Apparently you are not up to the challenge.", "I will return you where you came from, better luck in the", "future.")
            .endWith { _, player ->
                removeAttribute(player, SirTinleyDialogueFile.patience)
                setAttribute(player, RecruitmentDrive.stagePass, false)
                setAttribute(player, RecruitmentDrive.stageFail, false)
                RecruitmentDriveListener.FailTestCutscene(player).start()
            }
        b.onPredicate { _ -> true }
            .betweenStage { _, player, _, _ ->
                setVarbit(player, RecruitmentDriveUtils.VARBIT_FOX_EAST, 0)
                setVarbit(player, RecruitmentDriveUtils.VARBIT_FOX_WEST, 0)
                setVarbit(player, RecruitmentDriveUtils.VARBIT_CHICKEN_EAST, 0)
                setVarbit(player, RecruitmentDriveUtils.VARBIT_CHICKEN_WEST, 0)
                setVarbit(player, RecruitmentDriveUtils.VARBIT_GRAIN_EAST, 0)
                setVarbit(player, RecruitmentDriveUtils.VARBIT_GRAIN_WEST, 0)
            }
            .npcl(FacialExpression.FRIENDLY, "Ah, welcome @name.")
            .playerl(FacialExpression.FRIENDLY, "Hello there." + " What am I supposed to be doing in this room?")
            .npcl(FacialExpression.FRIENDLY, "Well, your task is to take this fox, this chicken and this bag of grain across that bridge there to the other side of the room.")
            .npcl(FacialExpression.FRIENDLY, "When you have done that, your task is complete.")
            .playerl(FacialExpression.FRIENDLY, "Is that it?")
            .npcl(FacialExpression.FRIENDLY, "Well, it is not quite as simple as that may sound.")
            .npcl(FacialExpression.FRIENDLY, "Firstly, you may only carry one of the objects across the room at a time, for the bridge is old and fragile.")
            .npcl(FacialExpression.FRIENDLY, "Secondly, the fox wants to eat the chicken, and the chicken wants to eat the grain. Should you ever leave the fox unattended with the chicken, or the grain unattended with the chicken, then")
            .npcl(FacialExpression.FRIENDLY, "one of them will be eaten, and you will be unable to complete the test.")
            .playerl(FacialExpression.FRIENDLY, "Okay, I'll see what I can do.")
    }
}