package content.location.alkharid

import core.api.animate
import cfg.consts.Animations
import core.api.getStatLevel
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.skill.Skills
import core.tools.END_DIALOGUE

/**
 * Represents the Al kharid heal dialogue.
 */
class AlKharidHealDialogue(val skipFirst: Boolean) : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        if (stage == 0 && skipFirst) stage++
        when (stage) {
            0 -> playerl(FacialExpression.ASKING, "Can you heal me?").also { stage++ }
            1 -> npcl(FacialExpression.FRIENDLY, "Of course!").also { stage++ }
            2 -> {
                animate(npc!!, Animations.HUMAN_PICKPOCKETING_881)
                if (player!!.skills.lifepoints < getStatLevel(player!!, Skills.HITPOINTS)) {
                    player!!.skills.heal(21)
                    npcl(FacialExpression.FRIENDLY, "There you go!")
                } else {
                    npcl(FacialExpression.FRIENDLY, "You look healthy to me!")
                }
                stage = END_DIALOGUE
            }
        }
    }
}
