package content.global.skill.production.crafting.item;

import content.global.skill.production.crafting.data.LeatherData;
import core.cache.def.impl.ItemDefinition;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.SkillPulse;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.update.flag.context.Animation;
import core.utilities.StringUtils;

public final class DragonCraftPulse extends SkillPulse<Item> {

    private static final Animation ANIMATION = Animation.create(1249);
    private final LeatherData.DragonHide hide;
    private int amount;
    private int ticks;

    public DragonCraftPulse(Player player, Item node, LeatherData.DragonHide hide, int amount) {
        super(player, node);
        this.hide = hide;
        this.amount = amount;
    }

    @Override
    public boolean checkRequirements() {
        if (player.getSkills().getLevel(Skills.CRAFTING) < hide.level) {
            player.getDialogueInterpreter().sendDialogue("You need a crafting level of " + hide.level + " to make " + ItemDefinition.forId(hide.product).getName() + ".");
            amount = 0;
            return false;
        }
        if (!player.getInventory().contains(LeatherData.NEEDLE, 1)) {
            player.getDialogueInterpreter().sendDialogue("You need a needle to make this.");
            amount = 0;
            return false;
        }
        if (!player.getInventory().containsItem(LeatherData.THREAD)) {
            player.getDialogueInterpreter().sendDialogue("You need thread to make this.");
            amount = 0;
            return false;
        }
        if (!player.getInventory().contains(hide.leather, hide.amount)) {
            player.getDialogueInterpreter().sendDialogue("You need " + hide.amount + " " + ItemDefinition.forId(hide.leather).getName().toLowerCase() + " to make this.");
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
        if (player.getInventory().remove(new Item(hide.leather, hide.amount))) {
            if (hide.name().contains("VAMBS")) {
                player.getPacketDispatch().sendMessage("You make a pair of " + ItemDefinition.forId(hide.product).getName().toLowerCase() + "'s.");
            } else {
                player.getPacketDispatch().sendMessage("You make " + (StringUtils.isPlusN(ItemDefinition.forId(hide.product).getName().toLowerCase()) ? "an" : "a") + " " + ItemDefinition.forId(hide.product).getName().toLowerCase() + ".");
            }
            Item item = new Item(hide.product);
            player.getInventory().add(item);
            player.getSkills().addExperience(Skills.CRAFTING, hide.experience, true);
            LeatherData.decayThread(player);
            if (LeatherData.isLastThread(player)) {
                LeatherData.removeThread(player);
            }
            amount--;
        }
        return amount < 1;
    }

    @Override
    public void message(int type) {

    }
}
