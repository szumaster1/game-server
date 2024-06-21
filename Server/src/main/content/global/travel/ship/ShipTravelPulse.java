package content.global.travel.ship;

import core.game.component.Component;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.diary.DiaryType;
import core.game.system.task.Pulse;
import core.network.packet.PacketRepository;
import core.network.packet.context.MinimapStateContext;
import core.network.packet.outgoing.MinimapState;

import static core.api.ContentAPIKt.setVarp;

/**
 * The Ship travel pulse.
 */
public final class ShipTravelPulse extends Pulse {

    private final Player player;

    private final Ships ship;

    private int counter = 0;

    /**
     * Instantiates a new Ship travel pulse.
     *
     * @param player the player
     * @param ship   the ship
     */
    public ShipTravelPulse(Player player, Ships ship) {
        super(1);
        this.player = player;
        this.ship = ship;
    }

    @Override
    public boolean pulse() {
        switch (counter++) {
            case 0:
                prepare();
                break;
            case 1:
                if (ship != Ships.PORT_SARIM_TO_CRANDOR) {
                    player.getProperties().setTeleportLocation(ship.getLocation());
                }
                break;
            default:
                if (counter == ship.getDelay()) {
                    arrive();
                    return true;
                }
                break;
        }
        return false;
    }

    private void arrive() {
        player.unlock();
        setVarp(player, 75, 0);
        player.getInterfaceManager().close();
        PacketRepository.send(MinimapState.class, new MinimapStateContext(player, 0));
        if (!ship.getName().equals("Crandor")) {
            player.getDialogueInterpreter().sendDialogue("The ship arrives at " + ship.getName() + ".");
            player.getInterfaceManager().close();
        } else {
            player.getInterfaceManager().open(new Component(317));
            PacketRepository.send(MinimapState.class, new MinimapStateContext(player, 2));
            player.getInterfaceManager().openOverlay(new Component(544));
            player.getInterfaceManager().open(new Component(317));
        }

        if (ship == Ships.KARAMJAMA_TO_PORT_SARIM) {
            player.getAchievementDiaryManager().finishTask(player, DiaryType.KARAMJA, 0, 3);
        }
        if (ship == Ships.BRIMHAVEN_TO_ARDOUGNE) {
            player.getAchievementDiaryManager().finishTask(player, DiaryType.KARAMJA, 0, 4);
        }
        if (ship == Ships.CAIRN_ISLAND_TO_PORT_KHAZARD) {
            player.getAchievementDiaryManager().finishTask(player, DiaryType.KARAMJA, 1, 6);
        }
    }

    private void prepare() {
        player.lock(ship.getDelay() + 1);
        player.getInterfaceManager().open(new Component(299));
        PacketRepository.send(MinimapState.class, new MinimapStateContext(player, 2));
        setVarp(player, 75, ship.getConfig());
    }
}
