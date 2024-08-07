package content.region.asgarnia.quest.trollstronghold.dialogue

import core.api.consts.NPCs
import core.api.getQuestStage
import core.api.sendMessage
import core.api.setQuestStage
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Dad dialogue.
 */
@Initializable
class DadDialogue(player: Player? = null) : Dialogue(player) {
    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (getQuestStage(player!!, "Troll Stronghold")) {
            in 3..4 -> {
                when (stage) {
                    START_DIALOGUE -> npcl(FacialExpression.OLD_HAPPY, "What tiny human do in troll arena? Dad challenge human to fight!").also { stage++ }
                    1 -> showTopics(
                        Topic(FacialExpression.THINKING, "Why are you called Dad?", 2),
                        Topic(FacialExpression.FRIENDLY, "I accept your challenge!", 3),
                        Topic(FacialExpression.SCARED, "Eek! No thanks.", END_DIALOGUE)
                    )
                    2 -> npcl(FacialExpression.OLD_HAPPY, "Troll named after first thing try to eat!").also { stage = 1 }
                    3 -> npcl(FacialExpression.OLD_HAPPY, "Tiny human brave. Dad squish!").also { stage++ }
                    4 -> npc!!.attack(player).also {
                        npc!!.skills.lifepoints = npc!!.skills.maximumLifepoints // Reset dad to max hitpoints.
                        setQuestStage(player!!, "Troll Stronghold", 4)
                        stage = END_DIALOGUE
                    }
                }
            }

            in 5..100 -> {
                sendMessage(player, "He doesn't seem interested in talking right now.")
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DAD_1125)
    }
}

/**
 * Dad t s dialogue file
 *
 * @property dialogueNum
 * @constructor Dad t s dialogue file
 */
class DadTSDialogueFile(private val dialogueNum: Int = 0) : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (dialogueNum) {
            1 -> when (stage) {
                START_DIALOGUE -> npcl(FacialExpression.OLD_HAPPY, "No human pass through arena without defeating Dad!").also {
                    stage = END_DIALOGUE
                    setQuestStage(player!!, "Troll Stronghold", 3)
                }
            }

            2 -> when (stage) {
                START_DIALOGUE -> npcl(FacialExpression.OLD_NORMAL, "Tiny human brave. Dad squish!").also { stage++ }
                1 -> npc!!.attack(player).also {
                    npc!!.skills.lifepoints = npc!!.skills.maximumLifepoints // Reset dad to max hitpoints.
                    setQuestStage(player!!, "Troll Stronghold", 4)
                    stage = END_DIALOGUE
                }
            }

            3 -> when (stage) {
                START_DIALOGUE -> npcl(FacialExpression.OLD_NORMAL, "Stop! You win. Not hurt Dad.").also { stage++ }
                1 -> showTopics(
                    Topic(FacialExpression.FRIENDLY, "I'll be going now.", END_DIALOGUE),
                    Topic(FacialExpression.ANGRY_WITH_SMILE, "I'm not done yet! Prepare to die!", 2)
                )

                2 -> player!!.attack(npc).also {
                    setQuestStage(player!!, "Troll Stronghold", 5)
                    stage = END_DIALOGUE
                }
            }
        }
    }
}
