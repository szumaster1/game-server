package content.location.burthope

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Anton dialogue.
 */
@Initializable
class AntonDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.ASKING, "Ahhh, hello there. How can I help?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.NEUTRAL, "Looks like you have a good selection of weapons around here...").also { stage++ }
            1 -> npcl(FacialExpression.FRIENDLY, "Indeed so, specially imported from the finest smiths around the lands, take a look at my wares.").also { stage++ }
            2 -> npcl(FacialExpression.FRIENDLY, "Indeed so, specially imported from the finest smiths around the lands, take a look at my wares.").also { stage++ }
            3 -> {
                end()
                openNpcShop(player, NPCs.ANTON_4295)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ANTON_4295)
    }

}