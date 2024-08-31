package content.region.asgarnia.burthope.dialogue

import core.api.*
import cfg.consts.Animations
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE

/**
 * Represents the Harold dialogue.
 */
@Initializable
class HaroldDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        if (isQuestInProgress(player!!, "Death Plateau", 10, 29)) {
            openDialogue(player!!, HaroldDialogue(), npc)
        }

        when (stage) {
            START_DIALOGUE -> player(FacialExpression.FRIENDLY, "Hello there.").also { stage++ }
            1 -> npc(FacialExpression.FRIENDLY, "Hi.").also { stage++ }
            2 -> player(FacialExpression.FRIENDLY, "Can I buy you a drink?").also { stage++ }
            3 -> npc(FacialExpression.HAPPY, "Now you're talking! An Asgarnian Ale, please!").also { stage++ }
            4 -> {
                if (!removeItem(player!!, Items.ASGARNIAN_ALE_1905)) {
                    player(FacialExpression.FRIENDLY, "I'll go and get you one.").also { stage = END_DIALOGUE }
                } else {
                    sendMessage(player!!, "You give Harold an Asgarnian Ale.")
                    sendItemDialogue(player!!, Items.ASGARNIAN_ALE_1905, "You give Harold an Asgarnian Ale.").also { stage++ }
                }
            }
            5 -> {
                end()
                animate(npc!!, Animation(Animations.HUMAN_EATTING_829), true)
                runTask(npc!!, 3) {
                    npc(FacialExpression.FRIENDLY, "*burp*").also { stage = END_DIALOGUE }
                }
            }
        }
        return true
    }


    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HAROLD_1078)
    }
}
