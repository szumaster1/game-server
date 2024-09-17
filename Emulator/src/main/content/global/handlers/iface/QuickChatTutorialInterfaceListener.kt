package content.global.handlers.iface

import core.api.*
import org.rs.consts.Components
import org.rs.consts.Vars
import core.game.interaction.InterfaceListener

/**
 * Represents the interface listener for the quick chat tutorial.
 */
class QuickChatTutorialInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {

        onOpen(Components.QUICKCHAT_TUTORIAL_157) { player, _ ->
            setVarbit(player, Vars.VARBIT_IFACE_QUICKCHAT_TUTORIAL_4762, 1)
            return@onOpen true
        }

        onClose(Components.QUICKCHAT_TUTORIAL_157) { player, _ ->
            setVarbit(player, Vars.VARBIT_IFACE_QUICKCHAT_TUTORIAL_4762, 0)
            return@onClose true
        }

        on(Components.CHATDEFAULT_137) { player, _, _, buttonID, _, _ ->
            if(buttonID == 5) {
                openInterface(player, Components.QUICKCHAT_TUTORIAL_157)
            }
            return@on true
        }

    }

}