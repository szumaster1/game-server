package content.region.misthalin.quest.free.dragonslayer.npc;

import content.region.misthalin.quest.free.dragonslayer.DragonSlayer;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.*;
import core.game.node.entity.combat.equipment.ArmourSet;
import core.game.node.entity.combat.spell.CombatSpell;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.SpellBookManager;
import core.game.node.entity.skill.Skills;
import core.game.node.item.GroundItemManager;
import core.game.node.item.Item;
import core.game.world.map.Location;
import core.game.world.map.RegionManager;
import core.utilities.RandomFunction;

/**
 * The Meldar mad npc.
 */
public final class MeldarMadNPC extends AbstractNPC {

    private static final int[] ID = {753};


    /**
     * The constant CABBAGE.
     */
    public static final Item CABBAGE = new Item(1965);


    /**
     * The constant MESSAGES.
     */
    public static final String[] MESSAGES = new String[]{"Feel the wrath of my feet!", "By the power of custard!", "Let me drink my tea in peace.", "Leave me alone, I need to feed my pet rock."};


    private final MeldarSwingHandler combatHandler = new MeldarSwingHandler();


    /**
     * Instantiates a new Meldar mad npc.
     */
    public MeldarMadNPC() {
        super(0, null);
    }


    private MeldarMadNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new MeldarMadNPC(id, location);
    }

    @Override
    public void init() {
        super.init();
        getSkills().setLevel(Skills.MAGIC, 40);
        getSkills().setLevel(Skills.ATTACK, 30);
        getSkills().setLevel(Skills.STRENGTH, 35);
        getSkills().setLevel(Skills.DEFENCE, 10);
    }

    @Override
    public void handleTickActions() {
        super.handleTickActions();
        if (!DeathTask.isDead(this) && getProperties().getCombatPulse().isAttacking() && RandomFunction.random(0, 4) == 1) {
            sendChat(MESSAGES[RandomFunction.random(MESSAGES.length)]);
        }
    }

    @Override
    public void finalizeDeath(final Entity killer) {
        super.finalizeDeath(killer);
        GroundItemManager.create(DragonSlayer.PURPLE_KEY, getLocation(), ((Player) killer));
    }

    @Override
    public CombatSwingHandler getSwingHandler(boolean swing) {
        return combatHandler;
    }

    @Override
    public int[] getIds() {
        return ID;
    }


    /**
     * The Meldar swing handler.
     */
    public class MeldarSwingHandler extends CombatSwingHandler {


        private CombatStyle style = CombatStyle.MAGIC;


        private final int[] SPELL_IDS = new int[]{8, 2, 7, 11};


        /**
         * Instantiates a new Meldar swing handler.
         */
        public MeldarSwingHandler() {
            super(CombatStyle.MAGIC);
        }

        @Override
        public InteractionType canSwing(Entity entity, Entity victim) {
            return getType().getSwingHandler().canSwing(entity, victim);
        }

        @Override
        public int swing(Entity entity, Entity victim, BattleState state) {
            if (RandomFunction.random(5) == 3) {
                style = CombatStyle.MELEE;
            } else {
                style = CombatStyle.MAGIC;
            }
            return 2;
        }

        @Override
        public void visualize(Entity entity, Entity victim, BattleState state) {
            if (style == CombatStyle.MAGIC) {
                state.setSpell(getCombatSpell());
                for (int i = 0; i < 2; i++) {
                    Location l = getLocation().transform(RandomFunction.random(1, 5), RandomFunction.random(1, 5), 0);
                    if (RegionManager.isTeleportPermitted(l) && GroundItemManager.get(CABBAGE.getId(), l, null) == null && l.getY() <= 9651 && l.getY() >= 9644) {
                        if (victim instanceof Player)
                            ((Player) victim).getPacketDispatch().sendPositionedGraphic(86, 1, 0, l);
                        GroundItemManager.create(CABBAGE, l, ((Player) victim));
                    }
                }
            }
            style.getSwingHandler().visualize(entity, victim, state);
        }

        @Override
        public void visualizeImpact(Entity entity, Entity victim, BattleState state) {
            style.getSwingHandler().visualizeImpact(entity, victim, state);
        }

        @Override
        public void adjustBattleState(Entity entity, Entity victim, BattleState state) {
            style.getSwingHandler().adjustBattleState(entity, victim, state);
        }

        @Override
        public int calculateAccuracy(Entity entity) {
            return style.getSwingHandler().calculateAccuracy(entity);
        }

        @Override
        public int calculateDefence(Entity victim, Entity attacker) {
            return style.getSwingHandler().calculateDefence(victim, attacker);
        }

        @Override
        public int calculateHit(Entity entity, Entity victim, double modifier) {
            return style.getSwingHandler().calculateHit(entity, victim, modifier);
        }

        @Override
        public void impact(Entity entity, Entity victim, BattleState state) {
            style.getSwingHandler().impact(entity, victim, state);
        }

        @Override
        public ArmourSet getArmourSet(Entity e) {
            return style.getSwingHandler().getArmourSet(e);
        }

        @Override
        public double getSetMultiplier(Entity e, int skillId) {
            return style.getSwingHandler().getSetMultiplier(e, skillId);
        }


        /**
         * Gets combat spell.
         *
         * @return the combat spell
         */
        public CombatSpell getCombatSpell() {
            return ((CombatSpell) SpellBookManager.SpellBook.MODERN.getSpell(SPELL_IDS[RandomFunction.random(SPELL_IDS.length)]));
        }
    }
}
