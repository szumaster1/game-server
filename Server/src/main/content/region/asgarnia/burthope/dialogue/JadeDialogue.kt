package content.region.asgarnia.burthope.dialogue

import core.api.*
import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.dialogue.IfTopic
import core.game.dialogue.Topic
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.IronmanMode
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Jade dialogue.
 */
class JadeDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> if (hasIronmanRestriction(player, IronmanMode.ULTIMATE)) {
                npcl(FacialExpression.NEUTRAL, "Greetings, warrior. I wish I could help you, but " + "our services are not available for Ultimate ${if (player.isMale) "Ironman" else "Ironwoman"}.").also { stage = END_DIALOGUE }
            } else {
                npcl(FacialExpression.NEUTRAL, "Greetings warrior, how may I help you?").also {
                    if (hasAwaitingGrandExchangeCollections(player)) {
                        stage++
                    } else {
                        stage += 2
                    }
                }
            }

            1 -> npcl(FacialExpression.NEUTRAL, "Before we go any further, I should inform you that you " + "have items ready for collection from the Grand Exchange.").also { stage++ }
            2 -> showTopics(
                Topic(FacialExpression.NEUTRAL, "I'd like to access my bank account, please.", 10),
                IfTopic(FacialExpression.NEUTRAL, "I'd like to switch to my ${getBankAccountName(player, true)} bank account.", 13, hasActivatedSecondaryBankAccount(player)),
                Topic(FacialExpression.NEUTRAL, "I'd like to check my PIN settings.", 11),
                Topic(FacialExpression.NEUTRAL, "I'd like to see my collection box.", 12),
                Topic(FacialExpression.ASKING, "How long have you worked here?", 3)
            )

            3 -> npcl(FacialExpression.FRIENDLY, "Oh, ever since the Guild opened. I like it here.").also { stage++ }
            4 -> playerl(FacialExpression.ASKING, "Why's that?").also { stage++ }
            5 -> npcl(FacialExpression.FRIENDLY, "Well... What with all these warriors around, there's not much chance of my bank being robbed, is there?").also { stage = 2 }
            10 -> {
                openBankAccount(player)
                end()
            }
            11 -> {
                openBankPinSettings(player)
                end()
            }
            12 -> {
                openGrandExchangeCollectionBox(player)
                end()
            }
            13 -> {
                toggleBankAccount(player)
                npcl(FacialExpression.FRIENDLY, "Of course! Your ${getBankAccountName(player)} account is now active!").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray = intArrayOf(NPCs.JADE_4296)
}
