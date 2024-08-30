package content.region.morytania.handlers;

import content.global.skill.support.firemaking.data.Log;
import cfg.consts.NPCs;
import core.cache.def.impl.SceneryDefinition;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.npc.drop.DropFrequency;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import content.data.skill.SkillingTool;
import core.game.node.item.ChanceItem;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.node.scenery.SceneryBuilder;
import core.game.system.task.Pulse;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.tools.RandomFunction;
import cfg.consts.Items;
import core.game.world.GameWorld;
import core.game.world.repository.Repository;
import core.plugin.ClassScanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Pyre site plugin.
 */
@Initializable
public final class PyreSitePlugin extends OptionHandler {

    private static final ChanceItem[] REWARDS = new ChanceItem[]{new ChanceItem(560, 8, 15, DropFrequency.COMMON), new ChanceItem(565, 4, 7, DropFrequency.COMMON), new ChanceItem(1601, 2, 2, DropFrequency.UNCOMMON), new ChanceItem(532, 10, 10, DropFrequency.RARE), new ChanceItem(100, 2, 2, DropFrequency.COMMON), new ChanceItem(9145, 5, 5, DropFrequency.COMMON), new ChanceItem(9144, 10, 10, DropFrequency.UNCOMMON), new ChanceItem(892, 10, 10, DropFrequency.COMMON), new ChanceItem(867, 20, 20, DropFrequency.UNCOMMON), new ChanceItem(816, 20, 20, DropFrequency.COMMON), new ChanceItem(9419, 1, 1, DropFrequency.COMMON)};
    private static final Item CHEWED_BONES = new Item(Items.CHEWED_BONES_11338);
    private static final Item MANGLED_BONES = new Item(Items.MANGLED_BONES_11337);
    private static final Item DFH = new Item(Items.DRAGON_FULL_HELM_11335);
    private static final List<Location> USED_LOCATIONS = new ArrayList<>(20);

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        SceneryDefinition.forId(25286).getHandlers().put("option:construct", this);
        ClassScanner.definePlugin(new FerociousBarbarianNPC());
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        for (Location l : USED_LOCATIONS) {
            if (l.withinDistance(node.getLocation(), 3)) {
                player.sendMessage("This pyre site is in use currently.");
                return true;
            }
        }
        if (!player.getInventory().containsItem(CHEWED_BONES) && !player.getInventory().containsItem(MANGLED_BONES)) {
            player.sendMessage("You need chewed bones or mangled bones in order to do this.");
            return true;
        }
        if (!player.getInventory().contains(590, 1)) {
            player.sendMessage("You need a tinderbox in order to do this.");
            return true;
        }
        final SkillingTool tool = SkillingTool.getHatchet(player);
        if (tool == null) {
            player.sendMessage("You need an axe in order to do this.");
            return true;
        }
        final LogType type = LogType.getType(player);
        if (type == null) {
            player.sendMessage("You don't have any logs.");
            return true;
        }
        if (player.getAttribute("barb", null) != null && ((NPC) player.getAttribute("barb")).isActive()) {
            player.sendMessage("You must defeat the barbarian spirit first.");
            return true;
        }
        player.setAttribute("logType", type);
        ritual(player, node.asScenery());
        return true;
    }

    private void ritual(Player player, Scenery object) {
        player.lock();
        USED_LOCATIONS.add(object.getLocation());
        player.faceLocation(object.getLocation());
        GameWorld.getPulser().submit(getPulse(player, object));
    }

    private Pulse getPulse(final Player player, final Scenery object) {
        final LogType logType = player.getAttribute("logType", LogType.NORMAL);
        final SkillingTool tool = SkillingTool.getHatchet(player);
        final Item bones = player.getInventory().containsItem(CHEWED_BONES) ? player.getInventory().getItem(CHEWED_BONES) : player.getInventory().getItem(MANGLED_BONES);
        return new Pulse(1, player) {
            int count;
            int objectId = 25288;

            @Override
            public boolean pulse() {
                if ((count % 6 == 0 || count == 0) && count <= 10 && objectId <= 25291) {
                    player.animate(getAnimation(tool));
                    player.faceLocation(object.getLocation());
                }
                if (count % 4 == 0) {
                    if (objectId == 25291) {
                        if (player.getInventory().remove(new Item(logType.getLog().getLogId()), bones)) {
                            player.getAnimator().reset();
                            player.getSkills().addExperience(Skills.CRAFTING, logType.getExperiences()[0]);
                            player.getSkills().addExperience(Skills.FIREMAKING, logType.getExperiences()[1]);
                            if (bones.getId() == CHEWED_BONES.getId()) {
                                player.getInventory().add(getRandomItem(player), player);
                                player.sendMessages("The ancient barbarian is laid to rest. Your future prayer training is blessed,", "as his spirit ascends to a glorious afterlife. Spirits drop an object into your", "pack.");
                            } else {
                                final NPC barb = NPC.create(752, object.getLocation().transform(object.getDirection(), 1));
                                ((FerociousBarbarianNPC) barb).target = player;
                                barb.init();
                                barb.moveStep();
                                player.setAttribute("barb", barb);
                                player.unlock();
                            }
                        }
                    }
                    if (objectId == 25295) {
                        return true;
                    }
                    replace(objectId++, object, player);
                }
                count++;
                return false;
            }

            @Override
            public void stop() {
                super.stop();
                player.unlock();
                replace(25286, object, player);
                USED_LOCATIONS.remove(object.getLocation());
            }
        };
    }

    /**
     * Replaces the pyre site with a new object.
     */
    private void replace(int newId, Scenery ship, Player player) {
        Scenery newShip = new Scenery(newId, getLocation(newId, ship), 10, ship.getLocation().getX() == 2503 ? 4 : 1);
        SceneryBuilder.add(newShip);
    }

    /**
     * Gets the location of where the object should be.
     */
    private Location getLocation(int newId, Scenery ship) {
        Location location = ship.getLocation().transform(ship.getDirection(), -2);
        if (ship.getLocation().getX() == 2507 || ship.getLocation().getX() == 2519) {
            return location.transform(-1, 0, 0);
        } else if (ship.getLocation().getX() == 2503) {
            return location.transform(-2, -1, 0);
        }
        return location;
    }

    /*
     * Gets a random item.
     */
    private Item getRandomItem(Player player) {
        if (RandomFunction.random(250) == 10) {
            Repository.sendNews(player.getUsername() + " has just received a Dragon Full Helm from the Pyre Ship.");
            return DFH;
        }
        return RandomFunction.getChanceItem(REWARDS);
    }

    @Override
    public Location getDestination(Node node, Node n) {
        if (n instanceof Scenery) {
            return n.getLocation().transform(n.getDirection(), 1);
        }
        return null;
    }

    /**
     * Gets the animation for the skilling tool.
     *
     * @param tool the tool
     * @return the animation
     */
    public Animation getAnimation(final SkillingTool tool) {
        Animation animation = null;
        switch (tool) {
            case BRONZE_AXE:
                animation = Animation.create(3291);
                break;
            case IRON_AXE:
                animation = Animation.create(3290);
                break;
            case STEEL_AXE:
                animation = Animation.create(3289);
                break;
            case BLACK_AXE:
                animation = Animation.create(3288);
                break;
            case MITHRIL_AXE:
                animation = Animation.create(3287);
                break;
            case ADAMANT_AXE:
                animation = Animation.create(3286);
                break;
            case RUNE_AXE:
                animation = Animation.create(3285);
                break;
            case DRAGON_AXE:
                animation = Animation.create(3292);
                break;
            default:
                break;
        }
        return animation;
    }

    /**
     * The enum Log type.
     */
    public enum LogType {
        /**
         * The Normal.
         */
        NORMAL(Log.NORMAL, 11, new double[]{10, 40}, 1),
        /**
         * The Achey.
         */
        ACHEY(Log.ACHEY, 11, new double[]{10, 40}, 1),
        /**
         * The Oak.
         */
        OAK(Log.OAK, 25, new double[]{15, 60}, 2),
        /**
         * The Willow.
         */
        WILLOW(Log.WILLOW, 40, new double[]{22.5, 90}, 2),
        /**
         * The Teak.
         */
        TEAK(Log.TEAK, 45, new double[]{26.2, 105}, 3),
        /**
         * The Arctic pine.
         */
        ARCTIC_PINE(Log.ARCTIC_PINE, 52, new double[]{31.2, 125}, 3),
        /**
         * The Maple.
         */
        MAPLE(Log.MAPLE, 55, new double[]{33.7, 135}, 3),
        /**
         * The Mahogany.
         */
        MAHOGANY(Log.MAHOGANY, 60, new double[]{39.3, 157.5}, 3),
        /**
         * The Yew.
         */
        YEW(Log.YEW, 70, new double[]{50.6, 202.5}, 4),
        /**
         * The Magic.
         */
        MAGIC(Log.MAGIC, 85, new double[]{75.9, 303.8}, 5);

        private final Log log;
        private final int level;
        private final double[] experiences;
        private final int enhancedExp;

        LogType(Log log, int level, double[] experiences, int enhancedExp) {
            this.log = log;
            this.level = level;
            this.experiences = experiences;
            this.enhancedExp = enhancedExp;
        }

        /**
         * Gets type.
         *
         * @param player the player
         * @return the type
         */
        public static LogType getType(Player player) {
            for (LogType type : values()) {
                if (player.getInventory().contains(type.getLog().getLogId(), 1)) {
                    return type;
                }
            }
            return null;
        }

        /**
         * Gets log.
         *
         * @return the log
         */
        public Log getLog() {
            return log;
        }

        /**
         * Gets level.
         *
         * @return the level
         */
        public int getLevel() {
            return level;
        }

        /**
         * Get experiences double.
         *
         * @return the double.
         */
        public double[] getExperiences() {
            return experiences;
        }

        /**
         * Gets enhanced exp.
         *
         * @return the enhanced exp
         */
        public int getEnhancedExp() {
            return enhancedExp;
        }

    }

    /**
     * Ferocious barbarian npc.
     */
    public class FerociousBarbarianNPC extends AbstractNPC {
        private Player target;

        /**
         * Instantiates a new Ferocious barbarian npc.
         *
         * @param id       the id
         * @param location the location
         */
        public FerociousBarbarianNPC(int id, Location location) {
            super(id, location);
            this.setRespawn(false);
            this.setAggressive(true);
        }

        /**
         * Instantiates a new Ferocious barbarian npc.
         */
        public FerociousBarbarianNPC() {
            this(-1, null);
        }

        @Override
        public void handleTickActions() {
            if (target == null) {
                return;
            }
            if (!target.isActive() || !target.getLocation().withinDistance(getLocation())) {
                clear();
            }
            if (!getProperties().getCombatPulse().isAttacking()) {
                getProperties().getCombatPulse().attack(target);
            }
        }

        @Override
        public boolean isAttackable(Entity entity, CombatStyle style, boolean message) {
            if (entity instanceof Player && entity != target) {
                if (message) {
                    ((Player) entity).getPacketDispatch().sendMessage("It's not after you.");
                }
                return false;
            }
            return super.isAttackable(entity, style, message);
        }

        @Override
        public void finalizeDeath(Entity killer) {
            super.finalizeDeath(killer);
            if (killer instanceof Player && killer == target) {
                target.removeAttribute("barb");
            }
        }

        @Override
        public AbstractNPC construct(int id, Location location, Object... objects) {
            return new FerociousBarbarianNPC(id, location);
        }

        @Override
        public int[] getIds() {
            return new int[]{NPCs.FEROCIOUS_BARBARIAN_SPIRIT_752};
        }
    }
}
