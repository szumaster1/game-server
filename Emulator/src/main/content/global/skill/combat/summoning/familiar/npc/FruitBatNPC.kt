package content.global.skill.combat.summoning.familiar.npc

import cfg.consts.Items
import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import content.global.skill.combat.summoning.familiar.Forager
import core.game.node.entity.player.Player
import core.game.node.item.GroundItemManager
import core.game.node.item.Item
import core.game.node.item.WeightedChanceItem
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.GameWorld.Pulser
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.tools.RandomFunction
import java.util.*

/**
 * Fruit bat npc.
 */
@Initializable
class FruitBatNPC
/**
 * Instantiates a new Fruit bat npc.
 *
 * @param owner the owner
 * @param id    the id
 */
/**
 * Instantiates a new Fruit bat npc.
 */
@JvmOverloads constructor(owner: Player? = null, id: Int = 6817) : Forager(owner, id, 4500, 12033, 6, *FRUIT_FORAGE) {
    override fun construct(owner: Player, id: Int): Familiar {
        return FruitBatNPC(owner, id)
    }

    override fun getIds(): IntArray {
        return intArrayOf(6817)
    }


    override fun specialMove(special: FamiliarSpecial): Boolean {
        if (owner.getAttribute("fruit-bat", 0) > GameWorld.ticks) {
            return false
        }

        val anyFruit = RandomFunction.random(10) <= 8
        val goodFruit = RandomFunction.random(100) <= 2
        val otherFruitAmount = if ((!goodFruit && RandomFunction.random(10) == 1)) RandomFunction.random(
            0,
            1
        ) else RandomFunction.random(0, if (goodFruit) 7 else 3)

        animate(Animation(8320))
        graphics(Graphic(1332, 200))
        animate(Animation(8321), 3) // TODO - this animates the fruit bat with the splattering fruit animation, should do it for all falling fruits but Items are not Entities and therefore cannot animate
        graphics(Graphic(1331), 4)
        owner.setAttribute("fruit-bat", GameWorld.ticks + 5)
        lock(4)
        Pulser.submit(object : Pulse(4, this) {
            override fun pulse(): Boolean {
                if (anyFruit) {
                    class Pair(val p1: Int, val p2: Int)

                    val coords: MutableList<Pair?> = LinkedList()
                    coords.add(Pair(-1, -1))
                    coords.add(Pair(-1, 0))
                    coords.add(Pair(-1, 1))
                    coords.add(Pair(0, -1))
                    coords.add(Pair(0, 1))
                    coords.add(Pair(1, -1))
                    coords.add(Pair(1, 0))
                    coords.add(Pair(1, 1))
                    Collections.shuffle(coords)

                    var coord = coords.removeAt(0)
                    GroundItemManager.create(
                        Item(Items.PAPAYA_FRUIT_5972),
                        owner.location.transform(coord!!.p1, coord.p2, 0),
                        owner
                    )

                    for (i in 0 until otherFruitAmount) {
                        val item = RandomFunction.rollWeightedChanceTable(*FRUIT_FALL)
                        if (item.id != 0) {
                            coord = coords.removeAt(0)
                            GroundItemManager.create(
                                item,
                                owner.location.transform(coord!!.p1, coord.p2, 0),
                                owner
                            )
                        }
                    }
                }
                return true
            }
        })
        return true
    }

    companion object {
        // The random fruit to forage.
        private val FRUIT_FORAGE = arrayOf<Item>(
            Item(Items.PAPAYA_FRUIT_5972),
            Item(Items.ORANGE_2108),
            Item(Items.PINEAPPLE_2114),
            Item(Items.LEMON_2102),
            Item(Items.LIME_2120),
            Item(Items.STRAWBERRY_5504),
            Item(Items.WATERMELON_5982),
            Item(Items.COCONUT_5974)
        )

        /*
         * The fruit for special move fruitfall EXCEPT the papaya
         * Sourced rates from various youtube videos and the RS wiki.
         * Note the RS wiki page does not go back to 2009 but there's no
         * indication that the rates have changed over time.
         */
        private val FRUIT_FALL = arrayOf<WeightedChanceItem>(
            WeightedChanceItem(Items.ORANGE_2108, 1, 4),
            WeightedChanceItem(Items.PINEAPPLE_2114, 1, 3),
            WeightedChanceItem(Items.LEMON_2102, 1, 2),
            WeightedChanceItem(Items.LIME_2120, 1, 2),
            WeightedChanceItem(Items.BANANA_1963, 1, 2),
            WeightedChanceItem(0, 1, 4)
        )
    }
}
