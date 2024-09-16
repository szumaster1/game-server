package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.tools.RandomFunction

/**
 * Represents an effect that randomly modifies a player's skill.
 */
class RandomSkillEffect(val skillSlot: Int, val a: Int, val b: Int) : ConsumableEffect() {

    /**
     * Activates the random skill effect by generating a random value within the specified range [a, b].
     *
     * @param player the player on which the effect is applied.
     */
    override fun activate(player: Player) {
        val effect = SkillEffect(skillSlot, RandomFunction.random(a, b).toDouble(), 0.0)
        effect.activate(player)
    }

}
