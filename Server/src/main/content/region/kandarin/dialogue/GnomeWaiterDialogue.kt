package content.region.kandarin.dialogue

import core.api.anyInInventory
import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class GnomeWaiterDialogue(player: Player? = null) : Dialogue(player) {

    val gnomesFood = intArrayOf(2219, 2221, 2223, 2225, 2227, 2229, 2231, 2233, 2235, 2237, 2239, 2241, 2243)

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        playerl(FacialExpression.FRIENDLY, "Hello").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.OLD_NORMAL, "Hello " + (if (player.isMale) "sir" else "madam") + ", can I tempt you with any of the dishes on our new menu?").also { stage++ }
            1 -> if (!anyInInventory(player, *gnomesFood)) {
                options("I'll take a look.", "Not really.").also { stage++ }
            } else {
                options("I'll take a look.", "Not really.", "Actually I'd like to sell some dishes.").also { stage++ }
            }
            2 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "I'll take a look.").also { stage = 4 }
                2 -> playerl(FacialExpression.NEUTRAL, "Not really.").also { stage = 3 }
                3 -> playerl(FacialExpression.FRIENDLY, "Actually I'd like to sell some dishes.").also { stage = 7 }
            }
            3 -> npcl(FacialExpression.OLD_NORMAL, "That's your choice " + (if (player.isMale) "sir" else "madam") + ", enjoy your stay.").also { stage = END_DIALOGUE }
            4 -> npcl(FacialExpression.OLD_NORMAL, "I hope you like what you see. Although all these premade dishes are good to eat - they were made by the last human assistant chef. He wasn't up to Chef's exacting standards - and you may find that they will").also { stage++ }
            5 -> npcl(FacialExpression.OLD_NORMAL, "not be accepted by some people as the 'real' thing.").also { stage++ }
            6 -> {
                end()
                openNpcShop(player, NPCs.GNOME_WAITER_851)
            }
            7 -> npcl(FacialExpression.OLD_NORMAL, " Mr Gianne is the one to talk to if you want to sell any dishes you've made yourself. He is the owner and head chef of this establishment. If on the other hand you are more interested in making food to deliver").also { stage++ }
            8 -> npcl(FacialExpression.OLD_NORMAL, "which can be quite lucrative, you should talk to Mr Giannes son Aluft Gianne jnr. Just don't call him Lufty, he doesn't like it.").also { stage++ }
            9 -> playerl(FacialExpression.FRIENDLY, "Thank you.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GNOME_WAITER_851)
    }
}
