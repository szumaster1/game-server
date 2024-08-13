package content.global.skill.production.crafting.item

import content.global.skill.production.crafting.data.LeatherData
import content.global.skill.production.crafting.data.LeatherData.decayThread
import content.global.skill.production.crafting.data.LeatherData.isLastThread
import content.global.skill.production.crafting.data.LeatherData.removeThread
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Hard craft pulse
 *
 * @param amount The amount of items to be crafted.
 * @constructor Represents a new instance of HardCraftPulse.
 *
 * @param player The player who is performing the crafting action.
 * @param node The item node that represents the item being crafted.
 */
class HardCraftPulse(player: Player?, node: Item?, var amount: Int) : SkillPulse<Item?>(player, node) {

    var ticks = 0

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < 28) {
            sendDialogue(player, "You need a crafting level of " + 28 + " to make a hardleather body.")
            return false
        }
        if (!inInventory(player, LeatherData.NEEDLE, 1)) {
            return false
        }
        if (!inInventory(player,LeatherData.HARD_LEATHER, 1)) {
            return false
        }
        if (!inInventory(player, LeatherData.THREAD.id)) {
            sendDialogue(player, "You need thread to make this.")
            return false
        }
        closeInterface(player)
        return true
    }

    override fun animate() {
        if (ticks % 5 == 0) {
            animate(player, ANIMATION)
        }
    }

    override fun reward(): Boolean {
        if (++ticks % 5 != 0) {
            return false
        }
        if (removeItem(player, Item(LeatherData.HARD_LEATHER))) {
            val item = Item(Items.HARDLEATHER_BODY_1131)
            addItem(player, item.id)
            rewardXP(player, Skills.CRAFTING, 35.0)
            decayThread(player)
            if (isLastThread(player)) {
                removeThread(player)
            }
        }
        amount--
        return amount < 1
    }

    companion object {
        private const val ANIMATION = Animations.CRAFT_LEATHER_1249
    }
}
