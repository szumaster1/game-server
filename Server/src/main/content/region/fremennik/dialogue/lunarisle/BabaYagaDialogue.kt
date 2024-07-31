package content.region.fremennik.dialogue.lunarisle

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class BabaYagaDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.FRIENDLY, "Hello there.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.ASKING, "Ah, a stranger to our island. How can I help?").also { stage++ }
            1 -> options("Have you got anything to trade?", "It's a very interesting house you have here.", "I'm good thanks, bye.").also { stage++ }
            2 -> when (buttonId) {
                1 -> {
                    end()
                    openNpcShop(player, NPCs.BABA_YAGA_4513)
                }
                2 -> playerl(FacialExpression.ASKING, "It's a very interesting house you have here. Does he have a name?").also { stage = 10 }
                3 -> end()
            }
            10 -> npc(FacialExpression.FRIENDLY, "Why of course. It's Berty.").also { stage++ }
            11 -> player(FacialExpression.THINKING, "Berty? Berty the Chicken leg house?").also { stage++ }
            12 -> npc(FacialExpression.LAUGH, "Yes.").also { stage++ }
            13 -> player(FacialExpression.ASKING, "May I ask why?").also { stage++ }
            14 -> npcl(FacialExpression.LAUGH, "It just has that certain ring to it, don't you think? Beeerteeee!").also { stage++ }
            15 -> player(FacialExpression.HALF_WORRIED, "You're ins...").also { stage++ }
            16 -> npc("Insane? Very.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BABA_YAGA_4513)
    }
}
