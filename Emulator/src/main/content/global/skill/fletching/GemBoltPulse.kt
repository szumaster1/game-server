package content.global.skill.fletching

import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Represents the attaching of a [GemBolt] to a [GemBolt.product].
 */
class GemBoltPulse(player: Player?, node: Item?, bolt: GemBolt, sets: Int) : SkillPulse<Item?>(player, node) {

    private val bolts: GemBolt = bolt
    private var sets = 0
    private var ticks = 0

    init {
        this.sets = sets
    }

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.FLETCHING) < bolts.level) {
            sendDialogue(player, "You need a fletching level of " + bolts.level + " or above to do that.")
            return false
        }
        if (!inInventory(player, bolts.base) || !inInventory(player, bolts.tip)) {
            return false
        }
        if (!hasSpaceFor(player, Item(bolts.product))) {
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
        val baseAmount: Int = amountInInventory(player, bolts.base)
        val tipAmount: Int = amountInInventory(player, bolts.tip)
        val base = Item(bolts.base)
        val tip = Item(bolts.tip)
        val product = Item(bolts.product)
        if (baseAmount >= 10 && tipAmount >= 10) {
            base.amount = 10
            tip.amount = 10
            product.amount = 10
        } else {
            val amount = if (baseAmount > tipAmount) tipAmount else baseAmount
            base.amount = amount
            tip.amount = amount
            product.amount = amount
        }
        if (player.inventory.remove(base, tip)) {
            player.inventory.add(product)
            rewardXP(player, Skills.FLETCHING, bolts.experience * product.amount)
            sendMessage(player, if (product.amount == 1) "You attach the tip to the bolt." else "You fletch " + product.amount + " bolts.")
        }
        sets--
        return sets <= 0
    }
}
