package content.global.skill.combat.summoning.familiar.dialogue.spirit

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class SpiritJellyDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.OLD_NORMAL, "Play play play play!").also { stage = 0 }
            1 -> npcl(FacialExpression.OLD_NORMAL, "It's playtime now!").also { stage = 4 }
            2 -> npcl(FacialExpression.OLD_NORMAL, "Can we go over there now, pleasepleasepleasepleeeeeease?").also { stage = 8 }
            3 -> npcl(FacialExpression.OLD_NORMAL, "What game are we playing now?").also { stage = 14 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "The only game I have time to play is the 'Staying Very Still' game.").also { stage++ }
            1 -> npcl(FacialExpression.OLD_NORMAL, "But that game is soooooo booooooring...").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "How about we use the extra house rule, that makes it the 'Staying Very Still and Very Quiet' game.").also { stage++ }
            3 -> npcl(FacialExpression.OLD_NORMAL, "Happy happy! I love new games!").also { stage = END_DIALOGUE }

            4 -> playerl(FacialExpression.FRIENDLY, "Okay, how about we play the 'Staying Very Still' game.").also { stage++ }
            5 -> npcl(FacialExpression.OLD_NORMAL, "But that game is booooooring...").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "If you win then you can pick the next game, how about that?").also { stage++ }
            7 -> npcl(FacialExpression.OLD_NORMAL, "Happy happy!").also { stage = END_DIALOGUE }

            8 -> playerl(FacialExpression.FRIENDLY, "Go over where?").also { stage++ }
            9 -> npcl(FacialExpression.OLD_NORMAL, "I dunno, someplace fun, pleasepleaseplease!").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "Okay, but first, let's play the 'Sitting Very Still' game.").also { stage++ }
            11 -> npcl(FacialExpression.OLD_NORMAL, "But that game is booooooring...").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "Well, if you win we can go somewhere else, okay?").also { stage++ }
            13 -> npcl(FacialExpression.OLD_NORMAL, "Happy happy!").also { stage = END_DIALOGUE }

            14 -> playerl(FacialExpression.FRIENDLY, "It's called the 'Staying Very Still' game.").also { stage++ }
            15 -> npcl(FacialExpression.OLD_NORMAL, "This game is booooooring...").also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "Hey, all that moping doesn't look very still to me.").also { stage++ }
            17 -> npcl(FacialExpression.OLD_NORMAL, "I never win at this game...").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "You know what? I think I'll not count it this one time").also { stage++ }
            19 -> npcl(FacialExpression.OLD_NORMAL, "Happy happy! You're the best friend ever!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_JELLY_6992, NPCs.SPIRIT_JELLY_6993)
    }

}
