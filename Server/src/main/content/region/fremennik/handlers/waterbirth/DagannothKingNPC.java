package content.region.fremennik.handlers.waterbirth;

import content.data.BossKillCounter;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.world.map.Location;
import core.game.world.map.RegionManager;
import core.tools.RandomFunction;

import java.util.List;

/**
 * The Dagannoth king npc.
 */
public final class DagannothKingNPC extends AbstractNPC {

    private DagType type;

    /**
     * Instantiates a new Dagannoth king npc.
     */
    public DagannothKingNPC() {
        super(-1, null);
    }

    /**
     * Instantiates a new Dagannoth king npc.
     *
     * @param id       the id
     * @param location the location
     */
    public DagannothKingNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public void init() {
        type = DagType.forId(getId());
        super.init();
    }

    @Override
    public void checkImpact(BattleState state) {
        CombatStyle style = state.getStyle();
        if (style == null) {
            style = state.getAttacker().getProperties().getCombatPulse().getStyle();
        }
        if (type.isImmune(style)) {
            state.neutralizeHits();
        }
    }

    @Override
    public double getLevelMod(Entity entity, Entity victim) {
        if (type == DagType.PRIME) {
            return 3.5;
        }
        return 0.0;
    }

    @Override
    public void sendImpact(BattleState state) {
        if (state.getEstimatedHit() > type.getMaxHit()) {
            state.setEstimatedHit(RandomFunction.random(type.getMaxHit() - 5, type.getMaxHit()));
        }
        if (type != DagType.REX && RandomFunction.random(5) <= 2) {
            List<Player> players = RegionManager.getLocalPlayers(this, 9);
            if (players.size() <= 1) {
                return;
            }
            Player newP = players.get(RandomFunction.random(players.size()));
            if (newP != null) {
                getProperties().getCombatPulse().stop();
                getAggressiveHandler().setPauseTicks(2);
                attack(newP);
            }
        }
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new DagannothKingNPC(id, location);
    }

    @Override
    public int[] getIds() {
        return new int[]{2881, 2882, 2883};
    }

    @Override
    public void finalizeDeath(Entity killer) {
        super.finalizeDeath(killer);
        if (getId() == 2881 || getId() == 2882 || getId() == 2883) {
            BossKillCounter.addtoKillcount((Player) killer, this.getId());
        }
    }

    /**
     * The enum Dag type.
     */
    public enum DagType {
        /**
         * Supreme dag type.
         */
        SUPREME(2881, CombatStyle.RANGE, CombatStyle.MELEE, CombatStyle.MAGIC, 30),
        /**
         * Prime dag type.
         */
        PRIME(2882, CombatStyle.MAGIC, CombatStyle.RANGE, CombatStyle.MELEE, 61),
        /**
         * Rex dag type.
         */
        REX(2883, CombatStyle.MELEE, CombatStyle.MAGIC, CombatStyle.RANGE, 28);


        private final int id;


        private final CombatStyle style;


        private final CombatStyle weakStyle;


        private final CombatStyle immuneStyle;


        private final int maxHit;


        DagType(int id, CombatStyle style, CombatStyle weakStyle, CombatStyle immuneStyle, int maxHit) {
            this.id = id;
            this.style = style;
            this.weakStyle = weakStyle;
            this.immuneStyle = immuneStyle;
            this.maxHit = maxHit;
        }


        /**
         * Gets max hit.
         *
         * @return the max hit
         */
        public int getMaxHit() {
            return maxHit;
        }


        /**
         * For id dag type.
         *
         * @param id the id
         * @return the dag type
         */
        public static DagType forId(int id) {
            for (DagType type : values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return null;
        }


        /**
         * Is immune boolean.
         *
         * @param style the style
         * @return the boolean
         */
        public boolean isImmune(CombatStyle style) {
            return style == immuneStyle || style == this.style;
        }


        /**
         * Gets id.
         *
         * @return the id
         */
        public int getId() {
            return id;
        }


        /**
         * Gets style.
         *
         * @return the style
         */
        public CombatStyle getStyle() {
            return style;
        }


        /**
         * Gets immune style.
         *
         * @return the immune style
         */
        public CombatStyle getImmuneStyle() {
            return immuneStyle;
        }


        /**
         * Gets weak style.
         *
         * @return the weak style
         */
        public CombatStyle getWeakStyle() {
            return weakStyle;
        }

    }

}
