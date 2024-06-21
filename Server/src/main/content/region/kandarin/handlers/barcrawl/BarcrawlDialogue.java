package content.region.kandarin.handlers.barcrawl;

import core.game.dialogue.Dialogue;
import core.game.dialogue.DialogueInterpreter;
import core.game.node.entity.player.Player;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.plugin.Initializable;

/**
 * The Barcrawl dialogue.
 */
@Initializable
public final class BarcrawlDialogue extends Dialogue {

    private BarcrawlType type;

    private int npcId;


    /**
     * Instantiates a new Barcrawl dialogue.
     *
     * @param player the player
     */
    public BarcrawlDialogue(final Player player) {
        super(player);
    }


    /**
     * Instantiates a new Barcrawl dialogue.
     */
    public BarcrawlDialogue() {

    }

    @Override
    public Dialogue newInstance(Player player) {
        return new BarcrawlDialogue(player);
    }

    @Override
    public boolean open(Object... args) {
        npcId = (Integer) args[0];
        type = (BarcrawlType) args[1];
        player("I'm doing Alfred Grimhand's Barcrawl.");
        return true;
    }

    @Override
    public boolean handle(int interfaceId, int buttonId) {
        switch (stage) {
            case 0:
                interpreter.sendDialogues(npcId, null, type.getDialogue()[0]);
                stage = type.getDialogue().length > 1 ? 1 : 2;
                break;
            case 1:
                interpreter.sendDialogues(npcId, null, type.getDialogue()[1]);
                stage++;
                break;
            case 2:
                end();
                if (!player.getInventory().containsItem(type.getCoins())) {
                    break;
                }
                type.message(player, true);
                player.getInventory().remove(type.getCoins());
                BarcrawlManager.getInstance(player).complete(type.ordinal());
                player.lock(6);
                GameWorld.getPulser().submit(new Pulse(6, player) {
                    @Override
                    public boolean pulse() {
                        type.message(player, false);
                        type.effect(player);
                        return true;
                    }
                });
                break;
        }
        return true;
    }

    @Override
    public int[] getIds() {
        return new int[]{DialogueInterpreter.getDialogueKey("barcrawl dialogue")};
    }

}