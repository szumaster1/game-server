package content.region.misthalin.dialogue.dorgeshuun

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Gourmet dialogue.
 */
@Initializable
class GourmetDialogue(player: Player? = null) : Dialogue(player) {

    // Need: https://runescape.wiki/w/Dorgesh-Kaan_market_trading

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.OLD_NORMAL, "Ooh, a surface-dweller! Have you got any exotic surface foods to sell?").also { stage++ }
            1 -> options("Not at the moment.", "Yes!", "What kind of foods do you like?").also { stage++ }
            2 -> when (buttonId) {
                1 -> playerl(FacialExpression.NEUTRAL, "Not at the moment.").also { stage++ }
                2 -> playerl(FacialExpression.HAPPY, "Yes!").also { stage = 4 }
                3 -> playerl(FacialExpression.ASKING, "What kind of foods do you like?").also { stage = 5 }
            }
            3 -> npcl(FacialExpression.OLD_NORMAL, "A pity. Dorgeshuun food seems so dull now I've tasted surface food.").also { stage = END_DIALOGUE }
            4 -> npcl(FacialExpression.OLD_NORMAL, "Splendid! Show me what you've got!").also { stage = END_DIALOGUE }
            5 -> npcl(FacialExpression.OLD_NORMAL, "All kinds! I don't want to keep eating the same food, even if I like it. I want to try new tastes!").also { stage++ }
            6 -> npcl(FacialExpression.OLD_NORMAL, "Why not try something on me and see if I like it?").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GOURMET_5788, NPCs.GOURMET_5789, NPCs.GOURMET_5790, NPCs.GOURMET_5791)
    }
}
