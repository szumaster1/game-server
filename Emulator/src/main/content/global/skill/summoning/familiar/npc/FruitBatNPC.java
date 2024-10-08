package content.global.skill.summoning.familiar.npc;

import content.global.skill.summoning.familiar.Familiar;
import content.global.skill.summoning.familiar.FamiliarSpecial;
import content.global.skill.summoning.familiar.Forager;
import core.game.node.entity.player.Player;
import core.game.node.item.GroundItemManager;
import core.game.node.item.Item;
import core.game.node.item.WeightedChanceItem;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.tools.RandomFunction;
import org.rs.consts.Items;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Fruit bat npc.
 */
@Initializable
public class FruitBatNPC extends Forager {

    // The random fruit to forage.
    private static final Item[] FRUIT_FORAGE = new Item[]{
        new Item(Items.PAPAYA_FRUIT_5972),
        new Item(Items.ORANGE_2108),
        new Item(Items.PINEAPPLE_2114),
        new Item(Items.LEMON_2102),
        new Item(Items.LIME_2120),
        new Item(Items.STRAWBERRY_5504),
        new Item(Items.WATERMELON_5982),
        new Item(Items.COCONUT_5974)
    };

    /*
     * The fruit for special move fruitfall EXCEPT the papaya
     * Sourced rates from various youtube videos and the RS wiki.
     * Note the RS wiki page does not go back to 2009 but there's no
     * indication that the rates have changed over time.
     */

    private static final WeightedChanceItem[] FRUIT_FALL = new WeightedChanceItem[]{
        new WeightedChanceItem(Items.ORANGE_2108, 1, 4),
        new WeightedChanceItem(Items.PINEAPPLE_2114, 1, 3),
        new WeightedChanceItem(Items.LEMON_2102, 1, 2),
        new WeightedChanceItem(Items.LIME_2120, 1, 2),
        new WeightedChanceItem(Items.BANANA_1963, 1, 2),
        new WeightedChanceItem(0, 1, 4)
    };

    /**
     * Instantiates a new Fruit bat npc.
     */
    public FruitBatNPC() {
        this(null, 6817);
    }

    /**
     * Instantiates a new Fruit bat npc.
     *
     * @param owner the owner
     * @param id    the id
     */
    public FruitBatNPC(Player owner, int id) {
        super(owner, id, 4500, 12033, 6, FRUIT_FORAGE);
    }

    @Override
    public Familiar construct(Player owner, int id) {
        return new FruitBatNPC(owner, id);
    }

    @Override
    public int[] getIds() {
        return new int[]{6817};
    }


    @Override
    protected boolean specialMove(FamiliarSpecial special) {
        if (owner.getAttribute("fruit-bat", 0) > GameWorld.getTicks()) {
            return false;
        }

        final boolean anyFruit = RandomFunction.random(10) <= 8;
        final boolean goodFruit = RandomFunction.random(100) <= 2;
        final int otherFruitAmount = (!goodFruit && RandomFunction.random(10) == 1) ? RandomFunction.random(0, 1) : RandomFunction.random(0, goodFruit ? 7 : 3);

        animate(new Animation(8320));
        graphics(new Graphic(1332, 200));
        animate(new Animation(8321), 3); // TODO - this animates the fruit bat with the splattering fruit animation, should do it for all falling fruits but Items are not Entities and therefore cannot animate
        graphics(new Graphic(1331), 4);
        owner.setAttribute("fruit-bat", GameWorld.getTicks() + 5);
        lock(4);
        GameWorld.getPulser().submit(new Pulse(4, this) {
            @Override
            public boolean pulse() {
                if (anyFruit) {

                    class Pair {
                        public final int p1;
                        public final int p2;

                        Pair(int p1, int p2) {
                            this.p1 = p1;
                            this.p2 = p2;
                        }
                    }
                    List<Pair> coords = new LinkedList<>();
                    coords.add(new Pair(-1, -1));
                    coords.add(new Pair(-1, 0));
                    coords.add(new Pair(-1, 1));
                    coords.add(new Pair(0, -1));
                    coords.add(new Pair(0, 1));
                    coords.add(new Pair(1, -1));
                    coords.add(new Pair(1, 0));
                    coords.add(new Pair(1, 1));
                    Collections.shuffle(coords);

                    Pair coord = coords.remove(0);
                    GroundItemManager.create(new Item(Items.PAPAYA_FRUIT_5972),
                        owner.getLocation().transform(coord.p1, coord.p2, 0),
                        owner);

                    for (int i = 0; i < otherFruitAmount; i++) {
                        Item item = RandomFunction.rollWeightedChanceTable(FRUIT_FALL);
                        if (item.getId() != 0) {
                            coord = coords.remove(0);
                            GroundItemManager.create(item,
                                owner.getLocation().transform(coord.p1, coord.p2, 0),
                                owner);
                        }
                    }
                }
                return true;
            }
        });
        return true;
    }

}
