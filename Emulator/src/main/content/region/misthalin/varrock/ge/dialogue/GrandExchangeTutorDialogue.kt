package content.region.misthalin.varrock.ge.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.GameWorld
import core.plugin.Initializable
import core.tools.DARK_RED
import core.tools.END_DIALOGUE

/**
 * Represents the Grand exchange tutor dialogue.
 */
@Initializable
class GrandExchangeTutorDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: Member of the Grand Exchange, located in north-west Varrock.
     * He teaches players the basics of the Grand Exchange and allows them to use it.
     * Location: 3161,3475
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "How can I help?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Can you teach me about the Grand Exchange?", "Where can I found out more info?", "I'm okay thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "Can you teach me about the Grand Exchange?").also { stage = 2 }
                2 -> player(FacialExpression.HALF_GUILTY, "Where can I find more info?").also { stage = 12 }
                3 -> player(FacialExpression.HALF_GUILTY, "I'm okay thanks.").also { stage = 13 }
            }
            2 -> npc(FacialExpression.HALF_GUILTY, "Of course.").also { stage++ }
            3 -> npc(FacialExpression.HALF_GUILTY, "This is the Grand Exchange. You can tell us", "what you want to buy or sell, and for how much", "and we'll search for another player willing to do the trade!").also { stage++ }
            4 -> npc(FacialExpression.HALF_GUILTY, "Let me describe the process in four steps:").also { stage++ }
            5 -> npc(FacialExpression.HALF_GUILTY, DARK_RED + "Step 1</col>: You come here with items to sell or money to", "spend.").also { stage++ }
            6 -> npc(FacialExpression.HALF_GUILTY, DARK_RED + "Step 2</col>: Speak with one of the clerks at the desk.", "They will help you set up your bid.").also { stage++ }
            7 -> npc(FacialExpression.HALF_GUILTY, "When you're setting up a bid we'll show you a", "guide price for each item. This is just a suggestion", "though: you can offer any amount you like.").also { stage++ }
            8 -> npc(FacialExpression.HALF_GUILTY, DARK_RED + "Step 3</col>: The clerks will take the items or money off you", "and look for someone to complete the trade. This may be", "very fast, or it could take several days.").also { stage++ }
            9 -> npc(FacialExpression.HALF_GUILTY, DARK_RED + "Step 4</col>: When the trade is complete, we will send you a", "message. You can collect your stuff by talking to the", "clerks or by visiting any banker in " + GameWorld.settings!!.name + ".").also { stage++ }
            10 -> npc(FacialExpression.HALF_GUILTY, "There's plenty more information about the Grand", "Exchange, all of which can be found out from Brugsen", "Bursen, the guy with the megaphone. I would suggest", "you speak with him to fully get to grips with the Grand").also { stage++ }
            11 -> npc(FacialExpression.HALF_GUILTY, "Exchange. Good luck!").also { stage = END_DIALOGUE }
            12 -> npc(FacialExpression.HALF_GUILTY, "Go and speak to Brugsen who's standing over", "there, closer to the building. He'll help you out.").also { stage = END_DIALOGUE }
            13 -> npc(FacialExpression.HALF_GUILTY, "Fair enough.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GRAND_EXCHANGE_TUTOR_6521)
    }

}
