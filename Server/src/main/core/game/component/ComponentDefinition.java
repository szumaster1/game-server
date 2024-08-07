package core.game.component;

import java.util.HashMap;
import java.util.Map;

/**
 * Component definition.
 */
public final class ComponentDefinition {

    private static final Map<Integer, ComponentDefinition> DEFINITIONS = new HashMap<Integer, ComponentDefinition>();

    private InterfaceType type = InterfaceType.DEFAULT;

    private boolean walkable;

    private int tabIndex = -1;

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
     * For id component definition.
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
     * Put.
     *
     * @param id     the id
     * @param plugin the plugin
     */
    public static void put(int id, ComponentPlugin plugin) {
        ComponentDefinition.forId(id).setPlugin(plugin);
    }

    /**
     * Gets definitions.
     *
     * @return the definitions
     */
    public static Map<Integer, ComponentDefinition> getDefinitions() {
        return DEFINITIONS;
    }

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    public ComponentPlugin getPlugin() {
        return plugin;
    }

    /**
     * Sets plugin.
     *
     * @param plugin the plugin
     */
    public void setPlugin(ComponentPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets window pane id.
     *
     * @param resizable the resizable
     * @return the window pane id
     */
    public int getWindowPaneId(boolean resizable) {
        return resizable ? type.resizablePaneId : type.fixedPaneId;
    }

    /**
     * Gets child id.
     *
     * @param resizable the resizable
     * @return the child id
     */
    public int getChildId(boolean resizable) {
        return resizable ? type.resizableChildId : type.fixedChildId;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public InterfaceType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(InterfaceType type) {
        this.type = type;
    }

    /**
     * Is walkable boolean.
     *
     * @return the boolean
     */
    public boolean isWalkable() {
        return walkable;
    }

    /**
     * Sets walkable.
     *
     * @param walkable the walkable
     */
    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    /**
     * Gets tab index.
     *
     * @return the tab index
     */
    public int getTabIndex() {
        return tabIndex;
    }

    /**
     * Sets tab index.
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