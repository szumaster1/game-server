package content.minigame.puropuro

import core.api.*
import core.cache.def.impl.ItemDefinition
import core.game.interaction.Option
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.GameWorld.ticks
import core.game.world.map.Location
import core.game.world.map.RegionManager.getObject
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBuilder
import core.game.world.map.zone.ZoneType
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin
import core.plugin.PluginManager.definePlugin
import core.tools.RandomFunction
import org.rs.consts.Components
import org.rs.consts.Items

/**
 * Puro-puro plugin.
 */
@Initializable
class ImpetuousImpulsesZone : MapZone("puro puro", true), Plugin<Any> {
    init {
        zoneType = ZoneType.SAFE.id
    }

    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any> {
        PULSE.stop()
        ZoneBuilder.configure(this)
        definePlugin(PuroOptionHandler())
        return this
    }

    override fun fireEvent(identifier: String, vararg args: Any): Any? {
        return null
    }

    override fun enter(e: Entity): Boolean {
        if (e is Player) {
            val p = e.asPlayer()
            setMinimapState(p, 2)
        }
        if (!PULSE.isRunning) {
            spawnWheat()
            PULSE.restart()
            PULSE.start()
            Pulser.submit(PULSE)
        }
        return super.enter(e)
    }

    override fun leave(e: Entity, logout: Boolean): Boolean {
        if (e is Player) {
            val p = e.asPlayer()
            if (!logout) {
                setMinimapState(p, 0)
                closeAllInterfaces(p)
            }
        }
        return super.leave(e, logout)
    }

    override fun interact(e: Entity, target: Node, option: Option): Boolean {
        return super.interact(e, target, option)
    }

    private fun spawnWheat() {
        for (set in WHEAT) {
            set.init()
        }
    }

    override fun configure() {
        registerRegion(10307)
        WHEAT.add(WheatSet(0, Location.create(2606, 4329, 0), Location.create(2606, 4328, 0)))
        WHEAT.add(WheatSet(1, Location.create(2596, 4331, 0), Location.create(2597, 4331, 0)))
        WHEAT.add(WheatSet(0, Location.create(2580, 4326, 0), Location.create(2580, 4325, 0)))
        WHEAT.add(WheatSet(1, Location.create(2595, 4308, 0), Location.create(2596, 4308, 0)))
        WHEAT.add(WheatSet(0, Location.create(2603, 4314, 0), Location.create(2603, 4313, 0)))
        WHEAT.add(WheatSet(1, Location.create(2599, 4305, 0), Location.create(2600, 4305, 0)))
        WHEAT.add(WheatSet(0, Location.create(2577, 4327, 0), Location.create(2577, 4328, 0)))
        WHEAT.add(WheatSet(1, Location.create(2587, 4334, 0), Location.create(2586, 4334, 0)))
        WHEAT.add(WheatSet(0, Location.create(2609, 4310, 0), Location.create(2609, 4309, 0)))
        WHEAT.add(WheatSet(1, Location.create(2586, 4302, 0), Location.create(2587, 4302, 0)))
        WHEAT.add(WheatSet(0, Location.create(2574, 4310, 0), Location.create(2574, 4311, 0)))
        WHEAT.add(WheatSet(1, Location.create(2582, 4337, 0), Location.create(2581, 4337, 0)))
        WHEAT.add(WheatSet(0, Location.create(2571, 4316, 0), Location.create(2571, 4315, 0)))
        WHEAT.add(WheatSet(1, Location.create(2601, 4340, 0), Location.create(2602, 4340, 0)))
        WHEAT.add(WheatSet(0, Location.create(2612, 4324, 0), Location.create(2612, 4323, 0)))
        WHEAT.add(WheatSet(1, Location.create(2584, 4296, 0), Location.create(2583, 4296, 0)))
        WHEAT.add(WheatSet(0, Location.create(2568, 4329, 0), Location.create(2568, 4330, 0)))
        WHEAT.add(WheatSet(1, Location.create(2595, 4343, 0), Location.create(2596, 4343, 0)))
        WHEAT.add(WheatSet(0, Location.create(2615, 4315, 0), Location.create(2615, 4314, 0)))
        WHEAT.add(WheatSet(1, Location.create(2601, 4293, 0), Location.create(2600, 4293, 0)))
        WHEAT.add(WheatSet(0, Location.create(2565, 4310, 0), Location.create(2565, 4311, 0)))
        WHEAT.add(WheatSet(1, Location.create(2582, 4346, 0), Location.create(2583, 4346, 0)))
        WHEAT.add(WheatSet(0, Location.create(2568, 4348, 0), Location.create(2568, 4347, 0)))
        WHEAT.add(WheatSet(0, Location.create(2615, 4347, 0), Location.create(2615, 4348, 0)))
        WHEAT.add(WheatSet(0, Location.create(2612, 4345, 0), Location.create(2612, 4344, 0)))
        WHEAT.add(WheatSet(0, Location.create(2614, 4292, 0), Location.create(2614, 4291, 0)))
        WHEAT.add(WheatSet(0, Location.create(2568, 4292, 0), Location.create(2568, 4291, 0)))
        WHEAT.add(WheatSet(0, Location.create(2571, 4295, 0), Location.create(2571, 4294, 0)))
        WHEAT.add(WheatSet(0, Location.create(2575, 4297, 0), Location.create(2575, 4298, 0)))
        WHEAT.add(WheatSet(0, Location.create(2584, 4330, 0), Location.create(2584, 4329, 0)))
        WHEAT.add(WheatSet(0, Location.create(2599, 4329, 0), Location.create(2599, 4330, 0)))
        WHEAT.add(WheatSet(1, Location.create(2602, 4312, 0), Location.create(2601, 4312, 0)))
        WHEAT.add(WheatSet(1, Location.create(2610, 4312, 0), Location.create(2611, 4312, 0)))
        WHEAT.add(WheatSet(1, Location.create(2570, 4309, 0), Location.create(2569, 4309, 0)))
        WHEAT.add(WheatSet(0, Location.create(2583, 4304, 0), Location.create(2583, 4303, 0)))
    }


