package content.region.karamja.quest.monkeymadness.dialogue

import core.api.addItem
import core.api.consts.Items
import core.api.sendItemDialogue
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression

/**
 * Represents the Garkor final battle dialogue.
 */
class GarkorFinalBattleDialogue: DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> playerl(FacialExpression.WORRIED, "What shall we do?").also { stage++ }
            1 -> npcl(FacialExpression.NEUTRAL, "Zooknock and I have come up with a plan.").also { stage++ }
            2 -> playerl(FacialExpression.NEUTRAL, "What kind of plan?").also { stage++ }
            3 -> npcl(FacialExpression.NEUTRAL, "I hope you were listening closely. The teleportation spell that was provided will teleport ALL of the 10th squad, no matter where we may be.").also { stage++ }
            4 -> npcl(FacialExpression.NEUTRAL, "In effect, the spell will break Lumo, Bunkdo and Carado out of the jail for us.").also { stage++ }
            5 -> playerl(FacialExpression.NEUTRAL, "But you will be teleported straight into whatever trap they have prepared!").also { stage++ }
            6 -> npcl(FacialExpression.NEUTRAL, "Indeed. This is where you come in. Do not forget that we are the 10th squad of the Royal Guard and that we are more than capable of holding our own.").also { stage++ }
            7 -> npcl(FacialExpression.NEUTRAL, "With your assistance, we should be able to defeat whatever is thrown at us.").also { stage++ }
            8 -> playerl(FacialExpression.NEUTRAL, "But how will i join you?").also { stage++ }
            9 -> npcl(FacialExpression.NEUTRAL, "Simple. We fool the teleportation spell that you are in fact a member of our squad.").also { stage++ }
            10 -> playerl(FacialExpression.NEUTRAL, "What?").also { stage++ }
            11 -> npcl(FacialExpression.NEUTRAL, "Zooknock knows Glough's grasp of magic well. He believes that spell is linked to the sigils that all of us our squad carry").also { stage++ }
            12 -> npcl(FacialExpression.NEUTRAL, "It is these sigils that identify us as a member of the squad.").also { stage++ }
            13 -> sendItemDialogue(player!!, Items.TENTH_SQUAD_SIGIL_4035, "Garkor hands you some kind of medallion.").also {
                addItem(player!!, Items.TENTH_SQUAD_SIGIL_4035, 1)
                stage++
            }
            14 -> npcl(FacialExpression.NEUTRAL, "Welcome to the 10th squad, ${player!!.name}.").also { stage++ }
            15 -> playerl(FacialExpression.NEUTRAL, "What is it?").also { stage++ }
            16 -> npcl(FacialExpression.NEUTRAL, "It is a replica Waymottin has made of our squad sigils. If you wear that when the spell is cast, you will be summoned along with the rest of us.").also { stage++ }
            17 -> npcl(FacialExpression.NEUTRAL, "You should prepare. Collect your thoughts and belongs and then wear the sigil. Hurry, human, we do not with to enter this fight without you.").also { stage++ }
            18 -> playerl(FacialExpression.NEUTRAL, "All i have to do is to wear the sigil?").also { stage++ }
            19 -> npcl(FacialExpression.NEUTRAL, "Yes - but do not do so until you are ready.").also {
                stage = 99
            }
            99 -> end()
        }
    }
}
