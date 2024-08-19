package core.game.container

/**
 * Represents a container listener.
 * @author Emperor
 */
interface ContainerListener {
    /**
     * Updates the changed item slots in the container.
     * @param c The container we're listening to.
     * @param event The container event.
     */
    fun update(c: Container?, event: ContainerEvent?)

    /**
     * Updates the entire container.
     * @param c The container.
     */
    fun refresh(c: Container?)
}