package content.region.asgarnia.burthope.dialogue

import org.rs.consts.NPCs
import content.region.asgarnia.burthope.quest.death.dialogue.EohricDialogueFile
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Eohric dialogue.
 */
@Initializable
class EohricDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Death Plateau")) {
            openDialogue(player!!, EohricDialogueFile(), npc)
        } else {
            player(FacialExpression.FRIENDLY, "Hi!")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.ASKING, "Hello. Can I help?").also { stage++ }
            1 -> options("What is this place?", "That's quite an outfit.", "Goodbye.").also { stage++ }
            2 -> when (buttonId) {
                1 -> playerl(FacialExpression.ASKING, "What is this place?").also { stage = 4 }
                2 -> playerl(FacialExpression.ASKING, "That's quite an outfit.").also { stage++ }
                3 -> player(FacialExpression.FRIENDLY, "Goodbye.").also { stage = END_DIALOGUE }
            }
            3 -> npcl(FacialExpression.HAPPY, "Why, thank you. I designed it myself. I've always found purple such a cheerful colour!").also { stage = 2 }
            4 -> npcl(FacialExpression.FRIENDLY, "This is Burthorpe Castle, home to His Royal Highness Prince Anlaf, heir to the throne of Asgarnia.").also { stage = END_DIALOGUE }
            5 -> npc(FacialExpression.FRIENDLY, "No doubt you're impressed.").also { stage++ }
            6 -> options("Where is the prince?", "Goodbye.").also { stage++ }
            7 -> when (buttonId) {
                1 -> npcl(FacialExpression.SUSPICIOUS, "I cannot disclose the prince's exact whereabouts for fear of compromising his personal safety.").also { stage++ }
                2 -> player(FacialExpression.FRIENDLY, "Goodbye.").also { stage = END_DIALOGUE }
            }
            8 -> npcl(FacialExpression.FRIENDLY, "But rest assured that he is working tirelessly to maintain the safety and well being of Burthorpe's people.").also { stage = 1 }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.EOHRIC_1080)
    }
}
