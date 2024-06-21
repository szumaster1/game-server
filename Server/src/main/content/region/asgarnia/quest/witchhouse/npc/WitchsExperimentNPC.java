package content.region.asgarnia.quest.witchhouse.npc;

import core.game.node.entity.Entity;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.game.world.map.RegionManager;
import core.plugin.Initializable;

/**
 * The Witchs experiment npc.
 */
@Initializable
public class WitchsExperimentNPC extends AbstractNPC {
    private ExperimentType type;
    private boolean commenced;
    /**
     * The P.
     */
    Player p;

    /**
     * Instantiates a new Witchs experiment npc.
     */
    public WitchsExperimentNPC() {
        super(0, null);
    }

    /**
     * Instantiates a new Witchs experiment npc.
     *
     * @param id       the id
     * @param location the location
     * @param player   the player
     */
    WitchsExperimentNPC(int id, Location location, Player player) {
        super(id, location);
        this.setWalks(true);
        this.setRespawn(false);
        this.type = WitchsExperimentNPC.ExperimentType.forId(id);
        p = player;
    }

    @Override
    public void handleTickActions() {
        super.handleTickActions();
        if (!p.isActive() || !RegionManager.getLocalPlayers(this).contains(p)) {
            p.removeAttribute("witchs-experiment:npc_spawned");
            clear();
        }
        if (!getProperties().getCombatPulse().isAttacking()) {
            getProperties().getCombatPulse().attack(p);
        }
    }

    @Override
    public void startDeath(Entity killer) {
        if (killer == p) {
            type.transform(this, p);
            return;
        }
        super.startDeath(killer);
    }

    @Override
    public boolean isAttackable(Entity entity, CombatStyle style, boolean message) {
        return p == entity;
    }

    @Override
    public boolean canSelectTarget(Entity target) {
        if (target instanceof Player) {
            Player p = (Player) target;
            return p == this.p;
        }
        return true;
    }


    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new WitchsExperimentNPC(id, location, null);
    }

    @Override
    public int[] getIds() {
        return new int[]{897, 898, 899, 900};
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(ExperimentType type) {
        this.type = type;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public ExperimentType getType() {
        return type;
    }

    /**
     * Sets commenced.
     *
     * @param commenced the commenced
     */
    public void setCommenced(boolean commenced) {
        this.commenced = commenced;
    }

    /**
     * The enum Experiment type.
     */
    public enum ExperimentType {
        /**
         * First experiment type.
         */
        FIRST(897, ""),
        /**
         * The Second.
         */
        SECOND(898, "The shapeshifter's body begins to deform!", "The shapeshifter turns into a spider!"),
        /**
         * The Third.
         */
        THIRD(899, "The shapeshifter's body begins to twist!", "The shapeshifter turns into a bear!"),
        /**
         * The Fourth.
         */
        FOURTH(900, "The shapeshifter's body pulses!", "The shapeshifter turns into a wolf!"),
        /**
         * The End.
         */
        END(-1, "You finally kill the shapeshifter once and for all."),

        ;

        private final int id;
        private final String[] message;

        ExperimentType(int id, String... message) {
            this.id = id;
            this.message = message;
        }


        /**
         * Transform.
         *
         * @param npc    the npc
         * @param player the player
         */
        public void transform(final WitchsExperimentNPC npc, final Player player) {
            final ExperimentType newType = next();
            npc.lock();
            npc.getPulseManager().clear();
            npc.getWalkingQueue().reset();
            player.setAttribute("/save:witchs_house:experiment_id", this.id);
            GameWorld.getPulser().submit(new Pulse(1, npc, player) {
                int counter;

                @Override
                public boolean pulse() {
                    switch (++counter) {

                        case 1:
                            npc.unlock();
                            npc.getAnimator().reset();
                            npc.fullRestore();
                            npc.setType(newType);
                            npc.transform(newType.getId());
                            npc.getImpactHandler().setDisabledTicks(1);
                            if (newType != END) {
                                npc.getProperties().getCombatPulse().attack(player);
                            }
                            if (newType.getMessage() != null && newType != END) {
                                player.sendMessage(newType.getMessage()[0]);
                                player.sendMessage(newType.getMessage()[1]);
                            }
                            if (newType == END) {
                                player.setAttribute("witchs_house:experiment_killed", true);
                                npc.clear();
                                return false;
                            }
                            player.unlock();
                            return true;
                    }
                    return false;
                }

            });
        }

        /**
         * For id experiment type.
         *
         * @param id the id
         * @return the experiment type
         */
        public static ExperimentType forId(int id) {
            for (ExperimentType type : values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return null;
        }

        private static final ExperimentType[] experimentTypes = values();

        /**
         * Next experiment type.
         *
         * @return the experiment type
         */
        public ExperimentType next() {
            return experimentTypes[this.ordinal() + 1];
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
         * Get message string [ ].
         *
         * @return the string [ ]
         */
        public String[] getMessage() {
            return message;
        }

    }

}