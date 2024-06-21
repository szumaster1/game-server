package content.data.consumables.effects

import core.api.sendMessage
import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.utilities.RandomFunction

class KebabEffect : ConsumableEffect() {

    override fun activate(p: Player) {
        val randomNumber = RandomFunction.nextInt(100)
        val effect: ConsumableEffect
        if (randomNumber < 66) {
            effect = PercentageHealthEffect(10)
            val initialLifePoints = p.getSkills().lifepoints
            effect.activate(p)
            if (p.getSkills().lifepoints > initialLifePoints) {
                sendMessage(p, "It heals some health.")
            }
        } else if (randomNumber < 87) {
            effect = RandomHealthEffect(10, 20)
            effect.activate(p)
            sendMessage(p, "That was a good kebab. You feel a lot better.")
        } else if (randomNumber < 96) {
            /*
                As the probability of lowering by 3 a non-combat skill or
                all melee skills is not specified, 50% is the percentage
                that was chosen.
             */
            if (RandomFunction.nextInt(100) < 50) {
                val affectedSkillSlot = RandomFunction.nextInt(Skills.NUM_SKILLS - 1)
                effect = when (affectedSkillSlot) {
                    Skills.ATTACK, Skills.DEFENCE, Skills.STRENGTH -> MultiEffect(SkillEffect(Skills.ATTACK, -3.0, 0.0), SkillEffect(Skills.DEFENCE, -3.0, 0.0), SkillEffect(Skills.STRENGTH, -3.0, 0.0))
                    else -> SkillEffect(affectedSkillSlot, -3.0, 0.0)
                }
                sendMessage(p, "That tasted a bit dodgy. You feel a bit ill.")
                effect.activate(p)
            } else {
                sendMessage(p, "That kebab didn't seem to do a lot.")
            }
        } else {
            effect = MultiEffect(HealingEffect(30), RandomSkillEffect(Skills.ATTACK, 1, 3), RandomSkillEffect(Skills.DEFENCE, 1, 3), RandomSkillEffect(Skills.STRENGTH, 1, 3))
            effect.activate(p)
            sendMessage(p, "Wow, that was an amazing kebab! You feel really invigorated.")
        }
    }
}
