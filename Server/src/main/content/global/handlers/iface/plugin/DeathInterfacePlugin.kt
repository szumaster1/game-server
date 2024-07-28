package content.global.handlers.iface.plugin

import core.api.consts.Components
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class DeathInterfacePlugin : ComponentPlugin() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        ComponentDefinition.forId(Components.AIDE_DEATH_153).plugin = this
        return this
    }

    override fun handle(
        player: Player,
        component: Component,
        opcode: Int,
        button: Int,
        slot: Int,
        itemId: Int
    ): Boolean {
        if (button == 1) {
            player.getSavedData().globalData.setDisableDeathScreen(true)
            player.interfaceManager.close()
        }
        return true
    }
}
