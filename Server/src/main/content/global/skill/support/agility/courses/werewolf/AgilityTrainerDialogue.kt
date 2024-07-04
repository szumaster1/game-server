package content.global.skill.support.agility.courses.werewolf

import core.api.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import core.tools.RandomFunction

class AgilityTrainerDialogue : DialogueFile() {
    override fun handle(componentID: Int, buttonID: Int) {
        val stages = RandomFunction.random(0, 1)
        npc = NPC(NPCs.AGILITY_TRAINER_1664)
        if (stages == 1) {
            npcl(FacialExpression.CHILD_NORMAL, "You need to take your headgear off before you try the Deathslide, otherwise you won't be able to get a good enough grip with your teeth.").also { stage = END_DIALOGUE }
        } else {
            npcl(FacialExpression.CHILD_NORMAL, "That headgear won't help you here, human! Take it off!").also { stage = END_DIALOGUE }
        }
    }
}

class AgilityTrainerStickDialogue : DialogueFile() {
    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.AGILITY_TRAINER_1664)
        when (stage) {
            0 -> npcl(FacialExpression.CHILD_NORMAL, "Have you brought the stick yet?").also { stage++ }
            1 -> playerl(FacialExpression.ASKING, "What stick?").also { stage++ }
            2 -> npcl(FacialExpression.CHILD_NORMAL, "Come on, get round that course - I need something to chew!").also { stage = END_DIALOGUE }
        }
    }
}
