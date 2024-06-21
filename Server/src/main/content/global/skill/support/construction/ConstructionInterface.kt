package content.global.skill.support.construction

import content.global.skill.support.construction.decoration.workshop.FurnitureFlatpack
import core.api.*
import core.api.consts.Components
import core.cache.def.impl.ItemDefinition
import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin
import core.utilities.Log


/**
 * Handles the creating of a decoration object.
 */
@Initializable
class ConstructionInterface : ComponentPlugin() {

    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any> {
        ComponentDefinition.put(Components.POH_BUILD_FURNITURE_396, this)
        ComponentDefinition.put(Components.POH_HOUSE_OPTIONS_398, this)
        ComponentDefinition.put(Components.POH_BUILD_SCREEN_402, this)
        return this
    }

    override fun handle(player: Player, component: Component, opcode: Int, button: Int, slot: Int, itemId: Int): Boolean {
        var slot = slot
        when (component.id) {
            396 -> when (button) {
                132 -> {
                    player.interfaceManager.close()
                    if (getAttribute(player, "con:using-workbench", false)) {
                        val sitAnim = Animation(4108)
                        val standAnim = Animation(4106)
                        val product = FurnitureFlatpack.productMap[itemId] ?: return true
                        val item = product.producedItemId
                        if (getStatLevel(player, Skills.CONSTRUCTION) < product.requiredLevel) {
                            sendDialogue(player, "You need to have a Construction level of " + product.requiredLevel + " to build that.")
                            return true
                        }
                        if (!finishedMoving(player))
                            return true
                        animate(player, 4110)
                        player.inventory.add(Item(item)).also {
                            removeAttribute(player, "con:using-workbench")
                            rewardXP(player, Skills.CONSTRUCTION, product.experience)
                            if (player.isAdmin) player.debug("flatpackid: [${item}], interfaceitem: [${product.interfaceItem}], exp: [${product.experience}]")
                            player.pulseManager.run(object : Pulse(2) {
                                override fun pulse(): Boolean {
                                    player.animate(sitAnim)
                                    return false
                                }
                                override fun stop() {
                                    super.stop()
                                    player.animate(standAnim, 1)
                                    //TODO: re-open interface after interaction completed (flatpack made)
                                    //setAttribute(player, "con:using-workbench", true)
                                    //openInterface(player, Components.POH_WORKBENCH_397)
                                }
                            })
                            return true
                        }
                    } else {
                        val hotspot = player.getAttribute<Hotspot>("con:hotspot")
                        val `object` = player.getAttribute<Scenery>("con:hsobject")
                        if (hotspot == null || `object` == null) {
                            log(this.javaClass, Log.ERR, "Failed building decoration $hotspot : $`object`")
                        }
                        slot = (if ((slot % 2 != 0)) 4 else 0) + (slot shr 1)
                        if (slot >= hotspot.hotspot.decorations.size) {
                            log(this.javaClass, Log.ERR, "Failed building decoration " + slot + "/" + hotspot.hotspot.decorations.size)
                        }
                        val debug = player.isStaff
                        val deco = hotspot.hotspot.decorations[slot]
                        if (!debug) {
                            if (getStatLevel(player, Skills.CONSTRUCTION) < deco.level) {
                                sendDialogue(player,"You need to have a Construction level of " + deco.level + " to build that.")
                                return true
                            }
                            if (!player.inventory.containsItems(*deco.items)) {
                                sendMessage(player,"You don't have the right materials.")
                                return true
                            }
                            for (tool in deco.tools) {
                                if (tool == BuildingUtils.WATERING_CAN) {
                                    var hasWateringCan = false
                                    var i = 0
                                    while (i < 8) {
                                        if (player.inventory.contains(tool - i, 1)) {
                                            hasWateringCan = true
                                            break
                                        }
                                        i++
                                    }
                                    if (!hasWateringCan) {
                                        sendMessage(player,"You need a watering can to plant this.")
                                        return true
                                    }
                                    continue
                                }
                                if (!player.inventory.contains(tool, 1)) {
                                    sendMessage(player,"You need a " + ItemDefinition.forId(tool).name + " to build this.")
                                    return true
                                }
                            }
                        }
                        BuildingUtils.buildDecoration(player, hotspot, deco, `object`)
                        return true
                    }
                }
            }

            398 -> when (button) {
                14 -> {
                    player.houseManager.toggleBuildingMode(player, true)
                    return true
                }

                1 -> {
                    player.houseManager.toggleBuildingMode(player, false)
                    return true
                }

                15 -> {
                    if (!player.houseManager.isInHouse(player)) {
                        sendMessage(player, "You can't do this outside of your house.")
                        return true
                    }
                    player.houseManager.expelGuests(player)
                    return true
                }

                13 -> {
                    if (!player.houseManager.isInHouse(player)) {
                        sendMessage(player, "You can't do this outside of your house.")
                        return true
                    }
                    HouseManager.leave(player)
                    return true
                }
            }

            402 -> {
                val index = button - 160
                log(this.javaClass, Log.FINE, "BuildRoom Interface Index: $index")
                if (index > -1 && index < RoomProperties.values().size) {
                    player.dialogueInterpreter.open("con:room", RoomProperties.values()[index])
                    return true
                }
            }
        }
        return false
    }
}
