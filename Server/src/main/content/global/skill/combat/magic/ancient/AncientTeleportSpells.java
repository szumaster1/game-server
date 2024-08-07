package content.global.skill.combat.magic.ancient;

import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.spell.MagicSpell;
import core.game.node.entity.combat.spell.Runes;
import core.game.node.entity.combat.spell.SpellType;
import core.game.node.entity.player.link.SpellBookManager.SpellBook;
import core.game.node.entity.player.link.TeleportManager.TeleportType;
import core.game.node.entity.player.link.diary.DiaryType;
import core.game.node.item.Item;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.tools.RandomFunction;

/**
 * Ancient teleport spells.
 */
@Initializable
public final class AncientTeleportSpells extends MagicSpell {

    private Location location;

    /**
     * Instantiates a new Ancient teleport spells.
     */
    public AncientTeleportSpells() {

    }

    /**
     * Instantiates a new Ancient teleport spells.
     *
     * @param level      the level
     * @param experience the experience
     * @param location   the location
     * @param items      the items
     */
    public AncientTeleportSpells(final int level, final double experience, final Location location, final Item... items) {
        super(SpellBook.ANCIENT, level, experience, null, null, null, items);
        this.location = location;
    }

    @Override
    public boolean cast(Entity entity, Node target) {
        if (entity.isTeleBlocked() || !super.meetsRequirements(entity, true, false)) {
            entity.asPlayer().sendMessage("A magical force has stopped you from teleporting.");
            return false;
        }
        if (entity.getTeleporter().send(location.transform(0, RandomFunction.random(3), 0), getSpellId() == 28 ? TeleportType.HOME : TeleportType.ANCIENT)) {
            if (!super.meetsRequirements(entity, true, true)) {
                entity.getTeleporter().getCurrentTeleport().stop();
                return false;
            }

            if (entity.isPlayer() && location.equals(Location.create(3087, 3495, 0))) {
                entity.asPlayer().getAchievementDiaryManager().finishTask(entity.asPlayer(), DiaryType.VARROCK, 2, 11);
            }
            entity.setAttribute("teleport:items", super.runes);
            entity.setAttribute("magic-delay", GameWorld.getTicks() + 5);
            return true;
        }
        return false;
    }

    @Override
    public Plugin<SpellType> newInstance(SpellType arg) throws Throwable {
        // home
        SpellBook.ANCIENT.register(28, new AncientTeleportSpells(0, 0, Location.create(3087, 3495, 0)));
        // paddewwa teleport
        SpellBook.ANCIENT.register(20, new AncientTeleportSpells(54, 64, Location.create(3098, 9882, 0), new Item(Runes.LAW_RUNE.getId(), 2), new Item(Runes.FIRE_RUNE.getId(), 1), new Item(Runes.AIR_RUNE.getId(), 1)));
        // sennisten teleport
        SpellBook.ANCIENT.register(21, new AncientTeleportSpells(60, 70, Location.create(3320, 3338, 0), new Item(Runes.SOUL_RUNE.getId(), 1), new Item(Runes.LAW_RUNE.getId(), 2)));
        // karyll teleport
        SpellBook.ANCIENT.register(22, new AncientTeleportSpells(66, 76, Location.create(3493, 3472, 0), new Item(Runes.LAW_RUNE.getId(), 2), new Item(Runes.BLOOD_RUNE.getId(), 1)));
        // lassar teleport
        SpellBook.ANCIENT.register(23, new AncientTeleportSpells(72, 82, Location.create(3003, 3470, 0), new Item(Runes.LAW_RUNE.getId(), 2), new Item(Runes.WATER_RUNE.getId(), 4)));
        // dareeyak teleport
        SpellBook.ANCIENT.register(24, new AncientTeleportSpells(78, 88, Location.create(2966, 3696, 0), new Item(Runes.LAW_RUNE.getId(), 2), new Item(Runes.FIRE_RUNE.getId(), 3), new Item(Runes.AIR_RUNE.getId(), 2)));
        // carralangar teleport
        SpellBook.ANCIENT.register(25, new AncientTeleportSpells(84, 82, Location.create(3163, 3664, 0), new Item(Runes.SOUL_RUNE.getId(), 2), new Item(Runes.LAW_RUNE.getId(), 2)));
        // annakarl teleport
        SpellBook.ANCIENT.register(26, new AncientTeleportSpells(90, 100, Location.create(3287, 3883, 0), new Item(Runes.BLOOD_RUNE.getId(), 2), new Item(Runes.LAW_RUNE.getId(), 2)));
        // ghorrock teleport
        SpellBook.ANCIENT.register(27, new AncientTeleportSpells(96, 106, Location.create(2972, 3873, 0), new Item(Runes.LAW_RUNE.getId(), 2), new Item(Runes.WATER_RUNE.getId(), 8)));
        return this;
    }

}
