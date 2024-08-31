package content.region.kandarin.quest.makinghistory.dialogue

import content.region.kandarin.quest.makinghistory.MHUtils
import core.api.*
import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Dron dialogue.
 */
@Initializable
class DronDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        if (getQuestStage(player, "Making History") >= 1 || getVarbit(player, MHUtils.DRON_PROGRESS) == 3) {
            playerl(FacialExpression.FRIENDLY, "I need to talk to you.")
            stage = 1
            return true
        } else if (getQuestStage(player, "Making History") >= 1 && getVarbit(player, MHUtils.DRON_PROGRESS) == 4) {
            npcl(FacialExpression.FRIENDLY, "You have your answers, now go away!")
            stage = END_DIALOGUE
            return true
        } else {
            end()
            sendDialogue(player, "Dron seems too busy to talk.")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            1 -> npcl(FacialExpression.HALF_ASKING, "Why should I?").also { stage++ }
            2 -> options("Erm, sorry. I don't mean to cause offence...", "Please don't hurt me.", "I'm after important answers.").also { stage++ }
            3 -> when (buttonId) {
                1 -> playerl(FacialExpression.HALF_GUILTY, "Erm, sorry. I don't mean to cause offence...").also { stage = 5 }
                2 -> playerl(FacialExpression.SCARED, "Please don't hurt me.").also { stage = END_DIALOGUE }
                3 -> playerl(FacialExpression.NEUTRAL, "I'm after important answers.").also { stage = 6 }
            }
            4 -> when ((1..2).random()) {
                3 -> npcl(FacialExpression.ANGRY, "Wrong, wrong, wrong!").also { stage = END_DIALOGUE }
                4 -> npcl(FacialExpression.ANGRY, "You know nothing!").also { stage = END_DIALOGUE }
            }
            5 -> when ((1..2).random()) {
                1 -> npcl(FacialExpression.ANGRY, "Don't waste my time!").also { stage = END_DIALOGUE }
                2 -> npcl(FacialExpression.ANGRY, "Leave me alone!").also { stage = END_DIALOGUE }
            }
            6 -> npcl(FacialExpression.HALF_ASKING, "But how do you know me?").also { stage++ }
            7 -> options("I just talked to your brother.", "Why, you're the famous warrior Dron!").also { stage++ }
            8 -> when (buttonId) {
                1 -> playerl(FacialExpression.NEUTRAL, "I just talked to your brother.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Why, you're the famous warrior Dron!").also { stage = 10 }
            }
            9 -> npcl(FacialExpression.ANNOYED, "Don't talk of my brother. He's not on my good side at the moment.").also { stage = END_DIALOGUE }
            10 -> npcl(FacialExpression.FRIENDLY, "If so, what weapon do I use?").also { stage++ }
            11 -> options("An iron axe.", "An iron mace.", "A steel mace.").also { stage++ }
            12 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "An iron axe.").also { stage = 4 }
                2 -> playerl(FacialExpression.FRIENDLY, "An iron mace.").also { stage++ }
                3 -> playerl(FacialExpression.FRIENDLY, "An steel mace.").also { stage = 4 }
            }
            13 -> npcl(FacialExpression.FRIENDLY, "When do I like to eat rats?").also { stage++ }
            14 -> options("Breakfast.", "Tea.", "Lunch.").also { stage++ }
            15 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Breakfast.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Tea.").also { stage = 4 }
                3 -> playerl(FacialExpression.FRIENDLY, "Lunch.").also { stage = 4 }
            }
            16 -> npcl(FacialExpression.FRIENDLY, "When are kittens best devoured?").also { stage++ }
            17 -> options("Tea.", "Lunch.").also { stage++ }
            18 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Tea.").also { stage = 4 }
                2 -> playerl(FacialExpression.FRIENDLY, "Lunch.").also { stage++ }
            }
            19 -> npcl(FacialExpression.FRIENDLY, "What do I usually eat for tea?").also { stage++ }
            20 -> options("Bunnies.", "Kittens.", "Puppies.").also { stage++ }
            21 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Bunnies.").also { stage = 24 }
                2 -> playerl(FacialExpression.FRIENDLY, "Kittens.").also { stage++ }
                3 -> playerl(FacialExpression.FRIENDLY, "Puppies.").also { stage = 4 }
            }
            22 -> npcl(FacialExpression.FRIENDLY, "Eww. Gross. You have some strange tastes.").also { stage++ }
            23 -> playerl(FacialExpression.FRIENDLY, "You can't lecture ME on eating habits.").also { stage = END_DIALOGUE }
            24 -> npcl(FacialExpression.FRIENDLY, "What colour spider blood tastes the best?").also { stage++ }
            25 -> options("Green.", "Red.", "Blue.").also { stage++ }
            26 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Green.").also { stage = 4 }
                2 -> playerl(FacialExpression.FRIENDLY, "Red.").also { stage++ }
                3 -> playerl(FacialExpression.FRIENDLY, "Blue.").also { stage = 4 }
            }
            27 -> npcl(FacialExpression.FRIENDLY, "and months?").also { stage++ }
            28 -> options("8.", "21.", "5.").also { stage++ }
            29 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "8.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "21.").also { stage = 4 }
                3 -> playerl(FacialExpression.FRIENDLY, "5.").also { stage = 4 }
            }
            30 -> npcl(FacialExpression.FRIENDLY, "What are the most interesting ages for battles?").also { stage++ }
            31 -> options("Fourth", "Third and Fourth", "Fifth and Fourth").also { stage++ }
            32 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Fourth").also { stage = 4 }
                2 -> playerl(FacialExpression.FRIENDLY, "Third and Fourth").also { stage = 4 }
                3 -> playerl(FacialExpression.FRIENDLY, "Fifth and Fourth").also { stage++ }
            }
            33 -> npcl(FacialExpression.FRIENDLY, "And my house is situated where?").also { stage++ }
            34 -> options("North side of the town", "Northwest side of town", "Northeast side of town").also { stage++ }
            35 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "North side of the town").also { stage = 4 }
                2 -> playerl(FacialExpression.FRIENDLY, "Northwest side of town").also { stage = 4 }
                3 -> playerl(FacialExpression.FRIENDLY, "Northeast side of town").also { stage++ }
            }
            36 -> npcl(FacialExpression.FRIENDLY, "What is my brother's name?").also { stage++ }
            37 -> options("Blanin.", "Dave.", "Blanon.").also { stage++ }
            38 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Blanin.").also { stage = 42 }
                2 -> playerl(FacialExpression.FRIENDLY, "Dave.").also { stage = 39 }
                3 -> playerl(FacialExpression.FRIENDLY, "Blanon.").also { stage = 4 }
            }
            39 -> npcl(FacialExpression.FRIENDLY, "Dev?").also { stage++ }
            40 -> playerl(FacialExpression.FRIENDLY, "No. D-A-V-E").also { stage++ }
            41 -> npcl(FacialExpression.FRIENDLY, "What a strange name. Unlike my brother's!").also { stage = END_DIALOGUE }
            42 -> npcl(FacialExpression.FRIENDLY, "And my pet cat is called?").also { stage++ }
            43 -> options("Fluffy.", "Snowy.", "Blanon.").also { stage++ }
            44 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Fluffy.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Snowy.").also { stage = 4 }
            }
            45 -> npcl(FacialExpression.FRIENDLY, "What's 5 plus 7?").also { stage++ }
            46 -> options("12.", "14.").also { stage++ }
            47 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "12, but what does that have to do with anything?.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "14.").also { stage = 4 }
            }
            48 -> npcl(FacialExpression.FRIENDLY, "Everything! Besides, it's 13!").also { stage++ }
            49 -> playerl(FacialExpression.FRIENDLY, "Er, I think you'll find it's 12.").also { stage++ }
            50 -> npcl(FacialExpression.FRIENDLY, "...").also { stage++ }
            51 -> npcl(FacialExpression.FRIENDLY, "..........").also { stage++ }
            52 -> npcl(FacialExpression.FRIENDLY, "Very well, you seem to know me quite well, I'll answer your questions best I can.").also { stage++ }
            53 -> playerl(FacialExpression.FRIENDLY, "Phew!").also { stage++ }
            54 -> npcl(FacialExpression.FRIENDLY, "Well what are they then?").also { stage++ }
            55 -> playerl(FacialExpression.FRIENDLY, "I'm trying to find out the history of the outpost near Ardougne. I was hoping you could help me.").also { stage++ }
            56 -> npcl(FacialExpression.FRIENDLY, "Let me see. Ah yes, I remember reading of a battle that took place at the outpost many years ago. Two ex-friends led forces in a small battle that ended with them pitted against each other at the top of").also { stage++ }
            57 -> npcl(FacialExpression.FRIENDLY, "the outpost as the sole survivors because of their superior strength.").also { stage++ }
            58 -> playerl(FacialExpression.FRIENDLY, "Ex-friends?").also { stage++ }
            59 -> npcl(FacialExpression.FRIENDLY, "They were once friends but a difference in their beliefs meant they fell out. One chose to follow the god Zamorak, whilst the other chose Saradomin.").also { stage++ }
            60 -> npcl(FacialExpression.FRIENDLY, "But finding themselves in this extreme situation at the top of the outpost caused them to see the errors of their ways, like the waste of life, the lost friendship and the wasted time.").also { stage++ }
            61 -> npcl(FacialExpression.FRIENDLY, "They both then decided to unite under Guthix.").also { stage++ }
            62 -> playerl(FacialExpression.FRIENDLY, "Ah, I like happy endings.").also { stage++ }
            63 -> npcl(FacialExpression.FRIENDLY, "You would.").also { stage++ }
            64 -> playerl(FacialExpression.FRIENDLY, "Thank you, I can report this back to Jorral now.").also { stage++ }
            65 -> npcl(FacialExpression.FRIENDLY, "Anything else?").also { stage++ }
            66 -> playerl(FacialExpression.FRIENDLY, "Well, I was wondering how you get the blood stains out of your clothes?").also { stage++ }
            67 -> npcl(FacialExpression.FRIENDLY, "BE GONE!").also { stage++ }
            68 -> {
                end()
                setVarbit(player, MHUtils.DRON_PROGRESS, 4, true)
                setAttribute(player, "/save:${MHUtils.ATTRIBUTE_DRON_PROGRESS}", true)
            }
            69 -> npcl(FacialExpression.FRIENDLY, "Look, I don't have time for weaklings, if you want conversation, talk to my brother Blanin!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DRON_2939)
    }

}
