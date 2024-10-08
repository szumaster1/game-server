package content.global.travel.glider

import core.api.setVarp
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import org.rs.consts.NPCs

/**
 * Gnome glider.
 *
 * @param button   The button ID for the gnome glider.
 * @param location The location where the gnome glider is located.
 * @param config   The configuration value for the gnome glider.
 * @param npc      The NPC associated with the gnome glider.
 * @constructor Creates a new instance of the GnomeGlider enum.
 */
enum class Glider(val button: Int, val location: Location, val config: Int, val npc: Int) {
    /*
     * Crash Island.
     */
    CRASH_ISLAND(
            button = 14,
            location = Location.create(2894, 2726, 0),
            config = 8,
            npc = NPCs.CAPTAIN_ERRDO_3811
    ),
    /*
     * South-Eastern Karamja.
     */
    GANDIUS(
            button = 15,
            location = Location.create(2972, 2969, 0),
            config = 8,
            npc = NPCs.CAPTAIN_KLEMFOODLE_3812
    ),
    /*
     * The Grand Tree.
     */
    TA_QUIR_PRIW(
            button = 16,
            location = Location.create(2465, 3501, 3),
            config = 9,
            npc = NPCs.CAPTAIN_DALBUR_3809
    ),

    /*
     * Top of the White Wolf Mountain.
     */
    SINDARPOS(
            button = 17,
            location = Location.create(2848, 3497, 0),
            config = 1,
            npc = NPCs.CAPTAIN_BLEEMADGE_3810
    ),

    /*
     * Just west of the Digsite near Varrock.
     */
    LEMANTO_ADRA(
            button = 18,
            location = Location.create(3321, 3427, 0),
            config = 3,
            npc = NPCs.CAPTAIN_ERRDO_3811
    ),

    /*
     * Al Kharid.
     */
    KAR_HEWO(
            button = 19,
            location = Location.create(3278, 3212, 0),
            config = 4,
            npc = NPCs.CAPTAIN_KLEMFOODLE_3812
    ),

    /*
     * Feldip Hills.
     */
    LEMANTOLLY_UNDRI(
            button = 20,
            location = Location.create(2544, 2970, 0),
            config = 10,
            npc = NPCs.GNORMADIUM_AVLAFRIM_1800
    );

    companion object {
        fun sendConfig(npc: NPC, player: Player) {
            val g = forNpc(npc.id)
            g?.let {
                setVarp(player, 153, it.config)
            }
        }

        fun forNpc(npc: Int): Glider? = values().find { it.npc == npc }

        fun forId(id: Int): Glider? = values().find { it.button == id }
    }
}
