package content.region.asgarnia.handlers.whitewolfmountain

import content.data.skill.SkillingTool
import core.api.*
import core.api.consts.Scenery
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.skill.Skills

/**
 * White Wolf Mountain listener.
 */
class WhiteWolfMountainListener : InteractionListener {

    override fun defineListeners() {
        // This listener is triggered when the player interacts with the ROCK_SLIDE_2634 scenery and selects "investigate".
        on(Scenery.ROCK_SLIDE_2634, SCENERY, "investigate") { player, node ->
            sendMessage(player, "These rocks contain nothing interesting.")
            sendMessage(player, "They are just in the way.")
            return@on true
        }

        // This listener is triggered when the player interacts with the ROCK_SLIDE_2634 scenery and selects "mine".
        on(Scenery.ROCK_SLIDE_2634, SCENERY, "mine") { player, node ->
            val pickaxe = SkillingTool.getPickaxe(player)
            val rockScenery = node as core.game.node.scenery.Scenery
            if (getDynLevel(player, Skills.MINING) < 50) {
                sendMessage(player, "You need a mining level of 50 to mine this rock slide.")
                return@on true
            }
            if (pickaxe == null) {
                sendMessage(player, "You do not have a pickaxe to use.")
                return@on true
            }
            animate(player, pickaxe.animation)
            lock(player, 6)
            queueScript(player, 0, QueueStrength.SOFT) { stage: Int ->
                when (stage) {
                    0 -> {
                        // This replaces the ROCK_SLIDE_2634 scenery with the ROCKSLIDE_472 scenery.
                        replaceScenery(rockScenery, Scenery.ROCKSLIDE_472, 2)
                        return@queueScript delayScript(player, 2)
                    }

                    1 -> {
                        // This replaces the ROCKSLIDE_472 scenery with the ROCKSLIDE_473 scenery.
                        replaceScenery(rockScenery, Scenery.ROCKSLIDE_473, 2)
                        return@queueScript delayScript(player, 2)
                    }

                    2 -> {
                        // This replaces the ROCKSLIDE_473 scenery with the scenery ID 476.
                        replaceScenery(rockScenery, 476, 2)
                        player.walkingQueue.reset()
                        if (player.location.x < 2839) {
                            player.walkingQueue.addPath(2840, 3517)
                        } else {
                            player.walkingQueue.addPath(2837, 3518)
                        }
                        return@queueScript delayScript(player, 2)
                    }

                    else -> return@queueScript stopExecuting(player)
                }
            }
            return@on true
        }
    }

}
