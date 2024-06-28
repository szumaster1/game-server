package content.global.skill.production.crafting.handlers;

import core.cache.def.impl.SceneryDefinition;
import core.game.dialogue.SkillDialogueHandler;
import core.game.dialogue.SkillDialogueHandler.SkillDialogue;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.diary.DiaryType;
import core.game.node.entity.skill.SkillPulse;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.utilities.StringUtils;

@Initializable
public final class WeaveOptionHandler extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        SceneryDefinition.setOptionHandler("weave", this);
        return this;
    }

    @Override
    public boolean handle(final Player player, final Node node, String option) {
        new SkillDialogueHandler(player, SkillDialogue.THREE_OPTION, WeavingItem.SACK.getProduct(), WeavingItem.BASKET.getProduct(), WeavingItem.CLOTH.getProduct()) {
            @Override
            public void create(int amount, int index) {
                player.getPulseManager().run(new WeavePulse(player, (Scenery) node, WeavingItem.values()[index], amount));
            }
        }.open();
        return true;
    }

    public static final class WeavePulse extends SkillPulse<Scenery> {

        private static final Animation ANIMATION = new Animation(2270);
        private final WeavingItem type;
        private int amount;
        private int ticks;

        public WeavePulse(Player player, Scenery node, final WeavingItem type, final int amount) {
            super(player, node);
            this.type = type;
            this.amount = amount;
        }

        @Override
        public boolean checkRequirements() {
            if (player.getSkills().getLevel(Skills.CRAFTING) < type.getLevel()) {
                player.getPacketDispatch().sendMessage("You need a Crafting level of at least " + type.getLevel() + " in order to do this.");
                return false;
            }
            if (!player.getInventory().containsItem(type.getRequired())) {
                player.getPacketDispatch().sendMessage("You need " + type.getRequired().getAmount() + " " + type.getRequired().getName().toLowerCase().replace("ball", "balls") + (type == WeavingItem.SACK ? "s" : type == WeavingItem.CLOTH ? "" : "es") + " to weave " + (StringUtils.isPlusN(type.getProduct().getName().toLowerCase()) ? "an" : "a") + " " + type.getProduct().getName().toLowerCase() + ".");
                return false;
            }
            return true;
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
            if (player.getInventory().remove(type.getRequired())) {
                player.getInventory().add(type.getProduct());
                player.getSkills().addExperience(Skills.CRAFTING, type.getExperience(), true);
                player.getPacketDispatch().sendMessage("You weave the "
                        + type.getRequired().getName().toLowerCase().replace("ball", "balls")
                        + (type == WeavingItem.SACK ? "s" : type == WeavingItem.CLOTH ? "" : "es")
                        + " into " + (StringUtils.isPlusN(type.getProduct().getName().toLowerCase()) ? "an" : "a")
                        + " " + type.getProduct().getName().toLowerCase() + ".");
                if (type == WeavingItem.BASKET && node.getId() == 8717 && player.getLocation().withinDistance(new Location(3039, 3287, 0))
                        && !player.getAchievementDiaryManager().getDiary(DiaryType.FALADOR).isComplete(1, 0)) {
                    player.getAchievementDiaryManager().getDiary(DiaryType.FALADOR).updateTask(player, 1, 0, true);
                }
            }
            amount--;
            return amount < 1;
        }

    }

    public enum WeavingItem {
        SACK(new Item(5418), new Item(5931, 4), 21, 38),
        BASKET(new Item(5376), new Item(5933, 6), 36, 56),
        CLOTH(new Item(3224), new Item(1759, 4), 10, 12);

        private final Item product;
        private final Item required;
        private final int level;
        private final double experience;

        WeavingItem(Item product, final Item required, int level, double experience) {
            this.product = product;
            this.required = required;
            this.level = level;
            this.experience = experience;
        }

        public Item getProduct() {
            return product;
        }
        public Item getRequired() {
            return required;
        }
        public int getLevel() {
            return level;
        }
        public double getExperience() {
            return experience;
        }
    }
}
