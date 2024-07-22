package core.plugin.type

import core.plugin.Plugin

abstract class StartupPlugin : Plugin<Any?> {
    abstract fun run()
}
