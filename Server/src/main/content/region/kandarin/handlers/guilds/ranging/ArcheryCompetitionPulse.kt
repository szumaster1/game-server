package content.region.kandarin.handlers.guilds.ranging

import core.api.consts.Items
import core.api.setVarp
import core.game.node.entity.combat.equipment.Ammunition
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation
import core.utilities.RandomFunction
import kotlin.math.floor
import kotlin.math.max

class ArcheryCompetitionPulse(private val player: Player, private val sceneryId: Scenery) : Pulse(1, player, sceneryId) {

    private fun showInterface(points: Int, arrowsLeft: Int, target: Int, msg: String) {
        player.interfaceManager.openComponent(325)
        setVarp(player, 156, 11 - arrowsLeft)
        setVarp(player, 157, points)
        setVarp(player, 158, target)
        player.packetDispatch.sendString(msg, 325, 32)
    }

    override fun pulse(): Boolean {
        if (player.archeryTargets <= 0) {
            return true
        }

        if (delay == 1) {
            delay = player.properties.attackSpeed
        }
        if (player.equipment.remove(Item(Items.BRONZE_ARROW_882, 1))) {
            val p = Ammunition.get(Items.BRONZE_ARROW_882).projectile.transform(player, sceneryId.location)
            p.endLocation = sceneryId.location
            p.endHeight = 25
            p.send()
            player.animate(Animation(426))
            player.lock(delay)

            val level = player.getSkills().getLevel(Skills.RANGE)
            val bonus = player.properties.bonuses[14]
            var prayer = 1.0
            prayer += player.prayer.getSkillBonus(Skills.RANGE)
            var cumulativeStr = floor(level * prayer)

            cumulativeStr *= 1.0
            var hit =
                (14 + cumulativeStr + (bonus / 8) + ((cumulativeStr * bonus) * 0.016865)).toInt() / 10 + 1
            hit += RandomFunction.randomSign(RandomFunction.random(3))

            val target = max(0.0, (13 - hit).toDouble()).toInt()
            var points = 0
            var msg = ""
            when (target) {
                0 -> {
                    points = 100
                    msg = "Bulls-Eye!"
                }

                1 -> {
                    points = 50
                    msg = "Hit Yellow!"
                }

                2, 3, 4 -> {
                    points = 30
                    msg = "Hit Red!"
                }

                5, 6, 7, 8 -> {
                    points = 20
                    msg = "Hit Blue!"
                }

                9, 10 -> {
                    points = 10
                    msg = "Hit Black!"
                }

                11, 12, 13 -> {
                    points = 0
                    msg = "Missed!"
                }
            }
            val xp = points / 2

            player.getSkills().addExperience(Skills.RANGE, xp.toDouble(), true)
            player.archeryTotal += points
            player.archeryTargets -= 1
            player.debug("Hit: $hit")
            player.debug("You have " + player.archeryTargets + " targets left.")
            player.debug("You have " + player.archeryTotal + " score.")

            showInterface(player.archeryTotal, player.archeryTargets, target, msg)
            //player.getArcheryTargets() <= 0;
            return true
        } else {
            return true
        }
    }
}
