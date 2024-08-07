package core.plugin.type;

import java.util.ArrayList;
import java.util.List;

/**
 * Managers class responsible for managing plugins.
 */
public class Managers {

    // List to store registered plugins
    private static List<ManagerPlugin> plugins = new ArrayList<>(20);

    /**
     * Register a plugin.
     *
     * @param plugin the plugin to register
     */
    public static void register(ManagerPlugin plugin) {
        if (plugin != null) {
            plugins.add(plugin); // Add the plugin to the list
        }
    }

    /**
     * Perform a tick operation on all registered plugins.
     */
    public static void tick() {
        for (ManagerPlugin p : plugins) {
            p.tick(); // Call the tick method of each plugin
        }
    }
}
