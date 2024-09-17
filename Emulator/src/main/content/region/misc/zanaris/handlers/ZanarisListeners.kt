package content.region.misc.zanaris.handlers

import org.rs.consts.*
import content.region.misc.keldagrim.dialogue.MagicDoorDialogue
import core.api.*
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.node.entity.skill.Skills
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

/**
 * Zanaris listeners.
 */
class ZanarisListeners : InteractionListener {

    private val teleportAnimationStart: Animation = Animation(2755)
    private val teleportAnimationEnd: Animation = Animation(2757)
    private val firstAnimation: Animation = Animation(3335)
    private val secondAnimation: Animation = Animation(3322)
    private val enterLocation = location(2461, 4356, 0)
    private val exitLocation = location(2453, 4476, 0)
    private val magicDoorIDs = intArrayOf(12045, 12047)
    private val requiredItemIDs = intArrayOf(Items.RAW_CHICKEN_2138, Items.EGG_1944)
    private val chickenShrineStatue = Scenery.CHICKEN_SHRINE_12093
    private val portalEntrance = Scenery.PORTAL_12260
    private val tunnelEntrance = Scenery.TUNNEL_ENTRANCE_12253
    private val ropeEntrance = Scenery.TUNNEL_ENTRANCE_12254
    private val ropeScenery = Scenery.ROPE_12255
    private val ropeId = Items.ROPE_954

    override fun defineListeners() {

        /*
         * Special interaction for (Fungi) mushrooms.
         */

        on(intArrayOf(NPCs.FUNGI_3344, NPCs.FUNGI_3345), IntType.NPC, "pick") { player, node ->
            val fungi = node as NPC
            if (getStatLevel(player, Skills.SLAYER) < 57) {
                sendMessage(player, "Zygomite is Slayer monster that require a Slayer level of 57 to kill.")
                return@on true
            }
            lock(player, 1)
            animate(player, firstAnimation)
            submitWorldPulse(object : Pulse() {
                var counter = 0
                override fun pulse(): Boolean {
                    when (counter++) {
                        0 -> {
                            animate(
                                entity = fungi,
                                anim = secondAnimation,
                                /*
                                gfx = if (fungi.id == NPCs.FUNGI_3344)
                                    Graphics.MUSHROOM_DUDE_577 else Graphics.HUGE_MUSHROOM_DUDE_578
                                */
                            )
                            transformNpc(fungi, fungi.id + 2, 500)
                            fungi.impactHandler.disabledTicks = 1
                            fungi.attack(player)
                            return true
                        }
                    }
                    return false
                }
            })
            return@on true
        }

        /*
         * Zanaris magic door interaction.
         */

        on(magicDoorIDs, IntType.SCENERY, "open") { player, node ->
            if ((node.id == 12045 && node.location == Location(2469, 4438, 0) && player.location.x >= 2470) || (player.location.y < 4434 && (node.id == 12045 || node.id == 12047 && node.location == Location(2465, 4434, 0))) || (node.id == 12047 && player.location.x >= 2470)) {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            } else {
                player.dialogueInterpreter.open(MagicDoorDialogue.NAME, node)
            }
            return@on true
        }

        /*
         * Evil Chicken lair interactions.
         */

        onUseWith(IntType.SCENERY, requiredItemIDs, chickenShrineStatue) { player, used, _ ->
            if (used.id != Items.RAW_CHICKEN_2138) {
                sendMessage(player, "Nice idea, but nothing interesting happens.")
                return@onUseWith false
            }
            if (!removeItem(player, used.asItem())) {
                sendMessage(player, "Nothing interesting happens.")
            } else {
                lock(player, 3)
                queueScript(player, 1, QueueStrength.SOFT) { stage: Int ->
                    when (stage) {
                        0 -> {
                            animate(player, teleportAnimationStart)
                            return@queueScript keepRunning(player)
                        }

                        1 -> {
                            teleport(player, enterLocation)
                            animate(player, teleportAnimationEnd)
                            return@queueScript stopExecuting(player)
                        }

                        else -> return@queueScript stopExecuting(player)
                    }
                }
            }
            return@onUseWith true
        }

        /*
         * Handling the Portal entrance.
         */

        on(portalEntrance, IntType.SCENERY, "use") { player, _ ->
            teleport(player, exitLocation)
            return@on true
        }

        /*
         * Handling use rope on entrance.
         */

        onUseWith(IntType.SCENERY, ropeId, tunnelEntrance) { player, used, _ ->
            if (!removeItem(player, used.asItem())) {
                sendMessage(player, "Nothing interesting happens.")
            } else {
                replaceScenery(
                    core.game.node.scenery.Scenery(tunnelEntrance, location(2455, 4380, 0)),
                    ropeEntrance,
                    80
                )
            }
            return@onUseWith true
        }

        /*
         * Handling climb down interaction on entrance.
         */

        on(ropeEntrance, IntType.SCENERY, "climb-down") { player, _ ->
            ClimbActionHandler.climb(
                player,
                Animation(Animations.MULTI_USE_BEND_OVER_827),
                Location(2441, 4381, 0)
            )
            return@on true
        }

        /*
         * Handle exit from the cave.
         */

        on(ropeScenery, IntType.SCENERY, "climb-up") { player, _ ->
            ClimbActionHandler.climb(
                player,
                Animation(Animations.MULTI_USE_BEND_OVER_827),
                Location(2457, 4380, 0)
            )
            return@on true
        }
    }
}
