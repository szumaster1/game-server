package core.game.system

import core.game.system.security.EncryptionManager
import core.game.world.GameWorld.majorUpdateWorker

/**
 * Manages the "game system" states, such as updating or terminating.
 * @author Emperor
 */
object SystemManager {
    private var state = SystemState.TERMINATED
    val updater = SystemUpdate()
    val terminator = SystemTermination()
    val systemConfig = SystemConfig()
    val encryption = EncryptionManager()

    /**
     * Sets the current state and handles it accordingly.
     * @param state The system state.
     */
    @JvmStatic
    fun flag(state: SystemState) {
        if (SystemManager.state == state) {
            return
        }
        SystemManager.state = state
        when (state) {
            SystemState.ACTIVE, SystemState.PRIVATE -> {
                majorUpdateWorker.started = false
                majorUpdateWorker.start()
            }

            SystemState.UPDATING -> updater.schedule()
            SystemState.TERMINATED -> terminator.terminate()
        }
    }

    /**
     * Checks if the system is still active (updating keeps the system active
     * until termination).
     * @return `True` if the state does not equal [SystemState.TERMINATED].
     */
    val isActive: Boolean
        get() = state != SystemState.TERMINATED

    /**
     * Checks if the system is being updated.
     * @return `True` if so.
     */
    val isUpdating: Boolean
        get() = state == SystemState.UPDATING

    /**
     * Checks if the system is private, so only developers can connect.
     * @return `True` if so.
     */
    val isPrivate: Boolean
        get() = state == SystemState.PRIVATE

    /**
     * Checks if the system has been terminated.
     * @return `True` if so.
     */
    @JvmStatic
    val isTerminated: Boolean
        get() = state == SystemState.TERMINATED


    fun state(): SystemState {
        return state
    }
}