package core.plugin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PluginManifest {
    public PluginType type() default PluginType.ACTION;
    public String name() default "";
}