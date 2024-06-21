package content.global.skill.support.slayer.npc;

import content.global.skill.support.slayer.SlayerUtils;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.world.map.Location;
import core.plugin.Initializable;

/**
 * The Kurask npc.
 */
@Initializable
public final class KuraskNPC extends AbstractNPC {

    /**
     * Instantiates a new Kurask npc.
     *
     * @param id       the id
     * @param location the location
     */
    public KuraskNPC(int id, Location location) {
        super(id, location);
    }

    /**
     * Instantiates a new Kurask npc.
     */
    public KuraskNPC() {
        super(0, null);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new KuraskNPC(id, location);
    }

    @Override
    public void checkImpact(final BattleState state) {
        super.checkImpact(state);
        boolean effective = false;
        if (state.getAttacker() instanceof Player) {
            final Player player = (Player) state.getAttacker();
            effective = SlayerUtils.hasBroadWeaponEquipped(player, state);
        }
        if (!effective) {
            state.setEstimatedHit(0);
            if (state.getSecondaryHit() > 0) {
                state.setSecondaryHit(0);
            }
        }
    }

    @Override
    public int[] getIds() {
        return new int[]{1608, 1609, 4229};
    }

}
