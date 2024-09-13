package content.data.consumable.effects

import cfg.consts.Items
import core.api.getStatLevel
import core.api.inInventory
import core.api.modPrayerPoints
import core.game.consumable.ConsumableEffect
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import kotlin.math.floor

/**
 * Prayer effect.
 *
 * @param base The base value for the prayer effect.
 * @param bonus The bonus value for the prayer effect.
 * @constructor Represents the PrayerEffect class with base and bonus values.
 */
class PrayerEffect(var base: Double, var bonus: Double) : ConsumableEffect() {

    /**
     * Activates the prayer effect for the player.
     *
     * @param player The player for whom the prayer effect is activated.
     */
    override fun activate(player: Player) {
        val level = getStatLevel(player, Skills.PRAYER) // Get the player's prayer level
        var b = bonus // Initialize a variable to store the bonus value
        if(inInventory(player, Items.HOLY_WRENCH_6714)) b += 0.02 // Check if the player has a holy wrench for an additional bonus
        val amount = floor(base + (level * b)) // Calculate the total prayer points to be added
        modPrayerPoints(player, amount) // Modify the player's prayer points
    }

}
