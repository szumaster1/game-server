package content.region.misthalin.dialogue.varrock.grandexchange

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.ge.GEGuidePrice
import core.game.ge.GEGuidePrice.GuideType
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class HofutHandDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: One of the current "Market Price Guides", Member of the Grand Exchange and
     * the only dwarf involved in the business.
     * Location: 3154,3498
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "Hello!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.CHILD_NORMAL, "What? Oh, hello. I was deep in thought. Did", "you say something?").also { stage++ }
            1 -> options("Do you know about the prices for armour and weapons?", "I didn't say anything at all.").also { stage++ }
            2 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Do you know about the prices for armour and weapons?").also { stage = 3 }
                2 -> player(FacialExpression.HALF_GUILTY, "I didn't say anything at all.").also { stage = END_DIALOGUE }
            }
            3 -> npc(FacialExpression.CHILD_NORMAL, "I thought you at least said. 'Hello' I must be", "going mad. Do you think I'm going mad?").also { stage++ }
            4 -> player(FacialExpression.HALF_GUILTY, "Oh, most definitely. You should see a doctor before it's", "too late.").also { stage++ }
            5 -> {
                end()
                GEGuidePrice.open(player, GuideType.WEAPONS_AND_ARMOUR)
            }
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return HofutHandDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HOFUTHAND_ARMOUR_AND_WEAPONS_6527)
    }

}
