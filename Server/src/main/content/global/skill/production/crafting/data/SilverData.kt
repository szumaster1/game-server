package content.global.skill.production.crafting.data

import core.api.consts.Items

private const val BUTTON_UNBLESSED = 16
private const val BUTTON_UNHOLY = 23
private const val BUTTON_SICKLE = 30
private const val BUTTON_TIARA = 44
private const val BUTTON_DEMONIC_SIGIL = 59
private const val BUTTON_SILVTHRIL_CHAIN = 73
private const val BUTTON_LIGHTNING_ROD = 37
private const val BUTTON_SILVTHRILL_ROD = 52
private const val BUTTON_CROSSBOW_BOLTS = 66

enum class SilverData(
    val buttonId: Int,
    val requiredItemId: Int,
    val producedItemId: Int,
    val amountProduced: Int,
    val minimumLevel: Int,
    val xpReward: Double,
    val strungId: Int
) {
    HOLY(
        buttonId = BUTTON_UNBLESSED,
        requiredItemId = Items.HOLY_MOULD_1599,
        producedItemId = Items.UNSTRUNG_SYMBOL_1714,
        amountProduced = 1,
        minimumLevel = 16,
        xpReward = 50.0,
        strungId = Items.UNBLESSED_SYMBOL_1716
    ),
    UNHOLY(
        buttonId = BUTTON_UNHOLY,
        requiredItemId = Items.UNHOLY_MOULD_1594,
        producedItemId = Items.UNSTRUNG_EMBLEM_1720,
        amountProduced = 1,
        minimumLevel = 17,
        xpReward = 50.0,
        strungId = Items.UNHOLY_SYMBOL_1724
    ),
    SICKLE(
        buttonId = BUTTON_SICKLE,
        requiredItemId = Items.SICKLE_MOULD_2976,
        producedItemId = Items.SILVER_SICKLE_2961,
        amountProduced = 1,
        minimumLevel = 18,
        xpReward = 50.0,
        strungId = -1
    ),
    TIARA(
        buttonId = BUTTON_TIARA,
        requiredItemId = Items.TIARA_MOULD_5523,
        producedItemId = Items.TIARA_5525,
        amountProduced = 1,
        minimumLevel = 23,
        xpReward = 52.5,
        strungId = -1
    ),
    SILVTHRIL_CHAIN(
        buttonId = BUTTON_SILVTHRIL_CHAIN,
        requiredItemId = Items.CHAIN_LINK_MOULD_13153,
        producedItemId = Items.SILVTHRIL_CHAIN_13154,
        amountProduced = 1,
        minimumLevel = 47,
        xpReward = 100.0,
        strungId = -1
    ),
    LIGHTNING_ROD(
        buttonId = BUTTON_LIGHTNING_ROD,
        requiredItemId = Items.CONDUCTOR_MOULD_4200,
        producedItemId = Items.CONDUCTOR_4201,
        amountProduced = 1,
        minimumLevel = 20,
        xpReward = 50.0,
        strungId = -1
    ),
    SILVTHRILL_ROD(
        buttonId = BUTTON_SILVTHRILL_ROD,
        requiredItemId = Items.ROD_CLAY_MOULD_7649,
        producedItemId = Items.SILVTHRILL_ROD_7637,
        amountProduced = 1,
        minimumLevel = 25,
        xpReward = 55.0,
        strungId = -1
    ),
    CROSSBOW_BOLTS(
        buttonId = BUTTON_CROSSBOW_BOLTS,
        requiredItemId = Items.BOLT_MOULD_9434,
        producedItemId = Items.SILVER_BOLTS_UNF_9382,
        amountProduced = 10,
        minimumLevel = 21,
        xpReward = 50.0,
        strungId = -1
    ),
    DEMONIC_SIGIL(
        buttonId = BUTTON_DEMONIC_SIGIL,
        requiredItemId = Items.DEMONIC_SIGIL_MOULD_6747,
        producedItemId = Items.DEMONIC_SIGIL_6748,
        amountProduced = 1,
        minimumLevel = 30,
        xpReward = 50.00,
        strungId = -1
    );

    companion object {
        private val BUTTON_MAP = HashMap<Int, SilverData>()
        private val PRODUCT_MAP = HashMap<Int, SilverData>()

        init {
            for (product in values()) {
                BUTTON_MAP[product.buttonId] = product
            }

            for (product in values()) {
                PRODUCT_MAP[product.producedItemId] = product
            }
        }

        fun forButtonID(buttonId: Int): SilverData? = BUTTON_MAP[buttonId]
        fun forProductID(productId: Int): SilverData? = PRODUCT_MAP[productId]
    }
}
