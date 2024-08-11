package content.region.asgarnia.quest.recruitmentdrive.dialogue

import content.region.asgarnia.quest.recruitmentdrive.RecruitmentDrive
import content.region.asgarnia.quest.recruitmentdrive.RecruitmentDriveListeners
import core.api.*
import core.api.consts.Components
import core.api.consts.NPCs
import core.game.dialogue.DialogueBuilder
import core.game.dialogue.DialogueBuilderFile
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.system.task.Pulse

/**
 * Lady table dialogue file.
 */
class LadyTableDialogueFile(private val dialogueNum: Int = 0) : DialogueBuilderFile(), InteractionListener {

    companion object {
        const val STATUE_VARBIT = 658
        const val ATTRIBUTE_STATUE_STATE = "rd:statuestatenumber"
        val statueIds = intArrayOf(0, 7308, 7307, 7306, 7305, 7304, 7303, 7312, 7313, 7314, 7311, 7310, 7309)
    }

    override fun defineListeners() {

        on(statueIds, IntType.SCENERY, "touch") { player, node ->
            if (node.id == statueIds[getAttribute(player, ATTRIBUTE_STATUE_STATE, 0)]) {
                if (!getAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, false)) {
                    setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_PASSED, true)
                    sendNPCDialogueLines(player, NPCs.LADY_TABLE_2283, FacialExpression.NEUTRAL, false, "Excellent work, @name.", "Please step through the portal to meet your next", "challenge.")
                }
            } else {
                setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, true)
                openDialogue(player, LadyTableDialogueFile(2), NPC(NPCs.LADY_TABLE_2283))
            }
            return@on true
        }

    }

    override fun create(b: DialogueBuilder) {
        b.onPredicate { player -> dialogueNum == 1 }
            .endWith { _, player ->
                submitWorldPulse(object : Pulse() {
                    var counter = 0
                    override fun pulse(): Boolean {
                        when (counter++) {
                            0 -> {
                                lock(player, 15)
                                setAttribute(player, ATTRIBUTE_STATUE_STATE, (1..12).random())
                                setVarbit(player, STATUE_VARBIT, getAttribute(player, ATTRIBUTE_STATUE_STATE, 0))
                                sendNPCDialogueLines(player, NPCs.LADY_TABLE_2283, FacialExpression.NEUTRAL, true, "Welcome, @name.", "This room will test your observation skills.")
                            }
                            5 -> sendNPCDialogueLines(player, NPCs.LADY_TABLE_2283, FacialExpression.NEUTRAL, true, "Study the statues closely.", "There is one missing statue in this room.")
                            10 -> sendNPCDialogueLines(player, NPCs.LADY_TABLE_2283, FacialExpression.NEUTRAL, true, "We will also mix the order up a little, to make things", "interesting for you!")
                            15 -> sendNPCDialogueLines(player, NPCs.LADY_TABLE_2283, FacialExpression.NEUTRAL, true, "You have 10 seconds to memorise the statues... starting", "NOW!")
                            20 -> closeDialogue(player)
                            31 -> {
                                openOverlay(player, Components.FADE_TO_BLACK_120)
                                sendNPCDialogueLines(player, NPCs.LADY_TABLE_2283, FacialExpression.NEUTRAL, true, "We will now dim the lights and bring the missing statue", "back in.")
                            }
                            34 -> {
                                setVarbit(player, STATUE_VARBIT, 0)
                                openOverlay(player, Components.FADE_FROM_BLACK_170)
                                sendNPCDialogueLines(player, NPCs.LADY_TABLE_2283, FacialExpression.NEUTRAL, true, "Please touch the statue you think has been added.")
                                return true
                            }
                        }
                        return false
                    }
                })
            }

        b.onPredicate { player -> dialogueNum == 2 || (getAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, false) && !getAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_PASSED, false)) }
            .betweenStage { _, player, _, _ ->
                setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, true)
            }
            .npc(FacialExpression.SAD, "No... I am very sorry.", "Apparently you are not up to the challenge.", "I will return you where you came from, better luck in the", "future.")
            .endWith { _, player ->
                lock(player, 10)
                removeAttribute(player, SirRenItchwoodDialogueFile.ATTRIBUTE_CLUE)
                setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_PASSED, false)
                setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, false)
                RecruitmentDriveListeners.FailTestCutscene(player).start()
            }
    }
}