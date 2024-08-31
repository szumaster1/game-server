package core.plugin

/**
 * Plugin manifest.
 * @author Emperor
 *
 * @param type The type of the plugin (e.g. PluginType.ACTION)
 * @param name The name of the plugin.
 * @constructor Plugin manifest.
 */
@Retention(AnnotationRetention.RUNTIME)
annotation class PluginManifest(val type: PluginType = PluginType.ACTION, val name: String = "")
