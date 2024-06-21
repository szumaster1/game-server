package content.region.fremennik.dialogue.miscellania

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class FerdDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_DEFAULT, "Good day, sir.").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.THINKING, "What are you doing down here?.").also { stage++ }
            1 -> npc(FacialExpression.OLD_DEFAULT, "Shoring up the walls.").also { stage++ }
            2 -> player(FacialExpression.ASKING, "What does that do?").also { stage++ }
            3 -> npc(FacialExpression.OLD_DEFAULT, "Stops them falling down.").also { stage = END_DIALOGUE }
            4 -> player(FacialExpression.ASKING, "Oh, I see.").also { stage++ }
            5 -> npc(FacialExpression.OLD_NOT_INTERESTED, "Aye.", "If you want to chatter, you'd better talk to ", "Thorodin over there. I'm working.").also { stage = END_DIALOGUE }
            6 -> player(FacialExpression.ASKING, "Okay then.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.FERD_3937)
    }
}
