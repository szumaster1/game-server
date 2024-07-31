package content.region.desert.dialogue.alkharid

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class ZekeDialogue(player: Player? = null) : Dialogue(player) {

    /*
        Zeke is the owner of Zeke's Superior Scimitars in
        Al Kharid which sells scimitars up to Adamant.
        Location: 3288,3190
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HAPPY, "A thousand greetings, sir.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Do you want to trade?", "Nice cloak.", "Could you sell me a dragon scimitar?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HAPPY, "Do you want to trade?").also { stage = 10 }
                2 -> player(FacialExpression.HAPPY, "Nice cloak.").also { stage = 20 }
                3 -> player(FacialExpression.HAPPY, "Could you sell me a dragon scimitar?").also { stage = 30 }

            }
            10 -> npc(FacialExpression.HAPPY, "Yes, certainly. I deal in scimitars.").also { stage++ }
            11 -> {
                end()
                openNpcShop(player, NPCs.ZEKE_541)
            }
            20 -> npc(FacialExpression.HAPPY, "Thank you.").also { stage = END_DIALOGUE }
            30 -> npc(FacialExpression.EXTREMELY_SHOCKED, "A dragon scimitar? A DRAGON scimitar?").also { stage++ }
            31 -> npc(FacialExpression.EXTREMELY_SHOCKED, "No way, man!").also { stage++ }
            32 -> npc(FacialExpression.ANGRY, "The banana-brained nitwits who make them would never", "dream of selling any to me.").also { stage++ }
            33 -> npc(FacialExpression.FRIENDLY, "Seriously, you'll be a monkey's uncle before you'll ever", "hold a dragon scimitar.").also { stage++ }
            34 -> player(FacialExpression.SUSPICIOUS, "Hmmm, funny you should say that...").also { stage++ }
            35 -> npc(FacialExpression.ASKING, "Perhaps you'd like to take a look at my stock?").also { stage++ }
            36 -> options("Yes, please, Zeke.", "Not today, thank you.").also { stage++ }
            37 -> when (buttonId) {
                1 -> {
                    end()
                    openNpcShop(player, NPCs.ZEKE_541)
                }
                2 -> player(FacialExpression.HALF_GUILTY, "No today, thank you.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ZEKE_541)
    }
}
