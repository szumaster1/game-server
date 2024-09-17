package content.region.karamja.apeatoll.quest.mm.dialogue

import org.rs.consts.NPCs
import core.api.getQuestStage
import core.api.sendNPCDialogue
import core.api.setQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Represents the Waydar crash island dialogue.
 */
class WaydarCrashIslandDialogue: DialogueFile(){

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.WAYDAR_1408)
        when (stage) {
            0 -> {
                if (getQuestStage(player!!, "Monkey Madness") == 24) {
                    playerl("Where are we?").also { stage++ }
                } else {
                    end()
                }
            }
            1 -> npcl(FacialExpression.OLD_NORMAL, "I am not sure. We appear to have landed where the 10th squad crashed. The number of gnome gliders is correct. Unfortunately for them, it appears that non of their gliders survived the collision.").also { stage++ }
            2 -> playerl("Did our glider survive?").also { stage++ }
            3 -> npcl(FacialExpression.OLD_NORMAL,"Of course.").also { stage++ }
            4 -> options("What shall we do now?", "Do you recognize the gnome at the beach?", "Can you take me back to your kingdom?", "I cannot convince Lumdo to take us to the island.").also { stage++ }
            5 -> when(buttonID) {
                1 -> end()
                2 -> end()
                3 -> end()
                4 -> npcl(FacialExpression.OLD_NORMAL,"What is the problem?").also { stage++ }
            }
            6 -> playerl("He claims to be under direct orders from Garkor to guard their gliders until the rest return.").also { stage++ }
            7 -> npcl(FacialExpression.OLD_NORMAL,"His zeal in this matter is to be expected. The Royal Guard - in particular the 10th squad - are renowned for their fierce loyalty.").also { stage++ }
            8 -> playerl("Can you do anything?").also { stage++ }
            9 -> npcl(FacialExpression.OLD_NORMAL,"I would rather not get involved. My mission is to protect you.").also { stage++ }
            10 -> playerl("You must do something!").also { stage++ }
            11 -> npcl(FacialExpression.OLD_NORMAL,"You are becoming tiresome, human. As you wish.").also { stage++ }
            12 -> npcl(FacialExpression.OLD_NORMAL,"Foot soldier Lumdo of the 10th squad.").also { stage++ }
            13 -> sendNPCDialogue(player!!, NPCs.LUMDO_1419, "Yes?", FacialExpression.OLD_NORMAL).also { stage++ }
            14 -> npcl(FacialExpression.OLD_NORMAL,"I am Flight Commander Waydar. I believe you are under direct orders from your Sergeant to guard this gliders?").also { stage++ }
            15 -> sendNPCDialogue(player!!, NPCs.LUMDO_1419, "That is correct, Commander.", FacialExpression.OLD_NORMAL).also { stage++ }
            16 -> npcl(FacialExpression.OLD_NORMAL,"I need not to remind you that i outrank Garkor. As of this instant, your orders are to convey the human to the atoll and remain there until he needs to return.").also { stage++ }
            17 -> sendNPCDialogue(player!!, NPCs.LUMDO_1419, "Garkor will not be pleased!", FacialExpression.OLD_NORMAL).also { stage++ }
            18 -> npcl(FacialExpression.OLD_NORMAL,"Then he can take up his issues with me personally.").also { stage++ }
            19 -> playerl("Waydar! Will you not accompany me to the island?").also { stage++ }
            20 -> npcl("No. after all, somebody has to look after the gliders.").also { stage++ }
            21 -> playerl("But it is your mission to protect me!").also { stage++ }
            22 -> npcl(FacialExpression.OLD_NORMAL,"Enough. Return here when you are done.").also {
                stage = END_DIALOGUE
                content.region.karamja.apeatoll.quest.mm.cutscenes.ShipyardCutScene(player!!).start()
                setQuestStage(player!!, "Monkey Madness", 25)
            }
        }
    }
}
