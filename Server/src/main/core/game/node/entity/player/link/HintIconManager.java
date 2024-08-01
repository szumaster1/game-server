package core.game.node.entity.player.link;

import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.player.Player;
import core.network.packet.PacketRepository;
import core.network.packet.context.HintIconContext;
import core.network.packet.outgoing.HintIcon;

public final class HintIconManager {

    public static final int MAXIMUM_SIZE = 8;

    public static final int DEFAULT_ARROW = 1;

    public static final int DEFAULT_MODEL = -1;

    public static final int ARROW_CIRCLE_MODEL = 40497;

    private HintIconContext[] hintIcons = new HintIconContext[MAXIMUM_SIZE];

    public HintIconManager() {
        /*
         * empty.
         */
    }

    public static int registerHintIcon(Player player, Node target) {
        return registerHintIcon(player, target, DEFAULT_ARROW, DEFAULT_MODEL, player.getHintIconManager().freeSlot());
    }

    public static int registerHeightHintIcon(Player player, Node target, int height) {
        return registerHintIcon(player, target, DEFAULT_ARROW, DEFAULT_MODEL, player.getHintIconManager().freeSlot(), height);
    }

    public static int registerHintIcon(Player player, Node target, int arrowId) {
        return registerHintIcon(player, target, arrowId, DEFAULT_MODEL, player.getHintIconManager().freeSlot());
    }

    public static int registerHintIcon(Player player, Node target, int arrowId, int modelId) {
        return registerHintIcon(player, target, arrowId, modelId, player.getHintIconManager().freeSlot());
    }

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

    public static int registerHintIcon(Player player, Node target, int arrowId, int modelId, int slot, int height) {
        int type = 2;
        if (target instanceof Entity) {
            type = target instanceof Player ? 10 : 1;
        }
        return registerHintIcon(player, target, arrowId, modelId, slot, height, type);
    }

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

    public void clear() {
        for (int i = 0; i < hintIcons.length; i++) {
            HintIconContext icon = hintIcons[i];
            if (icon != null) {
                removeHintIcon(icon.getPlayer(), i);
            }
        }
    }

    public int freeSlot() {
        for (int i = 0; i < hintIcons.length; i++) {
            if (hintIcons[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public HintIconContext getIcon(int slot) {
        return hintIcons[slot];
    }
}
