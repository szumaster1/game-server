package content.global.skill.gathering.hunter.imp

import core.api.sendMessage
import core.game.component.Component
import core.game.component.ComponentDefinition.forId
import core.game.component.ComponentPlugin
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.net.packet.PacketRepository
import core.net.packet.context.ContainerContext
import core.net.packet.outgoing.ContainerPacket
import core.plugin.Plugin

/**
 * Represents the imp interface handler.
 */
class ImpInterfaceHandler(private var box: Item?) : ComponentPlugin() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        forId(478)!!.plugin = this
        return this
    }

    override fun handle(player: Player, component: Component, opcode: Int, button: Int, slot: Int, itemId: Int): Boolean {
        val item = player.inventory[slot]
        if (item != null) {
            if (player.bank.canAdd(item) && item.id != box!!.id) {
                player.dialogueInterpreter.close()
                player.inventory.remove(item)
                player.bank.add(item)
                PacketRepository.send(
                    ContainerPacket::class.java,
                    ContainerContext(player, 478, 61, 91, player.inventory, true)
                )
                if (box!!.id == IDS[1]) {
                    val boxSlot = player.inventory.getSlot(box)
                    player.inventory.replace((Item(IDS[0]).also { box = it }), boxSlot)
                } else if (box!!.id == IDS[0]) {
                    val boxSlot = player.inventory.getSlot(box)
                    player.inventory.replace(Item(10025), boxSlot)
                    player.interfaceManager.close(component)
                    player.sendMessage(Companion.FINISHING_MESSAGE)
                }
            }
        } else {
            sendMessage(player, "You cannot add this item to your bank.")
            return false
        }
        return true
    }

    companion object {
        private val IDS = intArrayOf(10028, 10027)
        private const val FINISHING_MESSAGE = "The imp teleports away, taking the item to your bank account."
    }
}
