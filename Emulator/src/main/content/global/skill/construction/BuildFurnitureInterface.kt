package content.global.skill.construction

import core.api.*
import core.cache.def.impl.ItemDefinition
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.scenery.Scenery
import core.tools.Log
import org.rs.consts.Components

class BuildFurnitureInterface : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.POH_BUILD_FURNITURE_396) { player, _, _, buttonID, slot, itemID ->
            var slot = slot
            when (buttonID) {
                132 -> {
                    player.interfaceManager.close()
                    val hotspot = player.getAttribute<Hotspot>("con:hotspot")
                    val `object` = player.getAttribute<Scenery>("con:hsobject")

                    if (hotspot!!.hotspot != BuildHotspot.FLATPACK) {
                        if (hotspot == null || `object` == null) {
                            log(this.javaClass, Log.ERR, "Failed building decoration $hotspot : $`object`")
                            return@on true
                        }
                    }

                    slot = (if ((slot % 2 != 0)) 4 else 0) + (slot shr 1)
                    if (slot >= hotspot.hotspot.decorations.size) {
                        log(this.javaClass, Log.ERR, "Failed building decoration " + slot + "/" + hotspot.hotspot.decorations.size)
                        return@on true
                    }

                    val debug = player.isStaff
                    var deco = hotspot.hotspot.decorations[slot]

                    if (hotspot.hotspot == BuildHotspot.FLATPACK) {
                        deco = Decoration.forInterfaceItemId(itemID)
                        player.sendMessage("Building flatpack: $itemID")
                        player.sendMessage("Building flatpack: " + deco.name)

                        if (debug || checkRequirements(player, deco)) BuildingUtils.createFlatpack(player, deco, debug)
                        return@on true
                    }

                    if (!debug) {
                        if (getStatLevel(player, Skills.CONSTRUCTION) < deco!!.level) {
                            sendMessage(player,"You need to have a Construction level of " + deco.level + " to build that.")
                            return@on true
                        }
                        if (!player.inventory.containsItems(*deco.items)) {
                            sendMessage(player,"You don't have the right materials.")
                            return@on true
                        }
                        for (tool in deco.tools) {
                            if (tool == BuildingUtils.WATERING_CAN) {
                                var hasWateringCan = false
                                var i = 0
                                while (i < 8) {
                                    if (inInventory(player,tool - i, 1)) {
                                        hasWateringCan = true
                                        break
                                    }
                                    i++
                                }
                                if (!hasWateringCan) {
                                    sendMessage(player,"You need a watering can to plant this.")
                                    return@on true
                                }
                                continue
                            }
                            if (!inInventory(player,tool, 1)) {
                                sendMessage(player,"You need a " + ItemDefinition.forId(tool).name + " to build this.")
                                return@on true
                            }
                        }
                    }
                    BuildingUtils.buildDecoration(player, hotspot, deco, `object`)
                    return@on true
                }

                else -> return@on false
            }
        }
    }

    private fun checkRequirements(player: Player, deco: Decoration): Boolean {
        if (getStatLevel(player, Skills.CONSTRUCTION) < deco.level) {
            sendMessage(player, "You need to have a Construction level of " + deco.level + " to build that.")
            return true
        }
        if (!player.inventory.containsItems(*deco.items)) {
            sendMessage(player, "You don't have the right materials.")
            return true
        }
        for (tool in deco.tools) {
            if (tool == BuildingUtils.WATERING_CAN) {
                var hasWateringCan = false
                for (i in 0..7) {
                    if (inInventory(player, tool - i, 1)) {
                        hasWateringCan = true
                        break
                    }
                }
                if (!hasWateringCan) {
                    sendMessage(player, "You need a watering can to plant this.")
                    return false
                }
                continue
            }
            if (!inInventory(player, tool, 1)) {
                sendMessage(player, "You need a " + getItemName(tool) + " to build this.")
                return false
            }
        }
        return true
    }
}