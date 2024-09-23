package content.global.skill.construction.decoration.skillhall.dialogue

import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs

/**
 * Kurask head trophy dialogue.
 */
class KuraskHeadDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.KURASK_4229)
        when (stage) {
            0 -> npcl(FacialExpression.CHILD_FRIENDLY, "I KILL YOU!!!").also { stage++ }
            1 -> if (!player!!.houseManager.isInHouse(player!!)) {
                player(FacialExpression.FRIENDLY, "No, House owner kill you!").also { stage = END_DIALOGUE }
            } else {
                player(FacialExpression.FRIENDLY, "No, I kill you!").also { stage++ }
            }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "UUUHRG! Now I kill you!").also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "How are you going to do that? You're just a head on a wall!").also { stage++ }
            4 -> npcl(FacialExpression.CHILD_FRIENDLY, "Uhhhhrrr...").also { stage++ }
            5 -> options("Why are you so violent?", "What do you think about up there?", "I killed you really easily!").also { stage++ }
            6 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "Why are you so violent?").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "What do you think about up there?").also { stage = 23 }
                3 -> playerl(FacialExpression.HAPPY, "I killed you really easily!").also { stage = 18 }
            }
            7 -> npcl(FacialExpression.CHILD_FRIENDLY, "You kill me! Uuurgh! That make me angry!").also { stage++ }
            8 -> options("You seemed pretty angry before I killed you.", "I'm sorry I killed you.", "I killed you really easily!").also { stage++ }
            9 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "You seemed pretty angry before I killed you.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "I'm sorry I killed you.").also { stage = 11 }
                3 -> playerl(FacialExpression.HAPPY, "I killed you really easily!").also { stage = 18 }
            }
            10 -> npcl(FacialExpression.CHILD_FRIENDLY, "I like angry!").also { stage = END_DIALOGUE }
            11 -> npcl(FacialExpression.CHILD_FRIENDLY, "I hate sorry! Makes me more angry! WANT TO KILL YOU!").also { stage++ }
            12 -> options("Please try to calm down.", "I'm not really sorry.").also { stage++ }
            13 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "Please try to calm down!").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "I'm not really sorry!").also { stage = 15 }
            }
            14 -> npcl(FacialExpression.CHILD_FRIENDLY, "Hate calm! Smash it! Hur hur hur!").also { stage = END_DIALOGUE }
            15 -> npcl(FacialExpression.CHILD_FRIENDLY, "That make me more angry! Uuuurgh!").also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "Is there anything that doesn't make you angry?").also { stage++ }
            17 -> npcl(FacialExpression.CHILD_FRIENDLY, "No! I like angry! Hur hur hur!").also { stage = END_DIALOGUE }
            18 -> npcl(FacialExpression.CHILD_FRIENDLY, "Uhhhhrrr...").also { stage++ }
            19 -> playerl(FacialExpression.FRIENDLY, "Yeah! I could kill you again in my sleep! I think I might go off and kill some other kurask!").also { stage++ }
            20 -> npcl(FacialExpression.CHILD_FRIENDLY, "Uuuurrrrh! Hate you!").also { stage++ }
            21 -> playerl(FacialExpression.FRIENDLY, "What are you going to do about it? Eh? I totally owned you!").also { stage++ }
            22 -> npcl(FacialExpression.CHILD_FRIENDLY, "Hate you hate you hate you!!!").also { stage = END_DIALOGUE }
            23 -> playerl(FacialExpression.FRIENDLY, "What do you think about up there?").also { stage++ }
            24 -> npcl(FacialExpression.CHILD_FRIENDLY, "Think?").also { stage++ }
            25 -> playerl(FacialExpression.FRIENDLY, "You know, what goes through your tiny stuffed head?").also { stage++ }
            26 -> npcl(FacialExpression.CHILD_FRIENDLY, "Little bugs...").also { stage++ }
            27 -> playerl(FacialExpression.FRIENDLY, "You have bugs living in you? Eww!").also { stage++ }
            28 -> npcl(FacialExpression.CHILD_FRIENDLY, "Little bugs! Stomp and crush and stomp!").also { stage++ }
            29 -> options("Yeah! Stomp the bugs!", "What have the bugs done to you?", "You can't, you've got no feet!").also { stage++ }
            30 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "Yeah! Stomp the bugs!").also { stage++ }
                2 -> player(FacialExpression.FRIENDLY, "What have the bugs done to you?").also { stage = 38 }
                3 -> player(FacialExpression.FRIENDLY, "You can't, you've got no feet!").also { stage = 40 }
            }
            31 -> npcl(FacialExpression.CHILD_FRIENDLY, "Stomp crush splat!").also { stage++ }
            32 -> npcl(FacialExpression.CHILD_FRIENDLY, "Smash! Destroy! Crunch break tear destroy splunch! Hurt wound kill hit punch stab slash kill!").also { stage++ }
            33 -> options("'Splunch'? That's not a word!", "You said 'kill' twice.", "Yeah! Kill smash destroy!").also { stage++ }
            34 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "'Splunch'? That's not a word!").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "You said 'kill' twice.").also { stage = 36 }
                3 -> player(FacialExpression.FRIENDLY, "Yeah! Kill smash destroy!").also { stage = 37 }
            }
            35 -> npcl(FacialExpression.CHILD_FRIENDLY, "I HATE WORDS! Kill all words!").also { stage = END_DIALOGUE }
            36 -> npcl(FacialExpression.CHILD_FRIENDLY, "I like kill! Hur hur hur hur!").also { stage = END_DIALOGUE }
            37 -> npcl(FacialExpression.CHILD_FRIENDLY, "Kill smash destroy! Hur hur hur!").also { stage = END_DIALOGUE }
            38 -> npcl(FacialExpression.CHILD_FRIENDLY, "Skitter skitter through head noise in ears behind eyes.").also { stage++ }
            39 -> npcl(FacialExpression.CHILD_FRIENDLY, "HATE THEM! Kill kill kill!").also { stage = END_DIALOGUE }
            40 -> playerl(FacialExpression.FRIENDLY, "You can't, you've got no feet!").also { stage++ }
            41 -> npcl(FacialExpression.CHILD_FRIENDLY, "No feet...").also { stage++ }
            42 -> npcl(FacialExpression.CHILD_FRIENDLY, "Hate lack of feet! Stomp lack of feet! Kill crush destroy smash!").also { stage++ }
            43 -> playerl(FacialExpression.FRIENDLY, "That makes no sense! You can't destroy the absence of something!").also { stage++ }
            44 -> npcl(FacialExpression.CHILD_FRIENDLY, "Hate requirement to make sense! Smash it kill it destroy kill kill!").also { stage++ }
            45 -> playerl(FacialExpression.FRIENDLY, "You can't physically destroy an abstract concept! It's impossible!").also { stage++ }
            46 -> npcl(FacialExpression.CHILD_FRIENDLY, "Hate abstract concepts! Hate impossible! Kill kill kill destroy smash!").also { stage++ }
            47 -> playerl(FacialExpression.FRIENDLY, "This is getting both surreal and repetitive.").also { stage = END_DIALOGUE }
        }
    }
}