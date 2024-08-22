package content.region.kandarin.dialogue.ardougne.monastery

import content.region.kandarin.quest.clocktower.dialogue.BrotherKojoDialogueFile
import cfg.consts.NPCs
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Brother Kojo dialogue.
 */
@Initializable
class BrotherKojoDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        openDialogue(player!!, BrotherKojoDialogueFile(), npc)
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BROTHER_KOJO_223)
    }
}