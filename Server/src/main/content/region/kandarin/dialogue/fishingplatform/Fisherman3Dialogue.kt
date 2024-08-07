package content.region.kandarin.dialogue.fishingplatform

import core.api.consts.NPCs
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Fisherman3dialogue.
 */
@Initializable
class Fisherman3Dialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player("Hello there.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendDialogue(player!!, "His eyes are staring vacantly into space.").also { stage++ }
            1 -> npcl(FacialExpression.DRUNK, "Free of the deep blue we are... We must find...").also { stage++ }
            2 -> player("Yes?").also { stage++ }
            3 -> npcl(FacialExpression.DRUNK, "a new home... We must leave this place...").also { stage++ }
            4 -> player(FacialExpression.ASKING, "Where will you go?").also { stage++ }
            5 -> npcl(FacialExpression.DRUNK, "Away... Away to her...").also { stage++ }
            6 -> playerl(FacialExpression.AFRAID, "Riiiight.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FISHERMAN_704)
    }

}