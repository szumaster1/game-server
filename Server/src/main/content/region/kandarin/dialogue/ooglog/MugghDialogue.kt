package content.region.kandarin.dialogue.ooglog

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class MugghDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.CHILD_FRIENDLY, "Hey, what you doing here? We not open yet.").also { stage = 1 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            1 -> playerl(FacialExpression.FRIENDLY, "Just having a nosey, really.").also { stage++ }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "You bring dat nose back here when we open for business. I fix you up good.").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "Fix me up?").also { stage++ }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "Yeah, me give you facial. Try to make your ugly face look bit nicer.").also { stage++ }
            5 -> playerl(FacialExpression.FRIENDLY, "Charming.").also { stage++ }
            6 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MUGGH_7062)
    }
}

//After As a First Resort
//Muggh:
//Hey, what you doing here? You want me give you facial?
//Select an option
//Why, sure, since you make it sound so delightful.
//Player:
//Why, sure, since you make it sound so delightful.
//Normally
//Muggh smacks a fistful of mud on your face.
//If the player is wearing headgear
//Muggh:
//Take dat thing off your head, human, else you no get facial.
//If the player is already wearing Mud
//Muggh:
//Silly human, you already gots face full of muds.
//Um, maybe later.
//Player:
//Um, maybe later.