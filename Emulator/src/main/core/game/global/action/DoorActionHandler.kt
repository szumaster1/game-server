package core.game.global.action

import cfg.consts.Sounds
import core.api.hasRequirement
import core.api.playAudio
import core.game.node.entity.Entity
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.scenery.Constructed
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.config.DoorConfigLoader.Companion.forId
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.map.RegionManager.getObject
import core.game.world.map.path.Pathfinder
import java.awt.Point

/**
 * Handles door actions.
 * @author Emperor
 */
object DoorActionHandler {
    /**
     * The charge indicating the door is already in use.
     */
    private const val IN_USE_CHARGE = 88

    /**
     * Handles a door reward.
     *
     * @param player  The player.
     * @param scenery The object.
     */
    @JvmStatic
    fun handleDoor(player: Player, scenery: Scenery) {
        val second = if ((scenery.id == 1530 || scenery.id == 1531)) null else getSecondDoor(scenery, player)
        var o: Scenery? = null
        if (scenery is Constructed && (scenery.replaced.also { o = it }) != null) {
            val d = forId(scenery.getId())
            if (d != null && d.isMetal) {
                playAudio(player, Sounds.IRON_DOOR_CLOSE_70)
            } else if (d != null && d.isFence) {
                playAudio(player, Sounds.GATE_CLOSE_66)
            } else {
                playAudio(player, 60)
            }
            SceneryBuilder.replace(scenery, o)
            if (second is Constructed && (second.replaced.also { o = it }) != null) {
                SceneryBuilder.replace(second, o)
                return
            }
            return
        }
        if (scenery.definition.hasAction("close")) {
            if (second != null) {
                player.packetDispatch.sendMessage("The doors appear to be stuck.")
                playAudio(player, Sounds.DOOR_CREAK_61)
                return
            }
            val d = forId(scenery.id)
            if (d != null && d.isMetal) {
                playAudio(player, Sounds.IRON_DOOR_OPEN_71)
            } else if (d != null && d.isFence) {
                playAudio(player, Sounds.GATE_OPEN_67)
            } else {
                playAudio(player, Sounds.DOOR_OPEN_62)
            }
            if (d == null) {
                player.packetDispatch.sendMessage("The door appears to be stuck.")
                playAudio(player, Sounds.DOOR_CREAK_61)
                return
            }
            val firstDir = (scenery.rotation + 3) % 4
            val p = getCloseRotation(scenery)
            val firstLoc = scenery.location.transform(p.getX().toInt(), p.getY().toInt(), 0)
            SceneryBuilder.replace(scenery, scenery.transform(d.replaceId, firstDir, firstLoc))
            return
        }
        val d = forId(scenery.id)
        if (d != null && d.questRequirement != "") {
            if (!hasRequirement(player, d.questRequirement)) return
        }
        if (d == null || d.isAutoWalk) {
            handleAutowalkDoor(player, scenery)
            return
        }
        if (d.isMetal) {
            playAudio(player, Sounds.IRON_DOOR_OPEN_71)
        } else if (d.isFence) {
            playAudio(player, Sounds.GATE_OPEN_67)
        } else {
            playAudio(player, Sounds.DOOR_OPEN_62)
        }
        if (second != null) {
            val s = forId(second.id)
            open(scenery, second, d.replaceId, s?.replaceId ?: second.id, true, 500, d.isFence)
            return
        }
        open(scenery, null, d.replaceId, -1, true, 500, d.isFence)
    }

