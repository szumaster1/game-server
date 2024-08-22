package content.global.skill.combat.summoning.familiar.dialogue

import cfg.consts.NPCs
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Arctic bear dialogue.
 */
@Initializable
class ArcticBearDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..4).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Crikey! We're tracking ourselves a real live one here. I call 'em 'Brighteyes'.").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Crikey! Something seems to have startled Brighteyes, here.").also { stage = 5 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "We're tracking Brighteyes here as  goes about " + if (player!!.isMale) "his" else "her" + " daily routine.").also { stage = 8 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "These little guys get riled up real easy.").also { stage = 11 }
            4 -> npcl(FacialExpression.CHILD_NORMAL, "I'm going to use this snow to blend in and get closer to this little feller.").also { stage = 12 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.HALF_ASKING, "Will you stop stalking me like that?").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "Lookit that! Something's riled this one up good and proper.").also { stage++ }
            2 -> playerl(FacialExpression.HALF_ASKING, "Who are you talking to anyway?").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "Looks like I've been spotted.").also { stage++ }
            4 -> playerl(FacialExpression.HALF_ASKING, "Did you think you didn't stand out here or something?").also { stage = END_DIALOGUE }

            5 -> playerl(FacialExpression.HALF_ASKING, "What? What's happening?").also { stage++ }
            6 -> npcl(FacialExpression.CHILD_NORMAL, "Maybe " + if (player!!.isMale) "he" else "she's" + " scented a rival.").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "I smell something, but it's not a rival.").also { stage = END_DIALOGUE }

            8 -> playerl(FacialExpression.FRIENDLY, "My name is Player, not Brighteyes!").also { stage++ }
            9 -> npcl(FacialExpression.CHILD_NORMAL, "Looks like the little critter's upset about something.").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "I wonder if he'd be quiet if I just did really boring stuff.").also { stage = END_DIALOGUE }

            11 -> playerl(FacialExpression.HALF_ASKING, "Who wouldn't be upset with a huge bear tracking along behind them, commenting on everything they do?").also { stage = END_DIALOGUE }

            12 -> playerl(FacialExpression.FRIENDLY, "I'm looking right at you. I can still see you, you know.").also { stage++ }
            13 -> npcl(FacialExpression.CHILD_NORMAL, "I don't think they can see me...").also { stage++ }
            14 -> playerl(FacialExpression.FRIENDLY, "*Siiiigh*").also { stage++ }
            15 -> npcl(FacialExpression.CHILD_NORMAL, "So, I'm gonna get a little closer and see if I can rile 'em up.").also { stage++ }
            16 -> sendDialogue(player!!, "The bear nudges you in the stomach.").also { stage++ }
            17 -> playerl(FacialExpression.FRIENDLY, "Hey!").also { stage++ }
            18 -> npcl(FacialExpression.CHILD_NORMAL, "Willya lookit that! Lookit them teeth; I'd be a goner if it got hold of me!").also { stage++ }
            19 -> playerl(FacialExpression.FRIENDLY, "You have no idea how true that is.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ARCTIC_BEAR_6839, NPCs.ARCTIC_BEAR_6840)
    }

}
