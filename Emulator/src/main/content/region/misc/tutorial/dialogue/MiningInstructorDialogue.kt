package content.region.misc.tutorial.dialogue

import content.region.misc.tutorial.handlers.TutorialStage
import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Mining Instructor dialogue.
 */
@Initializable
class MiningInstructorDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        val tutorialStage = getAttribute(player, "tutorial:stage", 0)

        when (tutorialStage) {
            30 -> greetNewcomer()
            34 -> informAboutRocks()
            35 -> handlePickaxeStage()
            40 -> askAboutWeapon()
            41 -> handleHammerStage()
        }

        return true
    }

    private fun greetNewcomer() {
        npcl(
            FacialExpression.FRIENDLY,
            "Hi there. You must be new around here. So what do I call you? 'Newcomer' seems so impersonal, and if we're going to be working together, I'd rather tell you by name."
        )
    }

    private fun informAboutRocks() {
        playerl(
            FacialExpression.FRIENDLY,
            "I prospected both types of rock! One set contains tin and the other has copper ore inside."
        )
    }

    private fun handlePickaxeStage() {
        if (!inInventory(player, Items.BRONZE_PICKAXE_1265)) {
            addItem(player, Items.BRONZE_PICKAXE_1265)
            sendItemDialogue(player, Items.BRONZE_PICKAXE_1265, "Dezzick gives you a bronze pickaxe!")
            stage = 3
        } else {
            TutorialStage.load(player, 35)
        }
    }

    private fun askAboutWeapon() {
        playerl(FacialExpression.ASKING, "How do I make a weapon out of this?")
    }

    private fun handleHammerStage() {
        if (!inInventory(player, Items.HAMMER_2347)) {
            addItem(player, Items.HAMMER_2347)
            sendItemDialogue(player, Items.HAMMER_2347, "Dezzick gives you a hammer!")
            stage = 3
        } else {
            end()
            TutorialStage.load(player, 41)
        }
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val tutorialStage = getAttribute(player, "tutorial:stage", 0)

        when (tutorialStage) {
            30 -> handleStage30()
            34, 35 -> handleStage34And35()
            40, 41 -> handleStage40And41()
        }

        return true
    }

    private fun handleStage30() {
        when (stage) {
            0 -> {
                playerl(FacialExpression.FRIENDLY, "You can call me ${player?.username}.")
                stage++
            }

            1 -> {
                npcl(FacialExpression.FRIENDLY, "Ok then, ${player?.username}. My name is Dezzick and I'm a miner by trade. Let's prospect some of these rocks.")
                stage++
            }

            2 -> {
                end()
                setAttribute(player, "tutorial:stage", 31)
                TutorialStage.load(player, 31)
            }
        }
    }

    private fun handleStage34And35() {
        when (stage) {
            0 -> {
                npcl(FacialExpression.FRIENDLY, "Absolutely right, ${player?.username}. These two ore types can be smelted together to make bronze.")
                stage++
            }

            1 -> {
                npcl(FacialExpression.FRIENDLY, "So now you know what ore is in the rocks over there, why don't you have a go at mining some tin and copper? Here, you'll need this to start with.")
                stage++
            }

            2 -> {
                addItem(player, Items.BRONZE_PICKAXE_1265)
                sendItemDialogue(player, Items.BRONZE_PICKAXE_1265, "Dezzick gives you a bronze pickaxe!")
                stage++
            }

            3 -> {
                end()
                setAttribute(player, "tutorial:stage", 35)
                TutorialStage.load(player, 35)
            }
        }
    }

    private fun handleStage40And41() {
        when (stage) {
            0 -> {
                npcl(FacialExpression.FRIENDLY, "Okay, I'll show you how to make a dagger out of it. You'll be needing this..")
                stage++
            }

            1 -> {
                addItem(player, Items.HAMMER_2347)
                sendItemDialogue(player, Items.HAMMER_2347, "Drezzick gives you a hammer!")
                stage++
            }

            2 -> {
                end()
                setAttribute(player, "tutorial:stage", 41)
                TutorialStage.load(player, 41)
            }
        }
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MINING_INSTRUCTOR_948)
    }
}