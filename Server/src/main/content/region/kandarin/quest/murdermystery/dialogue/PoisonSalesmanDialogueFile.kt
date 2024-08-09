package content.region.kandarin.quest.murdermystery.dialogue

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Poison salesman dialogue file.
 */
class PoisonSalesmanDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        val questName = "Murder Mystery"
        val questStage = getQuestStage(player!!, questName)
        npc = NPC(NPCs.POISON_SALESMAN_820)

        when {
            (questStage >= 2) -> {
                when (stage) {
                    0 -> playerl(FacialExpression.SUSPICIOUS, "I'm investigating the murder at the Sinclair house.").also { stage++ }
                    1 -> npcl(FacialExpression.ANNOYED, "There was a murder at the Sinclair House??? That's terrible! And I was only there the other day too! They bought the last of my Patented Multi Purpose Poison!").also { stage++ }
                    2 -> {
                        if (inInventory(player!!, Items.PUNGENT_POT_1812)) {
                            options("Patented Multi Purpose Poison?", "Who did you sell Poison to at the house?", "Can I buy some Poison?", "I have this pot I found at the murder scene...").also { stage++ }
                        } else {
                            options("Patented Multi Purpose Poison?", "Who did you sell Poison to at the house?", "Can I buy some Poison?").also { stage++ }
                        }
                    }
                    3 -> {
                        if (inInventory(player!!, Items.PUNGENT_POT_1812)) {
                            when (buttonID) {
                                1 -> playerl(FacialExpression.SUSPICIOUS, "Patented Multi Purpose Poison?").also { stage++ }
                                2 -> playerl(FacialExpression.SUSPICIOUS, "Who did you sell Poison to at the house?").also { stage = 14 }
                                3 -> playerl(FacialExpression.SUSPICIOUS, "Can I buy some Poison?").also { stage = 18 }
                                4 -> playerl(FacialExpression.SUSPICIOUS, "I have this pot I found at the murder scene...").also { stage = 22 }
                            }
                        } else {
                            when (buttonID) {
                                1 -> playerl(FacialExpression.SUSPICIOUS, "Patented Multi Purpose Poison?").also { stage++ }
                                2 -> playerl(FacialExpression.SUSPICIOUS, "Who did you sell Poison to at the house?").also { stage = 14 }
                                3 -> playerl(FacialExpression.SUSPICIOUS, "Can I buy some Poison?").also { stage = 18 }
                            }
                        }
                    }
                    4 -> npcl(FacialExpression.NEUTRAL, "Aaaaah... a miracle of modern apothecaries!").also { stage++ }
                    5 -> npcl(FacialExpression.NEUTRAL, "This exclusive concoction has been tested on all known forms of life and been proven to kill them all in varying dilutions from cockroaches to king dragons!").also { stage++ }
                    6 -> npcl(FacialExpression.NEUTRAL, "So incredibly versatile, it can be used as pest control, a cleansing agent, drain cleaner, metal polish and washes whiter than white,").also { stage++ }
                    7 -> npcl(FacialExpression.NEUTRAL, "all with our uniquely fragrant concoction that is immediately recognisable across the land as Peter Potter's Patented Poison potion!!!").also { stage++ }
                    8 -> sendDialogue(player!!, "The salesman stops for breath.").also { stage++ }
                    9 -> npcl(FacialExpression.NEUTRAL, "I'd love to sell you some but I've sold out recently. That's just how good it is! Three hundred and twenty eight people in this area alone cannot be wrong!").also { stage++ }
                    10 -> npcl(FacialExpression.NEUTRAL, "Nine out of Ten poisoners prefer it in controlled tests!").also { stage++ }
                    11 -> npcl(FacialExpression.NEUTRAL, "Can I help you with anything else?").also { stage++ }
                    12 -> npcl(FacialExpression.NEUTRAL, "Perhaps I can take your name and add it to our mailing list of poison users? We will only send you information related to the use of poison and other Peter Potter Products!").also { stage++ }
                    13 -> playerl(FacialExpression.SUSPICIOUS, "Uh... no, it's ok. Really.").also { stage = END_DIALOGUE }
                    14 -> npcl(FacialExpression.NEUTRAL, "Well, Peter Potter's Patented Multi Purpose Poison is a product of such obvious quality that I am glad to say I managed to sell a bottle to each of the Sinclairs!").also { stage++ }
                    15 -> npcl(FacialExpression.NEUTRAL, "Anna, Bob, Carol, David, Elizabeth and Frank all bought a bottle! In fact they bought the last of my supplies!").also { stage++ }
                    16 -> npcl(FacialExpression.NEUTRAL, "Maybe I can take your name and address and I will personally come and visit you when stocks return?").also { stage++ }
                    17 -> playerl(FacialExpression.SUSPICIOUS, "Uh... no, it's ok.").also { stage = END_DIALOGUE }
                    18 -> npcl(FacialExpression.NEUTRAL, "I'm afraid I am totally out of stock at the moment after my successful trip to the Sinclairs' House the other day.").also { stage++ }
                    19 -> npcl(FacialExpression.NEUTRAL, "But don't worry! Our factories are working overtime to produce Peter Potter's Patented Multi Purpose Poison!").also { stage++ }
                    20 -> npcl(FacialExpression.NEUTRAL, "Possibly the finest multi purpose poison and cleaner yet available to the general market.").also { stage++ }
                    21 -> npcl(FacialExpression.NEUTRAL, "And its unique fragrance makes it the number one choice for cleaners and exterminators the whole country over!").also { stage = END_DIALOGUE }
                    22 -> sendItemDialogue(player!!, Items.PUNGENT_POT_1812, "You show the poison salesman the pot you found at the murder scene with the unusual smell.").also { stage++ }
                    23 -> npcl(FacialExpression.NEUTRAL, "Hmmm... yes, that smells exactly like my Patented Multi Purpose Poison, but I don't see how it could be. It quite clearly says on the label of all bottles").also { stage++ }
                    24 -> npcl(FacialExpression.NEUTRAL, "'Not to be taken internally - EXTREMELY POISONOUS'.").also { stage++ }
                    25 -> playerl(FacialExpression.SUSPICIOUS, "Perhaps someone else put it in his wine?").also { stage++ }
                    26 -> npcl(FacialExpression.NEUTRAL, "Yes... I suppose that could have happened...").also { stage++ }
                    27 -> {
                        end()
                        setQuestStage(player!!, "Murder Mystery", 3)
                    }
                }
            }

            (isQuestComplete(player!!, "Murder Mystery")) -> when (stage) {
                    0 -> npcl(FacialExpression.FRIENDLY, "I hear you're pretty smart to have solved the Sinclair Murder!").also { stage = END_DIALOGUE }
            }
        }
    }
}
