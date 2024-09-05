package content.dd.hns.handlers

import content.dd.hns.dialogue.LarryDialogue
import core.ServerStore
import core.api.StartupListener
import core.api.log
import core.plugin.PluginManager
import core.tools.Log
import org.json.simple.JSONObject

/**
 * The Penguin HNS event.
 */
class PenguinHNSEvent : StartupListener {

    val manager = PenguinManager()

    override fun startup() {
        manager.rebuildVars() // Rebuild variables in the PenguinManager
        PluginManager.definePlugins(LarryDialogue(), NotebookHandler()) // Define plugins using ClassScanner
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
