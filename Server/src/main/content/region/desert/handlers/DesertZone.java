package content.region.desert.handlers;

import core.cache.def.impl.ItemDefinition;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.ImpactHandler.HitsplatType;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.zone.MapZone;
import core.game.world.map.zone.ZoneBorders;
import core.game.world.map.zone.ZoneBuilder;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.utilities.RandomFunction;

import java.util.ArrayList;
import java.util.List;

@Initializable
public final class DesertZone extends MapZone implements Plugin<Object> {

    private static final Item[] WATER_SKINS = new Item[]{new Item(1823), new Item(1825), new Item(1827), new Item(1829)};

    private static final int[][] VESSILS = new int[][]{{1937, 1935}, {1929, 1925}, {1921, 1923}, {227, 229}};

    private static final Animation ANIMATION = new Animation(829);

    private static final List<Player> PLAYERS = new ArrayList<>(20);

    private static final Pulse pulse = new Pulse(3) {
        @Override
        public boolean pulse() {
            for (Player player : PLAYERS) {
                if (!player.getAttribute("tutorial:complete", false) || player.getInterfaceManager().isOpened() || player.getInterfaceManager().hasChatbox() || player.getLocks().isMovementLocked()) {
                    continue;
                }
                if (player.getAttribute("desert-delay", -1) < GameWorld.getTicks()) {
                    effect(player);
                }
            }
            return PLAYERS.isEmpty();
        }
    };

    private static void effect(Player p) {
        p.setAttribute("desert-delay", GameWorld.getTicks() + getDelay(p));
        evaporate(p);
        if (drink(p)) {
            return;
        }
        p.getImpactHandler().manualHit(p, RandomFunction.random(1, p.getLocation().getY() < 2990 ? 12 : 8), HitsplatType.NORMAL);
        p.getPacketDispatch().sendMessage("You start dying of thirst while you're in the desert.");
    }


    /**
     * Evaporate.
     *
     * @param p the p
     */
    public static void evaporate(Player p) {
        for (int i = 0; i < VESSILS.length; i++) {
            if (p.getInventory().contains(VESSILS[i][0], 1)) {
                if (p.getInventory().remove(new Item(VESSILS[i][0]))) {
                    p.getInventory().add(new Item(VESSILS[i][1]));
                    p.getPacketDispatch().sendMessage("The water in your " + ItemDefinition.forId(VESSILS[i][0]).getName().toLowerCase().replace("of water", "").trim() + " evaporates in the desert heat.");
                }
            }
        }
    }


    /**
     * Drink boolean.
     *
     * @param p the p
     * @return the boolean
     */
    public static boolean drink(Player p) {
        for (Item i : WATER_SKINS) {
            if (p.getInventory().containsItem(i) && p.getInventory().remove(i)) {
                p.getInventory().add(new Item(i.getId() + 2));
                p.animate(ANIMATION);
                p.getPacketDispatch().sendMessage("You take a drink of water.");
                return true;
            }
        }
        if (p.getInventory().contains(1831, 1)) {
            p.getPacketDispatch().sendMessage("Perhaps you should fill up one of your empty waterskins.");
        } else {
            p.getPacketDispatch().sendMessage("You should get a waterskin for any travelling in the desert.");
        }
        return false;
    }

    private static int getDelay(Player player) {
        int delay = 116;
        if (player.getEquipment().contains(1833, 1)) {
            delay += 17;
        }
        if (player.getEquipment().contains(1835, 1)) {
            delay += 17;
        }
        if (player.getEquipment().contains(1837, 1)) {
            delay += 17;
        }
        return delay;
    }


    /**
     * Instantiates a new Desert zone.
     */
    public DesertZone() {
        super("Desert Zone", true);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ZoneBuilder.configure(this);
        return this;
    }

    @Override
    public Object fireEvent(String identifier, Object... args) {
        return null;
    }

    @Override
    public void configure() {
        ZoneBorders borders = new ZoneBorders(3063, 2725, 3544, 3115);
        borders.addException(new ZoneBorders(3152, 2961, 3191, 2999));// Bandit
        // camp
        borders.addException(new ZoneBorders(3398, 2914, 3450, 2941));//Nardah
        borders.addException(new ZoneBorders(3410, 2883, 3450, 2941));//Nardah2
        borders.addException(new ZoneBorders(3147, 3019, 3185, 3059));// Bedabin
        borders.addException(new ZoneBorders(3217, 2881, 3248, 2914));// pyramid
        borders.addException(new ZoneBorders(3264, 2752, 3323, 2810));// Sophanem
        borders.addException(new ZoneBorders(3326, 2942, 3371, 3006)); //Pollnivneach
        borders.addException(new ZoneBorders(3007, 4672, 3071, 4735));// agility-pyramid
        // top(region12105)
        borders.addException(new ZoneBorders(3274, 3014, 3305, 3041));// Mining
        // camp
        borders.addException(new ZoneBorders(3260, 9408, 3331, 9472));// Mining
        // camp
        register(borders);
        pulse.stop();
    }

    @Override
    public boolean enter(Entity e) {
        if (e instanceof Player) {
            Player p = (Player) e;
            if (p.getAttribute("tutorial:stage", 0) > 71) {
                return true;
            }
            p.setAttribute("desert-delay", GameWorld.getTicks() + getDelay(p));
            PLAYERS.add(p);
            if (!pulse.isRunning()) {
                pulse.restart();
                pulse.start();
                GameWorld.getPulser().submit(pulse);
            }
        }
        return true;
    }

    @Override
    public boolean leave(Entity e, boolean logout) {
        if (e instanceof Player) {
            PLAYERS.remove(e);
            e.removeAttribute("desert-delay");
        }
        return super.leave(e, logout);
    }

}
