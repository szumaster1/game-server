package content.region.misc.dialogue.keldagrim

import core.api.addItemOrDrop
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.inInventory
import core.api.removeItem
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class GaussDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_DEFAULT, "I say, I say, a toast to this fine human!").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player("What? I mean, err... why?").also { stage++ }
            1 -> npc(FacialExpression.OLD_DEFAULT, "Why not!").also { stage++ }
            2 -> if (inInventory(player, Items.DWARVEN_STOUT_1913) || inInventory(player, Items.BEER_1917)) {
                player(FacialExpression.HAPPY, "Why not indeed! Cheers!")
                stage = 4
            } else if (inInventory(player, Items.JUG_OF_WINE_1993)) {
                player(FacialExpression.FRIENDLY, "Well, I have this jug of wine...")
                stage = 7
            } else {
                player(FacialExpression.NEUTRAL, "I don't have anything suitable to toast with.").also { stage++ }
            }
            3 -> npc(FacialExpression.OLD_DEFAULT, "Then go get something from the bar!").also { stage = END_DIALOGUE }
            4 -> if (removeItem(player, Items.DWARVEN_STOUT_1913) || removeItem(player, Items.BEER_1917)) {
                end()
                npc(FacialExpression.OLD_DEFAULT, "Cheers!")
                addItemOrDrop(player, Items.BEER_GLASS_1919)
            }
            7 -> npc(FacialExpression.OLD_DEFAULT, "No no, get a pint of beer, jugs of wine aren't", "good to toast with Wine stains are terribly hard", "to wash out of a beard.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GAUSS_2196)
    }
}
