package content.region.misthalin.draynor

import core.game.node.entity.Entity
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneBuilder
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Draynor mansion courtyard zone.
 */
@Initializable
class DraynorMansionCourtyardZone : MapZone("draynor-mansion-courtyard", true), Plugin<Any?> {

    override fun configure() {
        register(ZoneBorders(3100, 3333, 3114, 3346))
    }

    override fun enter(entity: Entity): Boolean {
        /*
         * Enter the courtyard of the spooky mansion in Draynor Village
         */
        if (entity.isPlayer) {
            val player = entity.asPlayer()
        }
        return super.enter(entity)
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
