package content.global.skill.production.crafting.pottery;

import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.diary.DiaryType;
import core.game.node.entity.skill.SkillPulse;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Animation;
import core.utilities.StringUtils;

import static core.api.ContentAPIKt.setAttribute;

/**
 * The Pottery pulse.
 */
public final class PotteryPulse extends SkillPulse<Item> {

    private static final Animation ANIMATION = Animation.create(896);
    private static final Item SOFT_CLAY = new Item(1761);
    private final PotteryItem pottery;
    private int amount;
    private int ticks;

    /**
     * Instantiates a new Pottery pulse.
     *
     * @param player  the player
     * @param node    the node
     * @param amount  the amount
     * @param pottery the pottery
     */
    public PotteryPulse(Player player, Item node, int amount, final PotteryItem pottery) {
        super(player, node);
        this.pottery = pottery;
        this.amount = amount;
    }

    @Override
    public boolean checkRequirements() {
        if (!player.getInventory().contains(1761, 1)) {
            player.getPacketDispatch().sendMessage("You need soft clay in order to do this.");
            return false;
        }
        if (player.getSkills().getLevel(Skills.CRAFTING) < pottery.getLevel()) {
            player.getPacketDispatch().sendMessage("You need a crafting level of " + pottery.getLevel() + " to make this.");
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
        if (player.getInventory().remove(SOFT_CLAY)) {
            if (pottery == PotteryItem.BOWL && player.getLocation().withinDistance(Location.create(3086, 3410, 0))) {
                setAttribute(player, "/save:diary:varrock:spun-bowl", true);
            }
            final Item item = pottery.getUnfinished();
            player.getInventory().add(item);
            player.getSkills().addExperience(Skills.CRAFTING, pottery.getExp(), true);
            player.getPacketDispatch().sendMessage("You make the soft clay into " + (StringUtils.isPlusN(pottery.getUnfinished().getName()) ? "an" : "a") + " " + pottery.getUnfinished().getName().toLowerCase() + ".");
            if (pottery == PotteryItem.POT && player.getLocation().withinDistance(Location.create(3086, 3410, 0))) {
                player.getAchievementDiaryManager().finishTask(player, DiaryType.LUMBRIDGE, 0, 7);
            }
        }
        amount--;
        return amount < 1;
    }

}
