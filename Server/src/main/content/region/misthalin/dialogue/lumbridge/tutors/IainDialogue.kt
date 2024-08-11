package content.region.misthalin.dialogue.lumbridge.tutors

import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.api.consts.NPCs


/**
 * Iain dialogue.
 */
@Initializable
class IainDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Iain was a religious pilgrim from the Asgarnian capital city of Falador.
     * He is visiting Lumbridge, has work-for option.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        sendDialogue(player, "Iain seems too busy to talk.").also { stage = END_DIALOGUE }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return IainDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.IAIN_7868)
    }

}
