package content.region.misthalin.dialogue.wizardstower

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class WizardGrayzagDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc("Not now, I'm trying to concentrate on a", "very difficult spell!")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> end()
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return WizardGrayzagDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.WIZARD_GRAYZAG_707)
    }

}