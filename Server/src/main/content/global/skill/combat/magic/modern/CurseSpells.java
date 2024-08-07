package content.global.skill.combat.magic.modern;

import core.api.consts.Sounds;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.spell.CombatSpell;
import core.game.node.entity.combat.spell.Runes;
import core.game.node.entity.combat.spell.SpellType;
import core.game.node.entity.impl.Animator.Priority;
import core.game.node.entity.impl.Projectile;
import core.game.node.entity.player.link.SpellBookManager.SpellBook;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * Curse spells.
 */
@Initializable
public final class CurseSpells extends CombatSpell {

    private static final Graphic CONFUSE_START = new Graphic(102, 96);
    private static final Projectile CONFUSE_PROJECTILE = Projectile.create((Entity) null, null, 103, 40, 36, 52, 75, 15, 11);
    private static final Graphic CONFUSE_END = new Graphic(104, 96);
    private static final Graphic WEAKEN_START = new Graphic(105, 96);
    private static final Projectile WEAKEN_PROJECTILE = Projectile.create((Entity) null, null, 106, 40, 36, 52, 75, 15, 11);
    private static final Graphic WEAKEN_END = new Graphic(107, 96);
    private static final Graphic CURSE_START = new Graphic(108, 96);
    private static final Projectile CURSE_PROJECTILE = Projectile.create((Entity) null, null, 109, 40, 36, 52, 75, 15, 11);
    private static final Graphic CURSE_END = new Graphic(110, 96);
    private static final Graphic VULNER_START = new Graphic(167, 96);
    private static final Projectile VULNER_PROJECTILE = Projectile.create((Entity) null, null, 168, 40, 36, 52, 75, 15, 11);
    private static final Graphic VULNER_END = new Graphic(169, 96);
    private static final Graphic ENFEEBLE_START = new Graphic(170, 96);
    private static final Projectile ENFEEBLE_PROJECTILE = Projectile.create((Entity) null, null, 171, 40, 36, 52, 75, 15, 11);
    private static final Graphic ENFEEBLE_END = new Graphic(172, 96);
    private static final Graphic STUN_START = new Graphic(173, 96);
    private static final Projectile STUN_PROJECTILE = Projectile.create((Entity) null, null, 174, 40, 36, 52, 75, 15, 11);
    private static final Graphic STUN_END = new Graphic(107, 96);
    private static final Animation LOW_ANIMATION = new Animation(716, Priority.HIGH);
    private static final Animation HIGH_ANIMATION = new Animation(729, Priority.HIGH);

    /**
     * Instantiates a new Curse spells.
     */
    public CurseSpells() {

    }

    private CurseSpells(SpellType type, int level, double baseExperience, int sound, int impactAudio, Graphic start, Projectile projectile, Graphic end, Item... runes) {
        super(type, SpellBook.MODERN, level, baseExperience, sound, impactAudio, type.ordinal() <= SpellType.CURSE.ordinal() ? LOW_ANIMATION : HIGH_ANIMATION, start, projectile, end, runes);
    }

    @Override
    public int getMaximumImpact(Entity entity, Entity victim, BattleState state) {
        return 1;
    }

    @Override
    public void fireEffect(Entity entity, Entity victim, BattleState state) {
        if (state.getEstimatedHit() == -1) {
            return;
        }
        state.setEstimatedHit(-2);
        switch (getType()) {
            case CONFUSE:
                victim.getSkills().drainLevel(Skills.ATTACK, 0.05, 0.05);
                break;
            case WEAKEN:
                victim.getSkills().drainLevel(Skills.STRENGTH, 0.05, 0.05);
                break;
            case CURSE:
                victim.getSkills().drainLevel(Skills.DEFENCE, 0.05, 0.05);
                break;
            case VULNERABILITY:
                victim.getSkills().drainLevel(Skills.DEFENCE, 0.10, 0.10);
                break;
            case ENFEEBLE:
                victim.getSkills().drainLevel(Skills.STRENGTH, 0.10, 0.10);
                break;
            case STUN:
                victim.getSkills().drainLevel(Skills.ATTACK, 0.10, 0.10);
                break;
            default:
        }
    }

    @Override
    public void addExperience(Entity entity, int hit) {
        entity.getSkills().addExperience(Skills.MAGIC, getExperience());
    }

    @Override
    public Plugin<SpellType> newInstance(SpellType type) throws Throwable {
        SpellBook.MODERN.register(2, new CurseSpells(SpellType.CONFUSE, 3, 13.0, Sounds.CONFUSE_CAST_AND_FIRE_119, Sounds.CONFUSE_HIT_121, CONFUSE_START, CONFUSE_PROJECTILE, CONFUSE_END, Runes.BODY_RUNE.getItem(1), Runes.EARTH_RUNE.getItem(2), Runes.WATER_RUNE.getItem(3)));
        SpellBook.MODERN.register(7, new CurseSpells(SpellType.WEAKEN, 11, 21.0, Sounds.WEAKEN_CAST_AND_FIRE_3011, Sounds.WEAKEN_HIT_3010, WEAKEN_START, WEAKEN_PROJECTILE, WEAKEN_END, Runes.BODY_RUNE.getItem(1), Runes.EARTH_RUNE.getItem(2), Runes.WATER_RUNE.getItem(3)));
        SpellBook.MODERN.register(11, new CurseSpells(SpellType.CURSE, 19, 29.0, Sounds.CURSE_CAST_AND_FIRE_127, Sounds.CURSE_HIT_126, CURSE_START, CURSE_PROJECTILE, CURSE_END, Runes.BODY_RUNE.getItem(1), Runes.EARTH_RUNE.getItem(3), Runes.WATER_RUNE.getItem(2)));
        SpellBook.MODERN.register(50, new CurseSpells(SpellType.VULNERABILITY, 66, 76.0, Sounds.VULNERABILITY_CAST_AND_FIRE_3009, Sounds.VULNERABILITY_IMPACT_3008, VULNER_START, VULNER_PROJECTILE, VULNER_END, Runes.SOUL_RUNE.getItem(1), Runes.EARTH_RUNE.getItem(5), Runes.WATER_RUNE.getItem(5)));
        SpellBook.MODERN.register(53, new CurseSpells(SpellType.ENFEEBLE, 73, 83.0, Sounds.ENFEEBLE_CAST_AND_FIRE_148, Sounds.ENFEEBLE_HIT_150, ENFEEBLE_START, ENFEEBLE_PROJECTILE, ENFEEBLE_END, Runes.SOUL_RUNE.getItem(1), Runes.EARTH_RUNE.getItem(8), Runes.WATER_RUNE.getItem(8)));
        SpellBook.MODERN.register(57, new CurseSpells(SpellType.STUN, 80, 90.0, Sounds.STUN_CAST_AND_FIRE_3004, Sounds.STUN_IMPACT_3005, STUN_START, STUN_PROJECTILE, STUN_END, Runes.SOUL_RUNE.getItem(1), Runes.EARTH_RUNE.getItem(12), Runes.WATER_RUNE.getItem(12)));
        return this;
    }

}
