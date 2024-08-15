package content.region.desert.handlers.alkharid

import core.api.consts.NPCs
import core.api.consts.Scenery
import core.api.findNPC
import core.api.getScenery
import core.api.replaceScenery
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location

/**
 * Bedabin listeners.
 */
class BedabinListeners : InteractionListener {

    companion object {
        // Constant for the tent scenery ID
        private const val TENT = Scenery.TENT_DOOR_2700
        // Constant for the experimental anvil scenery ID
        private const val EXPERIMENTAL_ANVIL = Scenery.AN_EXPERIMENTAL_ANVIL_2672
        // Constant for the Bedabin Nomad Guard NPC ID
        private const val NOMAD_GUARD = NPCs.BEDABIN_NOMAD_GUARD_834
    }

    // Method to define interaction listeners
    override fun defineListeners() {

        // Listener for walking through the tent
        on(TENT, IntType.SCENERY, "walk-through") { player, _ ->
            // Check if the player's Y location is greater than or equal to 3046
            if (player.location.y >= 3046) {
                // Get the scenery object at the specified location
                val door = getScenery(Location(3169, 3046, 0))
                // Check if the door ID is not equal to 2701
                if (door!!.id != 2701) replaceScenery(door.asScenery(), 2701, 2)
                // Reset the player's walking queue
                player.walkingQueue.reset()
                // Add a path for the player to walk
                player.walkingQueue.addPath(3169, 3045)
                // Send a message to the player
                sendMessage(player,"You walk back out the tent.")
                return@on true
            }
            // Open dialogue with the NPC if the player is not at the specified Y location
            player.dialogueInterpreter.open(834, findNPC(834))
            return@on true
        }

        // Listener for using the experimental anvil
        on(EXPERIMENTAL_ANVIL, IntType.SCENERY, "use") { player, _ ->
            // Send a message to the player about forging items
            sendMessage(player, "To forge items use the metal you wish to work with the anvil.")
            return@on true
        }

        // Listener for talking to the Nomad Guard NPC
        on(NOMAD_GUARD, IntType.NPC, "talk-to") { player, node ->
            // Open dialogue with the NPC
            player.dialogueInterpreter.open(node.id, node.asNpc())
            return@on true
        }

    }

    // Method to define destination overrides for NPC interactions
    override fun defineDestinationOverrides() {
        // Set destination for talking to the Bedabin Nomad Guard NPC
        setDest(IntType.NPC, intArrayOf(NPCs.BEDABIN_NOMAD_GUARD_834), "talk-to") { _, _ ->
            // Return the location for the NPC interaction
            return@setDest Location(3169, 3045, 0)
        }
    }

}