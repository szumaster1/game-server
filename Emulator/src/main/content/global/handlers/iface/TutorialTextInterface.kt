package content.global.handlers.iface

import core.api.setInterfaceSprite
import core.game.interaction.InterfaceListener
import org.rs.consts.Components

class TutorialInterface : InterfaceListener {

    override fun defineInterfaceListeners() {
        onOpen(Components.TUTORIAL_TEXT_372) { player, _ ->
          //setInterfaceSprite(player, Components.TUTORIAL_TEXT_372, 0, 25, 20)// Title.
            setInterfaceSprite(player, Components.TUTORIAL_TEXT_372, 1, 10, 35)// String 0.
            setInterfaceSprite(player, Components.TUTORIAL_TEXT_372, 2, 10, 50)// String 1.
            setInterfaceSprite(player, Components.TUTORIAL_TEXT_372, 3, 10, 65)// String 2.
            setInterfaceSprite(player, Components.TUTORIAL_TEXT_372, 4, 10, 80)// String 3.
            return@onOpen true
        }
    }

}