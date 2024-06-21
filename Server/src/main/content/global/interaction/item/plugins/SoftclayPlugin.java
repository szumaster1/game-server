package content.global.interaction.item.plugins;

import core.api.consts.Items;
import core.game.dialogue.SkillDialogueHandler;
import core.game.dialogue.SkillDialogueHandler.SkillDialogue;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.UseWithHandler;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.system.task.Pulse;
import core.plugin.Initializable;
import core.plugin.Plugin;

import java.util.Objects;

/**
 * The Softclay plugin.
 */
@Initializable
public final class SoftclayPlugin extends UseWithHandler {

    private static final Item CLAY = new Item(Items.CLAY_434);

    private static final Item SOFT_CLAY = new Item(Items.SOFT_CLAY_1761);

    private static final Item BOWL_OF_WATER = new Item(Items.BOWL_OF_WATER_1921);

    private static final Item BOWL = new Item(Items.BOWL_1923);

    private static final Item BUCKET = new Item(Items.BUCKET_1925);

    private static final Item BUCKET_OF_WATER = new Item(Items.BUCKET_OF_WATER_1929);

    private static final Item JUG = new Item(Items.JUG_1935);

    private static final Item JUG_OF_WATER = new Item(Items.JUG_OF_WATER_1937);

    /**
     * Instantiates a new Softclay plugin.
     */
    public SoftclayPlugin() {
        super(434);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        addHandler(1921, ITEM_TYPE, this);
        addHandler(1929, ITEM_TYPE, this);
        addHandler(1937, ITEM_TYPE, this);
        return this;
    }

    @Override
    public boolean handle(final NodeUsageEvent event) {
        final Player player = event.getPlayer();
        SkillDialogueHandler handler = new SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, SOFT_CLAY) {
            @Override
            public void create(final int amount, int index) {
                player.getPulseManager().run(new Pulse(2, player) {
                    int count;

                    @Override
                    public boolean pulse() {
                        if (!SoftclayPlugin.this.create(player, event)) {
                            return true;
                        }
                        return ++count >= amount;
                    }
                });
            }

            @Override
            public int getAll(int index) {
                return player.getInventory().getAmount(CLAY);
            }
        };
        if (player.getInventory().getAmount(CLAY) == 1) {
            create(player, event);
        } else {
            handler.open();
        }
        return true;
    }

    private boolean create(final Player player, NodeUsageEvent event) {
        Item removeItem = null;
        Item returnItem = null;
        if (event.getUsedItem().getId() == Items.BUCKET_OF_WATER_1929 || event.getBaseItem().getId() == Items.BUCKET_OF_WATER_1929) {
            removeItem = BUCKET_OF_WATER;
            returnItem = BUCKET;
        }
        if (event.getUsedItem().getId() == Items.BOWL_OF_WATER_1921 || event.getBaseItem().getId() == Items.BOWL_OF_WATER_1921) {
            removeItem = BOWL_OF_WATER;
            returnItem = BOWL;
        }
        if (event.getUsedItem().getId() == Items.JUG_OF_WATER_1937 || event.getBaseItem().getId() == Items.JUG_OF_WATER_1937) {
            removeItem = JUG_OF_WATER;
            returnItem = JUG;
        }

        if (player.getInventory().containsItem(CLAY) && player.getInventory().containsItem(Objects.requireNonNull(removeItem))) {
            player.getInventory().remove(removeItem);
            player.getInventory().remove(CLAY);
            player.getPacketDispatch().sendMessage("You mix the clay and water. You now have some soft, workable clay.");
            player.getInventory().add(SOFT_CLAY);
            player.getInventory().add(returnItem);
            return true;
        } else {
            return false;
        }
    }
}