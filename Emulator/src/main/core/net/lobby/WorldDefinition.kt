package core.net.lobby

import core.game.world.GameWorld
import core.game.world.repository.Repository

/**
 * Represents a world's definition.
 */
data class WorldDefinition(
    val worldId: Int,
    val location: Int,
    val flag: Int,
    val activity: String,
    val ip: String,
    val region: String,
    val country: Int,
    var players: Int = 0
) {

    /**
     * Gets the player count.
     *
     * @return The player count.
     */
    fun getPlayerCount(): Int {
        return if (worldId == GameWorld.settings!!.worldId) {
            Repository.players.size
        } else {
            players
        }
    }
}
