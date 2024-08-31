package content.region.misc.tutorial.dialogue

import content.region.misc.tutorial.handlers.TutorialStage
import cfg.consts.Components
import cfg.consts.NPCs
import core.api.*
import core.game.component.Component
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Quest Guide dialogue.
 */
@Initializable
class QuestGuideDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        val npc = args[0] as NPC
        val tutorialStage = getAttribute(player, "tutorial:stage", 0)

        return when (tutorialStage) {
            27 -> {
                showDialogue(
                    npc,
                    "Ah. Welcome, adventurer. I'm here to tell you all about",
                    "quests. Let's start by opening the Quest List."
                )
                true
            }

            28 -> {
                showDialogue(
                    npc,
                    "Now you have the journal open. I'll tell you a bit about",
                    "it. At the moment all the quests are shown in red, which",
                    "means you have not started them yet."
                )
                true
            }

            else -> false
        }
    }

    private fun showDialogue(npc: NPC, vararg messages: String) {
        Component.setUnclosable(player, interpreter.sendDialogues(npc, FacialExpression.FRIENDLY, *messages))
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val tutorialStage = getAttribute(player, "tutorial:stage", 0)

        return when (tutorialStage) {
            27 -> {
                showQuestJournalInstructions()
                true
            }

            28 -> handleQuestStage()
            else -> false
        }
    }

    private fun showQuestJournalInstructions() {
        setVarbit(player, 3756, 3)
        sendUnclosableDialogue(player, true, "${core.tools.BLUE}Open the Quest Journal.", "", "Click on the flashing icon next to your inventory.")
        openInterface(player, Components.QUESTJOURNAL_V2_274)
    }

    private fun handleQuestStage(): Boolean {
        return when (stage) {
            0 -> {
                showDialogue(npc, "When you start a quest it will change colour to yellow,", "and to green when you've finished. This is so you can", "easily see what's complete, what's started and what's left", "to begin.")
                stage++
                true
            }

            1 -> {
                showDialogue(npc, "The start of quests are easy to find. Look out for the", "star icons on the minimap, just like the one you should", "see marking my house.")
                stage++
                true
            }

            2 -> {
                showDialogue(npc, "There's not a lot more I can tell you about questing.", "You have to experience the thrill of it yourself to fully", "understand. You may find some adventure in the caves", "under my house.")
                stage++
                true
            }

            3 -> {
                end()
                setAttribute(player, "tutorial:stage", 29)
                TutorialStage.load(player, 29)
                true
            }

            else -> false
        }
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.QUEST_GUIDE_949)
    }
}