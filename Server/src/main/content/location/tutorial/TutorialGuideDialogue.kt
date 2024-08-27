package content.location.tutorial

import content.region.misc.handlers.tutorial.TutorialStage
import core.api.*
import cfg.consts.NPCs
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
        options("Skip tutorial island?", "Nevermind.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> when (buttonId) {
                1 -> sendDialogueOptions(player, "Are you sure?", "YES", "NO").also { stage++ }
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