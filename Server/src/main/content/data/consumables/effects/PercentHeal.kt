package content.data.consumables.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import kotlin.math.floor
import kotlin.math.min

class PercentHeal(base: Int, percent: Double) : ConsumableEffect() {

    var base: Int = 0
    var percent: Double = 0.0

    init {
        this.base = base
        this.percent = percent
    }

    override fun activate(p: Player) {
        val maxHp = p.getSkills().maximumLifepoints
        val curHp = p.getSkills().lifepoints
        var amount = floor(maxHp * percent).toInt()
        amount = (base + min(amount.toDouble(), ((1.0 + percent) * maxHp.toDouble() - curHp.toDouble()).toInt().toDouble())).toInt()
        p.getSkills().healNoRestrictions(amount)
    }
}
