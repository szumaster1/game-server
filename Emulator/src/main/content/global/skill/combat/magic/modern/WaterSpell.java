package content.global.skill.combat.magic.modern;

import org.rs.consts.Sounds;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.spell.CombatSpell;
import core.game.node.entity.combat.spell.Runes;
import core.game.node.entity.combat.spell.SpellType;
import core.game.node.entity.impl.Animator.Priority;
import core.game.node.entity.impl.Projectile;
import core.game.node.entity.player.link.SpellBookManager.SpellBook;
import core.game.node.item.Item;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * Water spell.
 */
@Initializable
public final class WaterSpell extends CombatSpell {

    private static final Graphic STRIKE_START = new Graphic(93, 96);

    private static final Projectile STRIKE_PROJECTILE = Projectile.create((Entity) null, null, 94, 40, 36, 52, 75, 15, 11);

    private static final Graphic STRIKE_END = new Graphic(95, 96);

    private static final Graphic BOLT_START = new Graphic(120, 96);

    private static final Projectile BOLT_PROJECTILE = Projectile.create((Entity) null, null, 121, 40, 36, 52, 75, 15, 11);

    private static final Graphic BOLT_END = new Graphic(122, 96);

    private static final Graphic BLAST_START = new Graphic(135, 96); // 129

    private static final Projectile BLAST_PROJECTILE = Projectile.create((Entity) null, null, 136, 40, 36, 52, 75, 15, 11); // 130

    private static final Graphic BLAST_END = new Graphic(137, 96); // 131

    private static final Graphic WAVE_START = new Graphic(161, 96);

    private static final Projectile WAVE_PROJECTILE = Projectile.create((Entity) null, null, 162, 40, 36, 52, 75, 15, 11);

    private static final Graphic WAVE_END = new Graphic(163, 96);

    private static final Animation ANIMATION = new Animation(711, Priority.HIGH);

    /**
     * Instantiates a new Water spell.
     */
    public WaterSpell() {

    }

    private WaterSpell(SpellType type, int level, double baseExperience, int sound, Graphic start, Projectile projectile, Graphic end, Item... runes) {
        super(type, SpellBook.MODERN, level, baseExperience, sound, sound + 1, ANIMATION, start, projectile, end, runes);
    }

    @Override
    public int getMaximumImpact(Entity entity, Entity victim, BattleState state) {
        return getType().getImpactAmount(entity, victim, 2);
    }

    @Override
    public Plugin<SpellType> newInstance(SpellType type) throws Throwable {
        SpellBook.MODERN.register(4, new WaterSpell(SpellType.STRIKE, 5, 7.5, Sounds.WATERSTRIKE_CAST_AND_FIRE_211, STRIKE_START, STRIKE_PROJECTILE, STRIKE_END, Runes.MIND_RUNE.getItem(1), Runes.WATER_RUNE.getItem(1), Runes.AIR_RUNE.getItem(1)));
        SpellBook.MODERN.register(14, new WaterSpell(SpellType.BOLT, 23, 16.5, Sounds.WATERBOLT_CAST_AND_FIRE_209, BOLT_START, BOLT_PROJECTILE, BOLT_END, Runes.CHAOS_RUNE.getItem(1), Runes.WATER_RUNE.getItem(2), Runes.AIR_RUNE.getItem(2)));
        SpellBook.MODERN.register(27, new WaterSpell(SpellType.BLAST, 47, 28.5, Sounds.WATERBLAST_CAST_AND_FIRE_207, BLAST_START, BLAST_PROJECTILE, BLAST_END, Runes.DEATH_RUNE.getItem(1), Runes.WATER_RUNE.getItem(3), Runes.AIR_RUNE.getItem(3)));
        SpellBook.MODERN.register(48, new WaterSpell(SpellType.WAVE, 65, 37.5, Sounds.WATERWAVE_CAST_AND_FIRE_213, WAVE_START, WAVE_PROJECTILE, WAVE_END, Runes.BLOOD_RUNE.getItem(1), Runes.WATER_RUNE.getItem(7), Runes.AIR_RUNE.getItem(5)));
        return this;
    }

}
