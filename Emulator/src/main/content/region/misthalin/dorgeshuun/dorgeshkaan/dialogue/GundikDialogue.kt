package content.region.misthalin.dorgeshuun.dorgeshkaan.dialogue

import core.api.*
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE
import cfg.consts.Items
import cfg.consts.NPCs

/**
 * Represents the Gundik dialogue.
 */
@Initializable
class GundikDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.OLD_NORMAL, "You want some Bat shish? Just 10gp.").also { stage++ }
            1 -> options("Yes please.", "No thanks.").also { stage++ }
            2 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Yes please.").also { stage++ }
                2 -> playerl(FacialExpression.NEUTRAL, "No thanks.").also { stage = 4 }
            }
            3 -> {
                end()
                if(freeSlots(player) == 0){
                    npcl(FacialExpression.OLD_NORMAL, "Looks like your hands are full. You'll have to free up some inventory space before I sell you anything.")
                } else if(amountInInventory(player, Items.COINS_995) < 10){
                    player("But I don't have enough money on me.")
                } else {
                    npcl(FacialExpression.OLD_NORMAL, "There you go.")
                    removeItem(player, Item(Items.COINS_995, 10), Container.INVENTORY)
                    addItem(player, Items.BAT_SHISH_10964)
                }
            }
            4 -> npcl(FacialExpression.OLD_NORMAL, "Have a good day!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GUNDIK_5796)
    }
}
