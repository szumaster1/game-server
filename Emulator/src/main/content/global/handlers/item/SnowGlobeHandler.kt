package content.global.handlers.item

import core.game.component.Component
import core.game.component.ComponentDefinition
import core.game.component.ComponentPlugin
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.plugin.Plugin
import core.plugin.PluginManager
import org.rs.consts.Animations
import org.rs.consts.Components
import org.rs.consts.Graphics
import org.rs.consts.Items

/**
 * Handles snow globe item options.
 */
@Initializable
class SnowGlobeHandler : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        PluginManager.definePlugin(SnowGlobeInterface())
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        return true
    }

    inner class SnowGlobeInterface : ComponentPlugin() {
        override fun newInstance(arg: Any?): Plugin<Any?> {
            ComponentDefinition.put(Components.SNOWGLOBE_INTERFACE_659, this)
            return this
        }

        override fun handle(player: Player, component: Component, opcode: Int, button: Int, slot: Int, itemId: Int): Boolean {
            if (button == 2) {
                player.interfaceManager.close()
                player.animator.animate(Animation(Animations.SNOWGLOBE_SNOW_FALL_SLOW_7538))
                player.pulseManager.run(object : Pulse(1) {
                    override fun pulse(): Boolean {
                        player.animator.animate(
                            Animation(Animations.SNOWGLOBE_STOMP_7528),
                            Graphic(Graphics.SNOW_FALLING_FROM_SNOW_GLOBE_1284)
                        )
                        player.inventory.add(Item(Items.SNOWBALL_11951, player.inventory.freeSlots()))
                        return true
                    }
                })
                return true
            }
            player.animator.animate(Animation(Animations.SNOWGLOBE_SNOW_FALL_FAST_7537))
            return true
        }
    }
}