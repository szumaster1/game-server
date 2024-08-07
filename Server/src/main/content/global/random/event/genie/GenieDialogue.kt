package content.global.random.event.genie

import core.api.addItemOrDrop
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.system.timer.impl.AntiMacro
import core.tools.END_DIALOGUE

/**
 * Genie dialogue.
 */
class GenieDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        val assigned = player!!.getAttribute("genie:item", 0)
        npcl(FacialExpression.NEUTRAL, "Ah, so you are there, ${player!!.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }}. I'm so glad you summoned me. Please take this lamp and make your wish.")
        addItemOrDrop(player!!, assigned)
        AntiMacro.terminateEventNpc(player!!)
        stage = END_DIALOGUE
    }
}
