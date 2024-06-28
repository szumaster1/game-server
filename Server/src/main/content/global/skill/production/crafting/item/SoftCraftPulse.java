package content.global.skill.production.crafting.item;

import content.global.skill.production.crafting.data.LeatherData;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.diary.DiaryType;
import core.game.node.entity.skill.SkillPulse;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.update.flag.context.Animation;
import core.utilities.StringUtils;

public final class SoftCraftPulse extends SkillPulse<Item> {

    private static final Animation ANIMATION = Animation.create(1249);
    private final LeatherData.SoftLeather soft;
    private int amount;
    private int ticks;

    public SoftCraftPulse(Player player, Item node, LeatherData.SoftLeather leather, int amount) {
        super(player, node);
        this.soft = leather;
        this.amount = amount;
    }

    @Override
    public boolean checkRequirements() {
        if (player.getSkills().getLevel(Skills.CRAFTING) < soft.level) {
            player.getDialogueInterpreter().sendDialogue("You need a crafting level of " + soft.level + " to make " + (StringUtils.isPlusN(soft.product.getName()) ? "an" : "a" + " " + soft.product.getName()).toLowerCase() + ".");
            return false;
        }
        if (!player.getInventory().contains(LeatherData.NEEDLE, 1)) {
            return false;
        }
        if (!player.getInventory().contains(LeatherData.LEATHER, 1)) {
            return false;
        }
        if (!player.getInventory().containsItem(LeatherData.THREAD)) {
            player.getDialogueInterpreter().sendDialogue("You need thread to make this.");
            amount = 0;
            return false;
        }
        player.getInterfaceManager().close();
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
        if (player.getInventory().remove(new Item(LeatherData.LEATHER))) {
            if (soft == LeatherData.SoftLeather.GLOVES || soft == LeatherData.SoftLeather.BOOTS || soft == LeatherData.SoftLeather.VAMBRACES) {
                player.getPacketDispatch().sendMessage("You make a pair of " + soft.product.getName().toLowerCase() + ".");
            } else {
                player.getPacketDispatch().sendMessage("You make " + (StringUtils.isPlusN(soft.product.getName()) ? "an " : "a ") + soft.product.getName().toLowerCase() + ".");
            }
            Item item = soft.product;
            player.getInventory().add(item);
            player.getSkills().addExperience(Skills.CRAFTING, soft.experience, true);
            LeatherData.decayThread(player);
            if (LeatherData.isLastThread(player)) {
                LeatherData.removeThread(player);
            }
            if (soft == LeatherData.SoftLeather.GLOVES) {
                player.getAchievementDiaryManager().finishTask(player, DiaryType.LUMBRIDGE, 1, 3);
            }
        }
        amount--;
        return amount < 1;
    }

}
