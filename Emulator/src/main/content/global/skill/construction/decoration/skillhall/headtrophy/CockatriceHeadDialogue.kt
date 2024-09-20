package content.global.skill.construction.decoration.skillhall.headtrophy

import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs

/**
 * Cockatrice head trophy dialogue.
 */
class CockatriceHeadDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.COCKATRICE_4227)
        when (stage) {
            0 -> {
                if (!player!!.houseManager.isInHouse(player!!)) {
                    player("Hey, a cockatrice!").also { stage = 100 }
                } else {
                    npcl(FacialExpression.CHILD_FRIENDLY, "You deaded me!").also { stage++ }
                }
            }

            1 -> playerl(FacialExpression.FRIENDLY, "Well, yes.").also { stage++ }
            2 -> npcl(FacialExpression.CHILD_FRIENDLY, "What did you do that for?").also { stage++ }
            3 -> options("A Slayer Master told me to.", "So I could mount your head on my wall.", "I just wanted to.").also { stage++ }

            4 -> when (buttonID) {
                1 -> playerl(FacialExpression.FRIENDLY, "A Slayer Master told me to.").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "So I could mount your head on my wall.").also { stage = 8 }
                3 -> playerl(FacialExpression.FRIENDLY, "I just wanted to.").also { stage = 11 }
            }
            5 -> npcl(FacialExpression.CHILD_FRIENDLY, "Why do the Slayer Masters all pick on poor cockatrice?").also { stage++ }
            6 -> playerl(FacialExpression.FRIENDLY, "They pick on lots of other creatures too.").also { stage++ }
            7 -> npcl(FacialExpression.CHILD_FRIENDLY, "Then mount one of them on your wall and let poor cockatrice rest in peace!").also { stage = END_DIALOGUE }
            8 -> npcl(FacialExpression.CHILD_FRIENDLY, "Another cockatrice falls victim to the dreaded mirror shield!").also { stage++ }
            9 -> playerl(FacialExpression.FRIENDLY, "Don't take it personally. You look good on my wall.").also { stage++ }
            10 -> npcl(FacialExpression.CHILD_FRIENDLY, "I don't care! I think I looked better with a body!").also { stage = END_DIALOGUE }
            11 -> npcl(FacialExpression.CHILD_FRIENDLY, "You dirty rotten swine, you!").also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "Steady on.").also { stage++ }
            13 -> npcl(FacialExpression.CHILD_FRIENDLY, "I will kill you with my paralyzing-type magic eyes look!").also { stage++ }
            14 -> npcl(FacialExpression.CHILD_FRIENDLY, "Dots appear in air between eyes and victim. Dot! Dot! Dotty!").also { stage++ }
            15 -> playerl(FacialExpression.FRIENDLY, "Er, nothing's happening...").also { stage++ }
            16 -> npcl(FacialExpression.CHILD_FRIENDLY, "Concentrates mental power. Eyes narrow beak clenches veins on head stand out.").also { stage++ }
            17 -> npcl(FacialExpression.CHILD_FRIENDLY, "Strain!").also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "You're dead, cockatrice. Your eyes are glass beads. It won't work.").also { stage++ }
            19 -> npcl(FacialExpression.CHILD_FRIENDLY, "STRA-A-AIN!").also { stage++ }
            20 -> playerl(FacialExpression.FRIENDLY, "think I'll leave it to you.").also { stage = END_DIALOGUE }
            100 -> npc(FacialExpression.CHILD_FRIENDLY, "House owner deaded me! That wasn't very nice!").also { stage = END_DIALOGUE }
        }
    }
}