package content.global.skill.support.agility.shortcuts;

import content.global.skill.support.agility.AgilityHandler;
import content.global.skill.support.agility.AgilityShortcut;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.scenery.Scenery;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * Log balance shortcut.
 */
@Initializable
public class LogBalanceShortcut extends AgilityShortcut {

    private Location start;
    private Location end;

    /**
     * Instantiates a new Log balance shortcut.
     *
     * @param ids        the ids
     * @param level      the level
     * @param experience the experience
     * @param start      the start
     * @param end        the end
     * @param options    the options
     */
    public LogBalanceShortcut(int[] ids, int level, double experience, Location start, Location end, String... options) {
        super(ids, level, experience, options);
        this.start = start;
        this.end = end;
    }

    /**
     * Instantiates a new Log balance shortcut.
     */
    public LogBalanceShortcut() {
        super(new int[]{}, 0, 0.0);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) {
        configure(new LogBalanceShortcut(new int[]{2296}, 20, 5, new Location(2598, 3477, 0), Location.create(2603, 3477, 0), "walk-across"));
        configure(new LogBalanceShortcut(new int[]{2332}, 1, 1, Location.create(2910, 3049, 0), Location.create(2906, 3049, 0), "cross"));
        configure(new LogBalanceShortcut(new int[]{3933}, 45, 1, Location.create(2290, 3232, 0), Location.create(2290, 3239, 0), "cross"));
        configure(new LogBalanceShortcut(new int[]{3932}, 45, 1, Location.create(2258, 3250, 0), Location.create(2264, 3250, 0), "cross"));
        configure(new LogBalanceShortcut(new int[]{3931}, 45, 1, Location.create(2202, 3237, 0), Location.create(2196, 3237, 0), "cross"));
        return this;
    }

    @Override
    public void run(Player player, Scenery object, String option, boolean failed) {
        Location destination = start;
        if (player.getLocation().getDistance(start) < player.getLocation().getDistance(end)) {
            destination = end;
        }
        AgilityHandler.walk(player, -1, player.getLocation(), destination, Animation.create(155), getExperience(), null);
    }

    @Override
    public Location getDestination(Node node, Node n) {
        if (node.getLocation().getDistance(start) < node.getLocation().getDistance(end)) {
            return start;
        }
        return end;
    }

}
