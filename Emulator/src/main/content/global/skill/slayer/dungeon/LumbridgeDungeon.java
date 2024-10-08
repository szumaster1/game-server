package content.global.skill.slayer.dungeon;

import content.global.skill.slayer.SlayerEquipmentFlags;
import core.cache.def.impl.NPCDefinition;
import core.game.interaction.Option;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.Direction;
import core.game.world.map.Location;
import core.game.world.map.zone.MapZone;
import core.game.world.map.zone.ZoneBorders;
import core.game.world.map.zone.ZoneBuilder;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.plugin.PluginManager;
import core.tools.RandomFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * Lumbridge dungeon.
 */
@Initializable
public final class LumbridgeDungeon extends MapZone implements Plugin<Object> {

    private static final Map<Location, WallBeastNPC> BEASTS = new HashMap<>();

    /**
     * Instantiates a new Lumbridge dungeon.
     */
    public LumbridgeDungeon() {
        super("lumbridge swamp dungeon", true);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ZoneBuilder.configure(this);
        PluginManager.definePlugin(new WallBeastNPC());
        return this;
    }
    @Override
    public void locationUpdate(Entity entity, Location last) {
        if (entity instanceof Player) {
            final Player player = (Player) entity;
            final WallBeastNPC npc = BEASTS.get(last);
            if (npc != null && npc.canAttack(player)) {
                npc.trigger(player);
            }
        }
    }

    private boolean hasHelmet(final Player player) {
        return SlayerEquipmentFlags.hasSpinyHelmet(player);
    }

    @Override
    public boolean interact(Entity e, Node target, Option option) {
        return super.interact(e, target, option);
    }

    @Override
    public Object fireEvent(String identifier, Object... args) {
        return null;
    }

    @Override
    public void configure() {
        register(new ZoneBorders(3137, 9534, 3295, 9602));
    }

    /**
     * Wall beast npc.
     */
    public final class WallBeastNPC extends AbstractNPC {

        /**
         * Instantiates a new Wall beast npc.
         *
         * @param id       the id
         * @param location the location
         */
        public WallBeastNPC(int id, Location location) {
            super(id, location, false);
        }

        /**
         * Instantiates a new Wall beast npc.
         */
        public WallBeastNPC() {
            super(7823, null, false);
        }

        @Override
        public AbstractNPC construct(int id, Location location, Object... objects) {
            return new WallBeastNPC(id, location);
        }

        @Override
        public void init() {
            super.init();
            getLocks().lockMovement(Integer.MAX_VALUE);
            BEASTS.put(getLocation().transform(Direction.SOUTH, 1), this);
        }

        @Override
        public void handleTickActions() {
            super.handleTickActions();
        }

        /**
         * Trigger.
         *
         * @param player the player
         */
        public void trigger(final Player player) {
            boolean isProtected = hasHelmet(player);
            player.face(this);
            if (!isProtected) {
                animate(NPCDefinition.forId(7823).getCombatAnimation(3));
                player.animate(Animation.create(1810));
                GameWorld.getPulser().submit(new Pulse(8, player) {
                    @Override
                    public boolean pulse() {
                        getAnimator().reset();
                        player.getAnimator().reset();
                        player.getImpactHandler().handleImpact(WallBeastNPC.this, RandomFunction.random(1, 18), CombatStyle.MELEE);
                        return true;
                    }
                });
            } else {
                transform(7823);
                attack(player);
            }
        }

        @Override
        public void finalizeDeath(Entity killer) {
            //TODO
        }

        @Override
        public boolean isPoisonImmune() {
            return true;
        }

        @Override
        public int[] getIds() {
            //return Tasks.WALL_BEASTS.getTask().getNpcs();
            int[] empty = {};
            return empty;
        }

    }

}
