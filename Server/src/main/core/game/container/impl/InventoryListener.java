package core.game.container.impl;

import content.global.skill.combat.summoning.SummoningPouch;
import core.game.container.Container;
import core.game.container.ContainerEvent;
import core.game.container.ContainerListener;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.network.packet.PacketRepository;
import core.network.packet.context.ContainerContext;
import core.network.packet.outgoing.ContainerPacket;

public final class InventoryListener implements ContainerListener {

    private final Player player;

    public InventoryListener(Player player) {
        this.player = player;
    }

    public void update(Container c) {
        player.getSettings().updateWeight();
        boolean hadPouch = player.getFamiliarManager().isHasPouch();
        boolean pouch = false;
        for (Item item : c.toArray()) {
            if (item != null && SummoningPouch.get(item.getId()) != null) {
                pouch = true;
                break;
            }
        }
        player.getFamiliarManager().setHasPouch(pouch);
        if (hadPouch != pouch) {
            player.getAppearance().sync();
        }
    }

    @Override
    public void refresh(Container c) {
        PacketRepository.send(ContainerPacket.class, new ContainerContext(player, 149, 0, 93, c, false));
        update(c);
    }

    @Override
    public void update(Container c, ContainerEvent event) {
        PacketRepository.send(ContainerPacket.class, new ContainerContext(player, 149, 0, 93, event.getItems(), false, event.getSlots()));
        update(c);
    }

}
