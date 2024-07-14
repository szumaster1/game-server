package content.global.travel.glider

import core.api.consts.NPCs
import core.api.setVarp
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.Location

enum class GnomeGlider(val button: Int, val location: Location, val config: Int, val npc: Int) {
    TA_QUIR_PRIW(
        button = 16,
        location = Location.create(2465, 3501, 3),
        config = 9,
        npc = NPCs.CAPTAIN_DALBUR_3809
    ),
    SINDARPOS(
        button = 17,
        location = Location.create(2848, 3497, 0),
        config = 1,
        npc = NPCs.CAPTAIN_BLEEMADGE_3810
    ),
    LEMANTO_ADRA(
        button = 18,
        location = Location.create(3321, 3427, 0),
        config = 3,
        npc = -1
    ),
    AR_HEWO(
        button = 19,
        location = Location.create(3278, 3212, 0),
        config = 4,
        npc = NPCs.CAPTAIN_KLEMFOODLE_3812
    ),
    LEMANTOLLY_UNDRI(
        button = 20,
        location = Location.create(2544, 2970, 0),
        config = 10,
        npc = NPCs.GNORMADIUM_AVLAFRIM_1800
    ),
    CRASH_ISLAND(
        button = 14,
        location = Location.create(2894, 2726, 0),
        config = 8,
        npc = NPCs.CAPTAIN_ERRDO_3811
    ),
    GANDIUS(
        button = 15,
        location = Location.create(2972, 2969, 0),
        config = 8,
        npc = NPCs.CAPTAIN_ERRDO_3811
    );

    companion object {
        fun sendConfig(asNpc: NPC, player: Player) {
            val g = forNpc(asNpc.id)
            g?.let {
                setVarp(player, 153, it.config)
            }
        }

        fun forNpc(npc: Int): GnomeGlider? = values().find { it.npc == npc }
        fun forId(id: Int): GnomeGlider? = values().find { it.button == id }

    }

}
