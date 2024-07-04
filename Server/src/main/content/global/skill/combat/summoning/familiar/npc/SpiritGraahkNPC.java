package content.global.skill.combat.summoning.familiar.npc;

import content.global.skill.combat.summoning.familiar.Familiar;
import content.global.skill.combat.summoning.familiar.FamiliarSpecial;
import core.game.node.entity.combat.equipment.WeaponInterface;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.SkillBonus;
import core.game.node.entity.skill.Skills;
import core.plugin.Initializable;

@Initializable
public class SpiritGraahkNPC extends Familiar {

    public SpiritGraahkNPC() {
        this(null, 7363);
    }

    public SpiritGraahkNPC(Player owner, int id) {
        super(owner, id, 4900, 12810, 3, WeaponInterface.STYLE_AGGRESSIVE);
        boosts.add(new SkillBonus(Skills.HUNTER, 5));
    }

    @Override
    public Familiar construct(Player owner, int id) {
        return new SpiritGraahkNPC(owner, id);
    }

    @Override
    protected boolean specialMove(FamiliarSpecial special) {
        if (!super.isOwnerAttackable()) {
            return false;
        }
        call();
        return true;
    }

    @Override
    public int[] getIds() {
        return new int[]{7363, 7364};
    }

}
