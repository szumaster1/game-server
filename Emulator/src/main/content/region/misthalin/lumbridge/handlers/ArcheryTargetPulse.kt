package content.region.misthalin.lumbridge.handlers

import core.game.node.entity.Entity
import core.game.node.entity.combat.equipment.Ammunition
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.update.flag.context.Animation
import kotlin.math.floor

/**
 * Represents the Archery target pulse.
 */
class ArcheryTargetPulse(private val player: Player, private val node: Scenery) : Pulse(1, player, node) {

    override fun pulse(): Boolean {
        if (delay == 1) {
            delay = player.properties.attackSpeed
        }

        if (player.equipment.remove(Item(9706, 1))) {
            val p = Ammunition.get(9706)?.projectile?.transform(player, node.location)
            p?.endLocation = node.location
            p?.endHeight = 25
            p?.send()
            player.animate(Animation(426))
            val entity: Entity = player
            val level = entity.getSkills().getLevel(Skills.RANGE)
            val bonus = entity.properties.bonuses[14]
            var prayer = 1.0
            if (entity is Player) {
                prayer += entity.prayer.getSkillBonus(Skills.RANGE)
            }
            var cumulativeStr = floor(level * prayer)
            if (entity.properties.attackStyle.style == WeaponInterface.STYLE_RANGE_ACCURATE) {
                cumulativeStr += 3.0
            } else if (entity.properties.attackStyle.style == WeaponInterface.STYLE_LONG_RANGE) {
                cumulativeStr += 1.0
            }
            cumulativeStr *= 1.0
            val hit = (14.0 + cumulativeStr + (bonus.toDouble() / 8) + ((cumulativeStr * bonus) * 0.016865)).toInt() / 10 + 1
            player.getSkills().addExperience(Skills.RANGE, ((hit * 1.33) / 10))
            return !player.equipment.contains(9706, 1) || !player.equipment.contains(9705, 1)
        } else {
            return true
        }
    }

}
