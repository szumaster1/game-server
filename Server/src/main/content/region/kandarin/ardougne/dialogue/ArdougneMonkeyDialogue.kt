package content.region.kandarin.ardougne.dialogue

import cfg.consts.Items
import cfg.consts.NPCs
import core.api.inEquipment
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Ardougne Monkey dialogue.
 */
@Initializable
class ArdougneMonkeyDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (!inEquipment(player, Items.MSPEAK_AMULET_4021, 1)) {
            npc(FacialExpression.OLD_LAUGH1, "Eeekeek ookeek!").also { stage = END_DIALOGUE }
        } else {
            var a = 1..5
            when (a.random()) {
                1 -> npc(FacialExpression.OLD_LAUGH1, "Arr!").also { stage = 10 }
                2 -> npcl(FacialExpression.OLD_LAUGH1, "Let me go, can't ye hear them? Howlin' in the dark...").also { stage = 20 }
                3 -> npcl(FacialExpression.OLD_DEFAULT, "I'm not goin' back in that brewery, not fer all the Bitternuts I can carry!").also { stage = END_DIALOGUE }
                4 -> npc(FacialExpression.OLD_DEFAULT, "Are ye here for...the stuff?").also { stage = 30 }
                5 -> npc(FacialExpression.OLD_DISTRESSED, "Arr! Yer messin with me monkey plunder!").also { stage = 40 }
            }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            10 -> player(FacialExpression.JOLLY, "Arr!").also { stage++ }
            11 -> npc(FacialExpression.OLD_LAUGH1, "Arr!").also { stage++ }
            12 -> player(FacialExpression.JOLLY, "Arr!").also { stage++ }
            13 -> npc(FacialExpression.OLD_LAUGH1, "Arr!").also { stage++ }
            14 -> player(FacialExpression.JOLLY, "Arr!").also { stage++ }
            15 -> npc(FacialExpression.OLD_LAUGH1, "Arr!").also { stage++ }
            16 -> player(FacialExpression.JOLLY, "Arr!").also { stage++ }
            17 -> npc(FacialExpression.OLD_LAUGH1, "Bored now...").also { stage = END_DIALOGUE }

            20 -> player(FacialExpression.ASKING, "What do you mean?").also { stage++ }
            21 -> npc(FacialExpression.OLD_DISTRESSED, "I'm not hangin' around te be killed!").also { stage++ }
            22 -> npc(FacialExpression.OLD_DISTRESSED, "The Horrors, the Horrors!").also { stage = END_DIALOGUE }

            30 -> player(FacialExpression.ASKING, "What?").also { stage++ }
            31 -> npc(FacialExpression.OLD_DEFAULT, "You know...the 'special' bananas?").also { stage++ }
            32 -> player(FacialExpression.ASKING, "No... why do you ask?").also { stage++ }
            33 -> npc(FacialExpression.OLD_SAD, "No reason. Have a nice day.").also { stage = END_DIALOGUE }

            40 -> player(FacialExpression.ASKING, "What?").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MONKEY_4363)
    }
}
