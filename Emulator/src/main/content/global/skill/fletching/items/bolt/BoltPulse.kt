package content.global.skill.fletching.items.bolt

import content.global.skill.slayer.SlayerManager
import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import org.rs.consts.Items

/**
 * Represents the [BoltPulse] class to make [bolt].
 */
class BoltPulse(player: Player?, node: Item?, private val bolt: Bolt, private val feather: Item, private var sets: Int) : SkillPulse<Item?>(player, node) {

    override fun checkRequirements(): Boolean {
        if (bolt.unfinished.asItem().id == Items.BROAD_BOLTS_UNF_13279) {
            if (!SlayerManager.getInstance(player).flags.isBroadsUnlocked()) {
                sendDialogue(player, "You need to unlock the ability to create broad bolts.")
                return false
            }
        }
        if (getStatLevel(player, Skills.FLETCHING) < bolt.level) {
            sendDialogue(player, "You need a fletching level of " + bolt.level + " in order to do this.")
            return false
        }
        if (!inInventory(player, feather.id)) {
            return false
        }
        if (!inInventory(player, bolt.unfinished)) {
            return false
        }
        if (!hasSpaceFor(player, Item(bolt.finished))) {
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

        val unfinished = Item(bolt.unfinished)
        val featherAmount = amountInInventory(player, feather.id)
        val boltAmount: Int = amountInInventory(player, bolt.unfinished)

        if (featherAmount >= 10 && boltAmount >= 10) {
            feather.amount = 10
            unfinished.amount = 10
            sendMessage(player, "You fletch 10 " + getItemName(bolt.finished) + ".")
        } else {
            val amount = if (featherAmount > boltAmount) boltAmount else featherAmount
            feather.amount = amount
            unfinished.amount = amount
            sendMessage(player, if (amount == 1) "You attach a feather to a bolt." else "You fletch $amount " + getItemName(bolt.finished) + " bolts.")
        }

        if (removeItem(player, Item(feather.id, unfinished.amount))) {
            val product = Item(bolt.finished)
            product.amount = feather.amount
            rewardXP(player, Skills.FLETCHING, product.amount * bolt.experience)
            addItem(player, product.id, product.amount)
        }

        feather.amount = 1
        if (!inInventory(player, feather.id)) {
            return true
        }
        if (!inInventory(player, bolt.unfinished)) {
            return true
        }
        sets--
        return sets <= 0
    }

    override fun message(type: Int) {
    }

}