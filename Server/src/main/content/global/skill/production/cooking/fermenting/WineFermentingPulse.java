package content.global.skill.production.cooking.fermenting;

import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.system.task.Pulse;
import core.utilities.RandomFunction;

/**
 * The Wine fermenting pulse.
 */
public final class WineFermentingPulse extends Pulse {

    private final Player player;

    private int count;

    /**
     * Instantiates a new Wine fermenting pulse.
     *
     * @param delay  the delay
     * @param player the player
     */
    public WineFermentingPulse(int delay, final Player player) {
        super(delay);
        this.player = player;
    }

    @Override
    public boolean pulse() {
        if (count++ >= 16) {
            int rand = RandomFunction.random(1, 3);
            switch (rand) {
                case 1:
                    if (player.getInventory().contains(1995, 1)) {
                        player.getInventory().replace(new Item(1991, 1), player.getInventory().getSlot(new Item(1995, 1)));
                    } else if (player.getBank().contains(1995, 1)) {
                        player.getBank().replace(new Item(1991, 1), player.getBank().getSlot(new Item(1995, 1)));
                    }
                    return true;
                case 2:
                    if (player.getInventory().contains(1995, 1)) {
                        player.getInventory().replace(new Item(1993, 1), player.getInventory().getSlot(new Item(1995, 1)));
                        player.getSkills().addExperience(Skills.COOKING, 200, true);
                    } else if (player.getBank().contains(1995, 1)) {
                        player.getBank().replace(new Item(1993, 1), player.getBank().getSlot(new Item(1995, 1)));
                        player.getSkills().addExperience(Skills.COOKING, 200);
                    }
                    return true;
                case 3:
                    if (player.getInventory().contains(1995, 1)) {
                        player.getInventory().replace(new Item(1993, 1), player.getInventory().getSlot(new Item(1995, 1)));
                        player.getSkills().addExperience(Skills.COOKING, 200);
                    } else if (player.getBank().contains(1995, 1)) {
                        player.getBank().replace(new Item(1993, 1), player.getBank().getSlot(new Item(1995, 1)));
                        player.getSkills().addExperience(Skills.COOKING, 200);
                    }
                    return true;
            }
            return true;
        }
        count++;
        return false;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

}
