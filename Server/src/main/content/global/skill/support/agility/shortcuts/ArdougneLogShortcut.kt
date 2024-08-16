package content.global.skill.support.agility.shortcuts

import content.global.skill.support.agility.AgilityHandler
import core.api.*
import core.api.consts.Animations
import core.api.consts.Graphics
import core.api.consts.Scenery
import core.api.consts.Sounds
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import kotlin.random.Random

/**
 * Represents the Agility log shortcut interaction.
 */
class ArdougneLogShortcut : InteractionListener {

    override fun defineListeners() {

        on(ardougneLog, IntType.SCENERY, "walk-across") { player, node ->
            if (!hasLevelDyn(player, Skills.AGILITY, 33)) {
                sendDialogue(player, "You need an Agility level of at least 33 to do this.")
                return@on true
            }

            val start = player.location
            var failAnim = Animation(Animations.FALL_OFF_LOG_2581)
            var failLand = Location(2598, 3333, 0)
            var fromWest = false

            lock(player, 10)
            face(player, node)
            if (node.id == Scenery.LOG_BALANCE_35997) {
                fromWest = true
                failAnim = Animation(Animations.FALL_OFF_LOG_2582)
                failLand = Location(2603, 3330, 0)
            }

            if (AgilityHandler.hasFailed(player, 33, 0.1)) {
                AgilityHandler.forceWalk(player, -1, start, failLocation, failAnim, 10, 0.0, null, 0)
                AgilityHandler.forceWalk(player, -1, failLocation, failLand, swimmingLoopAnimation,15,0.0,null, 1)
                submitIndividualPulse(player, object : Pulse(2) {
                    var counter = 0
                    override fun pulse(): Boolean {
                        when(counter++){
                            0 -> {
                                visualize(player, -1, splashGraphic)
                                playAudio(player, Sounds.WATERSPLASH_2496)
                                teleport(player, failLocation)
                                player.animator.forceAnimation(swimmingLoopAnimation)
                            }
                            1 -> AgilityHandler.fail(player, if(fromWest) 4 else 0, failLand, swimmingAnimation, Random.nextInt(1, 7), null).also { return true }
                        }
                        return false
                    }
                })
            } else {
                val end = if (fromWest) start.transform(4, 0, 0) else start.transform(-4, 0, 0)
                AgilityHandler.forceWalk(player, -1, start, end, logBalanceAnimation, 10, 0.0, null, 0).endAnimation = Animation.RESET
            }
            return@on true
        }
    }

    companion object {
        private val ardougneLog = intArrayOf(Scenery.LOG_BALANCE_35997, Scenery.LOG_BALANCE_35999)
        private val logBalanceAnimation = Animation(Animations.BALANCE_WALK_ACROSS_LOG_9908)
        private val swimmingAnimation = Animation(Animations.SWIMMING_6988)
        private val swimmingLoopAnimation = Animation(Animations.SWIMMING_LOOP_6989)
        private val failLocation = Location(2600, 3335, 0)
        private val splashGraphic = Graphic.create(Graphics.WATER_SPLASH_68)
    }
}
