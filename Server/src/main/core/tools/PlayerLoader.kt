package core.tools

import core.game.node.entity.player.Player
import core.game.node.entity.player.info.PlayerDetails
import core.game.node.entity.player.info.login.PlayerParser

/**
 * Object responsible for loading player data.
 */
object PlayerLoader {

    /**
     * Retrieves a Player object based on the player's name.
     * @param name The name of the player.
     * @return The Player object.
     */
    @JvmStatic
    fun getPlayerFile(name: String?): Player {
        val playerDetails = PlayerDetails(name)
        // playerDetails.parse(); // Commented out as parsing is not needed here.
        val player = Player(playerDetails)
        PlayerParser.parse(player)
        // GameWorld.getWorld().getAccountService().loadPlayer(player); // Commented out for simplicity.
        return player
    }

    /**
     * Retrieves PlayerDetails object based on the player's name.
     * @param name The name of the player.
     * @return The PlayerDetails object.
     */
    @JvmStatic
    fun getPlayerDetailFile(name: String?): PlayerDetails {
        val playerDetails = PlayerDetails(name)
        // playerDetails.parse(); // Commented out as parsing is not needed here.
        return playerDetails
    }
}
