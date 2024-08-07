package core.game.interaction;

import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;

import java.util.HashMap;

/**
 * Plugin interaction manager.
 */
public class PluginInteractionManager {
    private static final HashMap<Integer, PluginInteraction> npcInteractions = new HashMap<>();
    private static final HashMap<Integer, PluginInteraction> objectInteractions = new HashMap<>();
    private static final HashMap<Integer, PluginInteraction> useWithInteractions = new HashMap<>();
    private static final HashMap<Integer, PluginInteraction> groundItemInteractions = new HashMap<>();

    /**
     * Register.
     *
     * @param interaction the interaction
     * @param type        the type
     */
    public static void register(PluginInteraction interaction, InteractionType type) {
        switch (type) {
            case OBJECT:
                for (int i = 0; i < interaction.ids.length; i++) {
                    objectInteractions.putIfAbsent(interaction.ids[i], interaction);
                }
                break;
            case USEWITH:
                for (int i = 0; i < interaction.ids.length; i++) {
                    useWithInteractions.putIfAbsent(interaction.ids[i], interaction);
                }
                break;
            case NPC:
                for (int i = 0; i < interaction.ids.length; i++) {
                    npcInteractions.putIfAbsent(interaction.ids[i], interaction);
                }
                break;
            case ITEM:
                for (int i = 0; i < interaction.ids.length; i++) {
                    groundItemInteractions.putIfAbsent(interaction.ids[i], interaction);
                }
                break;
        }
    }

    /**
     * Handle boolean.
     *
     * @param player the player
     * @param object the object
     * @return the boolean
     */
    public static boolean handle(Player player, Scenery object) {
        PluginInteraction i = objectInteractions.get(object.getId());
        if (i == null) {
            return false;
        } else {
            return i.handle(player, object);
        }
    }

    /**
     * Handle boolean.
     *
     * @param player the player
     * @param event  the event
     * @return the boolean
     */
    public static boolean handle(Player player, NodeUsageEvent event) {
        PluginInteraction i = useWithInteractions.get(event.getUsed().asItem().getId());
        if (i == null) {
            return false;
        } else {
            return i.handle(player, event);
        }
    }

    /**
     * Handle boolean.
     *
     * @param player the player
     * @param npc    the npc
     * @param option the option
     * @return the boolean
     */
    public static boolean handle(Player player, NPC npc, Option option) {
        PluginInteraction i = npcInteractions.get(npc.getId());
        if (i == null) {
            return false;
        } else {
            return i.handle(player, npc, option);
        }
    }

    /**
     * Handle boolean.
     *
     * @param player the player
     * @param item   the item
     * @param option the option
     * @return the boolean
     */
    public static boolean handle(Player player, Item item, Option option) {
        PluginInteraction i = groundItemInteractions.get(item.getId());
        if (i == null) {
            return false;
        } else {
            return i.handle(player, item, option);
        }
    }

    /**
     * The enum Interaction type.
     */
    public enum InteractionType {
        /**
         * Npc interaction type.
         */
        NPC,
        /**
         * Object interaction type.
         */
        OBJECT,
        /**
         * Usewith interaction type.
         */
        USEWITH,
        /**
         * Item interaction type.
         */
        ITEM;
    }
}
