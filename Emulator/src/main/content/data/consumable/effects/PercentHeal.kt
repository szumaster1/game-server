package content.data.consumable.effects

import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import kotlin.math.floor
import kotlin.math.min

/**
 * Percent heal.
 */
class PercentHeal(base: Int, percent: Double) : ConsumableEffect() {

    var base: Int = 0 // Initialize base value
    var percent: Double = 0.0 // Initialize percent value

    init {
        this.base = base // Assign base value
        this.percent = percent // Assign percent value
    }

    override fun activate(player: Player) {
        val maxHp = player.getSkills().maximumLifepoints // Get player's maximum health points
        val curHp = player.getSkills().lifepoints // Get player's current health points
        var amount = floor(maxHp * percent).toInt() // Calculate the base amount to heal
        amount = (base + min(amount.toDouble(), ((1.0 + percent) * maxHp.toDouble() - curHp.toDouble()).toInt().toDouble())).toInt() // Calculate the final amount to heal
        player.getSkills().healNoRestrictions(amount) // Heal the player without restrictions
    }
}
