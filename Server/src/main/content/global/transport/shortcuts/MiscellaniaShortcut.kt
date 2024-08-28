package content.global.transport.shortcuts

import cfg.consts.Animations
import cfg.consts.Items
import cfg.consts.Scenery
import content.global.skill.support.agility.AgilityHandler
import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.link.diary.DiaryType
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

/**
 * Represents the Miscellania shortcut.
 */
class MiscellaniaShortcut : InteractionListener {

    override fun defineListeners() {
        on(Scenery.BROKEN_PIER_41531, IntType.SCENERY, "step") { player, _ ->
            if (!isDiaryComplete(player, DiaryType.FREMENNIK, 1)) {
                sendMessage(player, "You need to claim the reward for the medium Fremennik diary use this shortcut.")
                return@on true
            }
            if (!inEquipment(player, Items.FREMENNIK_SEA_BOOTS_2_14572)) {
                sendMessage(player, "You don't have the required boots in order to do that.")
                return@on true
            }

            if (inBorders(player, 2571, 3854, 2575, 3870)) {
                queueScript(player, 1, QueueStrength.NORMAL) { stage: Int ->
                    when (stage) {
                        0 -> {
                            AgilityHandler.forceWalk(
                                player,
                                -1,
                                Location(2572, 3862, 0),
                                Location(2573, 3862, 0),
                                Animation(Animations.JUMP_BRIDGE_769),
                                animationCycles(Animations.JUMP_BRIDGE_769),
                                0.0,
                                null
                            )
                            return@queueScript keepRunning(player)
                        }

                        1 -> {
                            AgilityHandler.forceWalk(
                                player,
                                -1,
                                Location(2573, 3862, 0),
                                Location(2576, 3862, 0),
                                Animation(Animations.JUMP_OVER_OBSTACLE_6132),
                                animationCycles(Animations.JUMP_OVER_OBSTACLE_6132),
                                0.0,
                                null
                            )
                            return@queueScript stopExecuting(player)
                        }

                        else -> return@queueScript stopExecuting(player)
                    }
                }
            } else {
                queueScript(player, 1, QueueStrength.NORMAL) { stage: Int ->
                    when (stage) {
                        0 -> {
                            AgilityHandler.forceWalk(
                                player,
                                -1,
                                Location(2576, 3862, 0),
                                Location(2573, 3862, 0),
                                Animation(Animations.JUMP_OVER_OBSTACLE_6132),
                                animationCycles(Animations.JUMP_OVER_OBSTACLE_6132),
                                0.0,
                                null
                            )
                            return@queueScript keepRunning(player)
                        }

                        1 -> {
                            AgilityHandler.forceWalk(
                                player,
                                -1,
                                location(2573, 3862, 0),
                                location(2572, 3862, 0),
                                Animation(Animations.JUMP_BRIDGE_769),
                                animationCycles(Animations.JUMP_BRIDGE_769),
                                0.0,
                                null
                            )
                            return@queueScript stopExecuting(player)
                        }

                        else -> return@queueScript stopExecuting(player)
                    }
                }
            }
            return@on true
        }
    }


    override fun defineDestinationOverrides() {
        setDest(IntType.SCENERY, intArrayOf(Scenery.BROKEN_PIER_41531), "step") { player, _ ->
            return@setDest if (inBorders(player, 2576, 3854, 2587, 3870)) {
                Location.create(2576, 3862, 0)
            } else {
                Location.create(2572, 3862, 0)
            }
        }

    }
}
