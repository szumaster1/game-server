package content.region.karamja.quest.monkeymadness.dialogue

import content.region.karamja.quest.monkeymadness.cutscenes.ShipyardCutScene
import core.api.consts.NPCs
import core.api.getQuestStage
import core.api.sendNPCDialogue
import core.api.setQuestStage
import core.game.dialogue.DialogueFile
import core.tools.END_DIALOGUE

/**
 * Waydar crash island dialogue
 *
 * @constructor Waydar crash island dialogue
 */
class WaydarCrashIslandDialogue: DialogueFile(){

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> {
                if (getQuestStage(player!!, "Monkey Madness") == 24) {
                    playerl("Where are we?").also { stage++ }
                } else {
                    end()
                }
            }
            1 -> npcl("I am not sure. We appear to have landed where the 10th squad crashed. The number of gnome gliders is correct. Unfortunately for them, it appears that non of their gliders survived the collision.").also { stage++ }
            2 -> playerl("Did our glider survive?").also { stage++ }
            3 -> npcl("Of course.").also { stage++ }
            4 -> options("What shall we do now?", "Do you recognize the gnome at the beach?", "Can you take me back to your kingdom?", "I cannot convince Lumdo to take us to the island.").also { stage++ }
            5 -> when(buttonID) {
                1 -> end()
                2 -> end()
                3 -> end()
                4 -> npcl("What is the problem?").also { stage++ }
            }
            6 -> playerl("He claims to be under direct orders from Garkor to guard their gliders until the rest return.").also { stage++ }
            7 -> npcl("His zeal in this matter is to be expected. The Royal Guard - in particular the 10th squad - are renowned for their fierce loyalty.").also { stage++ }
            8 -> playerl("Can you do anything?").also { stage++ }
            9 -> npcl("I would rather not get involved. My mission is to protect you.").also { stage++ }
            10 -> playerl("You must do something!").also { stage++ }
            11 -> npcl("You are becoming tiresome, human. As you wish.").also { stage++ }
            12 -> npcl("Foot soldier Lumdo of the 10th squad.").also { stage++ }
            13 -> sendNPCDialogue(player!!, NPCs.LUMDO_1419, "Yes?").also { stage++ }
            14 -> npcl("I am Flight Commander Waydar. I believe you are under direct orders from your Sergeant to guard this gliders?").also { stage++ }
            15 -> sendNPCDialogue(player!!, NPCs.LUMDO_1419, "That is correct, Commander.").also { stage++ }
            16 -> npcl("I need not to remind you that i outrank Garkor. As of this instant, your orders are to convey the human to the atoll and remain there until he needs to return.").also { stage++ }
            17 -> sendNPCDialogue(player!!, NPCs.LUMDO_1419, "Garkor will not be pleased!").also { stage++ }
            18 -> npcl("Then he can take up his issues with me personally.").also { stage++ }
            19 -> playerl("Waydar! Will you not accompany me to the island?").also { stage++ }
            20 -> npcl("No. after all, somebody has to look after the gliders.").also { stage++ }
            21 -> playerl("But it is your mission to protect me!").also { stage++ }
            22 -> npcl("Enough. Return here when you are done.").also {
                stage = END_DIALOGUE
                ShipyardCutScene(player!!).start()
                setQuestStage(player!!, "Monkey Madness", 25)
            }
        }
    }
}
