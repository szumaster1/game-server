package content.region.misthalin.lumbridge.hamhideout.handlers

import core.api.*
import core.cache.def.impl.SceneryDefinition
import core.game.global.action.ClimbActionHandler
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RandomFunction
import org.rs.consts.Animations

/**
 * H.A.M. hideout plugin.
 */
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
                if (withinDistance(player, Location.create(3149, 9652, 0))) {
                    ClimbActionHandler.climb(player, Animation(Animations.HUMAN_CLIMB_STAIRS_828), Location(3165, 3251, 0))
                    return true
                }
                ClimbActionHandler.climbLadder(player, node.asScenery(), option)
                sendMessage(player, "You leave the HAM Fanatics' Camp.")
                return true
            }

            5490, 5491 -> when (option) {
                "open" -> {
                    if (getVarp(player, 174) == 0) {
                        sendMessage(player, "This trapdoor seems totally locked.")
                    } else {
                        setVarp(player, 346, 272731282)
                        ClimbActionHandler.climb(player, Animation(Animations.MULTI_USE_BEND_OVER_827), Location(3149, 9652, 0))
                        submitIndividualPulse(player, object : Pulse(2, player) {
                            override fun pulse(): Boolean {
                                setVarp(player, 174, 0)
                                return true
                            }
                        })
                    }
                }

                "close" -> {
                    setVarp(player, 174, 0)
                }

                "climb-down" -> when (id) {
                    5491 -> {
                        player.properties.teleportLocation = Location.create(3149, 9652, 0)
                        sendMessage(player, "You climb down through the trapdoor...")
                        sendMessage(player, "...and enter a dimly lit cavern area.")
                    }
                }

                "pick-lock" -> {
                    lock(player, 3)
                    animate(player, Animations.MULTI_USE_BEND_OVER_827)
                    sendMessage(player, "You attempt to pick the lock on the trap door.")
                    submitIndividualPulse(player, object : Pulse(2) {
                        override fun pulse(): Boolean {
                            animate(player, Animations.MULTI_USE_BEND_OVER_827)
                            sendMessage(player, "You attempt to pick the lock on the trap door.")
                            val success = RandomFunction.random(3) == 1
                            sendMessage(player, if (success) "You pick the lock on the trap door." else "You fail to pick the lock - your fingers get numb from fumbling with the lock.")
                            unlock(player)
                            if (success) {
                                setVarp(player, 174, 1 shl 14)
                                submitWorldPulse(object : Pulse(40, player) {
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

}
