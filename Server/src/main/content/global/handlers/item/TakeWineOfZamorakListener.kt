package content.global.handlers.item

import core.api.consts.Items
import core.game.global.action.PickupHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.GroundItem
import core.game.world.map.RegionManager

/**
 * Take wine of zamorak listener
 *
 * @constructor Take wine of zamorak listener
 */
class TakeWineOfZamorakListener : InteractionListener {

    private val wineOfZamorak = Items.WINE_OF_ZAMORAK_245

    override fun defineListeners() {
        on(wineOfZamorak, IntType.GROUNDITEM, "take") { player, wine ->
            if (player.location.regionId != 11574) {
                PickupHandler.take(player, wine as GroundItem)
                return@on true
            }
            val npcs = RegionManager.getLocalNpcs(player)
            for (n in npcs) {
                if (n.id == 188) {
                    n.sendChat("Hands off Zamorak's wine!")
                    n.properties.combatPulse.attack(player)
                }
            }
            return@on true
        }
    }
}
