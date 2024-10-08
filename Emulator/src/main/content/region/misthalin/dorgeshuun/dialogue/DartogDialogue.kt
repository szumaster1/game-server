package content.region.misthalin.dorgeshuun.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Dartog dialogue.
 */
@Initializable
class DartogDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_NORMAL, "Hello, surface-dweller.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Who are you?", "Can you show me the way to the mine?", "Can you show me the way to Lumbridge Castle cellar?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.ASKING, "Who are you?").also { stage++ }
                2 -> npcl(FacialExpression.OLD_NORMAL, "Of course! You're always welcome in our mines!").also { stage = END_DIALOGUE }
                3 -> player(FacialExpression.ASKING, "Can you show me the way to Lumbridge Castle cellar?").also { stage = 6 }
            }
            2 -> npcl(FacialExpression.OLD_NORMAL, "The council posted me here to guard this new tunnel. I can also give you directions through the tunnels. A hero like you is always welcome in our mines!").also { stage++ }
            3 -> options("Can you show me the way to the mine?", "Can you show me the way to Lumbridge Castle cellar?", "Maybe some other time").also { stage++ }

            4 -> when (buttonId) {
                1 -> player(FacialExpression.ASKING, "Can you show me the way to the mine?").also { stage = 5 }
                2 -> player(FacialExpression.ASKING, "Can you show me the way to Lumbridge Castle cellar?").also { stage = 6 }

                3 -> player(FacialExpression.FRIENDLY, "Maybe some other time.").also { stage = END_DIALOGUE }
            }
            /*
             * Move player to mine.
             */
            5 -> npcl(FacialExpression.OLD_NORMAL, "Of course! You're always welcome in our mines!").also { stage = END_DIALOGUE }
            /*
             * Move to lumby castle celler.
             */
            6 -> npc(FacialExpression.OLD_NORMAL, "Of course!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DARTOG_4314)
    }

}
