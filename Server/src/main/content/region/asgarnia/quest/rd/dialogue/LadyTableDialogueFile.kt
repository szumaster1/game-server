package content.region.asgarnia.quest.rd.dialogue

import cfg.consts.Components
import cfg.consts.NPCs
import content.region.asgarnia.quest.rd.RecruitmentDrive
import content.region.asgarnia.quest.rd.RecruitmentDriveListeners
import core.api.*
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.system.task.Pulse

/**
 * Represents a dialogue file for the Lady Table.
 *
 * @property state The current state of the dialogue (default to 0).
 * @constructor Initializes the LadyTableDialogueFile with a specified state.
 */
class LadyTableDialogueFile(private val state: Int = 0) : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.LADY_TABLE_2283)
        when (state) {
            1 -> handleTaskBegin()
            2 -> handleTaskFail()
        }
    }

    private fun handleTaskBegin() {
        when (stage) {
            0 -> {
                player!!.lock()
                player!!.faceLocation(location(2458, 4980, 0))
                setAttribute(player!!, "rd:statues", (1..12).random())
                setVarbit(player!!, 658, getAttribute(player!!, "rd:statues", 0))
                npcl(FacialExpression.FRIENDLY, "Welcome, @name. This room will test your observation skills.").also { stage++ }
            }
            1 -> npcl(FacialExpression.FRIENDLY, "Study the statues closely. There is one missing statue in this room.").also { stage++ }
            2 -> npcl(FacialExpression.FRIENDLY, "We will also mix the order up a little, to make things interesting for you!").also { stage++ }
            3 -> npcl(FacialExpression.FRIENDLY, "You have 10 seconds to memorise the statues... starting NOW!").also { stage++ }
            4 -> {
                end()
                player!!.unlock()
                startStatueMemorizationPulse()
            }
        }
    }

    private fun startStatueMemorizationPulse() {
        submitWorldPulse(object : Pulse() {
            var counter = 0
            override fun pulse(): Boolean {
                return when (counter++) {
                    16 -> {
                        openOverlay(player!!, Components.FADE_TO_BLACK_120)
                        sendNPCDialogueLines(player!!, NPCs.LADY_TABLE_2283, FacialExpression.FRIENDLY, true, "We will now dim the lights and bring the missing statue", "back in.")
                        false
                    }
                    23 -> {
                        setVarbit(player!!, 658, 0)
                        openOverlay(player!!, Components.FADE_FROM_BLACK_170)
                        npc(FacialExpression.FRIENDLY, "Please touch the statue you think has been added.")
                        true
                    }
                    else -> false
                }
            }
        })
    }

    private fun handleTaskFail() {
        when (stage) {
            0 -> if (getAttribute(player!!, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, false) && !getAttribute(player!!, RecruitmentDrive.ATTRIBUTE_RD_STAGE_PASSED, false)) {
                setAttribute(player!!, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, true)
                npc(FacialExpression.SAD, "No... I am very sorry.", "Apparently you are not up to the challenge.", "I will return you where you came from, better luck in the", "future.").also { stage++ }
            }
            1 -> {
                lock(player!!, 10)
                removeAttribute(player!!, SirRenItchwoodDialogueFile.ATTRIBUTE_CLUE)
                setAttribute(player!!, RecruitmentDrive.ATTRIBUTE_RD_STAGE_PASSED, false)
                setAttribute(player!!, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, false)
                RecruitmentDriveListeners.FailTestCutscene(player!!).start()
            }
        }
    }
}