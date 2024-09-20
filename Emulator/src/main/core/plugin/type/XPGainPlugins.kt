package core.plugin.type

import core.game.node.entity.player.Player
import core.plugin.Plugin

/**
 * XP gain plugins.
 */
object XPGainPlugins {

    @JvmStatic
    private val plugins = ArrayList<XPGainPlugin>()

    @JvmStatic
    fun add(plugin: XPGainPlugin) {
        plugins.add(plugin)
    }

    @JvmStatic
    fun run(player: Player, skill: Int, amount: Double) {
        if (player.isArtificial) return
        plugins.forEach {
            it.run(player, skill, amount)
        }
    }
}

/**
 * Experience gain plugin.
 */
abstract class XPGainPlugin : Plugin<Any> {
    override fun newInstance(arg: Any?): Plugin<Any> {
        XPGainPlugins.add(this)
        return this
    }

    /**
     * Run method to be implemented by subclasses
     *
     * @param [player] the player object
     * @param [skill]  the skill affected
     * @param [amount] the amount of XP gained
     */
    abstract fun run(player: Player, skill: Int, amount: Double)
}
