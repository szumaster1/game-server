package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.NPCs
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class KaramOverlordDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> playerl(FacialExpression.FRIENDLY, "Do you want-").also { stage = 0 }
            1 -> npcl(FacialExpression.OLD_NORMAL, "Kneel before my awesome might!").also { stage = 10 }
            2 -> playerl(FacialExpression.HALF_ASKING, "...").also { stage = 19 }
            3 -> playerl(FacialExpression.FRIENDLY, "Errr...Have you FRIENDLYed down yet?").also { stage = 31 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.OLD_NORMAL, "Silence!").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "But I only...").also { stage++ }
            2 -> npcl(FacialExpression.OLD_NORMAL, "Silence!").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "Now, listen here...").also { stage++ }
            4 -> npcl(FacialExpression.OLD_NORMAL, "SIIIIIILLLLLEEEEENCE!").also { stage++ }
            5 -> playerl(FacialExpression.FRIENDLY, "Fine!").also { stage++ }
            6 -> npcl(FacialExpression.OLD_NORMAL, "Good!").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "But I only...").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Maybe I'll be so silent you'll think I never existed").also { stage++ }
            9 -> npcl(FacialExpression.OLD_NORMAL, "Oh, how I long for that day...").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.FRIENDLY, "I would, but I have a bad knee you see...").also { stage++ }
            11 -> npcl(FacialExpression.OLD_NORMAL, "Your feeble prattlings matter not, air-breather! Kneel or face my wrath!").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "I'm not afraid of you. You're only a squid in a bowl!").also { stage++ }
            13 -> npcl(FacialExpression.OLD_NORMAL, "Only? I, radiant in my awesomeness, am 'only' a squid in a bowl? Clearly you need to be shown in your place, lung-user!").also { stage++ }
            14 -> sendDialogue(player!!, "The Karamthulhu overlord narrows its eye and you find yourself unable to breathe!").also { stage++ }
            15 -> playerl(FacialExpression.FRIENDLY, "Gaak! Wheeeze!").also { stage++ }
            16 -> npcl(FacialExpression.OLD_NORMAL, "Who rules?").also { stage++ }
            17 -> playerl(FacialExpression.FRIENDLY, "You rule!").also { stage++ }
            18 -> npcl(FacialExpression.OLD_NORMAL, "And don't forget it!").also { stage = END_DIALOGUE }

            19 -> npcl(FacialExpression.OLD_NORMAL, "The answer 'be silent'!").also { stage++ }
            20 -> playerl(FacialExpression.FRIENDLY, "You have no idea what I was going to ask you.").also { stage++ }
            21 -> npcl(FacialExpression.OLD_NORMAL, "Yes I do; I know all!").also { stage++ }
            22 -> playerl(FacialExpression.FRIENDLY, "You have no idea what I was going to ask you.").also { stage++ }
            23 -> npcl(FacialExpression.OLD_NORMAL, "Yes I do; I know all!").also { stage++ }
            24 -> playerl(FacialExpression.FRIENDLY, "Then you will not be surprised to know I was going to ask you what you wanted to do today.").also { stage++ }
            25 -> npcl(FacialExpression.OLD_NORMAL, "You dare doubt me!").also { stage++ }
            26 -> npcl(FacialExpression.OLD_NORMAL, "The answer 'be silent' because your puny compressed brain could not even begin to comprehend my needs!").also { stage++ }
            27 -> playerl(FacialExpression.FRIENDLY, "Well, how about I dismiss you so you can go and do what you like?").also { stage++ }
            28 -> npcl(FacialExpression.OLD_NORMAL, "Well, how about I topple your nations into the ocean and dance my tentacle-waving victory dance upon your watery graves?").also { stage++ }
            29 -> playerl(FacialExpression.HALF_ASKING, "Yeah...well...").also { stage++ }
            30 -> npcl(FacialExpression.OLD_NORMAL, "Silence! Your burbling vexes me greatly!").also { stage = END_DIALOGUE }

            31 -> npcl(FacialExpression.OLD_NORMAL, "FRIENDLYed down? Why would I need to FRIENDLY down?").also { stage++ }
            32 -> playerl(FacialExpression.FRIENDLY, "Well there is that whole 'god complex' thing...").also { stage++ }
            33 -> npcl(FacialExpression.OLD_NORMAL, "Complex? What 'complex' are you drooling about this time, minion?").also { stage++ }
            34 -> playerl(FacialExpression.FRIENDLY, "I don't really think sheep really make mewling noises...").also { stage++ }
            35 -> npcl(FacialExpression.OLD_NORMAL, "Silence!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KARAMTHULHU_OVERLORD_6809, NPCs.KARAMTHULHU_OVERLORD_6810)
    }

}
