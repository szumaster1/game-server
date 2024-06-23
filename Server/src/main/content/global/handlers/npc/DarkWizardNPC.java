package content.global.handlers.npc;

import core.game.node.entity.Entity;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.combat.spell.CombatSpell;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.link.SpellBookManager.SpellBook;
import core.game.world.map.Location;
import core.plugin.Initializable;
import core.utilities.RandomFunction;

/**
 * The Dark wizard npc.
 */
@Initializable
public final class DarkWizardNPC extends AbstractNPC {

    private static final int[] ID = {172, 174};

    private static final int[][] SPELLS = new int[][]{{6, 7}, {4, 2}};

    /**
     * Instantiates a new Dark wizard npc.
     */
    public DarkWizardNPC() {
        super(0, null);
        setAggressive(true);
    }

    private DarkWizardNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new DarkWizardNPC(id, location);
    }

    @Override
    public void init() {
        super.init();
        getProperties().getCombatPulse().setStyle(CombatStyle.MAGIC);
        setDefault();
    }

    @Override
    public void onImpact(final Entity entity, BattleState state) {
        super.onImpact(entity, state);
        if (getAttribute("switched", false)) {
            removeAttribute("switched");
            setDefault();
            return;
        }
        if (RandomFunction.random(6) > 4) {
            setSpells(getSpells());
            setAttribute("switched", true);
        }
    }

    private void setDefault() {
        getProperties().setAutocastSpell((CombatSpell) SpellBook.MODERN.getSpell(getId() == 172 ? 6 : 4));
    }

    private void setSpells(int[] ids) {
        getProperties().setAutocastSpell((CombatSpell) SpellBook.MODERN.getSpell(ids[RandomFunction.random(ids.length)]));
    }

    private int[] getSpells() {
        int index = 0;
        for (int i = 0; i < ID.length; i++) {
            if (ID[i] == getId()) {
                index = i;
                break;
            }
        }
        return SPELLS[index];
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
