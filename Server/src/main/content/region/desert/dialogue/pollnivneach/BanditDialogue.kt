package content.region.desert.dialogue.pollnivneach

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class BanditDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc("Go away.")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        end()
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BANDIT_6388)
    }

}