package content.region.kandarin.quest.fightarena.dialogue

import core.api.consts.NPCs
import core.api.getQuestStage
import core.api.sendNPCDialogue
import core.api.sendPlayerDialogue
import core.api.setQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Jeremy servil dialogue file.
 */
class JeremyServilDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.JEREMY_SERVIL_266)
        when (getQuestStage(player!!, "Fight Arena")) {
            in 1..84 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Don't worry, I'll get us out of here.").also { stage++ }
                1 -> npcl(FacialExpression.CHILD_NORMAL, "Thanks, traveller. I'm sorry that you too are a subject of this arena.").also { stage = END_DIALOGUE }
            }
            in 85..97 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "You and you father can return to Lady Servil.").also { stage++ }
                1 -> npcl(FacialExpression.CHILD_NORMAL, "Thank you, we are truly indebted to you.").also { stage++ }
                2 -> {
                    end()
                    setQuestStage(player!!, "Fight Arena", 99)
                }
            }
            98 -> when (stage) {
                0 -> sendPlayerDialogue(player!!, "Khazard is dead, you and you father can return to Lady Servil.").also { stage++ }
                1 -> npcl(FacialExpression.CHILD_NORMAL, "Thank you, we are truly indebted to you.").also { stage++ }
                2 -> {
                    end()
                    setQuestStage(player!!, "Fight Arena", 99)
                }
            }
            100 -> when (stage) {
                0 -> playerl(FacialExpression.FRIENDLY, "Hiya kiddo! You're looking a lot happier now.").also { stage++ }
                1 -> npcl(FacialExpression.CHILD_NORMAL, "Thank you " + (if (player!!.isMale) "Sir" else "Madam") + " I saw you fighting in the arena. You were brilliant! You were like POW! and then you got hit, but you were like too hard and didn't care and then you like hit him again and KERPLOW! Then the big scary dog came out, but you weren't scared and you like smacked him as well, and then you...").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "Jeremy, calm down. Player doesn't need to hear everything " + (if (player!!.isMale) "He" else "She") + " did. " + (if (player!!.isMale) "He" else "She") + " was there when it happened, remember.").also { stage++ }
                3 -> sendNPCDialogue(player!!, NPCs.LADY_SERVIL_264, "I was rather enjoying it, actually.").also { stage++ }
                4 -> npcl(FacialExpression.CHILD_NORMAL, "Yeah, but mum... ${player!!.username} was so cool. " + (if (player!!.isMale) "He" else "She") + " was like BAM! BASH! SPLAT! When I grow up, I wanna be a hero as well.").also { stage++ }
                5 -> playerl(FacialExpression.FRIENDLY, "I'm sure you will be. You showed a lot of bravery in the arena, young lad. If you work hard to complete your squire training, you'll be a worthy knight. Trust me, I can tell these things.").also { stage++ }
                6 -> npcl(FacialExpression.CHILD_NORMAL, "Yeah! I'm gonna be the bravest knight in the world! After you, of course.").also { stage++ }
                7 -> sendNPCDialogue(player!!, NPCs.JUSTIN_SERVIL_267, "Thank you again, ${player!!.username}. You have saved my family and given young Jeremy here a new hero.").also { stage++ }
                8 -> playerl(FacialExpression.FRIENDLY, "I'm glad I could help. Have a good journey home and farewell.").also { stage++ }
                9 -> npcl(FacialExpression.CHILD_NORMAL, "See ya!").also { stage = END_DIALOGUE }
            }
        }
    }

}
