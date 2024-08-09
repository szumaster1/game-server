package content.global.travel.glider

import core.api.*
import core.api.consts.Components
import core.api.consts.NPCs
import core.api.utils.PlayerCamera
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.system.task.Pulse
import core.network.packet.PacketRepository
import core.network.packet.context.CameraContext
import core.network.packet.outgoing.CameraViewPacket

/**
 * Gnome glider listeners.
 */
class GnomeGliderListeners : InteractionListener {

    override fun defineListeners() {
        // Add comment to explain the purpose of this listener
        // This listener is triggered when a player interacts with a gnome glider NPC
        on(intArrayOf(NPCs.CAPTAIN_DALBUR_3809, NPCs.CAPTAIN_BLEEMADGE_3810, NPCs.CAPTAIN_ERRDO_3811, NPCs.CAPTAIN_KLEMFOODLE_3812), IntType.NPC, "glider") { player, node ->
            if (!isQuestComplete(player, "The Grand Tree")) {
                sendMessage(player, "You must complete The Grand Tree Quest to access the gnome glider.")
            } else {
                openInterface(player, Components.GLIDERMAP_138)
                GnomeGlider.sendConfig(node.asNpc(), player)
            }
            return@on true
        }
    }

    /**
     * Represents a Glider Pulse that handles glider functionality for a player.
     *
     * @property player the player associated with the glider pulse
     * @property glider the glider object for the player
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
                PacketRepository.send(
                    CameraViewPacket::class.java,
                    CameraContext(player, CameraContext.CameraType.SHAKE, 4, 4, 1200, 4, 4)
                )
                sendMessage(player, "The glider almost gets blown from its path as it withstands heavy winds.")
            }
            if (count == 3) {
                openOverlay(player, Components.FADE_TO_BLACK_115)
            } else if (count == 4) {
                unlock(player)
                player.properties.teleportLocation = glider.location
            } else if (count == 5) {
                if (crash) {
                    PlayerCamera(player).reset()
                    sendMessage(player, "The glider becomes uncontrollable and crashes down...")
                }
                closeOverlay(player)
                closeInterface(player)
                setMinimapState(player, 0)
                setVarp(player, 153, 0)
                if (!crash && glider == GnomeGlider.GANDIUS) {
                    player.achievementDiaryManager.finishTask(player, DiaryType.KARAMJA, 1, 11)
                }
                return true
            }
            count++
            return false
        }
    }
}
