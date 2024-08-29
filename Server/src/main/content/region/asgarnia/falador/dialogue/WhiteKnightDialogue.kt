package content.region.asgarnia.falador.dialogue

import cfg.consts.NPCs
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the White knight dialogue.
 */
@Initializable
class WhiteKnightDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        sendDialogue(player, "He is too busy dancing to talk!").also { stage = END_DIALOGUE }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KNIGHT_660)
    }

}
