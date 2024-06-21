package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player

class PoisonKarambwanEffect : ConsumableEffect() {

    override fun activate(p: Player) {
        if (p.getSkills().lifepoints > 5) {
            effect.activate(p)
        }
    }

    override fun getHealthEffectValue(player: Player): Int {
        return HEALING
    }

    companion object {
        private const val HEALING = -5
        private val effect = PoisonEffect(5)
    }
}
