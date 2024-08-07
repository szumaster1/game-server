package content.region.misthalin.handlers.lumbridge

import core.GlobalStats.incrementDailyCowDeaths
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneBuilder
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Lumbridge cow map area.
 */
@Initializable
class LumbridgeCowMapArea : MapZone("lumbridge cows", true), Plugin<Any?> {

    override fun fireEvent(identifier: String, vararg args: Any): Any? {
        return null
    }

    override fun configure() {
        super.register(ZoneBorders(3242, 3255, 3265, 3297))
    }

    override fun newInstance(arg: Any?): Plugin<Any?> {
        ZoneBuilder.configure(this)
        return this
    }

    override fun death(e: Entity, killer: Entity): Boolean {
        if (killer is Player && e is NPC) {
            incrementDailyCowDeaths()
        }
        return false
    }

    companion object {
        var cowDeaths: Int = 0
    }
}
