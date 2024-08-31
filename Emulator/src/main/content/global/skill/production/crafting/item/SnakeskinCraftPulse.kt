package content.global.skill.production.crafting.item

import content.global.skill.production.crafting.data.Leather
import content.global.skill.production.crafting.data.Leather.decayThread
import content.global.skill.production.crafting.data.Leather.isLastThread
import content.global.skill.production.crafting.data.Leather.removeThread
import content.global.skill.production.crafting.data.Snakeskin
import core.api.*
import cfg.consts.Animations
import cfg.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Snakeskin craft pulse
 *
 * @param amount The number of skins to be crafted.
 * @param skin The specific type of snakeskin being used.
 * @constructor Represents a new instance of SnakeskinCraftPulse.
 *
 * @param player The player who is performing the crafting action.
 * @param node The item node associated with the crafting process.
 */
class SnakeskinCraftPulse(player: Player?, node: Item?, var amount: Int, val skin: Snakeskin) : SkillPulse<Item?>(player, node) {

    var ticks = 0

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < skin.level) {
            sendDialogue(player, "You need a crafting level of " + skin.level + " to make this.")
            return false
        }
        if (!inInventory(player, Leather.NEEDLE, 1)) {
            return false
        }
        if (!inInventory(player, Leather.THREAD.id)) {
            sendDialogue(player, "You need thread to make this.")
            return false
        }
        if (!inInventory(player, Items.SNAKESKIN_6289, skin.requiredAmount)) {
            sendDialogue(player, "You need " + skin.requiredAmount + " snakeskins in order to do this.")
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
        if (removeItem(player, Item(Items.SNAKESKIN_6289, skin.requiredAmount))) {
            val item = skin.product
            addItem(player, item.id)
            rewardXP(player, Skills.CRAFTING, skin.experience)
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
