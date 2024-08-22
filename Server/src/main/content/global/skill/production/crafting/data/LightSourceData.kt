package content.global.skill.production.crafting.data

import cfg.consts.Items
import cfg.consts.Sounds

/**
 * Light source data
 *
 * @param emptyId Represents the ID for an empty light source.
 * @param fullId Represents the ID for a fully charged light source.
 * @param litId Represents the ID for a lit light source.
 * @param sfxId Represents the ID for the sound effect associated with the light source.
 * @param levelRequired Represents the level required to use this light source.
 * @param openFlame Indicates whether the light source produces an open flame.
 * @constructor Light source data Represents a new instance of LightSourceData with specified parameters.
 */
enum class LightSourceData(
    val emptyId: Int,      // ID for an empty light source
    val fullId: Int,       // ID for a fully charged light source
    val litId: Int,        // ID for a lit light source
    val sfxId: Int,        // ID for the sound effect associated with the light source
    val levelRequired: Int, // Level required to use this light source
    val openFlame: Boolean  // Indicates if the light source produces an open flame
) {
    /**
     * White Candle.
     */
    WHITE_CANDLE(
        emptyId = 0,
        fullId = Items.CANDLE_36,
        litId = Items.LIT_CANDLE_33,
        sfxId = Sounds.SKILL_LIGHT_CANDLE_3226,
        levelRequired = 0,
        true
    ),

    /**
     * Black Candle.
     */
    BLACK_CANDLE(
        emptyId = 0,
        fullId = Items.BLACK_CANDLE_38,
        litId = Items.LIT_BLACK_CANDLE_32,
        sfxId = Sounds.SKILL_LIGHT_CANDLE_3226,
        levelRequired = 0,
        true
    ),

    /**
     * Torch.
     */
    TORCH(
        emptyId = 0,
        fullId = Items.UNLIT_TORCH_596,
        litId = Items.LIT_TORCH_594,
        sfxId = Sounds.SLUG_TORCH_LIT_3028,
        levelRequired = 0,
        true
    ),

    /**
     * White Candle Lantern.
     */
    WHITE_CANDLE_LANTERN(
        emptyId = Items.CANDLE_LANTERN_4527,
        fullId = Items.CANDLE_LANTERN_4529,
        litId = Items.CANDLE_LANTERN_4531,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 4,
        true
    ),

    /**
     * Black Candle Lantern.
     */
    BLACK_CANDLE_LANTERN(
        emptyId = Items.CANDLE_LANTERN_4527,
        fullId = Items.CANDLE_LANTERN_4532,
        litId = Items.CANDLE_LANTERN_4534,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 4,
        true
    ),

    /**
     * Oil Lamp.
     */
    OIL_LAMP(
        emptyId = Items.OIL_LAMP_4525,
        fullId = Items.OIL_LAMP_4522,
        litId = Items.OIL_LAMP_4524,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 12,
        true
    ),

    /**
     * Bug Lantern.
     */
    BUG_LANTERN(
        emptyId = Items.UNLIT_BUG_LANTERN_7051,
        fullId = Items.UNLIT_BUG_LANTERN_7051,
        litId = Items.LIT_BUG_LANTERN_7053,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 33,
        false
    ),

    /**
     * Oil Lantern.
     */
    OIL_LANTERN(
        emptyId = Items.OIL_LANTERN_4535,
        fullId = Items.OIL_LANTERN_4537,
        litId = Items.OIL_LANTERN_4539,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 26,
        false
    ),

    /**
     * Bullseye Lantern.
     */
    BULLSEYE_LANTERN(
        emptyId = Items.BULLSEYE_LANTERN_4546,
        fullId = Items.BULLSEYE_LANTERN_4548,
        litId = Items.BULLSEYE_LANTERN_4550,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 49,
        false
    ),

    /**
     * Sapphire Lantern.
     */
    SAPPHIRE_LANTERN(
        emptyId = 0,
        fullId = Items.SAPPHIRE_LANTERN_4701,
        litId = Items.SAPPHIRE_LANTERN_4702,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 49,
        false
    ),

    /**
     * Mining Helmet.
     */
    MINING_HELMET(
        emptyId = 0,
        fullId = Items.MINING_HELMET_5014,
        litId = Items.MINING_HELMET_5013,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 65,
        false
    ),

    /**
     * Emerald Lantern.
     */
    EMERALD_LANTERN(
        emptyId = 0,
        fullId = Items.EMERALD_LANTERN_9064,
        litId = Items.EMERALD_LANTERN_9065,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 49,
        false
    );

    companion object {
        fun forId(id: Int): LightSourceData? {
            return when (id) {
                36, 33 -> WHITE_CANDLE
                38, 32 -> BLACK_CANDLE
                596, 594 -> TORCH
                4529, 4531 -> WHITE_CANDLE_LANTERN
                4532, 4534 -> BLACK_CANDLE_LANTERN
                4522, 4524 -> OIL_LAMP
                4537, 4539 -> OIL_LANTERN
                4548, 4550 -> BULLSEYE_LANTERN
                4701, 4702 -> SAPPHIRE_LANTERN
                5014, 5013 -> MINING_HELMET
                7051, 7053 -> BUG_LANTERN
                9064, 9065 -> EMERALD_LANTERN
                else -> null
            }
        }
    }
}
