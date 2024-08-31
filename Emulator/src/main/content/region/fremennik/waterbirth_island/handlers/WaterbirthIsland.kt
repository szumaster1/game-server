package content.region.fremennik.waterbirth_island.handlers

import core.api.closeOverlay
import cfg.consts.Components
import core.api.openInterface
import core.api.openOverlay
import core.api.teleport
import core.game.interaction.Option
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager
import core.game.world.map.Location
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneBuilder
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Waterbirth island zone.
 */
@Initializable
class WaterbirthIsland : MapZone("Water birth island", true), Plugin<Any?> {

    override fun newInstance(arg: Any?): Plugin<Any?> {
        ZoneBuilder.configure(this)
        return this
    }

    override fun fireEvent(identifier: String, vararg args: Any): Any? {
        return null
    }

    override fun interact(e: Entity, target: Node, option: Option): Boolean {
        if (e is Player) {
            val player = e.asPlayer()
            when (target.id) {
                2438 -> if (option.name == "Travel") {
                    player.dialogueInterpreter.open(target.id, target, true)
                    return true
                }

                8929 -> {
                    openInterface(player, Components.CWS_WARNING_1_574)
                    return true
                }

                8930 -> {
                    teleport(player, Location(2545, 10143, 0), TeleportManager.TeleportType.INSTANT)
                    return true
                }
            }
        }
        return super.interact(e, target, option)
    }

    override fun enter(e: Entity): Boolean {
        if (e is Player) {
            val player = e.asPlayer()
            openOverlay(player, 482)
        }
        return true
    }

    override fun leave(e: Entity, logout: Boolean): Boolean {
        if (e is Player) {
            val player = e.asPlayer()
            closeOverlay(player)
        }
        return true
    }

    override fun configure() {
        register(ZoneBorders(2487, 3711, 2565, 3776))
    }
}
