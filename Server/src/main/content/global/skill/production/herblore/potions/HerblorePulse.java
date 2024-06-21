package content.global.skill.production.herblore.potions;

import content.data.consumables.Consumables;
import content.global.skill.skillcape.SkillcapePerks;
import core.api.consts.Sounds;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.SkillPulse;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.update.flag.context.Animation;
import core.utilities.RandomFunction;
import core.utilities.StringUtils;

import static core.api.ContentAPIKt.playAudio;

/**
 * The Herblore pulse.
 */
public final class HerblorePulse extends SkillPulse<Item> {

    /**
     * The constant VIAL_OF_WATER.
     */
    public static final Item VIAL_OF_WATER = new Item(227);

    /**
     * The constant COCONUT_MILK.
     */
    public static final Item COCONUT_MILK = new Item(5935);

    private static final Animation ANIMATION = new Animation(363);

    private final GenericPotion potion;

    private int amount;

    private final int initialAmount;

    private int cycles;

    /**
     * Instantiates a new Herblore pulse.
     *
     * @param player the player
     * @param node   the node
     * @param amount the amount
     * @param potion the potion
     */
    public HerblorePulse(final Player player, final Item node, final int amount, final GenericPotion potion) {
        super(player, node);
        this.amount = amount;
        this.initialAmount = amount;
        this.potion = potion;
    }

    @Override
    public boolean checkRequirements() {
        if (!player.getQuestRepository().isComplete("Druidic Ritual")) {
            player.getPacketDispatch().sendMessage("You must complete the Druidic Ritual quest before you can use Herblore.");
            return false;
        }
        if (player.getSkills().getLevel(Skills.HERBLORE) < potion.getLevel()) {
            player.getPacketDispatch().sendMessage("You need a Herblore level of at least " + potion.getLevel() + " in order to do this.");
            return false;
        }
        return player.getInventory().containsItem(potion.getBase()) && player.getInventory().containsItem(potion.getIngredient());
    }

    @Override
    public void animate() {
    }

    @Override
    public boolean reward() {
        if (potion.getBase().getId() == VIAL_OF_WATER.getId()) {
            if (initialAmount == 1 && getDelay() == 1) {
                player.animate(ANIMATION);
                setDelay(3);
                return false;
            }
            handleUnfinished();
        } else {
            if (initialAmount == 1 && getDelay() == 1) {
                player.animate(ANIMATION);
                setDelay(3);
                return false;
            }
            if (getDelay() == 1) {
                setDelay(3);
                player.animate(ANIMATION);
                return false;
            }
            handleFinished();
        }
        amount--;
        return amount == 0;
    }

    /**
     * Handle unfinished.
     */
    public void handleUnfinished() {
        if (cycles == 0) {
            player.animate(ANIMATION);
        }
        if ((player.getInventory().containsItem(potion.getBase()) && player.getInventory().containsItem(potion.getIngredient())) && player.getInventory().remove(potion.getBase(), potion.getIngredient())) {
            final Item item = potion.getProduct();
            player.getInventory().add(item);
            player.getPacketDispatch().sendMessage("You put the" + StringUtils.formatDisplayName(potion.getIngredient().getName().toLowerCase().replace("clean", "")) + " leaf into the vial of water.");
            playAudio(player, Sounds.GRIND_2608);
            if (cycles++ == 3) {
                player.animate(ANIMATION);
                cycles = 0;
            }
        }
    }

    /**
     * Handle finished.
     */
    public void handleFinished() {
        if ((player.getInventory().containsItem(potion.getBase()) && player.getInventory().containsItem(potion.getIngredient())) && player.getInventory().remove(potion.getBase(), potion.getIngredient())) {
            Item item = potion.getProduct();
            if (SkillcapePerks.isActive(SkillcapePerks.BREWMASTER, player)) {
                if (RandomFunction.random(100) < 15) {
                    Consumables consum = Consumables.getConsumableById(item.getId());
                    if (consum != null)
                        item = new Item(consum.consumable.getIds()[0], item.getAmount());
                    player.sendMessage("Due to your expertise, you manage to make an extra dose.");
                }
            }

            player.getInventory().add(item);
            player.getSkills().addExperience(Skills.HERBLORE, potion.getExperience(), true);
            player.getPacketDispatch().sendMessage("You mix the " + potion.getIngredient().getName().toLowerCase() + " into your potion.");
            playAudio(player, Sounds.GRIND_2608);
            player.animate(ANIMATION);
        }
    }
}
