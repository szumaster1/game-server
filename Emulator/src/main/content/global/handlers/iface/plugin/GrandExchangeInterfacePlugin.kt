package content.global.handlers.iface.plugin

import content.global.handlers.iface.ge.StockMarketInterfaceListener.Companion.withdraw
import core.api.closeChatBox
import cfg.consts.Components
import cfg.consts.Sounds
import core.api.freeSlots
import core.api.playAudio
import core.api.sendMessage
import core.cache.def.impl.CS2Mapping
import core.cache.def.impl.ItemDefinition
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.ge.GEGuidePrice.GuideType
import core.game.ge.GEItemSet
import core.game.ge.GrandExchangeOffer
import core.game.ge.GrandExchangeRecords.Companion.getInstance
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.network.packet.PacketRepository
import core.network.packet.context.ContainerContext
import core.network.packet.outgoing.ContainerPacket
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Handles the Grand Exchange interface options.
 * @author Emperor
 */
@Initializable
class GrandExchangeInterfacePlugin : ComponentPlugin() {

    /* Main interface.
     * ComponentDefinition.put(105, this);
     * Selling tab.
     * ComponentDefinition.put(107, this);
     */

    override fun newInstance(arg: Any?): Plugin<Any> {
        ComponentDefinition.put(Components.STOCKCOLLECT_109, this)
        ComponentDefinition.put(Components.OBJDIALOG_389, this)
        ComponentDefinition.put(Components.EXCHANGE_SETS_SIDE_644, this)
        ComponentDefinition.put(Components.EXCHANGE_ITEMSETS_645, this)
        ComponentDefinition.put(Components.EXCHANGE_GUIDE_PRICE_642, this)
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
        Pulser.submit(object : Pulse(1, player) {
            override fun pulse(): Boolean {
                when (component.id) {
                    Components.EXCHANGE_SETS_SIDE_644, Components.EXCHANGE_ITEMSETS_645 -> {
                        handleItemSet(player, component, opcode, button, slot, itemId)
                        return true
                    }

                    Components.OBJDIALOG_389 -> {
                        handleSearchInterface(player, opcode, button, slot, itemId)
                        return true
                    }

                    Components.STOCKCOLLECT_109 -> {
                        handleCollectionBox(player, opcode, button, slot, itemId)
                        return true
                    }

                    Components.EXCHANGE_GUIDE_PRICE_642 -> {
                        handleGuidePrice(player, opcode, button, slot, itemId)
                        return true
                    }
                }
                return true
            }
        })
        return true
    }

    /**
     * Handle search interface.
     *
     * @param player The player object.
     * @param opcode The operation code.
     * @param button The button clicked.
     * @param slot   The slot number.
     * @param itemId The item ID.
     * @return true if the operation is successful, false otherwise.
     */
    fun handleSearchInterface(player: Player, opcode: Int, button: Int, slot: Int, itemId: Int): Boolean {
        when (button) {
            10 -> {
                closeChatBox(player)
                return true
            }
        }
        return false
    }

    /**
     * Handle collection box.
     *
     * @param player The player object.
     * @param opcode The operation code.
     * @param button The button clicked.
     * @param slot   The slot number.
     * @param itemId The item ID.
     * @return true if the operation is successful, false otherwise.
     */
    fun handleCollectionBox(player: Player?, opcode: Int, button: Int, slot: Int, itemId: Int): Boolean {
        var index = -1
        when (button) {
            18, 23, 28 -> index = (button - 18) shr 2
            36, 44, 52 -> index = 3 + ((button - 36) shr 3)
        }
        var offer: GrandExchangeOffer? = null
        val records = getInstance(player)
        if (index > -1 && (records.getOffer(records.offerRecords[index]).also { offer = it }) != null) {
            withdraw(player!!, offer!!, slot shr 1, opcode);
        }
        return true
    }

    private fun handleItemSet(player: Player, component: Component, opcode: Int, button: Int, slot: Int, itemId: Int) {
        if (button != 16 && button != 0) {
            return
        }
        val inventory = component.id == 644
        if (slot < 0 || slot >= (if (inventory) 28 else GEItemSet.values().size)) {
            return
        }

        val item: Item?
        val set: GEItemSet?
        if (inventory) {
            item = player.inventory[slot]
            if (item == null) return
            set = GEItemSet.forId(item.id)
            if (set == null) return
        } else {
            set = GEItemSet.values()[slot]
            item = Item(set.itemId)
        }

        if (opcode != 127 && inventory && set == null) {
            sendMessage(player, "This isn't a set item.")
            return
        }
        when (opcode) {
            124 -> sendMessage(player, item.definition.examine)
            196 -> {
                if (inventory) {
                    if (freeSlots(player) < set.components.size - 1) {
                        sendMessage(player, "You don't have enough inventory space for the component parts.")
                        return
                    }
                    if (!player.inventory.remove(item, false)) {
                        return
                    }
                    for (id in set.components) {
                        player.inventory.add(Item(id, 1))
                    }
                    player.inventory.refresh()
                    sendMessage(player, "You successfully traded your set for its component items!")
                } else {
                    if (!player.inventory.containItems(*set.components)) {
                        sendMessage(player, "You don't have the parts that make up this set.")
                    }
                    for (id in set.components) {
                        player.inventory.remove(Item(id, 1), false)
                    }
                    player.inventory.add(item)
                    player.inventory.refresh()
                    sendMessage(player, "You successfully traded your item components for a set!")
                }
                playAudio(player, Sounds.GE_TRADE_OK_4044)
                PacketRepository.send(
                    ContainerPacket::class.java,
                    ContainerContext(player, -1, -2, player.getAttribute("container-key", 93), player.inventory, false)
                )
            }

            155 -> {
                val mapping = CS2Mapping.forId(1089)
                if (mapping != null) {
                    player.packetDispatch.sendMessage(mapping.map?.get(set.itemId) as String?)
                }
            }
        }
    }

    private fun handleGuidePrice(player: Player, opcode: Int, buttonId: Int, slot: Int, itemId: Int) {
        when (opcode) {
            155 -> {
                val type = player.getAttribute<GuideType>("guide-price", null) ?: return
                var subtract = 0
                if (buttonId >= 15 && buttonId <= 23) {
                    subtract = 15
                }
                if (buttonId >= 43 && buttonId <= 57) {
                    subtract = 43
                }
                if (buttonId >= 89 && buttonId <= 103) {
                    subtract = 89
                }
                if (buttonId >= 135 && buttonId <= 144) {
                    subtract = 135
                }
                if (buttonId >= 167 && buttonId <= 182) {
                    subtract = 167
                }
                sendMessage(player, ItemDefinition.forId(type.items[buttonId - subtract].item).examine)
            }
        }
    }
}
