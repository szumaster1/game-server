package content.global.skill.crafting.glassblowing.lamps

import org.rs.consts.Items
import org.rs.consts.Sounds

/**
 * Represents the light source items.
 */
enum class LightSource(val emptyId: Int, val fullId: Int, val litId: Int, val sfxId: Int, val levelRequired: Int, val openFlame: Boolean) {
    WHITE_CANDLE(
        emptyId = 0,
        fullId = Items.CANDLE_36,
        litId = Items.LIT_CANDLE_33,
        sfxId = Sounds.SKILL_LIGHT_CANDLE_3226,
        levelRequired = 0,
        openFlame = true
    ),
    BLACK_CANDLE(
        emptyId = 0,
        fullId = Items.BLACK_CANDLE_38,
        litId = Items.LIT_BLACK_CANDLE_32,
        sfxId = Sounds.SKILL_LIGHT_CANDLE_3226,
        levelRequired = 0,
        openFlame = true
    ),
    TORCH(
        emptyId = 0,
        fullId = Items.UNLIT_TORCH_596,
        litId = Items.LIT_TORCH_594,
        sfxId = Sounds.SLUG_TORCH_LIT_3028,
        levelRequired = 0,
        openFlame = true
    ),
    WHITE_CANDLE_LANTERN(
        emptyId = Items.CANDLE_LANTERN_4527,
        fullId = Items.CANDLE_LANTERN_4529,
        litId = Items.CANDLE_LANTERN_4531,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 4,
        openFlame = true
    ),
    BLACK_CANDLE_LANTERN(
        emptyId = Items.CANDLE_LANTERN_4527,
        fullId = Items.CANDLE_LANTERN_4532,
        litId = Items.CANDLE_LANTERN_4534,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 4,
        openFlame = true
    ),
    OIL_LAMP(
        emptyId = Items.OIL_LAMP_4525,
        fullId = Items.OIL_LAMP_4522,
        litId = Items.OIL_LAMP_4524,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 12,
        openFlame = true
    ),
    BUG_LANTERN(
        emptyId = Items.UNLIT_BUG_LANTERN_7051,
        fullId = Items.UNLIT_BUG_LANTERN_7051,
        litId = Items.LIT_BUG_LANTERN_7053,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 33,
        openFlame = false
    ),
    OIL_LANTERN(
        emptyId = Items.OIL_LANTERN_4535,
        fullId = Items.OIL_LANTERN_4537,
        litId = Items.OIL_LANTERN_4539,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 26,
        openFlame = false
    ),
    BULLSEYE_LANTERN(
        emptyId = Items.BULLSEYE_LANTERN_4546,
        fullId = Items.BULLSEYE_LANTERN_4548,
        litId = Items.BULLSEYE_LANTERN_4550,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 49,
        openFlame = false
    ),
    SAPPHIRE_LANTERN(
        emptyId = 0,
        fullId = Items.SAPPHIRE_LANTERN_4701,
        litId = Items.SAPPHIRE_LANTERN_4702,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 49,
        openFlame = false
    ),
    MINING_HELMET(
        emptyId = 0,
        fullId = Items.MINING_HELMET_5014,
        litId = Items.MINING_HELMET_5013,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 65,
        openFlame = false
    ),
    EMERALD_LANTERN(
        emptyId = 0,
        fullId = Items.EMERALD_LANTERN_9064,
        litId = Items.EMERALD_LANTERN_9065,
        sfxId = Sounds.LIGHT_CANDLE_2305,
        levelRequired = 49,
        openFlame = false
    );

    companion object {
        @JvmStatic
        fun forId(id: Int): LightSource? {
            return values().find { it.fullId == id || it.litId == id }
        }
    }
}