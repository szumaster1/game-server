package content.global.skill.construction

import core.api.log
import core.game.interaction.InterfaceListener
import core.tools.Log
import org.rs.consts.Components

class BuildInterface : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.POH_BUILD_SCREEN_402) { player, _, _, buttonID, _, _ ->
            val index = buttonID - 160
            log(this.javaClass, Log.FINE, "BuildRoom Interface Index: $index")
            if (index > -1 && index < RoomProperties.values().size) {
                player.dialogueInterpreter.open("con:room", RoomProperties.values()[index])
            }
            return@on true
        }
    }
}