package content.region.misthalin.handlers.lumbridge

import core.api.getVarp
import core.api.setVarp
import core.cache.def.impl.SceneryDefinition
import core.game.global.action.ClimbActionHandler
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin
import core.utilities.RandomFunction

@Initializable
class HamHideoutPlugin : OptionHandler() {
    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any?> {
        SceneryDefinition.forId(5490).handlers["option:open"] = this
        SceneryDefinition.forId(5490).handlers["option:pick-lock"] = this
        SceneryDefinition.forId(5491).handlers["option:close"] = this
        SceneryDefinition.forId(5491).handlers["option:climb-down"] = this
        SceneryDefinition.forId(5493).handlers["option:climb-up"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val id = node.id
        when (id) {
            5493 -> {
                if (player.location.withinDistance(Location.create(3149, 9652, 0))) {
                    ClimbActionHandler.climb(player, Animation(828), Location(3165, 3251, 0))
                    return true
                }
                ClimbActionHandler.climbLadder(player, node as Scenery, option)
                return true
            }

            5490, 5491 -> when (option) {
                "open" -> if (getVarp(player, 174) == 0) {
                    player.packetDispatch.sendMessage("This trapdoor seems totally locked.")
                } else {
                    setVarp(player, 346, 272731282)
                    ClimbActionHandler.climb(player, Animation(827), Location(3149, 9652, 0))
                    Pulser.submit(object : Pulse(2, player) {
                        override fun pulse(): Boolean {
                            setVarp(player, 174, 0)
                            return true
                        }
                    })
                }

                "close" -> setVarp(player, 174, 0)
                "climb-down" -> when (id) {
                    5491 -> player.properties.teleportLocation = Location.create(3149, 9652, 0)
                }

                "pick-lock" -> {
                    player.lock(3)
                    player.animate(ANIMATION)
                    player.packetDispatch.sendMessage("You attempt to pick the lock on the trap door.")
                    Pulser.submit(object : Pulse(2, player) {
                        override fun pulse(): Boolean {
                            player.animate(ANIMATION)
                            player.packetDispatch.sendMessage("You attempt to pick the lock on the trap door.")
                            val success = RandomFunction.random(3) == 1
                            player.packetDispatch.sendMessage(if (success) "You pick the lock on the trap door." else "You fail to pick the lock - your fingers get numb from fumbling with the lock.")
                            player.unlock()
                            if (success) {
                                setVarp(player, 174, 1 shl 14)
                                Pulser.submit(object : Pulse(40, player) {
                                    override fun pulse(): Boolean {
                                        setVarp(player, 174, 0)
                                        return true
                                    }
                                })
                            }
                            return true
                        }
                    })
                }
            }
        }
        return true
    }

    companion object {
        private val ANIMATION = Animation(827)
    }
}
