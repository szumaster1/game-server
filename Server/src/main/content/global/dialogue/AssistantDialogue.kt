package content.global.dialogue

import content.global.travel.balloon.BalloonListeners
import core.api.consts.Components
import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.openInterface
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE
import core.utilities.START_DIALOGUE

@Initializable
class AssistantDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val faceExpression = if (npc.id != 5056) FacialExpression.HALF_GUILTY else FacialExpression.OLD_NORMAL
        when (stage) {
            START_DIALOGUE -> npcl(faceExpression, "Do you want to use the balloon? Just so you know, some locations require special logs and high Firemaking skills.").also { stage++ }
            1 -> if (npc.id != 5050) {
                options("Yes.", "No.", "Who are you?").also { stage++ }
            } else {
                options("Yes.", "No.").also { stage++ }
            }
            2 -> when (buttonId) {
                1 -> {
                    if(!isQuestComplete(player!!, "Enlightened Journey")) {
                        npcl(faceExpression, "Oh, Sorry...You must complete Enlightened Journey before you can use it.").also { stage = END_DIALOGUE }
                    } else {
                        player("Yes.").also { stage = 7 }
                    }
                }
                2 -> player("No.").also { stage = 10 }
                3 -> player("Who are you?").also { stage++ }
            }
            3 -> {
                when (npc.id) {
                    NPCs.ASSISTANT_SERF_5053 -> npcl(faceExpression, "I am a Serf. Assistant Serf to you! Auguste freed me and gave me this job.").also { stage++ }
                    NPCs.ASSISTANT_MARROW_5055 -> npcl(faceExpression, "I am Assistant Marrow. I'm working here part time while I study to be a doctor.").also { stage++ }
                    NPCs.ASSISTANT_LE_SMITH_5056 -> npcl(faceExpression, "I am Assistant Le Smith. I used to work as a glider pilot, but they kicked me off.").also { stage = 8 }
                    NPCs.ASSISTANT_STAN_5057 -> npcl(faceExpression, "I am Stan. Auguste hired me to look after this balloon. I make sure people are prepared to fly.").also { stage++ }
                    5065 -> npcl(faceExpression, "I am Assistant Brock. I serve under Auguste as his number two assistant.").also { stage++ }
                }
            }

            4 -> npcl(faceExpression, "Do you want to use the balloon?").also { stage++ }
            5 -> options("Yes.", "No.").also { stage++ }
            6 -> when (buttonId) {
                1 -> {
                    if (!isQuestComplete(player!!, "Enlightened Journey")) {
                        npcl(faceExpression, "Oh, Sorry...You must complete Enlightened Journey before you can use it.").also { stage = END_DIALOGUE }
                    } else {
                        player("Yes.").also { stage = 7 }
                    }
                }
                2 -> player("No.").also { stage = 10 }
            }
            7 -> {
                end()
                openInterface(player, Components.ZEP_BALLOON_MAP_469)
                BalloonListeners.adjustInterface(player!!, npc)
            }
            8 -> playerl(FacialExpression.FRIENDLY, "Why?").also { stage++ }
            9 -> npcl(faceExpression, "They said I was too full of hot air.").also { stage = 4 }
            10 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.AUGUSTE_5050, NPCs.ASSISTANT_SERF_5053, NPCs.ASSISTANT_MARROW_5055, NPCs.ASSISTANT_LE_SMITH_5056, NPCs.ASSISTANT_STAN_5057, 5065)
    }
}
