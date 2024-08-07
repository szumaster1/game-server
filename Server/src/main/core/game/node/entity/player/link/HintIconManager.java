package core.game.node.entity.player.link;

import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.player.Player;
import core.network.packet.PacketRepository;
import core.network.packet.context.HintIconContext;
import core.network.packet.outgoing.HintIcon;

/**
 * Hint icon manager.
 */
public final class HintIconManager {

    /**
     * The constant MAXIMUM_SIZE.
     */
    public static final int MAXIMUM_SIZE = 8;

    /**
     * The constant DEFAULT_ARROW.
     */
    public static final int DEFAULT_ARROW = 1;

    /**
     * The constant DEFAULT_MODEL.
     */
    public static final int DEFAULT_MODEL = -1;

    /**
     * The constant ARROW_CIRCLE_MODEL.
     */
    public static final int ARROW_CIRCLE_MODEL = 40497;

    private HintIconContext[] hintIcons = new HintIconContext[MAXIMUM_SIZE];

    /**
     * Instantiates a new Hint icon manager.
     */
    public HintIconManager() {
        /*
         * empty.
         */
    }

    /**
     * Register hint icon int.
     *
     * @param player the player
     * @param target the target
     * @return the int
     */
    public static int registerHintIcon(Player player, Node target) {
        return registerHintIcon(player, target, DEFAULT_ARROW, DEFAULT_MODEL, player.getHintIconManager().freeSlot());
    }

    /**
     * Register height hint icon int.
     *
     * @param player the player
     * @param target the target
     * @param height the height
     * @return the int
     */
    public static int registerHeightHintIcon(Player player, Node target, int height) {
        return registerHintIcon(player, target, DEFAULT_ARROW, DEFAULT_MODEL, player.getHintIconManager().freeSlot(), height);
    }

    /**
     * Register hint icon int.
     *
     * @param player  the player
     * @param target  the target
     * @param arrowId the arrow id
     * @return the int
     */
    public static int registerHintIcon(Player player, Node target, int arrowId) {
        return registerHintIcon(player, target, arrowId, DEFAULT_MODEL, player.getHintIconManager().freeSlot());
    }

    /**
     * Register hint icon int.
     *
     * @param player  the player
     * @param target  the target
     * @param arrowId the arrow id
     * @param modelId the model id
     * @return the int
     */
    public static int registerHintIcon(Player player, Node target, int arrowId, int modelId) {
        return registerHintIcon(player, target, arrowId, modelId, player.getHintIconManager().freeSlot());
    }

    /**
     * Register hint icon int.
     *
     * @param player  the player
     * @param target  the target
     * @param arrowId the arrow id
     * @param modelId the model id
     * @param slot    the slot
     * @return the int
     */
    public static int registerHintIcon(Player player, Node target, int arrowId, int modelId, int slot) {
        if (slot < 0) {
            return -1;
        }
        if (target == null) {
            return 0;
        }
        HintIconManager mng = player.getHintIconManager();
        HintIconContext icon = new HintIconContext(player, slot, arrowId, target, modelId);
        PacketRepository.send(HintIcon.class, icon);
        mng.hintIcons[slot] = icon;
        return slot;
    }

    /**
     * Register hint icon int.
     *
     * @param player  the player
     * @param target  the target
     * @param arrowId the arrow id
     * @param modelId the model id
     * @param slot    the slot
     * @param height  the height
     * @return the int
     */
    public static int registerHintIcon(Player player, Node target, int arrowId, int modelId, int slot, int height) {
        int type = 2;
        if (target instanceof Entity) {
            type = target instanceof Player ? 10 : 1;
        }
        return registerHintIcon(player, target, arrowId, modelId, slot, height, type);
    }

    /**
     * Register hint icon int.
     *
     * @param player     the player
     * @param target     the target
     * @param arrowId    the arrow id
     * @param modelId    the model id
     * @param slot       the slot
     * @param height     the height
     * @param targetType the target type
     * @return the int
     */
    public static int registerHintIcon(Player player, Node target, int arrowId, int modelId, int slot, int height, int targetType) {
        if (slot < 0) {
            return -1;
        }
        HintIconManager mng = player.getHintIconManager();
        HintIconContext icon = new HintIconContext(player, slot, arrowId, targetType, target, modelId, height);
        PacketRepository.send(HintIcon.class, icon);
        mng.hintIcons[slot] = icon;
        return slot;
    }

    /**
     * Remove hint icon.
     *
     * @param player the player
     * @param slot   the slot
     */
    public static void removeHintIcon(Player player, int slot) {
        if (slot < 0) {
            return;
        }
        HintIconManager mng = player.getHintIconManager();
        HintIconContext icon = mng.hintIcons[slot];
        if (icon != null) {
            icon.targetType = 0;
            PacketRepository.send(HintIcon.class, icon);
            mng.hintIcons[slot] = null;
        }
    }

    /**
     * Clear.
     */
    public void clear() {
        for (int i = 0; i < hintIcons.length; i++) {
            HintIconContext icon = hintIcons[i];
            if (icon != null) {
                removeHintIcon(icon.getPlayer(), i);
            }
        }
    }

    /**
     * Free slot int.
     *
     * @return the int
     */
    public int freeSlot() {
        for (int i = 0; i < hintIcons.length; i++) {
            if (hintIcons[i] == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets icon.
     *
     * @param slot the slot
     * @return the icon
     */
    public HintIconContext getIcon(int slot) {
        return hintIcons[slot];
    }
}
