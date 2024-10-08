package content.region.wilderness.handlers

import core.api.*
import org.rs.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import org.rs.consts.Animations

class MuddyChestListener : InteractionListener {

    companion object {
        const val MUDDY_CHEST = org.rs.consts.Scenery.CLOSED_CHEST_170
        val MUDDY_CHEST_LOOT = arrayOf(Item(Items.UNCUT_RUBY_1619), Item(Items.MITHRIL_BAR_2359), Item(Items.MITHRIL_DAGGER_1209), Item(Items.ANCHOVY_PIZZA_2297), Item(Items.LAW_RUNE_563, 2), Item(Items.DEATH_RUNE_560, 2), Item(Items.CHAOS_RUNE_562, 10), Item(Items.COINS_995, 50))
    }

    override fun defineListeners() {

        /*
         * Muddy chest interaction.
         */

        on(MUDDY_CHEST, IntType.SCENERY, "open") { player, node ->
            val key = Item(Items.MUDDY_KEY_991)
            if (!removeItem(player, key)) {
                sendMessage(player, "This chest is locked and needs some sort of key.")
            } else {
                animate(player, Animations.OPEN_CHEST_536)
                replaceScenery(node.asScenery(), org.rs.consts.Scenery.OPEN_CHEST_171, 3, node.direction, node.location)
                for (item in MUDDY_CHEST_LOOT) {
                    if (!addItem(player, item.id)) GroundItemManager.create(item, player)
                }
            }
            return@on true
        }

    }
}