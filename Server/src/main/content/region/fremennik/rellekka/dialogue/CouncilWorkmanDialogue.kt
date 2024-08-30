package content.region.fremennik.rellekka.dialogue

import content.region.fremennik.quest.viking.dialogue.CouncilWorkerDialogue
import content.dialogue.digsite.CouncilWorkmanDiaryDialogue
import cfg.consts.NPCs
import core.api.getQuestStage
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Council workman dialogue.
 */
@Initializable
class CouncilWorkmanDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        end()
        if (getQuestStage(player, "Fremennik Trials") in 1..99) {
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