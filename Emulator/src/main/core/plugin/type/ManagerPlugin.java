package core.plugin.type;

import core.plugin.Plugin;

/**
 * Abstract class representing a Manager Plugin.
 */
public abstract class ManagerPlugin implements Plugin<Object> {

    /**
     * Method to perform actions on each tick.
     */
    public abstract void tick();

    @Override
    public Object fireEvent(String identifier, Object... args) {
        return null;
    }
}
