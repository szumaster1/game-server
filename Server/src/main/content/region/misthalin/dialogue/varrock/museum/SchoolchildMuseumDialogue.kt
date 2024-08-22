package content.region.misthalin.dialogue.varrock.museum

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Represents the Schoolchild museum dialogue.
 */
@Initializable
class SchoolchildMuseumDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.CHILD_FRIENDLY, *chats[RandomFunction.random(0, chats.size)])
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        end()
        return true
    }

    companion object {
        private val chats = arrayOf(
            arrayOf("Can you find my teacher? I need the toilet!"),
            arrayOf("I wonder what they're doing behind that", "rope."),
            arrayOf("Teacher! Can we go to the Natural History Exhibit", "now?"),
            arrayOf("*sniff* They won't let me take an arrowhead as a", "souvenir."),
            arrayOf("Yaaay! A day off school."),
            arrayOf("I wanna be an archaeologist when I grow up!"),
            arrayOf("Sada... Sram... Sa-ra-do-min is bestest!"),
            arrayOf("*cough* It's so dusty in here."),
            arrayOf("Maz... Zar... Za-mor-ak is bestest!")
        )
    }

    override fun getIds(): IntArray {
        return intArrayOf(
            NPCs.SCHOOLGIRL_10,
            NPCs.SCHOOLBOY_5945,
            NPCs.SCHOOLBOY_5946,
            NPCs.SCHOOLGIRL_5984
        )
    }

}
