package content.global.skill.production.herblore

import core.game.dialogue.DialogueFile
import core.utilities.END_DIALOGUE

internal class DecantDialogue : DialogueFile() {
    override fun handle(componentID: Int, buttonID: Int) {
        when (stage++) {
            0 -> npc("There you go!")
            1 -> player("Thanks!").also { stage = END_DIALOGUE }
        }
    }
}
