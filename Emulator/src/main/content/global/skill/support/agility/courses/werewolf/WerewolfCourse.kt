package content.global.skill.support.agility.courses.werewolf

import content.global.skill.support.agility.AgilityCourse
import content.global.skill.support.agility.AgilityHandler
import core.api.*
import cfg.consts.Animations
import cfg.consts.Items
import cfg.consts.NPCs
import core.api.utils.Vector
import core.cache.def.impl.SceneryDefinition
import core.game.dialogue.FacialExpression
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable

/**
 * Werewolf course.
 */
@Initializable
class WerewolfCourse
@JvmOverloads constructor(player: Player? = null) : AgilityCourse(player, 5, 0.0) {
    override fun handle(p: Player, node: Node, option: String): Boolean {
        getCourse(p)
        val n = node as Scenery
        when (n.id) {
            35996 -> steppingStoneObstacle(p, n)
            5133, 5134, 5135 -> jumpHurdleObstacle(p, n)
            5152 -> squeezeThroughPipeObstacle(p, n)
            5136 -> climbSkullSlopeObstacle(p, n)
            5139, 5140, 5141 -> zipLineObstacle(p, n)
        }
        return true
    }

    fun steppingStoneObstacle(p: Player, n: Scenery) {
        val loc = p.location
        val dir = Vector.betweenLocs(loc, n.location).toDirection()
        if (getStatLevel(p, Skills.AGILITY) < 60) {
            sendMessage(p, "You need an agility level of 60 to cross this.")
            return
        }
        if (!anyInEquipment(p, Items.RING_OF_CHAROS_4202, Items.RING_OF_CHAROSA_6465)) {
            sendNPCDialogue(
                p,
                NPCs.AGILITY_TRAINER_1663,
                "Grrr - you don't belong in here, human!",
                FacialExpression.CHILD_NORMAL
            )
            return
        }
        if (loc == Location(3538, 9873, 0)) {
            findLocalNPC(p, 1661)?.let { face(it, p, 3) }
            findLocalNPC(p, 1661)!!.sendChat("FETCH!!!!!")
            findLocalNPC(p, 1661)?.let { animate(it, 6536) }
            setAttribute(p, "werewolf-agility-course", true)
        }
        GameWorld.Pulser.submit(object : Pulse() {
            override fun pulse(): Boolean {
                AgilityHandler.forceWalk(p, -1, loc, loc.transform(dir, 2), Animation.create(1604), 20, 10.0, null)
                return true
            }
        })
    }

    fun jumpHurdleObstacle(p: Player, n: Scenery) {
        val loc = p.location
        if (loc.y in arrayOf(9894, 9897, 9900)) return sendMessage(p, "You can't do that from here.")
        AgilityHandler.forceWalk(p, -1, loc, loc.transform(Direction.NORTH, 2), Animation.create(1603), 10, 25.0, null)
        runTask(p, 2) {
            p.faceLocation(loc.transform(Direction.SOUTH))
        }
        return
    }

    fun squeezeThroughPipeObstacle(p: Player, n: Node) {
        val loc = p.location
        if (loc.y > 9908) return sendMessage(p, "You can't do that from here.")
        if (loc.x in arrayOf(3538, 3541, 3544)) {
            if (getAttribute(p, "werewolf-agility-course", false)) {
                produceGroundItem(p, 4179, 1, stickRandomLocation.location)
            }
            GameWorld.Pulser.submit(object : Pulse(1, p) {
                override fun pulse(): Boolean {
                    lock(p, 6)
                    AgilityHandler.forceWalk(
                        p,
                        -1,
                        loc,
                        loc.transform(Direction.NORTH, 5),
                        Animation.create(10580),
                        10,
                        20.0,
                        null
                    )
                    p.animate(Animation(844), 3)
                    p.animate(Animation(Animations.CLIMB_OUT_OF_OBSTACLE_10579), 4)
                    return true
                }
            })
        }
    }

    fun climbSkullSlopeObstacle(p: Player, n: Node) {
        val loc = p.location
        if (loc.x == 3530) return sendMessage(p, "You can't do that from here.")
        GameWorld.Pulser.submit(object : Pulse() {
            override fun pulse(): Boolean {
                lock(p, 3)
                AgilityHandler.forceWalk(
                    p,
                    -1,
                    loc,
                    loc.transform(Direction.WEST, 3),
                    Animation.create(740),
                    15,
                    25.0,
                    null
                )
                p.animate(Animation(-1), 2)
                return true
            }
        })
    }

    fun zipLineObstacle(p: Player, n: Node) {
        val loc = p.location
        val helmet = getItemFromEquipment(p, EquipmentSlot.HEAD)
        if (helmet != null) {
            openDialogue(p, AgilityTrainerDialogue())
            return
        }

        if (!finishedMoving(p))

            lock(p, 16)
        face(p, loc.transform(Direction.SOUTH))
        animate(p, Animations.WEREWOLF_AGILITY_ZIPLINE_1601)
        replaceScenery(n.asScenery(), 5142, 6)
        sendMessage(p, "You bravely cling on to the death slide by your teeth")
        GameWorld.Pulser.submit(object : Pulse() {
            override fun pulse(): Boolean {
                sendChat(p, "WAAAAAARRRGGGHHHH!!!!!!", 4)
                AgilityHandler.forceWalk(p, -1, loc, Location.create(3528, 9874, 0), Animation(1602), 30, 0.0, null, 1)
                //p.sendMessage("only to fall from a great height!")
                p.sendMessage("... and land safely on your feet.", 14)
                p.animate(Animation(-1), 14)
                runTask(p, 14) {
                    rewardXP(p, Skills.AGILITY, 180.0)
                }
                return true
            }
        })
    }


    override fun configure() {
        SceneryDefinition.forId(35996).handlers["option:jump-to"] = this
        for (i in intArrayOf(5133, 5134, 5135)) {
            SceneryDefinition.forId(i).handlers["option:jump"] = this
        }
        SceneryDefinition.forId(5152).handlers["option:squeeze-through"] = this
        SceneryDefinition.forId(5136).handlers["option:climb-up"] = this
        for (i in intArrayOf(5139, 5140, 5141)) {
            SceneryDefinition.forId(i).handlers["option:teeth-grip"] = this
        }
    }

    override fun getDestination(n: Node?, node: Node): Location? {
        val scenery = node as Scenery
        if (scenery.id == 35996) {
            when (scenery.location.x) {
                3538 -> return scenery.location.transform(0, -2, 0)
                3540 -> return if (scenery.location.y == 9877) scenery.location.transform(-2, 0, 0) else scenery.location.transform(0, -2, 0)
            }
        }
        if (scenery.location.y in intArrayOf(9893, 9896, 9899)) {
            when (scenery.id) {
                5133, 5135 -> return scenery.location.transform(1, -1, 0)
                5134 -> return if (scenery.location.x == 3543) scenery.location.transform(-1, -1, 0) else scenery.location.transform(0, -1, 0)
            }
        }
        return null
    }

    override fun createInstance(player: Player): AgilityCourse {
        return WerewolfCourse(player)
    }

    companion object {
        private val stickLocation = location(3542, 9912, 0)
        val stickRandomLocation: Location = Location.getRandomLocation(stickLocation, 2, true)
    }
}