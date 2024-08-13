package core.game.node.entity.combat.spell;

import core.game.component.Component;
import core.game.event.SpellCastEvent;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.CombatSwingHandler;
import core.game.node.entity.combat.equipment.WeaponInterface;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.SpellBookManager.SpellBook;
import core.game.node.entity.player.link.audio.Audio;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.GameWorld;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Plugin;
import core.tools.RandomFunction;

import java.util.ArrayList;
import java.util.List;

import static core.api.ContentAPIKt.playGlobalAudio;

/**
 * Magic spell.
 */
public abstract class MagicSpell implements Plugin<SpellType> {

    /**
     * The Book.
     */
    protected final SpellBook book;

    /**
     * The Level.
     */
    protected final int level;

    /**
     * The Animation.
     */
    protected final Animation animation;

    /**
     * The Graphic.
     */
    protected final Graphic graphic;

    /**
     * The Audio.
     */
    protected final Audio audio;

    /**
     * The Runes.
     */
    protected final Item[] runes;

    /**
     * The Spell id.
     */
    protected int spellId;

    private final double experience;

    /**
     * Instantiates a new Magic spell.
     */
    public MagicSpell() {
        this(SpellBook.MODERN, 0, 0, null, null, null, new Item[0]);
    }

    /**
     * Instantiates a new Magic spell.
     *
     * @param book       the book
     * @param level      the level
     * @param experience the experience
     * @param animation  the animation
     * @param graphic    the graphic
     * @param Audio      the audio
     * @param runes      the runes
     */
    public MagicSpell(SpellBook book, int level, final double experience, Animation animation, Graphic graphic, Audio Audio, Item[] runes) {
        this.book = book;
        this.level = level;
        this.experience = experience;
        this.animation = animation;
        this.graphic = graphic;
        this.audio = Audio;
        this.runes = runes;
    }

    /**
     * Cast spell boolean.
     *
     * @param p       the p
     * @param book    the book
     * @param spellId the spell id
     * @param target  the target
     * @return the boolean
     */
    public static boolean castSpell(final Player p, SpellBook book, int spellId, Node target) {
        if (p.getAttribute("magic-delay", 0) > GameWorld.getTicks()) {
            return false;
        }
        MagicSpell spell = book.getSpell(spellId);
        if (spell == null) {
            return false;
        }
        if (spell.book != book || p.getSpellBookManager().getSpellBook() != book.getInterfaceId()) {
            return false;
        }
        if (target.getLocation() != null && target != p) {
            if (!target.getLocation().withinDistance(p.getLocation(), 15)) {
                return false;
            }
            p.faceLocation(target.getLocation());
        }
        boolean combatSpell = spell instanceof CombatSpell;
        if (!combatSpell && target instanceof Entity) {
            p.faceTemporary((Entity) target, 1);
        }
        if (spell.cast(p, target)) {
            if (book != SpellBook.LUNAR && p.getAttribute("spell:swap", 0) != 0) {
                p.removeAttribute("spell:swap");
                p.getSpellBookManager().setSpellBook(SpellBook.LUNAR);
                p.getInterfaceManager().openTab(new Component(SpellBook.LUNAR.getInterfaceId()));
            }
            if (!combatSpell) {
                p.getSkills().addExperience(Skills.MAGIC, spell.getExperience(p), true);
            }
            if (p.getAttribute("magic-delay", 0) <= GameWorld.getTicks()) {
                p.setAttribute("magic-delay", GameWorld.getTicks() + spell.getDelay());
            }
            p.dispatch(new SpellCastEvent(book, spellId, target));
            return true;
        }
        return false;
    }

    /**
     * Gets delay.
     *
     * @return the delay
     */
    public int getDelay() {
        return 3;
    }

    /**
     * Cast boolean.
     *
     * @param entity the entity
     * @param target the target
     * @return the boolean
     */
    public abstract boolean cast(Entity entity, Node target);

    /**
     * Visualize.
     *
     * @param entity the entity
     * @param target the target
     */
    public void visualize(Entity entity, Node target) {
        entity.graphics(graphic);
        entity.animate(animation);
        playGlobalAudio(entity.getLocation(), audio.id, 20);
    }

