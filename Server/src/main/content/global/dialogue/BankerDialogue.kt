package content.global.dialogue

import content.global.handlers.npc.BankerNPC
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.dialogue.IfTopic
import core.game.dialogue.Topic
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.IronmanMode
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Banker dialogue.
 */
@Initializable
class BankerDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val oldModels =
            intArrayOf(NPCs.GNOME_BANKER_166, NPCs.BANKER_498, NPCs.BANKER_2163, NPCs.BANKER_2164, NPCs.BANKER_5776)
        val incompatibleModels = intArrayOf(NPCs.TZHAAR_KET_ZUH_2619, NPCs.OGRESS_BANKER_7049, NPCs.OGRESS_BANKER_7050)
        val checkRestriction = (amountInInventory(player, Items.STARDUST_13727) > 1 && amountInBank(
            player,
            Items.STARDUST_13727,
            true
        ) >= 200)
        val checkFacialExpression = when (npc.id) {
            in oldModels -> FacialExpression.OLD_NORMAL
            in incompatibleModels -> FacialExpression.CHILD_NORMAL
            else -> FacialExpression.FRIENDLY
        }
        when (stage) {
            START_DIALOGUE -> when {
                hasIronmanRestriction(player, IronmanMode.ULTIMATE) -> {
                    npcl(
                        FacialExpression.ANNOYED,
                        "My apologies, dear ${if (player.isMale) "sir" else "madam"}, " + "our services are not available for Ultimate ${if (player.isMale) "Ironman" else "Ironwoman"}"
                    ).also { stage = END_DIALOGUE }
                }

                else -> {
                    npcl(checkFacialExpression, "Good day, how may I help you?").also {
                        if (hasAwaitingGrandExchangeCollections(player)) {
                            stage++
                        } else {
                            stage += 2
                        }
                    }
                }
            }

            1 -> npcl(
                checkFacialExpression,
                "Before we go any further, I should inform you that you " + "have items ready for collection from the Grand Exchange."
            ).also { stage++ }

            2 -> showTopics(
                Topic(FacialExpression.FRIENDLY, "I'd like to access my bank account, please.", 10),
                IfTopic(
                    FacialExpression.FRIENDLY,
                    "I'd like to switch to my ${getBankAccountName(player, true)} bank account.",
                    13,
                    hasActivatedSecondaryBankAccount(player)
                ),
                IfTopic(
                    FacialExpression.FRIENDLY,
                    "I'd like to open a secondary bank account.",
                    20,
                    !hasActivatedSecondaryBankAccount(player)
                ),
                Topic(FacialExpression.FRIENDLY, "I'd like to check my PIN settings.", 11),
                Topic(FacialExpression.FRIENDLY, "I'd like to collect items.", 12),
                Topic(FacialExpression.ASKING, "What is this place?", 3),
            )

            3 -> npcl(
                checkFacialExpression,
                "This is a branch of the Bank of Gielinor. We have branches in many towns."
            ).also { stage++ }

            4 -> playerl(FacialExpression.ASKING, "And what do you do?").also { stage++ }
            5 -> npcl(
                checkFacialExpression,
                "We will look after your items and money for you. " + "Leave your valuables with us if you want to keep them safe."
            ).also { stage = END_DIALOGUE }

            10 -> {
                end()
                if (checkRestriction) {
                    sendDialogue(
                        player,
                        "The banker detected that the items in your inventory are blacklisted, which prevented you from opening a bank account. You can do it again after removing them."
                    )
                } else {
                    openBankAccount(player)
                }
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
                npcl(
                    checkFacialExpression,
                    "Your active bank account has been switched. " + "You can now access your ${
                        getBankAccountName(player)
                    } account."
                ).also { stage = 2 }
            }

            20 -> npcl(
                checkFacialExpression,
                "Certainly. We offer secondary accounts to all our customers."
            ).also { stage++ }

            21 -> npcl(
                checkFacialExpression,
                "The secondary account comes with a standard fee of 10,000 coins. The fee is non-refundable " + "and account activation is permanent."
            ).also { stage++ }

            22 -> npcl(
                checkFacialExpression,
                "If your inventory does not contain enough money to cover the costs, we will complement " + "the amount with the money inside your primary bank account."
            ).also { stage++ }

            23 -> npcl(
                checkFacialExpression,
                "Knowing all this, would you like to proceed with opening your secondary bank account?"
            ).also { stage++ }

            24 -> showTopics(
                Topic(FacialExpression.HAPPY, "Yes, I am still interested.", 25),
                Topic(FacialExpression.ANNOYED, "Actually, I've changed my mind.", 26)
            )

            25 -> {
                when (activateSecondaryBankAccount(player)) {
                    SecondaryBankAccountActivationResult.ALREADY_ACTIVE -> {
                        npcl(
                            checkFacialExpression,
                            "Your bank account was already activated, there is no need to pay twice."
                        ).also { stage = END_DIALOGUE }
                    }

                    SecondaryBankAccountActivationResult.INTERNAL_FAILURE -> {
                        npcl(
                            checkFacialExpression,
                            "I must apologize, the transaction was not successful. Please check your " + "primary bank account and your inventory - if there's money missing, please " + "screenshot your chat box and contact the game developers."
                        ).also { stage = END_DIALOGUE }
                    }

                    SecondaryBankAccountActivationResult.NOT_ENOUGH_MONEY -> {
                        npcl(
                            checkFacialExpression,
                            "It appears that you do not have the money necessary to cover the costs " + "associated with opening a secondary bank account. I will be waiting here " + "until you do."
                        ).also { stage = END_DIALOGUE }
                    }

                    SecondaryBankAccountActivationResult.SUCCESS -> {
                        npcl(
                            checkFacialExpression,
                            "Your secondary bank account has been opened and can be accessed through any " + "of the Bank of Gielinor's employees. Thank you for choosing our services."
                        ).also { stage = END_DIALOGUE }
                    }
                }
            }

            26 -> npcl(
                checkFacialExpression,
                "Very well. Should you decide a secondary bank account is needed, do not hesitate to " + "contact any of the Bank of Gielinor's stationary employees. We will be happy to help."
            ).also { stage = END_DIALOGUE }
        }

        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(*BankerNPC.NPC_IDS)
    }

}
