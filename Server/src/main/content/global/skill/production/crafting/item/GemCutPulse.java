package content.global.skill.production.crafting.item;

import content.global.skill.production.crafting.data.GemData;
import core.api.consts.Items;
import core.api.consts.Sounds;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.SkillPulse;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;

import static core.api.ContentAPIKt.playAudio;

public final class GemCutPulse extends SkillPulse<Item> {

    private static final Item CHISEL = new Item(Items.CHISEL_1755);
    private final GemData gem;
    private int amount;
    private int ticks;

    public GemCutPulse(Player player, Item item, int ammount, final GemData gem) {
        super(player, item);
        this.amount = ammount;
        this.gem = gem;
        this.resetAnimation = false;
    }

    @Override
    public boolean checkRequirements() {
        if (!player.getInventory().containsItem(CHISEL)) {
            return false;
        }
        if (!player.getInventory().containsItem(gem.getUncut())) {
            return false;
        }
        if (player.getSkills().getLevel(Skills.CRAFTING) < gem.getLevel()) {
            player.getPacketDispatch().sendMessage("You need a crafting level of " + gem.getLevel() + " to craft this gem.");
            return false;
        }
        return true;
    }

    @Override
    public void animate() {
        if (ticks % 5 == 0 || ticks < 1) {
            playAudio(player, Sounds.CHISEL_2586);
            player.animate(gem.getAnimation());
        }
    }

    @Override
    public boolean reward() {
        if (player.getInventory().remove(gem.getUncut())) {
            final Item item = gem.getGem();
            player.getInventory().add(item);
            player.getSkills().addExperience(Skills.CRAFTING, gem.getExp(), true);
        }
        amount--;
        return amount < 1;
    }
}
