package content.location.fishingplatform

import cfg.consts.NPCs
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Fisherman1 dialogue.
 */
@Initializable
class Fisherman1Dialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player("Hello there.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendDialogue(player!!, "His eyes are staring vacantly into space.").also { stage++ }
            1 -> npcl(FacialExpression.DRUNK, "Lost to us... She is Lost to us...").also { stage++ }
            2 -> player("Who is lost?").also { stage++ }
            3 -> npcl(FacialExpression.DRUNK, "Trapped by the light... Lost and trapped...").also { stage++ }
            4 -> player(FacialExpression.THINKING, "Ermm... So you don't want to tell me then?").also { stage++ }
            5 -> npcl(FacialExpression.DRUNK, "Trapped... In stone and darkness...").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FISHERMAN_702)
    }

}