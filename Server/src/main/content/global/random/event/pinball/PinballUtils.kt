package content.global.random.event.pinball

import core.api.*
import core.api.consts.NPCs
import core.api.consts.Sounds
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.system.timer.impl.AntiMacro
import core.game.world.map.Location


object PinballUtils {
    const val PINBALL_LOGOUT = "pinball-logout"
    const val PINBALL_SAVE_LOCATION = "/save:original-loc"
    const val PINBALL_EVENT_START = "pinball-event-start"
    const val PILLAR_TOUCHED = "pinball-clicked"
    const val PINBALL_SCORE = "pinball-event-score"
    const val GET_PILLAR = "pinball-pillars"

    val wrongPillars = intArrayOf(15001, 15003, 15005, 15007, 15009)
    val pillars = intArrayOf(15000, 15001, 15002, 15004, 15005, 15006, 15007, 15008, 15009)

    const val CAVE_EXIT = 15010
    val guards = intArrayOf(NPCs.FLIPPA_3912,NPCs.TILT_3913)
    val oldMan = NPC(NPCs.MYSTERIOUS_OLD_MAN_410, Location.create(1971, 5046, 0))
    val PINBALL_LOCATION = Location.create(1972, 5046, 0)

    fun cleanup(player: Player) {
        player.properties.teleportLocation = getAttribute(player, PINBALL_SAVE_LOCATION, null)
        clearLogoutListener(player, PINBALL_LOGOUT)
        removeAttributes(player, PINBALL_LOGOUT, PINBALL_SAVE_LOCATION, PINBALL_EVENT_START, PINBALL_SCORE, GET_PILLAR, PILLAR_TOUCHED)
        player.interfaceManager.restoreTabs()
        closeOverlay(player)
        setMinimapState(player, 0)
        oldMan.clear()
        AntiMacro.terminateEventNpc(player)
    }

    fun handlePinballEvent(player: Player): Boolean {
        if (inBorders(player, 1965, 5038, 1979, 5048)) {
            openDialogue(player, OldManDialogue())
        }
        return true
    }

    private val getPillar = arrayOf(
        Scenery(15001, Location(1967, 5046, 0)),
        Scenery(15003, Location(1969, 5049, 0)),
        Scenery(15005, Location(1972, 5050, 0)),
        Scenery(15007, Location(1975, 5049, 0)),
        Scenery(15009, Location(1977, 5046, 0))
    )

    fun generateTag(player: Player) {
        for (i in 0..4) if (getAttribute(player, GET_PILLAR, -1) == i) {
            replaceScenery(getPillar[i], getPillar[i].id - 1, -1)
            setAttribute(player, PILLAR_TOUCHED, i)
            playAudio(player, Sounds.PILLARTAG_PINBALL_2278)
        }
    }

    fun replaceTag(player : Player) {
        for (i in 0..4) if (getAttribute(player, PILLAR_TOUCHED, -1) == i) {
            when (i) {
                0 -> replaceScenery(Scenery(15000, Location(1967, 5046, 0)), 15001, -1, Location(1967, 5046, 0))
                1 -> replaceScenery(Scenery(15002, Location(1969, 5049, 0)), 15003, -1, Location(1969, 5049, 0))
                2 -> replaceScenery(Scenery(15004, Location(1972, 5050, 0)), 15005, -1, Location(1972, 5050, 0))
                3 -> replaceScenery(Scenery(15006, Location(1975, 5049, 0)), 15007, -1, Location(1975, 5049, 0))
                4 -> replaceScenery(Scenery(15008, Location(1977, 5046, 0)), 15009, -1, Location(1977, 5046, 0))
            }
        }
    }
}


