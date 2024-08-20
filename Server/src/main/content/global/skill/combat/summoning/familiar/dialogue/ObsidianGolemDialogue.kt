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

/**
 * Represents the Obsidian golem dialogue.
 */
@Initializable
class ObsidianGolemDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (inEquipment(player!!, Items.FIRE_CAPE_6570)) {
            npcl(FacialExpression.CHILD_NORMAL, "Truly, you are a powerful warrior, Master!").also { stage = 0 }
            return true
        }
        when ((0..2).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "How many foes have you defeated, Master?").also { stage = 5 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Master! We are truly a mighty duo!").also { stage = 10 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Do you ever doubt your programming, Master?").also { stage = 15 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when(stage){
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Let us go forth and prove our strength, Master!").also { stage++ }
            1 -> playerl(FacialExpression.HALF_ASKING, "Where would you like to prove it?").also { stage++ }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "The caves of the TzHaar are filled with monsters for us to defeat, Master! TzTok-Jad shall quake in his slippers!").also { stage++ }
            3 -> playerl(FacialExpression.HALF_ASKING, "Have you ever met TzTok-Jad?").also { stage++ }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "Alas, Master, I have not. No Master has ever taken me to see him.").also { stage = END_DIALOGUE }

            5 -> playerl(FacialExpression.FRIENDLY, "Quite a few, I should think.").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_NORMAL, "Was your first foe as mighty as the volcano, Master?").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "Um, not quite.").also { stage++ }
            8 -> npcl(FacialExpression.CHILD_NORMAL, "I am sure it must have been a deadly opponent, Master!").also { stage++ }
            9 -> player(FacialExpression.FRIENDLY, "*Cough*","It might have been a chicken.","*Cough*").also { stage = END_DIALOGUE }

            10 -> playerl(FacialExpression.HALF_ASKING, "Do you think so?").also { stage++ }
            11 -> npcl(FacialExpression.CHILD_NORMAL, "Of course, Master! I am programmed to believe so.").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "Do you do anything you're not programmed to?").also { stage++ }
            13 -> npcl(FacialExpression.CHILD_NORMAL, "No, Master.").also { stage++ }
            14 -> playerl(FacialExpression.FRIENDLY, "I guess that makes things simple for you...").also { stage = END_DIALOGUE }

            15 -> playerl(FacialExpression.FRIENDLY, "I don't have programming. I can think about whatever I like.").also { stage++ }
            16 -> npcl(FacialExpression.CHILD_NORMAL, "What do you think about, Master?").also { stage++ }
            17 -> playerl(FacialExpression.FRIENDLY, "Oh, simple things: the sound of one hand clapping, where the gods come from...Simple things.").also { stage++ }
            18 -> npcl(FacialExpression.CHILD_NORMAL, "Paradox check = positive. Error. Reboot.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.OBSIDIAN_GOLEM_7345, NPCs.OBSIDIAN_GOLEM_7346)
    }

}
