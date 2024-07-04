package content.region.misthalin.quest.member.gertrudescat.npc;

import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.quest.Quest;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.plugin.Initializable;
import core.tools.RandomFunction;

/**
 * The Lumber kitten npc.
 */
@Initializable
public final class LumberKittenNPC extends AbstractNPC {

    private boolean hidden = true;

    private int nextSpeak;

    private int hideDelay;

    /**
     * Instantiates a new Lumber kitten npc.
     */
    public LumberKittenNPC() {
        super(0, null);
    }

    private LumberKittenNPC(int id, Location location) {
        super(id, location, false);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new LumberKittenNPC(id, location);
    }

    @Override
    public void init() {
        setWalks(false);
        super.init();
    }

    @Override
    public void tick() {
        if (nextSpeak < GameWorld.getTicks()) {
            hidden = false;
            nextSpeak = GameWorld.getTicks() + RandomFunction.random(10, 40);
            hideDelay = GameWorld.getTicks() + 4;
            sendChat("Mew!");
        }
        if (hideDelay < GameWorld.getTicks()) {
            hidden = true;
            int rand = RandomFunction.random(20, 40);
            hideDelay = GameWorld.getTicks() + rand;
            nextSpeak = GameWorld.getTicks() + rand;
        }
        super.tick();
    }

    @Override
    public boolean isHidden(final Player player) {
        Quest quest = player.getQuestRepository().getQuest("Gertrude's Cat");
        if (hidden) {
            return true;
        }
        return quest.getStage(player) < 20 || quest.getStage(player) > 50;
    }

    @Override
    public int[] getIds() {
        return new int[]{767};
    }

}
