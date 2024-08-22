package content.global.skill.support.agility.shortcuts

import content.global.skill.support.agility.AgilityHandler
import core.api.animationDuration
import cfg.consts.Items
import cfg.consts.Sounds
import core.api.playAudio
import core.api.sendMessage
import core.api.withinDistance
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.scenery.Scenery
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Rope swing shortcut interaction.
 */
@Initializable
class RopeSwingShortcut : UseWithHandler() {

    override fun handle(event: NodeUsageEvent): Boolean {
        if (event.usedWith is Scenery) {
            val scenery = event.usedWith as Scenery
            val objId = scenery.id

            requireNotNull(event.usedItem) { "Used item cannot be null" }

            val usedId = event.usedItem.id
            ropeDelay = GameWorld.ticks + animationDuration(Animation.create(497))
            val player = event.player

            if (objId == cfg.consts.Scenery.LONG_BRANCHED_TREE_2327 && usedId == Items.ROPE_954) {
                if (!withinDistance(player, scenery.location, 2)) {
                    sendMessage(player, "I can't reach that.")
                    return true
                }
                if (ropeDelay > GameWorld.ticks) {
                    sendMessage(event.player, "The rope is being used.")
                    return true
                }
                playAudio(player, Sounds.SWING_ACROSS_2494, 1)
                player.packetDispatch.sendSceneryAnimation(scenery, Animation.create(497), true)
                AgilityHandler.forceWalk(player, 0, player.location, Location.create(2505, 3087, 0), Animation.create(751), 50, 22.0, "You skillfully swing across.", 1)
                return true
            }
        }
        return false
    }

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(2327, OBJECT_TYPE, this)
        return this
    }

    companion object {
        var ropeDelay = 0
    }
}
