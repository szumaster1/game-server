package core.game.node.entity.player.link.request

import core.game.node.entity.player.Player
import core.game.world.GameWorld.ticks

/**
 * Request manager.
 */
class RequestManager(val player: Player) {
    /*
     * Represents the target being requested.
     */
    var target: Player? = null
        private set

    /**
     * Method used to send a request type to a target.
     *
     * @param target The player to whom the request is being sent.
     * @param type The type of request being sent.
     * @return Returns true if the request was successfully sent, false otherwise.
     */
    fun request(target: Player, type: RequestType): Boolean {
        // Check if the request can be made to the target
        if (!canRequest(type, target)) {
            return false // Return false if the request cannot be made
        }
        // Check if an existing request can be accepted
        if (acceptExisting(target, type)) {
            return true // Return true if the existing request is accepted
        }
        // Send a message to the player about the request type
        player.packetDispatch.sendMessage(type.message)
        // Send a request message to the target player
        target.packetDispatch.sendMessage(type.getRequestMessage(player))
        // Set the last request type for the player
        player.setAttribute("lastRequest", type)
        // Set the target for the request manager
        this.target = target
        return true // Return true indicating the request was successful
    }

    /*
     * Method used to check if a player can continue with a request.
     */
    private fun canRequest(type: RequestType, target: Player): Boolean {
        // Ensure the target is not the player themselves
        if (target === player) {
            return false // Return false if the target is the player
        }
        // Check if the target is within a certain distance
        if (!target.location.withinDistance(player.location, 15)) {
            player.packetDispatch.sendMessage("Unable to find " + target.username + ".")
            return false // Return false if the target is too far away
        }
        // Check if the target is active and not busy
        if (!target.isActive || target.interfaceManager.isOpened) {
            player.packetDispatch.sendMessage("Other player is busy at the moment.")
            return false // Return false if the target is busy
        }
        // Check if either player is marked as busy
        if (target.getAttribute("busy", 0) > ticks || player.getAttribute("busy", 0) > ticks) {
            player.packetDispatch.sendMessage("Other player is busy at the moment.")
            return false // Return false if either player is busy
        }
        // Check if the request can be made based on zone monitoring
        return if (!player.zoneMonitor.canRequest(type, target)) {
            false // Return false if the request cannot be made in the current zone
        } else type.canRequest(player, target) // Return the result of the request type check
    }

    /*
     * Method used to check if we can accept an existing request.
     */
    private fun acceptExisting(target: Player, type: RequestType): Boolean {
        // Retrieve the last request type from the target
        val lastType = target.getAttribute<RequestType>("lastRequest", null)
        // Check if the last request type matches and if the player is the target's current request
        if (lastType === type && player === target.requestManager.target) {
            close(player) // Close the player's interface
            clear() // Clear the current request
            target.requestManager.clear() // Clear the target's request manager
            // Set both players as busy for a short duration
            player.setAttribute("busy", ticks + 2)
            target.setAttribute("busy", ticks + 2)
            // Open the request module for both players
            type.module.open(player, target)
            return true // Return true indicating the existing request was accepted
        }
        close(player) // Close the player's interface if the request is not accepted
        return false // Return false indicating the existing request was not accepted
    }

    /*
     * Closes the components for the player.
     */
    private fun close(player: Player) {
        player.dialogueInterpreter.close() // Close any open dialogues for the player
        player.interfaceManager.close() // Close the player's interface
        player.interfaceManager.closeChatbox() // Close the chatbox for the player
    }

    /**
     * Method used to clear the cached target.
     *
     */
    fun clear() {
        target = null // Set the target to null, clearing the cached target
    }
}