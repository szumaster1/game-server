package content.global.skill.construction.decoration.workshop.workbench

import content.global.skill.construction.BuildHotspot
import content.global.skill.construction.Hotspot
import core.api.openInterface
import core.api.setVarp
import core.cache.def.impl.ItemDefinition
import core.game.component.Component
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.InterfaceListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.net.packet.PacketRepository
import core.net.packet.context.ContainerContext
import core.net.packet.outgoing.ContainerPacket
import kotlin.math.min

/**
 * Workbench listener.
 */
class WorkbenchListener : InterfaceListener, InteractionListener {
    override fun defineListeners() {
        on(intArrayOf(13704, 13705, 13706, 13707, 13708), IntType.SCENERY, "work-at") { player, obj ->
            player.interfaceManager.close()
            openInterface(player, 397)
            when (obj.id) {
                13704 -> player.setAttribute("maxFlatpackLevel", 20)
                13705 -> player.setAttribute("maxFlatpackLevel", 40)
                13706 -> player.setAttribute("maxFlatpackLevel", 60)
                13707 -> player.setAttribute("maxFlatpackLevel", 80)
                13708 -> player.setAttribute("maxFlatpackLevel", 99)
            }

            return@on true
        }
    }

    override fun defineInterfaceListeners() {
        on(397) { player, component, opcode, buttonID, slot, itemID ->
            val furnitureType = when (buttonID) {
                111 -> BuildHotspot.CHAIRS_1
                112 -> BuildHotspot.BOOKCASE
                113 -> BuildHotspot.BARRELS
                114 -> BuildHotspot.KITCHEN_TABLE
                115 -> BuildHotspot.DINING_TABLE
                116 -> BuildHotspot.DINING_BENCH_1
                117 -> BuildHotspot.BED
                118 -> BuildHotspot.DRESSER
                119 -> BuildHotspot.DRAWERS
                120 -> BuildHotspot.CLOCK
                121 -> BuildHotspot.CAPE_RACK
                122 -> BuildHotspot.MAGIC_WARDROBE
                123 -> BuildHotspot.ARMOUR_CASE
                124 -> BuildHotspot.TREASURE_CHEST
                125 -> BuildHotspot.COSTUME_BOX
                126 -> BuildHotspot.TOY_BOX
                else -> return@on false
            }
            val hotspot = Hotspot(BuildHotspot.FLATPACK, 0, 0)

            player.setAttribute("con:hotspot", hotspot)
            openBuildInterface(player, furnitureType)

            return@on true
        }
    }

    private fun openBuildInterface(player: Player, hotspot: BuildHotspot) {
        val BUILD_INDEXES = intArrayOf(0, 2, 4, 6, 1, 3, 5)
        player.interfaceManager.open(Component(396))
        val items = arrayOfNulls<Item>(7)

        var c261Value = 0

        for (menuIndex in 0..6) {
            val itemsStringOffset = 97 + (menuIndex * 5)

            //97 +
            if (menuIndex >= hotspot.decorations.size || (hotspot.decorations[menuIndex] != null && hotspot.decorations[menuIndex].isInvisibleNode)) {
                for (j in 0..4) {
                    player.packetDispatch.sendString("", 396, itemsStringOffset + j)
                }
                player.packetDispatch.sendString("", 396, 140 + menuIndex)
                c261Value += (1 shl (menuIndex + 1))
                continue
            }

            val decoration = hotspot.decorations[menuIndex]
            items[BUILD_INDEXES[menuIndex]] = Item(decoration.interfaceItem)
            player.packetDispatch.sendString(
                ItemDefinition.forId(decoration.interfaceItem).name,
                396,
                itemsStringOffset
            )
            var hasRequirements =
                min(player.skills.getLevel(22), player.getAttribute("maxFlatpackLevel", 0)) >= decoration.level
            for (j in 0..3) {
                if (j >= decoration.items.size) {
                    if (j == decoration.items.size && decoration.nailAmount > 0) {
                        player.packetDispatch.sendString(
                            "Nails: " + decoration.nailAmount,
                            396,
                            (itemsStringOffset + 1) + j
                        )
                    } else {
                        player.packetDispatch.sendString("", 396, (itemsStringOffset + 1) + j)
                    }
                } else {
                    val item = decoration.items[j]
                    if (!player.inventory.containsItem(item)) {
                        hasRequirements = false
                    }
                    val s = item.name + ": " + item.amount
                    player.packetDispatch.sendString(s, 396, (itemsStringOffset + 1) + j)
                }
            }
            if (hasRequirements) {
                c261Value += (1 shl (menuIndex + 1))
            }
            setVarp(player, 1485 + menuIndex, if (hasRequirements || player.isStaff) 1 else 0)
            player.packetDispatch.sendString("Level " + decoration.level, 396, 140 + menuIndex)
        }

        setVarp(player, 261, c261Value)
        PacketRepository.send(ContainerPacket::class.java, ContainerContext(player, 396, 132, 8, items, false))
    }

}