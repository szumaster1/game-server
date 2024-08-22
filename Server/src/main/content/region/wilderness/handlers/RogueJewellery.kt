package content.region.wilderness.handlers

import cfg.consts.Items

/**
 * Represents the Rogue jewellery enum class.
 *
 * @param item the item id.
 * @param amount amount.
 * @param price the price.
 */
enum class RogueJewellery(val item: Int, val amount: Int, val price: Int) {
    /**
     * Gold Ring.
     */
    GOLD_RING(
        item = Items.GOLD_RING_1635,
        amount = 1,
        price = 350
    ),

    /**
     * Gold Ring (Noted).
     */
    GOLD_RING_NOTED(
        item = Items.GOLD_RING_1636,
        amount = 1,
        price = 350
    ),

    /**
     * Sapphire Ring.
     */
    SAPPHIRE_RING(
        item = Items.SAPPHIRE_RING_1637,
        amount = 1,
        price = 900
    ),

    /**
     * Sapphire Ring (Noted).
     */
    SAPPHIRE_RING_NOTED(
        item = Items.SAPPHIRE_RING_1638,
        amount = 1,
        price = 900
    ),

    /**
     * Emerald Ring.
     */
    EMERALD_RING(
        item = Items.EMERALD_RING_1639,
        amount = 1,
        price = 1275
    ),

    /**
     * Emerald Ring (Noted).
     */
    EMERALD_RING_NOTED(
        item = Items.EMERALD_RING_1640,
        amount = 1,
        price = 1275
    ),

    /**
     * Ruby Ring.
     */
    RUBY_RING(
        item = Items.RUBY_RING_1641,
        amount = 1,
        price = 2025
    ),

    /**
     * Ruby Ring (Noted).
     */
    RUBY_RING_NOTED(
        item = Items.RUBY_RING_1642,
        amount = 1,
        price = 2025
    ),

    /**
     * Diamond Ring.
     */
    DIAMOND_RING(
        item = Items.DIAMOND_RING_1643,
        amount = 1,
        price = 3525
    ),

    /**
     * Diamond Ring (Noted).
     */
    DIAMOND_RING_NOTED(
        item = Items.DIAMOND_RING_1644,
        amount = 1,
        price = 3525
    ),

    /**
     * Dragonstone Ring.
     */
    DRAGONSTONE_RING(
        item = Items.DRAGONSTONE_RING_1645,
        amount = 1,
        price = 17625
    ),

    /**
     * Dragonstone Ring (Noted).
     */
    DRAGONSTONE_RING_NOTED(
        item = Items.DRAGONSTONE_RING_1646,
        amount = 1,
        price = 17625
    ),

    /**
     * Gold Necklace.
     */
    GOLD_NECKLACE(
        item = Items.GOLD_NECKLACE_1654,
        amount = 1,
        price = 450
    ),

    /**
     * Gold Necklace (Noted).
     */
    GOLD_NECKLACE_NOTED(
        item = Items.GOLD_NECKLACE_1655,
        amount = 1,
        price = 450
    ),

    /**
     * Sapphire Necklace.
     */
    SAPPHIRE_NECKLACE(
        item = Items.SAPPHIRE_NECKLACE_1656,
        amount = 1,
        price = 1050
    ),

    /**
     * Sapphire Necklace (Noted).
     */
    SAPPHIRE_NECKLACE_NOTED(
        item = Items.SAPPHIRE_NECKLACE_1657,
        amount = 1,
        price = 1050
    ),

    /**
     * Emerald Necklace.
     */
    EMERALD_NECKLACE(
        item = Items.EMERALD_NECKLACE_1658,
        amount = 1,
        price = 1425
    ),

    /**
     * Emerald Necklace (Noted).
     */
    EMERALD_NECKLACE_NOTED(
        item = Items.EMERALD_NECKLACE_1659,
        amount = 1,
        price = 1425
    ),

    /**
     * Ruby Necklace.
     */
    RUBY_NECKLACE(
        item = Items.RUBY_NECKLACE_1660,
        amount = 1,
        price = 2175
    ),

    /**
     * Ruby Necklace (Noted).
     */
    RUBY_NECKLACE_NOTED(
        item = Items.RUBY_NECKLACE_1661,
        amount = 1,
        price = 2175
    ),

    /**
     * Diamond Necklace.
     */
    DIAMOND_NECKLACE(
        item = Items.DIAMOND_NECKLACE_1662,
        amount = 1,
        price = 3675
    ),

    /**
     * Diamond Necklace (Noted).
     */
    DIAMOND_NECKLACE_NOTED(
        item = Items.DIAMOND_NECKLACE_1663,
        amount = 1,
        price = 3675
    ),

    /**
     * Dragon Necklace.
     */
    DRAGON_NECKLACE(
        item = Items.DRAGON_NECKLACE_1664,
        amount = 1,
        price = 18375
    ),

    /**
     * Dragon Necklace (Noted).
     */
    DRAGON_NECKLACE_NOTED(
        item = Items.DRAGON_NECKLACE_1665,
        amount = 1,
        price = 18375
    ),

