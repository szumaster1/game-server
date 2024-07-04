package content.global.skill.combat.summoning.familiar.npc;

import content.global.skill.combat.summoning.familiar.BurdenBeast;
import content.global.skill.combat.summoning.familiar.Familiar;
import content.global.skill.combat.summoning.familiar.FamiliarSpecial;
import core.game.node.entity.Entity;
import core.game.node.entity.impl.Projectile;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.tools.RandomFunction;

@Initializable
public class AbyssalParasiteNPC extends BurdenBeast {
    @SuppressWarnings("unused")
    private final boolean specialMove = false;

    public AbyssalParasiteNPC() {
        this(null, 6818);
    }

    public AbyssalParasiteNPC(Player owner, int id) {
        super(owner, id, 3000, 12035, 1, 7);
    }

    @Override
    public Familiar construct(Player owner, int id) {
        return new AbyssalParasiteNPC(owner, id);
    }

    @Override
    public boolean isAllowed(Player owner, Item item) {
        if (item.getId() != 1436 && item.getId() != 7936) {
            owner.getPacketDispatch().sendMessage("Your familiar can only hold unnoted essence.");
            return false;
        }
        return super.isAllowed(owner, item);
    }

    @Override
    protected boolean specialMove(FamiliarSpecial special) {
        final Entity target = special.getTarget();
        if (!canCombatSpecial(target)) {
            return false;
        }
        faceTemporary(target, 2);
        sendFamiliarHit(target, 7);
        visualize(Animation.create(7672), Graphic.create(1422));
        Projectile.magic(this, target, 1423, 40, 36, 51, 10).send();
        target.getSkills().decrementPrayerPoints(RandomFunction.random(1, 3));
        return true;
    }

    @Override
    public int[] getIds() {
        return new int[]{6818, 6819};
    }

}
