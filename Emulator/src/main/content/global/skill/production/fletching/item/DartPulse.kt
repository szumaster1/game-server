package content.global.skill.production.fletching.item

import content.global.skill.production.fletching.data.Dart
import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Dart pulse
 *
 * @param dart Represents the dart object associated with this pulse.
 * @param sets Indicates the number of sets for the dart pulse.
 * @constructor Represents a DartPulse instance with the specified player, node, dart, and sets.
 *
 * @param player The player associated with this pulse, can be null.
 * @param node The item node associated with this pulse, can be null.
 */
class DartPulse(player: Player?, node: Item?, private val dart: Dart, private var sets: Int) : SkillPulse<Item?>(player, node) {

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.FLETCHING) < dart.level) {
            sendDialogue(player,"You need a fletching level of " + dart.level + " to do this.")
            return false
        }
        if (!isQuestComplete(player,"The Tourist Trap")) {
            sendDialogue(player,"You need to have completed Tourist Trap to fletch darts.")
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
        val unfinished = Item(dart.unfinished)
        val dartAmount = player.inventory.getAmount(unfinished)
        val featherAmount = player.inventory.getAmount(featherId)
        if (dartAmount >= 10 && featherAmount >= 10) {
            featherId.amount = 10
            unfinished.amount = 10
            sendMessage(player, "You attach feathers to 10 darts.")
        } else {
            val amount = if (featherAmount > dartAmount) dartAmount else featherAmount
            featherId.amount = amount
            unfinished.amount = amount
            sendMessage(player, if (amount == 1) "You attach a feather to a dart." else "You attach feathers to $amount darts.")
        }
        if (player.inventory.remove(featherId, unfinished)) {
            val product = Item(dart.finished)
            product.amount = featherId.amount
            rewardXP(player, Skills.FLETCHING, dart.experience * product.amount)
            player.inventory.add(product)
        }
        featherId.amount = 1
        if (!player.inventory.containsItem(featherId)) {
            return true
        }
        if (!player.inventory.containsItem(Item(dart.unfinished))) {
            return true
        }
        sets--
        return sets == 0
    }

    override fun message(type: Int) {
    }

    companion object {
        private val featherId = Item(314)
    }
}
