package content.global.skill.fletching.items.dart

import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import org.rs.consts.Items

/**
 * Represents the [DartPulse] class to make [dart].
 */
class DartPulse(player: Player?, node: Item?, private val dart: Dart, private var sets: Int) : SkillPulse<Item?>(player, node) {

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.FLETCHING) < dart.level) {
            sendDialogue(player, "You need a fletching level of " + dart.level + " to do this.")
            return false
        }
        if (!isQuestComplete(player, "The Tourist Trap")) {
            sendDialogue(player, "You need to have completed Tourist Trap to fletch darts.")
            return false
        }
        if (!hasSpaceFor(player, Item(dart.finished))) {
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
        val feather = Item(Items.FEATHER_314)
        val unfinished = Item(dart.unfinished)
        val dartAmount = amountInInventory(player, unfinished.id)
        val featherAmount = amountInInventory(player, feather.id)
        if (dartAmount >= 10 && featherAmount >= 10) {
            feather.amount = 10
            unfinished.amount = 10
            sendMessage(player, "You attach feathers to 10 darts.")
        } else {
            val amount = if (featherAmount > dartAmount) dartAmount else featherAmount
            feather.amount = amount
            unfinished.amount = amount
            sendMessage(
                player,
                if (amount == 1) "You attach a feather to a dart." else "You attach feathers to $amount darts."
            )
        }
        if (removeItem(player, Item(feather.id, unfinished.id))) {
            val product = Item(dart.finished)
            product.amount = feather.amount
            rewardXP(player, Skills.FLETCHING, dart.experience * product.amount)
            addItem(player, product.id)
        }
        feather.amount = 1
        if (!inInventory(player, feather.id)) {
            return true
        }
        if (!inInventory(player, dart.unfinished)) {
            return true
        }
        sets--
        return sets == 0
    }

    override fun message(type: Int) {
    }

}
