package content.global.activity.enchkey

import core.api.*
import cfg.consts.Items
import core.game.interaction.InteractionListener

/**
 * Represents the rewards for Enchanted key activity.
 */
class EnchantedKeyListener : InteractionListener {

    override fun defineListeners() {
        onDig(EnchantedKey.rellekkaTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_ATTR, 0) == 0) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_ATTR)
                addItemOrDrop(player, Items.STEEL_ARROW_886, 20)
                addItemOrDrop(player, Items.MITHRIL_ORE_448, 10)
                addItemOrDrop(player, Items.LAW_RUNE_563, 15)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.ardougneTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_ATTR, 0) == 1) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_ATTR)
                addItemOrDrop(player, Items.PURE_ESSENCE_7937, 36)
                addItemOrDrop(player, Items.IRON_ORE_441, 15)
                addItemOrDrop(player, Items.FIRE_RUNE_554, 30)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.benchTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_ATTR, 0) == 2) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_ATTR)
                addItemOrDrop(player, Items.PURE_ESSENCE_7937, 40)
                addItemOrDrop(player, Items.IRON_ARROWTIPS_40, 20)
                addItemOrDrop(player, Items.FIRE_RUNE_554, 20)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.gnomeTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_ATTR, 0) == 3) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_ATTR)
                addItemOrDrop(player, Items.PURE_ESSENCE_7937, 39)
                addItemOrDrop(player, Items.IRON_ARROWTIPS_40, 20)
                addItemOrDrop(player, Items.WATER_RUNE_555, 30)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.altarTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_ATTR, 0) == 4) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_ATTR)
                addItemOrDrop(player, Items.MITHRIL_ORE_448, 10)
                addItemOrDrop(player, Items.IRON_ORE_441, 15)
                addItemOrDrop(player, Items.EARTH_RUNE_557, 45)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.faladorTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_ATTR, 0) == 5) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_ATTR)
                addItemOrDrop(player, Items.EARTH_RUNE_557, 15)
                addItemOrDrop(player, Items.IRON_ARROW_884, 20)
                addItemOrDrop(player, Items.SARADOMIN_MJOLNIR_6762, 1)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.mudskipperTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_ATTR, 0) == 6) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_ATTR)
                addItemOrDrop(player, Items.IRON_ORE_441, 15)
                addItemOrDrop(player, Items.MITHRIL_ARROW_888, 20)
                addItemOrDrop(player, Items.DEATH_RUNE_560, 15)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.swampTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_ATTR, 0) == 7) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_ATTR)
                addItemOrDrop(player, Items.PURE_ESSENCE_7937, 29)
                addItemOrDrop(player, Items.MIND_RUNE_558, 20)
                addItemOrDrop(player, Items.STEEL_ARROW_886, 20)
                addItemOrDrop(player, Items.ZOMBIE_HEAD_6722, 1)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.alkharidTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_ATTR, 0) == 8) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_ATTR)
                addItemOrDrop(player, Items.PURE_ESSENCE_7937, 40)
                addItemOrDrop(player, Items.MITHRIL_ORE_448, 10)
                addItemOrDrop(player, Items.ZAMORAK_MJOLNIR_6764, 1)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.examTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_ATTR, 0) == 9) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_ATTR)
                addItemOrDrop(player, Items.PURE_ESSENCE_7937, 40)
                addItemOrDrop(player, Items.IRON_ORE_441, 15)
                addItemOrDrop(player, Items.GUTHIX_MJOLNIR_6760, 1)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.geTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_ATTR, 0) == 10) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_ATTR)
                addItemOrDrop(player, Items.PURE_ESSENCE_7937, 39)
                addItemOrDrop(player, Items.MITHRIL_ARROW_888, 10)
                addItemOrDrop(player, Items.LAW_RUNE_563, 15)
                sendMessage(player, "You found a treasure!")

                if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_ATTR, 0) == 11) {
                    sendMessage(player, "Congratulations! You have completed the Enchanted key mini-quest!")
                    removeItem(player, Items.ENCHANTED_KEY_6754)
                    removeAttribute(player, EnchantedKey.ENCHANTED_KEY_ATTR)
                }

                return@onDig
            }
        }

        onDig(EnchantedKey.gnomeballfieldTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_2_ATTR, 0) == 0) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_2_ATTR)
                addItemOrDrop(player, Items.COINS_995, 510)
                addItemOrDrop(player, Items.GOLD_CHARM_12158, 3)
                addItemOrDrop(player, Items.LAW_RUNE_563, 15)
                addItemOrDrop(player, Items.MITHRIL_ARROW_888, 20)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.shantaypassTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_2_ATTR, 0) == 1) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_2_ATTR)
                addItemOrDrop(player, Items.COINS_995, 530)
                addItemOrDrop(player, Items.GOLD_CHARM_12158, 3)
                addItemOrDrop(player, Items.PURE_ESSENCE_7937, 10)
                addItemOrDrop(player, Items.UNCUT_SAPPHIRE_1624, 3)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.brimhavenTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_2_ATTR, 0) == 2) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_2_ATTR)
                addItemOrDrop(player, Items.COINS_995, 560)
                addItemOrDrop(player, Items.GREEN_CHARM_12159, 1)
                addItemOrDrop(player, Items.COSMIC_RUNE_564, 5)
                addItemOrDrop(player, Items.UNCUT_EMERALD_1622, 2)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.wildernessTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_2_ATTR, 0) == 3) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_2_ATTR)
                addItemOrDrop(player, Items.COINS_995, 650)
                addItemOrDrop(player, Items.GREEN_CHARM_12159, 1)
                addItemOrDrop(player, Items.PURE_ESSENCE_7937, 10)
                addItemOrDrop(player, Items.UNCUT_RUBY_1620, 1)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.taibwowannaiTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_2_ATTR, 0) == 4) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_2_ATTR)
                addItemOrDrop(player, Items.COINS_995, 750)
                addItemOrDrop(player, Items.GREEN_CHARM_12159, 2)
                addItemOrDrop(player, Items.COSMIC_RUNE_564, 10)
                addItemOrDrop(player, Items.MITHRIL_ARROW_888, 30)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.feldiphillsTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_2_ATTR, 0) == 5) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_2_ATTR)
                addItemOrDrop(player, Items.COINS_995, 800)
                addItemOrDrop(player, Items.GOLD_CHARM_12158, 30)
                addItemOrDrop(player, Items.CRIMSON_CHARM_12160, 1)
                addItemOrDrop(player, Items.NATURE_RUNE_561, 15)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.agilitypyramidTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_2_ATTR, 0) == 6) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_2_ATTR)
                addItemOrDrop(player, Items.COINS_995, 830)
                addItemOrDrop(player, Items.CRIMSON_CHARM_12160, 1)
                addItemOrDrop(player, Items.DEATH_RUNE_560, 5)
                addItemOrDrop(player, Items.UNCUT_RUBY_1620, 2)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.banditcampTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_2_ATTR, 0) == 7) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_2_ATTR)
                addItemOrDrop(player, Items.COINS_995, 950)
                addItemOrDrop(player, Items.CRIMSON_CHARM_12160, 2)
                addItemOrDrop(player, Items.UNCUT_EMERALD_1621, 3)
                addItemOrDrop(player, Items.CHAOS_RUNE_562, 15)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.daemonheimTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_2_ATTR, 0) == 8) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_2_ATTR)
                addItemOrDrop(player, Items.COINS_995, 950)
                addItemOrDrop(player, Items.BLUE_CHARM_12163, 1)
                addItemOrDrop(player, Items.PURE_ESSENCE_7937, 20)
                addItemOrDrop(player, Items.GOLD_BAR_2358, 5)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.deathplateauTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_2_ATTR, 0) == 9) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_2_ATTR)
                addItemOrDrop(player, Items.COINS_995, 1010)
                addItemOrDrop(player, Items.BLUE_CHARM_12163, 1)
                addItemOrDrop(player, Items.PURE_ESSENCE_7937, 20)
                addItemOrDrop(player, Items.BLOOD_RUNE_565, 10)
                sendMessage(player, "You found a treasure!")
                return@onDig
            }
        }

        onDig(EnchantedKey.scorpionPitTreasure) { player ->
            if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_2_ATTR, 0) == 10) {
                player.incrementAttribute(EnchantedKey.ENCHANTED_KEY_2_ATTR)
                addItemOrDrop(player, Items.COINS_995, 1100)
                addItemOrDrop(player, Items.BLUE_CHARM_12163, 2)
                addItemOrDrop(player, Items.GOLD_BAR_2358, 15)
                addItemOrDrop(player, Items.DEATH_RUNE_560, 10)
                sendMessage(player, "You found a treasure!")

                if (getAttribute(player, EnchantedKey.ENCHANTED_KEY_2_ATTR, 0) == 11) {
                    sendMessage(player, "Congratulations! You have completed the Enchanted key mini-quest II!")
                    removeItem(player, Items.ENCHANTED_KEY_6754)
                    removeAttribute(player, EnchantedKey.ENCHANTED_KEY_2_ATTR)
                }

                return@onDig
            }
        }
    }
}