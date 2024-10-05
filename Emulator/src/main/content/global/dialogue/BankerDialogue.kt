package content.global.dialogue

import content.global.handlers.npc.BankerNPC
import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
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
 * Represents the Banker dialogue.
 */
@Initializable
class BankerDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val oldModels = intArrayOf(NPCs.GNOME_BANKER_166, NPCs.BANKER_498, NPCs.BANKER_2163, NPCs.BANKER_2164, NPCs.BANKER_5776)
        val incompatibleModels = intArrayOf(NPCs.TZHAAR_KET_ZUH_2619, NPCs.OGRESS_BANKER_7049, NPCs.OGRESS_BANKER_7050)
        val checkRestriction = (amountInInventory(player, Items.STARDUST_13727) > 1 && amountInBank(player, Items.STARDUST_13727, true) >= 200)
        val checkFacialExpression = when (npc.id) {
            in oldModels -> FacialExpression.OLD_NORMAL
            in incompatibleModels -> FacialExpression.CHILD_NORMAL
            else -> FacialExpression.FRIENDLY
        }
        when (stage) {
            START_DIALOGUE -> when {
                hasIronmanRestriction(player, IronmanMode.ULTIMATE) -> {
                    npcl(FacialExpression.ANNOYED, "My apologies, dear ${if (player.isMale) "sir" else "madam"}, " + "our services are not available for Ultimate ${if (player.isMale) "Ironman" else "Ironwoman"}").also { stage = END_DIALOGUE }
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
            1 -> npcl(checkFacialExpression, "Before we go any further, I should inform you that you " + "have items ready for collection from the Grand Exchange.").also { stage++ }
            2 -> showTopics(Topic(FacialExpression.FRIENDLY, "I'd like to access my bank account, please.", 10), Topic(FacialExpression.FRIENDLY, "I'd like to check my PIN settings.", 11), Topic(FacialExpression.FRIENDLY, "I'd like to collect items.", 12), Topic(FacialExpression.ASKING, "What is this place?", 3))
            3 -> npcl(checkFacialExpression, "This is a branch of the Bank of Gielinor. We have branches in many towns.").also { stage++ }
            4 -> playerl(FacialExpression.ASKING, "And what do you do?").also { stage++ }
            5 -> npcl(checkFacialExpression, "We will look after your items and money for you. " + "Leave your valuables with us if you want to keep them safe.").also { stage = END_DIALOGUE }
            10 -> {
                end()
                if (checkRestriction) {
                    sendDialogue(player, "The banker detected that the items in your inventory are blacklisted, which prevented you from opening a bank account. You can do it again after removing them.")
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
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(*BankerNPC.NPC_IDS)
    }

}
