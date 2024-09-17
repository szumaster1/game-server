package content.region.morytania.phasmatys.dialogue

import org.rs.consts.NPCs
import content.region.morytania.phasmatys.quest.ahoy.dialogue.NecrovarusDialogueFile
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Necrovarus dialogue.
 */
@Initializable
class NecrovarusDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: ghostly priest and mage, as well as the creator of the Ectofuntus.
     * He arrived in Port Phasmatys from the Eastern Lands.
     * He is the main antagonist in the Ghosts Ahoy quest.
     * Location: 3660,3517
     */

    override fun open(vararg args: Any): Boolean {
        when {
            isQuestComplete(player, "Ghosts Ahoy") -> player("Told you I'd defeat you, Necrovarus. My advice to", "you is to pass over to the next world yourself with", "everybody else.").also { stage = 4 }
            !isQuestComplete(player, "Ghosts Ahoy") -> openDialogue(player, NecrovarusDialogueFile())
            else -> options("What is this place?", "What happened to everyone here?", "How do I get into the town?")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> when (buttonId) {
                1 -> player("What is this place?").also { stage = 1 }
                2 -> player("What happened to everyone here?").also { stage = 2 }
                3 -> player("How do I get into the town?").also { stage = 3 }
            }

            1 -> npc(FacialExpression.ANNOYED, "Speak to me again and I will rend the soul from your", "flesh.").also { stage = END_DIALOGUE }
            2 -> npc(FacialExpression.ANNOYED, "You dare to speak to me??? Have you lost your", "wits????").also { stage = END_DIALOGUE }
            3 -> npc(FacialExpression.ANNOYED, "I do not answer questions, mortal fool!").also { stage = END_DIALOGUE }
            4 -> npc("I should fry you for what you have done...").also { stage++ }
            5 -> player("Quiet, evil priest!! If you try anything I will command you", "again, but this time it will be to throw yourself into the", "Endless Void for the rest of eternity.").also { stage++ }
            6 -> npc(FacialExpression.SCARED, "Please no! I will do whatever you say!!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.NECROVARUS_1684)
    }

}
