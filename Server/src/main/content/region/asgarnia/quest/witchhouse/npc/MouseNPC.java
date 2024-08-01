package content.region.asgarnia.quest.witchhouse.npc;

import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.world.GameWorld;
import core.game.world.map.Location;

public class MouseNPC extends AbstractNPC {

    private Player player;

    private int endTime;

    private static final int[] ID = {901};


    public MouseNPC() {
        super(0, null);
    }

    private MouseNPC(int id, Location location) {
        super(id, location);
        this.endTime = (int) (GameWorld.getTicks() + (4 / 0.6));
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new MouseNPC(id, location);
    }

    @Override
    public void handleTickActions() {
        super.handleTickActions();
        if (player.getAttribute("mouse_out") == null) {
            clear();
        }
        if (GameWorld.getTicks() > endTime) {
            clear();
        }
        if (!player.isActive() || player.getLocation().getDistance(getLocation()) > 8) {
            clear();
        }

    }

    @Override
    public void clear() {
        super.clear();
        player.removeAttribute("mouse_out");
    }

    @Override
    public int[] getIds() {
        return ID;
    }


    public void setPlayer(Player player) {
        this.player = player;
    }


    public Player getPlayer() {
        return player;
    }


}