    /**
     * Puro-puro option handler.
     */
    inner class PuroOptionHandler : OptionHandler() {
        @Throws(Throwable::class)
        override fun newInstance(arg: Any?): Plugin<Any> {
            ItemDefinition.forId(Items.IMPLING_SCROLL_11273).handlers["option:toggle-view"] = this
            ItemDefinition.forId(Items.JAR_GENERATOR_11258).handlers["option:butterfly-jar"] = this
            ItemDefinition.forId(Items.JAR_GENERATOR_11258).handlers["option:impling-jar"] = this
            ItemDefinition.forId(Items.JAR_GENERATOR_11258).handlers["option:check"] = this
            return this
        }

        override fun handle(player: Player, node: Node, option: String): Boolean {
            when (node.id) {
                Items.JAR_GENERATOR_11258 -> handleJarGenerator(player, node as Item, option)
                Items.IMPLING_SCROLL_11273 ->
                {
                    if (!inZone(player,"puro puro")) {
                        sendMessage(player, "You can't do that while you're outside of minigame area.")
                        return true
                    }
                    if (player.interfaceManager.overlay != null) {
                        closeOverlay(player)
                    } else {
                        openOverlay(player, Components.IMPLING_SCROLL_169)
                    }
                    return true
                }
            }
            return true
        }


        private fun handleJarGenerator(player: Player, item: Item, option: String) {
            when (option) {
                "butterfly-jar", "impling-jar" -> generate(player, item, option)
                "check" -> sendMessage(player, "Your jar generator has a charge percentage of " + getPercent(item) + ".")
            }
        }


        private fun generate(player: Player, item: Item, option: String) {
            val jar = if (option == "butterfly-jar") Item(Items.BUTTERFLY_JAR_10012) else Item(Items.IMPLING_JAR_11260)
            val percent = if (jar.id == Items.BUTTERFLY_JAR_10012) 1 else 3
            if (!hasPercent(item, percent)) {
                sendMessage(player,"Your jar generator doesn't have enough charges to make another " + jar.name.lowercase() + ".")
                return
            }
            lock(player,5)
            animate(player, 6592)
            addItem(player, jar.id)
            setPercent(item, percent)
            sendMessage(player,"Your jar generator generates a " + jar.name.lowercase() + ".")
            if (getPercent(item) <= 0) {
                removeItem(player, item)
                sendMessage(player,"Your jar generator runs out of charges.")
            }
        }


        private fun hasPercent(item: Item, percent: Int): Boolean {
            return getPercent(item) - percent >= 0
        }


        private fun setPercent(item: Item, percent: Int) {
            item.charge -= percent
        }


        private fun getPercent(item: Item): Int {
            val difference = item.charge - 1000
            return difference + 100
        }

        override fun isWalk(p: Player, n: Node): Boolean {
            return n !is Item
        }

        override fun isWalk(): Boolean {
            return false
        }
    }

    class WheatSet(private val rot: Int, vararg locations: Location) {
        val locations: Array<Location> = locations as Array<Location>
        private val scenery = arrayOfNulls<Scenery>(2)
        var nextWhilt: Int = 0
        private var busyTicks = 0
        private var removed = false

        fun init() {
            for ((index, location) in locations.withIndex()) {
                val `object` = Scenery(25021, location, 22, rot)
                SceneryBuilder.add(`object`)
                scenery[index] = `object`
            }
            setNextWhilt()
        }

        fun whilt() {
            busyTicks = ticks + 5
            for (`object` in scenery) {
                if (`object` == null) {
                    continue
                }
                if (removed) {
                    submitWorldPulse(object : Pulse() {
                        var counter: Int = 0

                        override fun pulse(): Boolean {
                            if (counter++ == 0) {
                                animateScenery(`object`, 6596)
                                delay = animationDuration(Animation(6596))
                                return false
                            }
                            return true
                        }
                    })
                    addScenery(`object`)
                    continue
                }
                submitWorldPulse(object : Pulse() {
                    var counter: Int = 0

                    override fun pulse(): Boolean {
                        if (counter++ == 0) {
                            animateScenery(`object`, 6599)
                            delay = animationDuration(Animation(6599))
                            return false
                        }
                        removeScenery(`object`)
                        return true
                    }
                })
            }
            removed = !removed
            setNextWhilt()
        }

        fun setScenery() {
            for (i in locations.indices) {
                scenery[i] = getObject(locations[i])
            }
        }

        fun setNextWhilt() {
            this.nextWhilt = ticks + RandomFunction.random(40, 300)
        }

        fun canWhilt(): Boolean {
            return ticks > nextWhilt && ticks > busyTicks
        }
    }

    companion object {
        private val WHEAT: MutableList<WheatSet> = ArrayList(20)

        private val PULSE: Pulse = object : Pulse(1) {
            override fun pulse(): Boolean {
                for (set in WHEAT) {
                    if (set.canWhilt()) {
                        set.whilt()
                    }
                }
                return false
            }
        }
    }
}
