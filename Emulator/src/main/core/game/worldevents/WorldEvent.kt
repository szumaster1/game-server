package core.game.worldevents

import core.ServerStore
import core.api.ContentInterface
import core.plugin.PluginManager
import core.plugin.Plugin
import core.tools.Log
import org.json.simple.JSONObject
import java.util.*

/**
 * The class other world events should extend off of.
 * @author Ceikry
 *
 * @param name The name of the world event.
 * @constructor World event
 */
abstract class WorldEvent(var name: String) : ContentInterface {
    var plugins = PluginSet() // Initialize a set of plugins associated with the world event.

    /**
     * This function checks if the event is active or not.
     * It can be customized to check specific dates or simply return true
     * based on the requirements of the specific event.
     *
     * @param cal The calendar instance used to check the event's activity.
     * @return Boolean indicating if the event is active.
     */
    open fun checkActive(cal: Calendar): Boolean {
        return false // Default implementation returns false, indicating the event is not active.
    }

    /**
     * This function is used to initialize the event.
     * The WorldEventInitializer will call this method if checkActive() returns true.
     *
     */
    open fun initEvent() {
        // Default implementation does nothing; subclasses can override to provide specific initialization logic.
    }

    /**
     * This function is used to log messages related to the world event
     * in a standard and organized manner.
     *
     * @param message The message to log.
     */
    fun log(message: String) {
        core.api.log(this::class.java, Log.FINE, "[World Events($name)] $message") // Log the message with the event's name.
    }
}

/**
 * This class holds a set of plugins that should not be initialized by default.
 * It provides a clean way to initialize all of its plugins when needed.
 *
 * @param plugins The plugins to be included in the set.
 * @constructor Plugin set
 */
class PluginSet(vararg val plugins: Plugin<*>) {
    val set = ArrayList(plugins.asList()) // Create a list to hold the provided plugins.

    /**
     * This function initializes the plugins in the set by defining them
     * using the ClassScanner utility.
     *
     */
    fun initialize() {
        PluginManager.definePlugins(*set.toTypedArray()) // Convert the list to an array and define the plugins.
    }

    /**
     * This function adds a new plugin to the set of plugins.
     *
     * @param plugin The plugin to be added to the set.
     */
    fun add(plugin: Plugin<*>) {
        set.add(plugin) // Add the specified plugin to the internal list.
    }
}

/**
 * Static object for storing instances of loaded events.
 */
object WorldEvents {
    private var events = hashMapOf<String, WorldEvent>() // A map to store events by their lowercase names.

    fun add(event: WorldEvent) {
        events[event.name.lowercase(Locale.getDefault())] = event // Add the event to the map with its lowercase name as the key.
    }

    fun get(name: String): WorldEvent? {
        return events[name.lowercase(Locale.getDefault())] // Retrieve the event by its lowercase name.
    }

    fun getArchive(): JSONObject {
        return ServerStore.getArchive("world-event-status") // Fetch the archive of world event statuses from the server store.
    }
}
