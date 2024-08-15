package content.data

import core.api.consts.Components
import core.api.consts.Items
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Represents a light source.
 * @author Vexia, Emperor
 *
 * @param level       the required level.
 * @param raw         the raw item id.
 * @param product     the product item id.
 * @param open        the open boolean.
 * @param interfaceId the interface id.
 */
enum class LightSource(val level: Int, val raw: Item, val product: Item, val open: Boolean, val interfaceId: Int) {
    /**
     * Candle.
     */
    CANDLE(
        level = 1,
        raw = Item(Items.CANDLE_36, 1),
        product = Item(Items.LIT_CANDLE_33, 1),
        open = true,
        interfaceId = Components.DARKNESS_MEDIUM_98
    ),

    /**
     * Black candle.
     */
    BLACK_CANDLE(
        level = 1,
        raw = Item(Items.BLACK_CANDLE_38, 1),
        product = Item(Items.LIT_BLACK_CANDLE_32, 1),
        open = true,
        interfaceId = Components.DARKNESS_MEDIUM_98
    ),

    /**
     * Torch.
     */
    TORCH(
        level = 1,
        raw = Item(Items.UNLIT_TORCH_596, 1),
        product = Item(Items.LIT_TORCH_594, 1),
        open = true,
        interfaceId = Components.DARKNESS_MEDIUM_98
    ),

    /**
     * Candle lantern.
     */
    CANDLE_LANTERN(
        level = 4,
        raw = Item(Items.CANDLE_LANTERN_4527, 1),
        product = Item(Items.CANDLE_LANTERN_4531, 1),
        open = false,
        interfaceId = Components.DARKNESS_MEDIUM_98
    ),

    /**
     * Oil lamp.
     */
    OIL_LAMP(
        level = 12,
        raw = Item(Items.OIL_LAMP_4522, 1),
        product = Item(Items.OIL_LAMP_4524, 1),
        open = true,
        interfaceId = Components.DARKNESS_LIGHT_97
    ),

    /**
     * Oil lantern.
     */
    OIL_LANTERN(
        level = 26,
        raw = Item(Items.OIL_LANTERN_4535, 1),
        product = Item(Items.OIL_LANTERN_4539, 1),
        open = false,
        interfaceId = Components.DARKNESS_LIGHT_97
    ),

    /**
     * Bug lantern.
     */
    BUG_LANTERN(
        level = 33,
        raw = Item(Items.UNLIT_BUG_LANTERN_7051, 1),
        product = Item(Items.LIT_BUG_LANTERN_7053, 1),
        open = false,
        interfaceId = -1
    ),

    /**
     * Bullseye lantern.
     */
    BULLSEYE_LANTERN(
        level = 49,
        raw = Item(Items.BULLSEYE_LANTERN_4548, 1),
        product = Item(Items.BULLSEYE_LANTERN_4550, 1),
        open = false,
        interfaceId = -1
    ),

    /**
     * Sapphire lantern.
     */
    SAPPHIRE_LANTERN(
        level = 49,
        raw = Item(Items.SAPPHIRE_LANTERN_4701, 1),
        product = Item(Items.SAPPHIRE_LANTERN_4702, 1),
        open = false,
        interfaceId = -1
    ),

    /**
     * Emerald lantern.
     */
    EMERALD_LANTERN(
        level = 49,
        raw = Item(Items.EMERALD_LANTERN_9064, 1),
        product = Item(Items.EMERALD_LANTERN_9065, 1),
        open = false,
        interfaceId = -1
    ),

    /**
     * Mining Helmet.
     */
    MINING_HELMET(
        level = 65,
        raw = Item(Items.MINING_HELMET_5014, 1),
        product = Item(Items.MINING_HELMET_5013, 1),
        open = false,
        interfaceId = Components.DARKNESS_LIGHT_97
    ),

    /**
     * Seers Headband 1.
     */
    SEERS_HEADBAND_1(
        level = 1,
        raw = Item(Items.SEERS_HEADBAND_1_14631, 1),
        product = Item(Items.SEERS_HEADBAND_1_14631, 1),
        open = false,
        interfaceId = -1
    ),

    /**
     * Seers Headband 2.
     */
    SEERS_HEADBAND_2(
        level = 1,
        raw = Item(Items.SEERS_HEADBAND_2_14659, 1),
        product = Item(Items.SEERS_HEADBAND_2_14659, 1),
        open = false,
        interfaceId = -1
    ),

    /**
     * Seers Headband 3.
     */
    SEERS_HEADBAND_3(
        level = 1,
        raw = Item(Items.SEERS_HEADBAND_3_14660, 1),
        product = Item(Items.SEERS_HEADBAND_3_14660, 1),
        open = false,
        interfaceId = -1
    )
    ;

    /**
     * Get strength of light.
     *
     * @return interface int.
     */
    fun getStrength(): Int {
        return when (interfaceId) {
            Components.DARKNESS_LIGHT_97 -> 1
            Components.DARKNESS_MEDIUM_98 -> 2
            -1 -> 3
            else -> 0
        }
    }

    /**
     * Get name.
     *
     * @return name to lowercase.
     */
    fun getName(): String {
        return name.lowercase().replace("_", " ")
    }

    companion object {

        /**
         * Check if the player has an active light source.
         *
         * @param player the player.
         * @return true if the player has an active light source, false otherwise.
         */
        @JvmStatic
        fun hasActiveLightSource(player: Player): Boolean {
            return getActiveLightSource(player) != null
        }

        /**
         * Get the active light source of the player.
         *
         * @param player the player.
         * @return the active light source of the player, or null if none.
         */
        @JvmStatic
        fun getActiveLightSource(player: Player): LightSource? {
            for (item in player.inventory.toArray()) {
                item?.let { it -> forProductId(it.id)?.let { return it } }
            }
            for (item in player.equipment.toArray()) {
                item?.let { it -> forProductId(it.id)?.let { return it } }
            }
            return null
        }

        /**
         * Get the LightSource enum for the given raw item id.
         *
         * @param id the raw item id.
         * @return the LightSource enum for the given raw item id, or null if none.
         */
        @JvmStatic
        fun forId(id: Int): LightSource? {
            return values().firstOrNull { it.raw.id == id }
        }

        /**
         * Get the LightSource enum for the given product item id.
         *
         * @param id the product item id.
         * @return the LightSource enum for the given product item id, or null if none.
         */
        @JvmStatic
        fun forProductId(id: Int): LightSource? {
            return values().firstOrNull { it.product.id == id }
        }

        /**
         * Get an array of raw item ids for all LightSource enums.
         *
         * @return an array of raw item ids.
         */
        @JvmStatic
        fun getRawIds(): IntArray {
            return values().map { it.raw.id }.toIntArray()
        }
    }
}
