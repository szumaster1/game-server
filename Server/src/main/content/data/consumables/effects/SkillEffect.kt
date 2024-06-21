package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

class SkillEffect(private val skill_slot: Int, private val base: Double, private val bonus: Double) : ConsumableEffect() {

    override fun activate(p: Player) {
        val skills = p.getSkills()
        val slevel = skills.getStaticLevel(skill_slot)
        val delta = (base + (bonus * slevel)).toInt()
        skills.updateLevel(skill_slot, delta, if (delta >= 0) slevel + delta else 0)
    }

}
