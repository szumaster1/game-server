package content.global.plugins.iface

import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class CrystalKeyChestPlugin : ComponentPlugin {
    private val CHEST_INTERFACE = 501
    private var player: Player? = null

    constructor()
    constructor(player: Player?) {
        this.player = player
    }


    @Throws(Throwable::class)
    override fun newInstance(arg: Any): Plugin<Any> {
        ComponentDefinition.put(CHEST_INTERFACE, this)
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
        return false
    }

    fun constructInterface(player: Player) {
        val hiddenChildren = arrayOf(3, 4, 5, 6, 7)
        val component = Component(CHEST_INTERFACE)
        for (i in 3 until hiddenChildren.size) {
            player.packetDispatch.sendInterfaceConfig(CHEST_INTERFACE, i, true)
        }
        player.packetDispatch.sendItemOnInterface(989, 1, CHEST_INTERFACE, hiddenChildren[2])
        player.interfaceManager.open(component)
        this.player = player
    }
}
