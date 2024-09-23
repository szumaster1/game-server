package content.global.travel

import core.api.playAudio
import core.cache.def.impl.SceneryDefinition
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager.TeleportType
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.map.RegionManager.getLocalPlayersBoundingBox
import core.game.world.map.RegionManager.getRegionChunk
import core.game.world.update.flag.chunk.GraphicUpdateFlag
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.plugin.Plugin
import core.tools.RandomFunction
import org.rs.consts.Sounds

/**
 * Represents the wilderness obelisk option handler.
 */
@Initializable
class WildernessObelisk : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        SceneryDefinition.forId(14829).handlers["option:activate"] = this
        SceneryDefinition.forId(14826).handlers["option:activate"] = this
        SceneryDefinition.forId(14827).handlers["option:activate"] = this
        SceneryDefinition.forId(14828).handlers["option:activate"] = this
        SceneryDefinition.forId(14830).handlers["option:activate"] = this
        SceneryDefinition.forId(14831).handlers["option:activate"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        val nodeObject = node as Scenery
        val stationObelisk = Obelisk.forLocation(player.location) ?: return false

        for (i in 0..3) {
            var x = stationObelisk.location.x
            var y = stationObelisk.location.y
            val z = stationObelisk.location.z
            when (i) {
                0 -> {
                    x = x + 2
                    y = y + 2
                    SceneryBuilder.replace(
                        Scenery(nodeObject.id, Location.create(x, y, z)),
                        Scenery(14825, Location.create(x, y, 0)),
                        6
                    )
                }

                1 -> {
                    x = x - 2
                    y = y + 2
                    SceneryBuilder.replace(
                        Scenery(nodeObject.id, Location.create(x, y, z)),
                        Scenery(14825, Location.create(x, y, 0)),
                        6
                    )
                }

                2 -> {
                    x = x - 2
                    y = y - 2
                    SceneryBuilder.replace(
                        Scenery(nodeObject.id, Location.create(x, y, z)),
                        Scenery(14825, Location.create(x, y, 0)),
                        6
                    )
                }

                3 -> {
                    x = x + 2
                    y = y - 2
                    SceneryBuilder.replace(
                        Scenery(nodeObject.id, Location.create(x, y, z)),
                        Scenery(14825, Location.create(x, y, 0)),
                        6
                    )
                }
            }
        }
        playAudio(player, Sounds.WILDERNESS_TELEPORT_204)

        Pulser.submit(object : Pulse(6, player) {
            override fun pulse(): Boolean {
                val center = stationObelisk.location
                if (delay == 1) {
                    for (x in center.x - 1..center.x + 1) {
                        for (y in center.y - 1..center.y + 1) {
                            val l = Location.create(x, y, 0)
                            getRegionChunk(l).flag(GraphicUpdateFlag(Graphic.create(342), l))
                        }
                    }
                    return true
                }
                /*
                 * Determine new obelisk.
                 */
                val newObelisks = Obelisk.values()
                for (i in newObelisks.indices) {
                    /*
                     * Find our current obelisk and remove it from the candidate set
                     * by replacing it with the last obelisk.
                     */
                    if (newObelisks[i] == stationObelisk) {
                        newObelisks[i] = newObelisks[newObelisks.size - 1]
                        break
                    }
                }
                val index = RandomFunction.random(0, newObelisks.size - 1)

                /*
                 * Cutting out the last one that is now duplicated.
                 */
                val newObelisk = newObelisks[index]
                /*
                 * Teleport players standing within a 3-by-3 bounding box.
                 */
                for (player in getLocalPlayersBoundingBox(center, 1, 1)) {
                    player.packetDispatch.sendMessage("Ancient magic teleports you somewhere in the wilderness.")
                    val xOffset = player.location.x - center.x
                    val yOffset = player.location.y - center.y
                    player.teleporter.send(
                        Location.create(
                            newObelisk.location.x + xOffset,
                            newObelisk.location.y + yOffset,
                            0
                        ), TeleportType.OBELISK, 2
                    )
                }
                super.setDelay(1)
                return false
            }
        })
        return true
    }

    /**
     * Obelisk.
     */
    enum class Obelisk(val location: Location) {
        /**
         * Level 13.
         */
        LEVEL_13(Location(3156, 3620, 0)),

        /**
         * Level 19.
         */
        LEVEL_19(Location(3219, 3656, 0)),

        /**
         * Level 27.
         */
        LEVEL_27(Location(3035, 3732, 0)),

        /**
         * Level 35.
         */
        LEVEL_35(Location(3106, 3794, 0)),

        /**
         * Level 44.
         */
        LEVEL_44(Location(2980, 3866, 0)),

        /**
         * Level 50.
         */
        LEVEL_50(Location(3307, 3916, 0));


        companion object {
            fun forLocation(location: Location?): Obelisk? {
                for (obelisk in values()) if (obelisk.location.getDistance(location) <= 20) return obelisk
                return null
            }
        }
    }
}
