package content.region.kandarin.ardougne.quest.biohazard.dialogue

import org.rs.consts.NPCs
import core.api.getQuestStage
import core.api.sendMessage
import core.api.teleport
import core.game.dialogue.DialogueFile
import core.game.node.entity.npc.NPC
import core.game.world.map.Location
import org.rs.consts.QuestName

/**
 * Represents the Kilron dialogue (Biohazard quest).
 */
class KilronDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        val questStage = getQuestStage(player!!, QuestName.BIOHAZARD)
        npc = NPC(NPCs.KILRON_349)
        when {
            (questStage in 1..10) -> {
                when (stage) {
                    0 -> player("Hello Kilron.").also { stage++ }
                    1 -> npc("Hello traveller. Do you need to go back over?").also { stage++ }
                    2 -> options("Not yet Kilron.", "Yes I do.").also { stage++ }
                    3 -> when (buttonID) {
                        1 -> player("Not yet Kilron.").also { stage++ }
                        2 -> player("Yes I do.").also { stage = 6 }
                    }

                    4 -> npc("Okay, just give me the word.").also { stage++ }
                    5 -> end()
                    6 -> {
                        npc("Okay, quickly now!")
                        sendMessage(player!!, "You climb up the rope ladder...")
                        stage++
                    }

                    7 -> {
                        end()
                        sendMessage(player!!, "and drop down on the other side.")
                        teleport(player!!, Location(2559, 3267, 0))
                    }
                }
            }
        }
    }
}