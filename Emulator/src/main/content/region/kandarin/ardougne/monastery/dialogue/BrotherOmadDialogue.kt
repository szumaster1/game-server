package content.region.kandarin.ardougne.monastery.dialogue

import content.region.kandarin.ardougne.quest.drunkmonk.dialogue.BrotherOmadDialogueFile
import org.rs.consts.NPCs
import core.api.getQuestStage
import core.api.openDialogue
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.QuestName

/**
 * Represents the Brother Omad dialogue.
 */
@Initializable
class BrotherOmadDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (getQuestStage(player, QuestName.MONKS_FRIEND) in 0..100) {
            end()
            openDialogue(player, BrotherOmadDialogueFile())
        } else {
            sendDialogue(player, "Brother Omad is not interested in talking.")
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
        return intArrayOf(NPCs.BROTHER_OMAD_279)
    }

}