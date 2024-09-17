package content.region.misc.tutorial.dialogue

import content.region.misc.tutorial.handlers.TutorialStage
import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.component.Component
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Master Chef dialogue.
 */
@Initializable
class MasterChefDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        val tutorialStage = getAttribute(player, "tutorial:stage", 0)
        return when (tutorialStage) {
            18 -> {
                sendUnclosableDialogue(player, false, "Ah! Welcome, newcomer. I am the Master Chef, Lev. It", "is here I will teach you how to cook food truly fit for a", "king.")
                true
            }

            19, 20 -> handleItemRequests()
            else -> true
        }
    }

    private fun handleItemRequests(): Boolean {
        if (!inInventory(player, Items.BREAD_DOUGH_2307)) {
            when {
                !inInventory(player, Items.BUCKET_OF_WATER_1929) -> {
                    sendItemDialogue(player, Items.BUCKET_OF_WATER_1929, "The Master Chef gives you another bucket of water.")
                    addItem(player, Items.BUCKET_OF_WATER_1929)
                }

                !inInventory(player, Items.POT_OF_FLOUR_1933) -> {
                    sendItemDialogue(player, Items.POT_OF_FLOUR_1933, "The Master Chef gives you another pot of flour.")
                    addItem(player, Items.POT_OF_FLOUR_1933)
                }

                else -> sendMessage(player, "Nothing interesting happens.")
            }
            TutorialStage.load(player, 19)
            return false
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val tutorialStage = getAttribute(player, "tutorial:stage", 0)
        if (tutorialStage == 18) {
            return handleDialogueStages()
        }
        return true
    }

    private fun handleDialogueStages(): Boolean {
        return when (stage) {
            0 -> {
                sendUnclosableDialogue(player, false, "I already know how to cook. Brynna taught me just", "now.").also { stage++ }
                true
            }

            1 -> {
                sendUnclosableDialogue(player, false, "Hahahahahaha! You call THAT cooking? Some shrimp", "on an open log fire? Oh, no, no no. I am going to", "teach you the fine art of cooking bread.").also { stage++ }
                true
            }

            2 -> {
                sendUnclosableDialogue(player, false, "And no fine meal is complete without good music, so", "we'll cover that while you're here too.").also { stage++ }
                true
            }

            3 -> {
                Component.setUnclosable(player, interpreter.sendDoubleItemMessage(Items.BUCKET_OF_WATER_1929, Items.POT_OF_FLOUR_1933, "The Cooking Guide gives you a <col=08088A>bucket of water<col> and a <col=08088A>pot of flour</col>."))
                addItem(player, Items.BUCKET_OF_WATER_1929)
                addItem(player, Items.POT_OF_FLOUR_1933)
                stage++
                true
            }

            4 -> {
                end()
                setAttribute(player, "tutorial:stage", 19)
                TutorialStage.load(player, 19)
                true
            }

            else -> false
        }
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MASTER_CHEF_942)
    }
}
