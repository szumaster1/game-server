package content.region.misthalin.varrock.dialogue

import org.rs.consts.NPCs
import core.api.openNpcShop
import core.api.sendChat
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.RegionManager.getLocalNpcs
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Tea seller dialogue.
 */
@Initializable
class TeaSellerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (player.getSavedData().globalData.getTeaSteal() > System.currentTimeMillis()) {
            Pulser.submit(object : Pulse(1) {
                var count: Int = 0
                override fun pulse(): Boolean {
                    if (count == 0) sendChat(npc, "You're the one who stole something from me!")
                    if (count == 2) {
                        sendChat(npc,"Guards guards!")
                        return true
                    }
                    count++
                    return false
                }
            })
            return false
        }
        npc(FacialExpression.HALF_GUILTY, "Greetings! Are you in need of refreshment?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes, please.", "No, thanks.", "What are you selling?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Yes, please.").also { stage = 2 }
                2 -> player(FacialExpression.HALF_GUILTY, "No, thanks.").also { stage = 3 }
                3 -> player(FacialExpression.HALF_GUILTY, "What are you selling?").also { stage = 4 }
            }
            2 -> {
                end()
                openNpcShop(player, NPCs.TEA_SELLER_595)
            }
            3 -> npc(FacialExpression.HALF_GUILTY, "Well, if you're sure. You know where to come if you do.").also { stage = END_DIALOGUE }
            4 -> npc(FacialExpression.HALF_GUILTY, "Only the most delicious infusion of the leaves of the tea", "plant. Grown in the exotic regions of this world. Buy", "yourself a cup.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TEA_SELLER_595)
    }

}
