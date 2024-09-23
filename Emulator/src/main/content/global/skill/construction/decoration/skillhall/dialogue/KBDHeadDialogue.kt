package content.global.skill.construction.decoration.skillhall.dialogue

import core.api.sendNPCDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.world.GameWorld
import core.tools.END_DIALOGUE
import org.rs.consts.NPCs

/**
 * King black dragon head trophy dialogue.
 */
class KBDHeadDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> if (!player!!.houseManager.isInHouse(player!!)) {
                playerl(FacialExpression.AFRAID, "Hey, House owner killed the King Black Dragon!").also { stage = 23 }
            } else {
                playerl(FacialExpression.FRIENDLY, "How do you feel about all the more powerful monsters?").also { stage++ }
            }
            1 -> sendNPCDialogue(player!!, NPCs.LEFT_HEAD_4231, "There no monsters more powerful than us!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            2 -> sendNPCDialogue(player!!, NPCs.MIDDLE_HEAD_4232, "We top monster of all ${GameWorld.settings!!.name}!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            3 -> playerl(FacialExpression.FRIENDLY, "No you're not. The Kalphite Queen is more powerful than you!").also { stage++ }
            4 -> sendNPCDialogue(player!!, NPCs.MIDDLE_HEAD_4232, "Kalphite Queen? What that?", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            5 -> playerl(FacialExpression.FRIENDLY, "She's a giant insect who lives in the desert.").also { stage++ }
            6 -> sendNPCDialogue(player!!, NPCs.MIDDLE_HEAD_4232, "Insect?", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            7 -> sendNPCDialogue(player!!, NPCs.RIGHT_HEAD_4233, "Ha ha ha ha!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            8 -> sendNPCDialogue(player!!, NPCs.LEFT_HEAD_4231, "No insect tougher than us! We best!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            9 -> playerl(FacialExpression.FRIENDLY, "No, she's way tougher than you!").also { stage++ }
            10 -> sendNPCDialogue(player!!, NPCs.LEFT_HEAD_4231, "We no believe it!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            11 -> sendNPCDialogue(player!!, NPCs.MIDDLE_HEAD_4232, "Even if Kalphite Queen real, which we doubt, second best not bad, is it?", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            12 -> playerl(FacialExpression.FRIENDLY, "But it's not just the Kalphite Queen. What about TzTok-Jad?").also { stage++ }
            13 -> sendNPCDialogue(player!!, NPCs.LEFT_HEAD_4231, "Never heard of it!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            14 -> playerl(FacialExpression.FRIENDLY, "Or Dagannoth Rex?").also { stage++ }
            15 -> sendNPCDialogue(player!!, NPCs.RIGHT_HEAD_4233, "You making it up!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            16 -> playerl(FacialExpression.FRIENDLY, "Or the Chaos Elemental?").also { stage++ }
            17 -> sendNPCDialogue(player!!, NPCs.MIDDLE_HEAD_4232, "Now then, how we know you not just making these monsters up to demoralise us?", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            18 -> playerl(FacialExpression.FRIENDLY, "Alright, then, what about me?").also { stage++ }
            19 -> sendNPCDialogue(player!!, NPCs.LEFT_HEAD_4231, "Puny human! You not fearsome monster!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            20 -> playerl(FacialExpression.FRIENDLY, "I defeated you, didn't I? So, I must be stronger than you!").also { stage++ }
            21 -> sendNPCDialogue(player!!, NPCs.LEFT_HEAD_4231, "You got lucky! We get you next time!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            22 -> playerl(FacialExpression.FRIENDLY, "Now that you're just a stuffed head? I don't think so!").also { stage = END_DIALOGUE }
            23 -> sendNPCDialogue(player!!, NPCs.MIDDLE_HEAD_4232, "No, he didn't?", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            24 -> sendNPCDialogue(player!!, NPCs.LEFT_HEAD_4231, "What? Oh, ah, no. Course he didn't. We actually artificial likeness of King Black Dragon. No one could really kill King Black Dragon!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            25 -> sendNPCDialogue(player!!, NPCs.MIDDLE_HEAD_4232, "No! We...no, it far too powerful!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            26 -> sendNPCDialogue(player!!, NPCs.RIGHT_HEAD_4233, "What are you talking about? Of course we King Black Dragon!", FacialExpression.CHILD_FRIENDLY).also { stage++ }
            27 -> sendNPCDialogue(player!!, NPCs.MIDDLE_HEAD_4232, "Shut up, idiot!", FacialExpression.CHILD_FRIENDLY).also { stage = END_DIALOGUE }
        }
    }
}