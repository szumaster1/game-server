package content.region.karamja.quest.monkeymadness.dialogue

import content.global.transport.glider.GnomeGlider
import content.global.transport.glider.GnomeGliderListeners
import core.api.getQuestStage
import core.api.submitWorldPulse
import core.game.component.Component
import core.game.dialogue.DialogueFile
import core.tools.END_DIALOGUE

/**
 * Represents the Waydar dialogue.
 */
class WaydarDialogue: DialogueFile(){

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> {
                if (getQuestStage(player!!, "Monkey Madness") <= 22)
                    npcl("Sorry, I am not authorised to talk to you.").also { stage = END_DIALOGUE }
                else
                    npcl("You should stock up well on food before our journey. i can carry only enough provision for myself.").also { stage++ }
            }
            1 -> npcl("I'd be careful of the local fauna too - i've heard the bite is far worse than any noise they make.").also { stage++ }
            2 -> npcl("Do you wish to fly right now?").also { stage++ }
            3 -> options("Yes", "No").also { stage++ }
            4 -> when(buttonID) {
                1 -> playerl("Yes let's go.").also { stage++ }
                2 -> end()
            }
            5 -> npcl("As you wish").also { stage++ }
            6 -> {
                player!!.interfaceManager.open(Component(138))
                submitWorldPulse(GnomeGliderListeners.GliderPulse(1, player!!, GnomeGlider.forId(14)!!))
                stage = END_DIALOGUE
            }
        }
    }
}
