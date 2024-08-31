package content.global.dialogue

import cfg.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC

/**
 * Represents the Zombie head dialogue.
 */
class ZombieHeadDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.ZOMBIE_HEAD_2868)
        when (stage) {
            0 -> options("How did you die?", "What is your name?", "Can you do any tricks?", "Want a new hat?", "Want to go scare some people?",).also { stage++ }
            1 -> when (buttonID) {
                1 -> playerl(FacialExpression.ASKING, "Hey, Head?").also { stage = 2 }
                2 -> playerl(FacialExpression.ASKING, "Hey, Head?").also { stage = 7 }
                3 -> playerl(FacialExpression.ASKING, "Hey, Head?").also { stage = 15 }
                4 -> playerl(FacialExpression.ASKING, "Hey, Head?").also { stage = 27 }
                5 -> playerl(FacialExpression.ASKING, "Hey, Head?").also { stage = 33 }
            }

            2 -> npcl(FacialExpression.FRIENDLY, "What?").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "How did you die?").also { stage++ }
            4 -> npcl(FacialExpression.FRIENDLY, "I stuck my neck out for an old friend.").also { stage++ }
            5 -> playerl(FacialExpression.FRIENDLY, "You shouldn't get so cut up about it.").also { stage++ }
            6 -> npcl(FacialExpression.FRIENDLY, "Well if I keep it all bottled up I'll turn into a total head case.").also { stage = 0 }
            7 -> npcl(FacialExpression.ANNOYED, "What?").also { stage++ }
            8 -> playerl(FacialExpression.FRIENDLY, "What is your name?").also { stage++ }
            9 -> npcl(FacialExpression.FRIENDLY, "Mumblemumblemumble...").also { stage++ }
            10 -> playerl(FacialExpression.HALF_ASKING, "What was that?").also { stage++ }
            11 -> npcl(FacialExpression.ANNOYED, "My name is Edward Cranium.").also { stage++ }
            12 -> playerl(FacialExpression.HALF_ASKING, "Edd Cranium?").also { stage++ }
            13 -> playerl(FacialExpression.LAUGH, "Hahahahahahahahahahaha!").also { stage++ }
            14 -> npcl(FacialExpression.FRIENDLY, "Har har...").also { stage = 0 }

            15 -> npcl(FacialExpression.ANGRY_WITH_SMILE, "What now?").also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "Can you do any tricks?").also { stage++ }
            17 -> npcl(FacialExpression.ANNOYED, "Not any more...").also { stage++ }
            18 -> playerl(FacialExpression.HALF_ASKING, "How come?").also { stage++ }
            19 -> npcl(FacialExpression.ANNOYED, "Because I used to be able to do a handstand for over an hour while juggling cannon balls with my feet...").also { stage++ }

            20 -> playerl(FacialExpression.FRIENDLY, "Wow, you were quite the entertainer.").also { stage++ }
            21 -> npcl(FacialExpression.ANNOYED, "Yep. Now I can barely roll my eyes...").also { stage++ }
            22 -> playerl(FacialExpression.FRIENDLY, "I know what you can do!").also { stage++ }
            23 -> npcl(FacialExpression.HALF_ASKING, "What?").also { stage++ }
            24 -> playerl(FacialExpression.FRIENDLY, "Vent...").also { stage++ }
            25 -> npcl(FacialExpression.ANNOYED, "Don't even suggest it!").also { stage++ }
            26 -> playerl(FacialExpression.FRIENDLY, "Ok.").also { stage = 0 }

            27 -> npcl(FacialExpression.ANGRY, "Can't I rest in peace?").also { stage++ }
            28 -> playerl(FacialExpression.FRIENDLY, "No!").also { stage++ }
            29 -> playerl(FacialExpression.HALF_ASKING, "Would you like a new hat?").also { stage++ }
            30 -> npcl(FacialExpression.ANGRY, "No, but could you screw a handle into the top of my head?").also { stage++ }

            31 -> playerl(FacialExpression.FRIENDLY, "A handle? Why?").also { stage++ }
            32 -> npcl(FacialExpression.ANNOYED, "Because currently you wave me about by my hair, and it hurts.").also { stage = 0 }

            33 -> npcl(FacialExpression.ROLLING_EYES, "Will you ever leave me alone?").also { stage++ }
            34 -> playerl(FacialExpression.HAPPY, "No!").also { stage++ }
            35 -> playerl(FacialExpression.HALF_ASKING, "Want to go scare some people?").also { stage++ }
            36 -> npcl(FacialExpression.OLD_NOT_INTERESTED, "Let's leave it for now.").also { stage++ }
            37 -> playerl(FacialExpression.ANNOYED, "All right...").also { stage++ }
            38 -> playerl(FacialExpression.ANNOYED, "We'll quit while we're ahead!").also { stage++ }
            39 -> npcl(FacialExpression.ANNOYED, "If I try really hard I might be able to will myself deader...").also { stage = 0 }
        }
    }

}