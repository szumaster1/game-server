package content.region.fremennik.handlers.rellekka;

import content.region.fremennik.dialogue.rellekka.JarvaldDialogue;
import core.game.interaction.Option;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.player.Player;
import core.game.world.map.zone.MapZone;
import core.game.world.map.zone.ZoneBorders;
import core.game.world.map.zone.ZoneBuilder;
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

}
