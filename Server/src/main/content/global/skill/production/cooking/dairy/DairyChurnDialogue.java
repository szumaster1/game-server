package content.global.skill.production.cooking.dairy;

import core.api.consts.Items;
import core.game.component.Component;
import core.game.dialogue.Dialogue;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.plugin.Initializable;

/**
 * The Dairy churn dialogue.
 */
@Initializable
public final class DairyChurnDialogue extends Dialogue {

    private static final Item[] ITEMS = new Item[]{
            new Item(Items.BUCKET_OF_MILK_1927, 1),
            new Item(Items.POT_OF_CREAM_2130, 1),
            new Item(Items.PAT_OF_BUTTER_6697, 1)
    };

    /**
     * Instantiates a new Dairy churn dialogue.
     */
    public DairyChurnDialogue() {
    }

    /**
     * Instantiates a new Dairy churn dialogue.
     *
     * @param player the player
     */
    public DairyChurnDialogue(Player player) {
        super(player);
    }

    @Override
    public Dialogue newInstance(Player player) {
        return new DairyChurnDialogue(player);
    }

    @Override
    public boolean open(Object... args) {
        player.getInterfaceManager().openChatbox(new Component(74));
        return true;
    }

    @Override
    public boolean handle(int interfaceId, int buttonId) {
        DairyProduct product = null;
        int amount = 0;
        switch (buttonId) {
            case 6:
                product = DairyProduct.POT_OF_CREAM;
                amount = 1;
                break;
            case 5:
                product = DairyProduct.POT_OF_CREAM;
                amount = 5;
                break;
            case 4:
                product = DairyProduct.POT_OF_CREAM;
                amount = 10;
                break;
            case 9:
                product = DairyProduct.PAT_OF_BUTTER;
                amount = 1;
                break;
            case 8:
                product = DairyProduct.PAT_OF_BUTTER;
                amount = 5;
                break;
            case 7:
                product = DairyProduct.PAT_OF_BUTTER;
                amount = 10;
                break;
            case 12:
                product = DairyProduct.CHEESE;
                amount = 1;
                break;
            case 11:
                product = DairyProduct.CHEESE;
                amount = 5;
                break;
            case 10:
                product = DairyProduct.CHEESE;
                amount = 10;
                break;
        }
        player.getPulseManager().run(new DairyChurnPulse(player, ITEMS[0], product, amount));
        return true;
    }

    @Override
    public int[] getIds() {
        return new int[]{984374};
    }

}
