package content.global.skill.combat.summoning.familiar.dialogue

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
class MinotaurDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (inEquipment(player!!, Items.GUTHANS_HELM_4724)) {
            npcl(FacialExpression.CHILD_NORMAL, "...").also { stage = 0 }
            return true
        }
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "All this walking about is making me angry.").also { stage = 6 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Can you tell me why we're not fighting yet?").also { stage = 10 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Hey, no-horns?").also { stage = 12 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Hey no-horns!").also { stage = 18 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.HALF_ASKING, "What?").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Are you having a laugh?").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "I'm not sure I know what you-").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Listen, no-horns, you have two choices: take off the horns yourself or I'll headbutt you until they fall off.").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "Yessir.").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_NORMAL, "Good, no-horns. Let's not have this conversation again.").also { stage = END_DIALOGUE }

            6 -> playerl(FacialExpression.FRIENDLY, "You seem to be quite happy about that.").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_NORMAL, "Yeah! There's nothing like getting a good rage on and then working it out on some no-horns.").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "I can't say I know what you mean.").also { stage++ }
            9 -> npcl(FacialExpression.CHILD_NORMAL, "Well I didn't think a no-horns like you would get it!").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.FRIENDLY, "Buck up; I'll find you something to hit soon.").also { stage++ }
            11 -> npcl(FacialExpression.CHILD_NORMAL, "You'd better, no-horns, because that round head of yours is looking mighty axeable.").also { stage = END_DIALOGUE }

            12 -> playerl(FacialExpression.HALF_ASKING, "Why do you keep calling me no-horns?").also { stage++ }
            13 -> npcl(FacialExpression.CHILD_NORMAL, "Do I really have to explain that?").also { stage++ }
            14 -> playerl(FacialExpression.FRIENDLY, "No, thinking about it, it's pretty self-evident.").also { stage++ }
            15 -> npcl(FacialExpression.CHILD_NORMAL, "Glad we're on the same page, no-horns.").also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "So, what did you want?").also { stage++ }
            17 -> npcl(FacialExpression.CHILD_NORMAL, "I've forgotten, now. I'm sure it'll come to me later.").also { stage = END_DIALOGUE }

            18 -> playerl(FacialExpression.HALF_ASKING, "Yes?").also { stage++ }
            19 -> npcl(FacialExpression.CHILD_NORMAL, "Oh, I don't have anything to say, I was just yelling at you.").also { stage++ }
            20 -> playerl(FacialExpression.HALF_ASKING, "Why?").also { stage++ }
            21 -> npcl(FacialExpression.CHILD_NORMAL, "No reason. I do like to mess with the no-horns, though.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(
            NPCs.BRONZE_MINOTAUR_6853,  NPCs.BRONZE_MINOTAUR_6854,
            NPCs.IRON_MINOTAUR_6855,    NPCs.IRON_MINOTAUR_6856,
            NPCs.STEEL_MINOTAUR_6857,   NPCs.STEEL_MINOTAUR_6858,
            NPCs.MITHRIL_MINOTAUR_6859, NPCs.MITHRIL_MINOTAUR_6860,
            NPCs.ADAMANT_MINOTAUR_6861, NPCs.ADAMANT_MINOTAUR_6862,
            NPCs.RUNE_MINOTAUR_6863,    NPCs.RUNE_MINOTAUR_6864
        )
    }
}
