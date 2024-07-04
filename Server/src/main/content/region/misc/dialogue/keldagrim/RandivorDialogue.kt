package content.region.misc.dialogue.keldagrim

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class RandivorDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_NORMAL, "Welcome to my stall! Come and buy some", "tasty bread, freshly baked just 2 months ago!").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Sounds delicious!", "I think I'll pass.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HAPPY, "Sounds delicious!").also { stage++ }
                2 -> player(FacialExpression.NEUTRAL, "I think I'll pass.").also { stage = END_DIALOGUE }
            }
            2 -> {
                end()
                openNpcShop(player, NPCs.RANDIVOR_2156)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.RANDIVOR_2156)
    }
}
