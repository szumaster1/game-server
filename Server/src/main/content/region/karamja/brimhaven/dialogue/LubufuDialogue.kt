package content.region.karamja.brimhaven.dialogue

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Lubufu dialogue.
 */
@Initializable
class LubufuDialogue(player: Player? = null): Dialogue(player) {

    /*
     * Lubufu is an old fisherman who doesn't like young whippersnappers.
     * He can be found on the southern coast of Brimhaven.
     * Location: 2766,3168
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Watch where you're going, young whippersnapper!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("I wasn't going anywhere...", "What's a whippersnapper?", "Who are you?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "I wasn't going anywhere...").also { stage = 10 }
                2 -> player(FacialExpression.HALF_GUILTY, "What's a whippersnapper?").also { stage = 20 }
                3 -> player(FacialExpression.HALF_GUILTY, "Who are you?").also { stage = 30 }

            }
            10 -> npc(FacialExpression.HALF_GUILTY, "Well then go away from here!").also { stage = END_DIALOGUE }
            20 -> npc(FacialExpression.HALF_GUILTY, "It's a whip. Which snaps. Like me. Now leave!").also { stage = END_DIALOGUE }
            30 -> npc(FacialExpression.HALF_GUILTY, "I am Lubufu - the only fisherman who knows the secret", "of the Karambwan!").also { stage++ }
            31 -> player(FacialExpression.HALF_GUILTY, "What's a Karambwan?").also { stage++ }
            32 -> npc(FacialExpression.HALF_GUILTY, "What a foolish question! Now leave!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LUBUFU_1171)
    }
}
