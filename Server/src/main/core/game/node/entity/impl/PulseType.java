package core.game.node.entity.impl;

/**
 * The enum Pulse type.
 */
public enum PulseType {
    /**
     * Standard pulse type.
     */
    STANDARD, //standard pulse slot, should be interrupted/replaced by most things
    /**
     * Strong pulse type.
     */
    STRONG, //enforces itself as the only that can run
    /**
     * Custom 1 pulse type.
     */
    CUSTOM_1, //custom slots for extra tasks that should run alongside standard tasks.
    /**
     * Custom 2 pulse type.
     */
    CUSTOM_2
}
