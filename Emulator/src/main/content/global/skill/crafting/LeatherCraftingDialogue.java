package content.global.skill.crafting;

import core.api.InputType;
import core.cache.def.impl.ItemDefinition;
import core.game.component.Component;
import core.game.dialogue.Dialogue;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.plugin.Initializable;
import kotlin.Unit;

import static core.api.ContentAPIKt.sendInputDialogue;

/**
 * Represents the leather craft dialogue.
 */
@Initializable
public final class LeatherCraftingDialogue extends Dialogue {

    private String type = "";
    private int leather;

    public LeatherCraftingDialogue() {
    }

    public LeatherCraftingDialogue(Player player) {
        super(player);
    }

    @Override
    public Dialogue newInstance(Player player) {
        return new LeatherCraftingDialogue(player);
    }

    @Override
    public boolean open(Object... args) {
        Component component = new Component(304);
        type = (String) args[0];
        if (type.equals("hard")) {
            player.getInterfaceManager().openChatbox(309);
            player.getPacketDispatch().sendItemZoomOnInterface(1131, 150, 309, 2);
            player.getPacketDispatch().sendString("<br><br><br><br>Hardleather body", 309, 6);
        } else {

            leather = (int) args[1];
            player.getInterfaceManager().openChatbox(component);
            int[] index = new int[3];
            if (leather == Leather.GREEN_LEATHER) {
                index[0] = Leather.DragonHide.GREEN_D_HIDE_BODY.getProduct();
                index[1] = Leather.DragonHide.GREEN_D_HIDE_VAMBS.getProduct();
                index[2] = Leather.DragonHide.GREEN_D_HIDE_CHAPS.getProduct();
            }
            if (leather == Leather.BLUE_LEATHER) {
                index[0] = Leather.DragonHide.BLUE_D_HIDE_BODY.getProduct();
                index[1] = Leather.DragonHide.BLUE_D_HIDE_VAMBS.getProduct();
                index[2] = Leather.DragonHide.BLUE_D_HIDE_CHAPS.getProduct();
            }
            if (leather == Leather.RED_LEATHER) {
                index[0] = Leather.DragonHide.RED_D_HIDE_BODY.getProduct();
                index[1] = Leather.DragonHide.RED_D_HIDE_VAMBS.getProduct();
                index[2] = Leather.DragonHide.RED_D_HIDE_CHAPS.getProduct();
            }
            if (leather == Leather.BLACK_LEATHER) {
                index[0] = Leather.DragonHide.BLACK_D_HIDE_BODY.getProduct();
                index[1] = Leather.DragonHide.BLACK_D_HIDE_VAMBS.getProduct();
                index[2] = Leather.DragonHide.BLACK_D_HIDE_CHAPS.getProduct();
            }
            player.getPacketDispatch().sendItemZoomOnInterface(index[0], 175, 304, 2);
            player.getPacketDispatch().sendItemZoomOnInterface(index[1], 175, 304, 3);
            player.getPacketDispatch().sendItemZoomOnInterface(index[2], 165, 304, 4);
            player.getPacketDispatch().sendString("<br><br><br><br>" + ItemDefinition.forId(index[0]).getName(), 304, 8);
            player.getPacketDispatch().sendString("<br><br><br><br>" + ItemDefinition.forId(index[1]).getName(), 304, 11);
            player.getPacketDispatch().sendString("<br><br><br><br>" + ItemDefinition.forId(index[2]).getName(), 304, 16);
        }
        return true;
    }

    @Override
    public boolean handle(int interfaceId, int buttonId) {
        player.getInterfaceManager().closeChatbox();
        int amt = 0;
        switch (type) {
            case "hard":
                switch (buttonId) {
                    case 6:
                        amt = 1;
                        break;
                    case 4:
                        amt = 5;
                        break;
                    case 3:
                        sendInputDialogue(player, true, "Enter the amount:", (value) -> {
                            player.getPulseManager().run(new HardLeatherCraftPulse(player, null, (int) value));
                            return Unit.INSTANCE;
                        });
                        return true;
                    case 2:
                        amt = player.getInventory().getAmount(new Item(Leather.HARD_LEATHER));
                        break;
                }
                player.getPulseManager().run(new HardLeatherCraftPulse(player, null, amt));
                break;
            case "dragon":
                int index = 0;
                if (buttonId > 3 && buttonId < 8) {
                    index = 1;
                }
                if (buttonId > 7 && buttonId < 12) {
                    index = 2;
                }
                if (buttonId > 11 && buttonId < 16) {
                    index = 3;
                }
                Leather.DragonHide hide = null;
                if (index == 1) {
                    switch (leather) {
                        case Leather.GREEN_LEATHER:
                            hide = Leather.DragonHide.GREEN_D_HIDE_BODY;
                            break;
                        case Leather.BLUE_LEATHER:
                            hide = Leather.DragonHide.BLUE_D_HIDE_BODY;
                            break;
                        case Leather.RED_LEATHER:
                            hide = Leather.DragonHide.RED_D_HIDE_BODY;
                            break;
                        case Leather.BLACK_LEATHER:
                            hide = Leather.DragonHide.BLACK_D_HIDE_BODY;
                            break;
                    }
                }
                if (index == 2) {
                    switch (leather) {
                        case Leather.GREEN_LEATHER:
                            hide = Leather.DragonHide.GREEN_D_HIDE_VAMBS;
                            break;
                        case Leather.BLUE_LEATHER:
                            hide = Leather.DragonHide.BLUE_D_HIDE_VAMBS;
                            break;
                        case Leather.RED_LEATHER:
                            hide = Leather.DragonHide.RED_D_HIDE_VAMBS;
                            break;
                        case Leather.BLACK_LEATHER:
                            hide = Leather.DragonHide.BLACK_D_HIDE_VAMBS;
                            break;
                    }
                }
                if (index == 3) {
                    switch (leather) {
                        case Leather.GREEN_LEATHER:
                            hide = Leather.DragonHide.GREEN_D_HIDE_CHAPS;
                            break;
                        case Leather.BLUE_LEATHER:
                            hide = Leather.DragonHide.BLUE_D_HIDE_CHAPS;
                            break;
                        case Leather.RED_LEATHER:
                            hide = Leather.DragonHide.RED_D_HIDE_CHAPS;
                            break;
                        case Leather.BLACK_LEATHER:
                            hide = Leather.DragonHide.BLACK_D_HIDE_CHAPS;
                            break;
                    }
                }
                switch (buttonId) {
                    case 7:
                    case 11:
                    case 15:
                        amt = 1;
                        break;
                    case 6:
                    case 10:
                    case 14:
                        amt = 5;
                        break;
                    case 5:
                    case 9:
                    case 13:
                        amt = 10;
                        break;
                    case 4:
                    case 8:
                    case 12:
                        final Leather.DragonHide hidee = hide;
                        if (hidee == null) {
                            return false;
                        }
                        sendInputDialogue(player, InputType.AMOUNT, "Enter the amount:", (value) -> {
                            player.getPulseManager().run(new DragonCraftPulse(player, null, hidee, (int) value));
                            return Unit.INSTANCE;
                        });
                        return true;
                }
                if (hide == null) {
                    return false;
                }
                player.getPulseManager().run(new DragonCraftPulse(player, null, hide, amt));
                break;
        }
        return true;
    }

    @Override
    public int[] getIds() {
        return new int[]{48923};
    }
}
