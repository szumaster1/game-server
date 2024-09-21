package content.global.skill.fletching.items.arrow

import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import org.rs.consts.Items
import kotlin.math.min

/**
 * Represents the [HeadlessArrowPulse] for creating unfinished arrows.
 */
class HeadlessArrowPulse(player: Player?, node: Item?, private val feather: Item?, private var sets: Int) :
    SkillPulse<Item?>(player, node) {

    private val headlessArrow = Item(Items.HEADLESS_ARROW_53)
    private val arrowShaft = Item(Items.ARROW_SHAFT_52)
    private var useSets = false

    override fun checkRequirements(): Boolean {
        if (!inInventory(player, Items.ARROW_SHAFT_52)) {
            sendDialogue(player, "You don't have any arrow shafts.")
            return false
        }
        if (feather == null || !inInventory(player, feather.id)) {
            sendDialogue(player, "You don't have any feathers.")
            return false
        }
        useSets = inInventory(player, arrowShaft.id, 15) && inInventory(player, feather.id, 15)
        if (!hasSpaceFor(player, headlessArrow)) {
            sendDialogue(player, "You do not have enough inventory space.")
            return false
        }
        return true
    }

    override fun animate() {
    }

    override fun reward(): Boolean {
        val featherAmount = amountInInventory(player, feather!!.id)
        val shaftAmount = amountInInventory(player, arrowShaft.id)
        if (delay == 1) {
            super.setDelay(3)
        }
        if (featherAmount >= 15 && shaftAmount >= 15) {
            feather.amount = 15
            arrowShaft.amount = 15
            sendMessage(player, "You attach feathers to 15 arrow shafts.")
        } else {
            val amount = min(featherAmount.toDouble(), shaftAmount.toDouble()).toInt()
            feather.amount = amount
            arrowShaft.amount = amount
            sendMessage(player, if (amount == 1) "You attach a feathers to a shaft." else "You attach feathers to $amount arrow shafts.")
        }
        if (removeItem(player, Item(feather.id, arrowShaft.amount))) {
            headlessArrow.amount = feather.amount
            rewardXP(player, Skills.FLETCHING, headlessArrow.amount.toDouble())
            addItem(player, headlessArrow.id, headlessArrow.amount)
        }
        headlessArrow.amount = 1
        feather.amount = 1
        arrowShaft.amount = 1
        if (!inInventory(player, arrowShaft.id)) {
            return true
        }
        if (!inInventory(player, feather.id)) {
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
