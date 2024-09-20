package content.global.skill.hunter.imp

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueInterpreter
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Represents the imp box dialogue.
 */
class ImpBoxDialogue : Dialogue {

    constructor()

    constructor(player: Player?) : super(player)

    override fun newInstance(player: Player): Dialogue {
        return ImpBoxDialogue(player)
    }

    override fun open(vararg args: Any): Boolean {
        interpreter.sendDialogues(
            NPCs.IMP_708,
            FacialExpression.FURIOUS,
            MESSAGES[RandomFunction.getRandom(MESSAGES.size - 1)]
        )
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        end()
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(DialogueInterpreter.getDialogueKey("imp-box"))
    }

    companion object {
        private val MESSAGES =
            arrayOf("Let me outa here!", "Errgghh..", "Well look who it is.", "What are you looking at?")
    }
}