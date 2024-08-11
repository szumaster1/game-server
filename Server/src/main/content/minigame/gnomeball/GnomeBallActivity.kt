package content.minigame.gnomeball

import core.game.activity.ActivityManager
import core.game.activity.ActivityPlugin
import core.game.node.entity.player.Player
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.map.zone.ZoneRestriction

/**
 * Gnome ball activity.
 */
class GnomeBallActivity : ActivityPlugin(
    "gnomeball",
    false,
    false,
    false,
    ZoneRestriction.CANNON,
    ZoneRestriction.FIRES,
    ZoneRestriction.FOLLOWERS,
    ZoneRestriction.RANDOM_EVENTS
) {
    private val waitTime = if (GameWorld.settings?.isDevMode == true) 10 else 203
    private val waitingPlayers = ArrayList<Player>()
    private val sessions = ArrayList<GnomeBallSession>()
    private var activity: GnomeBallActivity? = null
    private var nextStart = GameWorld.ticks

    // Initialize the activity reference to the current instance.
    init {
        activity = this
    }

    override fun configure() {

    }

    // Start mini-game.
    override fun start(player: Player?, login: Boolean, vararg args: Any?): Boolean {
        player ?: return false
        waitingPlayers.add(player)
        return true
    }

    // Add the player to the waiting list.
    fun addPlayer(player: Player) {
        if (waitingPlayers.isEmpty()) {
            // Update the next start time if no players are waiting.
            nextStart = GameWorld.ticks + waitTime
        }
        waitingPlayers.add(player)
    }

    // Remove the player from the waiting list.
    fun removePlayer(player: Player) {
        waitingPlayers.remove(player)
    }

    // Register the activity with the ActivityManager.
    override fun newInstance(p: Player?): ActivityPlugin {
        ActivityManager.register(this)
        return this
    }

    // Return the spawn location for the activity.
    override fun getSpawnLocation(): Location {
        return Location.create(2383, 3488, 0)
    }
}