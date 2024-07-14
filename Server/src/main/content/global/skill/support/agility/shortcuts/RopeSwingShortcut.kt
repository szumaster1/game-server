package content.global.skill.support.agility.shortcuts

import content.global.skill.support.agility.AgilityHandler
import core.api.sendMessage
import core.api.withinDistance
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.scenery.Scenery
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin

@Initializable
class RopeSwingShortcut : UseWithHandler() {

    override fun handle(event: NodeUsageEvent): Boolean {
        if (event.usedWith is Scenery) {
            val scenery = event.usedWith as Scenery
            val objId = scenery.id

            requireNotNull(event.usedItem) { "Used item cannot be null" }
            val usedId = event.usedItem.id

            val player = event.player

            if (objId == 2327 && usedId == 954) {
                if (!withinDistance(player, scenery.location, 2)) {
                    sendMessage(player, "I can't reach that.")
                    return true
                }
                player.packetDispatch.sendSceneryAnimation(scenery, Animation.create(497), true)
                AgilityHandler.forceWalk(player, 0, player.location, Location.create(2505, 3087, 0), Animation.create(751), 50, 22.0, "You skillfully swing across.", 1)
                return true
            }
        }
        return false
    }

    override fun newInstance(arg: Any): Plugin<Any?> {
        addHandler(2327, OBJECT_TYPE, this)
        return this
    }
}
