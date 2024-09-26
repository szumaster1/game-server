package content.global.handlers.iface

import core.game.interaction.InterfaceListener
import core.game.node.entity.player.link.emote.Emotes
import org.rs.consts.Components

class EmoteTabInterface : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.EMOTES_464) { player, _, _, buttonID, _, _ ->
            Emotes.handle(player, buttonID)
            return@on true
        }
    }

}