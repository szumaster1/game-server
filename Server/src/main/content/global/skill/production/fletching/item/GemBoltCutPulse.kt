package content.global.skill.production.fletching.item

import content.global.skill.production.fletching.data.GemBolt
import core.api.animate
import cfg.consts.Items
import core.api.getStatLevel
import core.api.rewardXP
import core.api.sendDialogue
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Gem bolt cut pulse
 *
 * @param gem Represents the gem bolt being used in the pulse.
 * @param amount Indicates the quantity of gems involved in the pulse.
 * @constructor Represents a new instance of GemBoltCutPulse.
 *
 * @param player The player who is executing the pulse.
 * @param node The item node associated with the pulse.
 */
class GemBoltCutPulse(player: Player?, node: Item?, private val gem: GemBolt, private var amount: Int) : SkillPulse<Item?>(player, node) {

    private var ticks = 0

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.FLETCHING) < gem.level) {
            sendDialogue(player, "You need a fletching level of " + gem.level + " or above to do that.")
            return false
        }
        return player.inventory.containsItem(Item(gem.gem))
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
        if (player.inventory.remove(Item(gem.gem))) {
            player.inventory.add(reward)
            rewardXP(player, Skills.FLETCHING, gem.experience)
        }
        amount--
        return amount <= 0
    }

}