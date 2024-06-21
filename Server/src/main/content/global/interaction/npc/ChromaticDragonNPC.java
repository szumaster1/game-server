package content.global.interaction.npc;

import content.global.interaction.item.equipment.special.DragonfireSwingHandler;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.combat.CombatSwingHandler;
import core.game.node.entity.combat.MultiSwingHandler;
import core.game.node.entity.combat.equipment.SwitchAttack;
import core.game.node.entity.impl.Animator.Priority;
import core.game.node.entity.npc.AbstractNPC;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;

/**
 * The Chromatic dragon npc.
 */
@Initializable
public final class ChromaticDragonNPC extends AbstractNPC {

    private static final SwitchAttack DRAGONFIRE = DragonfireSwingHandler.get(true, 52, new Animation(81, Priority.HIGH), new Graphic(1, 64), null, null);

    private final CombatSwingHandler combatAction = new MultiSwingHandler(false, new SwitchAttack(CombatStyle.MELEE.getSwingHandler(), new Animation(80, Priority.HIGH)), new SwitchAttack(CombatStyle.MELEE.getSwingHandler(), new Animation(91, Priority.HIGH)), DRAGONFIRE);

    /**
     * Instantiates a new Chromatic dragon npc.
     */
    public ChromaticDragonNPC() {
        super(941, null);
    }

    /**
     * Instantiates a new Chromatic dragon npc.
     *
     * @param id       the id
     * @param location the location
     */
    public ChromaticDragonNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public CombatSwingHandler getSwingHandler(boolean swing) {
        return combatAction;
    }

    @Override
    public int getDragonfireProtection(boolean fire) {
        return 0x2 | 0x4 | 0x8;
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new ChromaticDragonNPC(id, location);
    }

    @Override
    public int[] getIds() {
        return new int[]{941, 4677, 4678, 4679, 4680, // Green dragon
                55, 4681, 4682, 4683, 4684, // Blue dragon
                53, 4669, 4670, 4671, 4672, // Red dragon
                54, 4673, 4674, 4675, 4676, // Black dragon
        };
    }

}
