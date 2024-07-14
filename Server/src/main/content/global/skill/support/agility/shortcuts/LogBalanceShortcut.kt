package content.global.skill.support.agility.shortcuts

import content.global.skill.support.agility.AgilityHandler
import content.global.skill.support.agility.AgilityShortcut
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class LogBalanceShortcut(
    ids: IntArray,
    level: Int,
    experience: Double,
    private val start: Location,
    private val end: Location,
    vararg options: String
) : AgilityShortcut(ids, level, experience, *options) {

    init {
        configure(LogBalanceShortcut(intArrayOf(2296), 20, 5.0, Location(2598, 3477, 0), Location.create(2603, 3477, 0), "walk-across"))
        configure(LogBalanceShortcut(intArrayOf(2332), 1, 1.0, Location.create(2910, 3049, 0), Location.create(2906, 3049, 0), "cross"))
        configure(LogBalanceShortcut(intArrayOf(3933), 45, 1.0, Location.create(2290, 3232, 0), Location.create(2290, 3239, 0), "cross"))
        configure(LogBalanceShortcut(intArrayOf(3932), 45, 1.0, Location.create(2258, 3250, 0), Location.create(2264, 3250, 0), "cross"))
        configure(LogBalanceShortcut(intArrayOf(3931), 45, 1.0, Location.create(2202, 3237, 0), Location.create(2196, 3237, 0), "cross"))
    }

    override fun newInstance(arg: Any?): Plugin<Any?> {
        return this
    }

    override fun run(player: Player, scenery: Scenery, option: String, failed: Boolean) {
        var destination = start
        if (player.location.withinDistance(start) < player.location.withinDistance(end)) {
            destination = end
        }
        AgilityHandler.walk(player, -1, player.location, destination, Animation.create(155), experience, null)
    }

    override fun getDestination(node: Node, n: Node): Location {
        return if (node.location.withinDistance(start) < node.location.withinDistance(end)) {
            start
        } else {
            end
        }
    }
}
