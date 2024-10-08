package content.global.skill.crafting.pottery

import core.api.openDialogue
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the plugin used for the fire pottery object.
 */
@Initializable
class FireOptionPotteryPlugin : OptionHandler() {

    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any> {
        SceneryDefinition.forId(2643).handlers["option:fire"] = this
        SceneryDefinition.forId(4308).handlers["option:fire"] = this
        SceneryDefinition.forId(11601).handlers["option:fire"] = this
        SceneryDefinition.forId(34802).handlers["option:fire"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        player.faceLocation(node.location)
        openDialogue(player, 99843, true, true)
        return true
    }
}
