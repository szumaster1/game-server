package core.game.dialogue;

import core.game.node.entity.player.Player;

public interface DialogueAction {

    public void handle(Player player, int buttonId);

}
