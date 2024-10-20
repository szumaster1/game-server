package content.region.misc.zanaris.handlers

import content.region.misc.keldagrim.dialogue.MagicDoorDialogue
import core.api.*
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import org.rs.consts.Animations
import org.rs.consts.Items
import org.rs.consts.Scenery

class ZanarisListener : InteractionListener {

    override fun defineListeners() {

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

        onUseWith(IntType.SCENERY, requiredItemIDs, CHICKEN_SHRINE) { player, used, _ ->
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
                            animate(player, TP_ANIMATION_A)
                            return@queueScript keepRunning(player)
                        }

                        1 -> {
                            teleport(player, enterLocation)
                            animate(player, TP_ANIMATION_B)
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

        on(ENTRANCE, IntType.SCENERY, "use") { player, _ ->
            teleport(player, exitLocation)
            return@on true
        }

        /*
         * Handling use rope on entrance.
         */

        onUseWith(IntType.SCENERY, ROPE, TUNNEL) { player, used, _ ->
            if (!removeItem(player, used.asItem())) {
                sendMessage(player, "Nothing interesting happens.")
            } else {
                replaceScenery(
                    core.game.node.scenery.Scenery(TUNNEL, location(2455, 4380, 0)),
                    ROPE_ENTRANCE,
                    80
                )
            }
            return@onUseWith true
        }

        /*
         * Handling climb down interaction on entrance.
         */

        on(ROPE_ENTRANCE, IntType.SCENERY, "climb-down") { player, _ ->
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

        on(ROPE_SCENERY, IntType.SCENERY, "climb-up") { player, _ ->
            ClimbActionHandler.climb(
                player,
                Animation(Animations.MULTI_USE_BEND_OVER_827),
                Location(2457, 4380, 0)
            )
            return@on true
        }
    }

    companion object {
        private val enterLocation = Location(2461, 4356, 0)
        private val exitLocation = Location(2453, 4476, 0)
        private val magicDoorIDs = intArrayOf(12045, 12047)
        private val requiredItemIDs = intArrayOf(Items.RAW_CHICKEN_2138, Items.EGG_1944)
        private const val TP_ANIMATION_A = Animations.DISAPPEAR_2755
        private const val TP_ANIMATION_B = Animations.APPEAR_2757
        private const val FIRST_ANIMATION = Animations.CLOSING_CHEST_3335
        private const val CHICKEN_SHRINE = Scenery.CHICKEN_SHRINE_12093
        private const val ENTRANCE = Scenery.PORTAL_12260
        private const val TUNNEL = Scenery.TUNNEL_ENTRANCE_12253
        private const val ROPE_ENTRANCE = Scenery.TUNNEL_ENTRANCE_12254
        private const val ROPE_SCENERY = Scenery.ROPE_12255
        private const val ROPE = Items.ROPE_954
    }
}
