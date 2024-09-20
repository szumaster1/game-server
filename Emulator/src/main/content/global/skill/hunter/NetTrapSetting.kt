package content.global.skill.hunter

import core.api.sendMessage
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.world.update.flag.context.Animation

/**
 * Handles the net trap.
 * @author Vexia
 */
class NetTrapSetting : content.global.skill.hunter.TrapSetting(
    intArrayOf(19652, 19663, 19671, 19679, 28564),
    arrayOf(Item(303), Item(954)),
    NetTrap.ids,
    intArrayOf(10142, 10143, 10144, 10145),
    "set-trap",
    29,
    -1,
    Animation(5215),
    Animation.create(5207),
    true
) {

    override fun hasItems(player: Player): Boolean {
        if (!super.hasItems(player)) {
            sendMessage(player, "You need a net and a rope to set a net trap.")
            return false
        }
        return true
    }

    override fun clear(wrapper: content.global.skill.hunter.TrapWrapper, type: Int): Boolean {
        if (super.clear(wrapper, type)) {
            if (wrapper.secondary != null && wrapper.secondary.isActive) {
                SceneryBuilder.remove(wrapper.secondary)
            }
            SceneryBuilder.add(wrapper.getObject().transform(wrapper.netType.original))
            return true
        }
        return false
    }

    override fun returnItems(`object`: Scenery, wrapper: content.global.skill.hunter.TrapWrapper, type: Int) {
        super.returnItems(`object`, wrapper, type)
        if (type == 0) {
            for (i in super.getItems()) {
                createGroundItem(i, `object`.location, wrapper.player)
            }
        }
    }

    override fun reward(player: Player, node: Node, wrapper: content.global.skill.hunter.TrapWrapper) {
        val `object` = wrapper.getObject()
        wrapper.netType = NetTrap.forId(node.id)
        var rotation = 0
        var increment = 0
        var x = false
        val netInfo = getNetInfo(player, node)
        rotation = netInfo[0] as Int
        increment = netInfo[1] as Int
        x = netInfo[2] as Boolean
        var secondary: Scenery? = Scenery(
            wrapper.netType.net,
            `object`.location.transform(if (x) increment else 0, if (!x) increment else 0, 0),
            rotation
        )
        secondary = SceneryBuilder.add(secondary)
        wrapper.secondary = secondary
        player.moveStep()
        wrapper.addItem(*items)
        player.inventory.remove(*wrapper.type.settings.items)
    }

    override fun handleCatch(counter: Int, wrapper: content.global.skill.hunter.TrapWrapper, node: TrapNode, npc: NPC, success: Boolean) {
        when (counter) {
            2 -> SceneryBuilder.remove(wrapper.secondary)
            3 -> {
                npc.moveStep()
                wrapper.setObject(wrapper.netType.failed)
            }
        }
    }

    override fun buildObject(player: Player, node: Node): Scenery {
        return (node as Scenery).transform(NetTrap.forId(node.getId())!!.bent)
    }

    override fun createHook(wrapper: content.global.skill.hunter.TrapWrapper): TrapHook {
        return TrapHook(wrapper, arrayOf(wrapper.secondary.location))
    }

    override fun getTransformId(wrapper: content.global.skill.hunter.TrapWrapper, node: TrapNode): Int {
        return wrapper.netType.catching
    }

    override fun getFinalId(wrapper: content.global.skill.hunter.TrapWrapper, node: TrapNode): Int {
        return wrapper.netType.caught
    }

    override fun getFailId(wrapper: content.global.skill.hunter.TrapWrapper, node: TrapNode): Int {
        return wrapper.netType.failing
    }

    override fun getTimeUpMessage(): String {
        return "The net trap that you constructed has collapsed."
    }

    private fun getNetInfo(player: Player, node: Node): Array<Any> {
        val rotation: Int
        val increment: Int
        var x = false
        if (player.location.x < node.location.x) {
            rotation = 3
            increment = -1
            x = true
        } else if (player.location.x > node.location.x) {
            rotation = 1
            increment = 1
            x = true
        } else if (player.location.y < node.location.y) {
            rotation = 2
            increment = -1
        } else {
            rotation = 0
            increment = 1
        }
        return arrayOf(rotation, increment, x)
    }

    enum class NetTrap(
        val original: Int,
        @JvmField val bent: Int,
        val failing: Int,
        @JvmField val failed: Int,
        val catching: Int,
        @JvmField val caught: Int,
        @JvmField val net: Int
    ) {
        /*
         * Green net trap.
         */
        GREEN(original = 19679, bent = 19678, failing = 19676, failed = 19677, catching = 19674, caught = 19675, net = 19651),

        /*
         * Squirel net trap.
         */
        SQUIREL(original = 28564, bent = 28563, failing = 28752, failed = 28753, catching = 28750, caught = 28751, net = 28566),

        /*
         * Orange net trap.
         */
        ORANGE(original = 19652, bent = 19650, failing = 19657, failed = 19656, catching = 19655, caught = 19654, net = 19665),

        /*
         * Red net trap.
         */
        RED(original = 19663, bent = 19662, failing = 19660, failed = 19661, catching = 19658, caught = 19659, net = 19673),

        /*
         * Black net trap.
         */
        BLACK(original = 19671, bent = 19670, failing = 19668, failed = 19669, catching = 19666, caught = 19667, net = 19681);

        companion object {
            @JvmStatic
            fun forId(id: Int): NetTrap? {
                for (trap in values()) {
                    if (trap.original == id) {
                        return trap
                    }
                }
                return null
            }

            val ids: IntArray
                get() {
                    val ids: MutableList<Int> = ArrayList(10)
                    for (trap in values()) {
                        ids.add(trap.bent)
                        ids.add(trap.caught)
                        ids.add(trap.net)
                        ids.add(trap.original)
                    }
                    val array = IntArray(ids.size)
                    for (i in array.indices) {
                        array[i] = ids[i]
                    }
                    return array
                }
        }
    }
}
