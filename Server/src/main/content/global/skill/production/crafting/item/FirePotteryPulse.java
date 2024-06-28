package content.global.skill.production.crafting.item;

import content.global.skill.production.crafting.data.PotteryData;
import core.api.consts.Animations;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.diary.DiaryType;
import core.game.node.entity.skill.SkillPulse;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;

public final class FirePotteryPulse extends SkillPulse<Item> {

    private static final Animation ANIMATION = new Animation(Animations.OLD_FURNACE_ANIMATION_899);
    private final PotteryData pottery;
    private int amount;
    private int ticks;

    public FirePotteryPulse(Player player, Item node, final PotteryData pottery, final int amount) {
        super(player, node);
        this.pottery = pottery;
        this.amount = amount;
    }

    @Override
    public boolean checkRequirements() {
        if (player.getSkills().getLevel(Skills.CRAFTING) < pottery.getLevel()) {
            player.getPacketDispatch().sendMessage("You need a crafting level of " + pottery.getLevel() + " in order to do this.");
            return false;
        }
        if (!player.getInventory().containsItem(pottery.getUnfinished())) {
            player.getPacketDispatch().sendMessage("You need a " + pottery.name().toLowerCase() + "in order to do this.");
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

        if (player.getInventory().remove(pottery.getUnfinished())) {
            final Item item = pottery.getProduct();
            player.getInventory().add(item);
            player.getSkills().addExperience(Skills.CRAFTING, pottery.getFireExp(), true);
            player.getPacketDispatch().sendMessage("You put the " + pottery.getUnfinished().getName().toLowerCase() + " in the oven.");
            player.getPacketDispatch().sendMessage("You remove a " + pottery.getProduct().getName().toLowerCase() + " from the oven.");

            /*
             * Spin a bowl on the pottery wheel and fire it in the oven in Barbarian Village.
             */
            if (pottery == PotteryData.BOWL
                    && player.getLocation().withinDistance(Location.create(3085, 3408, 0))
                    && player.getAttribute("diary:varrock:spun-bowl", false)) {
                player.getAchievementDiaryManager().finishTask(player, DiaryType.VARROCK, 0, 9);
            }
            /*
             * Fire a pot in the kiln in the Barbarian Village potter's house.
             */
            if (pottery == PotteryData.POT && player.getLocation().withinDistance(Location.create(3085, 3408, 0))) {
                player.getAchievementDiaryManager().finishTask(player, DiaryType.LUMBRIDGE, 0, 8);
            }
        }
        amount--;
        return amount < 1;
    }

}
