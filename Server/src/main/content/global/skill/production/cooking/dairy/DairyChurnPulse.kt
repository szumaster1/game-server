package content.global.skill.production.cooking.dairy;

import core.api.consts.Items;
import core.game.event.ResourceProducedEvent;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.SkillPulse;
import core.game.node.entity.skill.Skills;
import core.game.node.item.GroundItemManager;
import core.game.node.item.Item;
import core.game.world.update.flag.context.Animation;
import core.utilities.StringUtils;

/**
 * The Dairy churn pulse.
 */
public final class DairyChurnPulse extends SkillPulse<Item> {

    private static final Animation ANIMATION = new Animation(2793);

    private static final Item BUCKET_OF_MILK = new Item(1927, 1);

    private static final Item BUCKET = new Item(1925, 1);

    private final DairyProduct dairy;

    private int amount;

    /**
     * Instantiates a new Dairy churn pulse.
     *
     * @param player  the player
     * @param item    the item
     * @param product the product
     * @param amount  the amount
     */
    public DairyChurnPulse(Player player, Item item, DairyProduct product, int amount) {
        super(player, item);
        super.setDelay(8);
        this.amount = amount;
        this.dairy = product;
    }

    @Override
    public boolean checkRequirements() {
        player.getInterfaceManager().closeChatbox();
        boolean hasAnyInput = false;
        for (Item input : dairy.getInputs()) {
            if (player.getInventory().containsItem(input)) {
                hasAnyInput = true;
                node = input;
                break;
            }
        }
        if (!hasAnyInput) {
            player.getPacketDispatch().sendMessage("You need a bucket of milk.");
            return false;
        }
        if (player.getSkills().getLevel(Skills.COOKING) < dairy.getLevel()) {
            player.getPacketDispatch().sendMessage("You need a cooking level of " + dairy.getLevel() + " to cook this.");
            return false;
        }
        if (amount > player.getInventory().getAmount(node)) {
            amount = player.getInventory().getAmount(node);
        }
        if (amount < 1) {
            return false;
        }
        animate();
        return true;
    }

    @Override
    public void animate() {
        player.animate(ANIMATION);
    }

    @Override
    public boolean reward() {
        amount--;
        for (Item input : dairy.getInputs()) {
            if (player.getInventory().remove(input)) {
                player.getInventory().add(dairy.getProduct());
                if (input.getId() == Items.BUCKET_OF_MILK_1927) {
                    if (!player.getInventory().add(BUCKET)) {
                        GroundItemManager.create(BUCKET, player);
                    }
                }
                player.dispatch(new ResourceProducedEvent(dairy.getProduct().getId(), amount, node, BUCKET_OF_MILK.getId()));
                player.getPacketDispatch().sendMessage("You make " + (StringUtils.isPlusN(dairy.getProduct().getName().toLowerCase()) ? "an" : "a") + " " + dairy.getProduct().getName().toLowerCase() + ".");
                player.getSkills().addExperience(Skills.COOKING, dairy.getExperience(), true);
                break;
            }
        }

        return amount < 1;
    }

}
