package core.game.event

import core.game.node.entity.Entity

/**
 * Represents the [EventHook] interface.
 *
 * @param [T] the type of event.
 */
interface EventHook<T : Event> {
    /**
     * Process the event for a specific entity.
     *
     * @param [entity]  the entity for which the event is processed.
     * @param [event]   the event to be processed.
     */
    fun process(entity: Entity, event: T)
}