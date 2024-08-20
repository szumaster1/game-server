package content.global.skill.combat.summoning.familiar.dialogue

import core.api.consts.Items
import core.api.consts.NPCs
import core.api.inEquipmentOrInventory
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Praying mantis dialogue.
 */
@Initializable
class PrayingMantisDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (inEquipmentOrInventory(player!!, Items.BUTTERFLY_NET_10010) || inEquipmentOrInventory(player!!, Items.MAGIC_BUTTERFLY_NET_11259)) {
            npc(FacialExpression.CHILD_NORMAL, "Clatter click chitter click?","(Wouldn't you learn focus better if you used chopsticks?)").also { stage = 0 }
            return true
        }
        when ((0..3).random()) {
            0 -> npc(FacialExpression.CHILD_NORMAL, "Chitter chirrup chirrup?","(Have you been following your training, grasshopper?)").also { stage = 4 }
            1 -> npc(FacialExpression.CHILD_NORMAL, "Chitterchitter chirrup clatter.","(Today, grasshopper, I will teach you to walk on rice paper.)").also { stage = 9 }
            2 -> npc(FacialExpression.CHILD_NORMAL, "Clatter chirrup chirp chirrup clatter clatter.","(A wise man once said; 'Feed your mantis and it will be happy'.)").also { stage = 13 }
            3 -> npc(FacialExpression.CHILD_NORMAL, "Clatter chirrupchirp-","(Today, grasshopper, we will-)").also { stage = 15 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "Huh?").also { stage++ }
            1 -> npc(FacialExpression.CHILD_NORMAL, "Clicker chirrpchirrup.","(For catching the butterflies, grasshopper.)").also { stage++ }
            2 -> playerl(FacialExpression.FRIENDLY, "Oh, right! Well, if I use anything but the net I squash them.").also { stage++ }
            3 -> npc(FacialExpression.CHILD_NORMAL, "Chirrupchirrup click!","(Then, I could have them!)").also { stage = END_DIALOGUE }

            4 -> playerl(FacialExpression.FRIENDLY, "Yes, almost every day.").also { stage++ }
            5 -> npc(FacialExpression.CHILD_NORMAL, "Chirrupchirrup chirrup.","('Almost' is not good enough.)").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "Well, I'm trying as hard as I can.").also { stage++ }
            7 -> npc(FacialExpression.CHILD_NORMAL, "Chirrup chitter chitter chirrup?","(How do you expect to achieve enlightenment at this rate, grasshopper?)").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Spontaneously.").also { stage = END_DIALOGUE }

            9 -> playerl(FacialExpression.HALF_ASKING, "What if I can't find any?").also { stage++ }
            10 -> npc(FacialExpression.CHILD_NORMAL, "Clatter chitter click chitter...","(Then we will wander about and punch monsters in the head...)").also { stage++ }
            11 -> playerl(FacialExpression.HALF_ASKING, "I could do in an enlightened way if you want?").also { stage++ }
            12 -> npc(FacialExpression.CHILD_NORMAL, "Chirrupchitter!","(That will do!)").also { stage = END_DIALOGUE }

            13 -> playerl(FacialExpression.HALF_ASKING, "Is there any point to that saying?").also { stage++ }
            14 -> npc(FacialExpression.CHILD_NORMAL, "Clatter chirrupchirrup chirp.","(I find that a happy mantis is its own point.)").also { stage = END_DIALOGUE }

            15 -> playerl(FacialExpression.FRIENDLY, "You know, I'd rather you call me something other than grasshopper.").also { stage++ }
            16 -> npc(FacialExpression.CHILD_NORMAL, "Clitterchirp?","(Is there a reason for this?)").also { stage++ }
            17 -> playerl(FacialExpression.FRIENDLY, "You drool when you say it.").also { stage++ }
            18 -> npc(FacialExpression.CHILD_NORMAL, "Clickclatter! Chirrup chirpchirp click chitter...","(I do not! Why would I drool when I call you a juicy...)").also { stage++ }
            19 -> npc(FacialExpression.CHILD_NORMAL, "...clickclick chitter clickchitter click...","(...succulent, nourishing, crunchy...)").also { stage++ }
            20 -> npc(FacialExpression.CHILD_NORMAL, "*Drooool*").also { stage++ }
            21 -> playerl(FacialExpression.FRIENDLY, "You're doing it again!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.PRAYING_MANTIS_6798, NPCs.PRAYING_MANTIS_6799)
    }

}
