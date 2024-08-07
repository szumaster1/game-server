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
 * Fisherman2dialogue.
 */
@Initializable
class Fisherman2Dialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player("Hello there.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendDialogue(player!!, "His eyes are staring vacantly into space.").also { stage++ }
            1 -> npcl(FacialExpression.DRUNK, "Must find family...").also { stage++ }
            2 -> player("What?").also { stage++ }
            3 -> npcl(FacialExpression.DRUNK, "Soon we will all be together...").also { stage++ }
            4 -> player(FacialExpression.WORRIED, "Are you ok?").also { stage++ }
            5 -> npcl(FacialExpression.DRUNK, "Must find family... They are all under the blue... Deep deep under the blue...").also { stage++ }
            6 -> playerl(FacialExpression.HALF_ROLLING_EYES, "Ermm... I'll leave you to it then.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FISHERMAN_703)
    }

}