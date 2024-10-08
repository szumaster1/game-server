package core.game.interaction;

import core.game.event.InteractionEvent;
import core.game.container.Container;
import core.game.node.Node;
import core.game.node.entity.impl.PulseType;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.system.task.Pulse;
import core.net.packet.PacketRepository;
import core.net.packet.context.InteractionOptionContext;
import core.net.packet.outgoing.InteractionOption;
import core.tools.Log;
import core.game.world.GameWorld;

import static core.api.ContentAPIKt.log;

/**
 * Handles interaction between nodes.
 * @author Emperor
 */
public class InteractPlugin {

    /**
     * The current options.
     */
    private Option[] options = new Option[8];

    /**
     * The player.
     */
    private final Node node;

    /**
     * If the interaction option handlers have been initialized.
     */
    private boolean initialized;

    /**
     * Constructs a new {@code Interaction} {@code Object}.
     *
     * @param node The node reference.
     */
    public InteractPlugin(Node node) {
        this.node = node;
    }

    /**
     * Sends the current option.
     *
     * @param node  The node.
     * @param index The index.
     * @param name  The option name.
     */
    public static void sendOption(Node node, int index, String name) {
        if (!(node instanceof Player)) {
            return;
        }
        PacketRepository.send(InteractionOption.class, new InteractionOptionContext((Player) node, index, name));
    }

    /**
     * Handles an interaction option being clicked.
     *
     * @param player The player using the option.
     * @param option The option used.
     */
    public void handle(final Player player, final Option option) {
        try {
            if (player.getLocks().isInteractionLocked() || option == null) {
                return;
            }
            player.debug("Received interaction request " + option.getName());
            boolean hasHandler = option.getHandler() != null;
            boolean walk = hasHandler && option.getHandler().isWalk();
            if (!walk && hasHandler && option.getHandler().isWalk(player, node)) {
                walk = true;
            }
            if (!hasHandler || walk) {
                handleWalkOption(player, option, PulseType.STANDARD);
            } else if (hasHandler) {
                player.debug("Option handler being used=" + option.getHandler().getClass().getSimpleName());
                handleDefaultOption(player, option, PulseType.STANDARD);
            } else {
                player.getPulseManager().runUnhandledAction(player, PulseType.STANDARD);
            }
            player.dispatch(new InteractionEvent(node, option.getName().toLowerCase()));
        } catch (Exception e) {
            e.printStackTrace();
            log(this.getClass(), Log.ERR, this.getClass().getName() + e.getMessage());
        }
    }

