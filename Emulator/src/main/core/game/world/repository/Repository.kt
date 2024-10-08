package core.game.world.repository

import content.region.wilderness.handlers.revenants.RevenantNPC
import core.Configuration
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.map.RegionManager
import core.game.world.update.UpdateSequence
import java.util.concurrent.CopyOnWriteArrayList

/**
 * The repository holding all node lists, etc. in the game world.
 * @author Emperor
 */
object Repository {
    /**
     * Represents the repository of active players.
     */
    @JvmStatic
    val players = NodeList<Player>(Configuration.MAX_PLAYERS)
    val uid_map = HashMap<Int, Player>(Configuration.MAX_PLAYERS)

    /**
     * Represents the repository of active npcs.
     */
    @JvmStatic
    val npcs = NodeList<NPC>(Configuration.MAX_NPCS)

    /**
     * List of player names who have logged in, quick and dirty work-around for the double-logging issue.
     * This list needs to have values added to it before any sort of parsing is done for the login process due
     * to the time it takes for the parsing to occur, which is the root of this issue to begin with.
     */
    val LOGGED_IN_PLAYERS: MutableList<String> = ArrayList(Configuration.MAX_PLAYERS)

    /**
     * The renderable NPCs.
     */
    val RENDERABLE_NPCS: MutableList<NPC> = CopyOnWriteArrayList()

    /**
     * A mapping holding the players sorted by their names.
     */
    @JvmStatic
    val playerNames: MutableMap<String, Player> = HashMap()

    /**
     * Represents the list of players in lobby.
     */
    @JvmStatic
    val lobbyPlayers: MutableList<Player> = ArrayList()

    /**
     * The disconnection queue.
     */
    @JvmStatic
    val disconnectionQueue = DisconnectionQueue()

    /**
     * Sends a market update message to all players.
     * @param string The string.
     * @param color The color.
     */
    @JvmOverloads
    fun sendMarketUpdate(string: String, icon: Int = 12, color: String = "<col=CC6600>") {
        val players: Array<Any> = playerNames.values.toTypedArray()
        val size = players.size
        for (i in 0 until size) {
            val player = players[i] as Player
            player.sendMessage("<img=" + icon + ">" + color + "Market Update: " + string)
        }
    }

    /**
     * Send a news message to all players.
     * @param string The string.
     * @param icon The icon.
     */
    @JvmStatic
    fun sendNews(string: String, icon: Int = 12, color: String = "CC6600") {
        val players: Array<Any> = playerNames.values.toTypedArray()
        val size = players.size
        for (i in 0 until size) {
            val player = players[i] as Player
            player.sendMessage("<img=$icon><col=$color>News: $string")
        }
    }

    /**
     * This is another sendnews method which redirects to the one above.
     * This needs to exist because java fails to recognize default arguments in Kotlin methods....
     * I hate java.
     */
    @JvmStatic
    @Deprecated("Old and bad", ReplaceWith("sendNews()"), DeprecationLevel.WARNING)
    fun sendNews(string: String) {
        sendNews(string, 12)
    }

    /**
     * Adds a renderable NPC.
     * @param npc The NPC.
     */
    @JvmStatic
    fun addRenderableNPC(npc: NPC) {
        if (npc is RevenantNPC) return // hack to make sure we can update revenants every tick.
        if (!RENDERABLE_NPCS.contains(npc)) {
            RENDERABLE_NPCS.add(npc)
            npc.isRenderable = true
        }
    }

    /**
     * Removes an NPC from rendering.
     * @param npc The NPC.
     */
    @JvmStatic
    fun removeRenderableNPC(npc: NPC) {
        if (npc is RevenantNPC) return // hack to make sure we can update revenants every tick.
        RENDERABLE_NPCS.remove(npc)
        npc.isRenderable = false
    }

    /**
     * Finds the NPC on the given location.
     * @param l The location.
     * @return The NPC, or `null` if the NPC wasn't found.
     */
    @JvmStatic
    fun findNPC(l: Location): NPC? {
        for (n in RegionManager.getRegionPlane(l).npcs) {
            if (n.location == l) {
                return n
            }
        }
        return null
    }

    @JvmStatic
    fun addPlayer(player: Player) {
        if (players.isNotEmpty()) {
            for (i in 1 until players.size) {
                players[i] ?: continue
                if (players[i].details.uid == player.details.uid) {
                    val oldPl = players[i]
                    players.remove(oldPl)
                    oldPl.clear()
                    break
                }
            }
        }
        players.add(player)
        uid_map[player.details.uid] = player
        playerNames[player.name] = player
    }

    @JvmStatic
    fun removePlayer(player: Player) {
        players.remove(player)
        uid_map.remove(player.details.uid)
        playerNames.remove(player.name)
        UpdateSequence.renderablePlayers.remove(player)
        player.session.disconnect()
    }

    /**
     * Find a non-player character.
     * @param npcId The non-player character's id.
     * @return The non-player character's node.
     */
    @JvmStatic
    fun findNPC(npcId: Int): NPC? {
        for (npc in npcs) {
            if (npc == null) {
                continue
            }
            if (npc.id == npcId) {
                return npc
            }
        }
        return null
    }

    /**
     * Get a player by its username.
     * @param name The player's **username**.
     * @return The player.
     */
    @JvmStatic
    fun getPlayerByName(name: String?): Player? {
        return if (name == null) {
            null
        } else playerNames[name.lowercase().replace(" ".toRegex(), "_")]
    }

    @JvmStatic
    fun getPlayerByUid(uid: Int): Player? {
        return uid_map[uid]
    }

    /**
     * Gets the renderableNpcs.
     * @return The renderableNpcs.
     */
    @JvmStatic
    val renderableNpcs: List<NPC>
        get() = RENDERABLE_NPCS
}
