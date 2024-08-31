package core.plugin.type

import core.plugin.Plugin

/**
 * Represents a startup plugin that extends the Plugin class.
 */
abstract class StartupPlugin : Plugin<Any?> {
    /**
     * Abstract method that defines the behavior of running the plugin.
     */
    abstract fun run()
}