    /**
     * Handles the opening and walking through a door.
     *
     * @param entity      the entity walking through the door.
     * @param object      the door object.
     * @param endLocation the end location
     * @return boolean
     */
    @JvmStatic
    @JvmOverloads
    fun handleAutowalkDoor(
        entity: Entity,
        scenery: Scenery,
        endLocation: Location = getEndLocation(entity, scenery)
    ): Boolean {
        if (scenery.charge == IN_USE_CHARGE) {
            return false
        }
        val second = if ((scenery.id == 3)) null else getSecondDoor(scenery, entity)
        entity.lock(4)
        val loc = entity.location
        if (entity is Player) {
            val d = forId(scenery.id)
            if (d != null && d.isMetal) {
                playAudio(entity.asPlayer(), Sounds.IRON_DOOR_OPEN_71)
                playAudio(entity.asPlayer(), Sounds.IRON_DOOR_CLOSE_70, 60)
            } else if (d != null && d.isFence) {
                playAudio(entity.asPlayer(), Sounds.GATE_OPEN_67)
                playAudio(entity.asPlayer(), Sounds.GATE_CLOSE_66, 60)
            } else {
                playAudio(entity.asPlayer(), Sounds.DOOR_OPEN_62)
                playAudio(entity.asPlayer(), 60, 60)
            }
            entity.asPlayer().logoutListeners["autowalk"] = { player: Player ->
                player.location = loc
            }
        }
        Pulser.submit(object : Pulse(1) {
            var opened: Boolean = false

            override fun pulse(): Boolean {
                if (!opened) {
                    open(scenery, second, scenery.id, second?.id ?: -1, false, 2, false)
                    entity.walkingQueue.reset()
                    entity.walkingQueue.addPath(endLocation.x, endLocation.y)
                    opened = true

                    // Set the door in_use
                    scenery.charge = IN_USE_CHARGE
                    if (second != null) {
                        second.charge = IN_USE_CHARGE
                    }
                    return false
                }
                if (entity is Player) {
                    val player = entity.asPlayer()
                    if (scenery.id == 2112 && player.location.withinDistance(Location(3046, 9756, 0), 10)) {
                        player.achievementDiaryManager.finishTask(player, DiaryType.FALADOR, 2, 6)
                    }

                    /*
                     * pass the al kharid gate.
                     */
                    if (scenery.id == 35549 || scenery.id == 35551 && player.viewport.region.id == 13106) {
                        player.achievementDiaryManager.finishTask(player, DiaryType.LUMBRIDGE, 0, 4)
                    }

                    /*
                     * Search the shed in Lumbridge Swamp.
                     */
                    if (scenery.id == 2406 && player.location.withinDistance(Location.create(3202, 3169, 0))) {
                        player.achievementDiaryManager.finishTask(player, DiaryType.LUMBRIDGE, 1, 6)
                    }

                    entity.asPlayer().logoutListeners.remove("autowalk")
                }

                /*
                 * Reset door to inactive.
                 */
                scenery.charge = 1000
                if (second != null) {
                    second.charge = 1000
                }
                return true
            }
        })
        return true
    }

    /**
     * Gets the end location to walk to.
     *
     * @param entity the entity.
     * @param object the object.
     * @return the end location.
     */
    fun getEndLocation(entity: Entity, `object`: Scenery): Location {
        return getEndLocation(entity, `object`, false)
    }

    /**
     * Gets end location.
     *
     * @param entity     the entity
     * @param object     the object
     * @param isAutoWalk the is auto walk
     * @return the end location
     */
    fun getEndLocation(entity: Entity, `object`: Scenery, isAutoWalk: Boolean?): Location {
        var l = `object`.location
        when (`object`.rotation) {
            0 -> if (entity.location.x >= l.x) {
                l = l.transform(-1, 0, 0)
            }

            1 -> if (entity.location.y <= l.y) {
                l = l.transform(0, 1, 0)
            }

            2 -> if (entity.location.x <= l.x) {
                l = l.transform(1, 0, 0)
            }

            else -> if (entity.location.y >= l.y) {
                l = l.transform(0, -1, 0)
            }
        }
        return l
    }

    /**
     * Gets the destination for the door.
     *
     * @param entity the entity
     * @param door   the door.
     * @return The destination location.
     */
    @JvmStatic
    fun getDestination(entity: Entity, door: Scenery): Location {
        var l = door.location
        var rotation = door.rotation
        if (door is Constructed && door.getDefinition().hasAction("close")) {
            val o = door.replaced
            if (o != null) {
                l = o.location
                rotation = o.rotation
            }
        }
        if (door.type == 9) { // Diagonal doors
            when (rotation) {
                0, 2 -> {
                    if (entity.location.y < l.y || entity.location.x < l.x) {
                        return l.transform(0, -1, 0)
                    }
                    return l.transform(0, 1, 0)
                }

                1, 3 -> {
                    if (entity.location.x > l.x || entity.location.y > l.y) {
                        return l.transform(1, 0, 0)
                    }
                    return l.transform(-1, 0, 0)
                }
            }
        }
        when (rotation) {
            0 -> if (entity.location.x < l.x) {
                if (Pathfinder.find(entity, l.transform(-1, 0, 0)).isMoveNear) {
                    return l.transform(0, 0, 0)
                }
                return l.transform(-1, 0, 0)
            }

            1 -> if (entity.location.y > l.y) {
                if (Pathfinder.find(entity, l.transform(0, 1, 0)).isMoveNear) {
                    return l.transform(0, 0, 0)
                }
                return l.transform(0, 1, 0)
            }

            2 -> if (entity.location.x > l.x) {
                if (Pathfinder.find(entity, l.transform(1, 0, 0)).isMoveNear) {
                    return l.transform(0, 0, 0)
                }
                return l.transform(1, 0, 0)
            }

            3 -> if (entity.location.y < l.y) {
                if (Pathfinder.find(entity, l.transform(0, -1, 0)).isMoveNear) {
                    return l.transform(0, 0, 0)
                }
                return l.transform(0, -1, 0)
            }
        }
        return l
    }

