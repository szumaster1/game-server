package core.cache.def;

import java.util.HashMap;
import java.util.Map;

import core.tools.StringUtils;
import core.game.node.Node;

/**
 * Represents a node's definitions.
 * @param <T> The node type.
 * @author Emperor
 */
public class Definition<T extends Node> {

    /**
     * The node id.
     */
    protected int id;

    /**
     * The name.
     */
    protected String name = "null";

    /**
     * The examine info.
     */
    protected String examine;

    /**
     * The options.
     */
    protected String[] options;

    /**
     * The configurations.
     */
    protected final Map<String, Object> handlers = new HashMap<String, Object>();

    /**
     * Constructs a new {@code Definition} {@code Object}.
     */
    public Definition() {
        /*
         * empty.
         */
    }

    /**
     * Checks if this node has options.
     *
     * @return {@code True} if so.
     */
    public boolean hasOptions() {
        return hasOptions(true);
    }

    /**
     * Checks if this node has options.
     *
     * @param examine If examine should be treated as an option.
     * @return {@code True} if so.
     */
    public boolean hasOptions(boolean examine) {
        if (name.equals("null") || options == null) {
            return false;
        }
        for (String option : options) {
            if (option != null && !option.equals("null")) {
                if (examine || !option.equals("Examine")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets a configuration of this item's definitions.
     *
     * @param key the key.
     * @return The configuration value.
     */
    @SuppressWarnings("unchecked")
    public <V> V getConfiguration(String key) {
        return (V) handlers.get(key);
    }

    /**
     * Gets a configuration from this item's definitions.
     *
     * @param key  the key.
     * @param fail the object to return if there was no value found for this key.
     * @return The value, or the fail object.
     */
    @SuppressWarnings("unchecked")
    public <V> V getConfiguration(String key, V fail) {
        V object = (V) handlers.get(key);
        if (object == null) {
            return fail;
        }
        return object;
    }

    /**
     * Gets the id.
     *
     * @return the id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the examine.
     *
     * @return the examine.
     */
    public String getExamine() {
        if (examine == null) {
            try {
                if (handlers.get("examine") != null)
                    examine = handlers.get("examine").toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (examine == null) {
                if (name.length() > 0) {
                    examine = "It's a" + (StringUtils.isPlusN(name) ? "n " : " ") + name + ".";
                } else {
                    examine = "null";
                }
            }
        }
        return examine;
    }

    /**
     * Sets the examine.
     *
     * @param examine the examine to set.
     */
    public void setExamine(String examine) {
        this.examine = examine;
    }

    /**
     * Gets the options.
     *
     * @return the options.
     */
    public String[] getOptions() {
        return options;
    }

    /**
     * Sets the options.
     *
     * @param options the options to set.
     */
    public void setOptions(String[] options) {
        this.options = options;
    }

    /**
     * Gets the configurations.
     *
     * @return the configurations.
     */
    public Map<String, Object> getHandlers() {
        return handlers;
    }

}