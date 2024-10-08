package content.global.ame.certer

import core.api.addItemOrDrop
import core.game.component.Component
import core.game.dialogue.DialogueFile
import core.game.node.entity.impl.PulseType
import core.game.node.entity.player.link.emote.Emotes
import core.game.system.timer.impl.AntiMacro
import core.tools.END_DIALOGUE

/**
 * Represents the Certer dialogue.
 */
class CerterDialogue(val initial: Boolean) : DialogueFile() {

    val CERTER_INTERFACE = 184
    override fun handle(componentID: Int, buttonID: Int) {
        if (initial && !player!!.getAttribute("certer:reward", false)) {
            when (stage) {
                0 -> npc("Ah, hello, ${player!!.username.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }}. Could you", "please help me identify this?").also { stage++ }
                1 -> {
                    end()
                    player!!.interfaceManager.open(Component(CERTER_INTERFACE))
                }
            }
        } else {
            player!!.setAttribute("random:pause", true)
            val isCorrect = player!!.getAttribute("certer:correct", false)
            val receivedReward = player!!.getAttribute("certer:reward", false)
            if (receivedReward == true) {
                stage = 1
            }
            when (stage) {
                0 -> if (!isCorrect) {
                    npc("Sorry, I don't think so.").also {
                        player!!.setAttribute("certer:reward", true)
                        stage = END_DIALOGUE
                        AntiMacro.terminateEventNpc(player!!)
                    }
                } else {
                    npc("Thank you, I hope you like your present. I must be", "leaving now though.").also {
                        player!!.setAttribute("certer:reward", true)
                        stage = END_DIALOGUE
                        AntiMacro.rollEventLoot(player!!).forEach { addItemOrDrop(player!!, it.id, it.amount) }
                    }
                }
            }
        }
    }

    override fun end() {
        super.end()
        if (player!!.getAttribute("certer:reward", false)) {
            // Remove movement pulse to stop following player
            npc!!.pulseManager.clear(PulseType.STANDARD)
            // Wave goodbye
            npc!!.animate(Emotes.WAVE.animation)
            // Terminate the event
            AntiMacro.terminateEventNpc(player!!)
        } else {
            player!!.setAttribute("random:pause", false)
        }
    }
}
