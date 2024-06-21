package content.global.interaction.iface.warning

import core.api.*
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.dialogue.FacialExpression
import core.game.interaction.InterfaceListener

class ShantayPassWarningInterface : InterfaceListener {

    override fun defineInterfaceListeners() {

        on(Components.CWS_WARNING_10_565) { player, _, _, buttonID, _, _ ->
            when (buttonID) {
                17 -> {
                    closeInterface(player)
                    if (!inInventory(player, Items.SHANTAY_PASS_1854)) {
                        sendNPCDialogueLines(player, NPCs.SHANTAY_GUARD_838, FacialExpression.NEUTRAL, false, "You need a Shantay pass to get through this gate. See", "Shantay, he will sell you one for a very reasonable", "price.")
                        return@on true
                    } else {
                        openDialogue(player, NPCs.SHANTAY_GUARD_838, true)
                    }
                }
                18 -> {
                    closeInterface(player)
                    sendDialogue(player, "You decide that your visit to the desert can be postponed. Perhaps indefinitely.")
                    return@on true
                }
            }
            return@on true
        }
    }
}
