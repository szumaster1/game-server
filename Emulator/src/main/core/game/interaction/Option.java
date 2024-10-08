package core.game.interaction;

import core.cache.def.impl.ItemDefinition;
import core.cache.def.impl.NPCDefinition;
import core.cache.def.impl.SceneryDefinition;
import core.game.node.Node;
import core.game.node.entity.npc.NPC;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;

/**
 * Represents an Interaction option.
 * @author Emperor
 */
public final class Option {

    /**
     * The player attack option.
     */
    public static final Option _P_ATTACK = new Option("Attack", 0);

    /**
     * The player follow option.
     */
    public static final Option _P_FOLLOW = new Option("Follow", 2);

    /**
     * The player trade option.
     */
    public static final Option _P_TRADE = new Option("Trade with", 3);
    /**
     * The constant _P_GIVETO.
     */
    public static final Option _P_GIVETO = new Option("Give-to", 3);

    /**
     * The constant _P_PICKPOCKET.
     */
    public static final Option _P_PICKPOCKET = new Option("Pickpocket", 4);


    /**
     * The constant _P_EXAMINE.
     */
    public static final Option _P_EXAMINE = new Option("Examine", 7);

    /**
     * The player assist option.
     */
    public static final Option _P_ASSIST = new Option("Req Assist", 6);

    /**
     * A null option.
     */
    public static final Option NULL = new Option("null", 0);

    /**
     * The option name.
     */
    private final String name;

    /**
     * The index.
     */
    private final int index;

    /**
     * The option handler.
     */
    private OptionHandler handler;

    /**
     * Constructs a new {@code Interaction} {@code Object}.
     *
     * @param name  The name.
     * @param index The index.
     */
    public Option(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * Gets the default option handler for the given name.
     *
     * @param node   The node type that has this option.
     * @param nodeId The id of the node.
     * @param name   The name of the option.
     * @return The default option handler for this option.
     */
    public static OptionHandler defaultHandler(Node node, int nodeId, String name) {
        name = name.toLowerCase();
        if (node instanceof NPC) {
            return NPCDefinition.getOptionHandler(nodeId, name);
        }
        if (node instanceof Scenery) {
            return SceneryDefinition.getOptionHandler(nodeId, name);
        }
        if (node instanceof Item) {
            return ItemDefinition.getOptionHandler(nodeId, name);
        }

        return null;
    }

    /**
     * Gets the name.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the index.
     *
     * @return The index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Gets the handler.
     *
     * @return The handler.
     */
    public OptionHandler getHandler() {
        return handler;
    }

    /**
     * Sets the handler.
     *
     * @param handler The handler to set.
     * @return This option instance.
     */
    public Option setHandler(OptionHandler handler) {
        this.handler = handler;
        return this;
    }
}
