package content.region.kandarin.quest.holygrail.dialogue

import cfg.consts.NPCs
import core.api.openDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC

/**
 * Represents the High priest entrana holy grail dialogue file.
 */
class HighPriestEntranaHolyGrailDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.HIGH_PRIEST_216)

        when (stage) {
            0 -> npcl(FacialExpression.NEUTRAL, "Many greetings. Welcome to our fair island.").also { stage++ }
            1 -> playerl(FacialExpression.NEUTRAL, "Hello, I am in search of the Holy Grail.").also { stage++ }
            2 -> npcl(FacialExpression.NEUTRAL, "The object of which you speak did once pass through holy Entrana. I know not where it is now however.").also { stage++ }
            3 -> npcl(FacialExpression.NEUTRAL, "Nor do I really care.").also { stage++ }
            4 -> {
                openDialogue(player!!, CroneHolyGrailDialogueFile(true), NPCs.CRONE_217)
            }

        }
    }

}