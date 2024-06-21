package content.global.random.event.pinball

import core.api.*
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.system.timer.impl.AntiMacro
import core.utilities.BLUE

class PinballGuardDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> if (getAttribute(player!!, PinballUtils.PINBALL_SCORE, 0) >= 10) {
                playerl(FacialExpression.HALF_GUILTY, "So... I'm free to go now right?").also { stage++ }
            } else {
                lock(player!!, 1000)
                sendNPCDialogue(player!!, 3912, "You poke 10 flashing pillars, right? You NOT poke other pillars, right? Okay, you go play now.", FacialExpression.OLD_NORMAL)
                stage += 3
            }
            1 -> sendNPCDialogue(player!!, 3912, "Yer, get going. We get break now.", FacialExpression.OLD_NORMAL).also { stage++ }
            2 -> {
                end()
                PinballUtils.cleanup(player!!)
                AntiMacro.rollEventLoot(player!!).forEach { addItemOrDrop(player!!, it.id, it.amount) }
            }
            3 -> {
                end()
                sendUnclosableDialogue(player!!,true, "", "Tag the post with the " + BLUE + "flashing rings.")
                unlock(player!!)
            }
        }
    }
}
