package content.global.skill.combat.magic.modern;

import core.api.consts.Sounds;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.spell.MagicSpell;
import core.game.node.entity.combat.spell.Runes;
import core.game.node.entity.combat.spell.SpellType;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.SpellBookManager.SpellBook;
import core.game.node.entity.player.link.audio.Audio;
import core.game.node.entity.player.link.diary.DiaryType;
import core.game.node.entity.skill.SkillPulse;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.removeAttribute;
import static core.api.ContentAPIKt.setAttribute;

/**
 * The Charge orb spells.
 */
@Initializable
public final class ChargeOrbSpells extends MagicSpell {

    private static final Animation ANIMATION = Animation.create(791);

    private static final Item UNPOWERED_ORB = new Item(567);

    private int objectId;

    private int itemId;

    private int buttonId;


    /**
     * Instantiates a new Charge orb spells.
     */
    public ChargeOrbSpells() {

    }


    /**
     * Instantiates a new Charge orb spells.
     *
     * @param level    the level
     * @param objectId the object id
     * @param itemId   the item id
     * @param buttonId the button id
     * @param g        the g
     * @param a        the a
     * @param runes    the runes
     */
    public ChargeOrbSpells(int level, int objectId, int itemId, int buttonId, Graphic g, Audio a, Item... runes) {
        super(SpellBook.MODERN, level, level + 10, ANIMATION, g, a, runes);
        this.objectId = objectId;
        this.itemId = itemId;
        this.buttonId = buttonId;
    }

    @Override
    public Plugin<SpellType> newInstance(SpellType arg) throws Throwable {
        SpellBook.MODERN.register(35, new ChargeOrbSpells(56, 2151, 571, 35, new Graphic(149, 96), new Audio(Sounds.CHARGE_WATER_ORB_118), Runes.WATER_RUNE.getItem(30), Runes.COSMIC_RUNE.getItem(3), UNPOWERED_ORB));
        SpellBook.MODERN.register(39, new ChargeOrbSpells(60, 29415, 575, 39, new Graphic(151, 96), new Audio(Sounds.CHARGE_EARTH_ORB_115), Runes.EARTH_RUNE.getItem(30), Runes.COSMIC_RUNE.getItem(3), UNPOWERED_ORB));
        SpellBook.MODERN.register(46, new ChargeOrbSpells(63, 2153, 569, 46, new Graphic(152, 96), new Audio(Sounds.CHARGE_FIRE_ORB_117), Runes.FIRE_RUNE.getItem(30), Runes.COSMIC_RUNE.getItem(3), UNPOWERED_ORB));
        SpellBook.MODERN.register(49, new ChargeOrbSpells(66, 2152, 573, 49, new Graphic(150, 96), new Audio(Sounds.CHARGE_AIR_ORB_116), Runes.AIR_RUNE.getItem(30), Runes.COSMIC_RUNE.getItem(3), UNPOWERED_ORB));
        return this;
    }

    @Override
    public boolean cast(Entity entity, Node target) {
        if (!(target instanceof Scenery)) {
            return false;
        }
        Player p = (Player) entity;
        Scenery obj = (Scenery) target;
        if (obj == null || obj.getId() != objectId) {
            p.getPacketDispatch().sendMessage("You can't cast this spell on this object!");
            return false;
        }
        if (obj.getLocation().getDistance(p.getLocation()) > 3) {
            p.getPacketDispatch().sendMessage("You're standing too far from the obelisk's reach.");
            return false;
        }
        if (!p.getAchievementDiaryManager().hasCompletedTask(DiaryType.SEERS_VILLAGE, 2, 9)
                && objectId == 2151
                && p.getInventory().containsItems(Runes.COSMIC_RUNE.getItem(15), Runes.WATER_RUNE.getItem(150), new Item(UNPOWERED_ORB.getId(), 5))) {
            p.setAttribute("/save:diary:seers:water-orb-can-earn", true);

            p.setAttribute("/save:diary:seers:water-orb", 1);
        }
        if (!meetsRequirements(entity, true, true)) {
            return false;
        }
        p.faceLocation(obj.getLocation());
        visualize(p, target);
        if (!p.getPulseManager().hasPulseRunning()) {
            p.getPulseManager().run(new ChargeOrbPulse(p, new Item(itemId), target, buttonId));
        }
        p.getInventory().add(new Item(itemId));
        return true;
    }


    /**
     * The Charge orb pulse.
     */
    public static class ChargeOrbPulse extends SkillPulse<Item> {


        /**
         * The Item.
         */
        public Item item;


        /**
         * The Target.
         */
        public Node target;


        /**
         * The Button id.
         */
        public int buttonId;


        private static final Item UNPOWERED_ORB = new Item(567, 1);


        /**
         * Instantiates a new Charge orb pulse.
         *
         * @param player   the player
         * @param item     the item
         * @param target   the target
         * @param buttonId the button id
         */
        public ChargeOrbPulse(Player player, Item item, Node target, int buttonId) {
            super(player, item);
            this.item = item;
            this.target = target;
            this.buttonId = buttonId;
        }

        @Override
        public boolean checkRequirements() {
            return player.getInventory().contains(UNPOWERED_ORB.getId(), 1);
        }

        @Override
        public void animate() {
            player.animate(new Animation(791));
        }

        @Override
        public boolean reward() {
            if (getDelay() == 1) {
                super.setDelay(4);
                return false;
            }
            if (ChargeOrbSpells.castSpell(player, SpellBook.MODERN, buttonId, target)) {
                if (!player.getAchievementDiaryManager().hasCompletedTask(DiaryType.SEERS_VILLAGE, 2, 9)
                        && player.getAttribute("diary:seers:water-orb-can-earn", false)) {
                    setAttribute(player, "/save:diary:seers:water-orb", 1 + player.getAttribute("diary:seers:water-orb", 0));
                }
                if (player.getAttribute("diary:seers:water-orb", 0) >= 5) {
                    player.getAchievementDiaryManager().finishTask(player, DiaryType.SEERS_VILLAGE, 2, 9);
                    removeAttribute(player, "diary:seers:water-orb-can-earn");
                }
                return false;
            } else {
                return true;
            }
        }

        @Override
        public void stop() {
            removeAttribute(player, "diary:seers:water-orb-can-earn");
        }
    }
}
