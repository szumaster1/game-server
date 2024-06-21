package content.data

import core.api.consts.Components
import core.api.consts.Items
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * @author Emperor, Vexia
 */
enum class LightSource(val level: Int, val raw: Item, val product: Item, val open: Boolean, val interfaceId: Int) {
    CANDLE(1, Item(Items.CANDLE_36, 1), Item(Items.LIT_CANDLE_33, 1), true, Components.DARKNESS_MEDIUM_98),
    BLACK_CANDLE(1, Item(Items.BLACK_CANDLE_38, 1), Item(Items.LIT_BLACK_CANDLE_32, 1), true, Components.DARKNESS_MEDIUM_98),
    TORCH(1, Item(Items.UNLIT_TORCH_596, 1), Item(Items.LIT_TORCH_594, 1), true, Components.DARKNESS_MEDIUM_98),
    CANDLE_LANTERN(4, Item(Items.CANDLE_LANTERN_4527, 1), Item(Items.CANDLE_LANTERN_4531, 1), false, Components.DARKNESS_MEDIUM_98),
    OIL_LAMP(12, Item(Items.OIL_LAMP_4522, 1), Item(Items.OIL_LAMP_4524, 1), true, Components.DARKNESS_LIGHT_97),
    OIL_LANTERN(26, Item(Items.OIL_LANTERN_4535, 1), Item(Items.OIL_LANTERN_4539, 1), false, Components.DARKNESS_LIGHT_97),
    BULLSEYE_LANTERN(49, Item(Items.BULLSEYE_LANTERN_4548, 1), Item(Items.BULLSEYE_LANTERN_4550, 1), false, -1),
    SAPPHIRE_LANTERN(49, Item(Items.SAPPHIRE_LANTERN_4701, 1), Item(Items.SAPPHIRE_LANTERN_4702, 1), false, -1),
    EMERALD_LANTERN(49, Item(Items.EMERALD_LANTERN_9064, 1), Item(Items.EMERALD_LANTERN_9065, 1), false, -1),
    MINING_HELMET(65, Item(Items.MINING_HELMET_5014, 1), Item(Items.MINING_HELMET_5013, 1), false, Components.DARKNESS_LIGHT_97);

    fun getStrength(): Int {
        return when (interfaceId) {
            Components.DARKNESS_LIGHT_97 -> 1
            Components.DARKNESS_MEDIUM_98 -> 2
            -1 -> 3
            else -> 0
        }
    }

    fun getName(): String {
        return name.lowercase().replace("_", " ")
    }

    companion object {

        @JvmStatic
        fun hasActiveLightSource(player: Player): Boolean {
            return getActiveLightSource(player) != null
        }

        @JvmStatic
        fun getActiveLightSource(player: Player): LightSource? {
            for (item in player.inventory.toArray()) {
                item?.let { forProductId(it.id)?.let { return it } }
            }
            for (item in player.equipment.toArray()) {
                item?.let { forProductId(it.id)?.let { return it } }
            }
            return null
        }

        @JvmStatic
        fun forId(id: Int): LightSource? {
            return values().firstOrNull { it.raw.id == id }
        }

        @JvmStatic
        fun forProductId(id: Int): LightSource? {
            return values().firstOrNull { it.product.id == id }
        }

        @JvmStatic
        fun getRawIds(): IntArray {
            return values().map { it.raw.id }.toIntArray()
        }
    }
}