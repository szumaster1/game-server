package content.global.skill.support.construction;

import core.game.dialogue.Dialogue;
import core.game.dialogue.DialogueInterpreter;
import core.game.node.entity.player.Player;
import core.plugin.Initializable;

import java.util.Locale;

@Initializable
public final class RemovalDialogue extends Dialogue {

    private int[] pos;

    private int plane;

    private Room room;

    public RemovalDialogue() {
        super();
    }

    public RemovalDialogue(Player player) {
        super(player);
    }

    @Override
    public Dialogue newInstance(Player player) {
        return new RemovalDialogue(player);
    }

    @Override
    public boolean open(Object... args) {
        pos = (int[]) args[1];
        plane = player.getLocation().getZ();
        if (HouseManager.isInDungeon(player)) {
            plane = 3;
        }
        room = player.getHouseManager().getRooms()[plane][pos[0]][pos[1]];
        player.getDialogueInterpreter().sendOptions("Remove the " + (room != null ? room.getProperties().getName().toLowerCase(Locale.ROOT) : "room") + "?", "Yes", "No");
        stage = 0;
        return true;
    }

    @Override
    public boolean handle(int interfaceId, int buttonId) {
        if (stage == 0) {
            if (buttonId == 1) {
                if (plane == 0 && player.getHouseManager().hasRoomAt(1, pos[0], pos[1])) {
                    interpreter.sendPlainMessage(false, "You can't remove a room supporting another room.");
                    stage = 1;
                    return true;
                }
                if (room != null && room.getProperties().isLand()) {
                    Hotspot h = room.getHotspots()[0];
                    if (h != null && h.getDecorationIndex() == 0 && player.getHouseManager().getPortalAmount() <= 1) {
                        interpreter.sendPlainMessage(false, "You can't remove the garden with your portal in it.");
                        stage = 1;
                        return true;
                    }
                }
                player.getHouseManager().getRooms()[plane][pos[0]][pos[1]] = null;
                for (int i = plane; i < 3; i++) {
                    Room r = player.getHouseManager().getRooms()[i][pos[0]][pos[1]];
                    if (r != null && r.getProperties().isRoof()) {
                        player.getHouseManager().getRooms()[i][pos[0]][pos[1]] = null;
                    }
                }
                player.getHouseManager().reload(player, true);
            }
        }
        end();
        return true;
    }

    @Override
    public int[] getIds() {
        return new int[]{DialogueInterpreter.getDialogueKey("con:remove")};
    }

}
