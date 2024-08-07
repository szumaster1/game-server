package content.global.skill.support.construction.decoration.workshop;

import core.api.consts.Items;
import core.cache.def.impl.ItemDefinition;
import core.cache.def.impl.SceneryDefinition;
import core.game.dialogue.Dialogue;
import core.game.dialogue.DialogueInterpreter;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.plugin.ClassScanner;
import core.plugin.Initializable;
import core.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Tool store plugin.
 */
@Initializable
public class ToolStorePlugin extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        for (ToolStore t : ToolStore.values()) {
            SceneryDefinition.forId(t.objectId).getHandlers().put("option:search", this);
        }
        ClassScanner.definePlugin(new ToolDialogue());
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        Scenery object = node.asScenery();
        ToolStore ts = ToolStore.forId(object.getId());
        if (ts != null) {
            player.getDialogueInterpreter().open(DialogueInterpreter.getDialogueKey("con:tools"), ts);
        }
        return true;
    }

    private enum ToolStore {
        /**
         * Toolstore 1 tool store.
         */
        TOOLSTORE_1(13699,
                Items.SAW_8794,
                Items.CHISEL_1755,
                Items.HAMMER_2347,
                Items.SHEARS_1735
        ),
        /**
         * Toolstore 2 tool store.
         */
        TOOLSTORE_2(13700,
                Items.BUCKET_1925,
                Items.SPADE_952,
                Items.TINDERBOX_590
        ),
        /**
         * Toolstore 3 tool store.
         */
        TOOLSTORE_3(13701,
                Items.BROWN_APRON_1757,
                Items.GLASSBLOWING_PIPE_1785,
                Items.NEEDLE_1733
        ),
        /**
         * Toolstore 4 tool store.
         */
        TOOLSTORE_4(13702,
                Items.AMULET_MOULD_1595,
                Items.NECKLACE_MOULD_1597,
                Items.RING_MOULD_1592,
                Items.HOLY_MOULD_1599,
                Items.TIARA_MOULD_5523
        ),
        /**
         * Toolstore 5 tool store.
         */
        TOOLSTORE_5(13703,
                Items.RAKE_5341,
                Items.SPADE_952,
                Items.TROWEL_676,
                Items.SEED_DIBBER_5343,
                Items.WATERING_CAN_5331
        );
        private final int objectId;
        private final int[] tools;

        ToolStore(int objectId, int... tools) {
            this.objectId = objectId;
            this.tools = tools;
        }

        private static ToolStore forId(int objectId) {
            for (ToolStore t : ToolStore.values()) {
                if (t.objectId == objectId) {
                    return t;
                }
            }
            return null;
        }
    }

    private final class ToolDialogue extends Dialogue {
        private ToolStore toolStore;
        private ToolDialogue() {
        }

        /**
         * Instantiates a new Tool dialogue.
         *
         * @param player the player
         */
        public ToolDialogue(Player player) {
            super(player);
        }

        @Override
        public Dialogue newInstance(Player player) {
            return new ToolDialogue(player);
        }

        @Override
        public boolean open(Object... args) {
            toolStore = (ToolStore) args[0];
            List<String> itemNames = new ArrayList<String>();
            for (int itemId : toolStore.tools) {
                ItemDefinition n = ItemDefinition.forId(itemId);
                itemNames.add(n.getName());
            }
            interpreter.sendOptions("Select a Tool", itemNames.toArray(new String[itemNames.size()]));
            stage = 1;
            return true;
        }

        @Override
        public boolean handle(int interfaceId, int buttonId) {
            switch (stage) {
                case 1:
                    Item item = new Item(toolStore.tools[buttonId - 1], 1);
                    if (player.getInventory().freeSlots() <= 0) {
                        interpreter.sendDialogue("You have no space in your inventory.");
                        stage = 2;
                        return true;
                    }
                    player.getInventory().add(item);
                    end();
                    return true;
                case 2:
                    end();
                    return true;
            }
            return false;
        }

        @Override
        public int[] getIds() {
            return new int[]{DialogueInterpreter.getDialogueKey("con:tools")};
        }

    }

}