    /**
     * Handles an item option.
     *
     * @param player    The player.
     * @param option    The option.
     * @param container The container the item is in.
     */
    public void handleItemOption(final Player player, final Option option, final Container container) {
        if (player.getLocks().isInteractionLocked()) {
            return;
        }
        player.getPulseManager().clear(PulseType.STANDARD);
        GameWorld.getPulser().submit(new Pulse(1, player) {
            @Override
            public boolean pulse() {
                try {
                    if (player.getLocks().isInteractionLocked() || player.getZoneMonitor().interact(node, option)) {
                        return true;
                    }
                    if (InteractionListeners.run(node.getId(), IntType.ITEM, option.getName(), player, node)) {
                        return true;
                    }
                    if (option.getHandler() == null || !option.getHandler().handle(player, node, option.getName().toLowerCase())) {
                        player.getPacketDispatch().sendMessage("Nothing interesting happens.");
                    }
                    if (option.getHandler() != null) {
                        player.debug("Using item handler " + option.getHandler().getClass().getSimpleName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log(this.getClass(), Log.ERR, this.getClass().getName() + e.getMessage());
                }
                return true;
            }
        });
    }

    /**
     * Handles an invalid interaction.
     *
     * @param player The player.
     * @param node   The node to interact with.
     * @param option the option
     */
    public static void handleInvalidInteraction(final Player player, final Node node, final Option option) {
        if (node == null) {
            return;
        }
        if (node.getLocation() != null) {
            if (player.getLocks().isMovementLocked()) {
                return;
            }
            player.getPulseManager().run(new MovementPulse(player, node) {
                @Override
                public boolean pulse() {
                    try {
                        player.faceLocation(node.getFaceLocation(player.getLocation()));
                        if (player.getLocks().isInteractionLocked() || player.getZoneMonitor().interact(node, option)) {
                            return true;
                        }
                        player.getPacketDispatch().sendMessage("Nothing interesting happens.");
                    } catch (Exception e) {
                        e.printStackTrace();
                        log(this.getClass(), Log.ERR, this.getClass().getName() + e.getMessage());
                    }
                    return true;
                }
            }, PulseType.STANDARD);
        } else {
            player.getPulseManager().runUnhandledAction(player, PulseType.STANDARD);
        }
    }

    /**
     * Handles an option requiring the player to walk to the node.
     *
     * @param player    The player.
     * @param option    The option.
     * @param pulseType The pulse type.
     */
    private void handleWalkOption(final Player player, final Option option, PulseType pulseType) {
        if (node.getLocation() == null) {
            player.getPulseManager().runUnhandledAction(player, pulseType);
            return;
        }
        if (player.getLocks().isMovementLocked()) {
            player.getPulseManager().clear(pulseType);
            return;
        }
        player.getPulseManager().run(new MovementPulse(player, node, option.getHandler()) {
            @Override
            public boolean pulse() {
                try {
                    player.faceLocation(node.getFaceLocation(player.getLocation()));
                    if (player.getLocks().isInteractionLocked() || player.getZoneMonitor().interact(node, option)) {
                        return true;
                    }
                    if (option == null || option.getHandler() == null || !option.getHandler().handle(player, node, option.getName().toLowerCase())) {
                        player.getPacketDispatch().sendMessage("Nothing interesting happens.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log(this.getClass(), Log.ERR, this.getClass().getName() + e.getMessage());
                }
                return true;
            }
        }, pulseType);
    }

    /**
     * Handles a default option.
     *
     * @param player    The player.
     * @param option    The option.
     * @param pulseType The pulse type.
     */
    private void handleDefaultOption(final Player player, final Option option, PulseType pulseType) {
        if (!option.getHandler().isDelayed(player)) {
            if (player.getZoneMonitor().interact(node, option)) {
                return;
            }
            player.getProperties().getCombatPulse().stop();
            if (!option.getHandler().handle(player, node, option.getName().toLowerCase())) {
                player.getPulseManager().runUnhandledAction(player, pulseType);
            }
            return;
        }
        player.getPulseManager().run(new Pulse(1, player, node) {
            @Override
            public boolean pulse() {
                if (player.getLocks().isInteractionLocked() || player.getZoneMonitor().interact(node, option)) {
                    return true;
                }
                if (!option.getHandler().handle(player, node, option.getName().toLowerCase())) {
                    player.getPacketDispatch().sendMessage("Nothing interesting happens.");
                }
                return true;
            }
        }, pulseType);
    }

    /**
     * Initializes the interaction options.
     *
     * @param nodeId The node id.
     * @param names  The option names.
     */
    public void init(int nodeId, String... names) {
        options = new Option[names.length];
        for (int i = 0; i < options.length; i++) {
            String name = names[i];
            if (name != null && !name.equals("null")) {
                set(new Option(name, i).setHandler(Option.defaultHandler(node, nodeId, name)));
            }
            continue;
        }
    }

    /**
     * Sets the default option handlers (if not yet initialized).
     */
    public void setDefault() {
        if (initialized) {
            return;
        }
        if (node instanceof Player) {
            for (int i = 0; i < options.length; i++) {
                remove(i);
            }
            set(Option._P_FOLLOW);
            set(Option._P_TRADE);
            set(Option._P_ASSIST);
        } else if (node instanceof NPC) {
            NPC npc = (NPC) node;
            init(npc.getId(), npc.getDefinition().getOptions());
        } else if (node instanceof Scenery) {
            Scenery object = (Scenery) node;
            init(object.getId(), object.getDefinition().getOptions());
        } else if (node instanceof Item) {
            Item item = (Item) node;
            if (item.getLocation() != null) {
                init(item.getId(), item.getDefinition().getGroundOptions());
            } else {
                init(item.getId(), item.getDefinition().getInventoryOptions());
            }
        } else {
            throw new IllegalStateException("Unsupported node type - " + node);
        }
        initialized = true;
    }

    /**
     * Sets a new option.
     *
     * @param option The option.
     */
    public void set(Option option) {
        options[option.getIndex()] = option;
        sendOption(node, option.getIndex(), option.getName());
    }

    /**
     * Removes an option.
     *
     * @param option the option
     * @return {@code True} if the option got removed, {@code false} if the option wasn't set.
     */
    public boolean remove(Option option) {
        if (options[option.getIndex()] == option) {
            remove(option.getIndex());
            return true;
        }
        return false;
    }

    /**
     * Removes an option.
     *
     * @param index The index.
     */
    public void remove(int index) {
        if (options[index] == null) {
            return;
        }
        options[index] = null;
        sendOption(node, index, "null");
    }

    /**
     * Gets the option on the requested slot.
     *
     * @param index The slot index.
     * @return The option on that slot, or {@code null} if there was no option.
     */
    public Option get(int index) {
        return options[index];
    }

    /**
     * Gets the options.
     *
     * @return The options.
     */
    public Option[] getOptions() {
        return options;
    }

    /**
     * Gets the initialized value.
     *
     * @return The initialized.
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Sets the initialized value.
     *
     * @param initialized The initialized to set.
     */
    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

}
