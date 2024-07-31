package content.region.misthalin.dialogue.varrock

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.removeItem
import core.api.sendDialogueOptions
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class TrampDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: Tramps are poor, penniless humans who inhabit most of the major cities of Gielinor, such as Yanille,
     * Seers' Village and in particular Varrock, which is a very impoverished city.
     * Location: 3235,3399
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Got any spare change, mate?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendDialogueOptions(player, "What would you like to say?", "Yes, I can spare a little money.", "Sorry, you'll have to earn it yourself.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Yes, I can spare a little money.").also { stage = 2 }
                2 -> player(FacialExpression.FURIOUS, "Sorry, you'll have to earn it yourself, just like I did.").also { stage = 3 }
            }
            2 -> if (removeItem(player, Item(Items.COINS_995, 1))) {
                end()
                npc(FacialExpression.HALF_GUILTY, "Thanks mate!")
            } else {
                end()
                sendMessage(player, "You only need one coin to give to this tramp.")
            }
            3 -> npc(FacialExpression.HALF_GUILTY, "Please yourself.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TRAMP_11)
    }

}
