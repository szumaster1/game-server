package content.global.skill.support.construction.decoration;

import content.global.skill.support.construction.BuildHotspot;
import content.global.skill.support.construction.Decoration;
import content.global.skill.support.construction.HousingStyle;
import core.cache.def.impl.SceneryDefinition;
import core.game.global.action.DoorActionHandler;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.scenery.Scenery;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * The Construction door plugin.
 */
@Initializable
public final class ConstructionDoorPlugin extends OptionHandler {
    private static int[][] REPLACEMENT = {
            {core.api.consts.Scenery.DOOR_13100, core.api.consts.Scenery.DOOR_13102},
            {core.api.consts.Scenery.DOOR_13101, core.api.consts.Scenery.DOOR_13103},
            {core.api.consts.Scenery.LARGE_DOOR_13094, core.api.consts.Scenery.LARGE_DOOR_13095},
            {core.api.consts.Scenery.LARGE_DOOR_13096, core.api.consts.Scenery.LARGE_DOOR_13097},
            {core.api.consts.Scenery.DOOR_13006, core.api.consts.Scenery.DOOR_13008},
            {core.api.consts.Scenery.DOOR_13007, core.api.consts.Scenery.DOOR_13008},
            {core.api.consts.Scenery.DOOR_13109, core.api.consts.Scenery.DOOR_13110},
            {core.api.consts.Scenery.DOOR_13107, core.api.consts.Scenery.DOOR_13108},
            {core.api.consts.Scenery.DOOR_13016, core.api.consts.Scenery.DOOR_13018},
            {core.api.consts.Scenery.DOOR_13015, core.api.consts.Scenery.DOOR_13017},
            {core.api.consts.Scenery.DOOR_13119, core.api.consts.Scenery.DOOR_13121},
            {core.api.consts.Scenery.DOOR_13118, core.api.consts.Scenery.DOOR_13120},
    };

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        for (HousingStyle style : HousingStyle.values()) {
            SceneryDefinition.forId(style.getDoorId()).getHandlers().put("option:open", this);
            SceneryDefinition.forId(style.getSecondDoorId()).getHandlers().put("option:open", this);
        }
        for (Decoration deco : BuildHotspot.DUNGEON_DOOR_LEFT.getDecorations()) {
            SceneryDefinition.forId(deco.getObjectId()).getHandlers().put("option:open", this);
            SceneryDefinition.forId(deco.getObjectId()).getHandlers().put("option:pick-lock", this);
            SceneryDefinition.forId(deco.getObjectId()).getHandlers().put("option:force", this);
        }
        for (Decoration deco : BuildHotspot.DUNGEON_DOOR_RIGHT.getDecorations()) {
            SceneryDefinition.forId(deco.getObjectId()).getHandlers().put("option:open", this);
            SceneryDefinition.forId(deco.getObjectId()).getHandlers().put("option:pick-lock", this);
            SceneryDefinition.forId(deco.getObjectId()).getHandlers().put("option:force", this);
        }
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        Scenery object = (Scenery) node;
        Scenery second = DoorActionHandler.getSecondDoor(object, player);
        switch (option) {
            case "pick-lock":
            case "force":
                return false;
        }
        DoorActionHandler.open(object, second, getReplaceId(object), getReplaceId(second), true, 500, false);
        return true;
    }

    private int getReplaceId(Scenery object) {
        for (int[] data : REPLACEMENT) {
            if (object.getId() == data[0]) {
                return data[1];
            }
        }
        return object.getId() + 6;
    }

}