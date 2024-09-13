package content.global.travel.glider

import core.api.*
import cfg.consts.Components
import cfg.consts.NPCs
import core.api.utils.PlayerCamera
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.system.task.Pulse
import core.net.packet.PacketRepository
import core.net.packet.context.CameraContext
import core.net.packet.outgoing.CameraViewPacket

/**
 * Gnome glider listeners.
 */
class GnomeGliderListeners : InteractionListener {

    private val GNOME_PILOTS = intArrayOf(NPCs.CAPTAIN_DALBUR_3809, NPCs.CAPTAIN_BLEEMADGE_3810, NPCs.CAPTAIN_ERRDO_3811, NPCs.CAPTAIN_KLEMFOODLE_3812)

    override fun defineListeners() {

        on(GNOME_PILOTS, IntType.NPC, "glider") { player, _ ->
            if (!isQuestComplete(player, "The Grand Tree")) {
                sendMessage(player, "You must complete The Grand Tree Quest to access the gnome glider.")
            } else {
                openInterface(player, Components.GLIDERMAP_138)
            }
            return@on true
        }

        /*
         * Flashing landing light.
         * They are the result of repairing the gnome landing lights during One Small Favour.
         * Varbit 256
         */
    }

    /**
     * Represents a Glider Pulse that handles glider functionality for a player.
     *
     * @param player the player associated with the glider pulse
     * @param glider the glider object for the player
     * @constructor initializes the GliderPulse with a delay, player, and glider
     * @param delay the delay for the pulse
     */
    class GliderPulse(delay: Int, private val player: Player, private val glider: GnomeGlider) : Pulse(delay, player) {
        private var count: Int = 0

        init {
            lock(player, 100)
        }

        override fun pulse(): Boolean {
            val crash = glider == GnomeGlider.LEMANTO_ADRA
            if (count == 1) {
                setVarp(player, 153, glider.config)
                setMinimapState(player, 2)
            } else if (count == 2 && crash) {
                PacketRepository.send(CameraViewPacket::class.java, CameraContext(player, CameraContext.CameraType.SHAKE, 4, 4, 1200, 4, 4))
                sendMessage(player, "The glider almost gets blown from its path as it withstands heavy winds.")
            }
            when (count) {
                3 -> {
                    openOverlay(player, Components.FADE_TO_BLACK_115)
                }
                4 -> {
                    unlock(player)
                    player.properties.teleportLocation = glider.location
                }
                5 -> {
                    if (crash) {
                        PlayerCamera(player).reset()
                        sendMessage(player, "The glider becomes uncontrollable and crashes down...")
                    }
                    closeOverlay(player)
                    closeInterface(player)
                    setMinimapState(player, 0)
                    setVarp(player, 153, 0)
                    if (!crash && glider == GnomeGlider.GANDIUS) {
                        finishDiaryTask(player, DiaryType.KARAMJA, 1, 11)
                    }
                    return true
                }
            }
            count++
            return false
        }
    }
}
