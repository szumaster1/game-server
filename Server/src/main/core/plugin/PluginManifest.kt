package core.plugin

/**
 * Plugin manifest.
 *
 * @property type The type of the plugin (e.g. PluginType.ACTION)
 * @property name The name of the plugin.
 * @constructor Plugin manifest.
 * @author Emperor
 */
@Retention(AnnotationRetention.RUNTIME)
annotation class PluginManifest(val type: PluginType = PluginType.ACTION, val name: String = "")
