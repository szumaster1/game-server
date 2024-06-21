package content.region.desert.dialogue.alkharid

import core.api.consts.NPCs
import core.api.playAudio
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class CamTheCamelDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.HALF_GUILTY, "If I go near that camel, it'll probably bite my hand off.").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                end()
                playAudio(player, 327)
                sendMessage(player, "The camel spits at you, and you jump back hurriedly.")
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CAM_THE_CAMEL_2813)
    }
}
