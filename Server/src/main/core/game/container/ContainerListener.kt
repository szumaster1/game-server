package core.game.container

/**
 * Container listener interface that defines methods for handling container events.
 *
 * @constructor Container listener
 */
interface ContainerListener {
    /**
     * Update method to handle updates to the container.
     *
     * @param c The container that is being updated.
     * @param event The event that triggered the update.
     */
    fun update(c: Container?, event: ContainerEvent?)

    /**
     * Refresh method to refresh the state of the container.
     *
     * @param c The container that needs to be refreshed.
     */
    fun refresh(c: Container?)
}
