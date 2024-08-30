package content.region.morytania.canifis

import cfg.consts.Items

/**
 * Represents the Stuffed item enum class.
 *
 * @param dropId the item to become stuffed id.
 * @param stuffedId the product id.
 * @param price the price.
 * @param message the message.
 */
enum class StuffedItem(val dropId: Int, val stuffedId: Int, val price: Int, val message: String) {
    /**
     * Crawling Hand Drop.
     */
    CRAWLING_HAND_DROP(
        dropId = Items.CRAWLING_HAND_7975,
        stuffedId = Items.CRAWLING_HAND_7982,
        price = 1000,
        message = "That's a very fine crawling hand."
    ),

    /**
     * Cockatrice Head Drop.
     */
    COCKATRICE_HEAD_DROP(
        dropId = Items.COCKATRICE_HEAD_7976,
        stuffedId = Items.COCKATRICE_HEAD_7983,
        price = 2000,
        message = "A cockatrice! Beautiful, isn't it? Look at the plumage!"
    ),

    /**
     * Basilisk Head Drop.
     */
    BASILISK_HEAD_DROP(
        dropId = Items.BASILISK_HEAD_7977,
        stuffedId = Items.BASILISK_HEAD_7984,
        price = 4000,
        message = "My, he's a scary-looking fellow, isn't he? He'll look good on your wall!"
    ),

    /**
     * Kurask Head Drop.
     */
    KURASK_HEAD_DROP(
        dropId = Items.KURASK_HEAD_7978,
        stuffedId = Items.KURASK_HEAD_7985,
        price = 6000,
        message = "A kurask? Splendid! Look at those horns!"
    ),

    /**
     * Abyssal Head Drop.
     */
    ABYSSAL_HEAD_DROP(
        dropId = Items.ABYSSAL_HEAD_7979,
        stuffedId = Items.ABYSSAL_HEAD_7986,
        price = 12000,
        message = "Goodness, an abyssal demon! See how it's still glowing?  I'll have to use some magic to preserve that."
    ),

    /**
     * Kbd Heads Drop.
     */
    KBD_HEADS_DROP(
        dropId = Items.KBD_HEADS_7980,
        stuffedId = Items.KBD_HEADS_7987,
        price = 50000,
        message = "This must be a King Black Dragon! I'll have to get out my heavy duty tools, this skin's as tough as iron!"
    ),

    /**
     * Kq Head Drop.
     */
    KQ_HEAD_DROP(
        dropId = Items.KQ_HEAD_7981,
        stuffedId = Items.KQ_HEAD_7988,
        price = 50000,
        message = "That must be the biggest kalphite I've ever seen! Preserving insects is always tricky. I'll have to be careful..."
    ),

    /**
     * Big Bass Drop.
     */
    BIG_BASS_DROP(
        dropId = Items.BIG_BASS_7989,
        stuffedId = Items.BIG_BASS_7990,
        price = 1000,
        message = "That's a mighty fine sea bass you've caught there."
    ),

    /**
     * Big Swordfish Drop.
     */
    BIG_SWORDFISH_DROP(
        dropId = Items.BIG_SWORDFISH_7991,
        stuffedId = Items.BIG_SWORDFISH_7992,
        price = 2500,
        message = "Don't point that thing at me!"
    ),

    /**
     * Big Shark Drop.
     */
    BIG_SHARK_DROP(
        dropId = Items.BIG_SHARK_7993,
        stuffedId = Items.BIG_SHARK_7994,
        price = 5000,
        message = "That's quite a fearsome shark! You've done everyone a service by removing it from the sea!"
    );


    companion object {
        val stuffedItemMap =  HashMap<Int, StuffedItem>()

        init {
            for (stuffedItem in StuffedItem.values()) {
                stuffedItemMap[stuffedItem.dropId] = stuffedItem
            }
        }
    }
}