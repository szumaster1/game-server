package content.region.desert.handlers.alkharid

import core.api.sendMessage
import core.cache.def.impl.SceneryDefinition
import core.game.global.action.DoorActionHandler
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.world.map.Location
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Al kharid stairs plugin.
 */
@Initializable
class AlKharidStairsPlugin : OptionHandler() {
    // Constants
    private val zekeStairsTop = Scenery(35645, Location(3284, 3190, 1), 2, 0)
    private val zekeDoorClosed = Scenery(27988, Location(3284, 3190, 1), 0, 2)
    private val zekeDoorOpened = Scenery(27989, Location(3285, 3190, 1), 0, 3)
    private val craftingStairsTop = Scenery(35645, Location(3314, 3187, 1), 2, 0)
    private val craftingDoorClosed = Scenery(27988, Location(3314, 3187, 1), 0, 3)
    private val craftingDoorOpened = Scenery(27989, Location(3314, 3186, 1), 0, 0)

    // Handle player interaction with the scenery
    override fun handle(player: Player?, node: Node?, option: String?): Boolean {
        // Check if player is null, return false if so
        player ?: return false
        // Check if node is null, return false if so
        node ?: return false
        // Check if option is null, return false if so
        option ?: return false
        // Check if the door is opened
        if (node.location == zekeDoorOpened.location || node.location == craftingDoorOpened.location) {
            // Send message to player indicating the door is stuck open
            sendMessage(player, "This door appears to be stuck open.")
        } else {
            // Handle the door action if it is not opened
            DoorActionHandler.handleDoor(player, node.asScenery())
        }
        return true // Return true indicating the action was handled
    }

    // Create a new instance of the plugin
    override fun newInstance(arg: Any?): Plugin<Any> {
        // Replace the closed door with the opened door for Zeke's shop
        SceneryBuilder.replace(zekeDoorClosed, zekeDoorOpened)
        // Add the top stairs for Zeke's shop
        SceneryBuilder.add(zekeStairsTop)

        // Replace the closed door with the opened door for the Crafting shop
        SceneryBuilder.replace(craftingDoorClosed, craftingDoorOpened)
        // Add the top stairs for the Crafting shop
        SceneryBuilder.add(craftingStairsTop)

        // Assign the close option handler to the opened door scenery
        SceneryDefinition.forId(27989).handlers["option:close"] = this
        return this // Return the current instance of the plugin
    }
}
