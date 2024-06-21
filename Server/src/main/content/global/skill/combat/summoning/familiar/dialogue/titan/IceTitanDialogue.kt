package content.global.skill.combat.summoning.familiar.dialogue.titan

import core.api.consts.NPCs
import core.api.inBorders
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.utilities.END_DIALOGUE

@Initializable
class IceTitanDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (inBorders(player!!, 3113, 2753, 3391, 3004)) {
            npcl(FacialExpression.CHILD_NORMAL, "I'm melting!").also { stage = 0 }
            return true
        }
        when ((0..3).random()) {
            0 -> playerl(FacialExpression.HALF_ASKING, "How are you feeling?").also { stage = 3 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Can we just stay away from fire for a while?").also { stage = 19 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "I could murder an ice-cream.").also { stage = 28 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "It's too hot here.").also { stage = 42 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "I have to admit, I am rather on the hot side myself.").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "No, I mean I'm actually melting! My legs have gone dribbly.").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Urk! Well, try hold it together.").also { stage = END_DIALOGUE }

            3 -> npcl(FacialExpression.CHILD_NORMAL, "Hot.").also { stage++ }
            4 -> playerl(FacialExpression.HALF_ASKING, "Are you ever anything else?").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_NORMAL, "Sometimes I'm just the right temperature: absolute zero.").also { stage++ }
            6 -> playerl(FacialExpression.HALF_ASKING, "What's that then, when it's not at home with it's feet up on the couch?").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_NORMAL, "What?").also { stage++ }
            8 -> playerl(FacialExpression.HALF_ASKING, "Absolute zero; what is it?").also { stage++ }
            9 -> npcl(FacialExpression.CHILD_NORMAL, "Oh...it's the lowest temperature that can exist.").also { stage++ }
            10 -> playerl(FacialExpression.HALF_ASKING, "Like the temperature of ice?").also { stage++ }
            11 -> npcl(FacialExpression.CHILD_NORMAL, "Um, no. Rather a lot colder.").also { stage++ }
            12 -> playerl(FacialExpression.HALF_ASKING, "Like a deepest, darkest winter day?").also { stage++ }
            13 -> npcl(FacialExpression.CHILD_NORMAL, "Nah, that's warm by comparison.").also { stage++ }
            14 -> playerl(FacialExpression.HALF_ASKING, "Like an Ice Barrage in your jammies?").also { stage++ }
            15 -> npcl(FacialExpression.CHILD_NORMAL, "Even colder than that.").also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "Yikes! That's rather chilly.").also { stage++ }
            17 -> npcl(FacialExpression.CHILD_NORMAL, "Yeah. Wonderful, isn't it?").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "If you say so.").also { stage = END_DIALOGUE }

            19 -> playerl(FacialExpression.FRIENDLY, "I like fire, it's so pretty.").also { stage++ }
            20 -> npcl(FacialExpression.CHILD_NORMAL, "Personally, I think it's terrifying.").also { stage++ }
            21 -> playerl(FacialExpression.FRIENDLY, "Why?").also { stage++ }
            22 -> npcl(FacialExpression.CHILD_NORMAL, "I'm not so keen on hot things.").also { stage++ }
            23 -> playerl(FacialExpression.FRIENDLY, "Ah.").also { stage++ }
            24 -> npcl(FacialExpression.CHILD_NORMAL, "Indeed.").also { stage++ }
            25 -> playerl(FacialExpression.FRIENDLY, "I see.").also { stage++ }
            26 -> npcl(FacialExpression.CHILD_NORMAL, "Yes. Well...").also { stage++ }
            27 -> playerl(FacialExpression.FRIENDLY, "...let's get on with it.").also { stage = END_DIALOGUE }

            28 -> playerl(FacialExpression.FRIENDLY, "Is that a Slayer creature?").also { stage++ }
            29 -> npcl(FacialExpression.CHILD_NORMAL, "Um...").also { stage++ }
            30 -> playerl(FacialExpression.HALF_ASKING, "What does it drop?").also { stage++ }
            31 -> npcl(FacialExpression.CHILD_NORMAL, "Erm...").also { stage++ }
            32 -> playerl(FacialExpression.HALF_ASKING, "What level is it?").also { stage++ }
            33 -> npcl(FacialExpression.CHILD_NORMAL, "It...").also { stage++ }
            34 -> playerl(FacialExpression.HALF_ASKING, "Where can I find it?").also { stage++ }
            35 -> npcl(FacialExpression.CHILD_NORMAL, "I...").also { stage++ }
            36 -> playerl(FacialExpression.HALF_ASKING, "What equipment will I need?").also { stage++ }
            37 -> npcl(FacialExpression.CHILD_NORMAL, "What...").also { stage++ }
            38 -> playerl(FacialExpression.FRIENDLY, "I don't think it will be high enough level.").also { stage++ }
            39 -> npcl(FacialExpression.CHILD_NORMAL, "Urm...").also { stage++ }
            40 -> playerl(FacialExpression.FRIENDLY, "...").also { stage++ }
            41 -> npcl(FacialExpression.CHILD_NORMAL, "We should get on with what we were doing.").also { stage = END_DIALOGUE }

            42 -> playerl(FacialExpression.FRIENDLY, "It's really not that hot. I think it's rather pleasant.").also { stage++ }
            43 -> npcl(FacialExpression.CHILD_NORMAL, "Well, it's alright for some. Some of us don't like the heat. I burn easily - well, okay, melt.").also { stage++ }
            44 -> playerl(FacialExpression.FRIENDLY, "Well, at least I know where to get a nice cold drink if I need one.").also { stage++ }
            45 -> npcl(FacialExpression.CHILD_NORMAL, "What was that?").also { stage++ }
            46 -> playerl(FacialExpression.FRIENDLY, "Nothing. Hehehehe").also { stage = END_DIALOGUE }

        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ICE_TITAN_7359, NPCs.ICE_TITAN_7360)
    }

}
