package content.region.misthalin.dialogue.varrock

import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class VarrockBartenderDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "Hello.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HALF_ASKING, "Good day to you, brave adventurer. Can I get you a", "refreshing beer?").also { stage++ }
            1 -> options( "Yes please!", "No thanks.", "How much?").also { stage++ }
            2 -> when (buttonId) {
                1 -> player(FacialExpression.HAPPY, "Yes please!").also { stage = 10 }
                2 -> player(FacialExpression.NEUTRAL, "No thanks.").also { stage = 20 }
                3 -> player(FacialExpression.HALF_ASKING, "How much?").also { stage = 30 }
            }
            10 -> npc(FacialExpression.FRIENDLY, "Ok then, that's two gold coins please.").also { stage++ }
            11 -> {
                end()
                if(freeSlots(player) == 0){
                    sendMessage(player, "You don't have enough inventory space.")
                    return true
                }
                if (!removeItem(player, Item(995, 2))) {
                    sendMessage(player, "You need two gold coins to buy a beer.")
                } else {
                    addItem(player, Items.BEER_1917, 1)
                    npc(FacialExpression.HAPPY, "Cheers!")
                }
            }
            12 -> player(FacialExpression.HAPPY, "Cheers!").also { stage = END_DIALOGUE }
            20 -> npc(FacialExpression.FRIENDLY, "Let me know if you change your mind.").also { stage = END_DIALOGUE }
            30 -> npc(FacialExpression.FRIENDLY, "Two gold pieces a pint. So, what do you say?").also { stage++ }
            31 -> options( "Yes please!", "No thanks.").also { stage++ }
            32 -> when (buttonId) {
                1 -> player(FacialExpression.HAPPY, "Yes please!").also { stage = 10 }
                2 -> player(FacialExpression.NEUTRAL, "No thanks.").also { stage = 20 }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BARTENDER_732, NPCs.BARTENDER_731)
    }

}