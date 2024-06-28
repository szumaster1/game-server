package content.global.skill.production.crafting.item;

import content.global.skill.production.crafting.data.LeatherData;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.SkillPulse;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.update.flag.context.Animation;

public final class HardCraftPulse extends SkillPulse<Item> {

    private static final Animation ANIMATION = Animation.create(1249);
    private int amount;
    private int ticks;

    public HardCraftPulse(Player player, Item node, int amount) {
        super(player, node);
        this.amount = amount;
    }

    @Override
    public boolean checkRequirements() {
        if (player.getSkills().getLevel(Skills.CRAFTING) < 28) {
            player.getDialogueInterpreter().sendDialogue("You need a crafting level of " + 28 + " to make a hardleather body.");
            return false;
        }
        if (!player.getInventory().contains(LeatherData.NEEDLE, 1)) {
            return false;
        }
        if (!player.getInventory().contains(LeatherData.HARD_LEATHER, 1)) {
            return false;
        }
        if (!player.getInventory().containsItem(LeatherData.THREAD)) {
            player.getDialogueInterpreter().sendDialogue("You need thread to make this.");
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
        if (player.getInventory().remove(new Item(LeatherData.HARD_LEATHER))) {
            Item item = new Item(1131);
            player.getInventory().add(item);
            player.getSkills().addExperience(Skills.CRAFTING, 35, true);
            LeatherData.decayThread(player);
            if (LeatherData.isLastThread(player)) {
                LeatherData.removeThread(player);
            }
        }
        amount--;
        return amount < 1;
    }

}
