package content.region.misc.dialogue.keldagrim

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Tombar dialogue.
 */
@Initializable
class TombarDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_ASKING, "Say, aren't you a bit tall for a dwarf?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.OLD_NORMAL, "Was there anything in particular you wanted?").also { stage++ }
            1 -> options("I'd like a quest please.", "No, I just like talking to strangers.").also { stage++ }
            2 -> when (buttonId) {
                1 -> player("I'd like a quest please.").also { stage++ }
                2 -> player("No, I just like talking to strangers.").also { stage = 4 }
            }
            3 -> npc(FacialExpression.OLD_NORMAL, "I have nothing to do for you, I'm afraid.", "Ask around town, though, there are always people", "who need some work done around here.").also { stage = END_DIALOGUE }
            4 -> npc(FacialExpression.OLD_NORMAL, "Well I don't.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TOMBAR_2199)
    }
}
