package content.region.fremennik.dialogue.neitiznot

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Slug Hemliggsen dialogue.
 */
@Initializable
class SlugHemliggsenDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npcl(FacialExpression.WORRIED, "Shhh. Go away. I'm not allowed to talk to you.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when(stage) {
            0 -> playerl(FacialExpression.ANNOYED, "Fine, whatever ...").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SLUG_HEMLIGSSEN_5520)
    }

}
