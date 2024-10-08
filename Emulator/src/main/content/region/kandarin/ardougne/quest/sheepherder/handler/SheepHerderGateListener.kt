package content.region.kandarin.ardougne.quest.sheepherder.handler

import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.Scenery
import core.api.inEquipment
import core.api.sendNPCDialogueLines
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

class SheepHerderGateListener : InteractionListener {

    override fun defineListeners() {
        on(intArrayOf(Scenery.GATE_166, Scenery.GATE_167), IntType.SCENERY, "open") { player, _ ->
            if (!inEquipment(player, Items.PLAGUE_JACKET_284, Items.PLAGUE_TROUSERS_285)) {
                sendNPCDialogueLines(player, NPCs.FARMER_BRUMTY_291, FacialExpression.SUSPICIOUS, false,"You can't enter without your protective gear!", "Can't have you spreading the plague!")
            }
            return@on false
        }
    }
}