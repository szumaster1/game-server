package content.region.kandarin.ardougne.dialogue

import content.region.kandarin.ardougne.quest.biohazard.dialogue.OmartBiohazardDialogue
import org.rs.consts.NPCs
import core.api.isQuestInProgress
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Omart dialogue.
 */
@Initializable
class OmartDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Omart is a man located between the south-east corner of
     * West Ardougne's walls and Ceril Carnillean's house in East Ardougne,
     * just south of the castle. He plays a minor role in the Biohazard quest,
     * smuggling players into West Ardougne.
     * Location: 2559,3266
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (isQuestInProgress(player, "Biohazard", 2, 100)) {
            end().also { openDialogue(player, OmartBiohazardDialogue()) }
        } else {
            npc("Hello.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.OMART_350)
    }
}
