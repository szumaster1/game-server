package content.region.misc.dialogue.keldagrim.directors

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class YellowFortuneDirectorDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (player.isMale) {
            npc(FacialExpression.OLD_NORMAL, "Hello there, ${player.username}. You look a little masculine today!")
            stage = 0
        } else {
            npc(FacialExpression.OLD_NORMAL, "You are looking more and more like a male these days, ${player.username}... be careful now!")
        }
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.YELLOW_FORTUNE_DIRECTOR_2102)
    }
}