    /**
     * Using staff boolean.
     *
     * @param p    the p
     * @param rune the rune
     * @return the boolean
     */
    public boolean usingStaff(Player p, int rune) {
        Item weapon = p.getEquipment().get(3);
        if (weapon == null) {
            return false;
        }
        MagicStaff staff = MagicStaff.forId(rune);
        if (staff == null) {
            return false;
        }
        int[] staves = staff.getStaves();
        for (int id : staves) {
            if (weapon.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Meets requirements boolean.
     *
     * @param caster  the caster
     * @param message the message
     * @param remove  the remove
     * @return the boolean
     */
    public boolean meetsRequirements(Entity caster, boolean message, boolean remove) {
        if (!checkLevelRequirement(caster, message)) {
            return false;
        }
        if (caster instanceof Player) {
            CombatSpell spell = ((Player) caster).getProperties().getAutocastSpell();
            if (spell != null) {
                boolean slayer = ((Player) caster).getEquipment().get(3).getName().contains("layer's staff");
                boolean voidKnight = ((Player) caster).getEquipment().get(3).getName().contains("knight mace");
                if ((spell.getSpellId() == 31 && !slayer) || (spell.getSpellId() == 42 && !voidKnight)) {
                    ((Player) caster).getPacketDispatch().sendMessage("You need the proper staff to autocast this spell.");
                    return false;
                }
            }
        }
        if ((spellId == 12 || spellId == 30 || spellId == 56) && caster instanceof Player) {
            if (caster.getAttribute("entangleDelay", 0) > GameWorld.getTicks()) {
                caster.asPlayer().sendMessage("You have recently cast a binding spell.");
                return false;
            }
        }
        if (caster instanceof Player) {
            Player p = (Player) caster;
            if (p.getEquipment().get(3) != null && p.getEquipment().get(3).getId() == 14726) {
                if (RandomFunction.getRandom(100) < 13) {
                    p.sendMessage("Your staff negates the rune requirement of the spell.");
                    return true;
                }
            }
            if (runes == null) {
                return true;
            }
            List<Item> toRemove = new ArrayList<>(20);
            for (Item item : runes) {
                if (!hasRune(p, item, toRemove, message)) {
                    return false;
                }
            }
            if (remove) {
                toRemove.forEach(i -> {
                    p.getInventory().remove(i);
                });
            }
            return true;
        }
        return true;
    }

    /**
     * Check level requirement boolean.
     *
     * @param caster  the caster
     * @param message the message
     * @return the boolean
     */
    public boolean checkLevelRequirement(Entity caster, boolean message) {
        if (caster instanceof Player && caster.getSkills().getLevel(Skills.MAGIC, this instanceof CombatSpell ? true : false) < levelRequirement()) {
            if (message && caster instanceof Player) {
                ((Player) caster).getPacketDispatch().sendMessage("You need a Magic level of " + levelRequirement() + " to cast this spell.");
            }
            return false;
        }
        return true;
    }

    /**
     * Has rune boolean.
     *
     * @param p        the p
     * @param item     the item
     * @param toRemove the to remove
     * @param message  the message
     * @return the boolean
     */
    public boolean hasRune(Player p, Item item, List<Item> toRemove, boolean message) {
        if (!usingStaff(p, item.getId())) {
            boolean hasBaseRune = p.getInventory().contains(item.getId(), item.getAmount());
            if (!hasBaseRune) {
                int baseAmt = p.getInventory().getAmount(item.getId());
                if (baseAmt > 0) {
                    toRemove.add(new Item(item.getId(), p.getInventory().getAmount(item.getId())));
                }
                int amtRemaining = item.getAmount() - baseAmt;
                List<CombinationRune> possibleComboRunes = CombinationRune.eligibleFor(Runes.forId(item.getId()));
                for (CombinationRune r : possibleComboRunes) {
                    if (p.getInventory().containsItem(new Item(r.id)) && amtRemaining > 0) {
                        int amt = p.getInventory().getAmount(r.id);
                        if (amtRemaining < amt) {
                            toRemove.add(new Item(r.id, amtRemaining));
                            amtRemaining = 0;
                            continue;
                        }
                        amtRemaining -= p.getInventory().getAmount(r.id);
                        toRemove.add(new Item(r.id, p.getInventory().getAmount(r.id)));
                    }
                }
                if (amtRemaining <= 0) {
                    return true;
                } else {
                    p.getPacketDispatch().sendMessage("You don't have enough " + item.getName() + "s to cast this spell.");
                    return false;
                }
            }
            toRemove.add(item);
            return true;
        }
        return true;
    }

    /**
     * Add experience.
     *
     * @param entity the entity
     * @param hit    the hit
     */
    public void addExperience(Entity entity, int hit) {
        entity.getSkills().addExperience(Skills.MAGIC, experience, true);
        if (!(entity instanceof Player) || hit < 1) {
            return;
        }
        entity.getSkills().addExperience(Skills.HITPOINTS, hit * 1.33, true);
        if (entity.getProperties().getAttackStyle().getStyle() == WeaponInterface.STYLE_DEFENSIVE_CAST) {
            double baseXpReward = (CombatSwingHandler.EXPERIENCE_MOD * hit) / 2.0;
            entity.getSkills().addExperience(Skills.DEFENCE, baseXpReward, true);
            entity.getSkills().addExperience(Skills.MAGIC, baseXpReward, true);
            return;
        }
        entity.getSkills().addExperience(Skills.MAGIC, hit * (CombatSwingHandler.EXPERIENCE_MOD), true);
    }

    /**
     * Level requirement int.
     *
     * @return the int
     */
    public int levelRequirement() {
        return level;
    }

    @Override
    public Object fireEvent(String identifier, Object... args) {
        return null;
    }

    /**
     * Gets audio.
     *
     * @return the audio
     */
    public Audio getAudio() {
        return audio;
    }

    /**
     * Gets book.
     *
     * @return the book
     */
    public SpellBook getBook() {
        return book;
    }

    /**
     * Get cast runes item [ ].
     *
     * @return the item [ ]
     */
    public Item[] getCastRunes() {
        return runes;
    }

    /**
     * Gets spell id.
     *
     * @return the spell id
     */
    public int getSpellId() {
        return spellId;
    }

    /**
     * Sets spell id.
     *
     * @param spellId the spell id
     */
    public void setSpellId(int spellId) {
        this.spellId = spellId;
    }

    /**
     * Gets experience.
     *
     * @return the experience
     */
    public double getExperience() {
        return experience;
    }

    /**
     * Gets experience.
     *
     * @param player the player
     * @return the experience
     */
    public double getExperience(Player player) {
        return experience;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }
}
