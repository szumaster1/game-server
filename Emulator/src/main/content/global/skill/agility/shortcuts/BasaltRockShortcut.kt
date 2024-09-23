package content.global.skill.agility.shortcuts

import content.global.skill.agility.AgilityHandler
import content.global.skill.agility.AgilityShortcut
import core.api.lock
import core.api.sendMessage
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Basalt rock shortcut.
 */
@Initializable
class BasaltRockShortcut : AgilityShortcut {

    private val locations = mapOf(
        4550 to Location.create(2522, 3597, 0),
        4551 to Location.create(2522, 3595, 0),
        4552 to Location.create(2522, 3602, 0),
        4553 to Location.create(2522, 3600, 0),
        4554 to Location.create(2516, 3611, 0),
        4555 to Location.create(2518, 3611, 0),
        4556 to Location.create(2514, 3615, 0),
        4557 to Location.create(2514, 3613, 0),
        4558 to Location.create(2514, 3619, 0),
        4559 to Location.create(2514, 3617, 0)
    )

    constructor() : super(intArrayOf(), 0, 0.0, "")

    constructor(ids: IntArray?, level: Int, experience: Double, vararg options: String?) : super(ids, level, experience, *options)

    override fun newInstance(arg: Any?): Plugin<Any> {
        for (id in locations.keys) {
            configure(BasaltRockShortcut(intArrayOf(id), 1, 0.0, "jump-${if (id == 4550 || id == 4559) "to" else "across"}"))
        }
        return this
    }

    override fun getDestination(n: Node?, node: Node): Location? {
        return if (node is Scenery) locations[node.id] ?: super.getDestination(node, node) else null
    }

    override fun run(player: Player, obj: Scenery, option: String, failed: Boolean) {
        GameWorld.Pulser.submit(object : Pulse(1, player) {
            override fun pulse(): Boolean {
                val destination = locations[obj.id]
                if (destination != null && player.location != destination) {
                    lock(player, 3)
                    AgilityHandler.forceWalk(player, -1, destination, player.location, Animation.create(769), 20, 0.0, null, 1)
                } else {
                    sendMessage(player, "I can't reach.")
                }
                return true
            }
        })
    }
}
