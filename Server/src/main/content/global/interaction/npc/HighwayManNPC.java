package content.global.interaction.npc;

import core.game.node.entity.Entity;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.diary.DiaryType;
import core.game.world.map.Location;
import core.plugin.Initializable;

/**
 * The Highway man npc.
 */
@Initializable
public final class HighwayManNPC extends AbstractNPC {

    /**
     * Instantiates a new Highway man npc.
     *
     * @param id       the id
     * @param location the location
     */
    public HighwayManNPC(int id, Location location) {
        super(id, location);
    }

    /**
     * Instantiates a new Highway man npc.
     */
    public HighwayManNPC() {
        super(0, null);
    }

    @Override
    public void finalizeDeath(Entity killer) {
        super.finalizeDeath(killer);
        if (getId() == 180) {
            if (killer instanceof Player) {
                final Player player = killer.asPlayer();
                if (!player.getAchievementDiaryManager().getDiary(DiaryType.FALADOR).isComplete(0, 10)) {
                    player.getAchievementDiaryManager().getDiary(DiaryType.FALADOR).updateTask(player, 0, 10, true);
                }
            }
        }
    }

    @Override
    public void onAttack(Entity target) {
        sendChat("Stand and deliver!");
    }

    @Override
    public int[] getIds() {
        return new int[]{180, 2677};
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new HighwayManNPC(id, location);
    }

}
