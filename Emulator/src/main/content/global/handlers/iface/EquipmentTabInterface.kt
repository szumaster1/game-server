package content.global.handlers.iface

import core.api.log
import core.api.submitWorldPulse
import core.cache.def.impl.ItemDefinition
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.container.Container
import core.game.container.ContainerEvent
import core.game.container.ContainerListener
import core.game.container.access.InterfaceContainer
import core.game.container.impl.EquipmentContainer
import core.game.global.action.EquipHandler.Companion.unequip
import core.game.interaction.IntType
import core.game.interaction.InteractionListeners.run
import core.game.node.entity.combat.DeathTask
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.net.packet.PacketRepository
import core.net.packet.context.ContainerContext
import core.net.packet.outgoing.ContainerPacket
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.Log

/**
 * Represents the equipment interface.
 */
@Initializable
class EquipmentTabInterface : ComponentPlugin() {
    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any> {
        ComponentDefinition.put(102, this)
        ComponentDefinition.put(387, this)
        ComponentDefinition.put(667, this)
        ComponentDefinition.put(670, this)
        return this
    }

    override fun handle(p: Player, component: Component, opcode: Int, button: Int, slot: Int, itemId: Int): Boolean {
        if (component.id == 667) {
            if (button != 14) {
                return false
            }
            when (opcode) {
                155 -> {
                    p.pulseManager.clear()
                    submitWorldPulse(object : Pulse(1, p) {
                        override fun pulse(): Boolean {
                            unequip(p, slot, itemId)
                            return true
                        }
                    })
                    return true
                }

                9 -> {
                    p.sendMessage(p.equipment[slot].definition.examine)
                    return true
                }

                196 -> {
                    submitWorldPulse(object : Pulse(1, p) {
                        override fun pulse(): Boolean {
                            operate(p, slot, itemId)
                            return true
                        }
                    })
                    return true
                }
            }
            return false
        } else if (component.id == 670) {
            when (opcode) {
                155 -> {
                    p.pulseManager.clear()
                    val item = p.inventory[slot]
                    submitWorldPulse(object : Pulse(1, p) {
                        override fun pulse(): Boolean {
                            if (item == null) return true
                            run(item.id, IntType.ITEM, "equip", p, item)
                            return true
                        }
                    })
                    return true
                }

                9 -> {
                    p.sendMessage(p.inventory[slot].definition.examine)
                    return true
                }
            }
        }
        when (opcode) {
            206 -> {
                if (button != 28) {
                    return false
                }
                submitWorldPulse(object : Pulse(1, p) {
                    override fun pulse(): Boolean {
                        operate(p, slot, itemId)
                        return true
                    }
                })
                return true
            }

            else -> when (button) {
                52 -> {
                    if (p.interfaceManager.isOpened && p.interfaceManager.opened.id == 102) {
                        return true
                    }

                    // (Highlight white items are auto destroyed on death Enum#616 (Items kept on death interface) TODO: Parse server sided
                    // SCRIPT 118 - Items kept on death interface CS
                    // ARG 0: Safe location check Takes: 0 Safe Area/2 in POH/3 in Castle Wars/4 in Trouble Brewing/5 in Barbass
                    val zoneType = p.zoneMonitor.type
                    // ARG 1: Amount of items kept on death Takes: 0/1/3/4
                    val itemArray = DeathTask.getContainers(p)
                    val kept = itemArray[0]
                    val amtKeptOnDeath = kept.itemCount()
                    if (amtKeptOnDeath > 4 && zoneType == 0) {
                        log(
                            this.javaClass,
                            Log.ERR,
                            "Items kept on death interface should not contain more than 4 items when not in a safe zone!"
                        )
                    }
                    // ARG 2: Item kept on death slot 0
                    val slot0 = kept.getId(0)
                    // ARG 3: Item kept on death slot 1
                    val slot1 = kept.getId(1)
                    // ARG 4: Item kept on death slot 2
                    val slot2 = kept.getId(2)
                    // ARG 5: Item kept on death slot 3
                    val slot3 = kept.getId(3)
                    // ARG 6: Player skulled Takes: 0 not skulled/1 skulled
                    val skulled = if (p.skullManager.isSkulled) 1 else 0
                    // ARG 7: Player has summoning creature out Takes: 0 not out/1 Creature summoned
                    val hasBoB = if (p.familiarManager.hasFamiliar()) {
                        if (p.familiarManager.familiar.isBurdenBeast) {
                            if ((p.familiarManager.familiar as content.global.skill.summoning.familiar.BurdenBeast).container.isEmpty) 0 else 1
                        } else {
                            0
                        }
                    } else {
                        0
                    }
                    // ARG 8: String for effect:
                    // 			if (arg1 == 0) arg8 + " This reduces the items you keep from three to zero!"
                    //			if (arg1 == 1) arg8 + " This reduces the items you keep from three to zero!" + "<br>" + "<br>" + "However, you also have the " + "<col=ff3333>" + "Protect Items" + "<col=ff981f>" + " prayer active, which saves you one extra item!");
                    val params = arrayOf<Any>(
                        hasBoB,
                        skulled,
                        slot3,
                        slot2,
                        slot1,
                        slot0,
                        amtKeptOnDeath,
                        zoneType,
                        "You are skulled."
                    )
                    p.packetDispatch.sendRunScript(118, "siiooooii", *params)

                    p.interfaceManager.openComponent(102)
                }

                28 -> if (opcode == 81) {
                    p.pulseManager.clear()
                    submitWorldPulse(object : Pulse(1, p) {
                        override fun pulse(): Boolean {
                            unequip(p, slot, itemId)
                            return true
                        }
                    })
                    return true
                }

                55 -> {
                    if (p.interfaceManager.isOpened && p.interfaceManager.opened.id == 667) {
                        return true
                    }
                    val listener: ContainerListener = object : ContainerListener {
                        override fun update(c: Container?, e: ContainerEvent?) {
                            PacketRepository.send(
                                ContainerPacket::class.java,
                                ContainerContext(p, -1, -1, 98, e!!.items, false, *e.slots)
                            )
                        }

                        override fun refresh(c: Container?) {
                            PacketRepository.send(
                                ContainerPacket::class.java,
                                ContainerContext(p, -1, -1, 98, c!!, false)
                            )
                        }
                    }
                    p.interfaceManager.openComponent(667).setCloseEvent { player: Player, c: Component? ->
                        player.removeAttribute("equip_stats_open")
                        player.interfaceManager.closeSingleTab()
                        player.inventory.listeners.remove(listener)
                        true
                    }
                    p.setAttribute("equip_stats_open", true)
                    EquipmentContainer.update(p)
                    p.interfaceManager.openSingleTab(Component(670))
                    InterfaceContainer.generateItems(p, p.inventory.toArray(), arrayOf("Equip"), 670, 0, 7, 4, 93)
                    p.inventory.listeners.add(listener)
                    p.inventory.refresh()
                    ItemDefinition.statsUpdate(p)
                    p.packetDispatch.sendIfaceSettings(1278, 14, 667, 0, 13)
                }
            }
        }
        return true
    }

    fun operate(player: Player, slot: Int, itemId: Int) {
        if (slot < 0 || slot > 13) {
            return
        }
        val item = player.equipment[slot] ?: return
        if (run(item.id, IntType.ITEM, "operate", player, item)) {
            return
        }
        val handler = item.operateHandler
        if (handler != null && handler.handle(player, item, "operate")) {
            return
        }
        player.packetDispatch.sendMessage("You can't operate that.")
    }
}
