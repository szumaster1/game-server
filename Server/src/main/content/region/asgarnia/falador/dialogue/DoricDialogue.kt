package content.region.asgarnia.falador.dialogue

import content.region.asgarnia.quest.doricquest.cutscene.DoricCutscene
import content.region.asgarnia.quest.doricquest.dialogue.DoricDialogueFile
import core.api.*
import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.dialogue.IfTopic
import core.game.dialogue.Topic
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.GameWorld.settings
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Doric dialogue.
 */
@Initializable
class DoricDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        val quesStage = getQuestStage(player, "Doric's Quest")
        when (quesStage) {
            0 -> {
                npcl(FacialExpression.OLD_NORMAL, "Hello traveller, what brings you to my humble smithy?").also { stage = 0 }
            }
            in 1..99 -> {
                openDialogue(player, DoricDialogueFile(30), npc)
            }
            else -> {
                npcl(FacialExpression.OLD_HAPPY, "Hello traveller, how is your metalworking coming along?").also { stage = 60 }
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> showTopics(
                Topic(FacialExpression.FRIENDLY, "I wanted to use your anvils.", 10),
                Topic(FacialExpression.FRIENDLY, "I wanted to use your whetstone.", 20),
                IfTopic(FacialExpression.OLD_ANGRY1, "Mind your own business, shortstuff!", 30, !getAttribute(player, "pre-dq:doric-calm", false), true),
                Topic(FacialExpression.FRIENDLY, "I was just checking out the landscape.", 40),
                Topic(FacialExpression.ASKING, "What do you make here?", 50)
            )

            10 -> openDialogue(player, DoricDialogueFile(10), npc)

            20 -> openDialogue(player, DoricDialogueFile(20), npc)

            30 -> {
                if (getAttribute(player, "pre-dq:doric-angy-count", 0) == 10) {
                    end()
                    DoricCutscene(player).start()
                } else {
                    setAttribute(player, "/save:pre-dq:doric-angy-count", getAttribute(player, "pre-dq:doric-angy-count", 0) + 1)
                    npcl(FacialExpression.OLD_ANGRY1, "How nice to meet someone with such pleasant manners. Do come again when you need to shout at someone smaller than you!").also { stage = END_DIALOGUE }
                }
            }

            40 -> {
                npcl(FacialExpression.OLD_HAPPY, "Hope you like it. I do enjoy the solitude of my little home. If you get time, please say hi to my friends in the Dwarven Mine.")
                setAttribute(player, "/save:pre-dq:said-hi", false)
                stage = END_DIALOGUE
            }

            50 -> npcl(FacialExpression.OLD_NORMAL, "I make pickaxes. I am the best maker of pickaxes in the whole of ${settings!!.name}.").also { stage++ }
            51 -> playerl(FacialExpression.HALF_ASKING, "Do you have any to sell?").also { stage++ }
            52 -> npcl(FacialExpression.OLD_NOT_INTERESTED, "Sorry, but I've got a running order with Nurmof.").also { stage++ }
            53 -> playerl(FacialExpression.FRIENDLY, "Ah, fair enough.").also { stage = END_DIALOGUE }
            60 -> playerl(FacialExpression.FRIENDLY, "Not too bad, Doric.").also { stage++ }
            61 -> npcl(FacialExpression.OLD_HAPPY, "Good, the love of metal is a thing close to my heart.").also { stage++ }
            62 -> {
                if (getAttribute(player, "pre-dq:said-hi", false)) {
                    playerl(FacialExpression.FRIENDLY, "By the way, I told Nurmof you said hello.")
                    stage++
                } else {
                    end()
                }
            }

            63 -> {
                npcl(FacialExpression.OLD_HAPPY, "Thank you traveller! You'll always be welcome in my home.")
                removeAttribute(player, "pre-dq:said-hi")
                rewardXP(player, Skills.MINING, 5.0)
                stage = END_DIALOGUE
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DORIC_284)
    }
}
