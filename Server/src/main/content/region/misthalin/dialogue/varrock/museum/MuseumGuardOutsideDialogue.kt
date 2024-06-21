package content.region.misthalin.dialogue.varrock.museum

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class MuseumGuardOutsideDialogue(player: Player? = null) : Dialogue(player) {

    /*
     *  Info: Guard the Varrock Museum and the path leading to the Varrock Dig Site east of Varrock.
     *  Location: 3263,3441
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Hello there. Come to see the new museum?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.HALF_GUILTY, "Yes, how do I get in?").also { stage++ }
            1 -> npc(FacialExpression.HALF_GUILTY, "Well, the main entrance is 'round the front. Just head", "west then north slightly, you can't miss it!").also { stage++ }
            2 -> player(FacialExpression.HALF_GUILTY, "What about these doors?").also { stage++ }
            3 -> npc(FacialExpression.HALF_GUILTY, "They're primarily for the workmen bringing finds from the", "Dig Site, but you can go through if you want.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MUSEUM_GUARD_5943)
    }

}
