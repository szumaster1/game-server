package content.location.neitiznot

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Anna Isaakson dialogue.
 */
@Initializable
class AnnaIsaaksonDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc("Hello visitor, how are you?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player("Better than expected. Its a lot...nicer...here than I was", "expecting. Everyone seems pretty happy.").also { stage++ }
            1 -> npc("Of course, the Burgher is strong and wise, and looks", "after us well").also { stage++ }
            2 -> player("I think some of those Jatizso citizens have got", "the wrong idea about this place.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ANNE_ISAAKSON_5512)
    }
}