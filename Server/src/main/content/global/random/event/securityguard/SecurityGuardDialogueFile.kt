package content.global.random.event.securityguard

import core.api.addItemOrDrop
import core.api.consts.Items
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.system.timer.impl.AntiMacro

/**
 * Security guard dialogue file.
 */
class SecurityGuardDialogueFile : DialogueFile() {
    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> npc(FacialExpression.OLD_DEFAULT, "My records show you have your recovery questions", "set. Here, take this small gift and book and explore the", "Stronghold of Security. There's some great rewards to", "be had there!").also { stage++ }
            1 -> {
                AntiMacro.rollEventLoot(player!!).forEach { addItemOrDrop(player!!, it.id, it.amount) }
                addItemOrDrop(player!!, Items.SECURITY_BOOK_9003)
                AntiMacro.terminateEventNpc(player!!)
                end()
            }
        }
    }
}