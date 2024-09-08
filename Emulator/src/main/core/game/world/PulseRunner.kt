package core.game.world

import core.Configuration
import core.api.log
import core.game.bots.GeneralBotCreator
import core.game.system.task.Pulse
import core.integration.grafana.Grafana
import core.tools.Log
import java.util.concurrent.LinkedBlockingQueue

/**
 * Pulse runner.
 */
class PulseRunner {
    // Queue to hold pulses for processing
    private val pulses = LinkedBlockingQueue<Pulse>()

    // Property to get current pulses as an array
    val currentPulses: Array<Pulse> get() = pulses.toTypedArray()

    /**
     * Submit.
     *
     * @param pulse The pulse to be submitted.
     */
    fun submit(pulse: Pulse) {
        // Add the submitted pulse to the queue
        pulses.add(pulse)
    }

    /**
     * Update all pulses.
     */
    fun updateAll() {
        // Get the current number of pulses in the queue
        val pulseCount = pulses.size

        // Initialize counters for total time taken by bot and other pulses
        var totalTimeBotPulses = 0
        var totalTimeOtherPulses = 0

        // Iterate through each pulse in the queue
        for (i in 0 until pulseCount) {
            // Retrieve and remove the pulse from the queue
            val pulse = pulses.take()

            // Measure the time taken to update the pulse
            val elapsedTime = measure {
                try {
                    // Update the pulse and check if it should be re-added to the queue
                    if (!pulse.update() && pulse.isRunning) {
                        pulses.add(pulse)
                    }
                } catch (e: Exception) {
                    // Log an error if an exception occurs during pulse execution
                    log(this::class.java, Log.ERR, "Pulse execution error. Stack trace below.")
                    e.printStackTrace()
                }
            }

            // Get the name of the pulse class
            var pulseName = pulse::class.java.name

            // Categorize the elapsed time based on pulse type
            if (pulse is GeneralBotCreator.BotScriptPulse || pulseName.contains("ScriptAPI")) {
                totalTimeBotPulses += elapsedTime.toInt() // Accumulate time for bot pulses
            } else {
                totalTimeOtherPulses += elapsedTime.toInt() // Accumulate time for other pulses
            }

            // Log the length of the pulse and count it in Grafana
            Grafana.addPulseLength(pulseName, elapsedTime.toInt())
            Grafana.countPulse(pulseName)

            // Notify if the pulse execution time exceeds a threshold
            notifyIfTooLong(pulse, elapsedTime)
        }

        // If Grafana logging is enabled, update the total times for bot and other pulses
        if (Configuration.GRAFANA_LOGGING) {
            Grafana.botPulseTime = totalTimeBotPulses
            Grafana.otherPulseTime = totalTimeOtherPulses
        }
    }

    /**
     * Measure the execution time of a given logic block
     *
     * @param logic A lambda function representing the logic to be executed
     * @receiver This function does not have a receiver
     * @return The time taken to execute the logic block in milliseconds
     */
    private fun measure(logic: () -> Unit): Long {
        val startTime = System.currentTimeMillis() // Record the start time

        logic() // Execute the logic

        return System.currentTimeMillis() - startTime // Return the elapsed time
    }

    /**
     * Notify if the pulse execution time is too long
     *
     * @param pulse The Pulse object that is being monitored
     * @param elapsedTime The time taken for the pulse execution in milliseconds
     */
    private fun notifyIfTooLong(pulse: Pulse, elapsedTime: Long) {
        // Check if the elapsed time exceeds the critical threshold
        if (elapsedTime >= 100) {
            if (pulse is GeneralBotCreator.BotScriptPulse) {
                // Log a warning for critically long bot-script execution
                log(this::class.java, Log.WARN, "CRITICALLY long bot-script tick - ${pulse.botScript.javaClass.name} took $elapsedTime ms")
            } else {
                // Log a warning for critically long pulse execution
                log(this::class.java, Log.WARN, "CRITICALLY long running pulse - ${pulse.javaClass.name} took $elapsedTime ms")
            }
        } else if (elapsedTime >= 30) {
            // Check if the elapsed time exceeds the warning threshold
            if (pulse is GeneralBotCreator.BotScriptPulse) {
                // Log a warning for long bot-script execution
                log(this::class.java, Log.WARN, "Long bot-script tick - ${pulse.botScript.javaClass.name} took $elapsedTime ms")
            } else {
                // Log a warning for long pulse execution
                log(this::class.java, Log.WARN, "Long running pulse - ${pulse.javaClass.name} took $elapsedTime ms")
            }
        }
    }
}