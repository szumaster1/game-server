package content.global.skill.production.herblore.data

import core.api.consts.Items
import core.game.node.item.Item

enum class GrindingItem(val items: Array<Item>, val product: Item, val message: String) {
    UNICORN_HORN(
        items = arrayOf(Item(Items.UNICORN_HORN_237)),
        product = Item(Items.UNICORN_HORN_DUST_235),
        message = "You grind the Unicorn horn to dust."
    ),
    KEBBIT_TEETH(
        items = arrayOf(Item(Items.KEBBIT_TEETH_10109)),
        product = Item(Items.KEBBIT_TEETH_DUST_10111),
        message = "You grind the Kebbit teeth to dust."
    ),
    BIRDS_NEST(
        items = arrayOf(Item(Items.BIRDS_NEST_5070), Item(Items.BIRDS_NEST_5071), Item(Items.BIRDS_NEST_5072), Item(Items.BIRDS_NEST_5073), Item(Items.BIRDS_NEST_5074), Item(Items.BIRDS_NEST_5075)),
        product = Item(Items.CRUSHED_NEST_6693),
        message = "You grind the Bird's nest down."
    ),
    GOAT_HORN(
        items = arrayOf(Item(Items.DESERT_GOAT_HORN_9735)),
        product = Item(Items.GOAT_HORN_DUST_9736),
        message = "You grind the goat's horn to dust."
    ),
    MUD_RUNE(
        items = arrayOf(Item(Items.MUD_RUNE_4698)),
        product = Item(Items.GROUND_MUD_RUNES_9594),
        message = "You grind the Mud rune down."
    ),
    ASHES(
        items = arrayOf(Item(Items.ASHES_592)),
        product = Item(Items.GROUND_ASHES_8865),
        message = "You grind down the ashes."
    ),
    RAW_KARAMBWAN(
        items = arrayOf(Item(Items.RAW_KARAMBWAN_3142)),
        product = Item(Items.KARAMBWAN_PASTE_3152),
        message = "You grind the raw Karambwan to form a sticky paste."
    ),
    POISON_KARAMBWAN(
        items = arrayOf(Item(Items.POISON_KARAMBWAN_3146)),
        product = Item(Items.KARAMBWAN_PASTE_3153),
        message = "You grind the cooked Karambwan to form a sticky paste."
    ),
    FISHING_BAIT(
        items = arrayOf(Item(Items.FISHING_BAIT_313)),
        product = Item(Items.GROUND_FISHING_BAIT_12129),
        message = "You grind down the Fishing bait."
    ),
    SEAWEED(
        items = arrayOf(Item(Items.SEAWEED_401)),
        product = Item(Items.GROUND_SEAWEED_6683),
        message = "You grind down the seaweed."
    ),
    BAT_BONES(
        items = arrayOf(Item(Items.BAT_BONES_530)),
        product = Item(Items.GROUND_BAT_BONES_2391),
        message = "You grind down the bat bone."
    ),
    CHARCOAL(
        items = arrayOf(Item(Items.CHARCOAL_973)),
        product = Item(Items.GROUND_CHARCOAL_704),
        message = "You grind the charcoal to a powder."
    ),
    ASTRAL_RUNE_SHARDS(
        items = arrayOf(Item(Items.ASTRAL_RUNE_SHARDS_11156)),
        product = Item(Items.GROUND_ASTRAL_RUNE_11155),
        message = "You grind down the Astral Rune shard's."
    ),
    GARLIC(
        items = arrayOf(Item(Items.GARLIC_1550)),
        product = Item(Items.GARLIC_POWDER_4668),
        message = "You grind the Garlic into a powder."
    ),
    DRAGON_SCALE(
        items = arrayOf(Item(Items.BLUE_DRAGON_SCALE_243)),
        product = Item(Items.DRAGON_SCALE_DUST_241),
        message = "You grind the Dragon scale to dust."
    ),
    ANCHOVIES(
        items = arrayOf(Item(Items.ANCHOVIES_319)),
        product = Item(Items.ANCHOVY_PASTE_11266),
        message = "You grind the anchovies into a fishy, sticky paste."
    ),
    CHOCOLATE_BAR(
        items = arrayOf(Item(Items.CHOCOLATE_BAR_1973)),
        product = Item(Items.CHOCOLATE_DUST_1975),
        message = "You grind the chocolate into dust."
    ),
    SULPHUR(items = arrayOf(Item(Items.SULPHUR_3209)),
        product = Item(Items.GROUND_SULPHUR_3215),
        message = "You grind down the sulphur."
    ),
    GUAM_LEAF(
        items = arrayOf(Item(Items.CLEAN_GUAM_249)),
        product = Item(Items.GROUND_GUAM_6681),
        message = "You grind down the guam."
    );

    companion object {
        fun forItem(item: Item): GrindingItem? {
            for (g in GrindingItem.values()) {
                for (i in g.items) {
                    if (i.id == item.id) {
                        return g
                    }
                }
            }
            return null
        }
    }
}