    /**
     * Opens the doors.
     *
     * @param object          the door object.
     * @param second          the second door object.
     * @param replaceId       the replacement id.
     * @param secondReplaceId the second replace id.
     * @param clip            if clipping should be changed due to opening the door.
     * @param restoreTicks    the amount of ticks before the door(s) should be closed again.
     * @param fence           the fence
     */
    fun open(
        `object`: Scenery,
        second: Scenery?,
        replaceId: Int,
        secondReplaceId: Int,
        clip: Boolean,
        restoreTicks: Int,
        fence: Boolean
    ) {
        var scenery = `object`
        var second = second
        var replaceId = replaceId
        scenery = scenery.wrapper
        val mod = if (scenery.type == 9) -1 else 1
        var firstDir = (scenery.rotation + ((mod + 4) % 4)) % 4
        val p = getRotationPoint(scenery.rotation)
        var firstLoc = scenery.location.transform(p!!.getX().toInt() * mod, p.getY().toInt() * mod, 0)
        if (second == null) {
            if (replaceId == 4577) {
                replaceId = 4578
                firstDir = 3
                firstLoc = firstLoc.transform(0, 1, 0)
            }
            SceneryBuilder.replace(scenery, scenery.transform(replaceId, firstDir, firstLoc), restoreTicks, clip)
            return
        }
        second = second.wrapper
        if (fence) {
            openFence(scenery, second, replaceId, secondReplaceId, clip, restoreTicks)
            return
        }
        val offset =
            Direction.getDirection(second.location.x - scenery.location.x, second.location.y - scenery.location.y)
        var secondDir = (second.rotation + mod) % 4
        if (firstDir == 1 && offset == Direction.NORTH) {
            firstDir = 3
        } else if (firstDir == 2 && offset == Direction.EAST) {
            firstDir = 0
        } else if (firstDir == 3 && offset == Direction.SOUTH) {
            firstDir = 1
        } else if (firstDir == 0 && offset == Direction.WEST) {
            firstDir = 2
        }
        if (firstDir == secondDir) {
            secondDir = (secondDir + 2) % 4
        }
        val secondLoc = second.location.transform(p.getX().toInt(), p.getY().toInt(), 0)
        SceneryBuilder.replace(scenery, scenery.transform(replaceId, firstDir, firstLoc), restoreTicks, clip)
        SceneryBuilder.replace(second, second.transform(secondReplaceId, secondDir, secondLoc), restoreTicks, clip)
    }

    /**
     * Handles the opening of a fence.
     *
     * @param scenery         the fence object.
     * @param second          the second fence object.
     * @param replaceId       the replacement id.
     * @param secondReplaceId the second replace id.
     * @param clip            if clipping should be changed due to opening the door.
     * @param restoreTicks    the amount of ticks before the door(s) should be
     * closed again.
     */
    private fun openFence(
        scenery: Scenery,
        second: Scenery?,
        replaceId: Int,
        secondReplaceId: Int,
        clip: Boolean,
        restoreTicks: Int
    ) {
        var replaceId = replaceId
        var secondReplaceId = secondReplaceId
        val offset =
            Direction.getDirection(second!!.location.x - scenery.location.x, second.location.y - scenery.location.y)
        var firstDir = (scenery.rotation + 3) % 4
        val p = getRotationPoint(scenery.rotation)
        var firstLoc: Location? = null
        var secondDir = (second.rotation + 3) % 4
        if (offset == Direction.WEST || offset == Direction.SOUTH) {
            firstLoc = second.location.transform(p!!.getX().toInt(), p.getY().toInt(), 0)
            val s = replaceId
            replaceId = secondReplaceId
            secondReplaceId = s
        } else {
            firstLoc = scenery.location.transform(p!!.getX().toInt(), p.getY().toInt(), 0)
        }
        if (scenery.rotation == 3 || scenery.rotation == 2) {
            firstDir = (firstDir + 2) % 4
            secondDir = (secondDir + 2) % 4
        }
        val secondLoc = firstLoc.transform(p.getX().toInt(), p.getY().toInt(), 0)
        if ((scenery.id == 36917 || scenery.id == 36919)) {
            when (scenery.direction) {
                Direction.SOUTH -> {
                    SceneryBuilder.replace(scenery, scenery.transform(36919, firstDir, firstLoc), restoreTicks, true)
                    SceneryBuilder.replace(second, second.transform(36917, secondDir, secondLoc), restoreTicks, true)
                }

                Direction.EAST -> {
                    SceneryBuilder.replace(scenery, scenery.transform(36917, firstDir, firstLoc), restoreTicks, true)
                    SceneryBuilder.replace(second, second.transform(36919, secondDir, secondLoc), restoreTicks, true)
                }

                else -> {
                    SceneryBuilder.replace(scenery, scenery.transform(36919, firstDir, firstLoc), restoreTicks, true)
                    SceneryBuilder.replace(second, second.transform(36917, secondDir, secondLoc), restoreTicks, true)
                }
            }
        } else {
            SceneryBuilder.replace(scenery, scenery.transform(replaceId, firstDir, firstLoc), restoreTicks, clip)
            SceneryBuilder.replace(second, second.transform(secondReplaceId, secondDir, secondLoc), restoreTicks, clip)
        }
    }

