package content.global.skill.construction.decoration.skillhall.dialogue

import core.api.sendDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import core.tools.RandomFunction
import org.rs.consts.NPCs

/**
 * Basilisk head trophy dialogue.
 */
class BasiliskHeadDialogue : DialogueFile() {

    private val randomWord_0 = RandomFunction.getRandomElement(arrayOf("boring", "fat", "hideous", "puny", "smelly", "stupid"))
    private val randomWord_1 = RandomFunction.getRandomElement(arrayOf("beetle", "chicken", "egg", "mud", "slime", "worm"))
    private val randomWord_2 = RandomFunction.getRandomElement(arrayOf("brained", "eating", "like", "loving", "smelling", "witted"))
    private val randomWord_3 = RandomFunction.getRandomElement(arrayOf("basilisk", "idiot", "lizard", "mudworm", "slimeball", "weakling"))

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.BASILISK_4228)
        when (stage) {
            0 -> npcl(FacialExpression.CHILD_FRIENDLY, "What do you want?").also { stage++ }
            1 -> if (!player!!.houseManager.isInHouse(player!!)) {
                playerl(FacialExpression.AFRAID, "Oh, er, nothing!").also { stage = END_DIALOGUE }
            } else {
                options("I want to mock you.", "I want to apologise for killing you.", "I just wanted to check that you're okay.", "Nothing.").also { stage++ }
            }

            2 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "I want to mock you.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "I want to apologise for killing you.").also { stage = 6 }
                3 -> playerl(FacialExpression.FRIENDLY, "I just wanted to check that you're okay.").also { stage = 30 }
                4 -> playerl(FacialExpression.FRIENDLY, "Nothing.").also { stage = END_DIALOGUE }
            }
            3 -> npcl(FacialExpression.CHILD_FRIENDLY, "All right. Go on then.").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "You're a $randomWord_0 $randomWord_1 $randomWord_2 $randomWord_3.").also { stage++ }
            5 -> npcl(FacialExpression.CHILD_SAD, "I'm going back to sleep.").also { stage = END_DIALOGUE }
            6 -> npcl(FacialExpression.CHILD_FRIENDLY, "Go on then.").also { stage++ }
            7 -> playerl(FacialExpression.FRIENDLY, "I'm, um, very sorry I killed you.").also { stage++ }
            8 -> npcl(FacialExpression.CHILD_FRIENDLY, "Really sorry?").also { stage++ }
            9 -> options("No, not really.", "Yes, really.").also { stage++ }
            10 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "No, not really!").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Yes really!").also { stage = 12 }
            }
            11 -> npcl(FacialExpression.CHILD_NORMAL, "I don't care.").also { stage = END_DIALOGUE }
            12 -> npcl(FacialExpression.CHILD_FRIENDLY, "Really really?").also { stage++ }
            13 -> options("Yes, really really.", "Don't push it.").also { stage++ }
            14 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "Yes, really really!").also { stage = 16 }
                2 -> playerl(FacialExpression.FRIENDLY, "Don't push it!").also { stage++ }
            }
            15 -> npcl(FacialExpression.CHILD_FRIENDLY, "I don't care anyway.").also { stage = END_DIALOGUE }
            16 -> npcl(FacialExpression.CHILD_FRIENDLY, "Fat lot of good that does, I'm still dead.").also { stage++ }
            17 -> options("I'm not THAT sorry.", "But will you forgive me?", "I promise not to do it again.").also { stage++ }
            18 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "I'm not THAT sorry!").also { stage = 11 }
                2 -> playerl(FacialExpression.FRIENDLY, "But will you forgive me?").also { stage = 19 }
                3 -> playerl(FacialExpression.FRIENDLY, "I promise not to do it again.").also { stage = 22 }
            }
            19 -> npcl(FacialExpression.CHILD_FRIENDLY, "Of course I'll forgive you!").also { stage++ }
            20 -> playerl(FacialExpression.FRIENDLY, "Really?").also { stage++ }
            21 -> npcl(FacialExpression.CHILD_FRIENDLY, "No!").also { stage = END_DIALOGUE }
            22 -> npcl(FacialExpression.CHILD_FRIENDLY, "Of course you won't do it again, you can only kill me once.").also { stage++ }
            23 -> options("That's why I won't do it again.", "But I won't do it to any other basilisks.").also { stage++ }
            24 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "That's why I won't do it again! There's be no point!").also { stage = 15 }

                2 -> playerl(FacialExpression.FRIENDLY, "But I won't do it to any other basilisks!").also { stage++ }
            }
            25 -> npcl(FacialExpression.CHILD_FRIENDLY, "Really?").also { stage++ }
            26 -> options("Yes, really!", "No, not really!", "Don't start that again!").also { stage++ }
            27 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "Yes, really!").also { stage = 29 }
                2 -> playerl(FacialExpression.FRIENDLY, "No, not really!").also { stage = 11 }
                3 -> player(FacialExpression.FRIENDLY, "Don't start that again!").also { stage++ }
            }
            28 -> npcl(FacialExpression.CHILD_FRIENDLY, "Leave me alone then.").also { stage = END_DIALOGUE }
            29 -> npcl(FacialExpression.CHILD_FRIENDLY, "All right then. Apology accepted. Now leave me alone.").also { stage = END_DIALOGUE }
            30 -> npcl(FacialExpression.CHILD_FRIENDLY, "Apart from being dead and stuffed and hanging on a wall, you mean?").also { stage++ }
            31 -> playerl(FacialExpression.FRIENDLY, "Uh... yeah, apart from that are you okay?").also { stage++ }
            32 -> npcl(FacialExpression.CHILD_FRIENDLY, "Actually there's something blocking my view of the far wall.").also { stage++ }
            33 -> playerl(FacialExpression.FRIENDLY, "I don't see anything.").also { stage++ }
            34 -> npcl(FacialExpression.CHILD_FRIENDLY, "Perhaps if you were to move to one side of me.").also { stage++ }
            35 -> sendDialogue(player!!, "You walk to the side of the basilisk head...").also { stage++ }
            36 -> playerl(FacialExpression.FRIENDLY, "I still don't see anything.").also { stage++ }
            37 -> npcl(FacialExpression.CHILD_FRIENDLY, "Oh, it's moved away. I can see now.").also { stage = END_DIALOGUE }
        }
    }
}