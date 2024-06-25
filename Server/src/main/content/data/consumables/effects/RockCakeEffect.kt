package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

class RockCakeEffect : ConsumableEffect() {

    override fun activate(p: Player) {
        if (p.getSkills().lifepoints > 1) {
            effect.activate(p)
        }
    }

    override fun getHealthEffectValue(player: Player): Int {
        return (player.getSkills().lifepoints * -0.1).toInt()
    }

    companion object {
        private val effect = DamageEffect(10.0, true)
    }

}
