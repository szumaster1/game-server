package content.global.skill.combat.summoning.familiar.dialogue

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Giant chinchompa dialogue.
 */
@Initializable
class GiantChinchompaDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..4).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Half a pound of tuppenny rice, half a pound of treacle...").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "What's small, brown and blows up?").also { stage = 5 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "I spy, with my little eye, something beginning with 'B'.").also { stage = 10 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "I seem to have found a paper bag.").also { stage = 15 }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "Woah, woah, woah - hold up there.").also { stage = 19 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0  -> playerl(FacialExpression.HALF_ASKING, "I hate it when you sing that song.").also { stage++ }
            1  -> npcl(FacialExpression.CHILD_NORMAL, "...that's the way the money goes...").also { stage++ }
            2  -> playerl(FacialExpression.HALF_ASKING, "Couldn't you sing 'Kumbaya' or something?").also { stage++ }
            3  -> npcl(FacialExpression.CHILD_NORMAL, "...BANG, goes the chinchompa!").also { stage++ }
            4  -> playerl(FacialExpression.HALF_ASKING, "Sheesh.").also { stage = END_DIALOGUE }

            5  -> playerl(FacialExpression.HALF_ASKING, "A brown balloon?").also { stage++ }
            6  -> npcl(FacialExpression.CHILD_NORMAL, "A chinchompa! Pull my finger.").also { stage++ }
            7  -> playerl(FacialExpression.HALF_ASKING, "I'm not pulling your finger.").also { stage++ }
            8  -> npcl(FacialExpression.CHILD_NORMAL, "Nothing will happen. Truuuuust meeeeee.").also { stage++ }
            9  -> playerl(FacialExpression.FRIENDLY, "Oh, go away.").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.HALF_ASKING, "Bomb? Bang? Boom? Blowing-up-little-chipmunk?").also { stage++ }
            11 -> npcl(FacialExpression.CHILD_NORMAL, "No. Body odour. You should wash a bit more.").also { stage++ }
            12 -> playerl(FacialExpression.HALF_ASKING, "Well, that was pleasant. You don't smell all that great either, you know.").also { stage++ }
            13 -> npcl(FacialExpression.CHILD_NORMAL, "Stop talking, stop talking! Your breath stinks!").also { stage++ }
            14 -> playerl(FacialExpression.HALF_ASKING, "We're never going to get on, are we?").also { stage = END_DIALOGUE }

            15 -> playerl(FacialExpression.HALF_ASKING, "Well done. Anything in it?").also { stage++ }
            16 -> npcl(FacialExpression.CHILD_NORMAL, "Hmmm. Let me see. It seems to be full of some highly sought after, very expensive...chinchompa breath!").also { stage++ }
            17 -> playerl(FacialExpression.FRIENDLY, "No, don't pop it!").also { stage++ }
            18 -> playerl(FacialExpression.HALF_ASKING, "You just cannot help yourself, can you?").also { stage = END_DIALOGUE }

            19 -> playerl(FacialExpression.HALF_ASKING, "What is it, ratty?").also { stage++ }
            20 -> npcl(FacialExpression.CHILD_NORMAL, "You got something in your backpack that you'd like to tell me about?").also { stage++ }
            21 -> playerl(FacialExpression.HALF_ASKING, "I was wondering when you were going to bring up the chinchompa. I'm sure they like it in my inventory.").also { stage++ }
            22 -> npcl(FacialExpression.CHILD_NORMAL, "Did they not teach you anything in school? Chinchompas die in hot bags. You know what happens when chinchompas die. Are you attached to your back?").also { stage++ }
            23 -> playerl(FacialExpression.HALF_ASKING, "Medically, yes. And I kind of like it too. I get the point.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GIANT_CHINCHOMPA_7353, NPCs.GIANT_CHINCHOMPA_7354)
    }

}
