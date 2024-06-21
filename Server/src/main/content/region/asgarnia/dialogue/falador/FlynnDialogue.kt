package content.region.asgarnia.dialogue.falador

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class FlynnDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "Hello. Do you want to buy or sell any maces?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("No, thanks.", "Well, I'll have a look, at least.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.NEUTRAL, "No, thanks.").also { stage = END_DIALOGUE }
                2 -> {
                    end()
                    openNpcShop(player, NPCs.FLYNN_580)
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FLYNN_580)
    }

}
