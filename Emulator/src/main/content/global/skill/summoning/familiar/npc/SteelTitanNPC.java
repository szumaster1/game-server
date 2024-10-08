package content.global.skill.summoning.familiar.npc;

import content.global.skill.summoning.familiar.Familiar;
import content.global.skill.summoning.familiar.FamiliarSpecial;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.combat.MultiSwingHandler;
import core.game.node.entity.combat.equipment.SwitchAttack;
import core.game.node.entity.combat.equipment.WeaponInterface;
import core.game.node.entity.impl.Projectile;
import core.game.node.entity.player.Player;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.tools.RandomFunction;

/**
 * Steel titan npc.
 */
@Initializable
public final class SteelTitanNPC extends Familiar {

    private static final SwitchAttack[] ATTACKS = {new SwitchAttack(CombatStyle.RANGE.getSwingHandler(), Animation.create(8190), null, null, Projectile.create(null, null, 1445, 60, 36, 41, 46)), new SwitchAttack(CombatStyle.MAGIC.getSwingHandler(), Animation.create(8190), null, null, Projectile.create(null, null, 1445, 60, 36, 41, 46)), new SwitchAttack(CombatStyle.MELEE.getSwingHandler(), Animation.create(8183))};
    private boolean specialMove;

    /**
     * Instantiates a new Steel titan npc.
     */
    public SteelTitanNPC() {
        this(null, 7343);
    }

    /**
     * Instantiates a new Steel titan npc.
     *
     * @param owner the owner
     * @param id    the id
     */
    public SteelTitanNPC(Player owner, int id) {
        super(owner, id, 6400, 12790, 12, WeaponInterface.STYLE_RANGE_ACCURATE);
        super.setCombatHandler(new MultiSwingHandler(true, ATTACKS) {
            @Override
            public int swing(Entity entity, Entity victim, BattleState s) {
                int ticks = super.swing(entity, victim, s);
                if (specialMove) {
                    BattleState[] states = new BattleState[4];
                    for (int i = 1; i < 4; i++) {
                        BattleState state = states[i] = new BattleState(entity, victim);
                        int hit = 0;
                        if (isAccurateImpact(entity, victim)) {
                            int max = calculateHit(entity, victim, 1.0);
                            state.setMaximumHit(max);
                            hit = RandomFunction.random(max);
                        }
                        state.setEstimatedHit(hit);
                        state.setStyle(getCurrent().getStyle());
                    }
                    states[0] = s;
                    s.setTargets(states);
                    specialMove = false;
                }
                return ticks;
            }
        });
    }

    @Override
    public Familiar construct(Player owner, int id) {
        return new SteelTitanNPC(owner, id);
    }

    @Override
    public int[] getIds() {
        return new int[]{7343, 7344};
    }

    @Override
    public boolean specialMove(FamiliarSpecial special) {
        if (specialMove) {
            owner.getPacketDispatch().sendMessage("Your familiar is already charging its attack.");
            return false;
        }
        specialMove = true;
        visualize(Animation.create(8183), Graphic.create(1449));
        return true;
    }

}
