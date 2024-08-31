package content.region.misc.tutorial.dialogue

import cfg.consts.NPCs
import content.region.misc.tutorial.handlers.TutorialStage
import core.api.*
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Represents the Tutorial Guide dialogue.
 */
@Initializable
class TutorialGuideDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        /*
         * Hide title swords so that the title of the dialogue is
         * visible to the player.
         */
        setComponentVisibility(player, 228, 6, true)
        setComponentVisibility(player, 228, 9, true)
        face(findNPC(NPCs.TUTORIAL_GUIDE_8591)!!, player, 1)
        sendDialogueOptions(
            player,
            "Hey. Do you wanna skip the Tutorial? I can send you straight<br>to Lumbridge.",
            "Skip tutorial island?", "Nevermind."
        )
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> when (buttonId) {
                1 -> sendDialogueOptions(player, "Are you sure?", "YES!", "NO.").also { stage++ }
                2 -> end()
            }

            1 -> when (buttonId) {
                1 -> {
                    end()
                    setAttribute(player!!, "/save:tutorial:stage", 71)
                    TutorialStage.load(player!!, 71)
                    teleport(player!!, Location.create(3141, 3089, 0))
                }

                2 -> end()
            }
        }
        return true
    }


    override fun newInstance(player: Player?): Dialogue {
        return TutorialGuideDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TUTORIAL_GUIDE_8591)
    }
}