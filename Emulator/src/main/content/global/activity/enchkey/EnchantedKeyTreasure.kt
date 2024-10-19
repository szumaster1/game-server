package content.global.activity.enchkey

import core.api.*
import org.rs.consts.Items
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.world.map.Location

/*
 * Data class to hold treasure information.
 */

data class Treasure(val rewards: List<Pair<Int, Int>>, val completionMessage: String? = null)

class EnchantedKeyListener : InteractionListener {

    /*
     * Handles map of treasures and their rewards after the Making history quest.
     */

    private val treasures = mapOf(
        EnchantedKey.rellekkaTreasure to Treasure(listOf(Items.STEEL_ARROW_886 to 20, Items.MITHRIL_ORE_448 to 10, Items.LAW_RUNE_563 to 15)),
        EnchantedKey.ardougneTreasure to Treasure(listOf(Items.PURE_ESSENCE_7937 to 36, Items.IRON_ORE_441 to 15, Items.FIRE_RUNE_554 to 30)),
        EnchantedKey.benchTreasure to Treasure(listOf(Items.PURE_ESSENCE_7937 to 40, Items.IRON_ARROWTIPS_40 to 20, Items.FIRE_RUNE_554 to 20)),
        EnchantedKey.gnomeTreasure to Treasure(listOf(Items.PURE_ESSENCE_7937 to 39, Items.IRON_ARROWTIPS_40 to 20, Items.WATER_RUNE_555 to 30)),
        EnchantedKey.altarTreasure to Treasure(listOf(Items.MITHRIL_ORE_448 to 10, Items.IRON_ORE_441 to 15, Items.EARTH_RUNE_557 to 45)),
        EnchantedKey.faladorTreasure to Treasure(listOf(Items.EARTH_RUNE_557 to 15, Items.IRON_ARROW_884 to 20, Items.SARADOMIN_MJOLNIR_6762 to 1)),
        EnchantedKey.mudskipperTreasure to Treasure(listOf(Items.IRON_ORE_441 to 15, Items.MITHRIL_ARROW_888 to 20, Items.DEATH_RUNE_560 to 15)),
        EnchantedKey.swampTreasure to Treasure(listOf(Items.PURE_ESSENCE_7937 to 29, Items.MIND_RUNE_558 to 20, Items.STEEL_ARROW_886 to 20, Items.ZOMBIE_HEAD_6722 to 1)),
        EnchantedKey.alkharidTreasure to Treasure(listOf(Items.PURE_ESSENCE_7937 to 40, Items.MITHRIL_ORE_448 to 10, Items.ZAMORAK_MJOLNIR_6764 to 1)),
        EnchantedKey.examTreasure to Treasure(listOf(Items.PURE_ESSENCE_7937 to 40, Items.IRON_ORE_441 to 15, Items.GUTHIX_MJOLNIR_6760 to 1)),
        EnchantedKey.geTreasure to Treasure(listOf(Items.PURE_ESSENCE_7937 to 39, Items.MITHRIL_ARROW_888 to 10, Items.LAW_RUNE_563 to 15), "Congratulations! You have completed the Enchanted key mini-quest!")
    )

    /*
     * Handles map of second quest treasures and their rewards.
     */

