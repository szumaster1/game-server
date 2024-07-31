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
class BettyDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Betty is the owner of Betty's Magic Emporium
     * in Port Sarim.
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc("Welcome to the magic emporium.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> if (hasRequirement(player, "The Hand in the Sand", false)) {
                options("Can I see your wares?", "Sorry, I'm not into magic.", "Talk about The Hand in the Sand.").also { stage++ }
            } else {
                options("Can I see your wares?", "Sorry, I'm not into magic.").also { stage++ }
            }
            1 -> when (buttonId) {
                1 -> {
                    end()
                    openNpcShop(player, NPCs.BETTY_583)
                }
                2 -> player("Sorry, I'm not into magic.").also { stage++ }
                3 -> npc("Zavistic told me what a good job you did. If you want", "some more pink dye, I have made up a batch and you", "can have some for 20 gold.").also { stage = 3  }
            }
            2 -> npc(FacialExpression.HAPPY, "Well, if you see anyone who is into Magic, please send", "them my way.").also { stage = END_DIALOGUE }
            3 -> options("No thanks, Betty.", "Yes, please!").also { stage++ }
            4 -> when (buttonId) {
                1 -> player("No thanks, Betty.").also { stage = END_DIALOGUE }
                2 -> player("Yes, please!").also { stage++ }
            }
            5 -> {
                end()
                if (freeSlots(player) < 1) {
                    sendMessage(player, "You don't have enough inventory space.")
                } else if (!removeItem(player, Item(Items.COINS_995, 20))) {
                    sendDialogue(player, "You don't have enough coins for that.")
                } else {
                    sendDialogue("You hand over 20 gold pieces in return for the dye")
                    addItem(player, Items.PINK_DYE_6955)
                    setAttribute(player, "diary:falador:pink-dye-from-betty", true)
                }
            }
        }
        return true
    }
    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BETTY_583)
    }

}
