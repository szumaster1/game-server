package content.global.random.event.lostpirate

import core.api.addItemOrDrop
import core.api.consts.Items
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.system.timer.impl.AntiMacro

class CapnHandDialogue(val rand : Int) : DialogueFile() {
    override fun handle(componentID: Int, buttonID: Int) {
        stage = rand
        when (stage) {
            0 -> npc(FacialExpression.OLD_DEFAULT, "Yarr!","It's a fine day to go a pirating!").also { stage++ }
            1 -> {
                AntiMacro.rollEventLoot(player!!).forEach { addItemOrDrop(player!!, it.id, it.amount) }
                AntiMacro.terminateEventNpc(player!!)
                end()
            }
        }
    }
}
