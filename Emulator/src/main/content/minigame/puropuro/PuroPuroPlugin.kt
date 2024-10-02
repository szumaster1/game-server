package content.minigame.puropuro

import core.api.*
import core.cache.def.impl.ItemDefinition
import core.game.component.Component
import core.game.interaction.Option
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.GameWorld.ticks
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.map.RegionManager.getObject
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBuilder
import core.game.world.map.zone.ZoneType
import core.game.world.update.flag.context.Animation
import core.net.packet.PacketRepository
import core.net.packet.context.MinimapStateContext
import core.net.packet.outgoing.MinimapState
import core.plugin.PluginManager.definePlugin
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RandomFunction

/**
 * Puro-puro plugin.
 */
@Initializable
class PuroPuroPlugin : MapZone("puro puro", true), Plugin<Any> {
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
            PacketRepository.send(MinimapState::class.java, MinimapStateContext(p, 2))
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
                p.interfaceManager.close()
                p.interfaceManager.closeOverlay()
                PacketRepository.send(MinimapState::class.java, MinimapStateContext(p, 0))
            }
        }
        return super.leave(e, logout)
    }

    override fun interact(e: Entity, target: Node, option: Option): Boolean {
        if (e is Player) {
            when (target.id) {
                25016, 25029, 25019, 25018, 25020, 25021 -> {
                    pushThrough(e, target as Scenery)
                    return true
                }
            }
        }
        return super.interact(e, target, option)
    }


    private fun pushThrough(player: Player, `object`: Scenery) {
        if (getStatLevel(player, Skills.HUNTER) < 17) {
            sendMessage(player, "You need a Hunting level of at least 17 to enter the maze.")
            return
        }
        if (hasImplingBox(player)) {
            sendDialogue(player, "Something prevents you from entering. You think the portal is offended by your imp boxes. They are not popular on imp and impling planes.")
            return
        }
        val dest = `object`.location.transform(Direction.getLogicalDirection(player.location, `object`.location), 1)
        if (getObject(dest) != null) {
            sendMessage(player, "An object on the other side is in your way.")
            return
        }
        if (RandomFunction.random(2) == 0) {
            sendMessage(player, "You use your strength to push through the wheat.")
        } else {
            sendMessage(player, "You use your strength to push through the wheat. It's hard work though.")
        }
        setAttribute(player, "cantMove", true)
        forceMove(player, player.location, dest, 0, 265, null, 6595, null)
    }


    private fun spawnWheat() {
        for (set in WHEAT) {
            set.init()
        }
    }


    private fun hasImplingBox(player: Player): Boolean {
        return player.inventory.contains(10025, 1) || player.inventory.contains(10027, 1) || player.inventory.contains(10028, 1)
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
            ItemDefinition.forId(11273).handlers["option:toggle-view"] = this
            ItemDefinition.forId(11258).handlers["option:butterfly-jar"] = this
            ItemDefinition.forId(11258).handlers["option:impling-jar"] = this
            ItemDefinition.forId(11258).handlers["option:check"] = this
            return this
        }

        override fun handle(player: Player, node: Node, option: String): Boolean {
            when (node.id) {
                11258 -> handleJarGenerator(player, node as Item, option)
                11273 -> {
                    if (!player.zoneMonitor.isInZone("puro puro")) {
                        player.sendMessage("You can only use this in the Puro Puro Maze.")

                    } else {
                        if (player.interfaceManager.overlay != null) {
                            player.interfaceManager.closeOverlay()

                        }

                        player.interfaceManager.openOverlay(Component(169))
                    }
                    return true
                }
            }
            return true
        }


        private fun handleJarGenerator(player: Player, item: Item, option: String) {
            when (option) {
                "butterfly-jar", "impling-jar" -> generate(player, item, option)
                "check" -> player.sendMessage("Your jar generator has a charge percentage of " + getPercent(item) + ".")
            }
        }


        private fun generate(player: Player, item: Item, option: String) {
            val jar = if (option == "butterfly-jar") Item(10012) else Item(11260)
            val percent = if (jar.id == 10012) 1 else 3
            if (!hasPercent(item, percent)) {
                player.sendMessage("Your jar generator doesn't have enough charges to make another " + jar.name.lowercase() + ".")
                return
            }
            player.lock(5)
            player.animate(Animation(6592))
            player.inventory.add(jar)
            setPercent(item, percent)
            player.sendMessage("Your jar generator generates a " + jar.name.lowercase() + ".")
            if (getPercent(item) <= 0) {
                player.inventory.remove(item)
                player.sendMessage("Your jar generator runs out of charges.")
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
                    SceneryBuilder.add(`object`)
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
                        SceneryBuilder.remove(`object`)
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
