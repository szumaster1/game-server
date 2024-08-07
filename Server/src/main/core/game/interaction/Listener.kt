package core.game.interaction

import core.api.StartupListener

/**
 * Listener
 *
 * @constructor Listener
 */
interface Listener : StartupListener {
    override fun startup() {
        defineListeners()
    }

    /**
     * Define listeners
     *
     */
    fun defineListeners()
}