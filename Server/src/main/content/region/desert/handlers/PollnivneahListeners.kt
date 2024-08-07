package content.region.desert.handlers

import content.region.desert.dialogue.pollnivneach.AliTheBarmanDialogue
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.NPCs
import core.api.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import core.game.world.map.Location

/**
 * Pollnivneah listeners.
 */
class PollnivneahListeners : InteractionListener {

    companion object {
        private const val BAR_TABLE = Scenery.TABLE_6246
        private const val BARMAN = NPCs.ALI_THE_BARMAN_1864
        private const val BANDIT = NPCs.BANDIT_6388
        private const val CAMEL = NPCs.ALI_THE_CAMEL_1873
        private const val MONEY_POT = Scenery.MONEY_POT_6230
        private const val COINS = Items.COINS_995
        private const val SNAKE_CHARMER = NPCs.ALI_THE_SNAKE_CHARMER_1872
    }

    override fun defineListeners() {

        on(CAMEL, IntType.NPC, "talk-to"){ player, _ ->
            openDialogue(player, CAMEL)
            return@on true
        }

        on(BANDIT, IntType.NPC, "talk-to"){ player, _ ->
            openDialogue(player, BANDIT)
            return@on true
        }

        on(BARMAN, IntType.NPC, "talk-to"){ player, _ ->
            openDialogue(player, AliTheBarmanDialogue())
            return@on true
        }

        on(BAR_TABLE, IntType.SCENERY, "take-beer") { player, node ->
            if (freeSlots(player) < 1) {
                sendDialogue(player, "You don't have enough inventory space.")
            } else {
                lock(player, 1)
                animate(player, Animations.HUMAN_MULTI_USE_832)
                replaceScenery(node.asScenery(), 602, 1500)
                addItem(player, Items.BEER_1917)
            }
            return@on true
        }

        /**
         * The Snake charmer basket interaction.
         */

        onUseWith(IntType.SCENERY, MONEY_POT, COINS){ player, _, _ ->
            if (removeItem(player, Item(Items.COINS_995, 3))) {
                player.dialogueInterpreter.open(SNAKE_CHARMER, true)
            }
            return@onUseWith true
        }
    }

    override fun defineDestinationOverrides() {
        setDest(IntType.NPC, intArrayOf(BARMAN), "talk-to") { _, _ ->
            return@setDest Location.create(3361, 2956, 0)
        }

    }

}