package content.global.handlers.item.plugin

import cfg.consts.Animations
import cfg.consts.Components
import cfg.consts.Graphics
import cfg.consts.Items
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
import core.plugin.PluginManager
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Snow globe plugin.
 */
@Initializable
class SnowGlobePlugin : OptionHandler() {

    private val INTERFACE = Component(Components.SNOWGLOBE_INTERFACE_659)
    private val DOWNFAST = Animation(Animations.SNOWGLOBE_SNOW_FALL_FAST_7537)
    private val DOWNSLOW = Animation(Animations.SNOWGLOBE_SNOW_FALL_SLOW_7538)
    private val STOMP = Animation(Animations.SNOWGLOBE_STOMP_7528)
    private val SNOW = Graphic(Graphics.SNOW_FALLING_FROM_SNOW_GLOBE_1284)

    override fun newInstance(arg: Any?): Plugin<Any> {
        PluginManager.definePlugin(SnowGlobeInterface())
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        return true
    }

    /**
     * Snow globe interface.
     */
    inner class SnowGlobeInterface : ComponentPlugin() {
        override fun newInstance(arg: Any?): Plugin<Any?> {
            ComponentDefinition.put(INTERFACE.id, this)
            return this
        }

        override fun handle(player: Player, component: Component, opcode: Int, button: Int, slot: Int, itemId: Int): Boolean {
            if (button == 2) {
                player.interfaceManager.close()
                player.animator.animate(DOWNSLOW)
                player.pulseManager.run(object : Pulse(1) {
                    override fun pulse(): Boolean {
                        player.animator.animate(STOMP, SNOW)
                        player.inventory.add(Item(Items.SNOWBALL_11951, player.inventory.freeSlots()))
                        return true
                    }
                })
                return true
            }
            player.animator.animate(DOWNFAST)
            return true
        }
    }
}