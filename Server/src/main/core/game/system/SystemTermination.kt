package core.game.system

import core.Server.reactor
import core.Server.running
import core.ServerConstants
import core.ServerStore
import core.api.ShutdownListener
import core.api.log
import core.game.bots.AIRepository.Companion.clearAllBots
import core.game.node.entity.player.Player
import core.game.node.entity.player.info.PlayerMonitor.flushRemainingEventsImmediately
import core.game.world.GameWorld.majorUpdateWorker
import core.game.world.GameWorld.shutdownListeners
import core.game.world.GameWorld.worldPersists
import core.game.world.repository.Repository.disconnectionQueue
import core.game.world.repository.Repository.players
import core.tools.Log
import java.io.File
import java.util.function.Consumer

/**
 * System termination.
 */
class SystemTermination {

    /**
     * Terminates the system safely.
     */
    fun terminate() {
        log(this.javaClass, Log.INFO, "Initializing termination sequence - do not shutdown!")
        try {
            log(this.javaClass, Log.INFO, "Shutting down networking...")
            running = false
            log(this.javaClass, Log.INFO, "Stopping all bots...")
            clearAllBots()
            reactor!!.terminate()
            log(this.javaClass, Log.INFO, "Stopping all pulses...")
            majorUpdateWorker.stop()
            val it: Iterator<Player> = players.iterator()
            while (it.hasNext()) {
                try {
                    val p = it.next()
                    if (p != null && !p.isArtificial) { // Should never be null.
                        p.details.save()
                        p.clear()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            shutdownListeners.forEach(Consumer { obj: ShutdownListener -> obj.shutdown() })
            flushRemainingEventsImmediately()
            var s: ServerStore? = null
            for (wld in worldPersists) {
                if (wld is ServerStore) s = wld else wld.save()
            }
            /*
             * ServerStore should ***always*** save last. Fudging a race condition here :)
             */
            s?.save()
            if (ServerConstants.DATA_PATH != null) save(ServerConstants.DATA_PATH)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        log(this.javaClass, Log.INFO, "Server successfully terminated!")
    }

    /**
     * Saves all system data on the directory.
     *
     * @param directory The directory to save the data.
     */
    fun save(directory: String?) {
        val file = File(directory)
        log(this.javaClass, Log.INFO, "Saving data [dir=" + file.absolutePath + "]...")
        if (!file.isDirectory) {
            file.mkdirs()
        }
        reactor!!.terminate()
        val start = System.currentTimeMillis()
        while (!disconnectionQueue.isEmpty() && System.currentTimeMillis() - start < 5000L) {
            disconnectionQueue.update()
            try {
                Thread.sleep(100)
            } catch (ignored: Exception) {
            }
        }
        disconnectionQueue.update()
        disconnectionQueue.clear()
    }
}
