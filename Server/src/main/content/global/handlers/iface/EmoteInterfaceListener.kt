package content.global.handlers.iface

import core.api.consts.Components
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.link.emote.Emotes

class EmoteInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.EMOTES_464){ player, _, _, buttonID, _, _ ->
            Emotes.handle(player, buttonID)
            return@on true
        }
    }
}
