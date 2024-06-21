package content.region.misthalin.quest.free.shieldofarrav.plugin;

import content.region.misthalin.quest.free.shieldofarrav.ShieldofArrav;
import core.game.dialogue.Dialogue;
import core.game.dialogue.FacialExpression;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;

/**
 * The Jonnythe beard plugin.
 */
public final class JonnytheBeard extends Dialogue {

    /**
     * Instantiates a new Jonnythe beard plugin.
     */
    public JonnytheBeard() {

    }

    /**
     * Instantiates a new Jonnythe beard plugin.
     *
     * @param player the player
     */
    public JonnytheBeard(Player player) {
        super(player);
    }

    @Override
    public Dialogue newInstance(Player player) {
        return new JonnytheBeard(player);
    }

    @Override
    public boolean open(Object... args) {
        npc = (NPC) args[0];
        if (ShieldofArrav.isPhoenixMission(player)) {
            player.getPacketDispatch().sendMessage("Johnny the beard is not interested in talking.");
            end();
            return true;
        }
        interpreter.sendDialogues(npc, FacialExpression.HALF_GUILTY, "Will you buy me a beer?");
        stage = 0;
        return true;
    }

    @Override
    public boolean handle(int interfaceId, int buttonId) {
        switch (stage) {
            case 0:
                interpreter.sendDialogues(player, FacialExpression.HALF_GUILTY, "No, I don't think I will.");
                stage = 1;
                break;
            case 1:
                end();
                break;
        }
        return true;
    }

    @Override
    public int[] getIds() {
        return new int[]{645};
    }
}