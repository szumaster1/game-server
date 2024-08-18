package content.region.karamja.dialogue

import core.api.consts.NPCs
import core.api.getAttribute
import core.api.removeAttribute
import core.api.sendMessage
import core.api.setAttribute
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.GroundItem
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Luthas dialogue.
 */
@Initializable
class LuthasDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Owns a banana plantation on Karamja.
     * He is an NPC who plays a small role in the Pirate's Treasure quest.
     * He can be found in his living quarters next to the plantation.
     * Will pay 30 coins to players that fill a crate with 10 bananas.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (player.getSavedData().globalData.isLuthasTask()) {
            val current = player.getSavedData().globalData.getKaramjaBananas();
            if (current >= 10) {
                player(FacialExpression.FRIENDLY, "I've filled a crate with bananas.")
                stage = 905
                return true
            }
            npc(FacialExpression.HALF_ASKING, "Have you completed your task yet?")
            stage = 900
            return true
        }
        npc(FacialExpression.HAPPY, "Hello I'm Luthas, I run the banana plantation here.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Could you offer me employment on your plantation?", "That customs officer is annoying isn't he?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.ASKING, "Could you offer me employment on your plantation?").also { stage = 10 }
                2 -> player(FacialExpression.SUSPICIOUS, "That customs officer is annoying isn't she?").also { stage = 20 }
            }
            10 -> npc(FacialExpression.HAPPY, "Yes, I can sort something out. There's a crate ready to", "be loaded onto the ship.").also { stage++ }
            11 -> npc(FacialExpression.NEUTRAL, "You wouldn't believe the demand for bananas from", "Wydin's shop over in Port Sarim. I think this is the", "third crate I've shipped him this month..").also { stage++ }
            12 -> npc(FacialExpression.HAPPY, "If you could go fill it up with bananas, I'll pay you 30", "gold.").also { stage++ }
            13 -> {
                end()
                player.getSavedData().globalData.setLuthasTask(true)
            }
            20 -> npc(FacialExpression.NEUTRAL, "Well I know her pretty well. She doesn't cause me any", "trouble any more").also { stage++ }
            21 -> npc(FacialExpression.NEUTRAL, "She doesn't even search my export crates anymore.", "She knows they only contain bananas.").also { stage++ }
            22 -> player(FacialExpression.SUSPICIOUS, "Really? How interesting. Whereabouts do you send", "those to?").also { stage++ }
            23 -> npc(FacialExpression.NEUTRAL, "There is a little shop over in Port Sarim that buys", "them up by the crate. I believe it is run by a man", "called Wydin.").also { stage = END_DIALOGUE }
            900 -> {
                val amt = player.getSavedData().globalData.getKaramjaBananas();
                if (amt < 30) {
                    player(FacialExpression.HALF_GUILTY, "No, the crate isn't full yet.").also { stage++ }
                } else {
                    end()
                }
            }
            901 -> npc(FacialExpression.NEUTRAL, "Well come back when it is.").also { stage = 21 }
            905 -> npc(FacialExpression.HAPPY, "Well done, here's your payment.").also { stage++ }
            906 -> {
                end()
                sendMessage(player, "Luthas hands you 30 coins.")
                player.getSavedData().globalData.setKaramjaBannanas(0)
                player.getSavedData().globalData.setLuthasTask(false)
                if (getAttribute(player, "stashed-rum", false)) {
                    removeAttribute(player, "stashed-rum")
                    setAttribute(player, "/save:wydin-rum", true)
                }
                if (!player.inventory.add(Item(995, 30))) {
                    GroundItemManager.create(GroundItem(Item(995, 30), player.location, player))
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LUTHAS_379)
    }

}
