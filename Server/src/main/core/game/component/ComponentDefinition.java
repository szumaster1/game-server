package core.game.component;

import java.util.HashMap;
import java.util.Map;

public final class ComponentDefinition {

    private static final Map<Integer, ComponentDefinition> DEFINITIONS = new HashMap<Integer, ComponentDefinition>();

    private InterfaceType type = InterfaceType.DEFAULT;

    private boolean walkable;

    private int tabIndex = -1;

    private ComponentPlugin plugin;

    public ComponentDefinition() {
        /*
         * empty.
         */
    }

    public ComponentDefinition parse(String type, String walkable, String tabIndex) {
        setType(InterfaceType.values()[Integer.parseInt(type)]);
        setWalkable(Boolean.parseBoolean(walkable));
        setTabIndex(Integer.parseInt(tabIndex));
        return this;
    }

    public static ComponentDefinition forId(int componentId) {
        ComponentDefinition def = DEFINITIONS.get(componentId);
        if (def == null) {
            DEFINITIONS.put(componentId, def = new ComponentDefinition());
        }
        return def;
    }

    public static void put(int id, ComponentPlugin plugin) {
        ComponentDefinition.forId(id).setPlugin(plugin);
    }

    public static Map<Integer, ComponentDefinition> getDefinitions() {
        return DEFINITIONS;
    }

    public ComponentPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(ComponentPlugin plugin) {
        this.plugin = plugin;
    }

    public int getWindowPaneId(boolean resizable) {
        return resizable ? type.resizablePaneId : type.fixedPaneId;
    }

    public int getChildId(boolean resizable) {
        return resizable ? type.resizableChildId : type.fixedChildId;
    }

    public InterfaceType getType() {
        return type;
    }

    public void setType(InterfaceType type) {
        this.type = type;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    @Override
    public String toString() {
        return "ComponentDefinition [type=" + type + ", walkable=" + walkable + ", tabIndex=" + tabIndex + ", plugin=" + plugin + "]";
    }

}