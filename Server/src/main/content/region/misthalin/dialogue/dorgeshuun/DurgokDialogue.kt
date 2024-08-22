package content.region.misthalin.dialogue.dorgeshuun

import cfg.consts.Items
import cfg.consts.NPCs
import core.api.addItemOrDrop
import core.api.removeItem
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Durgok dialogue.
 */
@Initializable
class DurgokDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.OLD_NORMAL, "Frogburger! There's nothing like grilled frog in a bun. Do you want one? Only 10gp!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes, please.", "No, thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.FRIENDLY, "Yes please!").also { stage++ }
                2 -> player(FacialExpression.FRIENDLY, "No thanks.").also { stage = END_DIALOGUE }
            }
            2 -> {
                if (!removeItem(player, Item(Items.COINS_995, 10))) {
                    end()
                    npc(FacialExpression.OLD_NORMAL, "I'm sorry, but you need 10gp for that.")
                } else {
                    end()
                    npc(FacialExpression.OLD_NORMAL, "There you go.")
                    addItemOrDrop(player, Items.FROGBURGER_10962, 1)
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DURGOK_5794)
    }

}