    private val secondQuestTreasures = mapOf(
        EnchantedKey.gnomeballfieldTreasure to Treasure(listOf(Items.COINS_995 to 510, Items.GOLD_CHARM_12158 to 3, Items.LAW_RUNE_563 to 15, Items.MITHRIL_ARROW_888 to 20)),
        EnchantedKey.shantaypassTreasure to Treasure(listOf(Items.COINS_995 to 530, Items.GOLD_CHARM_12158 to 3, Items.PURE_ESSENCE_7937 to 10, Items.UNCUT_SAPPHIRE_1624 to 3)),
        EnchantedKey.brimhavenTreasure to Treasure(listOf(Items.COINS_995 to 560, Items.GREEN_CHARM_12159 to 1, Items.COSMIC_RUNE_564 to 5, Items.UNCUT_EMERALD_1622 to 2)),
        EnchantedKey.wildernessTreasure to Treasure(listOf(Items.COINS_995 to 650, Items.GREEN_CHARM_12159 to 1, Items.PURE_ESSENCE_7937 to 10, Items.UNCUT_RUBY_1620 to 1)),
        EnchantedKey.taibwowannaiTreasure to Treasure(listOf(Items.COINS_995 to 750, Items.GREEN_CHARM_12159 to 2, Items.COSMIC_RUNE_564 to 10, Items.MITHRIL_ARROW_888 to 30)),
        EnchantedKey.feldiphillsTreasure to Treasure(listOf(Items.COINS_995 to 800, Items.GOLD_CHARM_12158 to 30, Items.CRIMSON_CHARM_12160 to 1, Items.NATURE_RUNE_561 to 15)),
        EnchantedKey.agilitypyramidTreasure to Treasure(listOf(Items.COINS_995 to 830, Items.CRIMSON_CHARM_12160 to 1, Items.DEATH_RUNE_560 to 5, Items.UNCUT_RUBY_1620 to 2)),
        EnchantedKey.banditcampTreasure to Treasure(listOf(Items.COINS_995 to 950, Items.CRIMSON_CHARM_12160 to 2, Items.UNCUT_EMERALD_1621 to 3, Items.CHAOS_RUNE_562 to 15)),
        EnchantedKey.daemonheimTreasure to Treasure(listOf(Items.COINS_995 to 950, Items.BLUE_CHARM_12163 to 1, Items.PURE_ESSENCE_7937 to 20, Items.GOLD_BAR_2358 to 5)),
        EnchantedKey.deathplateauTreasure to Treasure(listOf(Items.COINS_995 to 1010, Items.BLUE_CHARM_12163 to 1, Items.PURE_ESSENCE_7937 to 20, Items.BLOOD_RUNE_565 to 10)),
        EnchantedKey.scorpionPitTreasure to Treasure(listOf(Items.COINS_995 to 1100, Items.BLUE_CHARM_12163 to 2, Items.GOLD_BAR_2358 to 15, Items.DEATH_RUNE_560 to 10)),
    )

    override fun defineListeners() {

        /*
         * Handles the treasure in the first quest.
         */

        treasures.forEach { (location, treasure) ->
            onDig(location) { player ->
                handleTreasureDig(player, location, treasure, EnchantedKey.ENCHANTED_KEY_ATTR)
            }
        }

        /*
         * Handles the second quest treasures.
         */

        secondQuestTreasures.forEach { (location, treasure) ->
            onDig(location) { player ->
                handleTreasureDig(player, location, treasure, EnchantedKey.ENCHANTED_KEY_2_ATTR)
            }
        }
    }

    /*
     * Handles the logic for digging up treasures.
     */

    private fun handleTreasureDig(player: Player, location: Location, treasure: Treasure, attribute: String) {
        val currentProgress = getAttribute(player, attribute, 0)
        val expectedProgress = getLocationIndex(location, treasures)

        if (currentProgress == expectedProgress) {
            player.incrementAttribute(attribute)

            /*
             * Give the rewards to the player.
             */

            treasure.rewards.forEach { (item, amount) ->
                addItemOrDrop(player, item, amount)
            }

            sendMessage(player, "You found a treasure!")

            /*
             * Handle any completion messages or quest ending
             */

            if (treasure.completionMessage != null && currentProgress == treasures.size - 1) {
                sendMessage(player, treasure.completionMessage)
                removeItem(player, Items.ENCHANTED_KEY_6754)
                removeAttribute(player, attribute)
            }
        }
    }

    private fun getLocationIndex(location: Location, treasureMap: Map<Location, Treasure>): Int {
        return treasureMap.keys.indexOf(location)
    }
}
