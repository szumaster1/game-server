package core.game.system.task;

import core.game.node.Node;

/**
 * Represents "Node pulse", which is used to execute methods with node parameters.
 * @author Emperor
 */
public abstract class NodeTask {

    /**
     * The amount of ticks for the pulse (if any).
     */
    private final int ticks;

    /**
     * The pulse
     */
    private Pulse pulse;

    /**
     * Constructs a new {@code NodeTask} {@code Object}.
     */
    public NodeTask() {
        this(-1);
    }

    /**
     * Constructs a new {@code NodeTask} {@Code Object}
     *
     * @param ticks The ticks.
     */
    public NodeTask(int ticks) {
        this.ticks = ticks;
    }

    /**
     * Called when the pulse starts.
     *
     * @param node The base node.
     * @param n    The other nodes.
     */
    public void start(Node node, Node... n) {

    }

    /**
     * Runs the task.
     *
     * @param node The base node.
     * @param n    The other nodes.
     * @return {@code True} if the pulse (if this is used in a pulse) should stop.
     */
    public abstract boolean exec(Node node, Node... n);

    /**
     * Called when the pulse is stopped.
     *
     * @param node The base node.
     * @param n    The other nodes.
     */
    public void stop(Node node, Node... n) {

    }

    /**
     * Checks if the node task pulse should be removed for a duplicate.
     *
     * @param s    The command string.
     * @param node The base node.
     * @param n    The other nodes.
     * @return the boolean
     */
    public boolean removeFor(String s, Node node, Node... n) {
        return true;
    }

    /**
     * Schedules the node task.
     *
     * @param node The base node.
     * @param n    The other nodes.
     * @return The pulse used for this task.
     */
    public Pulse schedule(final Node node, final Node... n) {
        pulse = new Pulse(ticks, node) {

            @Override
            public void start() {
                super.start();
                NodeTask.this.start(node, n);
            }

            @Override
            public boolean pulse() {
                return exec(node, n);
            }

            @Override
            public void stop() {
                super.stop();
                NodeTask.this.stop(node, n);
            }

            @Override
            public boolean removeFor(String s) {
                return NodeTask.this.removeFor(s, node, n);
            }
        };
        pulse.start();
        return pulse;
    }

    /**
     * Gets the Pulse for this Task
     *
     * @return the Pulse
     */
    public Pulse getPulse() {
        return pulse;
    }

    /**
     * Gets the ticks.
     *
     * @return the ticks.
     */
    public int getTicks() {
        return ticks;
    }

}