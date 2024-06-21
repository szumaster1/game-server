package content.global.skill.production.runecrafting;

import core.game.node.entity.impl.Animator;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.diary.DiaryType;
import core.game.node.entity.skill.SkillPulse;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;

import static java.lang.Math.min;

/**
 * The Enchant tiara pulse.
 */
public class EnchantTiaraPulse extends SkillPulse<Item> {

    /**
     * Represents the Altar.
     */
    private final Altar altar;

    /**
     * Represents the tiara.
     */
    private final Tiara tiara;

    /**
     * Represents the item tiara.
     */
    private final Item TIARA = new Item(5525);

    private static final Animation ANIMATION = new Animation(791, Animator.Priority.HIGH);
    private static final Graphic GRAPHIC = new Graphic(186, 100);

    /**
     * Represents the amount.
     */
    private int amount;

    /**
     * Constructs a new {@code EnchantTiaraPulse.java} {@code Object}.
     *
     * @param player the player.
     * @param node   the node.
     * @param altar  the altar
     * @param tiara  the tiara
     * @param amount the amount
     */
    public EnchantTiaraPulse(Player player, Item node, final Altar altar, final Tiara tiara, final int amount) {
        super(player, node);
        this.tiara = tiara;
        this.amount = amount;
        this.altar = altar;
    }

    @Override
    public void start() {
        super.start();
        int tiaraAmt = player.getInventory().getAmount(TIARA);
        int talismanAmt = player.getInventory().getAmount(tiara.getTalisman().getTalisman()); // specific talisman being fused, "node" is all various talismans in inventory
        String talismanType = tiara.getTalisman().getTalisman().getName().toLowerCase();
        String altarType = altar.getRuin().name().toLowerCase();
        if (talismanType.contains(altarType)) {
            amount = min(talismanAmt, min(tiaraAmt, amount));
        }

    }

    @Override
    public boolean checkRequirements() {
        if (!player.getInventory().containsItem(TIARA)) {
            player.getPacketDispatch().sendMessage("You need a tiara.");
            return false;
        }
        return true;
    }

    @Override
    public void animate() {
        player.animate(ANIMATION);
        player.graphics(GRAPHIC);
    }

    @Override
    public boolean reward() {
        if (getDelay() == 1) {
            setDelay(2);
            return false;
        }
        if (player.getInventory().remove(TIARA) && player.getInventory().remove(tiara.getTalisman().getTalisman())) {
            player.getInventory().add(tiara.getTiara());
            player.getSkills().addExperience(Skills.RUNECRAFTING, tiara.getExperience(), true);

            if (tiara == Tiara.AIR) {
                player.getAchievementDiaryManager().finishTask(player, DiaryType.FALADOR, 0, 11);
            }
            if (tiara == Tiara.EARTH) {
                player.getAchievementDiaryManager().finishTask(player, DiaryType.VARROCK, 1, 11);
            }
        }
        amount--;
        return amount == 0;
    }

    @Override
    public void message(int type) {
        if (type == 1) {
            player.getPacketDispatch().sendMessage("You bind the power of the talisman into your tiara.");
        }
    }

}
