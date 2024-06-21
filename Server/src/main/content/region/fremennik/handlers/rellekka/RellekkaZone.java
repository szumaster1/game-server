package content.region.fremennik.handlers.rellekka;

import content.global.skill.support.agility.AgilityHandler;
import content.region.fremennik.dialogue.rellekka.JarvaldDialogue;
import content.region.fremennik.dialogue.rellekka.MariaGunnarsDialogue;
import core.cache.def.impl.SceneryDefinition;
import core.game.interaction.Option;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.player.Player;
import core.game.world.map.Location;
import core.game.world.map.zone.MapZone;
import core.game.world.map.zone.ZoneBorders;
import core.game.world.map.zone.ZoneBuilder;
import core.game.world.update.flag.context.Animation;
import core.plugin.ClassScanner;
import core.plugin.Initializable;
import core.plugin.Plugin;

@Initializable
public final class RellekkaZone extends MapZone implements Plugin<Object> {

    public RellekkaZone() {
        super("rellekka", true);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ZoneBuilder.configure(this);
        ClassScanner.definePlugin(new JarvaldDialogue());
        ClassScanner.definePlugins(new RellekaOptionHandler(), new MariaGunnarsDialogue());
        ClassScanner.definePlugin(new OptionHandler() {

            @Override
            public Plugin<Object> newInstance(Object arg) throws Throwable {
                return this;
            }

            @Override
            public boolean handle(Player player, Node node, String option) {
                return true;
            }

        });
        return this;
    }

    @Override
    public boolean interact(Entity e, Node target, Option option) {
        if (e instanceof Player) {
            final Player player = (Player) e;
            switch (target.getId()) {
                case 4306:
                case 4310:
                case 4309:
                case 4304:
                case 4308:
                    player.sendMessage("Only Fremenniks may use this " + target.getName().toLowerCase() + ".");
                    return true;
                case 100:
                    player.getDialogueInterpreter().sendDialogue("You try to open the trapdoor but it won't budge! It looks like the", "trapdoor can only be opened from the other side.");
                    return true;
                case 2435:
                case 2436:
                case 2437:
                case 2438:
                    if (option.getName().equals("Travel")) {
                        player.getDialogueInterpreter().open(target.getId(), target, true);
                        return true;
                    }
                    break;
            }
        }
        return super.interact(e, target, option);
    }

    @Override
    public Object fireEvent(String identifier, Object... args) {
        return null;
    }

    @Override
    public void configure() {
        register(new ZoneBorders(2602, 3639, 2739, 3741));
    }

    /**
     * The Relleka option handler.
     */
    public static final class RellekaOptionHandler extends OptionHandler {

        @Override
        public Plugin<Object> newInstance(Object arg) throws Throwable {
            SceneryDefinition.forId(5847).getHandlers().put("option:climb-over", this);
            SceneryDefinition.forId(5008).getHandlers().put("option:enter", this);
            SceneryDefinition.forId(15116).getHandlers().put("option:climb-down", this);
            return this;
        }

        @Override
        public boolean handle(Player player, Node node, String option) {
            switch (option) {
                case "climb-over":
                    switch (node.getId()) {
                        case 5847:
                            AgilityHandler.forceWalk(player, -1, player.getLocation(), player.getLocation().transform(0, player.getLocation().getY() <= 3657 ? 3 : -3, 0), Animation.create(839), 20, 1, null, 0);
                            break;
                    }
                    break;
                case "enter":
                    switch (node.getId()) {
                        case 5008:
                            player.getProperties().setTeleportLocation(Location.create(2773, 10162, 0));
                            break;
                    }
                    break;
                case "climb-down":
                    switch (node.getId()) {
                        case 15116:
                            player.getProperties().setTeleportLocation(Location.create(2509, 10245, 0));
                            break;
                    }
                    break;
            }
            return true;
        }
    }
}
