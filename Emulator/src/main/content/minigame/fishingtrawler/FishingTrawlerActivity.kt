package content.minigame.fishingtrawler

import core.api.MapArea
import core.game.activity.ActivityManager
import core.game.activity.ActivityPlugin
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.game.world.map.build.DynamicRegion
import core.game.world.map.zone.ZoneBorders
import core.game.world.map.zone.ZoneRestriction
import core.plugin.Initializable
import core.tools.colorize
import core.tools.ticksToSeconds

import java.util.ArrayList

private val WAIT_TIME = if (GameWorld.settings?.isDevMode == true) 10 else 203
private val waitingPlayers = ArrayList<Player>()
private val sessions = ArrayList<FishingTrawlerSession>()
private var activity: FishingTrawlerActivity? = null
private var nextStart = GameWorld.ticks + WAIT_TIME
private val ftWaitingArea = arrayOf(ZoneBorders(2668, 3165, 2675, 3184))

/**
 * Fishing trawler activity.
 */
@Initializable
class FishingTrawlerActivity : ActivityPlugin("fishing trawler", false, false, true, ZoneRestriction.CANNON, ZoneRestriction.FIRES, ZoneRestriction.FOLLOWERS, ZoneRestriction.RANDOM_EVENTS), MapArea {

    init {
        activity = this
    }

    override fun configure() {
        GameWorld.Pulser.submit(object : Pulse(1) {
            override fun pulse(): Boolean {
                /*
                 * Display the time until the next game for players waiting.
                 */
                if ((nextStart - GameWorld.ticks) % 100 == 0) {
                    for (player in waitingPlayers) {
                        player.sendMessage(colorize("%R${ticksToSeconds(nextStart - GameWorld.ticks) / 60} minutes until next game."))
                    }
                }

                /*
                 * Start a new session if the waiting time has passed and there are players waiting.
                 */
                if (GameWorld.ticks >= nextStart && waitingPlayers.isNotEmpty()) {
                    val session = FishingTrawlerSession(DynamicRegion.create(8011), activity!!)
                    session.start(waitingPlayers)
                    sessions.add(session)
                    waitingPlayers.clear()
                    nextStart = GameWorld.ticks + WAIT_TIME
                }
                /*
                 * Remove inactive sessions.
                 */
                sessions.removeIf { session ->
                    if (!session.isActive && session.inactiveTicks >= 100) {
                        session.clearNPCs()
                        true
                    } else {
                        if (!session.isActive) {
                            session.inactiveTicks++
                        }
                        false
                    }
                }
                return false
            }
        })
    }

    override fun start(player: Player?, login: Boolean, vararg args: Any?): Boolean {
        player ?: return false
        waitingPlayers.add(player)
        return true
    }

    /**
     * Add player
     *
     * @param player The player to add
     */
    fun addPlayer(player: Player) {
        if (waitingPlayers.isEmpty()) {
            nextStart = GameWorld.ticks + WAIT_TIME
            player.dialogueInterpreter.sendDialogue(
                "Trawler will leave in 2 minutes.", "If you have a team get them on board now!"
            )
        }
        waitingPlayers.add(player)
    }

    /**
     * Remove player
     *
     * @param player The player to remove
     */
    fun removePlayer(player: Player) {
        waitingPlayers.remove(player)
    }

    override fun newInstance(p: Player?): ActivityPlugin {
        ActivityManager.register(this)
        return this
    }

    override fun getSpawnLocation(): Location {
        return Location.create(2667, 3161, 0)
    }

    override fun defineAreaBorders(): Array<ZoneBorders> {
        return ftWaitingArea
    }

    override fun getRestrictions(): Array<ZoneRestriction> {
        return arrayOf(ZoneRestriction.RANDOM_EVENTS, ZoneRestriction.TELEPORT)
    }

}
