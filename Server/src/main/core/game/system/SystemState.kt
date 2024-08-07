package core.game.system

/**
 * System state
 *
 * @constructor System state
 */
enum class SystemState {
    /**
     * Active
     *
     * @constructor Active
     */
    ACTIVE,

    /**
     * Updating
     *
     * @constructor Updating
     */
    UPDATING,

    /**
     * Private
     *
     * @constructor Private
     */
    PRIVATE,

    /**
     * Terminated
     *
     * @constructor Terminated
     */
    TERMINATED
}