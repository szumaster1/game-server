package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

class DwarvenRockCakeEffect : ConsumableEffect() {

    override fun activate(p: Player) {
        if (p.getSkills().lifepoints > 2) {
            effect.activate(p)
        }
    }

    override fun getHealthEffectValue(player: Player): Int {
        return if (player.getSkills().lifepoints > 2) -1 else 0
    }

    companion object {
        private val effect = DamageEffect(1.0, false)
    }

}
