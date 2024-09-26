package content.region.kandarin.ardougne.monastery.dialogue

import content.region.kandarin.ardougne.quest.drunkmonk.dialogue.MonasteryMonkDialogueFile
import org.rs.consts.NPCs
import core.api.getQuestStage
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.QuestName

/**
 * Represents the Monastery Monk dialogue.
 */
@Initializable
class MonasteryMonkDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (getQuestStage(player!!, QuestName.MONKS_FRIEND) > 1) {
            end()
            openDialogue(player, MonasteryMonkDialogueFile())
        } else {
            npcl(FacialExpression.NEUTRAL, "Peace brother.")
            stage = 0
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MONK_281)
    }

}