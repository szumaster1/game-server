package content.global.activity.champion

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
            if (entity !is Player) return
            if (entity.isArtificial) return
            handler(entity, event)
        }
    }

    /**
     * Event handler for specific events.
     */
    class EventHandler<T : Event>(
        private val owner: ChampionScrollsEventHookBase, private val handler: (Player, T) -> Unit
    ) : EventHook<T> {
        override fun process(entity: Entity, event: T) {
            forEligibleEntityDo(entity, event, handler)
        }
    }

    final override fun login(player: Player) {
        player.hook(core.api.Event.NPCKilled, EventHandler(this, ::onNpcKilled))
    }

    /**
     * Handles the event when an NPC is killed.
     */
    protected open fun onNpcKilled(player: Player, event: NPCKillEvent) {}

}
