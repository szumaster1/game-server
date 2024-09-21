package content.global.skill.fletching.items.arrow

import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import org.rs.consts.Items
import content.global.skill.slayer.SlayerManager

/**
 * Represents the [ArrowHeadPulse] class to make the [arrows].
 */
class ArrowHeadPulse(player: Player?, node: Item?, arrow: ArrowHead, private var sets: Int) : SkillPulse<Item?>(player, node) {

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
        val tipAmount = amountInInventory(player, arrows.unfinished)
        val shaftAmount = amountInInventory(player, Items.HEADLESS_ARROW_53)
        if (tipAmount >= 15 && shaftAmount >= 15) {
            Item(Items.HEADLESS_ARROW_53).amount = 15
            tip.amount = 15
            sendMessage(player, "You attach ${getItemName(arrows.unfinished).lowercase()} to 15 arrow shafts.")
        } else {
            val amount = if (tipAmount > shaftAmount) shaftAmount else tipAmount
            Item(Items.HEADLESS_ARROW_53).amount = amount
            tip.amount = amount
            sendMessage(
                player,
                if (amount == 1) "You attach an " + "${getItemName(arrows.unfinished).lowercase()} to an arrow shaft." else "You attach ${
                    getItemName(arrows.unfinished).lowercase()
                } to $amount arrow shafts."
            )
        }
        if (removeItem(player, Item(Items.HEADLESS_ARROW_53, tip.id))) {
            rewardXP(player, Skills.FLETCHING, arrows.experience * tip.amount)
            val product = Item(arrows.finished)
            product.amount = tip.amount
            addItem(player, product.id)
        }
        Item(Items.HEADLESS_ARROW_53).amount = 1
        tip.amount = 1
        if (!inInventory(player, Items.HEADLESS_ARROW_53)) {
            return true
        }
        if (!inInventory(player, tip.id)) {
            return true
        }
        sets--
        return sets == 0
    }

    override fun message(type: Int) {
    }

}
