package content.global.travel.ship

import core.api.*
import core.api.consts.Components
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.system.task.Pulse
import core.tools.StringUtils

/**
 * Ship travel pulse
 *
 * @property player
 * @property ship
 * @constructor Ship travel pulse
 */
class ShipTravelPulse(private val player: Player, private val ship: Ships) : Pulse(1) {

    private var counter = 0

    override fun pulse(): Boolean {
        when (counter++) {
            0 -> prepare()
            1 -> if (ship != Ships.PORT_SARIM_TO_CRANDOR) {
                player.properties.teleportLocation = ship.location
            }
            else -> if (counter == ship.delay) {
                arrive()
                return true
            }
        }
        return false
    }

    private fun arrive() {
        unlock(player)
        setVarp(player, 75, 0)
        closeInterface(player)
        setMinimapState(player, 0)
        if (ship.destination != "Crandor") {
            sendDialogue(player, "The ship arrives at " + StringUtils.formatDisplayName(ship.destination) + ".")
            closeInterface(player)
        } else {
            openInterface(player, Components.SOULBANE_DARKNESS_317)
            setMinimapState(player, 2)
            openOverlay(player, Components.DRAGON_SLAYER_QIP_CR_JOURNEY_544)
            openInterface(player, Components.SOULBANE_DARKNESS_317)
        }

        if (ship == Ships.KARAMJAMA_TO_PORT_SARIM) {
            finishDiaryTask(player, DiaryType.KARAMJA, 0, 3)
        }
        if (ship == Ships.BRIMHAVEN_TO_ARDOUGNE) {
            finishDiaryTask(player, DiaryType.KARAMJA, 0, 4)
        }
        if (ship == Ships.CAIRN_ISLAND_TO_PORT_KHAZARD) {
            finishDiaryTask(player, DiaryType.KARAMJA, 1, 6)
        }
    }

    private fun prepare() {
        lock(player, ship.delay + 1)
        openInterface(player, Components.SHIP_MAP_299)
        setMinimapState(player, 2)
        setVarp(player, 75, ship.config)
    }
}
