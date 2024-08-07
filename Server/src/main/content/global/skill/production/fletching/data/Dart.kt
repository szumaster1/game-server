package content.global.skill.production.fletching.data

import core.api.consts.Items

/**
 * Dart
 *
 * @property unfinished
 * @property finished
 * @property level
 * @property experience
 * @constructor Dart
 */
enum class Dart(var unfinished: Int, var finished: Int, var level: Int, var experience: Double) {
    /**
     * Bronze Dart
     *
     * @constructor Bronze Dart
     */
    BRONZE_DART(
        unfinished = Items.BRONZE_DART_TIP_819,
        finished = Items.BRONZE_DART_806,
        level = 1,
        experience = 1.8
    ),

    /**
     * Iron Dart
     *
     * @constructor Iron Dart
     */
    IRON_DART(
        unfinished = Items.IRON_DART_TIP_820,
        finished = Items.IRON_DART_807,
        level = 22,
        experience = 3.8
    ),

    /**
     * Steel Dart
     *
     * @constructor Steel Dart
     */
    STEEL_DART(
        unfinished = Items.STEEL_DART_TIP_821,
        finished = Items.STEEL_DART_808,
        level = 37,
        experience = 7.5
    ),

    /**
     * Mithril Dart
     *
     * @constructor Mithril Dart
     */
    MITHRIL_DART(
        unfinished = Items.MITHRIL_DART_TIP_822,
        finished = Items.MITHRIL_DART_809,
        level = 52,
        experience = 11.2
    ),

    /**
     * Adamant Dart
     *
     * @constructor Adamant Dart
     */
    ADAMANT_DART(
        unfinished = Items.ADAMANT_DART_TIP_823,
        finished = Items.ADAMANT_DART_810,
        level = 67,
        experience = 15.0
    ),

    /**
     * Rune Dart
     *
     * @constructor Rune Dart
     */
    RUNE_DART(
        unfinished = Items.RUNE_DART_TIP_824,
        finished = Items.RUNE_DART_811,
        level = 81,
        experience = 18.8
    ),

    /**
     * Dragon Dart
     *
     * @constructor Dragon Dart
     */
    DRAGON_DART(
        unfinished = Items.DRAGON_DART_TIP_11232,
        finished = Items.DRAGON_DART_11230,
        level = 95,
        experience = 25.0
    );

    companion object {
        val productMap = HashMap<Int, Dart>()

        init {
            for (product in Dart.values()) {
                productMap[product.unfinished] = product
            }
        }

        fun isDart(id: Int): Boolean {
            return productMap[id] != null
        }
    }
}