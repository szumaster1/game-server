package content.global.skill.fletching.items.arrow

import content.global.skill.slayer.SlayerManager
import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Represents the arrow head pulse to complete the headless arrow.
 */
class ArrowHeadPulse(player: Player?, node: Item?, arrow: ArrowHead, private var sets: Int) :
    SkillPulse<Item?>(player, node) {

    private val arrows: ArrowHead = arrow

    override fun checkRequirements(): Boolean {
        if (arrows.unfinished == 4160) {
            if (!SlayerManager.getInstance(player).flags.isBroadsUnlocked()) {
                sendDialogue(player, "You need to unlock the ability to create broad arrows.")
                return false
            }
        }
        if (getStatLevel(player, Skills.FLETCHING) < arrows.level) {
            sendDialogue(player, "You need a fletching level of " + arrows.level + " to do this.")
            return false
        }
        if (!hasSpaceFor(player, Item(arrows.finished))) {
            sendDialogue(player, "You do not have enough inventory space.")
            return false
        }
        return true
    }

    override fun animate() {
    }

    override fun reward(): Boolean {
        if (delay == 1) {
            super.setDelay(3)
        }
        val tip = Item(arrows.unfinished)
        val tipAmount: Int = player.inventory.getAmount(arrows.unfinished)
        val shaftAmount = player.inventory.getAmount(HEADLESS_ARROW)
        if (tipAmount >= 15 && shaftAmount >= 15) {
            HEADLESS_ARROW.amount = 15
            tip.amount = 15
            sendMessage(player, "You attach ${getItemName(arrows.unfinished).lowercase()} to 15 arrow shafts.")
        } else {
            val amount = if (tipAmount > shaftAmount) shaftAmount else tipAmount
            HEADLESS_ARROW.amount = amount
            tip.amount = amount
            sendMessage(
                player,
                if (amount == 1) "You attach an ${getItemName(arrows.unfinished).lowercase()} to an arrow shaft." else "You attach ${
                    getItemName(arrows.unfinished).lowercase()
                } to $amount arrow shafts."
            )
        }
        if (player.inventory.remove(HEADLESS_ARROW, tip)) {
            rewardXP(player, Skills.FLETCHING, arrows.experience * tip.amount)
            val product = Item(arrows.finished)
            product.amount = tip.amount
            addItem(player, product.id, product.amount)
        }
        HEADLESS_ARROW.amount = 1
        tip.amount = 1
        if (!player.inventory.containsItem(HEADLESS_ARROW)) {
            return true
        }
        if (!player.inventory.containsItem(tip)) {
            return true
        }
        sets--
        return sets == 0
    }

    override fun message(type: Int) {
    }

    companion object {
        private val HEADLESS_ARROW = Item(53)
    }
}
