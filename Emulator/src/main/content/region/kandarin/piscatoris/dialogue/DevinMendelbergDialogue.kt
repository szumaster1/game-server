package content.region.kandarin.piscatoris.dialogue

import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.api.sendDialogueOptions
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Devin mendelberg dialogue.
 */
@Initializable
class DevinMendelbergDialogue(player: Player? = null) : Dialogue(player) {

    // setVarbit(player, 2098, 1, true) unlock npc 3828 || hides Wise Old man.

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Swan Song") && npc.id == 3828) {
            playerl(FacialExpression.FRIENDLY, "Hello! What can I do for you?")
            stage = 9
        } else {
            playerl(FacialExpression.FRIENDLY, "Good morning.")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.HALF_CRYING, "Sorry, but this is NOT a good morning!").also { stage++ }
            1 -> playerl(FacialExpression.FRIENDLY, "Why, what's the matter?").also { stage++ }
            2 -> npcl(FacialExpression.SAD, "I'm going to lose my job!").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "What have you done to deserve that?").also { stage++ }
            4 -> npcl(
                FacialExpression.SAD,
                "Nothing! But the Piscatoris Fishing Colony is going to go out of business, and I'll have to go back to the east."
            ).also { stage++ }

            5 -> playerl(FacialExpression.FRIENDLY, "Oh dear.").also { stage++ }
            6 -> npcl(
                FacialExpression.HALF_GUILTY,
                "If you want to help, you can get Kathy to take you up to the Colony. Herman's looking for someone to run an important errand for him."
            ).also { stage++ }

            7 -> playerl(FacialExpression.FRIENDLY, "I'll bear it in mind.").also { stage++ }
            8 -> end().also { stage = END_DIALOGUE }

            9 -> sendDialogueOptions(
                player,
                "What would you like to say?",
                "How are things going for the Colony?",
                "What are you doing?",
                "You know all your doors get stuck open?",
                "Nothing, I'm fine."
            ).also { stage++ }

            10 -> when (buttonId) {
                1 -> player("How are things going for the Colony?").also { stage++ }
                2 -> player("What are you doing?").also { stage = 17 }
                3 -> player("You know all your doors get stuck open?").also { stage = 23 }
                4 -> player("Nothing, I'm fine.").also { stage = 26 }
            }

            11 -> playerl(
                FacialExpression.HALF_ASKING,
                "How are things going for the Colony now the trolls have all gone away?"
            ).also { stage++ }

            12 -> npcl(
                FacialExpression.FRIENDLY,
                "Well, it's been fairly quiet around here, but there's always repair work to keep us busy."
            ).also { stage++ }

            13 -> player("Catching plenty of fish?").also { stage++ }
            14 -> npcl(
                FacialExpression.FRIENDLY,
                "Oh yes, lots of them. Their population's been swelling ever since you wiped out the trolls in the area. They've hardly got any natural predators now."
            ).also { stage++ }

            15 -> player("Oh... I suppose that's good...").also { stage++ }
            16 -> end().also { stage = END_DIALOGUE }

            17 -> player("I was just wondering what you're doing.").also { stage++ }
            18 -> npcl(
                FacialExpression.FRIENDLY,
                "Franklin's got me fixing this wall. You did a pretty good patch-job, but it could do with some work."
            ).also { stage++ }

            19 -> playerl(
                FacialExpression.FRIENDLY,
                "I'm not surprised - iron sheets won't last any time at all with all that salt water around."
            ).also { stage++ }

            20 -> npcl(
                FacialExpression.FRIENDLY,
                "Yes, that's right. I'll probably get Arnold to order some fancy protective paint from the dwarves."
            ).also { stage++ }

            21 -> player(FacialExpression.FRIENDLY, "Good luck with that.").also { stage++ }
            22 -> end().also { stage = END_DIALOGUE }

            23 -> npcl(
                FacialExpression.FRIENDLY,
                "Yes, the salt does terrible things to the hinges. I've been meaning to deal with them, but I never seem to get around to it."
            ).also { stage++ }

            24 -> player("Fair enough.").also { stage++ }
            25 -> end().also { stage = END_DIALOGUE }

            26 -> npcl(FacialExpression.FRIENDLY, "Enjoy your fishing.").also { stage++ }
            27 -> end().also { stage = END_DIALOGUE }

        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DEVIN_MENDELBERG_3825, 3828)
    }

}
