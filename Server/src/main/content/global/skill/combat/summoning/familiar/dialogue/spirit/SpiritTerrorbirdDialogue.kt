package content.global.skill.combat.summoning.familiar.dialogue.spirit

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class SpiritTerrorbirdDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..4).random()) {
            0 -> npcl(FacialExpression.OLD_NORMAL, "This is a fun little walk.").also { stage = 0 }
            1 -> npcl(FacialExpression.OLD_NORMAL, "I can keep this up for hours.").also { stage = 1 }
            2 -> npcl(FacialExpression.OLD_NORMAL, "Are we going to visit a bank soon?").also { stage = 2 }
            3 -> npcl(FacialExpression.OLD_NORMAL, "Can we go to a bank now?").also { stage = 4 }
            4 -> npcl(FacialExpression.OLD_NORMAL, "So...heavy...").also { stage = 9 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Why do I get the feeling you'll change your tune when I start loading you up with items?").also { stage = END_DIALOGUE }

            1 -> playerl(FacialExpression.FRIENDLY, "I'm glad, as we still have plenty of time to go.").also { stage = END_DIALOGUE }

            2 -> playerl(FacialExpression.FRIENDLY, "I'm not sure, you still have plenty of room for more stuff.").also { stage++ }
            3 -> npcl(FacialExpression.OLD_NORMAL, "Just don't leave it too long, okay?").also { stage = END_DIALOGUE }

            4 -> playerl(FacialExpression.FRIENDLY, "Just give me a little longer, okay?").also { stage++ }
            5 -> npcl(FacialExpression.OLD_NORMAL, "That's what you said last time!").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "Did I?").also { stage++ }
            7 -> npcl(FacialExpression.OLD_NORMAL, "Yes!").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Well, I mean it this time, promise.").also { stage = END_DIALOGUE }

            9 -> playerl(FacialExpression.FRIENDLY, "I knew you'd change your tune once you started carrying things.").also { stage++ }
            10 -> npcl(FacialExpression.OLD_NORMAL, "Can we go bank this stuff now?").also { stage++ }
            11 -> playerl(FacialExpression.FRIENDLY, "Sure. You do look like you're about to collapse.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_TERRORBIRD_6794, NPCs.SPIRIT_TERRORBIRD_6795)
    }

}
