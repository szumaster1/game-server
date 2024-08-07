package content.region.desert.dialogue.pollnivneach

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Ali the hag dialogue.
 */
@Initializable
class AliTheHagDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.FRIENDLY, "Good day, old hag.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.LAUGH, "Old hag indeed! I have a name you know!").also { stage++ }
            1 -> player(FacialExpression.THINKING, "Let me guess it wouldn't be Ali would it?").also { stage++ }
            2 -> npc(FacialExpression.ANNOYED, "Well how else would you abbreviate Alice then?", "And no, you can't call me Al!").also { stage++ }
            3 -> npc(FacialExpression.ASKING, "Now what do you want from the Old hag of Pollnivneach?", "To hex someone? Power, beauty, eternal youth", "or something else drab like that?").also { stage++ }
            4 -> player(FacialExpression.HALF_GUILTY, "Actually none of those, I'm new in town and", "I just wanted to get to know the locals.").also { stage++ }
            5 -> npc(FacialExpression.ANNOYED, "I'm busy brewing potions, so if you ", "disturb me again without reason,", "I will turn you into a frog!").also { stage++ }
            6 -> player(FacialExpression.SCARED, "Oh I'm sorry I won't let it happen again.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ALI_THE_HAG_1871)
    }
}
