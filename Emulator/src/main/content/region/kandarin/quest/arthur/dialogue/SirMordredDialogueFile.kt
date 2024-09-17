package content.region.kandarin.quest.arthur.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.world.repository.Repository.findNPC
import core.tools.END_DIALOGUE

/**
 * Represents the Sir Mordred dialogue file.
 *
 * Related to [Merlin Crystal][content.region.kandarin.quest.arthur.MerlinCrystal] quest.
 * @author lostmyphat
 */
class SirMordredDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SIR_MORDRED_247)

        when (stage) {
            0 -> npcl(FacialExpression.ANGRY, "You DARE to invade MY stronghold?!?! Have at thee knave!!!").also { stage++ }

            1 -> {
                end()
                val mord = findNPC(NPCs.SIR_MORDRED_247)

                if (mord != null) {
                    mord.attack(player!!)
                }
                stage = END_DIALOGUE
            }
        }

    }
}
