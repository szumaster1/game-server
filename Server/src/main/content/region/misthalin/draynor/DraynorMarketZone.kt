package content.region.misthalin.draynor

import core.api.getAttribute
import core.api.removeAttribute
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneBuilder
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Draynor market zone.
 */
@Initializable
class DraynorMarketZone : MapZone("draynor-market", true), Plugin<Any?> {
    override fun configure() {
        register(ZoneBorders(3074, 3245, 3086, 3255))
    }

    override fun enter(entity: Entity): Boolean {
        // Visit the Draynor Village market
        if (entity.isPlayer) {
            val player = entity.asPlayer()
        }
        return super.enter(entity)
    }

    override fun canLogout(p: Player): Boolean {
        if (getAttribute(p, "draynor:feed-tree-guard", false)) {
            removeAttribute(p, "draynor:feed-tree-guard")
        }
        return true
    }

    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any?> {
        ZoneBuilder.configure(this)
        return this
    }

    override fun fireEvent(identifier: String, vararg args: Any): Any? {
        return null
    }
}
