package content.global.ame.kissthefrog

import core.api.lock
import core.api.queueScript
import core.api.stopExecuting
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.NPCs

/**
 * Represents the Frog princess dialogue.
 */
@Initializable
class FrogPrincessDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val event = player!!.getAttribute(FrogUtils.ATTRIBUTE_FROG_TASK_FAIL, 0)
        if (event == 0) {
            when (stage) {
                0 -> npc(FacialExpression.OLD_SAD, "${player!!.username}, you must help me! I have been turned", "into a frog by a well-meaning wizard who suffers from", "an unfortunate obsession with frogs.").also { stage++ }
                1 -> npc(FacialExpression.OLD_NORMAL, "The only thing that will restore my true form is a kiss.").also { stage++ }
                2 -> player(FacialExpression.LAUGH, "Excuses, excuses!", "Okay, if that's what you want...").also { stage++ }
                3 -> {
                    end()
                    lock(npc, 100)
                    FrogUtils.kissTheFrog(player, npc)
                }
            }
        } else {
            when (stage) {
                0 -> npcl(FacialExpression.OLD_SAD, "Hmph. Have you come to apologize for ignoring me?").also { stage++ }
                1 -> playerl(FacialExpression.OLD_NORMAL, "I'm very sorry. Please change me back!").also { stage++ }
                2 -> npcl(FacialExpression.OLD_NORMAL, "All right. But in future be more polite.").also { stage++ }
                3 -> {
                    end()
                    lock(player, 3)
                    queueScript(player, 1, QueueStrength.SOFT) { _ ->
                        FrogUtils.cleanup(player!!)
                        playerl(FacialExpression.ANGRY_WITH_SMILE, "No, I haven't.")
                        return@queueScript stopExecuting(player)
                    }
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray = intArrayOf(NPCs.FROG_2469)
}