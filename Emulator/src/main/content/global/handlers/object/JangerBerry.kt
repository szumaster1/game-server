package content.global.handlers.`object`

import core.cache.def.impl.SceneryDefinition
import core.game.interaction.NodeUsageEvent
import core.game.interaction.OptionHandler
import core.game.interaction.UseWithHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.scenery.SceneryBuilder
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Janger berry plugin.
 */
@Initializable
class JangerBerry : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        UseWithHandler.addHandler(2326, UseWithHandler.OBJECT_TYPE, object : UseWithHandler(954) {

            override fun newInstance(arg: Any?): Plugin<Any> {
                return this
            }

            override fun handle(event: NodeUsageEvent): Boolean {
                val `object` = event.usedWith.asScenery()
                if (`object`.isActive) SceneryBuilder.replace(`object`, `object`.transform(2325))
                event.player.inventory.remove(event.usedItem)
                return true
            }
        })
        SceneryDefinition.forId(2325).handlers["option:swing-on"] = this
        SceneryDefinition.forId(2324).handlers["option:swing-on"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        if (!player.location.withinDistance(node.location, 2)) {
            player.sendMessage("I can't reach that.")
            return true
        }
        val end = if (node.id == 2325) Location(2505, 3087, 0) else Location(2511, 3096, 0)
        player.packetDispatch.sendSceneryAnimation(node.asScenery(), Animation.create(497), true)
        content.global.skill.agility.AgilityHandler.forceWalk(player, 0, player.location, end, Animation.create(751), 50, 22.0, "You skillfully swing across.", 1)
        return true
    }

    override fun getDestination(node: Node, n: Node): Location {
        return if (n.id == 2324) Location(2511, 3092, 0) else Location(2501, 3087, 0)
    }
}
