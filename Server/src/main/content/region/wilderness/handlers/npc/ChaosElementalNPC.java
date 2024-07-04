package content.region.wilderness.handlers.npc;

import core.api.consts.NPCs;
import core.api.consts.Sounds;
import content.data.BossKillCounter;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.combat.CombatSwingHandler;
import core.game.node.entity.combat.MultiSwingHandler;
import core.game.node.entity.combat.equipment.SwitchAttack;
import core.game.node.entity.impl.Projectile;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.tools.RandomFunction;

import static core.api.ContentAPIKt.*;

/**
 * The Chaos elemental npc.
 */
@Initializable
public class ChaosElementalNPC extends AbstractNPC {

    private final MultiSwingHandler COMBAT_HANDLER = new ChaosCombatHandler();

    /**
     * Instantiates a new Chaos elemental npc.
     *
     * @param id       the id
     * @param location the location
     */
    public ChaosElementalNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public void tick() {
        super.tick();
        if (!isActive()) {
            getProperties().getCombatPulse().stop();
        }
    }

    /**
     * Instantiates a new Chaos elemental npc.
     */
    public ChaosElementalNPC() {
        this(-1, null);
    }

    @Override
    public CombatSwingHandler getSwingHandler(boolean swing) {
        return COMBAT_HANDLER;
    }

    @Override
    public void sendImpact(BattleState state) {
        if (state.getEstimatedHit() > 28) {
            state.setEstimatedHit(RandomFunction.random(20, 28)); //possibly absolutely mental "haha random" damage adjustment. not sure. - crash
        }
        super.sendImpact(state);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new ChaosElementalNPC(id, location);
    }

    @Override
    public int[] getIds() {
        return new int[]{NPCs.CHAOS_ELEMENTAL_3200};
    }

    @Override
    public void finalizeDeath(Entity killer) {
        super.finalizeDeath(killer);
        BossKillCounter.addtoKillcount((Player) killer, this.getId());
    }

    /**
     * The Chaos combat handler.
     */
    public static class ChaosCombatHandler extends MultiSwingHandler {


        private static final Animation PROJECTILE_ANIM = new Animation(3148);


        private static final Projectile PRIMARY_PROJECTILE = Projectile.create((Entity) null, null, 557, 60, 55, 41, 46, 20, 255);


        private static final SwitchAttack[] ATTACKS = new SwitchAttack[]{new SwitchAttack(CombatStyle.MELEE.getSwingHandler(), PROJECTILE_ANIM, new Graphic(556), null, PRIMARY_PROJECTILE), // primary
                new SwitchAttack(CombatStyle.RANGE.getSwingHandler(), PROJECTILE_ANIM, new Graphic(556), null, PRIMARY_PROJECTILE),// primary
                new SwitchAttack(CombatStyle.MAGIC.getSwingHandler(), PROJECTILE_ANIM, new Graphic(556), null, PRIMARY_PROJECTILE),// primary
                new SwitchAttack(CombatStyle.MAGIC.getSwingHandler(), PROJECTILE_ANIM, new Graphic(553), null, Projectile.create((Entity) null, null, 554, 60, 55, 41, 46, 20, 255)) {
                    @Override
                    public boolean canSelect(Entity entity, Entity victim, BattleState state) {
                        return true;
                    }
                },// tele
                new SwitchAttack(CombatStyle.MAGIC.getSwingHandler(), PROJECTILE_ANIM, new Graphic(550), null, Projectile.create((Entity) null, null, 551, 60, 55, 41, 46, 20, 255)) {
                    @Override
                    public boolean canSelect(Entity entity, Entity victim, BattleState state) {
                        return true;
                    }
                }};


        /**
         * Instantiates a new Chaos combat handler.
         */
        public ChaosCombatHandler() {
            super(ATTACKS);
        }

        @Override
        public void impact(Entity entity, Entity victim, BattleState state) {
            super.impact(entity, victim, state);
            SwitchAttack attack = super.getCurrent();
            if (victim instanceof Player) {
                Player player = victim.asPlayer();
                if (player == null) {
                    return;
                }
                if (attack.getProjectile().getProjectileId() == 557) {
                    playGlobalAudio(player.getLocation(), Sounds.CHAOS_ELEMENTAL_DISCORD_IMPACT_350); // C. Elemental Discord Impact SFX
                } else if (attack.getProjectile().getProjectileId() == 554) {
                    playAudio(player, Sounds.CHAOS_ELEMENTAL_CONFUSION_IMPACT_346); // C. Elemental Confusion Impact SFX
                    Location loc = getPathableRandomLocalCoordinate(player, 10, entity.getLocation(), 3);
                    player.teleport(loc);
                } else if (attack.getProjectile().getProjectileId() == 551) {
                    playGlobalAudio(player.getLocation(), Sounds.CHAOS_ELEMENTAL_MADNESS_IMPACT_353); // C. Elemental Madness Impact SFX
                    if (player.getInventory().freeSlots() < 1 || player.getEquipment().itemCount() < 1) {
                        return;
                    }
                    Item e = null;
                    int tries = 0;
                    while (e == null && tries < 30) {
                        e = player.getEquipment().toArray()[RandomFunction.random(player.getEquipment().itemCount())];
                        tries++;
                        if (e != null && player.getInventory().hasSpaceFor(e)) {
                            break;
                        }
                        e = null;
                    }
                    if (e == null) {
                        return;
                    }
                    player.lock(1);
                    if (!player.getEquipment().containsItem(e)) {
                        return;
                    }
                    if (player.getEquipment().remove(e)) {
                        player.getInventory().add(e);
                    }
                }
            }
        }


        /**
         * Gets random loc.
         *
         * @param entity the entity
         * @return the random loc
         */
        public Location getRandomLoc(Entity entity) {
            Location l = entity.getLocation();
            boolean negative = RandomFunction.random(2) == 1;
            return l.getLocation().transform((negative ? RandomFunction.random(-10, 10) : RandomFunction.random(0, 10)), (negative ? RandomFunction.random(-10, 10) : RandomFunction.random(0, 10)), 0);
        }
    }

}
