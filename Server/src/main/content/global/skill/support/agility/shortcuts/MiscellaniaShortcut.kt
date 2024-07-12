package content.global.skill.support.agility.shortcuts

import content.global.skill.support.agility.AgilityHandler
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.link.diary.DiaryType
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation

class MiscellaniaShortcut : InteractionListener {

    /*
        Broken pier shortcut between Miscellania and Etceteria.
    */

    override fun defineListeners() {
        on(Scenery.BROKEN_PIER_41531, IntType.SCENERY, "step") { player, _ ->
            if (!player.achievementDiaryManager.getDiary(DiaryType.FREMENNIK).isComplete(1, true)) {
                sendMessage(player, "You must complete the medium Fremennik diary to use this shortcut.")
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
                                player, -1, location(2572, 3862, 0),
                                Location.create(2573, 3862, 0),
                                Animation.create(Animations.JUMP_BRIDGE_769), 15, 0.0, null
                            )
                            return@queueScript delayScript(player, 3)
                        }

                        1 -> {
                            AgilityHandler.forceWalk(
                                player, -1, location(2573, 3862, 0),
                                Location.create(2576, 3862, 0),
                                Animation.create(Animations.JUMP_OVER_OBSTACLE_6132), 10, 0.0, null
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
                                player, -1, location(2576, 3862, 0),
                                location(2573, 3862, 0),
                                Animation.create(Animations.JUMP_OVER_OBSTACLE_6132), 10, 0.0, null
                            )
                            return@queueScript delayScript(player, 3)
                        }

                        1 -> {
                            AgilityHandler.forceWalk(
                                player, -1, location(2573, 3862, 0),
                                location(2572, 3862, 0),
                                Animation.create(Animations.JUMP_BRIDGE_769), 15, 0.0, null
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
            if (inBorders(player, 2576, 3854, 2587, 3870)) {
                return@setDest Location.create(2576, 3862, 0)
            } else {
                return@setDest Location.create(2572, 3862, 0)
            }
        }
    }
}
