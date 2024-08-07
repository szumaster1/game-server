package content.region.karamja.handlers.shilovillage

import core.api.consts.Items
import core.game.node.item.Item

/**
 * Antique item enum class.
 *
 * @property item     the item id.
 * @property price    the offered price.
 * @property message  the message.
 * @property dialogue the dialogue.
 * @constructor Antique item
 */
enum class AntiqueItem(val item: Item, val price: Int, val message: String, val dialogue: String){
    /**
     * Bone Key.
     */
    BONE_KEY(
        item = Item(Items.BONE_KEY_605, 1),
        price = 100,
        message = "I'll give you 100 coins for the Bone Key...",
        dialogue = "That's a great bone key."
    ),

    /**
     * Stone Plaque.
     */
    STONE_PLAQUE(
        item = Item(Items.STONE_PLAQUE_606, 1),
        price = 100,
        message = "I'll give you 100 coins for the Stone Plaque...",
        dialogue = "That's a great stone-plaque."
    ),

    /**
     * Tattered Scroll.
     */
    TATTERED_SCROLL(
        item = Item(Items.TATTERED_SCROLL_607, 1),
        price = 100,
        message = "I'll give you 100 coins for the tattered scroll...",
        dialogue = "That's a great tattered scroll."
    ),

    /**
     * Crumpled Scroll.
     */
    CRUMPLED_SCROLL(
        item = Item(Items.CRUMPLED_SCROLL_608, 1),
        price = 100,
        message = "I'll give you 100 coins for the crumpled scroll...",
        dialogue = "That's a great crumpled scroll."
    ),

    /**
     * Locating Crystal.
     */
    LOCATING_CRYSTAL(
        item = Item(Items.LOCATING_CRYSTAL_611, 1),
        price = 500,
        message = "I'll give you 500 coins for your locating crystal...",
        dialogue = "That's a great Locating Crystal."
    ),

    /**
     * Beads Of The Dead.
     */
    BEADS_OF_THE_DEAD(
        item = Item(Items.BEADS_OF_THE_DEAD_616, 1),
        price = 1000,
        message = "I'll give you 1000 coins for your 'Beads of the Dead'...",
        dialogue = "Impressive necklace there."
    ),

    /**
     * Bervirius Notes.
     */
    BERVIRIUS_NOTES(
        item = Item(Items.BERVIRIUS_NOTES_624, 1),
        price = 100,
        message = "I'll give you 100 coins for your bervirius scroll...",
        dialogue = "That's a great copy of Bervirius notes."
    ),

    /**
     * Black Prism.
     */
    BLACK_PRISM(
        item = Item(Items.BLACK_PRISM_4808, 1),
        price = 5000,
        message = "I'll give you 5000 coins for your Black prism...",
        dialogue = "Ah you'd like to sell this to me would you? I can offer you 5000 coins!"
    );

    companion object {
        val antiqueMap = HashMap<Int, AntiqueItem>()

        init {
            for (antiqueItem in values()) {
                antiqueMap[antiqueItem.item.id] = antiqueItem
            }
        }
    }
}