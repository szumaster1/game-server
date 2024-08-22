package content.region.asgarnia.dialogue.falador

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Drunken man dialogue.
 */
@Initializable
class DrunkenManDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.FRIENDLY, "Hello.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.DRUNK, "... whassup?").also { stage++ }
            1 -> player(FacialExpression.ASKING, "Are you alright?").also { stage++ }
            2 -> npc(FacialExpression.DRUNK, "... see... two of you... why there two of you?").also { stage++ }
            3 -> player(FacialExpression.FRIENDLY, "There's only one of me, friend.").also { stage++ }
            4 -> npc(FacialExpression.DRUNK, "... no, two of you... you can't count...", "... maybe you drunk too much...").also { stage++ }
            5 -> player(FacialExpression.FRIENDLY, "Whatever you say, friend.").also { stage++ }
            6 -> npc(FacialExpression.DRUNK, "... giant hairy cabbages...").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DRUNKEN_MAN_3222)
    }
}