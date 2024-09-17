package content.global.skill.gathering.hunter.imp

import cfg.consts.Items
import core.cache.def.impl.ItemDefinition
import core.game.component.Component
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.net.packet.PacketRepository
import core.net.packet.context.ContainerContext
import core.net.packet.outgoing.ContainerPacket
import core.plugin.PluginManager.definePlugin
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Handles the imp box options.
 * @author Taylor
 */
@Initializable
class ImpBoxOptionHandler : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        definePlugin(ImpInterfaceHandler(null))
        for (id in intArrayOf(Items.IMP_IN_A_BOX1_10028, Items.IMP_IN_A_BOX2_10027)) {
            ItemDefinition.forId(id).handlers["option:bank"] = this
            ItemDefinition.forId(id).handlers["option:talk-to"] = this
        }
        definePlugin(ImpBoxDialogue())
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        when (option) {
            "bank" -> {
                val component = Component(478)
                component.plugin = ImpInterfaceHandler(node as Item)
                player.interfaceManager.open(component)
                PacketRepository.send(
                    ContainerPacket::class.java,
                    ContainerContext(player, 478, 61, 91, player.inventory, true)
                )
            }

            "talk-to" -> player.dialogueInterpreter.open("imp-box")
        }
        return true
    }

    override fun isWalk(): Boolean {
        return false
    }

}