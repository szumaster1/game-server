package content.global.skill.production.crafting.item;

import content.global.skill.production.crafting.data.LeatherData;
import content.global.skill.production.crafting.data.SnakeskinData;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.SkillPulse;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.update.flag.context.Animation;

public final class SnakeskinCraftPulse extends SkillPulse<Item> {

    private static final Animation ANIMATION = Animation.create(1249);
    private final SnakeskinData skin;
    private int amount;
    private int ticks;

    public SnakeskinCraftPulse(Player player, Item node, int amount, SnakeskinData skin) {
        super(player, node);
        this.amount = amount;
        this.skin = skin;
    }

    @Override
    public boolean checkRequirements() {
        if (player.getSkills().getLevel(Skills.CRAFTING) < skin.getLevel()) {
            player.getDialogueInterpreter().sendDialogue("You need a crafting level of " + skin.getLevel() + " to make this.");
            return false;
        }
        if (!player.getInventory().contains(LeatherData.NEEDLE, 1)) {
            return false;
        }
        if (!player.getInventory().containsItem(LeatherData.THREAD)) {
            player.getDialogueInterpreter().sendDialogue("You need thread to make this.");
            return false;
        }
        if (!player.getInventory().contains(6289, skin.getRequiredAmount())) {
            player.getDialogueInterpreter().sendDialogue("You need " + skin.getRequiredAmount() + " snakeskins in order to do this.");
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
        if (player.getInventory().remove(new Item(6289, skin.getRequiredAmount()))) {
            Item item = skin.getProduct();
            player.getInventory().add(item);
            player.getSkills().addExperience(Skills.CRAFTING, skin.getExperience(), true);
            LeatherData.decayThread(player);
            if (LeatherData.isLastThread(player)) {
                LeatherData.removeThread(player);
            }
        }
        amount--;
        return amount < 1;
    }

}
