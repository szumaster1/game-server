package content.global.skill.summoning.familiar

import content.global.skill.combat.summoning.pet.IncubatorEgg
import content.global.skill.combat.summoning.pet.IncubatorTimer
import core.api.addItem
import core.api.freeSlots
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.Node
import core.game.node.entity.player.Player
import core.tools.StringUtils

/**
 * Incubator handler.
 */
class IncubatorHandler : InteractionListener {

    val eggIds = IncubatorEgg.values().map { it.egg.id }.toIntArray()
    val incubators = intArrayOf(28550, 28352, 28359)

    override fun defineListeners() {
        /*
         * Listener for inspecting the incubator.
         */

        on(incubators, IntType.SCENERY, "inspect", handler = ::handleInspectOption)

        /*
         * Listener for taking the egg from the incubator
         */

        on(incubators, IntType.SCENERY, "take-egg", handler = ::handleTakeOption)

        /*
         * Listener for using an egg on the incubator
         */

        onUseWith(IntType.SCENERY, eggIds, *incubators, handler = ::handleEggOnIncubator)
    }

    /**
     * Handle egg on incubator
     *
     * @param player The player interacting with the incubator
     * @param used The egg being used
     * @param with The incubator being interacted with
     * @return Boolean indicating success or failure
     */
    fun handleEggOnIncubator(player: Player, used: Node, with: Node): Boolean {
        // Retrieve the egg type from the used item
        val egg = IncubatorEgg.forItem(used.asItem()) ?: return false

        // Check if the player already has an egg in the incubator
        val activeEgg = IncubatorTimer.getEggFor(player, player.location.regionId)

        if (activeEgg != null) {
            // Notify the player that they already have an egg in the incubator
            sendMessage(player, "You already have an egg in this incubator.")
            return true
        }

        // Remove the egg from the player's inventory
        if (removeItem(player, used.asItem()))
        // Register the egg in the incubator for the player
            IncubatorTimer.registerEgg(player, player.location.regionId, egg)
        return true
    }

    /**
     * Handle inspect option
     *
     * @param player The player inspecting the incubator
     * @param node The incubator being inspected
     * @return Boolean indicating success or failure
     */
    fun handleInspectOption(player: Player, node: Node): Boolean {
        // Check for an active egg in the incubator
        val activeEgg = IncubatorTimer.getEggFor(player, player.location.regionId)

        if (activeEgg == null) {
            // Notify the player that the incubator is empty
            sendMessage(player, "The incubator is currently empty.")
            return true
        }

        if (activeEgg.finished) {
            // Notify the player that the egg has finished incubating
            sendMessage(player, "The egg inside has finished incubating.")
            return true
        }

        // Get the name of the creature from the active egg
        val creatureName = activeEgg.egg.product.name.lowercase()
        // Notify the player about the egg currently incubating
        sendMessage(
            player,
            "There is currently ${if (StringUtils.isPlusN(creatureName)) "an" else "a"} $creatureName egg incubating."
        )
        return true
    }

    /**
     * Handle take option
     *
     * @param player The player taking the egg
     * @param node The incubator being interacted with
     * @return Boolean indicating success or failure
     */
    fun handleTakeOption(player: Player, node: Node): Boolean {
        // Get the region ID of the player's current location
        val region = player.location.regionId

        // Retrieve the active egg in the incubator
        val activeEgg = IncubatorTimer.getEggFor(player, region) ?: return false

        if (!activeEgg.finished) {
            // Notify the player that the egg hasn't finished incubating
            sendMessage(player, "That egg hasn't finished incubating!")
            return true
        }

        // Check if the player has enough inventory space
        if (freeSlots(player) < 1) {
            // Notify the player about insufficient inventory space
            sendMessage(player, "You do not have enough inventory space to do that.")
            return true
        }

        // Remove the egg from the incubator
        val egg = IncubatorTimer.removeEgg(player, region) ?: return false
        val product = egg.product
        val name = product.name.lowercase()

        // Notify the player that they have taken the egg out of the incubator
        sendMessage(player, "You take your $name out of the incubator.")
        // Add the egg to the player's inventory
        addItem(player, product.id)
        return true
    }
}