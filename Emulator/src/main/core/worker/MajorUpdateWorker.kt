package core.worker

import core.Main
import core.Configuration
import core.ServerStore
import core.api.log
import core.api.sendNews
import core.api.submitWorldPulse
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.repository.Repository
import core.game.world.update.UpdateSequence
import core.integration.grafana.Grafana
import core.net.packet.PacketProcessor
import core.net.packet.PacketWriteQueue
import core.plugin.type.Managers
import core.tools.Log
import core.tools.colorize
import java.lang.Long.max
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

/**
 * Handles the running of pulses and writing of masks, etc
 * @author Ceikry
 */
class MajorUpdateWorker {

    var running: Boolean = false
    var started = false
    val sequence = UpdateSequence()
    val sdf = SimpleDateFormat("HHmmss")
    val worker = Thread {
        Thread.currentThread().name = "Major Update Worker"
        started = true

        Thread.sleep(600L)

        while (running) {
            Grafana.startTick()
            val start = System.currentTimeMillis()
            Main.heartbeat()
            handleTickActions()


            for (player in Repository.players.filter { !it.isArtificial }) {
                if (System.currentTimeMillis() - player.session.lastPing > 20000L) {
                    player?.session?.lastPing = Long.MAX_VALUE
                    player?.session?.disconnect()
                }
                if (!player.isActive && !Repository.disconnectionQueue.contains(player.name) && player.getAttribute("logged-in-fully", false)) {
                    /*
                     * If player has somehow been set as inactive without being
                     * queued for disconnection, do that now. This is a failsafe, and should not be relied on.
                     * If you made a change, and now this is suddenly getting triggered a lot, your change is
                     * probably bad.
                     */
                    player?.session?.disconnect()
                    log(MajorUpdateWorker::class.java, Log.WARN, "Manually disconnecting ${player.name} because they were set as inactive without being disconnected. This is bad.")
                }
            }

            /*
             * Handle daily restart if enabled.
             */
            if (sdf.format(Date()).toInt() == 0) {

                if (GameWorld.checkDay() == 1) { // Monday
                    ServerStore.clearWeeklyEntries()
                }

                ServerStore.clearDailyEntries()
                if (Configuration.DAILY_RESTART) {
                    sendNews(colorize("%RSERVER GOING DOWN FOR DAILY RESTART IN 5 MINUTES!"))
                    Configuration.DAILY_RESTART = false
                    submitWorldPulse(object : Pulse(100) {
                        var counter = 0
                        override fun pulse(): Boolean {
                            counter++
                            if (counter == 5) {
                                exitProcess(0)
                            }
                            sendNews(colorize("%RSERVER GOING DOWN FOR DAILY RESTART IN ${5 - counter} MINUTE${if (counter < 4) "S" else ""}!"))
                            return false
                        }
                    })
                }
            }

            // Get the end time of the tick
            val end = System.currentTimeMillis()
            // Calculate the total tick time in Grafana
            Grafana.totalTickTime = (end - start).toInt()
            // End the tick in Grafana
            Grafana.endTick()
            /*
             * ServerMonitor.eventQueue.add(GuiEvent.UpdateTickTime(end - start));
             * ServerMonitor.eventQueue.add(GuiEvent.UpdatePulseCount(GameWorld.Pulser.TASKS.size));
             */
            // Sleep for the remaining time of the tick
            Thread.sleep(max(600 - (end - start), 0))
        }

        // Log that the update worker has stopped
        log(this::class.java, Log.FINE, "Update worker stopped.")
    }

    // Function to handle tick actions
    fun handleTickActions(skipPulseUpdate: Boolean = false) {
        try {
            var packetStart = System.currentTimeMillis()
            PacketProcessor.processQueue()
            Grafana.packetProcessTime = (System.currentTimeMillis() - packetStart).toInt()
            /*
             * Disconnect all players waiting to be disconnected.
             */
            Repository.disconnectionQueue.update()

            if (!skipPulseUpdate) {
                GameWorld.Pulser.updateAll()
            }
            GameWorld.tickListeners.forEach { it.tick(); }

            sequence.start()
            sequence.run()
            sequence.end()

            /*
             * Increment global ticks variable.
             */
            GameWorld.pulse()
            /*
             * Tick all manager plugins.
             */
            Managers.tick()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                // Flush the packet write queue
                PacketWriteQueue.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /*
     * Function to start the worker.
     */
    fun start() {
        if (!started) {
            running = true
            worker.start()
        }
    }

    /*
     * Function to stop the worker.
     */
    fun stop() {
        running = false
        worker.interrupt()
    }
}