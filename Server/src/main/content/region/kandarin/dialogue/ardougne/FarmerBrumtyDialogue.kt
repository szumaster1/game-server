package content.region.kandarin.dialogue.ardougne

import core.api.consts.NPCs
import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class FarmerBrumtyDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Farmer Brumty is a sheep keeper in East Ardougne
     * who is involved in the Sheep Herder quest.
     * Location: 2594,3357
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Sheep Herder")) {
            player("Hello there.")
        } else {
            player(FacialExpression.HALF_GUILTY, "Hello there. Sorry about your sheep...").also { stage = 2 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when(stage){
            0 -> npcl(FacialExpression.HALF_GUILTY,"I have all the bad luck. My sheep all run off somewhere, and then those mourners tell me they're infected!").also { stage++ }
            1 -> playerl(FacialExpression.HALF_GUILTY,"Well, I hope things start to look up for you.").also { stage = END_DIALOGUE }
            2 -> npcl(FacialExpression.HALF_GUILTY,"That's alright. It had to be done for the sake of the town. I just hope none of my other livestock get infected.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FARMER_BRUMTY_291)
    }

}
