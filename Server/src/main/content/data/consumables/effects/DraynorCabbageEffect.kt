package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills

class DraynorCabbageEffect : ConsumableEffect() {

    override fun activate(p: Player) {
        val effect = HealingEffect(getHealthEffectValue(p))
        effect.activate(p)
    }

    override fun getHealthEffectValue(player: Player): Int {
        return if (player.getSkills().getLevel(Skills.DEFENCE) > 50) 3 else 4
    }
}
