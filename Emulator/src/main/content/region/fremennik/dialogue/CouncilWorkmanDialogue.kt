package content.region.fremennik.dialogue

import content.region.fremennik.rellekka.quest.viking.dialogue.CouncilWorkerDialogue
import content.region.misthalin.varrock.diary.dialogue.CouncilWorkmanDiaryDialogue
import core.api.getQuestStage
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import org.rs.consts.NPCs
import org.rs.consts.QuestName

/**
 * Represents the Council workman dialogue.
 */
@Initializable
class CouncilWorkmanDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        end()
        if (getQuestStage(player, QuestName.THE_FREMENNIK_TRIALS) in 1..99) {
            player.dialogueInterpreter.open((CouncilWorkerDialogue(1)))
        } else {
            openDialogue(player, CouncilWorkmanDiaryDialogue(), npc)
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.COUNCIL_WORKMAN_1287)
    }

}