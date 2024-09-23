package content.global.skill.construction.decoration.workshop.workbench

import content.global.skill.construction.BuildHotspot
import content.global.skill.construction.BuildingUtils.buildDecoration
import content.global.skill.construction.Decoration
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import org.rs.consts.Items

/**
 * Handles interaction for creation of flatpacks in the workshop.
 */
class Flatpack : InteractionListener {

    private val flatpackIDs = Decoration.values().map { it.flatpackItemID }.toIntArray()
    private val buildHotspot = BuildHotspot.values().map { it.objectId }.toIntArray()

    override fun defineListeners() {
        for (hotspot in buildHotspot) {
            onUseWith(IntType.SCENERY, flatpackIDs, hotspot) { player, used, with ->
                return@onUseWith buildFlatpackOnHotspot(player, used.asItem(), with.asScenery())
            }
        }
    }

    private fun buildFlatpackOnHotspot(player: Player, used: Item, with: Scenery): Boolean {
        val hotspotUsed = player.houseManager.getHotspot(with)
        val decorationUsed = Decoration.forFlatpackItemId(used.id)

        if (!hotspotUsed.hotspot.decorations.contains(decorationUsed)) {
            sendMessage(player, "You can't build that here.")
            return false
        }
        if (!player.inventory.containsItems(Item(Items.HAMMER_2347), Item(Items.SAW_8794))) {
            sendMessage(player, "You need a hammer and a saw to build this.")
            return false
        }
        buildDecoration(player, hotspotUsed, decorationUsed, with.asScenery(), true)
        return true

    }
}