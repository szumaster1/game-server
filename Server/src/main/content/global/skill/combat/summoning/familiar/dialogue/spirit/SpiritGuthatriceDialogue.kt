package content.global.skill.combat.summoning.familiar.dialogue.spirit

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.inEquipment
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class SpiritGuthatriceDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (inEquipment(player!!, Items.MIRROR_SHIELD_4156)) {
            npcl(FacialExpression.OLD_NORMAL, "You know, I'm sensing some trust issues here.").also { stage = 0 }
            return true
        }
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.OLD_NORMAL, "Is this what you do for fun?").also { stage = 11 }
            1 -> playerl(FacialExpression.FRIENDLY, "You know, I think I might train as a hypnotist.").also { stage = 15 }
            2 -> npcl(FacialExpression.OLD_NORMAL, "Come on, lets have a staring contest!").also { stage = 23 }
            3 -> npcl(FacialExpression.OLD_NORMAL, "You know, sometimes I don't think we're good friends.").also { stage = 27 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when(stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "I'm not sure I know what you are talking about.").also { stage++ }
            1 -> npcl(FacialExpression.OLD_NORMAL, "What are you holding?").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "A mirror shield.").also { stage++ }
            3 -> npcl(FacialExpression.OLD_NORMAL, "And what do those do?").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "Mumblemumble...").also { stage++ }
            5 -> npcl(FacialExpression.OLD_NORMAL, "What was that?").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "It protects me from your gaze attack.").also { stage++ }
            7 -> npcl(FacialExpression.OLD_NORMAL, "See! Why would you need one unless you didn't trust me?").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Who keeps demanding that we stop and have staring contests?").also { stage++ }
            9 -> npcl(FacialExpression.OLD_NORMAL, "How about we drop this and call it even?").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "Fine by me.").also { stage = END_DIALOGUE }

            11 -> npcl(FacialExpression.HALF_ASKING, "Is this what you do for fun?").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "Sometimes. Why, what do you do for fun?").also { stage++ }
            13 -> npcl(FacialExpression.FRIENDLY, "I find things and glare at them until they die!").also { stage++ }
            14 -> playerl(FacialExpression.FRIENDLY, "Well...everyone needs a hobby, I suppose.").also { stage = END_DIALOGUE }

            15 -> playerl(FacialExpression.FRIENDLY, "You know, I think I might train as a hypnotist.").also { stage++ }
            16 -> playerl(FacialExpression.HALF_ASKING, "Isn't that an odd profession for a cockatrice?").also { stage++ }
            17 -> npcl(FacialExpression.FRIENDLY, "Not at all! I've already been practicing!").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "Oh, really? How is that going?").also { stage++ }
            19 -> npcl(FacialExpression.FRIENDLY, "Not good. I tell them to look in my eyes and that they are feeling sleepy.").also { stage++ }
            20 -> playerl(FacialExpression.FRIENDLY, "I think I can see where this is headed.").also { stage++ }
            21 -> npcl(FacialExpression.FRIENDLY, "And then they just lie there and stop moving.").also { stage++ }
            22 -> playerl(FacialExpression.FRIENDLY, "I hate being right sometimes.").also { stage = END_DIALOGUE }

            23 -> npcl(FacialExpression.FRIENDLY, "Come on, lets have a staring contest!").also { stage++ }
            24 -> playerl(FacialExpression.FRIENDLY, "You win!").also { stage++ }
            25 -> npcl(FacialExpression.FRIENDLY, "Yay! I win again!").also { stage++ }
            26 -> playerl(FacialExpression.FRIENDLY, "Oh, it's no contest alright.").also { stage = END_DIALOGUE }

            27 -> npcl(FacialExpression.FRIENDLY, "You know, sometimes I don't think we're good friends.").also { stage++ }
            28 -> playerl(FacialExpression.HALF_ASKING, "What do you mean?").also { stage++ }
            29 -> npcl(FacialExpression.FRIENDLY, "Well, you never make eye contact with me for a start.").also { stage++ }
            30 -> playerl(FacialExpression.HALF_ASKING, "What happened the last time someone made eye contact with you?").also { stage++ }
            31 -> npcl(FacialExpression.CHILD_NORMAL, "Oh, I petrified them really good! Ooooh...okay, point taken.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SPIRIT_GUTHATRICE_6877, NPCs.SPIRIT_GUTHATRICE_6878)
    }

}
