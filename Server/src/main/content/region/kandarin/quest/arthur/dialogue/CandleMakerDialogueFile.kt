package content.region.kandarin.quest.arthur.dialogue

import content.region.kandarin.quest.arthur.MerlinUtils
import cfg.consts.Items
import cfg.consts.NPCs
import core.api.sendDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.npc.NPC
import core.game.node.item.Item
import core.tools.END_DIALOGUE

/**
 * Represents the Candle Maker dialogue file.
 *
 * Related to [Merlin Crystal][content.region.kandarin.quest.arthur.MerlinCrystal] quest.
 * @author lostmyphat
 */
class CandleMakerDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.CANDLE_MAKER_562)

        when (stage) {
            0 -> {
                if (player!!.getAttribute(MerlinUtils.ATTR_STATE_TALK_CANDLE, false) == false) {
                    npcl(FacialExpression.NEUTRAL, "Hi! Would you be interested in some of my fine candles?")
                    stage++
                } else {
                    npcl(FacialExpression.THINKING, "Have you got any wax yet?")

                    if (player!!.hasItem(Item(Items.BUCKET_OF_WAX_30))) {
                        stage = 25
                    } else {
                        stage = 20
                    }

                }
            }

            1 -> showTopics(
                    Topic(FacialExpression.NEUTRAL, "Have you got any black candles?", 2),
                    Topic(FacialExpression.NEUTRAL, "Yes please.", 10),
                    Topic(FacialExpression.NEUTRAL, "No thank you.", END_DIALOGUE))

            2 -> npcl(FacialExpression.AFRAID, "BLACK candles???").also { stage++ }
            3 -> npcl(FacialExpression.THINKING, "Hmmm. In the candle making trade, we have a tradition that it's very bad luck to make black candles.").also { stage++ }
            4 -> npcl(FacialExpression.THINKING, "VERY bad luck.").also { stage++ }
            5 -> playerl(FacialExpression.NEUTRAL, "I will pay good money for one...").also { stage++ }
            6 -> npcl(FacialExpression.THINKING, "I still dunno...").also { stage++ }
            7 -> npcl(FacialExpression.THINKING, "Tell you what. I'll supply you with a black candle...").also { stage++ }
            8 -> npcl(FacialExpression.THINKING, "IF you can bring me a bucket FULL of wax.").also {
                player!!.setAttribute(MerlinUtils.ATTR_STATE_TALK_CANDLE, true)
                stage = END_DIALOGUE
            }

            10 -> {
                end()
                npc!!.openShop(player)
            }

            // player has no wax
            20 -> playerl(FacialExpression.NEUTRAL, "Nope, not yet.").also { stage = END_DIALOGUE }

            // player has wax
            25 -> playerl(FacialExpression.NEUTRAL, "Yes, I have some now.").also { stage++ }
            26 -> sendDialogue(player!!, "You exchange the wax with the candle maker for a black candle.").also {
                player!!.inventory.remove(Item(Items.BUCKET_OF_WAX_30, 1))
                player!!.inventory.add(Item(Items.BLACK_CANDLE_38, 1))
                player!!.setAttribute(MerlinUtils.ATTR_STATE_TALK_CANDLE, false)
                stage = END_DIALOGUE
            }

        }
    }
}