    /**
     * Handles the opening of a fence.
     *
     * @param entity          the entity.
     * @param scenery         the fence object.
     * @param replaceId       the replacement id.
     * @param secondReplaceId the second replace id.
     * @return the boolean
     */
    fun autowalkFence(entity: Entity, scenery: Scenery, replaceId: Int, secondReplaceId: Int): Boolean {
        val second = getSecondDoor(scenery, entity)
        if (scenery.charge == IN_USE_CHARGE || second == null) {
            return false
        }
        entity.lock(4)
        val loc = entity.location
        if (entity is Player) {
            entity.asPlayer().logoutListeners["autowalk"] = { player: Player ->
                player.location = loc
                Unit
            }
        }
        scenery.charge = IN_USE_CHARGE
        second.charge = IN_USE_CHARGE
        Pulser.submit(object : Pulse(1) {
            var opened: Boolean = false

            override fun pulse(): Boolean {
                if (!opened) {
                    openFence(scenery, second, replaceId, secondReplaceId, false, 2)
                    val l = getEndLocation(entity, scenery)
                    entity.walkingQueue.reset()
                    entity.walkingQueue.addPath(l.x, l.y)
                    opened = true
                    return false
                }
                if (entity is Player) {
                    entity.asPlayer().logoutListeners.remove("autowalk")
                }
                scenery.charge = 1000
                if (second != null) {
                    second.charge = 1000
                }
                return true
            }
        })
        return true
    }

    /**
     * Gets the closing rotation point.
     *
     * @param object the object.
     * @return The point.
     */
    private fun getCloseRotation(`object`: Scenery): Point {
        when (`object`.rotation) {
            0 -> return Point(0, 1)
            1 -> return Point(1, 0)
            2 -> return Point(0, -1)
            3 -> return Point(-1, 0)
        }
        return Point(0, 0)
    }

    /**
     * Gets the rotation point for the object.
     *
     * @param rotation the rotation.
     * @return The rotation point.
     */
    @JvmStatic
    fun getRotationPoint(rotation: Int): Point? {
        when (rotation) {
            0 -> return Point(-1, 0)
            1 -> return Point(0, 1)
            2 -> return Point(1, 0)
            3 -> return Point(0, -1)
        }
        return null
    }

    /**
     * Gets the door next to this door.
     *
     * @param scenery the door.
     * @param entity  the entity.
     * @return The second door, if any, `null` if no second door existed.
     */
    @JvmStatic
    fun getSecondDoor(scenery: Scenery, entity: Entity?): Scenery? {
        val l = scenery.location
        val player = if (entity is Player) entity else null
        var o: Scenery? = null
        if ((getObject(l.transform(-1, 0, 0)).also { o = it }) != null && (o!!.name == scenery.name)) {
            return o
        }
        if ((getObject(l.transform(1, 0, 0)).also { o = it }) != null && (o!!.name == scenery.name)) {
            return o
        }
        if ((getObject(l.transform(0, -1, 0)).also { o = it }) != null && (o!!.name == scenery.name)) {
            return o
        }
        if ((getObject(l.transform(0, 1, 0)).also { o = it }) != null && (o!!.name == scenery.name)) {
            return o
        }
        return null
    }

    /**
     * Gets the rotations to set the opened doors to.
     *
     * @param scenery the first door.
     * @param second  the second door.
     * @param rp      the rotation point.
     * @return An int-array, with index 0 being first door rotation and index 1 being second door rotation.
     */
    fun getRotation(scenery: Scenery, second: Scenery?, rp: Point): IntArray {
        if (second == null) {
            return intArrayOf((scenery.rotation + 1) % 4)
        }
        var rotations = intArrayOf(3, 1)
        val fl = scenery.location
        val sl = second.location
        if (fl.x > sl.x) {
            rotations = intArrayOf(2, 0)
        }
        if (fl.x < sl.x) {
            rotations = intArrayOf(0, 2)
        }
        if (fl.y > sl.y) {
            rotations = intArrayOf(1, 3)
        }
        if (rp.getY() > 0 || rp.getX() > 0) {
            rotations = intArrayOf(rotations[1], rotations[0])
        }
        return rotations
    }
}
