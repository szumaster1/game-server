package core.plugin.type

import core.game.node.entity.player.Player
import core.plugin.Plugin

/**
 * XP gain plugins.
 */
object XPGainPlugins {

    // List to store XPGainPlugin instances
    @JvmStatic
    private val plugins = ArrayList<XPGainPlugin>()

    // Add XPGainPlugin to the list of plugins
    @JvmStatic
    fun add(plugin: XPGainPlugin) {
        plugins.add(plugin)
    }

    // Run XPGainPlugins for a player, skill, and amount
    @JvmStatic
    fun run(player: Player, skill: Int, amount: Double) {
        if (player.isArtificial) return // Skip if player is artificial
        plugins.forEach {
            it.run(player, skill, amount) // Execute XPGainPlugin for each plugin
        }
    }
}

/**
 * XP gain plugin
 *
 * @constructor XP gain plugin
 */
abstract class XPGainPlugin : Plugin<Any> {
    override fun newInstance(arg: Any?): Plugin<Any> {
        XPGainPlugins.add(this) // Add the XPGainPlugin instance to the plugins list
        return this
    }

    /**
     * Run method to be implemented by subclasses
     *
     * @param player The player object
     * @param skill  The skill affected
     * @param amount The amount of XP gained
     */
    abstract fun run(player: Player, skill: Int, amount: Double)
}
