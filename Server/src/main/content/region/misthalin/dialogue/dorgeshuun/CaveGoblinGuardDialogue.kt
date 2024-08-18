package content.region.misthalin.dialogue.dorgeshuun

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.RandomFunction

/**
 * Represents the Cave goblin guard dialogue.
 */
@Initializable
class CaveGoblinGuardDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        when (RandomFunction.random(2)) {
            0 -> npc(FacialExpression.OLD_NORMAL, "Have a nice day!").also { stage = END_DIALOGUE }
            1 -> npc(FacialExpression.OLD_NORMAL, "I'm keeping an eye on you, surface-dweller.").also { stage = END_DIALOGUE }
            2 -> npc(FacialExpression.OLD_NORMAL, "Surface-dweller! You will never find your way into the city of ", "Dorgesh-Kaan!").also { stage = 0 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.HALF_ASKING, "Isn't it through that big door?").also { stage++ }
            1 -> npcl(FacialExpression.OLD_NORMAL, " Well, yes, but you'll never find your way in!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CAVE_GOBLIN_GUARD_2073, NPCs.CAVE_GOBLIN_GUARD_2074)
    }
}
