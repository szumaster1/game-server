package content.data.consumables.effects

import core.api.impact
import core.api.sendMessageWithDelay
import core.game.consumable.ConsumableEffect
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.tools.RandomFunction

/**
 * Poison chalice effect.
 */
class PoisonChaliceEffect : ConsumableEffect() {

    override fun activate(player: Player) {

        when (RandomFunction.nextInt(7)) {
            1 -> {
                val effect = MultiEffect(
                    SkillEffect(Skills.ATTACK, -base, 0.0),
                    SkillEffect(Skills.STRENGTH, -base, 0.0),
                    SkillEffect(Skills.DEFENCE, -base, 0.0),
                    SkillEffect(Skills.THIEVING, 1.0, 0.0)
                )
                effect.activate(player)
                sendMessageWithDelay(player, "You feel a little better.", 1)
                return
            }

            2 -> {
                val effect = MultiEffect(
                    SkillEffect(Skills.ATTACK, -1.0, 0.0),
                    SkillEffect(Skills.STRENGTH, -1.0, 0.0),
                    SkillEffect(Skills.DEFENCE, -1.0, 0.0),
                    SkillEffect(Skills.CRAFTING, 1.0, 0.0)
                )
                effect.activate(player)
                sendMessageWithDelay(player, "You feel a little strange.", 1)
            }

            3 -> {
                val totalLife = 10 + (player.getSkills().maximumLifepoints - 100) * 0.07
                val effect = HealingEffect(totalLife.toInt())
                effect.activate(player)
                sendMessageWithDelay(player, "It heals some health.", 1)
                return
            }

            4 -> {
                val effect = MultiEffect(
                    HealingEffect(getHealthEffectValue(player)),
                    SkillEffect(Skills.THIEVING, 1.0, 0.0)
                )
                effect.activate(player)
                sendMessageWithDelay(player, "You feel a lot better.", 1)
                return
            }

            5 -> {
                val effect = MultiEffect(
                    SkillEffect(Skills.ATTACK, 4.0, 0.0),
                    SkillEffect(Skills.STRENGTH, 4.0, 0.0),
                    SkillEffect(Skills.DEFENCE, 4.0, 0.0),
                    SkillEffect(Skills.THIEVING, 0.0, 1.0)
                )
                effect.activate(player)
                sendMessageWithDelay(player, "Wow! That was amazing! You feel really invigorated.", 1)
                return
            }

            6 -> {
                var rand = RandomFunction.random(1.0, 2.0)
                var amt = RandomFunction.random(1, 2)
                val effect = MultiEffect(
                    SkillEffect(Skills.ATTACK, rand, 0.0),
                    SkillEffect(Skills.STRENGTH, rand, 0.0),
                    SkillEffect(Skills.DEFENCE, rand, 0.0),
                )
                impact(player, amt, ImpactHandler.HitsplatType.NORMAL)
                effect.activate(player)
                sendMessageWithDelay(player, "That tasted a bit dodgy. You feel a bit ill.", 1)
                return
            }

            else -> sendMessageWithDelay(player, "It has a slight taste of apricot.", 1)
        }

    }

    override fun getHealthEffectValue(player: Player): Int {
        return (20 + (player.getSkills().maximumLifepoints - 100) * 0.14).toInt()
    }

    companion object {
        var base = RandomFunction.random(1.0, 4.0)
        var amt = RandomFunction.random(1, 49)
    }

}
