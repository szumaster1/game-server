package content.global.skill.support.construction.decoration.chapel

import core.api.*
import cfg.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction

/**
 * Burner listener.
 */
class BurnerListener : InteractionListener {

    private val burnerIds = intArrayOf(13202, 13203, 13204, 13205, 13206, 13207, 13208, 13209, 13210, 13211, 13212, 13213)

    override fun defineListeners() {
        on(burnerIds, IntType.SCENERY, "light") { player, node ->
            if (player.ironmanManager.checkRestriction() && !player.houseManager.isInHouse(player)) {
                return@on true
            }
            if (!inInventory(player, Items.TINDERBOX_590) || !inInventory(player, Items.CLEAN_MARRENTILL_251)) {
                sendDialogue(player, "You'll need a tinderbox and a clean marrentill herb in order to light the burner.")
                return@on true
            }
            if (removeItem(player, Item(Items.CLEAN_MARRENTILL_251))) {
                lock(player, 1)
                animate(player, Animation.create(3687))
                sendMessage(player, "You burn some marrentill in the incense burner.")
                SceneryBuilder.replace(
                    node.asScenery(), Scenery(node.asScenery().id + 1, node.location), RandomFunction.random(100, 175)
                )
            }
            return@on true
        }
    }
}
