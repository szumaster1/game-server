package core.worker

import core.Server
import core.ServerConstants
import core.ServerStore
import core.api.log
import core.api.sendNews
import core.api.submitWorldPulse
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.repository.Repository
import core.game.world.update.UpdateSequence
import core.integration.grafana.Grafana
import core.network.packet.PacketProcessor
import core.network.packet.PacketWriteQueue
import core.plugin.type.Managers
import core.tools.Log
import core.tools.colorize
import java.lang.Long.max
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

class MajorUpdateWorker {

    // Variable to track if the worker is running
    var running: Boolean = false;
    // Variable to track if the worker has started
    var started = false;
    // Update sequence for handling updates
    val sequence = UpdateSequence();
    // SimpleDateFormat for formatting time
    val sdf = SimpleDateFormat("HHmmss");
    // Worker thread
    val worker = Thread {
        // Set the name of the thread
        Thread.currentThread().name = "Major Update Worker";
        // Mark the worker as started
        started = true;
        // Sleep for 600 milliseconds
        Thread.sleep(600L);
        // Loop while the worker is running
        while (running) {
            // Start a tick in Grafana for monitoring
            Grafana.startTick();
            // Get the start time of the tick
            val start = System.currentTimeMillis();
            // Perform server heartbeat
            Server.heartbeat();
            // Handle tick actions
            handleTickActions();

            // Iterate through players and handle disconnections
            for (player in Repository.players.filter { !it.isArtificial }) {
                if (System.currentTimeMillis() - player.session.lastPing > 20000L) {
                    player?.session?.lastPing = Long.MAX_VALUE;
                    player?.session?.disconnect();
                }
                if (!player.isActive && !Repository.disconnectionQueue.contains(player.name) && player.getAttribute("logged-in-fully", false)) {
                    /*
                     * If player has somehow been set as inactive without being
                     * queued for disconnection, do that now. This is a failsafe, and should not be relied on.
                     * If you made a change, and now this is suddenly getting triggered a lot, your change is
                     * probably bad.
                     */
                    player?.session?.disconnect();
                    log(MajorUpdateWorker::class.java, Log.WARN, "Manually disconnecting ${player.name} because they were set as inactive without being disconnected. This is bad.");
                }
            }

            /*
             * Handle daily restart if enabled.
             */
            if (sdf.format(Date()).toInt() == 0) {

                if (GameWorld.checkDay() == 1) { // Monday
                    ServerStore.clearWeeklyEntries();
                }

                ServerStore.clearDailyEntries();
                if (ServerConstants.DAILY_RESTART) {
                    Repository.sendNews(colorize("%RSERVER GOING DOWN FOR DAILY RESTART IN 5 MINUTES!"));
                    ServerConstants.DAILY_RESTART = false;
                    submitWorldPulse(object : Pulse(100) {
                        var counter = 0;
                        override fun pulse(): Boolean {
                            counter++;
                            if (counter == 5) {
                                exitProcess(0);
                            }
                            sendNews(colorize("%RSERVER GOING DOWN FOR DAILY RESTART IN ${5 - counter} MINUTE${if (counter < 4) "S" else ""}!"));
                            return false;
                        }
                    });
                }
            }

            // Get the end time of the tick
            val end = System.currentTimeMillis();
            // Calculate the total tick time in Grafana
            Grafana.totalTickTime = (end - start).toInt();
            // End the tick in Grafana
            Grafana.endTick();
            /*
             * ServerMonitor.eventQueue.add(GuiEvent.UpdateTickTime(end - start));
             * ServerMonitor.eventQueue.add(GuiEvent.UpdatePulseCount(GameWorld.Pulser.TASKS.size));
             */
            // Sleep for the remaining time of the tick
            Thread.sleep(max(600 - (end - start), 0));
        }

        // Log that the update worker has stopped
        log(this::class.java, Log.FINE, "Update worker stopped.");
    }

    // Function to handle tick actions
    fun handleTickActions(skipPulseUpdate: Boolean = false) {
        try {
            // Get the start time of packet processing
            var packetStart = System.currentTimeMillis();
            // Process the packet queue
            PacketProcessor.processQueue();
            // Calculate the packet process time in Grafana
            Grafana.packetProcessTime = (System.currentTimeMillis() - packetStart).toInt();
            /*
             * Disconnect all players waiting to be disconnected.
             */
            Repository.disconnectionQueue.update();

            if (!skipPulseUpdate) {
                // Update all pulses in the game world
                GameWorld.Pulser.updateAll();
            }
            // Call the tick method of all tick listeners
            GameWorld.tickListeners.forEach { it.tick(); }

            // Start the update sequence
            sequence.start();
            // Run the update sequence
            sequence.run();
            // End the update sequence
            sequence.end();

            /*
             * Increment global ticks variable.
             */
            GameWorld.pulse();
            /*
             * Tick all manager plugins.
             */
            Managers.tick();
        } catch (e: Exception) {
            e.printStackTrace();
        } finally {
            try {
                // Flush the packet write queue
                PacketWriteQueue.flush();
            } catch (e: Exception) {
                e.printStackTrace();
            }
        }
    }

    // Function to start the worker
    fun start() {
        if (!started) {
            running = true;
            worker.start();
        }
    }

    // Function to stop the worker
    fun stop() {
        running = false;
        worker.interrupt();
    }
}