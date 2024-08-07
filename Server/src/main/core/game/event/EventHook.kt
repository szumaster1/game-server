package core.game.event

import core.game.node.entity.Entity

/**
 * Event hook
 *
 * @param T
 * @constructor Event hook
 */
interface EventHook<T : Event> {
    /**
     * Process
     *
     * @param entity
     * @param event
     */
    fun process(entity: Entity, event: T)
}
