package core.game.container

/**
 * Container listener
 *
 * @constructor Container listener
 */
interface ContainerListener {
    /**
     * Update
     *
     * @param c
     * @param event
     */
    fun update(c: Container?, event: ContainerEvent?)

    /**
     * Refresh
     *
     * @param c
     */
    fun refresh(c: Container?)
}