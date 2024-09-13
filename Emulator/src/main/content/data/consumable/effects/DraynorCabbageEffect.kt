package content.data.consumable.effects

import core.api.getStatLevel
import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills

/**
 * Draynor cabbage effect.
 */
class DraynorCabbageEffect : ConsumableEffect() {

    override fun activate(player: Player) {
        val effect = HealingEffect(getHealthEffectValue(player))
        effect.activate(player)
    }

    override fun getHealthEffectValue(player: Player): Int {
        return if (getStatLevel(player, Skills.DEFENCE) > 50) 3 else 4
    }
}
