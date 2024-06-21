package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills

class RestoreEffect(var base: Double, var bonus: Double) : ConsumableEffect() {

    val skills: IntArray = intArrayOf(Skills.DEFENCE, Skills.ATTACK, Skills.STRENGTH, Skills.MAGIC, Skills.RANGE)

    override fun activate(p: Player) {
        val sk = p.getSkills()
        for (skill in skills) {
            val statL = sk.getStaticLevel(skill)
            val curL = sk.getLevel(skill)
            if (curL < statL) {
                val boost = (base + (statL * bonus)).toInt()
                p.getSkills().updateLevel(skill, boost, statL)
            }
        }
    }
}
