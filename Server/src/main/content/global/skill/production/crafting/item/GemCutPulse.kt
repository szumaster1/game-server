package content.global.skill.production.crafting.item

import content.global.skill.production.crafting.data.GemData
import core.api.*
import core.api.consts.Items
import core.api.consts.Sounds
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Gem cut pulse
 *
 * @param amount The number of gems to be cut.
 * @param gem The specific type of gem being cut.
 * @constructor Represents a GemCutPulse instance.
 *
 * @param player The player who is performing the gem cutting action.
 * @param item The item associated with the gem cutting process.
 */
class GemCutPulse(player: Player?, item: Item?, var amount: Int, val gem: GemData) : SkillPulse<Item?>(player, item) {

    val ticks = 0

    init {
        resetAnimation = false
    }

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < gem.level) {
            sendMessage(player, "You need a crafting level of " + gem.level + " to craft this gem.")
            return false
        }
        if (!inInventory(player, CHISEL)) {
            return false
        }
        if (!inInventory(player, gem.uncut.id)) {
            return false
        }
        return true
    }

    override fun animate() {
        if (ticks % 5 == 0 || ticks < 1) {
            playAudio(player, Sounds.CHISEL_2586)
            animate(player, gem.animation)
        }
    }

    override fun reward(): Boolean {
        if (removeItem(player, gem.uncut)) {
            val item = gem.gem
            addItem(player, item.id)
            rewardXP(player, Skills.CRAFTING, gem.exp)
        }
        amount--
        return amount < 1
    }

    companion object {
        private const val CHISEL = Items.CHISEL_1755
    }
}
