package content.global.skill.support.construction.decoration.questhall

import org.rs.consts.Graphics
import org.rs.consts.Sounds
import core.api.*
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Handles the construction Mounted Glory.
 * @author Splinter
 */
@Initializable
class MountedGloryPlugin : OptionHandler() {

    /**
     * Represents the teleport animation.
     */
    private val ANIMATION = Animation(714)

    /**
     * Represents the graphics to use.
     */
    private val GRAPHIC = Graphic(Graphics.TELEPORT_GRAPHIC_308, 100, 50)

    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any> {
        SceneryDefinition.forId(13523).handlers["option:rub"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val interpreter = player.dialogueInterpreter
        setTitle(player, 5)
        interpreter.sendOptions(
            "Where would you like to teleport to?",
            "Edgeville",
            "Karamja",
            "Draynor Village",
            "Al-Kharid",
            "Nowhere."
        )
        interpreter.addAction { player, buttonId ->
            when (buttonId) {
                2 -> teleport(player, Location.create(3087, 3495, 0))
                3 -> teleport(player, Location.create(2919, 3175, 0))
                4 -> teleport(player, Location.create(3081, 3250, 0))
                5 -> teleport(player, Location.create(3304, 3124, 0))
            }
        }
        return true
    }

    /**
     * Method used to teleport to a location.
     *
     * @param player the player.
     * @param location the location.
     */
    private fun teleport(player: Player, location: Location): Boolean {
        if (player.isTeleBlocked) {
            sendMessage(player, "A magical force has stopped you from teleporting.")
            return false
        }
        lock(player, 4)
        visualize(player, ANIMATION, GRAPHIC)
        playGlobalAudio(player.location, Sounds.TELEPORT_ALL_200)
        player.impactHandler.disabledTicks = 4
        GameWorld.Pulser.submit(object : Pulse(4, player) {
            override fun pulse(): Boolean {
                teleport(player, location)
                resetAnimator(player)
                return true
            }
        })
        return true
    }

}
