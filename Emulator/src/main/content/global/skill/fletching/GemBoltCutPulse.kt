package content.global.skill.fletching

import org.rs.consts.Items
import core.api.animate
import core.api.getStatLevel
import core.api.rewardXP
import core.api.sendDialogue
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Represents the [GemBoltCutPulse] - [GemBolt].
 */
class GemBoltCutPulse(player: Player?, node: Item?, private val gem: GemBolt, private var amount: Int) : SkillPulse<Item?>(player, node) {

    private var ticks = 0

    /**
     * Checks if the player meets the requirements to cut the gem bolts.
     */
    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.FLETCHING) < gem.level) {
            sendDialogue(player, "You need a fletching level of " + gem.level + " or above to do that.")
            return false
        }
        return player.inventory.containsItem(Item(gem.gem))
    }
    /**
     * Triggers the animation for cutting the gem bolts.
     */
    override fun animate() {
        if (ticks % 6 == 0) {
            animate(player, 6702)
        }
    }

    /**
     * Rewards the player.
     */
    override fun reward(): Boolean {
        if (++ticks % 5 != 0) {
            return false
        }
        val reward = if (gem.gem == Items.OYSTER_PEARLS_413) Item(gem.tip, 24) else Item(gem.tip, 12)
        if (player.inventory.remove(Item(gem.gem))) {
            player.inventory.add(reward)
            rewardXP(player, Skills.FLETCHING, gem.experience)
        }
        amount--
        return amount <= 0
    }

}