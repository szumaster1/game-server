package content.region.kandarin.piscatoris.dialogue

import org.rs.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Herman carlos dialogue.
 */
@Initializable
class HermanCarlosDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.ASKING, "Oh, hello again. Want some ore?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Hiya Hermie!").also { stage++ }
            1 -> npcl(FacialExpression.FRIENDLY, "Ah, " + player.username + "! How's the fishing going?").also { stage++ }
            2 -> playerl(FacialExpression.NEUTRAL, "Still working on it. How's the Colony?").also { stage++ }
            3 -> npcl(FacialExpression.HAPPY, "Flourishing, now that the colonists are coming back! Soon all the finest restaurants will be serving monkfish!").also { stage++ }
            4 -> npcl(FacialExpression.ANNOYED, "It's a pity those skeletons you brought here haven't gone away again, though. They're in the dormitories, which is rather annoying! So please take a little time to kick 'em around a bit.").also { stage++ }
            5 -> npcl(FacialExpression.ANNOYED, "Oh, and please don't call me Hermie.").also { stage++ }
            6 -> playerl(FacialExpression.LAUGH, "Whatever you like, Hermie!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HERMAN_CARANOS_3822)
    }

}
