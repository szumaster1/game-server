package content.region.kandarin.ardougne.dialogue

import org.rs.consts.NPCs
import content.region.kandarin.ardougne.quest.biohazard.dialogue.ElenaDialogueFile
import core.api.isQuestComplete
import core.api.isQuestInProgress
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import org.rs.consts.QuestName

/**
 * Represents the Elena dialogue.
 */
@Initializable
class ElenaDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Elena is an Ardougnese woman who voluntarily went to West Ardougne
     * to try to help the people there and research the plague.
     * Elena's house is rather small and can be found north-east of
     * Ardougne Castle, near that of her parents', Alrena and Edmond.
     * Location: 2591,3336
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (isQuestComplete(player, QuestName.PLAGUE_CITY) && isQuestInProgress(player, QuestName.BIOHAZARD, 0, 100)) {
            end().also { openDialogue(player, ElenaDialogueFile()) }
        } else {
            npc("Hello.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ELENA_3209)
    }
}
