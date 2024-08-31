package content.minigame.pestcontrol

import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneRestriction

/**
 * Represents the Pest Control island map zone.
 */
class PestControlIsland :
    MapZone("pest control island", true, ZoneRestriction.CANNON, ZoneRestriction.FIRES, ZoneRestriction.RANDOM_EVENTS) {

    override fun death(entity: Entity, killer: Entity): Boolean {
        if (entity is Player) { // Ensure players can't die on the island.
            entity.getProperties().teleportLocation = entity.getLocation()
            return true
        }
        return false
    }

    override fun configure() {
        registerRegion(10537)
    }
}