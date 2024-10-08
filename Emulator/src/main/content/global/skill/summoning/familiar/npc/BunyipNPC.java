package content.global.skill.summoning.familiar.npc;

import content.data.consumables.Consumables;
import content.global.skill.cooking.Cookable;
import content.global.skill.gather.fishing.Fish;
import content.global.skill.summoning.familiar.Familiar;
import content.global.skill.summoning.familiar.FamiliarSpecial;
import core.game.consumable.Consumable;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.entity.combat.ImpactHandler;
import core.game.node.entity.combat.equipment.WeaponInterface;
import core.game.node.entity.impl.Projectile;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.GameWorld;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.tools.RandomFunction;

/**
 * Bunyip npc.
 */
@Initializable
public class BunyipNPC extends Familiar {
    private static final int[] FISH = new int[]{317, 327, 3150, 345, 321, 353, 335, 341, 349, 3379, 331, 5004, 359, 10138, 5001, 377, 363, 371, 2148, 7944, 3142, 383, 395, 389, 401, 405, 407};
    private int lastHeal;

    /**
     * Instantiates a new Bunyip npc.
     */
    public BunyipNPC() {
        this(null, 6813);
    }

    /**
     * Instantiates a new Bunyip npc.
     *
     * @param owner the owner
     * @param id    the id
     */
    public BunyipNPC(Player owner, int id) {
        super(owner, id, 4400, 12029, 3, WeaponInterface.STYLE_ACCURATE);
        setLastHeal();
    }

    @Override
    public Familiar construct(Player owner, int id) {
        return new BunyipNPC(owner, id);
    }

    @Override
    public void tick() {
        super.tick();
        if (lastHeal < GameWorld.getTicks()) {
            setLastHeal();
            owner.graphics(Graphic.create(1507), 1);
            // Since https://runescape.wiki/w/Bunyip?oldid=391088 (2008-04-02)
            // "The bunyip will automatically heal two hitpoints approximately every 15 seconds up to a player's maximum."
            // Since https://runescape.wiki/w/Bunyip?oldid=400848 (2008-04-06)
            // "The healing effect of the Bunyip can restore up to 352 hitpoints over its summoning duration of 44 minutes."
            // Numbers were multiplied by 10 with the constitution update on 2010-03-10 (https://runescape.wiki/w/Bunyip?oldid=2345672)
            owner.getSkills().heal(2);
        }
    }

    @Override
    public boolean isPoisonImmune() {
        return true;
    }

    /**
     * Sets last heal.
     */
    public void setLastHeal() {
        this.lastHeal = GameWorld.getTicks() + (int) (15 / 0.6);
    }

    @Override
    protected boolean specialMove(FamiliarSpecial special) {
        Fish fish = Fish.forItem(special.getItem());
        Player player = owner;
        if (fish == null) {
            player.sendMessage("You can't use this special on an object like that.");
            return false;
        }
        Consumable consumable = Consumables.getConsumableById(special.getItem().getId() + 2).consumable;
        if (consumable == null) {
            player.sendMessage("Error: Report to admin.");
            return false;
        }
        if (player.getSkills().getLevel(Skills.COOKING) < Cookable.forId(special.getItem().getId()).getLevel()) {
            player.sendMessage("You need a Cooking level of at least " + Cookable.forId(special.getItem().getId()).getLevel() + " in order to do that.");
            return false;
        }
        if (player.getInventory().remove(special.getItem())) {
            animate(Animation.create(7747));
            graphics(Graphic.create(1481));
            final int healthEffectValue = consumable.getHealthEffectValue(player);
            if (healthEffectValue > 0) {
                owner.getSkills().heal(consumable.getHealthEffectValue(player));
            } else {
                owner.getImpactHandler().manualHit(player, healthEffectValue, ImpactHandler.HitsplatType.NORMAL);
            }
        }
        return true;
    }

    @Override
    public void visualizeSpecialMove() {
        owner.visualize(Animation.create(7660), Graphic.create(1316));
    }

    @Override
    protected void handleFamiliarTick() {
    }

    @Override
    protected void configureFamiliar() {
        UseWithHandler.addHandler(6813, UseWithHandler.NPC_TYPE, new UseWithHandler(FISH) {
            @Override
            public Plugin<Object> newInstance(Object arg) throws Throwable {
                addHandler(6814, UseWithHandler.NPC_TYPE, this);
                return this;
            }

            @Override
            public boolean handle(NodeUsageEvent event) {
                Player player = event.getPlayer();
                Fish fish = Fish.forItem(event.getUsedItem());
                Consumable consumable = Consumables.getConsumableById(fish.getItem().getId() + 2).consumable;
                if (consumable == null) {
                    return true;
                }
                player.lock(1);
                Item runes = new Item(555, RandomFunction.random(1, consumable.getHealthEffectValue(player)));
                if (player.getInventory().remove(event.getUsedItem())) {
                    player.animate(Animation.create(2779));
                    Projectile.create(player, event.getUsedWith().asNpc(), 1435).send();
                    player.getInventory().add(runes);
                    player.sendMessage("The bunyip transmutes the fish into some water runes.");
                }
                return true;
            }
        });
    }

    @Override
    public int[] getIds() {
        return new int[]{6813, 6814};
    }
}
