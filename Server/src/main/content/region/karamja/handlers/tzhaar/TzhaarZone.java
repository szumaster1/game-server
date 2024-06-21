package content.region.karamja.handlers.tzhaar;

import core.game.world.map.zone.MapZone;
import core.game.world.map.zone.ZoneBorders;
import core.game.world.map.zone.ZoneBuilder;
import core.game.world.map.zone.ZoneRestriction;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * The Tzhaar zone.
 */
@Initializable
public class TzhaarZone extends MapZone implements Plugin<Object> {

    /**
     * Instantiates a new Tzhaar zone.
     */
    public TzhaarZone() {
        super("Tzhaar zone", true, ZoneRestriction.CANNON);
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
        register(new ZoneBorders(2369, 5054, 2549, 5188));
    }

}
