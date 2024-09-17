package content.global.skill.gathering.hunter

import content.global.skill.gathering.hunter.NetTrapSetting.NetTrap
import content.global.skill.gathering.hunter.Traps.Companion.forNode
import content.global.skill.gathering.hunter.bnet.BNetTypes
import content.global.skill.gathering.hunter.bnet.ImplingNode
import content.global.skill.gathering.hunter.falconry.FalconryActivityPlugin
import core.cache.def.Definition
import core.cache.def.impl.ItemDefinition
import core.cache.def.impl.NPCDefinition
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.NodeUsageEvent
import core.game.interaction.OptionHandler
import core.game.interaction.UseWithHandler
import core.game.node.Node
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.item.GroundItem
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.PluginManager.definePlugin
import core.plugin.Initializable
import core.plugin.Plugin
import java.util.*

/**
 * Handles the hunter skill.
 * @author Vexia
 */
@Initializable
class HunterPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        var definition: Definition<*>? = null
        for (trap in Traps.values()) {
            for (nodeId in trap.settings.nodeIds) {
                definition =
                    if (trap.settings.isObjectTrap) SceneryDefinition.forId(nodeId) else ItemDefinition.forId(nodeId)
                definition!!.handlers["option:" + trap.settings.option] = this
            }
            if (trap.settings.failId != -1) {
                definition = SceneryDefinition.forId(trap.settings.failId)
                definition.getHandlers()["option:dismantle"] = this
                definition.getHandlers()["option:deactivate"] = this
            }
            for (objectId in trap.settings.objectIds) {
                definition = SceneryDefinition.forId(objectId)
                definition.getHandlers()["option:deactivate"] = this
                definition.getHandlers()["option:dismantle"] = this
                definition.getHandlers()["option:investigate"] = this
            }
            for (node in trap.nodes) {
                for (objectId in node.objectIds) {
                    definition = SceneryDefinition.forId(objectId)
                    definition.getHandlers()["option:check"] = this
                    definition.getHandlers()["option:retrieve"] = this
                }
            }
        }
        for (trap in NetTrap.values()) {
            SceneryDefinition.forId(trap.bent).handlers["option:dismantle"] = this
            SceneryDefinition.forId(trap.failed).handlers["option:dismantle"] = this
            SceneryDefinition.forId(trap.net).handlers["option:dismantle"] = this
            SceneryDefinition.forId(trap.caught).handlers["option:check"] = this
            SceneryDefinition.forId(trap.bent).handlers["option:investigate"] = this
            SceneryDefinition.forId(trap.failed).handlers["option:investigate"] = this
            SceneryDefinition.forId(trap.net).handlers["option:investigate"] = this
        }
        definePlugin(HunterNPC())
        definePlugin(HunterNetPlugin())
        definePlugin(HunterItemPlugin())
        definePlugin(FalconryActivityPlugin())
        definePlugin(HuntingItemUseWithHandler())
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val trap = forNode(node)
        when (option) {
            "lay", "activate", "set-trap", "trap" -> {
                trap!!.create(player, node)
                return true
            }

            "dismantle", "deactivate", "retrieve", "check" -> {
                trap!!.dismantle(player, (node as Scenery))
                return true
            }

            "investigate" -> {
                trap!!.investigate(player, node as Scenery)
                return true
            }
        }
        return true
    }

    override fun getDestination(node: Node, n: Node): Location? {
        if (n.name.startsWith("Bird")) {
            if (node.location == n.location) {
                return n.location.transform(node.direction, 1)
            }
        }
        return null
    }

    override fun isWalk(player: Player, node: Node): Boolean {
        return node is GroundItem || node !is Item
    }

    override fun isWalk(): Boolean {
        return false
    }

    /**
     * Handles the usage of an item on a trap.
     */
    class HuntingItemUseWithHandler : UseWithHandler(*ids) {

        override fun newInstance(arg: Any?): Plugin<Any> {
            for (trap in Traps.values()) {
                for (objectId in trap.settings.objectIds) {
                    addHandler(objectId, OBJECT_TYPE, this)
                }
            }
            return this
        }

        override fun handle(event: NodeUsageEvent): Boolean {
            val player = event.player
            val `object` = if (event.usedWith is Scenery) event.usedWith as Scenery else (event.used as Scenery)
            val item = event.usedItem
            if (HunterManager.getInstance(player).isOwner(`object`)) {
                player.sendMessage("This isn't your trap!")
                return true
            }
            val wrapper = HunterManager.getInstance(player).getWrapper(`object`)
            if (item.id == 594) {
                wrapper.smoke()
            } else {
                wrapper.bait(item)
            }
            return true
        }


        companion object {
            val ids: IntArray
                get() {
                    val list: MutableList<Int> = ArrayList(10)
                    for (trap in Traps.values()) {
                        for (id in trap.settings.baitIds) {
                            list.add(id)
                        }
                    }
                    list.add(594)
                    val array = IntArray(list.size)
                    for (i in array.indices) {
                        array[i] = list[i]
                    }
                    return array
                }
        }
    }

    /**
     * Handles a hunter item plugin.
     */
    class HunterItemPlugin : OptionHandler() {

        override fun newInstance(arg: Any?): Plugin<Any> {
            ItemDefinition.setOptionHandler("release", this)
            for (i in BNetTypes.BABY_IMPLING.ordinal - 1 until BNetTypes.values().size) {
                BNetTypes.values()[i].node.reward.definition.handlers["option:loot"] = this
            }
            return this
        }

        override fun handle(player: Player, node: Node, option: String): Boolean {
            when (option) {
                "release" -> {
                    val type = ReleaseType.forId(node.id)
                    type?.release(player, node as Item)
                }

                "loot" -> (BNetTypes.forItem(node as Item) as ImplingNode).loot(player, node)
            }
            return true
        }

        override fun isWalk(): Boolean {
            return false
        }


        /**
         * A release type.
         */
        enum class ReleaseType(vararg val ids: Int) {
            TRAPS(10033, 10034, 10092, 10146, 10147, 10148, 10149),
            BUTTERFLY(10020, 10018, 10016, 10014) {
                override fun release(player: Player, item: Item) {
                    val node = BNetTypes.forItem(item)
                    if (player.inventory.remove(item)) {
                        player.animate(Animation.create(5213))
                        player.inventory.add(Item(10012))
                        player.graphics(node!!.graphics[1])
                    }
                }
            };


            /**
             * Releases an item.
             *
             * @param player the player.
             * @param item the item.
             */
            open fun release(player: Player, item: Item) {
                val multiple = item.amount > 1
                player.inventory.remove(item)
                player.sendMessage("You release the " + item.name.lowercase(Locale.getDefault()) + (if (multiple) "s" else "") + " and " + (if (multiple) "they" else "it") + " bound" + (if (!multiple) "s" else "") + " away.")
            }


            companion object {
                /**
                 * Handles a release type.
                 *
                 * @param id the id.
                 * @return the type.
                 */
                @JvmStatic
                fun forId(id: Int): ReleaseType? {
                    for (type in values()) {
                        for (i in type.ids) {
                            if (i == id) {
                                return type
                            }
                        }
                    }
                    return null
                }
            }
        }
    }

    /**
     * Handles the catching of a hunter npc with a net.
     * @author Vexia
     */
    class HunterNetPlugin : OptionHandler() {

        override fun newInstance(arg: Any?): Plugin<Any> {
            for (type in BNetTypes.values()) {
                for (id in type.node.npcs) {
                    NPCDefinition.forId(id).handlers["option:catch"] = this
                }
            }
            return this
        }

        override fun handle(player: Player, node: Node, option: String): Boolean {
            val type = BNetTypes.forNpc(node as NPC)
            if (type == null) {
                player.sendMessage("There seems to be something wrong with this catch option.")
                player.sendMessage("Please submit a detailed bug report on gitlab.")
                return true
            }
            type.handle(player, node)
            return true
        }
    }
}
