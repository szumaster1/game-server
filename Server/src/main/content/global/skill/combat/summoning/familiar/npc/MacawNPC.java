package content.global.skill.combat.summoning.familiar.npc;

import content.global.skill.combat.summoning.familiar.Familiar;
import content.global.skill.combat.summoning.familiar.FamiliarSpecial;
import content.global.skill.combat.summoning.familiar.Forager;
import core.game.node.entity.player.Player;
import core.game.node.item.GroundItemManager;
import core.game.node.item.Item;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.utilities.RandomFunction;

/**
 * The Macaw npc.
 */
@Initializable
public class MacawNPC extends Forager {
    private static final Item[] HERBS = new Item[]{new Item(249), new Item(251), new Item(253), new Item(255), new Item(257), new Item(2998), new Item(12172), new Item(259), new Item(261), new Item(263), new Item(3000), new Item(265), new Item(2481), new Item(267), new Item(269)};
    private int specialDelay;

    /**
     * Instantiates a new Macaw npc.
     */
    public MacawNPC() {
        this(null, 6851);
    }

    /**
     * Instantiates a new Macaw npc.
     *
     * @param owner the owner
     * @param id    the id
     */
    public MacawNPC(Player owner, int id) {
        super(owner, id, 3100, 12071, 12, HERBS);
    }

    @Override
    public Familiar construct(Player owner, int id) {
        return new MacawNPC(owner, id);
    }

    @Override
    public Plugin<Object> newInstance(Object object) throws Throwable {
        return super.newInstance(object);
    }

    @Override
    public int[] getIds() {
        return new int[]{6851, 6852};
    }

    @Override
    protected boolean specialMove(FamiliarSpecial special) {
        if (specialDelay > GameWorld.getTicks()) {
            owner.getPacketDispatch().sendMessage("You must wait one minute until using the macaws special again.");
            return false;
        }
        final Item herb = HERBS[RandomFunction.random(HERBS.length)];
        animate(Animation.create(8013));
        graphics(Graphic.create(1321), 2);
        GameWorld.getPulser().submit(new Pulse(5, owner) {
            @Override
            public boolean pulse() {
                GroundItemManager.create(herb, getLocation(), owner);
                return true;
            }
        });
        specialDelay = GameWorld.getTicks() + 100;
        return true;
    }
}
