package content.region.asgarnia.quest.bkfortress;

import core.game.node.entity.Entity;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.diary.DiaryType;
import core.game.world.map.Location;
import core.plugin.Initializable;

/**
 * The Black knight npc.
 */
@Initializable
public final class BlackKnightNPC extends AbstractNPC {

    /**
     * Instantiates a new Black knight npc.
     */
    public BlackKnightNPC() {
        super(0, null);
    }

    /**
     * Instantiates a new Black knight npc.
     *
     * @param id       the id
     * @param location the location
     */
    public BlackKnightNPC(int id, Location location) {
        super(id, location, true);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new BlackKnightNPC(id, location);
    }

    @Override
    public void finalizeDeath(final Entity killer) {
        super.finalizeDeath(killer);
        if (killer instanceof Player) {
            final Player player = killer.asPlayer();
            if (!player.getAchievementDiaryManager().getDiary(DiaryType.FALADOR).isComplete(1, 3)) {
                player.getAchievementDiaryManager().getDiary(DiaryType.FALADOR).updateTask(player, 1, 3, true);
            }
        }
    }

    @Override
    public int[] getIds() {
        return new int[]{178, 179, 610, 2698, 6189};
    }

}
