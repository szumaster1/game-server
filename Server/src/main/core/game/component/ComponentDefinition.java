package core.game.component;

import java.util.HashMap;
import java.util.Map;

/**
 * Component definition.
 */
public final class ComponentDefinition {

    // Map to store component definitions
    private static final Map<Integer, ComponentDefinition> DEFINITIONS = new HashMap<Integer, ComponentDefinition>();

    // Type of the interface
    private InterfaceType type = InterfaceType.DEFAULT;

    // Flag to indicate if the component is walkable
    private boolean walkable;

    // Tab index of the component
    private int tabIndex = -1;

    // Plugin associated with the component
    private ComponentPlugin plugin;

    /**
     * Instantiates a new Component definition.
     */
    public ComponentDefinition() {
        /*
         * empty.
         */
    }

    /**
     * Parse component definition.
     *
     * @param type     the type
     * @param walkable the walkable
     * @param tabIndex the tab index
     * @return the component definition
     */
    public ComponentDefinition parse(String type, String walkable, String tabIndex) {
        setType(InterfaceType.values()[Integer.parseInt(type)]);
        setWalkable(Boolean.parseBoolean(walkable));
        setTabIndex(Integer.parseInt(tabIndex));
        return this;
    }

    /**
     * Retrieves the component definition for the given component ID.
     *
     * @param componentId the component id
     * @return the component definition
     */
    public static ComponentDefinition forId(int componentId) {
        ComponentDefinition def = DEFINITIONS.get(componentId);
        if (def == null) {
            DEFINITIONS.put(componentId, def = new ComponentDefinition());
        }
        return def;
    }

    /**
     * Associates the given plugin with the specified component ID.
     *
     * @param id     the id
     * @param plugin the plugin
     */
    public static void put(int id, ComponentPlugin plugin) {
        ComponentDefinition.forId(id).setPlugin(plugin);
    }

    /**
     * Gets all the component definitions.
     *
     * @return the definitions
     */
    public static Map<Integer, ComponentDefinition> getDefinitions() {
        return DEFINITIONS;
    }

    /**
     * Gets the plugin associated with the component.
     *
     * @return the plugin
     */
    public ComponentPlugin getPlugin() {
        return plugin;
    }

    /**
     * Sets the plugin associated with the component.
     *
     * @param plugin the plugin
     */
    public void setPlugin(ComponentPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets the window pane ID based on the resizable flag.
     *
     * @param resizable the resizable
     * @return the window pane id
     */
    public int getWindowPaneId(boolean resizable) {
        return resizable ? type.resizablePaneId : type.fixedPaneId;
    }

    /**
     * Gets the child ID based on the resizable flag.
     *
     * @param resizable the resizable
     * @return the child id
     */
    public int getChildId(boolean resizable) {
        return resizable ? type.resizableChildId : type.fixedChildId;
    }

    /**
     * Gets the type of the component.
     *
     * @return the type
     */
    public InterfaceType getType() {
        return type;
    }

    /**
     * Sets the type of the component.
     *
     * @param type the type
     */
    public void setType(InterfaceType type) {
        this.type = type;
    }

    /**
     * Checks if the component is walkable.
     *
     * @return the boolean
     */
    public boolean isWalkable() {
        return walkable;
    }

    /**
     * Sets the walkable flag of the component.
     *
     * @param walkable the walkable
     */
    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    /**
     * Gets the tab index of the component.
     *
     * @return the tab index
     */
    public int getTabIndex() {
        return tabIndex;
    }

    /**
     * Sets the tab index of the component.
     *
     * @param tabIndex the tab index
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    @Override
    public String toString() {
        return "ComponentDefinition [type=" + type + ", walkable=" + walkable + ", tabIndex=" + tabIndex + ", plugin=" + plugin + "]";
    }

}
