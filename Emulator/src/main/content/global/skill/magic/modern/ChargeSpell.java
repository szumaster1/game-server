package content.global.skill.magic.modern;

import org.rs.consts.Sounds;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.spell.MagicSpell;
import core.game.node.entity.combat.spell.Runes;
import core.game.node.entity.combat.spell.SpellType;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.SpellBookManager.SpellBook;
import core.game.node.entity.player.link.audio.Audio;
import core.game.node.item.Item;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.*;

/**
 * Charge spell.
 */
@Initializable
public final class ChargeSpell extends MagicSpell {

    /**
     * Instantiates a new Charge spell.
     */
    public ChargeSpell() {
        super(SpellBook.MODERN, 80, 180, Animation.create(811), new Graphic(6, 96), new Audio(Sounds.CHARGE_1651), new Item[]{Runes.FIRE_RUNE.getItem(3), Runes.BLOOD_RUNE.getItem(3), Runes.AIR_RUNE.getItem(3)});
    }

    @Override
    public Plugin<SpellType> newInstance(SpellType arg) throws Throwable {
        SpellBook.MODERN.register(58, this);
        return this;
    }

    @Override
    public boolean cast(Entity entity, Node target) {
        final Player p = (Player) entity;
        if (p.getLocks().isLocked("charge_cast")) {
            p.getPacketDispatch().sendMessage("You need to wait for the spell to recharge.");
            return false;
        }
        if (!meetsRequirements(entity, true, true)) {
            return false;
        }
        p.getLocks().lock("charge_cast", 100);
        visualize(entity, target);
        // Remove the previous copy of the state in order to
        // refresh the duration if recast before 7 minutes
        removeTimer(p, "magic:spellcharge");
        registerTimer(p, spawnTimer("magic:spellcharge"));
        p.getPacketDispatch().sendMessage("You feel charged with magic power.");
        return true;
    }

}
