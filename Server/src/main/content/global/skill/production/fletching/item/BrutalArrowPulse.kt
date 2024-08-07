package content.global.skill.production.fletching.item

import content.global.skill.production.fletching.data.BrutalArrow
import core.api.*
import core.api.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Brutal arrow pulse.
 * @param node
 * @param arrow
 * @param sets
 */
class BrutalArrowPulse(player: Player?, node: Item?, arrow: BrutalArrow, sets: Int) : SkillPulse<Item?>(player, node) {

    private val arrow: BrutalArrow = arrow
    private val base = Items.FLIGHTED_OGRE_ARROW_2865
    private var sets = 0
    private var ticks = 0

    init {
        this.sets = sets
    }

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.FLETCHING) < arrow.level) {
            sendDialogue(player, "You need a fletching level of " + arrow.level + " in order to do that.")
            return false
        }
        if (!inInventory(player, arrow.base) || !inInventory(player, base)) {
            return false
        }
        if (!hasSpaceFor(player, Item(arrow.product))) {
            sendDialogue(player,"You do not have enough inventory space.")
            return false
        }
        return true
    }

    override fun animate() {
    }

    override fun reward(): Boolean {
        if (++ticks % 3 != 0) {
            return false
        }
        val baseAmount: Int = amountInInventory(player, base)
        val nailAmount: Int = amountInInventory(player, arrow.base)
        val base = Item(base)
        val nail = Item(arrow.base)
        val product = Item(arrow.product)
        if (baseAmount >= 6 && nailAmount >= 6) {
            base.amount = 6
            nail.amount = 6
            product.amount = 6
        } else {
            val amount = if (baseAmount > nailAmount) nailAmount else baseAmount
            base.amount = amount
            nail.amount = amount
            product.amount = amount
        }
        if (player.inventory.remove(base, nail)) {
            player.inventory.add(product)
            rewardXP(player, Skills.FLETCHING, arrow.experience * product.amount)
            sendMessage(player, if (product.amount == 1) "You attach the " + getItemName(arrow.base) + "to the flighted ogre arrow." else "You fletch " + product.amount + " " + getItemName(arrow.product) + " arrows.")
        }
        sets--
        return sets <= 0
    }
}
