package content.region.misthalin.lumbridge.quest.haunted.dialogue

import core.api.finishQuest
import core.api.isQuestComplete
import core.api.runTask
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.NPCs
import org.rs.consts.QuestName

/**
 * Represents the Ernest dialogue.
 */
@Initializable
class ErnestDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Thank you sir. It was dreadfully irritating being a", "chicken. How can I ever thank you?")
        stage = 1
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            1 -> player(FacialExpression.HALF_GUILTY, "Well a cash reward is always nice...").also { stage++ }
            2 -> npc(FacialExpression.HALF_GUILTY, "Of course, of course.").also { stage++ }
            3 -> {
                runTask(player, 1) {
                    npc.clear()
                    end()
                }
            }
        }
        return true
    }

    override fun close(): Boolean {
        finish()
        return super.close()
    }

    override fun finish() {
        if (isQuestComplete(player, QuestName.ERNEST_THE_CHICKEN)) {
            npc.clear()
            return
        }
        npc.clear()
        finishQuest(player, QuestName.ERNEST_THE_CHICKEN)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ERNEST_287)
    }
}