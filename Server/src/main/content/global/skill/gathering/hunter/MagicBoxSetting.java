package content.global.skill.gathering.hunter;

import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.world.update.flag.context.Animation;

/**
 * The Magic box setting.
 */
public final class MagicBoxSetting extends TrapSetting {

    /**
     * Instantiates a new Magic box setting.
     */
    public MagicBoxSetting() {
        super(10025, new int[]{19223}, new int[]{1470, 1472, 1476, 1474}, "activate", 19224, Animation.create(5208), Animation.create(9726), 27);
    }

    @Override
    public void handleCatch(int counter, TrapWrapper wrapper, TrapNode node, NPC npc, boolean success) {
        switch (counter) {
            case 2:
                if (success) {
                    wrapper.getPlayer().getPacketDispatch().sendPositionedGraphic(932, 0, 0, npc.getLocation());
                }
                break;
            case 3:
                npc.moveStep();
                break;
        }
    }

    @Override
    public void addTool(Player player, TrapWrapper wrapper, int type) {
        if (!wrapper.isCaught()) {
            super.addTool(player, wrapper, type);
        }
    }
}
