package content.data.consumable.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills

/**
 * Summoning effect
 *
 * @param base The base value for the summoning effect
 * @param bonus The bonus value for the summoning effect
 * @constructor Represents the Summoning effect with base and bonus values
 */
class SummoningEffect(var base: Double, var bonus: Double) : ConsumableEffect() {

    override fun activate(player: Player) {
        // Get the current Summoning skill level of the player
        val level = player.getSkills().getStaticLevel(Skills.SUMMONING)
        // Calculate the new amount based on the base and bonus values
        val amt = base + (level * bonus)
        // Update the Summoning skill level of the player
        player.getSkills().updateLevel(Skills.SUMMONING, amt.toInt(), level)
    }
}
