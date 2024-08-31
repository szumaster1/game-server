package content.region.morytania.phasmatys.dialogue

import cfg.consts.Items
import cfg.consts.NPCs
import content.region.morytania.phasmatys.quest.ahoy.dialogue.GravingasDialogue
import core.api.getQuestStage
import core.api.inEquipment
import core.api.openDialogue
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Gravingas dialogue.
 */
@Initializable
class GravingasDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: One of the ghost residents of Port Phasmatys.
     * He can be found in what was the town market, waving a placard and protesting about Necrovarus'
     * refusal to allow the townspeople to cross into the next world.
     */

    override fun open(vararg args: Any): Boolean {
        when {
            inEquipment(player, Items.BEDSHEET_4285) && getQuestStage(player, "Ghosts Ahoy") >= 1 -> end().also { openDialogue(player, GravingasDialogue()) }
            !inEquipment(player, Items.GHOSTSPEAK_AMULET_552) -> npc("Woooo wooo wooooo woooo")
            else -> npc("Will you join with me and protect against the evil ban", "of Nercrovarus and his disciples?").also { stage = 1 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendDialogue(player!!, "You cannot understand the ghost.").also { stage = END_DIALOGUE }
            1 -> player("I'm sorry, I don't really think I should get involved.").also { stage++ }
            2 -> npc("Ah, the youth of today - so apathetic to politics.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GRAVINGAS_1685)
    }

}
