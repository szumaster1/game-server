package content.location.lumbridge.tutors

import cfg.consts.NPCs
import core.api.getQuestStage
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Lachtopher dialogue.
 */
@Initializable
class LachtopherDialogue(player: Player? = null) : Dialogue(player) {


    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (getQuestStage(player, "Lost Tribe") == 10) {
            player("Do you know what happened in the cellar?").also { stage = 11 }
        } else {
            player("Hello there.").also { stage = 0 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.HALF_GUILTY, "Hello, I suppose. I'm Lachtopher. Could you lend me some money?").also { stage++ }
            1 -> playerl(FacialExpression.HALF_GUILTY, "Lend you money? I really don't think so. Don't you have any of your own?").also { stage++ }
            2 -> npcl(FacialExpression.HALF_GUILTY, "I spent it all and I can't be bothered to earn any more.").also { stage++ }
            3 -> playerl(FacialExpression.HALF_GUILTY, "Right, and you want my hard-earned money instead? No chance!").also { stage++ }
            4 -> npcl(FacialExpression.HALF_GUILTY, "You're just like my sister, Victoria. She won't give me any money.").also { stage++ }
            5 -> playerl(FacialExpression.HALF_GUILTY, "Your sister sounds like she has the right idea.").also { stage++ }
            6 -> npcl(FacialExpression.HALF_GUILTY, "Yeah, I've heard it all before. 'Oh,' she says, 'It's easy to make money: just complete Tasks for cash.'").also { stage++ }
            7 -> playerl(FacialExpression.HALF_GUILTY, "Well, if you want to make money...").also { stage++ }
            8 -> npcl(FacialExpression.HALF_GUILTY, "That's just it. I don't want to make money. I just want to have money.").also { stage++ }
            9 -> playerl(FacialExpression.HALF_GUILTY, "I've had it with you! I don't think I've come across a less worthwhile person.").also { stage++ }
            10 -> playerl(FacialExpression.HALF_GUILTY, "I think I'll call you Lazy Lachtopher, from now on.").also { stage = END_DIALOGUE }

            11 -> npcl(FacialExpression.HALF_GUILTY,"No. What happened in the cellar?").also { stage++ }
            12 -> playerl(FacialExpression.HALF_GUILTY,"Well, part of the wall has collapsed.").also { stage++ }
            13 -> npcl(FacialExpression.HALF_GUILTY,"Good heavens! You'd better find out what happened!").also { stage++ }
            14 -> playerl(FacialExpression.HALF_GUILTY,"Well, yes, that's what I'm doing.").also { stage++ }
            15 -> npcl(FacialExpression.HALF_GUILTY,"Good! I know we're safe in your hands.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LACHTOPHER_7870)
    }
}
