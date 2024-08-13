package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

/**
 * Skill effect class for consumable effects.
 *
 * @param skillSlot the slot of the skill affected.
 * @param base the base value of the effect.
 * @param bonus the bonus value of the effect.
 * @constructor Represents a SkillEffect with skillSlot, base, and bonus.
 */
class SkillEffect(var skillSlot: Int, var base: Double, var bonus: Double) : ConsumableEffect() {

    /**
     * Activates the skill effect on the player.
     *
     * @param player the player on which the effect is activated.
     */
    override fun activate(player: Player) {
        val skills = player.getSkills() // Get the player's skills
        val slevel = skills.getStaticLevel(skillSlot) // Get the static level of the affected skill
        val delta = (base + (bonus * slevel)).toInt() // Calculate the change in skill level
        skills.updateLevel(skillSlot, delta, if (delta >= 0) slevel + delta else 0) // Update the skill level
    }

}
