package content.global.random.event.drilldemon

import core.api.*
import cfg.consts.Components
import cfg.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.DARK_RED
import core.tools.END_DIALOGUE

/**
 * Represents the Sergeant Damien dialogue.
 */
class SergeantDamienDialogue(var isCorrect: Boolean = false, var eventStart: Boolean = false) : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SERGEANT_DAMIEN_2790)
        val correctCounter = player!!.getAttribute(DrillDemonUtils.DD_CORRECT_COUNTER, 0)
        when (stage) {
            0 -> {
                if (correctCounter >= 4) {
                    npc(FacialExpression.OLD_NORMAL, "Well I'll be, you actually did it, private.", "Now take a reward and get out of my sight.")
                    stage = 100
                } else if (eventStart) {
                    npc(FacialExpression.OLD_NORMAL, "You can get back to your business in a minute, private.", "Now, listen up...").also { stage++ }
                    DrillDemonUtils.changeSignsAndAssignTask(player!!)
                    stage = 0
                    eventStart = false
                } else {
                    npcl(
                        FacialExpression.OLD_NORMAL, when (getAttribute(player!!, DrillDemonUtils.DD_KEY_TASK, -1)) {
                            DrillDemonUtils.DD_SIGN_JOG -> if (!isCorrect) "Wrong exercise, worm! I want to see you jogging on the spot!" else "I want to see you jogging on the spot!"
                            DrillDemonUtils.DD_SIGN_JUMP -> if (!isCorrect) "Wrong exercise, worm! I want to see some star jumps, private!" else "I want to see some star jumps, private!"
                            DrillDemonUtils.DD_SIGN_PUSHUP -> if (!isCorrect) "Wrong exercise, worm! Drop and give me push ups, private!" else "Drop and give me push ups, private!"
                            DrillDemonUtils.DD_SIGN_SITUP -> if (!isCorrect) "Wrong exercise, worm! Get down and give me sit ups, private!" else "Get down and give me sit ups, private!"
                            else -> ""
                        }
                    )
                    stage = 1
                    unlock(player!!)
                }
            }

            1 -> {
                end()
                openInterface(player!!, Components.DOUBLEOBJBOX_131)
                sendModelOnInterface(
                    player!!, Components.DOUBLEOBJBOX_131, 2,
                    when (getAttribute(player!!, DrillDemonUtils.DD_KEY_TASK, -1)) {
                        DrillDemonUtils.DD_SIGN_JOG -> 23216
                        DrillDemonUtils.DD_SIGN_JUMP -> 23218
                        DrillDemonUtils.DD_SIGN_PUSHUP -> 23215
                        DrillDemonUtils.DD_SIGN_SITUP -> 23217
                        else -> -1
                    }, -1
                )
                setComponentVisibility(player!!, Components.DOUBLEOBJBOX_131, 3, true)
                sendAngleOnInterface(player!!, Components.DOUBLEOBJBOX_131, 2, 2800, 0, 0)
                sendInterfaceText(
                    player!!,
                    when (getAttribute(player!!, DrillDemonUtils.DD_KEY_TASK, -1)) {
                        DrillDemonUtils.DD_SIGN_JOG -> "Go to$DARK_RED this mat</col> and jog on the spot!"
                        DrillDemonUtils.DD_SIGN_JUMP -> "Go to$DARK_RED this mat</col> and do some starjumps!"
                        DrillDemonUtils.DD_SIGN_PUSHUP -> "Go to$DARK_RED this mat</col> and do some pushups!"
                        DrillDemonUtils.DD_SIGN_SITUP -> "Go to$DARK_RED this mat</col> and do some sit ups!"
                        else -> ""
                    }, Components.DOUBLEOBJBOX_131, 1
                )
                stage = END_DIALOGUE
            }

            100 -> {
                end()
                DrillDemonUtils.cleanup(player!!)
                DrillDemonUtils.reward(player!!)
            }
        }
    }
}
