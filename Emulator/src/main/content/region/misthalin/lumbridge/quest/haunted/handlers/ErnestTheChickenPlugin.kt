package content.region.misthalin.lumbridge.quest.haunted.handlers

import content.global.skill.support.agility.AgilityHandler
import content.region.misthalin.lumbridge.quest.haunted.handlers.ErnestTheChickenPlugin.LeverCacheExtension
import core.api.sendMessage
import core.api.setVarp
import core.cache.def.impl.SceneryDefinition
import core.game.global.action.DoorActionHandler.getSecondDoor
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.map.zone.MapZone
import core.game.world.map.zone.ZoneBuilder
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.plugin.Plugin
import java.awt.Point
import java.util.*

/**
 * Represents the Ernest the chicken plugin.
 */
@Initializable
class ErnestTheChickenPlugin : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        SceneryDefinition.forId(11450).handlers["option:open"] = this
        for (lever in Lever.values()) {
            for (i in lever.objectIds) {
                SceneryDefinition.forId(i).handlers["option:pull"] = this
                SceneryDefinition.forId(i).handlers["option:inspect"] = this
            }
        }
        ZoneBuilder.configure(LeverZone())
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val `object` = (node as Scenery)
        val extension = LeverCacheExtension.extend(player)
        var lever = Lever.forObject(`object`.id)
        when (option) {
            "pull" -> {
                lever = Lever.forObject(`object`.id)
                extension.pull(lever, `object`)
            }

            "inspect" -> extension.inspect(lever)
            "open" -> {
                extension.walk(`object`)
                return true
            }
        }
        return true
    }

    /**
     * Lever cache extension.
     */
    class LeverCacheExtension
    /**
     * Instantiates a new Lever cache extension.
     *
     * @param player the player
     */(
        /**
         * Gets player.
         *
         * @return the player
         */
        val player: Player
    ) {
        private val levers = BooleanArray(Lever.values().size)


        /**
         * Pull.
         *
         * @param lever  the lever
         * @param object the object
         */
        fun pull(lever: Lever?, `object`: Scenery?) {
            val up = isUp(lever)
            levers[lever!!.ordinal] = !up
            player.animate(if (!up) UP_ANIMATION else DOWN_ANIMATION)
            Pulser.submit(object : Pulse(1) {
                override fun pulse(): Boolean {
                    updateConfigs()
                    sendMessage(player, "You pull lever " + lever.name.replace("LEVER_", "").trim { it <= ' ' } + " " + (if (up) "down" else "up") + ".")
                    sendMessage(player, "You hear a clunk.")
                    return true
                }
            })
        }


        /**
         * Inspect.
         *
         * @param lever the lever
         */
        fun inspect(lever: Lever?) {
            sendMessage(player, "The lever is " + (if (isUp(lever)) "up" else "down") + ".")
        }


        /**
         * Walk.
         *
         * @param object the object
         */
        fun walk(`object`: Scenery) {
            player.lock(4)
            Pulser.submit(object : Pulse(1, player, `object`) {
                override fun pulse(): Boolean {
                    val p = walkData[0] as Point
                    val rotation = walkData[1] as IntArray
                    val destination = walkData[2] as Location
                    val opened = `object`.transform(`object`.id, rotation[0])
                    opened.charge = 88
                    opened.location = `object`.location.transform(p.getX().toInt(), p.getY().toInt(), 0)
                    val second = getSecondDoor(`object`, player)
                    SceneryBuilder.replace(`object`, opened, 2)
                    if (second != null) {
                        val secondOpened = second.transform(second.id, rotation[1])
                        secondOpened.charge = 88
                        secondOpened.location = second.location.transform(p.getX().toInt(), p.getY().toInt(), 0)
                        SceneryBuilder.replace(second, secondOpened, 2)
                    }
                    AgilityHandler.walk(player, 0, player.location, destination, null, 0.0, null)
                    return true
                }
            })
        }


        /**
         * Update configs.
         */
        fun updateConfigs() {
            setVarp(player, LEVER_CONFIG, calculateLeverConfig())
            setVarp(player, DOOR_CONFIG, calculateDoorConfig())
            save()
        }


        /**
         * Save.
         */
        fun save() {
            var value = false
            for (index in levers.indices) {
                value = levers[index]
                if (!value) { // down.
                    player.getSavedData().questData.draynorLevers[index] = false
                }
            }
        }


        /**
         * Read.
         */
        fun read() {
            var value = false
            for (i in Lever.values().indices) {
                value = player.getSavedData().questData.draynorLevers[i]
                levers[i] = value
            }
            updateConfigs()
        }


        /**
         * Calculate lever config int.
         *
         * @return the int
         */
        fun calculateLeverConfig(): Int {
            var value = 0
            for (i in levers.indices) {
                if (!levers[i]) {
                    value += 2.pow((i + 1).toDouble())
                }
            }
            return value
        }


        /**
         * Calculate door config int.
         *
         * @return the int
         */
        fun calculateDoorConfig(): Int {
            val downCount = downCount
            var value = 0
            var up = false
            var lever: Lever? = null
            for (i in levers.indices) {
                up = levers[i] // if its up.
                lever = Lever.values()[i]
                if (downCount == 0 || downCount == 1 && !isUp(Lever.LEVER_B) && lever == Lever.LEVER_B && levers[0]) { // translation:
                    // no
                    // down
                    // lever
                    // and
                    // lever
                    // b
                    // is
                    // on/off
                    // just
                    // send
                    // 0.
                    value = 0
                    break
                }
                if (downCount == 1 && !isUp(Lever.LEVER_A)) {
                    return 4
                }
                if (downCount == 1 && !isUp(Lever.LEVER_D)) {
                    return 328
                }
                if (downCount == 2 && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_E)) {
                    return 290
                }
                if (downCount == 2 && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_F)) {
                    return 64
                }
                if (downCount == 3 && !isUp(Lever.LEVER_F) && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C) && isUp(Lever.LEVER_A)) {
                    return 306
                }
                if (downCount == 3 && !isUp(Lever.LEVER_E) && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C) && isUp(Lever.LEVER_F)) {
                    return 64
                }
                if (downCount == 5 && !isUp(Lever.LEVER_F) && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C) && !isUp(
                        Lever.LEVER_A
                    ) && !isUp(Lever.LEVER_B)
                ) {
                    return 304
                }
                if (!isUp(Lever.LEVER_F) && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C) && isUp(Lever.LEVER_E) && lever == Lever.LEVER_A) {
                    return 306
                }
                if (!isUp(Lever.LEVER_F) && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C) && isUp(Lever.LEVER_E) && isUp(Lever.LEVER_A) && !isUp(Lever.LEVER_B)) {
                    return 304
                }
                if (!isUp(Lever.LEVER_F) && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C) && isUp(Lever.LEVER_E) && !isUp(Lever.LEVER_A) && !isUp(Lever.LEVER_B)) {
                    return 304
                }
                if (downCount == 4 && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C) && !isUp(Lever.LEVER_A) && !isUp(Lever.LEVER_B)) {
                    return 264
                }
                if (downCount == 3 && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_F) && !isUp(Lever.LEVER_E)) {
                    return 3
                }
                if (downCount == 2 && !isUp(Lever.LEVER_E) && !isUp(Lever.LEVER_F) && isUp(Lever.LEVER_D)) {
                    return 1
                }
                if (downCount == 4 && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_F) && !isUp(Lever.LEVER_E) && !isUp(Lever.LEVER_C)) {
                    return 19
                }
                if (downCount == 3 && !isUp(Lever.LEVER_E) && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C)) {
                    return 306
                }
                if (downCount == 3 && isUp(Lever.LEVER_E) && !isUp(Lever.LEVER_D) && !isUp(Lever.LEVER_C) && !isUp(Lever.LEVER_F)) {
                    return 306
                }
                if (downCount == 2 && !isUp(Lever.LEVER_B) && !isUp(Lever.LEVER_D)) {
                    return 64
                }
                if (downCount == 2 && !isUp(Lever.LEVER_A) && !isUp(Lever.LEVER_D)) {
                    return 328
                }
                if (lever == Lever.LEVER_A && up && !isUp(Lever.LEVER_B)) {
                    value -= 132
                    continue
                } else if (lever == Lever.LEVER_B && up && !isUp(Lever.LEVER_A)) {
                    value -= 64
                    continue
                }
                if (!up) { // if toggled.
                    if (lever == Lever.LEVER_A) {
                        value += 4
                    } else if (lever == Lever.LEVER_B) {
                        value += 128
                    } else if (lever == Lever.LEVER_D) {
                        value += 264
                    } else if (lever == Lever.LEVER_C) {
                        value -= 132
                    } else if (lever == Lever.LEVER_F) {
                        value -= 38
                    } else if (lever == Lever.LEVER_E) {
                        value -= 287
                    }
                }
            }
            return value
        }


        val walkData: Array<Any>
            /**
             * Get the walk data object.
             *
             * @return the object.
             */
            get() {
                var data: Array<Any>? = null
                val x = player.location.x
                val y = player.location.y
                if (x == 3108 && y == 9757) {
                    data = arrayOf(Point(-1, 0), intArrayOf(2, 0), Location.create(3108, 9759, 0))
                } else if (x == 3108 && y == 9759) {
                    data = arrayOf(Point(-1, 0), intArrayOf(2, 0), Location.create(3108, 9757, 0))
                } else if (x == 3106 && y == 9760) {
                    data = arrayOf(Point(0, 1), intArrayOf(3, 0), Location.create(3104, 9760, 0))
                } else if (x == 3104 && y == 9760) {
                    data = arrayOf(Point(0, 1), intArrayOf(3, 0), Location.create(3106, 9760, 0))
                } else if (x == 3104 && y == 9765) {
                    data = arrayOf(Point(0, 1), intArrayOf(3, 0), Location.create(3106, 9765, 0))
                } else if (x == 3106 && y == 9765) {
                    data = arrayOf(Point(0, 1), intArrayOf(3, 0), Location.create(3104, 9765, 0))
                } else if (x == 3102 && y == 9764) {
                    data = arrayOf(Point(-1, 0), intArrayOf(2, 0), Location.create(3102, 9762, 0))
                } else if (x == 3102 && y == 9762) {
                    data = arrayOf(Point(-1, 0), intArrayOf(2, 0), Location.create(3102, 9764, 0))
                } else if (x == 3102 && y == 9759) {
                    data = arrayOf(Point(-1, 0), intArrayOf(2, 0), Location.create(3102, 9757, 0))
                } else if (x == 3102 && y == 9757) {
                    data = arrayOf(Point(-1, 0), intArrayOf(2, 0), Location.create(3102, 9759, 0))
                } else if (x == 3101 && y == 9760) {
                    data = arrayOf(Point(0, 1), intArrayOf(3, 0), Location.create(3099, 9760, 0))
                } else if (x == 3099 && y == 9760) {
                    data = arrayOf(Point(0, 1), intArrayOf(3, 0), Location.create(3101, 9760, 0))
                } else if (x == 3101 && y == 9765) {
                    data = arrayOf(Point(0, 1), intArrayOf(3, 0), Location.create(3099, 9765, 0))
                } else if (x == 3099 && y == 9765) {
                    data = arrayOf(Point(0, 1), intArrayOf(3, 0), Location.create(3101, 9765, 0))
                } else if (x == 3097 && y == 9762) {
                    data = arrayOf(Point(-1, 0), intArrayOf(2, 0), Location.create(3097, 9764, 0))
                } else if (x == 3097 && y == 9764) {
                    data = arrayOf(Point(-1, 0), intArrayOf(2, 0), Location.create(3097, 9762, 0))
                } else if (x == 3101 && y == 9755) {
                    data = arrayOf(Point(0, 1), intArrayOf(3, 0), Location.create(3099, 9755, 0))
                } else if (x == 3099 && y == 9755) {
                    data = arrayOf(Point(0, 1), intArrayOf(3, 0), Location.create(3101, 9755, 0))
                }
                return data ?: arrayOf(Point(0, 0), intArrayOf(0, 0), player.location)
            }


        /**
         * Reset.
         */
        fun reset() {
            Arrays.fill(levers, true)
            Arrays.fill(player.getSavedData().questData.draynorLevers, true)
            updateConfigs()
        }


        val downCount: Int
            /**
             * Gets down count.
             *
             * @return the down count
             */
            get() {
                var count = 0
                for (i in levers.indices) {
                    count += if (!levers[i]) 1 else 0
                }
                return count
            }


        /**
         * Is up boolean.
         *
         * @param lever the lever
         * @return the boolean
         */
        fun isUp(lever: Lever?): Boolean {
            return levers[lever!!.ordinal]
        }


        companion object {
            private const val LEVER_CONFIG = 33


            private const val DOOR_CONFIG = 668


            /**
             * Extend lever cache extension.
             *
             * @param player the player
             * @return the lever cache extension
             */
            fun extend(player: Player): LeverCacheExtension {
                var extension = player.getExtension<LeverCacheExtension>(LeverCacheExtension::class.java)
                if (extension == null) {
                    extension = LeverCacheExtension(player)
                    player.addExtension(LeverCacheExtension::class.java, extension)
                }
                return extension
            }
        }
    }

    /**
     * Lever zone.
     */
    inner class LeverZone
    /**
     * Instantiates a new Lever zone.
     */
        : MapZone("Draynor lever zone", true) {
        override fun configure() {
            registerRegion(12440)
        }

        override fun enter(entity: Entity): Boolean {
            if (entity !is Player) {
                return super.enter(entity)
            }
            val extension = LeverCacheExtension.extend(entity)
            extension.read()
            return super.enter(entity)
        }

        override fun leave(entity: Entity, logout: Boolean): Boolean {
            if (entity is Player) {
                val extension = LeverCacheExtension.extend(entity)
                extension.save()
            }
            return super.leave(entity, logout)
        }
    }

    /**
     * The enum Lever.
     */
    enum class Lever(
        /**
         * Get object ids int [ ].
         *
         * @return the int [ ]
         */
        vararg val objectIds: Int
    ) {
        /**
         * Lever a lever.
         */
        LEVER_A(11451, 11452),

        /**
         * Lever b lever.
         */
        LEVER_B(11453, 11454),

        /**
         * Lever c lever.
         */
        LEVER_C(11455, 11456),

        /**
         * Lever d lever.
         */
        LEVER_D(11457, 11458),

        /**
         * Lever e lever.
         */
        LEVER_E(11459, 11460),

        /**
         * Lever f lever.
         */
        LEVER_F(11461, 11462);


        companion object {
            /**
             * For object lever.
             *
             * @param objectId the object id
             * @return the lever
             */
            fun forObject(objectId: Int): Lever? {
                for (lever in values()) {
                    for (i in lever.objectIds) {
                        if (i == objectId) {
                            return lever
                        }
                    }
                }
                return null
            }
        }
    }

    override fun getDestination(node: Node, n: Node): Location? {
        if (n is Scenery) {
            val player = (node as Player)
            val x = player.location.x
            val y = player.location.y
            val loc = n.getLocation()
            if (loc == Location(3108, 9758, 0)) {
                return if (y <= 9757) {
                    Location.create(3108, 9757, 0)
                } else {
                    Location.create(3108, 9759, 0)
                }
            }
            if (loc == Location(3105, 9760, 0)) {
                return if (x >= 3105) {
                    Location.create(3106, 9760, 0)
                } else {
                    Location.create(3104, 9760, 0)
                }
            }
            if (loc == Location(3105, 9765, 0)) {
                return if (x >= 3106) {
                    Location.create(3106, 9765, 0)
                } else {
                    Location.create(3104, 9765, 0)
                }
            }
            if (loc == Location(3102, 9763, 0)) {
                return if (y >= 9764) {
                    Location.create(3102, 9764, 0)
                } else {
                    Location.create(3102, 9762, 0)
                }
            }
            if (loc == Location(3102, 9758, 0)) {
                return if (y >= 9759) {
                    Location.create(3102, 9759, 0)
                } else {
                    Location.create(3102, 9757, 0)
                }
            }
            if (loc == Location(3100, 9760, 0)) {
                return if (x >= 3100) {
                    Location.create(3101, 9760, 0)
                } else {
                    Location.create(3099, 9760, 0)
                }
            }
            if (loc == Location(3100, 9765, 0)) {
                return if (x >= 3100) {
                    Location.create(3101, 9765, 0)
                } else {
                    Location.create(3099, 9765, 0)
                }
            }
            if (loc == Location(3097, 9763, 0)) {
                return if (y <= 9763) {
                    Location.create(3097, 9762, 0)
                } else {
                    Location.create(3097, 9764, 0)
                }
            }
            if (loc == Location(3100, 9755, 0)) {
                return if (x >= 3100) {
                    Location.create(3101, 9755, 0)
                } else {
                    Location.create(3099, 9755, 0)
                }
            }
        }
        return null
    }

    companion object {
        private val DOWN_ANIMATION = Animation(2140)

        private val UP_ANIMATION = Animation(2139)
    }
}
