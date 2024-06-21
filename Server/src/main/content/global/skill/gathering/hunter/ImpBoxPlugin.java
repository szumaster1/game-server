package content.global.skill.gathering.hunter;

import core.cache.def.impl.ItemDefinition;
import core.game.component.Component;
import core.game.component.ComponentDefinition;
import core.game.component.ComponentPlugin;
import core.game.dialogue.Dialogue;
import core.game.dialogue.DialogueInterpreter;
import core.game.dialogue.FacialExpression;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.network.packet.PacketRepository;
import core.network.packet.context.ContainerContext;
import core.network.packet.outgoing.ContainerPacket;
import core.plugin.ClassScanner;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.utilities.RandomFunction;

/**
 * The Imp box plugin.
 */
@Initializable
public class ImpBoxPlugin extends OptionHandler {

    private static final int[] IDS = new int[]{10028, 10027};

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ClassScanner.definePlugin(new ImpInterfaceHandler(null));
        for (int id : IDS) {
            ItemDefinition.forId(id).getHandlers().put("option:bank", this);
            ItemDefinition.forId(id).getHandlers().put("option:talk-to", this);
        }
        ClassScanner.definePlugin(new ImpBoxDialogue());
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        switch (option) {
            case "bank":
                Component component = new Component(478);
                component.setPlugin(new ImpInterfaceHandler((Item) node));
                player.getInterfaceManager().open(component);
                PacketRepository.send(ContainerPacket.class, new ContainerContext(player, 478, 61, 91, player.getInventory(), true));
                break;
            case "talk-to":
                player.getDialogueInterpreter().open("imp-box");
                break;
        }
        return true;
    }

    @Override
    public boolean isWalk() {
        return false;
    }

    /**
     * The Imp box dialogue.
     */
    public static class ImpBoxDialogue extends Dialogue {


        private static final String[] MESSAGES = {"Let me outa here!", "Errgghh..", "Well look who it is.", "What are you looking at?"};


        /**
         * Instantiates a new Imp box dialogue.
         */
        public ImpBoxDialogue() {

        }


        /**
         * Instantiates a new Imp box dialogue.
         *
         * @param player the player
         */
        public ImpBoxDialogue(Player player) {
            super(player);
        }

        @Override
        public Dialogue newInstance(Player player) {
            return new ImpBoxDialogue(player);
        }

        @Override
        public boolean open(Object... args) {
            interpreter.sendDialogues(708, FacialExpression.FURIOUS, MESSAGES[RandomFunction.getRandom(MESSAGES.length - 1)]);
            return true;
        }

        @Override
        public boolean handle(int interfaceId, int buttonId) {
            end();
            return true;
        }

        @Override
        public int[] getIds() {
            return new int[]{DialogueInterpreter.getDialogueKey("imp-box")};
        }

    }

    /**
     * The Imp interface handler.
     */
    public class ImpInterfaceHandler extends ComponentPlugin {


        private static final String FINISHING_MESSAGE = "The imp teleports away, taking the item to your bank account.";


        private Item box;


        /**
         * Instantiates a new Imp interface handler.
         *
         * @param box the box
         */
        public ImpInterfaceHandler(Item box) {
            this.box = box;
        }

        @Override
        public Plugin<Object> newInstance(Object arg) throws Throwable {
            ComponentDefinition.forId(478).setPlugin(this);
            return this;
        }

        @Override
        public boolean handle(Player player, Component component, int opcode, int button, int slot, int itemId) {
            Item item = player.getInventory().get(slot);
            if (item != null) {
                if (player.getBank().canAdd(item) && item.getId() != box.getId()) {
                    player.getDialogueInterpreter().close();
                    player.getInventory().remove(item);
                    player.getBank().add(item);
                    PacketRepository.send(ContainerPacket.class, new ContainerContext(player, 478, 61, 91, player.getInventory(), true));
                    if (box.getId() == IDS[1]) {
                        int boxSlot = player.getInventory().getSlot(box);
                        player.getInventory().replace((box = new Item(IDS[0])), boxSlot);
                    } else if (box.getId() == IDS[0]) {
                        int boxSlot = player.getInventory().getSlot(box);
                        player.getInventory().replace(new Item(10025), boxSlot);
                        player.getInterfaceManager().close(component);
                        player.sendMessage(FINISHING_MESSAGE);
                    }
                }
            } else {
                player.sendMessage("You cannot add this item to your bank.");
                return false;
            }
            return true;
        }
    }
}
