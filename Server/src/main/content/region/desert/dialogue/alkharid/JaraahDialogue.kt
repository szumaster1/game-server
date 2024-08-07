package content.region.desert.dialogue.alkharid

import core.api.animate
import core.api.consts.Animations
import core.api.consts.NPCs
import core.api.getStatLevel
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Jaraah dialogue.
 */
@Initializable
class JaraahDialogue(player: Player? = null) : Dialogue(player) {

    /*
        He can be found in a building in northern Al'Kharid.
        He, along with Jaraah.
        Can heal players for free, restoring life points.
        Location: 3362,3276
    */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        player(FacialExpression.FRIENDLY, "Hi!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.ANNOYED, "What? Can't you see I'm busy?!").also { stage++ }
            1 -> showTopics(
                Topic("Can you heal me?", 101),
                Topic("You must see some gruesome things?", 201),
                Topic("Why do they call you 'The Butcher'?", 301)
            )
            101 -> {
                end()
                animate(npc!!, Animations.HUMAN_PICKPOCKETING_881)
                if (player!!.skills.lifepoints < getStatLevel(player!!, Skills.HITPOINTS)) {
                    player!!.skills.heal(21)
                    npcl(FacialExpression.FRIENDLY, "There you go!")
                } else {
                    npcl(FacialExpression.FRIENDLY, "Okay, this will hurt you more than it will me.")
                }
            }
            201 -> npcl(FacialExpression.FRIENDLY, "It's a gruesome business and with the tools they give me it gets mroe gruesome before it gets better!").also { stage = END_DIALOGUE }
            301 -> npcl(FacialExpression.HALF_THINKING, "'The Butcher'?").also { stage++ }
            302 -> npcl(FacialExpression.LAUGH, "Ha!").also { stage++ }
            303 -> npcl(FacialExpression.HALF_ASKING, "Would you like me to demonstrate?").also { stage++ }
            304 -> player(FacialExpression.AFRAID, "Er...I'll give it a miss, thanks.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JARAAH_962)
    }
}
