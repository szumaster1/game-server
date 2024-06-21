package content.region.kandarin.dialogue.feldip.gutanoth

import core.api.consts.NPCs
import core.api.isQuestComplete
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class TobanDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if(!isQuestComplete(player, "Watchtower")){
            end()
            sendDialogue(player!!, "He is busy at the moment...")
        } else {
            npc(FacialExpression.OLD_NORMAL, "The small t'ing returns; what do you want now?")
            stage = 0
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when(stage){
            0 -> player("I seek another task.").also { stage++ }
            1 -> npcl(FacialExpression.OLD_CALM_TALK2,"Have you arrived for dinner? Hahaha! Be gone, small t'ing!").also { stage++ }
            2 -> end()
        }
        return true
    }



    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TOBAN_855)
    }
}