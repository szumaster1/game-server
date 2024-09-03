package content.global.skill.production.fletching.item

import cfg.consts.Items
import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Represents the [GrapplePulse] used to create a mith grapple.
 */
@Suppress("unused")
class GrapplePulse(player: Player?, node: Item?, private var amount: Int) : SkillPulse<Item?>(player, node) {

    override fun checkRequirements(): Boolean {
        val inventoryAmount = amountInInventory(player, Items.MITH_GRAPPLE_TIP_9416)
        if (amount > inventoryAmount) {
            amount = inventoryAmount
        }

        if (!inInventory(player, Items.MITH_GRAPPLE_TIP_9416) || !inInventory(player, Items.MITHRIL_BOLTS_9142)) {
            return false
        }

        if (getStatLevel(player, Skills.FLETCHING) < 59) {
            sendDialogue(player,"You need a fletching level of at least 59 in order to do this.")
            return false
        }
        return true
    }

    override fun animate() {
    }

    override fun reward(): Boolean {
        if (delay == 1) {
            delay = 3
            return false
        }
        if (player.inventory.remove(Item(Items.MITH_GRAPPLE_TIP_9416)) && player.inventory.remove(Item(Items.MITHRIL_BOLTS_9142))) {
            addItem(player, Items.MITH_GRAPPLE_9418)
            rewardXP(player, Skills.FLETCHING, 5.0)
        }
        amount--
        return amount < 1
    }

    override fun message(type: Int) {
    }

}
