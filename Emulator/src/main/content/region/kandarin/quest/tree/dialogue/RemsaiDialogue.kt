package content.region.kandarin.quest.tree.dialogue

import org.rs.consts.Items
import org.rs.consts.NPCs
import core.api.getQuestStage
import core.api.inInventory
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Remsai dialogue.
 */
@Initializable
class RemsaiDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (inInventory(player!!, Items.ORBS_OF_PROTECTION_588) || getQuestStage(
                player,
                "Tree Gnome Village"
            ) > 40
        ) {
            playerl(FacialExpression.FRIENDLY, "I've returned.").also { stage++ }
        } else if (getQuestStage(player, "Tree Gnome Village") == 40) {
            playerl(FacialExpression.ASKING, "Are you ok?").also { stage++ }
        } else {
            playerl(FacialExpression.FRIENDLY, "Hello Remsai.").also { stage++ }
        }
        return true
    }

    override fun handle(componentID: Int, buttonID: Int): Boolean {
        val questStage = getQuestStage(player!!, "Tree Gnome Village")
        when {
            inInventory(player!!, Items.ORBS_OF_PROTECTION_588) -> {
                when (stage) {
                    1 -> npcl(FacialExpression.OLD_DEFAULT, "You're back, well done brave adventurer. Now the orbs are safe we can perform the ritual for the spirit tree. We can live in peace once again.").also { stage = END_DIALOGUE }
                }
            }

            inInventory(player!!, Items.ORB_OF_PROTECTION_587) -> {
                when (stage) {
                    1 -> npcl(FacialExpression.OLD_DEFAULT, "Hello, did you find the orb?").also { stage++ }
                    2 -> playerl(FacialExpression.FRIENDLY, "I have it here.").also { stage++ }
                    3 -> npcl(FacialExpression.OLD_DEFAULT, "You're our saviour.").also { stage = END_DIALOGUE }
                }
            }

            questStage < 40 -> {
                when (stage) {
                    1 -> npcl(FacialExpression.OLD_DEFAULT, "Hello, did you find the orb?").also { stage++ }
                    2 -> playerl(FacialExpression.FRIENDLY, "No, I'm afraid not.").also { stage++ }
                    3 -> npcl(FacialExpression.OLD_DEFAULT, "Please, we must have the orb if we are to survive.").also { stage = END_DIALOGUE }
                }
            }

            questStage == 40 -> {
                when (stage) {
                    1 -> npcl(FacialExpression.OLD_DEFAULT, "Khazard's men came. Without the orb we were defenceless. They killed many and then took our last hope, the other orbs. Now surely we're all doomed. Without them the spirit tree is useless.").also { stage = END_DIALOGUE }
                }
            }

            questStage > 40 -> {
                when (stage) {
                    1 -> npcl(FacialExpression.OLD_DEFAULT, "You're back, well done brave adventurer. Now the orbs are safe we can perform the ritual for the spirit tree. We can live in peace once again.").also { stage = END_DIALOGUE }
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.REMSAI_472)
    }
}
