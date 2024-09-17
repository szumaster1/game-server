package content.region.karamja.shilovillage.handlers

import org.rs.consts.Items
import core.game.node.item.Item

/**
 * Represents the antique item.
 */
enum class AntiqueItem(val item: Item, val price: Int, val message: String, val dialogue: String){
    BONE_KEY(
        item = Item(Items.BONE_KEY_605, 1),
        price = 100,
        message = "I'll give you 100 coins for the Bone Key...",
        dialogue = "That's a great bone key."
    ),
    STONE_PLAQUE(
        item = Item(Items.STONE_PLAQUE_606, 1),
        price = 100,
        message = "I'll give you 100 coins for the Stone Plaque...",
        dialogue = "That's a great stone-plaque."
    ),
    TATTERED_SCROLL(
        item = Item(Items.TATTERED_SCROLL_607, 1),
        price = 100,
        message = "I'll give you 100 coins for the tattered scroll...",
        dialogue = "That's a great tattered scroll."
    ),
    CRUMPLED_SCROLL(
        item = Item(Items.CRUMPLED_SCROLL_608, 1),
        price = 100,
        message = "I'll give you 100 coins for the crumpled scroll...",
        dialogue = "That's a great crumpled scroll."
    ),
    LOCATING_CRYSTAL(
        item = Item(Items.LOCATING_CRYSTAL_611, 1),
        price = 500,
        message = "I'll give you 500 coins for your locating crystal...",
        dialogue = "That's a great Locating Crystal."
    ),
    BEADS_OF_THE_DEAD(
        item = Item(Items.BEADS_OF_THE_DEAD_616, 1),
        price = 1000,
        message = "I'll give you 1000 coins for your 'Beads of the Dead'...",
        dialogue = "Impressive necklace there."
    ),
    BERVIRIUS_NOTES(
        item = Item(Items.BERVIRIUS_NOTES_624, 1),
        price = 100,
        message = "I'll give you 100 coins for your bervirius scroll...",
        dialogue = "That's a great copy of Bervirius notes."
    ),
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