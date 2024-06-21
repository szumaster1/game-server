package content.global.skill.production.herblore

import core.api.consts.Items
import core.game.node.item.Item

enum class GrindingItem(val items: Array<Item>, val product: Item, val message: String) {
    UNICORN_HORN(arrayOf(Item(Items.UNICORN_HORN_237)), Item(Items.UNICORN_HORN_DUST_235), "You grind the Unicorn horn to dust."),
    KEBBIT_TEETH(arrayOf(Item(Items.KEBBIT_TEETH_10109)), Item(Items.KEBBIT_TEETH_DUST_10111), "You grind the Kebbit teeth to dust."),
    BIRDS_NEST(arrayOf(Item(Items.BIRDS_NEST_5070), Item(Items.BIRDS_NEST_5071), Item(Items.BIRDS_NEST_5072), Item(Items.BIRDS_NEST_5073), Item(Items.BIRDS_NEST_5074), Item(Items.BIRDS_NEST_5075)), Item(Items.CRUSHED_NEST_6693), "You grind the Bird's nest down."),
    GOAT_HORN(arrayOf(Item(Items.DESERT_GOAT_HORN_9735)), Item(Items.GOAT_HORN_DUST_9736), "You grind the goat's horn to dust."),
    MUD_RUNE(arrayOf(Item(Items.MUD_RUNE_4698)), Item(Items.GROUND_MUD_RUNES_9594), "You grind the Mud rune down."),
    ASHES(arrayOf(Item(Items.ASHES_592)), Item(Items.GROUND_ASHES_8865), "You grind down the ashes."),
    RAW_KARAMBWAN(arrayOf(Item(Items.RAW_KARAMBWAN_3142)), Item(Items.KARAMBWAN_PASTE_3152), "You grind the raw Karambwan to form a sticky paste."),
    POISON_KARAMBWAN(arrayOf(Item(Items.POISON_KARAMBWAN_3146)), Item(Items.KARAMBWAN_PASTE_3153), "You grind the cooked Karambwan to form a sticky paste."),
    FISHING_BAIT(arrayOf(Item(Items.FISHING_BAIT_313)), Item(Items.GROUND_FISHING_BAIT_12129), "You grind down the Fishing bait."),
    SEAWEED(arrayOf(Item(Items.SEAWEED_401)), Item(Items.GROUND_SEAWEED_6683), "You grind down the seaweed."),
    BAT_BONES(arrayOf(Item(Items.BAT_BONES_530)), Item(Items.GROUND_BAT_BONES_2391), "You grind down the bat bone."),
    CHARCOAL(arrayOf(Item(Items.CHARCOAL_973)), Item(Items.GROUND_CHARCOAL_704), "You grind the charcoal to a powder."),
    ASTRAL_RUNE_SHARDS(arrayOf(Item(Items.ASTRAL_RUNE_SHARDS_11156)), Item(Items.GROUND_ASTRAL_RUNE_11155), "You grind down the Astral Rune shard's."),
    GARLIC(arrayOf(Item(Items.GARLIC_1550)), Item(Items.GARLIC_POWDER_4668), "You grind the Garlic into a powder."),
    DRAGON_SCALE(arrayOf(Item(Items.BLUE_DRAGON_SCALE_243)), Item(Items.DRAGON_SCALE_DUST_241), "You grind the Dragon scale to dust."),
    ANCHOVIES(arrayOf(Item(Items.ANCHOVIES_319)), Item(Items.ANCHOVY_PASTE_11266), "You grind the anchovies into a fishy, sticky paste."),
    CHOCOLATE_BAR(arrayOf(Item(Items.CHOCOLATE_BAR_1973)), Item(Items.CHOCOLATE_DUST_1975), "You grind the chocolate into dust."),
    SULPHUR(arrayOf(Item(Items.SULPHUR_3209)), Item(Items.GROUND_SULPHUR_3215), "You grind down the sulphur."),
    GUAM_LEAF(arrayOf(Item(Items.CLEAN_GUAM_249)), Item(Items.GROUND_GUAM_6681), "You grind down the guam.");

    companion object {

        @JvmStatic
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