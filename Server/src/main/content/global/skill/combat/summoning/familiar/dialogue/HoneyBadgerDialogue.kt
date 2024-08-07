package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Honey badger dialogue.
 */
@Initializable
class HoneyBadgerDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..4).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "An outpouring of sanity-straining abuse*").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "An outpouring of spittal-flecked insults.*").also { stage = 0 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "A lambasting of visibly illustrated obscenities.*").also { stage = 0 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "A tirade of biologically questionable threats*").also { stage = 0 }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "A stream of eye-watering crudities*").also { stage = 0 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Why do I talk to you again?").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HONEY_BADGER_6845, NPCs.HONEY_BADGER_6846)
    }

}
