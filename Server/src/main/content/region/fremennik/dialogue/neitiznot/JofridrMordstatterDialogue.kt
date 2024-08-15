package content.region.fremennik.dialogue.neitiznot

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Jofridr Mordstatter dialogue.
 */
@Initializable
class JofridrMordstatterDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.NEUTRAL, "Hello there. Would you like to see the goods I have for sale?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes please, Jofridr.", "No thank you, Jofridr.", "Why do you have so much wool in your store?").also { stage++ }
            1 -> when (buttonId) {
                1 -> playerl(FacialExpression.NEUTRAL, "Yes please, Jofridr.").also { stage = 6 }
                2 -> playerl(FacialExpression.NEUTRAL, "No thank you, Jofridr.").also { stage = 5 }
                3 -> playerl(FacialExpression.THINKING, "Why do you have so much wool in your store? I haven't seen any sheep anywhere.").also { stage = 11 }
            }
            5 -> npcl(FacialExpression.NEUTRAL, "Fair thee well.").also { stage = END_DIALOGUE }
            6 -> {
                end()
                openNpcShop(player, NPCs.JOFRIDR_MORDSTATTER_5509)
            }
            11 -> npcl(FacialExpression.FRIENDLY, "Ah, I have contacts on the mainland. I have a sailor friend who brings me crates of wool on a regular basis.").also { stage++ }
            12 -> playerl(FacialExpression.ASKING, "What do you trade for it?").also { stage++ }
            13 -> npcl(FacialExpression.FRIENDLY, "Rope of course! What else can we sell? Fish would go off before it got so far south.").also { stage++ }
            14 -> playerl(FacialExpression.ASKING, "Where does all this rope go?").also { stage++ }
            15 -> npcl(FacialExpression.THINKING, "Err, I don't remember the name of the place very well. Dreinna? Drennor? Something like that.").also { stage++ }
            16 -> playerl(FacialExpression.NEUTRAL, "That's very interesting. Thanks Jofridr.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JOFRIDR_MORDSTATTER_5509)
    }

}
