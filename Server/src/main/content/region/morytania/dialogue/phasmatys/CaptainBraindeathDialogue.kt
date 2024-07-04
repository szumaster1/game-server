package content.region.morytania.dialogue.phasmatys

import content.region.morytania.quest.rumdeal.CaptainBrainDeathDialogueFile
import core.api.*
import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class CaptainBraindeathDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        val quest = getQuestStage(player, "Rum Deal")
        if (getQuestStage(player, "Rum Deal") >= 1) {
            openDialogue(player, CaptainBrainDeathDialogueFile(quest))
        } else {
            sendDialogue(player!!, "The Captain Braindeath is too busy to talk.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.CAPTAIN_BRAINDEATH_2827)
    }

}
