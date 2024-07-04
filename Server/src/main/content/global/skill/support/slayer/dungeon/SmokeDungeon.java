package content.global.skill.support.slayer.dungeon;

import content.global.skill.support.slayer.SlayerEquipmentFlags;
import core.cache.def.impl.SceneryDefinition;
import core.game.component.Component;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.ImpactHandler.HitsplatType;
import core.game.node.entity.player.Player;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.game.world.map.zone.MapZone;
import core.game.world.map.zone.ZoneBorders;
import core.game.world.map.zone.ZoneBuilder;
import core.plugin.ClassScanner;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.tools.RandomFunction;

import java.util.ArrayList;
import java.util.List;

import static core.api.ContentAPIKt.setAttribute;

/**
 * The Smoke dungeon.
 */
@Initializable
public final class SmokeDungeon extends MapZone implements Plugin<Object> {

    private static final String[] CHATS = new String[]{"*choke*", "*cough*"};

    private static final List<Player> PLAYERS = new ArrayList<>(20);

    private static final int DELAY = 20;

    private static final Pulse pulse = new Pulse(3) {
        @Override
        public boolean pulse() {
            for (Player player : PLAYERS) {
                if (player.getInterfaceManager().isOpened() || player.getInterfaceManager().hasChatbox() || player.getLocks().isMovementLocked()) {
                    continue;
                }
                if (SmokeDungeon.getDelay(player) < GameWorld.getTicks() && !isProtected(player)) {
                    effect(player);
                }
            }
            return PLAYERS.isEmpty();
        }
    };

    /**
     * Instantiates a new Smoke dungeon.
     */
    public SmokeDungeon() {
        super("zmoke dungeon", true);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ClassScanner.definePlugin(new OptionHandler() {

            @Override
            public Plugin<Object> newInstance(Object arg) throws Throwable {
                SceneryDefinition.forId(36002).getHandlers().put("option:climb-down", this);
                SceneryDefinition.forId(6439).getHandlers().put("option:climb-up", this);
                return this;
            }
            @Override
            public boolean handle(Player player, Node node, String option) {
                switch (node.getId()) {
                    case 36002:
                        player.getProperties().setTeleportLocation(Location.create(3206, 9379, 0));
                        player.getPacketDispatch().sendMessage("You climb down the well.");
                        break;
                    case 6439:
                        player.getProperties().setTeleportLocation(Location.create(3310, 2963, 0));
                        player.getPacketDispatch().sendMessage("You climb up the rope.");
                        break;
                }
                return true;
            }

        });
        ZoneBuilder.configure(this);
        return this;
    }

    @Override
    public boolean enter(Entity e) {
        if (e instanceof Player) {
            Player p = (Player) e;
            setDelay(p);
            PLAYERS.add(p);
            if (!pulse.isRunning()) {
                pulse.restart();
                pulse.start();
                GameWorld.getPulser().submit(pulse);
            }
            p.getInterfaceManager().openOverlay(new Component(118));
        }
        return true;
    }

    @Override
    public boolean leave(Entity e, boolean logout) {
        if (e instanceof Player) {
            Player p = (Player) e;
            p.getInterfaceManager().closeOverlay();
            PLAYERS.remove(e);
            e.removeAttribute("smoke-delay");
        }
        return super.leave(e, logout);
    }

    private static void effect(Player player) {
        int hit = 2;
        setDelay(player);
        if (RandomFunction.random(2) == 1) {
            player.sendChat(CHATS[RandomFunction.random(CHATS.length)]);
            player.getPacketDispatch().sendMessage("The stagnant, smoky air chokes you.");
        }
        player.getImpactHandler().manualHit(player, hit, HitsplatType.NORMAL);
    }

    private static void setDelay(Player player) {
        setAttribute(player, "smoke-delay", GameWorld.getTicks() + DELAY);
    }
    private static int getDelay(Player player) {
        return player.getAttribute("smoke-delay", 0);
    }
    private static boolean isProtected(Player player) {
        return SlayerEquipmentFlags.hasFaceMask(player);
    }

    @Override
    public Object fireEvent(String identifier, Object... args) {
        return this;
    }

    @Override
    public void configure() {
        register(new ZoneBorders(3196, 9337, 3344, 9407));
        pulse.stop();
    }

}
