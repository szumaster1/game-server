package core.game.node.entity.impl;

public enum PulseType {
    STANDARD, //standard pulse slot, should be interrupted/replaced by most things
    STRONG, //enforces itself as the only that can run
    CUSTOM_1, //custom slots for extra tasks that should run alongside standard tasks.
    CUSTOM_2
}
