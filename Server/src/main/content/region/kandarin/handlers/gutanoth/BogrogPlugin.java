package content.region.kandarin.handlers.gutanoth;

import core.cache.def.impl.NPCDefinition;
import core.game.dialogue.Dialogue;
import core.game.dialogue.FacialExpression;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.plugin.ClassScanner;
import core.plugin.Initializable;
import core.plugin.Plugin;
import kotlin.Unit;

import static core.api.ContentAPIKt.sendItemSelect;

/**
 * The Bogrog plugin.
 */
@Initializable
public final class BogrogPlugin extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        NPCDefinition.forId(4472).getHandlers().put("option:swap", this);
        ClassScanner.definePlugin(new BogrogDialogue());
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        switch (option) {
            case "swap":
                openSwap(player);
                break;
        }
        return true;
    }

    private void openSwap(Player player) {
        if (player.getSkills().getStaticLevel(Skills.SUMMONING) < 21) {
            player.sendMessage("You need a Summoning level of at least 21 in order to do that.");
        } else {
            sendItemSelect(player, new String[]{"Value", "Swap 1", "Swap 5", "Swap 10", "Swap X"}, true, (slot, index) -> {
                BogrogPouchSwapper.handle(player, index, slot);
                return Unit.INSTANCE;
            });
        }
    }

    /**
     * The Bogrog dialogue.
     */
    public final class BogrogDialogue extends Dialogue {

        public BogrogDialogue() {

        }

        public BogrogDialogue(Player player) {
            super(player);
        }

        @Override
        public Dialogue newInstance(Player player) {
            return new BogrogDialogue(player);
        }

        @Override
        public boolean open(Object... args) {
            npc = (NPC) args[0];
            npc(FacialExpression.CHILD_NORMAL,"Hey, yooman, what you wanting?");
            return true;
        }

        @Override
        public boolean handle(int interfaceId, int buttonId) {
            switch (stage) {
                case 0:
                    options("Can I buy some summoning supplies?", "Are you interested in buying pouch pouches or scrolls?");
                    stage++;
                    break;
                case 1:
                    switch (buttonId) {
                        case 1:
                            player("Can I buy some summoning supplies?");
                            stage = 10;
                            break;
                        case 2:
                            player("Are you interested in buying pouch pouches or scrolls?");
                            stage = 20;
                            break;
                    }
                    break;
                case 10:
                    npc(FacialExpression.CHILD_NORMAL,"Hur, hur, hur! Yooman's gotta buy lotsa stuff if yooman", "wants ta train good!");
                    stage++;
                    break;
                case 11:
                    npc.openShop(player);
                    end();
                    break;
                case 20:
                    npc(FacialExpression.CHILD_NORMAL,"Des other ogre's stealin' Bogrog's stock. Gimmie pouches", "and scrolls and yooman gets da shardies.");
                    stage++;
                    break;
                case 21:
                    player("Ok.");
                    stage++;
                    break;
                case 22:
                    openSwap(player);
                    end();
                    break;
            }
            return true;
        }

        @Override
        public int[] getIds() {
            return new int[]{4472};
        }
    }
}
