package content.global.travel.glider

import core.api.consts.NPCs
import core.api.setVarp
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.Location

enum class GnomeGlider(val button: Int, val location: Location, val config: Int, val npc: Int) {
    TA_QUIR_PRIW(16,Location.create(2465, 3501, 3), 9, NPCs.CAPTAIN_DALBUR_3809),
    SINDARPOS(17, Location.create(2848, 3497, 0), 1, NPCs.CAPTAIN_BLEEMADGE_3810),
    LEMANTO_ADRA(18, Location.create(3321, 3427, 0), 3, -1),
    AR_HEWO(19, Location.create(3278, 3212, 0), 4, NPCs.CAPTAIN_KLEMFOODLE_3812),
    LEMANTOLLY_UNDRI(20, Location.create(2544, 2970, 0), 10, NPCs.GNORMADIUM_AVLAFRIM_1800),
    GANDIUS(15, Location.create(2972, 2969, 0), 8, NPCs.CAPTAIN_ERRDO_3811);

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