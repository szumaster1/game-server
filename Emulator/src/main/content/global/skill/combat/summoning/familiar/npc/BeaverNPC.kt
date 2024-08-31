package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import content.global.skill.combat.summoning.familiar.Forager
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillBonus
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Direction
import core.game.world.map.path.Pathfinder
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.tools.RandomFunction
import kotlin.math.floor

/**
 * Beaver familiar.
 */
@Initializable
class BeaverNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 6808) :
    Forager(owner, id, 2700, 12021, 6, *ITEMS) {
    private var multiChop = false

    init {
        boosts.add(SkillBonus(Skills.WOODCUTTING, 2.0))
    }

    override fun construct(owner: Player, id: Int): Familiar {
        return BeaverNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        val `object` = special.node as Scenery
        if (!isTree(`object`.name)) {
            owner.packetDispatch.sendMessages(
                "This scroll only works on naturally growing, oak, willow, arctic pine",
                "teak, mahogany, maple, yew, and magic trees."
            )
            return false
        }
        if (owner.inventory.freeSlots() == 0) {
            return false
        }
        if (`object`.location.getDistance(getLocation()) > 5) {
            owner.packetDispatch.sendMessages(
                "The beaver is a little too far from the tree for the scroll to work - stand",
                "closer."
            )
            return false
        }
        val dir = Direction.getLogicalDirection(getLocation(), `object`.location)
        Pathfinder.find(getLocation(), `object`.location.transform(dir)).walk(this)
        val ticks = 2 + floor(owner.location.getDistance(`object`.location.transform(dir)) * 0.5)
            .toInt()
        owner.lock(ticks)
        multiChop = true
        pulseManager.clear()
        Pulser.submit(object : Pulse(ticks, owner, this) {
            override fun pulse(): Boolean {
                lock(11)
                owner.lock(11)
                faceLocation(`object`.location)
                animate(Animation.create(7722))
                Pulser.submit(object : Pulse(1, owner, this@BeaverNPC) {
                    var counter: Int = 0
                    var recieved: Boolean = false

                    override fun pulse(): Boolean {
                        when (++counter) {
                            11 -> {
                                if (!recieved) {
                                    owner.inventory.add(ITEMS[RandomFunction.random(ITEMS.size)], owner)
                                }
                                multiChop = false
                                return true
                            }

                            else -> if (counter > 3) {
                                if (RandomFunction.random(12) < 4) {
                                    owner.inventory.add(ITEMS[RandomFunction.random(ITEMS.size)], owner)
                                    recieved = true
                                }
                            }
                        }
                        return false
                    }
                })
                return true
            }
        })
        return true
    }

    override fun startFollowing() {
        if (multiChop) {
            return
        }
        super.startFollowing()
    }


    private fun isTree(name: String): Boolean {
        for (s in TREE_NAMES) {
            if (s == name) {
                return true
            }
        }
        return false
    }

    override fun getIds(): IntArray {
        return intArrayOf(6808)
    }

    companion object {
        private val ITEMS = arrayOf(
            Item(1511),
            Item(2862),
            Item(1521),
            Item(1519),
            Item(6333),
            Item(10810),
            Item(1517),
            Item(6332),
            Item(12581),
            Item(960),
            Item(8778)
        )
        private val TREE_NAMES = arrayOf(
            "Tree",
            "Oak",
            "Hollow",
            "Willow",
            "Arctic pine",
            "Eucalyptus",
            "Maple",
            "Yew",
            "Magic",
            "Cursed magic"
        )
    }
}
