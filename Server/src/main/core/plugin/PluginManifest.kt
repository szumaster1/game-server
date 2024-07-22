package core.plugin

@Retention(AnnotationRetention.RUNTIME)
annotation class PluginManifest(val type: PluginType = PluginType.ACTION, val name: String = "")