package content.region.desert.quest.icthlarinslittlehelper.dialogue;

import core.game.dialogue.Dialogue;
import core.game.node.entity.player.Player;
import core.plugin.Initializable;

/**
 * Wanderer ilh dialogue.
 */
@Initializable
public class WandererILHDialogue extends Dialogue {

    /**
     * Instantiates a new Wanderer ilh dialogue.
     */
    public WandererILHDialogue() {
    }

    /**
     * Instantiates a new Wanderer ilh dialogue.
     *
     * @param player the player
     */
    public WandererILHDialogue(Player player) {
        super(player);
    }

    @Override
    public Dialogue newInstance(Player player) {
        return new WandererILHDialogue(player);
    }

    @Override
    public boolean open(Object... args) {
        player("Good day, wanderer.");
        return true;
    }

    @Override
    public boolean handle(int interfaceId, int buttonId) {
        switch (stage) {
            case 0:
                npc("Hello to you too adventurer.");
                next();
                break;
            case 1:
                end();
                break;
        }
        return true;
    }

    @Override
    public int[] getIds() {
        return new int[]{};


    }
}
