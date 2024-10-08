package content.region.karamja.apeatoll.dialogue.marim

import org.rs.consts.NPCs
import core.api.toIntArray
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Elder Guard dialogue.
 */
@Initializable
class ElderGuardDialogue(player: Player? = null) : Dialogue(player){

    val ids = 4025..4031

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC

        //todo check player pos, if outside, player needs to be unable from entering without speaking, but currently player can clip through monke
        var outside = true
        //todo check if monke is in the corner
        var cornerGuard = false

        if (player.equipment.containsAtLeastOneItem(ids.toIntArray())) {
            if (outside) {
                npc(FacialExpression.OLD_ANGRY1, "Grrr ... What do you want?").also { stage = 10 }
            } else if(cornerGuard){
                npc(FacialExpression.OLD_ANGRY1, "Grrr ... What do you want?").also { stage = 20 }
            } else {
                npc(FacialExpression.OLD_ANGRY1, "Move!").also { stage = 99 }
            }
        } else {
            //todo monke is gonna knock you out if you don't have a monkey gree gree thang, chicken wang
        }

        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when(stage){
            10 -> playerl(FacialExpression.ASKING, "I must speak with Awowogei on a subject of great import.").also { stage ++ }
            11 -> npc(FacialExpression.OLD_NORMAL, "Only the Captain of the Monkey Guard or those he authorizes may enter this building. You will need his permission to enter.").also { stage++ }
            12 -> player(FacialExpression.ASKING, "Who is the Captain of the Monkey Guard?").also { stage ++ }
            13 -> player(FacialExpression.ASKING, "He goes by the name of Kruk").also { stage = 99 }

            20 -> player(FacialExpression.ASKING, "I would like to leave now.").also { stage ++ }
            21 -> npc(FacialExpression.OLD_NORMAL, "As you wish.").also { stage = 99 }

            99 -> end()
        }
        return true
    }

    override fun newInstance(player: Player?): Dialogue {
        return ElderGuardDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ELDER_GUARD_1461, NPCs.ELDER_GUARD_1462)
    }
}
