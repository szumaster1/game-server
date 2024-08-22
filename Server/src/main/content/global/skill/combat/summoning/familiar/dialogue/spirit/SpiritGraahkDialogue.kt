package content.global.skill.combat.summoning.familiar.dialogue.spirit

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Spirit graahk dialogue.
 */
@Initializable
class SpiritGraahkDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> playerl(FacialExpression.FRIENDLY, "Your spikes are looking particularly spiky today.").also { stage = 0 }
            1 -> npcl(FacialExpression.OLD_DEFAULT, "My spikes hurt, could you pet them for me?").also { stage = 6 }
            2 -> npcl(FacialExpression.OLD_DEFAULT, "Hi!").also { stage = 7 }
            3 -> playerl(FacialExpression.FRIENDLY, "How's your day going?").also { stage = 14 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.OLD_DEFAULT, "Really, you think so?").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "Yes. Most pointy, indeed.").also { stage++ }
            2 -> npcl(FacialExpression.OLD_DEFAULT, "That's really kind of you to say. I was going to spike you but I won't now...").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "Thanks?").also { stage++ }
            4 -> npcl(FacialExpression.OLD_DEFAULT, "...I'll do it later instead.").also { stage++ }
            5 -> playerl(FacialExpression.FRIENDLY, "*sigh!*").also { stage = END_DIALOGUE }

            6 -> playerl(FacialExpression.FRIENDLY, "Aww, of course I can I'll just... Oww! I think you drew blood that time.").also { stage = END_DIALOGUE }

            7 -> playerl(FacialExpression.FRIENDLY, "Hello. Are you going to spike me again?").also { stage++ }
            8 -> npcl(FacialExpression.OLD_DEFAULT, "No, I got a present to apologise for last time.").also { stage++ }
            9 -> playerl(FacialExpression.FRIENDLY, "That's really sweet, thank you.").also { stage++ }
            10 -> npcl(FacialExpression.OLD_DEFAULT, "Here you go, it's a special cushion to make you comfortable.").also { stage++ }
            11 -> playerl(FacialExpression.FRIENDLY, "It's made of spikes!").also { stage++ }
            12 -> npcl(FacialExpression.OLD_DEFAULT, "Yes, but they're therapeutic spikes.").also { stage++ }
            13 -> playerl(FacialExpression.FRIENDLY, "...").also { stage = END_DIALOGUE }

            14 -> npcl(FacialExpression.OLD_DEFAULT, "It's great! Actually I've got something to show you!").also { stage++ }
            15 -> playerl(FacialExpression.FRIENDLY, "Oh? What's that?").also { stage++ }
            16 -> npcl(FacialExpression.OLD_DEFAULT, "You'll need to get closer!").also { stage++ }
            17 -> playerl(FacialExpression.FRIENDLY, "I can't see anything...").also { stage++ }
            18 -> npcl(FacialExpression.OLD_DEFAULT, "It's really small - even closer.").also { stage++ }
            19 -> playerl(FacialExpression.FRIENDLY, "Oww! I'm going to have your spikes trimmed!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_GRAAHK_7363, NPCs.SPIRIT_GRAAHK_7364)
    }
}
