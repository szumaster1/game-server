package content.global.skill.combat.summoning.familiar.dialogue

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.RemoteViewer
import core.game.dialogue.Dialogue
import core.game.dialogue.DialogueInterpreter
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Remote view dialogue.
 */
@Initializable
class RemoteViewDialogue(player: Player? = null) : Dialogue(player) {

    private var familiar: Familiar? = null

    override fun open(vararg args: Any): Boolean {
        familiar = args[0] as Familiar
        options("North", "East", "South", "West", "Straight up")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        end()
        RemoteViewer.create(player, familiar, familiar!!.viewAnimation, RemoteViewer.ViewType.values()[-1 + buttonId]).startViewing()
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(DialogueInterpreter.getDialogueKey(RemoteViewer.DIALOGUE_NAME))
    }
}
