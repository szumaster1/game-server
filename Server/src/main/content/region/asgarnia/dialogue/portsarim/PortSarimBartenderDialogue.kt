package content.region.asgarnia.dialogue.portsarim

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
class PortSarimBartenderDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * The bartender at The Rusty Anchor in Port Sarim
     * is an NPC that serves the purpose of selling beer
     * to the players. He is also involved in the Rocking Out.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.FRIENDLY, "Good day to you!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.HAPPY, "Hello there!").also { stage++ }
            1 -> sendDialogueOptions(player, "Choose an option:", "Could I buy a beer, please.", "Bye, then.").also { stage++ }
            2 -> when (buttonId) {
                1 -> player(FacialExpression.HAPPY, "Could I buy a beer, please?").also { stage++ }
                2 -> {
                    player(FacialExpression.FRIENDLY, "Bye, then.").also { stage = 6 }
                    stage = 20
                }
            }
            3 -> npc(FacialExpression.FRIENDLY, "Sure, that will be two gold coins, please.").also { stage++ }
            4 -> player(FacialExpression.FRIENDLY, "Okay, here you go.").also { stage++ }
            5 -> {
                end()
                if (!removeItem(player, Item(Items.COINS_995, 2))) {
                    sendMessage(player, "You need 2 gold coins to buy beer.")
                } else {
                    sendDialogue(player, "You buy a pint of beer.")
                    addItemOrDrop(player, Items.BEER_1917, 1)
                }
            }
            6 -> npc(FacialExpression.FRIENDLY, "Come back soon!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BARTENDER_734)
    }

}
