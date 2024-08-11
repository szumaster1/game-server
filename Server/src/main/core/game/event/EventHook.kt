package core.game.event

import core.game.node.entity.Entity

/**
 * Event hook interface for handling events.
 *
 * @param T the type of event.
 * @constructor Initializes the EventHook interface.
 */
interface EventHook<T : Event> {
    /**
     * Process the event for a specific entity.
     *
     * @param entity the entity for which the event is processed.
     * @param event the event to be processed.
     */
    fun process(entity: Entity, event: T)
}