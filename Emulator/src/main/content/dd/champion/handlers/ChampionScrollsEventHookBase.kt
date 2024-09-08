package content.dd.champion.handlers

import core.api.LoginListener
import core.game.event.Event
import core.game.event.EventHook
import core.game.event.NPCKillEvent
import core.game.node.entity.Entity
import core.game.node.entity.player.Player

/**
 * Manages the champion scrolls drop.
 * @authors Phil, Skelsoft.
 */
abstract class ChampionScrollsEventHookBase : LoginListener {

    protected companion object {
        /**
         * Checks if the entity is a valid player and not an artificial entity before executing the handler.
         *
         * @param entity the entity to check
         * @param event the event to handle
         * @param handler the function to execute if the entity is a valid player
         */
        private fun <T> forEligibleEntityDo(entity: Entity, event: T, handler: (Player, T) -> Unit) {
            if (entity !is Player) return // Check if the entity is not a Player
            if (entity.isArtificial) return // Check if the entity is not artificial
            handler(entity, event) // Execute the handler function with the valid player entity
        }
    }

    /**
     * Event handler for specific events.
     */
    class EventHandler<T : Event>(
        private val owner: ChampionScrollsEventHookBase, private val handler: (Player, T) -> Unit
    ) : EventHook<T> {
        override fun process(entity: Entity, event: T) {
            forEligibleEntityDo(entity, event, handler) // Call the function to handle eligible entities
        }
    }

    final override fun login(player: Player) {
        player.hook(core.api.Event.NPCKilled, EventHandler(this, ::onNpcKilled)) // Hook the NPCKilled event with the EventHandler
    }

    /**
     * Handles the event when an NPC is killed.
     */
    protected open fun onNpcKilled(player: Player, event: NPCKillEvent) {
        // Add specific logic here for handling NPC kill events
    }

}
