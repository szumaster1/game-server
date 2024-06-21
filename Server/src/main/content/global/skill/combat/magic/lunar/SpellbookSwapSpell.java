package content.global.skill.combat.magic.lunar;

import core.game.component.Component;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.spell.MagicSpell;
import core.game.node.entity.combat.spell.Runes;
import core.game.node.entity.combat.spell.SpellType;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.SpellBookManager.SpellBook;
import core.game.node.item.Item;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.utilities.RandomFunction;

import static core.api.ContentAPIKt.removeAttribute;
import static core.api.ContentAPIKt.setAttribute;

/**
 * The Spellbook swap spell.
 */
@Initializable
public class SpellbookSwapSpell extends MagicSpell {

    private final Animation ANIMATION = new Animation(6299);

    private final Graphic GRAPHIC = new Graphic(1062);

    /**
     * Instantiates a new Spellbook swap spell.
     */
    public SpellbookSwapSpell() {
        super(SpellBook.LUNAR, 96, 130, null, null, null, new Item[]{new Item(Runes.LAW_RUNE.getId(), 1), new Item(Runes.COSMIC_RUNE.getId(), 2), new Item(Runes.ASTRAL_RUNE.getId(), 3)});
    }

    @Override
    public Plugin<SpellType> newInstance(SpellType arg) throws Throwable {
        SpellBook.LUNAR.register(12, this);
        return this;
    }

    @Override
    public boolean cast(Entity entity, Node target) {
        final Player player = (Player) entity;
        if (!super.meetsRequirements(player, true, true)) {
            return false;
        }
        player.lock(9);
        player.animate(ANIMATION);
        player.graphics(GRAPHIC);
        player.getDialogueInterpreter().open(3264731);
        final int id = RandomFunction.random(1, 500000);
        setAttribute(player, "spell:swap", id);
        GameWorld.getPulser().submit(new Pulse(20, player) {
            @Override
            public boolean pulse() {
                if (player.getAttribute("spell:swap", 0) == id) {
                    removeTemporarySpell(player);
                }
                return true;
            }

        });
        return true;
    }

    /**
     * Remove temporary spell.
     *
     * @param player the player
     */
    public static void removeTemporarySpell(final Player player) {
        removeAttribute(player, "spell:swap");
        player.getSpellBookManager().setSpellBook(SpellBook.LUNAR);
        player.getInterfaceManager().openTab(new Component(SpellBook.LUNAR.getInterfaceId()));
    }
}
