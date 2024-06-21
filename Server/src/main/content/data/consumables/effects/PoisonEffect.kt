package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.combat.ImpactHandler
import core.game.node.entity.player.Player

class PoisonEffect(private val amount: Int) : ConsumableEffect() {

    override fun activate(p: Player) {
        p.impactHandler.manualHit(p, amount, ImpactHandler.HitsplatType.POISON)
    }

    override fun getHealthEffectValue(player: Player): Int {
        return -amount
    }

}
