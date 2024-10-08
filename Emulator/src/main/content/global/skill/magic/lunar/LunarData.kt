package content.global.skill.magic.lunar

import core.api.addItemOrDrop
import core.api.freeSlots
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Items

// Array of items in the Hunter Kit.
private val HunterKitContents = intArrayOf(
    Items.NOOSE_WAND_10150,
    Items.BUTTERFLY_NET_10010,
    Items.BIRD_SNARE_10006,
    Items.RABBIT_SNARE_10031,
    Items.TEASING_STICK_10029,
    Items.UNLIT_TORCH_596,
    Items.BOX_TRAP_10008
)

/**
 * Interaction listener for the Hunter Kit.
 */
class HunterKitInteraction : InteractionListener {

    override fun defineListeners() {
        // Listener for opening the Hunter Kit.
        on(Items.HUNTER_KIT_11159, IntType.ITEM, "open") { player, _ ->
            // Check if the player has enough inventory space.
            if(freeSlots(player) < 6) sendMessage(player, "You don't have enough inventory space.").also { return@on false }
            // Remove the Hunter Kit from the player's inventory.
            if(removeItem(player, Items.HUNTER_KIT_11159)) {
                // Add each item from the Hunter Kit Contents array to the player's inventory.
                for(item in HunterKitContents) {
                    addItemOrDrop(player, item)
                }
            }
            return@on true
        }
    }
}

/**
 * Enum class for String Jewellery items.
 */
enum class StringJewelleryItems(val unstrung: Int, val strung: Int) {
    GOLD(
        unstrung = Items.GOLD_AMULET_1673,
        strung = Items.GOLD_AMULET_1692
    ),
    SAPPHIRE(
        unstrung = Items.SAPPHIRE_AMULET_1675,
        strung = Items.SAPPHIRE_AMULET_1694
    ),
    EMERALD(
        unstrung = Items.EMERALD_AMULET_1677,
        strung = Items.EMERALD_AMULET_1696
    ),
    RUBY(
        unstrung = Items.RUBY_AMULET_1679,
        strung = Items.RUBY_AMULET_1698
    ),
    DIAMOND(
        unstrung = Items.DIAMOND_AMULET_1681,
        strung = Items.DIAMOND_AMULET_1700
    ),
    DRAGONSTONE(
        unstrung = Items.DRAGONSTONE_AMMY_1683,
        strung = Items.DRAGONSTONE_AMMY_1702
    ),
    ONYX(
        unstrung = Items.ONYX_AMULET_6579,
        strung = Items.ONYX_AMULET_6581
    ),
    SALVE(
        unstrung = Items.SALVE_SHARD_4082,
        strung = Items.SALVE_AMULET_4081
    ),
    HOLY(
        unstrung = Items.UNSTRUNG_SYMBOL_1714,
        strung = Items.UNBLESSED_SYMBOL_1716
    ),
    UNHOLY(
        unstrung = Items.UNSTRUNG_EMBLEM_1720,
        strung = Items.UNPOWERED_SYMBOL_1722
    );
    companion object {
        private val productOfString = values().associate { it.unstrung to it.strung }
        // Get the strung item ID for a given unstrung item ID.
        fun forId(id: Int): Int {
            return productOfString[id]!!
        }

        // Check if the unstrung item ID exists in the enum.
        fun unstrungContains(id: Int): Boolean {
            return productOfString.contains(id)
        }
    }
}

/**
 * Enum class for Humidify items.
 */
enum class HumidifyItems(val empty: Int, val full: Int) {
    VIAL(Items.VIAL_229, Items.VIAL_OF_WATER_227),
    WATERSKIN0(Items.WATERSKIN0_1831, Items.WATERSKIN4_1823),
    WATERSKIN1(Items.WATERSKIN1_1829, Items.WATERSKIN4_1823),
    WATERSKIN2(Items.WATERSKIN2_1827, Items.WATERSKIN4_1823),
    WATERSKIN3(Items.WATERSKIN3_1825, Items.WATERSKIN4_1823),
    BUCKET(Items.BUCKET_1925, Items.BUCKET_OF_WATER_1929),
    BOWL(Items.BOWL_1923, Items.BOWL_OF_WATER_1921),
    JUG(Items.JUG_1935, Items.JUG_OF_WATER_1937),
    WATERING_CAN0(Items.WATERING_CAN_5331, Items.WATERING_CAN8_5340),
    WATERING_CAN1(Items.WATERING_CAN1_5333, Items.WATERING_CAN8_5340),
    WATERING_CAN2(Items.WATERING_CAN2_5334, Items.WATERING_CAN8_5340),
    WATERING_CAN3(Items.WATERING_CAN3_5335, Items.WATERING_CAN8_5340),
    WATERING_CAN4(Items.WATERING_CAN4_5336, Items.WATERING_CAN8_5340),
    WATERING_CAN5(Items.WATERING_CAN5_5337, Items.WATERING_CAN8_5340),
    WATERING_CAN6(Items.WATERING_CAN6_5338, Items.WATERING_CAN8_5340),
    WATERING_CAN7(Items.WATERING_CAN7_5339, Items.WATERING_CAN8_5340),
    FISHBOWL(Items.FISHBOWL_6667, Items.FISHBOWL_6668),
    KETTLE(Items.KETTLE_7688, Items.FULL_KETTLE_7690),
    ENCHANTED_VIAL(Items.ENCHANTED_VIAL_731, Items.HOLY_WATER_732),
    CUP(Items.EMPTY_CUP_1980, Items.CUP_OF_WATER_4458);
    companion object {
        private val productOfFill = values().associate { it.empty to it.full }
        // Get the full item ID for a given empty item ID.
        fun forId(id: Int): Int {
            return productOfFill[id]!!
        }

        // Check if the empty item ID exists in the enum.
        fun emptyContains(id: Int): Boolean {
            return productOfFill.contains(id)
        }
    }
}
