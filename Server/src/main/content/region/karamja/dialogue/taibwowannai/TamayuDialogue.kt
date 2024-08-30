package content.region.karamja.dialogue.taibwowannai

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Tamayu dialogue.
 */
@Initializable
class TamayuDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * One of the brothers in Tai Bwo Wannai Trio.
     * During the quest he can be found south-east
     * of Tai Bwo Wannai Trio near the mining spot.
     */
    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if(npc.id == NPCs.TAMAYU_1167){
            npc(FacialExpression.ANNOYED, randomConversation.random()).also { stage = END_DIALOGUE }
        } else {
            player("Hello, Tamayu, first son of Timfraku.")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_ASKING, "Stranger, why you have returned?").also { stage++ }
            1 -> player("I'm just passing through.").also { stage++ }
            2 -> npcl(FacialExpression.HALF_GUILTY, "Using your new karambwan poison I have been creating spears. Would you like to buy some?").also { stage++ }
            3 -> options("Yes.", "No.").also { stage++ }
            4 -> when (buttonId) {
                1 -> end().also { openNpcShop(player, NPCs.TAMAYU_1167) }
                2 -> npc(FacialExpression.FRIENDLY, "As you wish, stranger.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(2487, NPCs.TAMAYU_1167, NPCs.TAMAYU_1168, NPCs.TAMAYU_1169, NPCs.TAMAYU_1170)
    }

    companion object {
        val randomConversation = arrayOf("Get out of my way! Argh! I'm hunting!", "Move, lest I slay not the beast but you!", "Die! Die!!")
    }
}
