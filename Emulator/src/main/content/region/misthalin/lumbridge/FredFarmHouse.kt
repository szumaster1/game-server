package content.region.misthalin.lumbridge

import core.game.node.entity.Entity
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneBuilder
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Fred farm house map area.
 */
@Initializable
class FredFarmHouse : MapZone("freds-farm-house", true), Plugin<Any?> {

    override fun configure() {
        register(ZoneBorders(3188, 3275, 3192, 3270))
    }

    override fun enter(entity: Entity): Boolean {
        /**
         * Visit Fred the Farmer's chicken and sheep farm.
         */
        if (entity.isPlayer) {
            val player = entity.asPlayer()
        }
        return super.enter(entity)
    }

    override fun newInstance(arg: Any?): Plugin<Any?> {
        ZoneBuilder.configure(this)
        return this
    }

    override fun fireEvent(identifier: String, vararg args: Any): Any? {
        return null
    }
}
