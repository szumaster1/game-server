package content.region.asgarnia.dialogue.portsarim

import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.GameWorld
import core.plugin.Initializable

@Initializable
class TheFaceDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (!GameWorld.settings!!.isMembers) {
            npc("I have no time for you.").also { stage = 5 }
        } else {
            player("Hello.")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                if (!isQuestComplete(player, "Ratcatchers")) {
                    sendDialogue(player!!, "She looks through you as if you don't exist.").also { stage = 5 }
                } else {
                    npcl(FacialExpression.FRIENDLY, "Oh it's you again.").also { stage++ }
                }
            }
            1 -> sendDialogue(player!!, "*She glances at you with barely disguised disdain.*").also { stage++ }
            2 -> player(FacialExpression.FRIENDLY, "*Aside* I wonder what her problem is?").also { stage++ }
            3 -> npcl(FacialExpression.FRIENDLY, "I must congratulate you on your performance, I have only ever seen one other display of musicality to match, as you should know.").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "Thank you, and don't worry, I won't tell Felkrash that you helped me.").also { stage = 5 }
            5 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.THE_FACE_2950)
    }

}