package content.global.skill.magic.modern;

import core.game.node.entity.Entity;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.spell.CombatSpell;
import core.game.node.entity.combat.spell.SpellType;
import core.game.node.entity.impl.Projectile;
import core.game.node.entity.player.link.SpellBookManager.SpellBook;
import core.game.node.item.Item;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * Sonic wave spell.
 */
@Initializable
public final class SonicWaveSpell extends CombatSpell {

    private static final Projectile STRIKE_PROJECTILE = Projectile.create((Entity) null, null, 337, 8, 8, 52, 100, 15, 1);

    /**
     * Instantiates a new Sonic wave spell.
     */
    public SonicWaveSpell() {

    }

    private SonicWaveSpell(SpellType type, int level, int sound, Graphic start, Projectile projectile, Graphic end, Item... runes) {
        super(type, SpellBook.MODERN, level, 0.0, sound, sound + 1, null, start, projectile, end, runes);
    }

    @Override
    public int getMaximumImpact(Entity entity, Entity victim, BattleState state) {
        return getType().getImpactAmount(entity, victim, 5);
    }

    @Override
    public Plugin<SpellType> newInstance(SpellType type) throws Throwable {
        SpellBook.MODERN.register(63232, new SonicWaveSpell(SpellType.STRIKE, 1, -1, new Graphic(337), STRIKE_PROJECTILE, null));
        return this;
    }

}
