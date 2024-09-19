package content.global.skill.support.slayer.npc;

import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.ImpactHandler.HitsplatType;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.map.Location;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * Represents the Desert lizard plugin.
 */
@Initializable
public final class DesertLizardPlugin implements Plugin<Object> {

    private static final int[] IDS = new int[]{2803, 2804, 2805, 2806, 2807, 2808};

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        new IcecoolerPlugin().newInstance(arg);
        new DezertLizardNPC().newInstance(arg);
        return this;
    }

    @Override
    public Object fireEvent(String identifier, Object... args) {
        return null;
    }

    /**
     * Icecooler plugin.
     */
    public static final class IcecoolerPlugin extends UseWithHandler {

        private static final Item ICE_COOLER = new Item(6696);

        /**
         * Instantiates a new Icecooler plugin.
         */
        public IcecoolerPlugin() {
            super(ICE_COOLER.getId());
        }

        @Override
        public Plugin<Object> newInstance(Object arg) throws Throwable {
            for (int id : IDS) {
                addHandler(id, NPC_TYPE, this);
            }
            return this;
        }

        @Override
        public boolean handle(NodeUsageEvent event) {
            final Player player = event.getPlayer();
            if (player.getSkills().getLevel(Skills.SLAYER) < 22) {
                player.getPacketDispatch().sendMessage("You need a Slayer level of at least 22 to do this.");
                return true;
            }
            final NPC npc = (NPC) event.getUsedWith();
            if (npc.getSkills().getLifepoints() > 2) {
                player.getPacketDispatch().sendMessage("The lizard isn't weak enough to be affected by the icy water.");
                return true;
            }
            if (player.getInventory().remove(ICE_COOLER)) {
                npc.getImpactHandler().manualHit(player, npc.getSkills().getLifepoints(), HitsplatType.NORMAL);
                player.getPacketDispatch().sendMessage("The lizard shudders and collapses from the freezing water.");
            }
            return true;
        }

    }

    /**
     * Dezert lizard npc.
     */
    public final class DezertLizardNPC extends AbstractNPC {

        /**
         * Instantiates a new Dezert lizard npc.
         *
         * @param id       the id
         * @param location the location
         */
        public DezertLizardNPC(int id, Location location) {
            super(id, location);
        }

        /**
         * Instantiates a new Dezert lizard npc.
         */
        public DezertLizardNPC() {
            super(0, null);
        }

        @Override
        public AbstractNPC construct(int id, Location location, Object... objects) {
            return new DezertLizardNPC(id, location);
        }

        @Override
        public void checkImpact(BattleState state) {
            super.checkImpact(state);
            int lifepoints = getSkills().getLifepoints();
            if (state.getEstimatedHit() > -1) {
                lifepoints -= state.getEstimatedHit();
                if (lifepoints < 1) {
                    state.setEstimatedHit(lifepoints - 1);
                }
                if (state.getEstimatedHit() < 0) {
                    state.setEstimatedHit(0);
                    getSkills().setLifepoints(2);
                }
            }
            if (state.getSecondaryHit() > -1) {
                lifepoints -= state.getSecondaryHit();
                if (lifepoints < 1) {
                    state.setSecondaryHit(lifepoints - 1);
                }
                if (state.getSecondaryHit() < 0) {
                    state.setSecondaryHit(0);
                }
            }
        }

        @Override
        public int[] getIds() {
            return IDS;
        }

    }
}
