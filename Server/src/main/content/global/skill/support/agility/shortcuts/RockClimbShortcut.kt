package content.global.skill.support.agility.shortcuts

import content.global.skill.support.agility.AgilityShortcut
import cfg.consts.Animations
import core.api.getStatLevel
import core.api.sendMessage
import core.game.node.entity.impl.ForceMovement
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.scenery.Scenery
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable

/**
 * Represents the Rock climb shortcut interaction.
 */
@Initializable
class RockClimbShortcut : AgilityShortcut(intArrayOf(9335, 9336, 2231, 26327, 26328, 26324, 26323, 19849, 9296, 9297), 1, 0.0, "climb") {

    override fun run(player: Player, `object`: Scenery, option: String, failed: Boolean) {
        val scalingDownAnim = Animation(Animations.WALK_BACKWARDS_THEN_CLIMB_DOWN_WALL_1148)
        val scalingUpAnim = Animation(Animations.CLIMBING_DOWN_WALL_740)
        val req = if (player.location.equals(2346, 3300, 0) || player.location.equals(2344, 3294, 0)) { 59 } else if (player.location.equals(2338, 3281, 0) || player.location.equals(2338, 3286, 0)) { 85 } else if (player.location.equals(2332, 3252, 0) || player.location.equals(2338, 3253, 0)) { 68 } else { 0 }

        when (`object`.id) {
            2231 -> {
                if (player.location.x <= 2791) {
                    ForceMovement.run(player, `object`.location, `object`.location.transform(3, 0, 0), scalingUpAnim, scalingUpAnim, Direction.WEST, 13).endAnimation = Animation.RESET
                } else {
                    ForceMovement.run(player, `object`.location, `object`.location.transform(-3, 0, 0), scalingDownAnim, scalingDownAnim, Direction.WEST, 13).endAnimation = Animation.RESET
                }
            }

            /**
             * Eagles Peak
             */
            19849 -> {
                if (player.location.x <= 2322) {
                    if (getStatLevel(player, Skills.AGILITY) >= 25) {
                        ForceMovement.run(player, player.location, Location.create(2324, 3497, 0), scalingUpAnim, scalingUpAnim, Direction.SOUTH, 13).endAnimation = Animation.RESET
                    } else {
                        sendMessage(player, "You need an agility level of at least 25 to do this.")
                    }
                } else {
                    if (getStatLevel(player, Skills.AGILITY) >= 25) {
                        ForceMovement.run(player, player.location, Location.create(2322, 3502, 0), scalingDownAnim, scalingDownAnim, Direction.SOUTH, 13).endAnimation = Animation.RESET
                    } else {
                        sendMessage(player, "You need an agility level of at least 25 to do this.")
                    }
                }
            }
            9335 -> ForceMovement.run(player, player.location, Location.create(3427, 3478, 0), scalingUpAnim, scalingUpAnim, Direction.WEST, 13).endAnimation = Animation.RESET
            9336 -> ForceMovement.run(player, player.location, Location.create(3424, 3476, 0), scalingDownAnim, scalingDownAnim, Direction.WEST, 13).endAnimation = Animation.RESET

            /**
             * GodWars shortcut.
             */
            26327 -> {
                if (player.skills.hasLevel(Skills.AGILITY, 60)) {
                    ForceMovement.run(player, player.location, Location.create(2942, 3768, 0), scalingUpAnim, scalingUpAnim, Direction.WEST, 13).endAnimation = Animation.RESET
                } else {
                    sendMessage(player, "You need an agility level of at least 60 to do this.")
                }
            }

            /**
             * GodWars shortcut.
             */
            26328 -> {
                if (getStatLevel(player, Skills.AGILITY) >= 60) {
                    ForceMovement.run(player, player.location, Location.create(2950, 3767, 0), scalingDownAnim, scalingDownAnim, Direction.WEST, 13).endAnimation = Animation.RESET
                } else {
                    sendMessage(player, "You need an agility level of at least 60 to do this.")
                }
            }

            /**
             * GodWars shortcut.
             */
            26324 -> {
                if (getStatLevel(player, Skills.AGILITY) >= 60) {
                    ForceMovement.run(player, player.location, Location.create(2928, 3757, 0), scalingUpAnim, scalingUpAnim, Direction.NORTH, 13).endAnimation = Animation.RESET
                } else {
                    sendMessage(player, "You need an agility level of at least 60 to do this.")
                }
            }

            /**
             * GodWars shortcut.
             */
            26323 -> {
                if (getStatLevel(player, Skills.AGILITY) >= 60) {
                    ForceMovement.run(player, player.location, Location.create(2927, 3761, 0), scalingDownAnim, scalingDownAnim, Direction.NORTH, 13).endAnimation = Animation.RESET
                } else {
                    sendMessage(player, "You need an agility level of at least 60 to do this.")
                }
            }

            /**
             * Arandar.
             */
            9297 -> {
                if (getStatLevel(player, Skills.AGILITY) >= 59 && req == 59) {
                    ForceMovement.run(player, player.location, Location.create(2346, 3300, 0), scalingUpAnim, scalingUpAnim, Direction.NORTH, 13).endAnimation = Animation.RESET
                } else if (player.skills.hasLevel(Skills.AGILITY, 85) && req == 85) {
                    ForceMovement.run(player, player.location, Location.create(2338, 3281, 0), scalingUpAnim, scalingUpAnim, Direction.SOUTH, 13).endAnimation = Animation.RESET
                } else if (player.skills.hasLevel(Skills.AGILITY, 68) && req == 68) {
                    ForceMovement.run(player, player.location, Location.create(2332, 3252, 0), scalingUpAnim, scalingUpAnim, Direction.WEST, 13).endAnimation = Animation.RESET
                } else {
                    sendMessage(player, "You need an agility level of at least $req to do this.")
                }
            }

            /**
             * Arandar.
             */
            9296 -> {
                if (getStatLevel(player, Skills.AGILITY) >= 59 && req == 59) {
                    ForceMovement.run(player, player.location, Location.create(2344, 3294, 0), scalingDownAnim, scalingDownAnim, Direction.NORTH, 13).endAnimation = Animation.RESET
                } else if (player.skills.hasLevel(Skills.AGILITY, 85) && req == 85) {
                    ForceMovement.run(player, player.location, Location.create(2338, 3286, 0), scalingDownAnim, scalingDownAnim, Direction.SOUTH, 13).endAnimation = Animation.RESET
                } else if (player.skills.hasLevel(Skills.AGILITY, 68) && req == 68) {
                    ForceMovement.run(player, player.location, Location.create(2338, 3253, 0), scalingDownAnim, scalingDownAnim, Direction.WEST, 13).endAnimation = Animation.RESET
                } else {
                    sendMessage(player, "You need an agility level of at least $req to do this.")
                }
            }
        }
    }
}
