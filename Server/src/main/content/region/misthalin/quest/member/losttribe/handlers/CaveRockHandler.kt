package content.region.misthalin.quest.member.losttribe.handlers

import cfg.consts.Components
import cfg.consts.Scenery
import core.api.openInterface
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.world.map.Direction
import core.game.world.map.Location
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Represents the Cave rock handler.
 */
@Initializable
class CaveRockHandler : OptionHandler() {

    /*
     * Creates a new instance of the plugin and registers the "look-at" option for specific scenery items.
     */
    override fun newInstance(arg: Any?): Plugin<Any> {
        for (i in Scenery.SYMBOL_6921..Scenery.SYMBOL_6924) {
            // Assigns the current instance as the handler for the "look-at" option of the scenery item.
            SceneryDefinition.forId(i).handlers["option:look-at"] = this
        }
        return this
    }

    /*
     * Handles the player's interaction with the scenery node based on the selected option.
     */
    override fun handle(player: Player?, node: Node?, option: String?): Boolean {
        // Checks if the player is null; if so, returns false to indicate no action was taken.
        player ?: return false
        // Matches the node ID to determine which rock to show based on the player's interaction.
        when (node?.id) {
            Scenery.SYMBOL_6921 -> showRock(player, Scenery.SYMBOL_6923) // Shows the corresponding rock for SYMBOL_6921
            Scenery.SYMBOL_6922 -> showRock(player, Scenery.SYMBOL_6924) // Shows the corresponding rock for SYMBOL_6922
            Scenery.SYMBOL_6923 -> showRock(player, Scenery.SYMBOL_6922) // Shows the corresponding rock for SYMBOL_6923
            Scenery.SYMBOL_6924 -> showRock(player, Scenery.BIG_BIG_BOULDER_6927) // Shows the big boulder for SYMBOL_6924
        }
        return true // Indicates that the interaction was successfully handled.
    }

    /**
     * Show rock
     *
     * @param player The player interacting with the rock.
     * @param model The model ID of the rock to be displayed.
     */
    fun showRock(player: Player, model: Int) {
        // Opens the interface for the player to view the rock.
        openInterface(player, Components.CAVE_GOBLIN_MARKERS_62)
        // Sends the model of the rock to be displayed on the interface.
        player.packetDispatch.sendModelOnInterface(model, 62, 1, 1)
    }

    // Determines the destination location based on the direction of the node.
    override fun getDestination(n: Node?, node: Node?): Location {
        // Checks if the first node is null; if so, calls the superclass method to handle it.
        n ?: return super.getDestination(n, node)
        // Checks if the second node is null; if so, calls the superclass method to handle it.
        node ?: return super.getDestination(n, node)

        var diffX = 0 // Initializes the X-axis difference.
        var diffY = 0 // Initializes the Y-axis difference.

        // Adjusts the X-axis difference based on the direction of the node.
        if (node.direction == Direction.SOUTH)
            diffX = -1 // Moves left if facing south.
        if (node.direction == Direction.NORTH)
            diffX = 1 // Moves right if facing north.
        if (node.direction == Direction.WEST)
            diffY = 1 // Moves down if facing west.
        if (node.direction == Direction.EAST)
            diffY = -1 // Moves up if facing east.

        // Returns the new location based on the calculated differences.
        return node.location.transform(diffX, diffY, 0)
    }
}
