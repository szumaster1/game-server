package content.global.skill.combat.summoning.familiar.dialogue

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Stranger plant dialogue.
 */
@Initializable
class StrangerPlantDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when ((0..3).random()) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "I'M STRANGER PLANT!").also { stage = 0 }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "WILL WE HAVE TO BE HERE LONG?").also { stage = 5 }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "DIIIIVE!").also { stage = 16 }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "I THINK I'M WILTING!").also { stage = 21 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when(stage) {
            0 -> playerl(FacialExpression.FRIENDLY, "I know you are.").also { stage++ }
            1 -> npcl(FacialExpression.CHILD_NORMAL, "I KNOW! I'M JUST SAYING!").also { stage++ }
            2 -> playerl(FacialExpression.HALF_ASKING, "Do you have to shout like that all of the time?").also { stage++ }
            3 -> npcl(FacialExpression.CHILD_NORMAL, "WHO'S SHOUTING?").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "If this is you speaking normally, I'd hate to hear you shouting.").also { stage++ }

            5 -> npcl(FacialExpression.CHILD_NORMAL, "OH, SNAP!").also { stage = END_DIALOGUE }
            6 -> playerl(FacialExpression.FRIENDLY, "We'll be here until I am finished.").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_NORMAL, "BUT THERE'S NO DRAMA HERE!").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "Well, how about you pretend to be an undercover agent.").also { stage++ }
            9 -> npcl(FacialExpression.CHILD_NORMAL, "WONDERFUL! WHAT'S MY MOTIVATION?").also { stage++ }
            10 -> playerl(FacialExpression.FRIENDLY, "You're trying to remain stealthy and secretive, while looking out for clues.").also { stage++ }
            11 -> npcl(FacialExpression.CHILD_NORMAL, "I'LL JUST GET INTO CHARACTER! AHEM!").also { stage++ }
            12 -> npcl(FacialExpression.CHILD_NORMAL, "PAPER! PAPER! VARROCK HERALD FOR SALE!").also { stage++ }
            13 -> playerl(FacialExpression.HALF_ASKING, "What kind of spy yells loudly like that?").also { stage++ }
            14 -> npcl(FacialExpression.CHILD_NORMAL, "ONE WHOSE COVER IDENTITY IS A PAPER-SELLER, OF COURSE!").also { stage++ }
            15 -> playerl(FacialExpression.FRIENDLY, "Ask a silly question...").also { stage = END_DIALOGUE }

            16 -> playerl(FacialExpression.HALF_ASKING, "What? Help! Why dive?").also { stage++ }
            17 -> npcl(FacialExpression.CHILD_NORMAL, "OH, DON'T WORRY! I JUST LIKE TO YELL THAT FROM TIME TO TIME!").also { stage++ }
            18 -> playerl(FacialExpression.HALF_ASKING, "Well, can you give me a little warning next time?").also { stage++ }
            19 -> npcl(FacialExpression.CHILD_NORMAL, "WHAT, AND TAKE ALL THE FUN OUT OF LIFE?").also { stage++ }
            20 -> playerl(FacialExpression.FRIENDLY, "If by 'fun' you mean 'sudden heart attacks', then yes, please take them out of my life!").also { stage = END_DIALOGUE }

            21 -> playerl(FacialExpression.HALF_ASKING, "Do you need some water?").also { stage++ }
            22 -> npcl(FacialExpression.CHILD_NORMAL, "DON'T BE SILLY! I CAN PULL THAT OUT OF THE GROUND!").also { stage++ }
            23 -> playerl(FacialExpression.HALF_ASKING, "Then why are you wilting?").also { stage++ }
            24 -> npcl(FacialExpression.CHILD_NORMAL, "IT'S SIMPLE: THERE'S A DISTINCT LACK OF DRAMA!").also { stage++ }
            25 -> playerl(FacialExpression.HALF_ASKING, "Drama?").also { stage++ }
            26 -> npcl(FacialExpression.CHILD_NORMAL, "YES, DRAMA!").also { stage++ }
            27 -> playerl(FacialExpression.FRIENDLY, "Okay...").also { stage++ }
            28 -> playerl(FacialExpression.FRIENDLY, "Let's see if we can find some for you.").also { stage++ }
            29 -> npcl(FacialExpression.CHILD_NORMAL, "LEAD ON!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.STRANGER_PLANT_6827, NPCs.STRANGER_PLANT_6828)
    }

}
