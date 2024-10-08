package content.global.skill.construction.decoration.study

import content.global.activity.star.ShootingStarPlugin
import core.api.animate
import core.api.lock
import core.api.sendDialogue
import core.api.unlock
import core.cache.def.impl.SceneryDefinition
import core.game.component.Component
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RandomFunction
import java.util.concurrent.TimeUnit

/**
 * Telescope option handler.
 */
@Initializable
class TelescopeOptionHandler : OptionHandler() {
    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any?> {
        SceneryDefinition.forId(13656).handlers["option:observe"] = this
        SceneryDefinition.forId(13657).handlers["option:observe"] = this
        SceneryDefinition.forId(13658).handlers["option:observe"] = this
        return this
    }

    override fun handle(player: Player?, node: Node?, option: String?): Boolean {
        val star = ShootingStarPlugin.getStar()
        val delay: Int = 25000 + (25000 / 3)
        val timeLeft = delay - star.ticks
        val fakeTime =
            TimeUnit.MILLISECONDS.toMinutes(timeLeft * 600L) + if (RandomFunction.random(0, 100) % 2 == 0) 2 else -2
        val obj = node?.asScenery() as Scenery
        lock(player!!, 10000)
        animate(player, ANIMATION)
        player.interfaceManager?.open(Component(782)).also {
            unlock(player)
            Pulser.submit(object : Pulse(2, player) {
                override fun pulse(): Boolean {
                    if (obj.isActive) {
                        sendDialogue(
                            player,
                            "You see a shooting star! The star looks like it will land in about $fakeTime minutes!"
                        )
                        return true
                    }
                    return true
                }
            })
            return true
        }
    }

    companion object {
        private val ANIMATION = Animation.create(3649)
    }
}
