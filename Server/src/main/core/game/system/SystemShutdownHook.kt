package core.game.system

import core.api.log
import core.game.system.SystemManager.flag
import core.game.system.SystemManager.isTerminated
import core.tools.Log

/**
 * System shutdown hook.
 */
class SystemShutdownHook : Runnable {
    override fun run() {
        if (isTerminated) {
            return
        }
        log(this.javaClass, Log.INFO, "Terminating...")
        flag(SystemState.TERMINATED)
    }
}
