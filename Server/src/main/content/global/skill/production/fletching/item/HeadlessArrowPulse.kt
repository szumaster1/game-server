package content.global.skill.production.fletching.item

import core.api.*
import core.api.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import kotlin.math.min

/**
 * Headless arrow pulse
 *
 * @property feather Represents the feather item associated with the arrow pulse.
 * @property sets Indicates the number of sets for the arrow pulse.
 * @constructor Represents a new instance of HeadlessArrowPulse.
 *
 * @param player The player associated with this arrow pulse.
 * @param node The item node associated with this arrow pulse.
 */
class HeadlessArrowPulse(player: Player?, node: Item?, private val feather: Item?, private var sets: Int) : SkillPulse<Item?>(player, node) {

    private val headlessArrow = Item(Items.HEADLESS_ARROW_53)
    private val arrowShaft = Item(Items.ARROW_SHAFT_52)
    private var useSets = false

    override fun checkRequirements(): Boolean {
        if (!inInventory(player, Items.ARROW_SHAFT_52)) {
            sendDialogue(player,"You don't have any arrow shafts.")
            return false
        }
        if (feather == null || !inInventory(player, feather.id)) {
            sendDialogue(player,"You don't have any feathers.")
            return false
        }
        useSets = if (player.inventory.contains(arrowShaft.id, 15) && player.inventory.contains(feather.id, 15)) {
            true
        } else {
            false
        }
        if (!hasSpaceFor(player, headlessArrow)) {
            sendDialogue(player, "You do not have enough inventory space.")
            return false
        }
        return true
    }

    override fun animate() {
    }

    override fun reward(): Boolean {
        val featherAmount = player.inventory.getAmount(feather)
        val shaftAmount = player.inventory.getAmount(arrowShaft)
        if (delay == 1) {
            super.setDelay(3)
        }
        if (featherAmount >= 15 && shaftAmount >= 15) {
            feather!!.amount = 15
            arrowShaft.amount = 15
            sendMessage(player, "You attach feathers to 15 arrow shafts.")
        } else {
            val amount = min(featherAmount.toDouble(), shaftAmount.toDouble()).toInt()
            feather!!.amount = amount
            arrowShaft.amount = amount
            sendMessage(player, if (amount == 1) "You attach a feathers to a shaft." else "You attach feathers to $amount arrow shafts.")
        }
        if (player.inventory.remove(feather, arrowShaft)) {
            headlessArrow.amount = feather.amount
            rewardXP(player, Skills.FLETCHING, headlessArrow.amount.toDouble())
            addItem(player, headlessArrow.id, headlessArrow.amount)
        }
        headlessArrow.amount = 1
        feather.amount = 1
        arrowShaft.amount = 1
        if (!player.inventory.containsItem(arrowShaft)) {
            return true
        }
        if (!player.inventory.containsItem(feather)) {
            return true
        }
        sets--
        return sets <= 0
    }

    override fun message(type: Int) {
    }

    private fun getFeather(): Item? {
        val length = FEATHER.size
        for (i in 0 until length) {
            val f = FEATHER[i]
            if (player.inventory.containsItem(f)) {
                return f
            }
        }
        return null
    }

    companion object {
        private val FEATHER = arrayOf(
            Item(Items.FEATHER_314),
            Item(Items.STRIPY_FEATHER_10087),
            Item(Items.RED_FEATHER_10088),
            Item(Items.BLUE_FEATHER_10089),
            Item(Items.YELLOW_FEATHER_10090),
            Item(Items.ORANGE_FEATHER_10091)
        )
    }
}
