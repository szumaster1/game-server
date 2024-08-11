package core.game.system.timer

import core.api.hasTimerActive
import core.api.log
import core.api.registerTimer
import core.game.node.entity.Entity
import core.tools.Log

/**
 * Timer registry.
 */
object TimerRegistry {
    val timerMap = HashMap<String, RSTimer>() // Create a HashMap to store timers
    val autoTimers = ArrayList<RSTimer>() // Create an ArrayList to store auto timers

    /**
     * Register timer.
     *
     * @param timer The timer to register.
     */
    fun registerTimer(timer: RSTimer) {
        log(this::class.java, Log.WARN, "Registering timer ${timer::class.java.simpleName}") // Log a warning message indicating that a timer is being registered
        if (timerMap.containsKey(timer.identifier.lowercase())) { // Check if the timer identifier is already in use
            log(this::class.java, Log.ERR, "Timer identifier ${timer.identifier} already in use by ${timerMap[timer.identifier.lowercase()]!!::class.java.simpleName}! Not loading ${timer::class.java.simpleName}!") // Log an error message indicating that the timer identifier is already in use
            return // Exit the function
        }
        timerMap[timer.identifier.lowercase()] = timer // Add the timer to the timerMap
        if (timer.isAuto) autoTimers.add(timer) // If the timer is an auto timer, add it to the autoTimers list
    }

    /**
     * Get timer instance.
     *
     * @param identifier The identifier of the timer.
     * @param args Additional arguments for the timer.
     * @return The timer instance.
     */
    fun getTimerInstance(identifier: String, vararg args: Any): RSTimer? {
        var t = timerMap[identifier.lowercase()] // Get the timer from the timerMap
        if (args.size > 0)
            return t?.getTimer(*args) // If there are additional arguments, return the timer instance with the arguments
        else
            return t?.retrieveInstance() // Otherwise, return the timer instance without any arguments
    }

    /**
     * Add auto timers.
     *
     * @param entity The entity to add auto timers to.
     */
    @JvmStatic
    fun addAutoTimers(entity: Entity) {
        for (timer in autoTimers) {
            if (!hasTimerActive(entity, timer.identifier)) // Check if the entity already has the timer active
                registerTimer(entity, timer.retrieveInstance()) // If not, register the timer for the entity
        }
    }

    /**
     * Get timer instance.
     *
     * @param T The type of the timer.
     * @param args Additional arguments for the timer.
     * @return The timer instance.
     */
    inline fun <reified T> getTimerInstance(vararg args: Any): T? {
        for ((_, inst) in timerMap)
            if (inst is T) {
                return if (args.isNotEmpty())
                    inst.getTimer(*args) as? T // If there are additional arguments, return the timer instance with the arguments
                else
                    inst.retrieveInstance() as? T // Otherwise, return the timer instance without any arguments
            }
        return null // Return null if no timer instance of the specified type is found
    }
}
