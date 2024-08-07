package content.region.misc.handlers.zanaris

import content.region.misc.dialogue.keldagrim.MagicDoorDialogue
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.component.Component
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager.TeleportType
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

/**
 * Zanaris listeners.
 */
class ZanarisListeners : InteractionListener {

    companion object {
        private val TP_ANIM_START = Animation(2755)
        private val TP_ANIM_ENDED = Animation(2757)
        private val ENTER_LOCATION = location(2461, 4356, 0)
        private val EXIT_LOCATION = location(2453, 4476, 0)
        private val RINGS = intArrayOf(12003,12095,14058,14061,14064,14067,14070,14073,14076,14079,14082,14085,14088,14091,14094,14097,14100,14103,14106,14109,14112,14115,14118,14121,14124,14127,14130,14133,14136,14139,14142,14145,14148,14151,14154,14157,14160,16181,16184,23047,27325,3772)
        private val MAGIC_DOORS = intArrayOf(12045, 12047)
        private val REQUIRED_ITEMS = intArrayOf(Items.RAW_CHICKEN_2138, Items.EGG_1944)
        private val ZANARIS_MARKET_RING = getScenery(2486, 4471, 0)!!
        private const val MAIN_RING = Scenery.FAIRY_RING_12128
        private const val ENTRY_RING = Scenery.FAIRY_RING_12094
        private const val CHICKEN_SHRINE = Scenery.CHICKEN_SHRINE_12093
        private const val TUNNEL_ENTRANCE = Scenery.TUNNEL_ENTRANCE_12253
        private const val ENTRANCE_ROPE = Scenery.TUNNEL_ENTRANCE_12254
        private const val ROPE_SCENERY = Scenery.ROPE_12255
        private const val PORTAL_ENTRANCE = Scenery.PORTAL_12260
        private const val ROPE = Items.ROPE_954
    }

    override fun defineListeners() {

        /**
         * Zanaris magic doors interaction.
         */
        on(MAGIC_DOORS, IntType.SCENERY, "open") { player, node ->
            if ((node.id == 12045 && node.location == Location(2469, 4438, 0) && player.location.x >= 2470) || (player.location.y < 4434 && (node.id == 12045 || node.id == 12047 && node.location == Location(2465, 4434, 0))) || (node.id == 12047 && player.location.x >= 2470)) {
                DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            } else {
                player.dialogueInterpreter.open(MagicDoorDialogue.NAME, node)
            }
            return@on true
        }

        /**
         * Evil Chicken lair interactions.
         */
        onUseWith(IntType.SCENERY, REQUIRED_ITEMS, CHICKEN_SHRINE) { player, used, _ ->
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
                            animate(player, TP_ANIM_START)
                            return@queueScript keepRunning(player)
                        }

                        1 -> {
                            teleport(player, ENTER_LOCATION)
                            animate(player, TP_ANIM_ENDED)
                            return@queueScript stopExecuting(player)
                        }

                        else -> return@queueScript stopExecuting(player)
                    }
                }
            }
            return@onUseWith true
        }

        on(PORTAL_ENTRANCE, IntType.SCENERY, "use") { player, _ ->
            teleport(player, EXIT_LOCATION)
            return@on true
        }

        onUseWith(IntType.SCENERY, ROPE, TUNNEL_ENTRANCE) { player, used, _ ->
            if (!removeItem(player, used.asItem())) {
                sendMessage(player, "Nothing interesting happens.")
            } else {
                replaceScenery(core.game.node.scenery.Scenery(TUNNEL_ENTRANCE, location(2455, 4380, 0)), ENTRANCE_ROPE, 80)
            }
            return@onUseWith true
        }

        on(ENTRANCE_ROPE, IntType.SCENERY, "climb-down") { player, _ ->
            ClimbActionHandler.climb(
                player,
                Animation(Animations.MULTI_USE_BEND_OVER_827),
                Location(2441, 4381, 0)
            )
            return@on true
        }

        on(ROPE_SCENERY, IntType.SCENERY, "climb-up") { player, _ ->
            ClimbActionHandler.climb(
                player,
                Animation(Animations.MULTI_USE_BEND_OVER_827),
                Location(2457, 4380, 0)
            )
            return@on true
        }

        /**
         * Fairy ring interactions.
         */
        fun fairyMagic(player: Player): Boolean {
            if (!hasRequirement(player, "Fairytale I - Growing Pains")) {
                sendMessage(player, "The fairy ring is inert.")
                return false
            }
            if (!anyInEquipment(player, Items.DRAMEN_STAFF_772, Items.LUNAR_STAFF_9084)) {
                sendMessage(player, "The fairy ring only works for those who wield fairy magic.")
                return false
            }
            return true
        }

        fun reset(player: Player) {
            removeAttribute(player, "fairy-delay")
            removeAttribute(player, "fairy_location_combo")
            for (i in 0..2) {
                setVarp(player, 816 + i, 0)
            }
        }

        fun openFairyRing(player: Player) {
            reset(player)
            player.interfaceManager.openSingleTab(Component(735))
            player.interfaceManager.open(Component(734))
        }

        on(RINGS, IntType.SCENERY, "use") { player, ring ->
            if (!fairyMagic(player)) return@on true
            teleport(player, if (ring == ZANARIS_MARKET_RING) Location.create(3261, 3168, 0) else Location.create(2412, 4434, 0), TeleportType.FAIRY_RING)
            return@on true
        }

        on(MAIN_RING, IntType.SCENERY, "use") { player, _ ->
            if (!fairyMagic(player)) return@on true
            openFairyRing(player)
            return@on true
        }

        on(ENTRY_RING, IntType.SCENERY, "use") { player, _ ->
            if (!fairyMagic(player)) return@on true
            teleport(player, Location.create(3203, 3168, 0), TeleportType.FAIRY_RING)
            return@on true
        }
    }
}
