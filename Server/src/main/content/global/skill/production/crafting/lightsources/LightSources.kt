package content.global.skill.production.crafting.lightsources

enum class LightSources(val emptyID: Int, val fullID: Int, val litID: Int, val lightSoundID : Int, val levelRequired: Int, val openFlame: Boolean) {
    WHITE_CANDLE(0, 36, 33, 3226, 0, true),
    BLACK_CANDLE(0, 38, 32, 3226, 0, true),
    TORCH(0, 596, 594, 3028,0, true),
    WHITE_CANDLE_LANTERN(4527, 4529, 4531,2305, 4, true),
    BLACK_CANDLE_LANTERN(4527, 4532, 4534,2305, 4, true),
    OIL_LAMP(4525, 4522, 4524, 2305,12, true),
    OIL_LANTERN(4535, 4537, 4539, 2305,26, false),
    BULLSEYE_LANTERN(4546, 4548, 4550, 2305,49, false),
    SAPPHIRE_LANTERN(0, 4701, 4702, 2305,49, false),
    MINING_HELMET(0, 5014, 5013, 2305,65, false),
    EMERALD_LANTERN(0, 9064, 9065, 2305,49, false);

    companion object {
        fun forId(id: Int): LightSources? {
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
                9064, 9065 -> EMERALD_LANTERN
                else -> null
            }
        }
    }
}
