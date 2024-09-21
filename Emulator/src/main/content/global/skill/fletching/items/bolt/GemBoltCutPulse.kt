package content.global.skill.fletching.items.bolt

import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import org.rs.consts.Items

/**
 * Represents the [GemBoltCutPulse] class to make [GemBolt.tip].
 */
class GemBoltCutPulse(player: Player?, node: Item?, private val gem: GemBolt, private var amount: Int) : SkillPulse<Item?>(player, node) {

    private var ticks = 0

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.FLETCHING) < gem.level) {
            sendDialogue(player, "You need a fletching level of " + gem.level + " or above to do that.")
            return false
        }
        return inInventory(player, gem.gem)
    }

    override fun animate() {
        if (ticks % 6 == 0) {
            animate(player, 6702)
        }
    }

    override fun reward(): Boolean {
        if (++ticks % 5 != 0) {
            return false
        }
        val reward = if (gem.gem == Items.OYSTER_PEARLS_413) Item(gem.tip, 24) else Item(gem.tip, 12)
        if (removeItem(player, gem.gem)) {
            addItem(player, reward.id)
            rewardXP(player, Skills.FLETCHING, gem.experience)
        }
        amount--
        return amount <= 0
    }

}