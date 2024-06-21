package content.region.misthalin.dialogue.dorgeshuun

import core.api.toIntArray
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class RandomChildrenDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        when ((1..5).random()) {
            1 -> npc(FacialExpression.OLD_NORMAL, "Are you a surface-dweller?").also { stage = 0 }
            2 -> npcl(FacialExpression.OLD_NORMAL, "Are you " + player.name + "? Did you help Zanik save the city?").also { stage = 10 }
            3 -> npc(FacialExpression.OLD_NORMAL, "Sorry, I'm not meant to talk to strangers.").also { stage = 99 }
            4 -> npc(FacialExpression.OLD_NORMAL, "Shh! Don't tell anyone!").also { stage = 20 }
            5 -> npc(FacialExpression.OLD_NORMAL, "Help! Help! The surface people are attacking!").also { stage = 30 }
        }

        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> player(FacialExpression.FRIENDLY, "Yes...").also { stage++ }
            1 -> npcl(FacialExpression.OLD_NORMAL, "Haha! You look funny! All tall and skinny with tiny eyes!").also { stage = 99 }
            10 -> player(FacialExpression.FRIENDLY, "Yes, that was me!").also { stage++ }
            11 -> npcl(FacialExpression.OLD_NORMAL, "When I'm older I'm going to be an adventurer, just like Zanik!").also { stage = 99 }
            20 -> player(FacialExpression.FRIENDLY, "Don't tell anyone what?").also { stage++ }
            21 -> npc(FacialExpression.OLD_NORMAL, "SHHH!").also { stage = 99 }
            30 -> player(FacialExpression.FRIENDLY, "It's alright, I'm friendly!").also { stage = 99 }
            99 -> end()
        }
        return true
    }

    val ids = 5807..5822

    override fun getIds(): IntArray {
        return ids.toIntArray()
    }
}
