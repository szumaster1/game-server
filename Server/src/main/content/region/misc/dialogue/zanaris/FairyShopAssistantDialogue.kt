package content.region.misc.dialogue.zanaris

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Fairy Shop Assistant dialogue.
 */
@Initializable
class FairyShopAssistantDialogue(player: Player? = null) : Dialogue(player) {


    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_HAPPY, "Can I help you at all?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes please. What are you selling?", "How should I use your shop?", "No thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> end().also { npc.openShop(player) }
                2 -> playerl(FacialExpression.ASKING, "How should I use your shop?").also { stage++ }
                3 -> player(FacialExpression.NEUTRAL, "No thanks.").also { stage = END_DIALOGUE }
            }
            2 -> npcl(FacialExpression.OLD_CALM_TALK1, "I'm glad you ask! You can buy as many of the items stocked as you wish. You can also sell most items to the shop.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FAIRY_SHOP_ASSISTANT_535, NPCs.FAIRY_SHOPKEEPER_534)
    }

}
