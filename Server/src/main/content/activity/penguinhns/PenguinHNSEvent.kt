package content.activity.penguinhns

import core.ServerStore
import core.api.StartupListener
import core.api.log
import core.plugin.ClassScanner
import core.tools.Log
import org.json.simple.JSONObject

/**
 * The Penguin HNS event.
 */
class PenguinHNSEvent : StartupListener {

    val manager = PenguinManager()

    override fun startup() {
        manager.rebuildVars() // Rebuild variables in the PenguinManager
        ClassScanner.definePlugins(LarryHandler(), NotebookHandler()) // Define plugins using ClassScanner
        log(this::class.java, Log.FINE, "Penguin HNS initialized.") // Log initialization message
    }

    companion object {
        /**
         * Get the store file for Penguin HNS.
         *
         * @return JSONObject representing the store file.
         */
        @JvmStatic
        fun getStoreFile(): JSONObject {
            return ServerStore.getArchive("weekly-penguinhns") // Retrieve the archive for weekly Penguin HNS
        }
    }

}
