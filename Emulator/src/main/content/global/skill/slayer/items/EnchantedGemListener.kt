package content.global.skill.slayer.items

import content.global.skill.slayer.Tasks
import core.api.*
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.dialogue.IfTopic
import core.game.dialogue.Topic
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.tools.END_DIALOGUE
import org.rs.consts.Items

/**
 * Handles the enchanted gem option.
 */
class EnchantedGemListener : InteractionListener {
    override fun defineListeners() {
        on(Items.ENCHANTED_GEM_4155, IntType.ITEM, "activate") { player, _ ->
            openDialogue(player, EnchantedGemDialogue())
            return@on true
        }
    }
}

class EnchantedGemDialogue : DialogueFile() {
    var firstRun = true
    override fun handle(componentID: Int, buttonID: Int) {
        npc = getSlayerMaster(player!!)
        when(stage) {
            0 -> npcl(FacialExpression.FRIENDLY, "Hello there ${player!!.username}, what can I help you with?").also { stage++ }
            1 -> showTopics(
                Topic(FacialExpression.ASKING, "How am I doing so far?", 100),
                Topic(FacialExpression.HALF_ASKING, "Who are you?", 200),
                Topic(FacialExpression.HALF_ASKING, "Where are you?", 300),
                Topic(FacialExpression.FRIENDLY, "Got any tips for me?", 400),
                IfTopic(FacialExpression.FRIENDLY, "Nothing really.", END_DIALOGUE, firstRun),
                IfTopic(FacialExpression.HAPPY, "That's all thanks.", END_DIALOGUE, !firstRun)
            )
            100 -> {
                firstRun = false
                if(!hasSlayerTask(player!!)) {
                    npcl(FacialExpression.HALF_THINKING, "You need something new to hunt. Come and see me when you can and I'll give you a new task.").also { stage = 1 }
                } else {
                    if(getSlayerTask(player!!) == Tasks.JAD) {
                        npcl(FacialExpression.FRIENDLY, "You're currently assigned to kill TzTok-Jad!")
                    } else {
                        npcl(FacialExpression.FRIENDLY, "You're currently assigned to kill ${getSlayerTaskName(player!!)}s; only ${getSlayerTaskKillsRemaining(player!!)} more to go.")
                    }
                    setVarp(player!!, 2502, getSlayerTaskFlags(player!!) shr 4)
                    stage = 1
                }
            }
            200 -> {
                firstRun = false
                npcl(FacialExpression.HAPPY, "My name's ${getSlayerMaster(player!!).name}, I'm the Slayer Master best able to train you.").also { stage = 1 }
            }
            300 -> {
                firstRun = false
                npcl(FacialExpression.HAPPY, "You'll find me in ${getSlayerMasterLocation(player!!)}, I'll be here when you need a new task.").also { stage = 1 }
            }
            400 -> {
                firstRun = false
                npc(FacialExpression.FRIENDLY, *getSlayerTip(player!!))
                stage++
            }
            401 -> player(FacialExpression.HAPPY, "Great, thanks!").also { stage = 1 }
        }
    }
}