    /**
     * Gold Bracelet.
     */
    GOLD_BRACELET(
        item = Items.GOLD_BRACELET_11069,
        amount = 1,
        price = 550
    ),

    /**
     * Gold Bracelet (Noted).
     */
    GOLD_BRACELET_NOTED(
        item = Items.GOLD_BRACELET_11070,
        amount = 1,
        price = 550
    ),

    /**
     * Sapphire Bracelet.
     */
    SAPPHIRE_BRACELET(
        Items.SAPPHIRE_BRACELET_11072,
        1,
        1150
    ),

    /**
     * Sapphire Bracelet (Noted).
     */
    SAPPHIRE_BRACELET_NOTED(
        item = Items.SAPPHIRE_BRACELET_11073,
        amount = 1,
        price = 1150
    ),

    /**
     * Emerald Bracelet.
     */
    EMERALD_BRACELET(
        item = Items.EMERALD_BRACELET_11076,
        amount = 1,
        price = 1525
    ),

    /**
     * Emerald Bracelet (Noted).
     */
    EMERALD_BRACELET_NOTED(
        item = Items.EMERALD_BRACELET_11077,
        amount = 1,
        price = 1525
    ),

    /**
     * Ruby Bracelet.
     */
    RUBY_BRACELET(
        item = Items.RUBY_BRACELET_11085,
        amount = 1,
        price = 2325
    ),

    /**
     * Ruby Bracelet (Noted).
     */
    RUBY_BRACELET_NOTED(
        item = Items.RUBY_BRACELET_11086,
        amount = 1,
        price = 2325
    ),

    /**
     * Diamond Bracelet.
     */
    DIAMOND_BRACELET(
        item = Items.DIAMOND_BRACELET_11092,
        amount = 1,
        price = 3825
    ),

    /**
     * Diamond Bracelet (Noted).
     */
    DIAMOND_BRACELET_NOTED(
        item = Items.DIAMOND_BRACELET_11093,
        amount = 1,
        price = 3825
    ),

    /**
     * Dragon Bracelet.
     */
    DRAGON_BRACELET(
        item = Items.DRAGON_BRACELET_11115,
        amount = 1,
        price = 19125
    ),

    /**
     * Dragon Bracelet (Noted).
     */
    DRAGON_BRACELET_NOTED(
        item = Items.DRAGON_BRACELET_11116,
        amount = 1,
        price = 19125
    ),

    /**
     * Gold Amulet.
     */
    GOLD_AMULET(
        item = Items.GOLD_AMULET_1692,
        amount = 1,
        price = 350
    ),

    /**
     * Gold Amulet (Noted).
     */
    GOLD_AMULET_NOTED(
        item = Items.GOLD_AMULET_1693,
        amount = 1,
        price = 350
    ),

    /**
     * Sapphire Amulet.
     */
    SAPPHIRE_AMULET(
        item = Items.SAPPHIRE_AMULET_1694,
        amount = 1,
        price = 900
    ),

    /**
     * Sapphire Amulet (Noted).
     */
    SAPPHIRE_AMULET_NOTED(
        item = Items.SAPPHIRE_AMULET_1695,
        amount = 1,
        price = 900
    ),

    /**
     * Emerald Amulet.
     */
    EMERALD_AMULET(
        item = Items.EMERALD_AMULET_1696,
        amount = 1,
        price = 1275
    ),

    /**
     * Emerald Amulet (Noted).
     */
    EMERALD_AMULET_NOTED(
        item = Items.EMERALD_AMULET_1697,
        amount = 1,
        price = 1275
    ),

    /**
     * Ruby Amulet.
     */
    RUBY_AMULET(
        item = Items.RUBY_AMULET_1698,
        amount = 1,
        price = 2025
    ),

    /**
     * Ruby Amulet (Noted).
     */
    RUBY_AMULET_NOTED(
        item = Items.RUBY_AMULET_1699,
        amount = 1,
        price = 2025
    ),

    /**
     * Diamond Amulet.
     */
    DIAMOND_AMULET(
        item = Items.DIAMOND_AMULET_1700,
        amount = 1,
        price = 3525
    ),

    /**
     * Diamond Amulet (Noted).
     */
    DIAMOND_AMULET_NOTED(
        item = Items.DIAMOND_AMULET_1701,
        amount = 1,
        price = 3525
    ),

    /**
     * Dragonstone Ammy.
     */
    DRAGONSTONE_AMMY(
        item = Items.DRAGONSTONE_AMMY_1702,
        amount = 1,
        price = 17625
    ),

    /**
     * Dragonstone Ammy (Noted).
     */
    DRAGONSTONE_AMMY_NOTED(
        item = Items.DRAGONSTONE_AMMY_1703,
        amount = 1,
        price = 17625
    );


    companion object {
        val JewelleryMap = HashMap<Int, RogueJewellery>()

        init {
            for (antiqueItem in values()) {
                JewelleryMap[antiqueItem.item] = antiqueItem
            }
        }
    }

}