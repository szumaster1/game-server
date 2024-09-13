package content.global.skill.combat.magic.lunar;

import content.data.consumable.Consumables;
import cfg.consts.Sounds;
import core.game.bots.AIPlayer;
import core.game.consumable.Potion;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.spell.MagicSpell;
import core.game.node.entity.combat.spell.Runes;
import core.game.node.entity.combat.spell.SpellType;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.SpellBookManager.SpellBook;
import core.game.node.item.Item;
import core.game.world.map.RegionManager;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.plugin.Plugin;

import java.util.List;

import static core.api.ContentAPIKt.playGlobalAudio;

/**
 * Stat boost spell.
 */
@Initializable
public final class StatBoostSpell extends MagicSpell {
    private static final Animation ANIMATION = new Animation(4413);
    private static final Graphic GRAPHIC = new Graphic(733, 130);
    /**
     * The constant VIAL.
     */
    public static final int VIAL = 229;

    /**
     * Instantiates a new Stat boost spell.
     */
    public StatBoostSpell() {
        super(SpellBook.LUNAR, 84, 88, null, null, null, new Item[]{new Item(Runes.ASTRAL_RUNE.getId(), 3), new Item(Runes.EARTH_RUNE.getId(), 12), new Item(Runes.WATER_RUNE.getId(), 10)});
    }

    @Override
    public Plugin<SpellType> newInstance(SpellType arg) throws Throwable {
        SpellBook.LUNAR.register(26, this);
        return this;
    }

    @Override
    public boolean cast(Entity entity, Node target) {
        final Player player = ((Player) entity);
        Item item = ((Item) target);
        final Potion potion = (Potion) Consumables.getConsumableById(item.getId()).consumable;
        player.getInterfaceManager().setViewedTab(6);
        if (potion == null) {
            player.getPacketDispatch().sendMessage("You can only cast this spell on a potion.");
            return false;
        }
        if (!item.getDefinition().isTradeable() || item.getName().toLowerCase().contains("restore") || item.getName().toLowerCase().contains("zamorak") || item.getName().toLowerCase().contains("saradomin") || item.getName().toLowerCase().contains("combat")) {
            player.getPacketDispatch().sendMessage("You can't cast this spell on that item.");
            return false;
        }
        List<Player> pl = RegionManager.getLocalPlayers(player, 1);
        int plSize = pl.size() - 1;
        int doses = potion.getDose(item);
        if (pl.size() == 0) {
            return false;
        }
        if (!super.meetsRequirements(player, true, false)) {
            return false;
        }
        int size = 0;
        for (Player players : pl) {
            Player o = players;
            if (size >= doses) break;
            if (!o.isActive() || o.getLocks().isInteractionLocked() || o == player) {
                continue;
            }
            if (!o.getSettings().isAcceptAid() && !(o instanceof AIPlayer)) {
                continue;
            }
            o.graphics(GRAPHIC);
            playGlobalAudio(o.getLocation(), Sounds.LUNAR_STRENGTH_SHARE2_2902);
            potion.getEffect().activate(o);
            size++;
        }
        if (size == 0) {
            player.getPacketDispatch().sendMessage("There is nobody around that has accept aid on to share the potion with you.");
            return false;
        }
        super.meetsRequirements(player, true, true);
        potion.getEffect().activate(player);
        playGlobalAudio(player.getLocation(), Sounds.LUNAR_STRENGTH_SHARE_2901);
        player.animate(ANIMATION);
        player.graphics(GRAPHIC);
        player.getInventory().remove(item);
        int newIndex = (potion.getIds().length - doses) + size;
        if (newIndex > potion.getIds().length - 1) {
            player.getInventory().add(new Item(229));
            return true;
        }
        player.getInventory().add(new Item(potion.getIds()[newIndex]));
        return true;
    }
}
