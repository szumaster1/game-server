package content.region.asgarnia.handlers.whitewolfmountain

import content.data.skill.SkillingTool
import core.api.*
import core.api.consts.Scenery
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.skill.Skills

class WhiteWolfMountainListeners : InteractionListener {

    /*
        Author: Oven Bread (https://gitlab.com/ovenbreado)
     */

    override fun defineListeners() {

        on(Scenery.ROCK_SLIDE_2634, SCENERY, "mine", "investigate") { player, node ->
            if (getUsedOption(player) == "investigate") {
                sendMessage(player, "These rocks contain nothing interesting.")
                sendMessage(player, "They are just in the way.")
                return@on true
            }

            if (getUsedOption(player) == "mine") {
                val pickaxe = SkillingTool.getPickaxe(player)
                val rockScenery = node.asScenery()

                if (getDynLevel(player, Skills.MINING) < 50) {
                    sendMessage(player, "You need a mining level of 50 to mine this rock slide.")
                    return@on true
                }

                if (pickaxe == null) {
                    sendMessage(player, "You do not have an axe to use.")
                    return@on true
                }

                lock(player, 6)
                animate(player, pickaxe.animation)
                queueScript(player, 0, QueueStrength.SOFT) { stage ->
                    when (stage) {
                        0 -> {
                            replaceScenery(rockScenery, Scenery.ROCKSLIDE_472, 2)
                            return@queueScript delayScript(player, 2)
                        }

                        1 -> {
                            replaceScenery(rockScenery, Scenery.ROCKSLIDE_473, 2)
                            return@queueScript delayScript(player, 2)
                        }

                        2 -> {
                            replaceScenery(rockScenery, 476, 1)
                            player.walkingQueue.reset()
                            if (player.location.x < 2839) {
                                player.walkingQueue.addPath(2840, 3517)
                            } else {
                                player.walkingQueue.addPath(2837, 3518)
                            }
                            return@queueScript delayScript(player, 1)
                        }

                        else -> return@queueScript stopExecuting(player)
                    }
                }
            } else {
                sendMessage(player, "Nothing interesting happens.")
            }
            return@on true
        }

    }

}