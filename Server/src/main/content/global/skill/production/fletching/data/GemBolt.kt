package content.global.skill.production.fletching.data

import core.api.consts.Items

enum class GemBolt(var base: Int, var gem: Int, var tip: Int, var product: Int, var level: Int, var experience: Double) {
    OPAL(Items.BRONZE_BOLTS_877, Items.OPAL_1609, Items.OPAL_BOLT_TIPS_45, Items.OPAL_BOLTS_879, 11, 1.6),
    PEARL(Items.IRON_BOLTS_9140, Items.OYSTER_PEARL_411, Items.PEARL_BOLT_TIPS_46, Items.PEARL_BOLTS_880, 41, 3.2),
    PEARLS(Items.IRON_BOLTS_9140, Items.OYSTER_PEARLS_413, Items.PEARL_BOLT_TIPS_46, Items.PEARL_BOLTS_880, 41, 3.2),
    JADE(Items.BLURITE_BOLTS_9139, Items.JADE_1611, Items.JADE_BOLT_TIPS_9187, Items.JADE_BOLTS_9335, 26, 2.4),
    RED_TOPAZ(Items.STEEL_BOLTS_9141, Items.RED_TOPAZ_1613, Items.TOPAZ_BOLT_TIPS_9188, Items.TOPAZ_BOLTS_9336, 48, 3.9),
    SAPPHIRE(Items.MITHRIL_BOLTS_9142, Items.SAPPHIRE_1607, Items.SAPPHIRE_BOLT_TIPS_9189, Items.SAPPHIRE_BOLTS_9337, 56, 4.7),
    EMERALD(Items.MITHRIL_BOLTS_9142, Items.EMERALD_1605, Items.EMERALD_BOLT_TIPS_9190, Items.EMERALD_BOLTS_9338, 58, 5.5),
    RUBY(Items.ADAMANT_BOLTS_9143, Items.RUBY_1603, Items.RUBY_BOLT_TIPS_9191, Items.RUBY_BOLTS_9339, 63, 6.3),
    DIAMOND(Items.ADAMANT_BOLTS_9143, Items.DIAMOND_1601, Items.DIAMOND_BOLT_TIPS_9192, Items.DIAMOND_BOLTS_9340, 65, 7.0),
    DRAGONSTONE(Items.RUNE_BOLTS_9144, Items.DRAGONSTONE_1615, Items.DRAGON_BOLT_TIPS_9193, Items.DRAGON_BOLTS_9341, 71, 8.2),
    ONYX(Items.RUNE_BOLTS_9144, Items.ONYX_6573, Items.ONYX_BOLT_TIPS_9194, Items.ONYX_BOLTS_9342, 73, 9.4);

    companion object {
        val productMap = HashMap<Int, GemBolt>()

        init {
            for (gem in GemBolt.values()) {
                productMap[gem.gem] = gem
            }
            for (tip in GemBolt.values()) {
                productMap[tip.gem] = tip
            }
        }
    }
}
