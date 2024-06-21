package content.global.skill.combat.summoning.familiar.npc;

import content.global.skill.combat.summoning.familiar.Familiar;
import content.global.skill.combat.summoning.familiar.FamiliarSpecial;
import core.game.node.entity.combat.equipment.WeaponInterface;
import core.game.node.entity.player.Player;
import core.plugin.Initializable;

/**
 * The Spirit dagannoth npc.
 */
@Initializable
public class SpiritDagannothNPC extends Familiar {
    /**
     * Instantiates a new Spirit dagannoth npc.
     */
    public SpiritDagannothNPC() {
        this(null, 6804);
    }

    /**
     * Instantiates a new Spirit dagannoth npc.
     *
     * @param owner the owner
     * @param id    the id
     */
    public SpiritDagannothNPC(Player owner, int id) {
        super(owner, id, 5700, 12017, 6, WeaponInterface.STYLE_CONTROLLED);
    }

    @Override
    public Familiar construct(Player owner, int id) {
        return new SpiritDagannothNPC(owner, id);
    }

    @Override
    protected boolean specialMove(FamiliarSpecial special) {
        return false;
    }

    @Override
    public int[] getIds() {
        return new int[]{6804, 6805};
    }

}
