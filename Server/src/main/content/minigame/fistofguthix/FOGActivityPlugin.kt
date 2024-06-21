package content.minigame.fistofguthix

import core.cache.def.impl.SceneryDefinition
import core.game.activity.ActivityPlugin
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders
import core.plugin.ClassScanner.definePlugin
import core.plugin.Plugin


class FOGActivityPlugin : ActivityPlugin("Fist of Guthix", false, true, true) {

    var round = 0

    @Throws(Throwable::class)
    override fun newInstance(p: Player): ActivityPlugin {
        return FOGActivityPlugin()
    }

    override fun getSpawnLocation(): Location? {
        return null
    }

    override fun configure() {
        definePlugin(FOGLobbyZone())
        definePlugin(FOGWaitingZone())
        definePlugin(object : OptionHandler() {
            @Throws(Throwable::class)
            override fun newInstance(arg: Any?): Plugin<Any> {
                SceneryDefinition.forId(30204).handlers["option:enter"] = this
                return this
            }

            override fun handle(player: Player, node: Node, option: String?): Boolean {
                when (node.id) {
                    30204 -> {
                        player.teleport(Location.create(1675, 5599, 0))
                        return true
                    }
                }
                return true
            }
        })
        register(ZoneBorders(1625, 5638, 1715, 5747))
    }

    companion object {
        const val MAX_PLAYERS = 250
        const val WAITING_INTERFACE = 731
    }
}