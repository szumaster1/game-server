package content.dialogue.keldagrim

import cfg.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Nolar dialogue.
 */
@Initializable
class NolarDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npc(FacialExpression.OLD_DEFAULT, "I have a wide variety of crafting tools on offer,", "care to take a look?").also { stage++ }
            1 -> showTopics(
                Topic(FacialExpression.FRIENDLY, "Yes please!", 2),
                Topic(FacialExpression.FRIENDLY, "No thanks.", END_DIALOGUE)
            )
            2 -> {
                end()
                openNpcShop(player, NPCs.NOLAR_2158)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.NOLAR_2158)
    }
}
