package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class EvilTurnipDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> playerl(FacialExpression.HALF_ASKING, "So, how are you feeling?").also { stage = 0 }
            1 -> npcl(FacialExpression.OLD_NORMAL, "Hur hur hur...").also { stage = 3 }
            2 -> npcl(FacialExpression.OLD_NORMAL, "When we gonna fighting things, boss?").also { stage = 4 }
            3 -> npcl(FacialExpression.OLD_NORMAL, "I are turnip hear me roar! I too deadly to ignore.").also { stage = 6 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.OLD_NORMAL, "My roots feel hurty. I thinking it be someone I eated.").also { stage++ }
            1 -> playerl(FacialExpression.ASKING, "You mean some THING you ate?").also { stage++ }
            2 -> npcl(FacialExpression.OLD_NORMAL, "Hur hur hur. Yah, sure, why not.").also { stage = END_DIALOGUE }

            3 -> playerl(FacialExpression.FRIENDLY, "Well, as sinister as it's chuckling is, at least it's happy. That's a good thing, right?").also { stage = END_DIALOGUE }

            4 -> playerl(FacialExpression.FRIENDLY, "Soon enough.").also { stage++ }
            5 -> npcl(FacialExpression.OLD_NORMAL, "Hur hur hur. I gets the fighting.").also { stage = END_DIALOGUE }

            6 -> playerl(FacialExpression.FRIENDLY, "I'm glad it's on my side... and not behind me.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.EVIL_TURNIP_6833, NPCs.EVIL_TURNIP_6834)
    }

}
