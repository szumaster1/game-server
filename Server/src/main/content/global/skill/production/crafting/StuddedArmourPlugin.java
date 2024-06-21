package content.global.skill.production.crafting;

import core.game.dialogue.SkillDialogueHandler;
import core.game.dialogue.SkillDialogueHandler.SkillDialogue;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.SkillPulse;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.plugin.Plugin;

/**
 * The Studded armour plugin.
 */
@Initializable
public final class StuddedArmourPlugin extends UseWithHandler {

    private static final Item STEEL_STUDS = new Item(2370);

    /**
     * Instantiates a new Studded armour plugin.
     */
    public StuddedArmourPlugin() {
        super(2370);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        for (StuddedArmour armour : StuddedArmour.values()) {
            addHandler(armour.getItem().getId(), ITEM_TYPE, this);
        }
        return this;
    }

    @Override
    public boolean handle(final NodeUsageEvent event) {
        final Player player = event.getPlayer();
        final StuddedArmour armour = StuddedArmour.forItem(event.getBaseItem());
        SkillDialogueHandler handler = new SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, armour.getStudded()) {

            @Override
            public void create(int amount, int index) {
                player.getPulseManager().run(new StudArmourPulse(player, event.getBaseItem(), armour, amount));
            }

            @Override
            public int getAll(int index) {
                return player.getInventory().getAmount(STEEL_STUDS);
            }

        };
        if (player.getInventory().getAmount(armour.getItem()) == 1) {
            handler.create(1, 0);
        } else {
            handler.open();
        }
        return true;
    }

    /**
     * The enum Studded armour.
     */
    public enum StuddedArmour {
        /**
         * The Chaps.
         */
        CHAPS(new Item(1095), new Item(1097), 44, 42),
        /**
         * The Body.
         */
        BODY(new Item(1129), new Item(1133), 41, 40);
        private final Item item;
        private final Item studded;
        private final int level;
        private final double experience;

        StuddedArmour(Item item, Item studded, int level, double experience) {
            this.item = item;
            this.studded = studded;
            this.level = level;
            this.experience = experience;
        }

        /**
         * For item studded armour.
         *
         * @param item the item
         * @return the studded armour
         */
        public static StuddedArmour forItem(final Item item) {
            for (StuddedArmour armour : values()) {
                if (armour.getItem().getId() == item.getId()) {
                    return armour;
                }
            }
            return null;
        }

        /**
         * Gets item.
         *
         * @return the item
         */
        public Item getItem() {
            return item;
        }

        /**
         * Gets studded.
         *
         * @return the studded
         */
        public Item getStudded() {
            return studded;
        }

        /**
         * Gets level.
         *
         * @return the level
         */
        public int getLevel() {
            return level;
        }

        /**
         * Gets experience.
         *
         * @return the experience
         */
        public double getExperience() {
            return experience;
        }

    }

    /**
     * The Stud armour pulse.
     */
    public static final class StudArmourPulse extends SkillPulse<Item> {
        private static final Animation ANIMATION = Animation.create(1249);
        private final StuddedArmour armour;
        private int amount;
        private int ticks;


        /**
         * Instantiates a new Stud armour pulse.
         *
         * @param player the player
         * @param node   the node
         * @param armour the armour
         * @param amount the amount
         */
        public StudArmourPulse(Player player, Item node, final StuddedArmour armour, final int amount) {
            super(player, node);
            this.armour = armour;
            this.amount = amount;
        }

        @Override
        public boolean checkRequirements() {
            if (player.getSkills().getLevel(Skills.CRAFTING) < armour.getLevel()) {
                player.getPacketDispatch().sendMessage("You need a Crafting level of at least " + armour.getLevel() + " to do this.");
                return false;
            }
            if (!player.getInventory().containsItem(STEEL_STUDS)) {
                player.getPacketDispatch().sendMessage("You need studs in order to make studded armour.");
                return false;
            }
            return player.getInventory().containsItem(armour.getItem());
        }

        @Override
        public void animate() {
            if (ticks % 5 == 0) {
                player.animate(ANIMATION);
            }
        }

        @Override
        public boolean reward() {
            if (++ticks % 5 != 0) {
                return false;
            }
            if (player.getInventory().remove(armour.getItem(), STEEL_STUDS)) {
                player.getInventory().add(armour.getStudded());
                player.getSkills().addExperience(Skills.CRAFTING, armour.getExperience(), true);
                player.getPacketDispatch().sendMessage("You make a " + armour.getStudded().getName().toLowerCase() + ".");
            }
            amount--;
            return amount < 1;
        }

        @Override
        public void message(int type) {
            switch (type) {
                case 0:
                    player.getPacketDispatch().sendMessage("You use the studs with the " + node.getName().toLowerCase() + ".");
                    break;
            }
        }
    }
}
