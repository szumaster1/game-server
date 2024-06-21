package content.global.skill.production.fletching.item

import content.global.skill.production.fletching.data.Bolt
import content.global.skill.support.slayer.SlayerManager.Companion.getInstance
import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

class BoltPulse(player: Player?, node: Item?, bolt: Bolt, private val feather: Item, private var sets: Int) : SkillPulse<Item?>(player, node) {

    private val bolt: Bolt = bolt

    private val useSets = false

    override fun checkRequirements(): Boolean {
        if (bolt.unfinished.asItem().id == 13279) {
            if (!getInstance(player).flags.isBroadsUnlocked()) {
                sendDialogue(player, "You need to unlock the ability to create broad bolts.")
                return false
            }
        }
        if (getStatLevel(player, Skills.FLETCHING) < bolt.level) {
            sendDialogue(player, "You need a fletching level of " + bolt.level + " in order to do this.")
            return false
        }
        if (!player.inventory.containsItem(feather)) {
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
        val featherAmount = player.inventory.getAmount(feather)
        val boltAmount: Int = player.inventory.getAmount(bolt.unfinished)
        if (delay == 1) {
            super.setDelay(3)
        }
        val unfinished = Item(bolt.unfinished)
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
        if (player.inventory.remove(feather, unfinished)) {
            val product = Item(bolt.finished)
            product.amount = feather.amount
            rewardXP(player, Skills.FLETCHING, product.amount * bolt.experience)
            player.inventory.add(product)
        }
        feather.amount = 1
        if (!player.inventory.containsItem(feather)) {
            return true
        }
        if (!player.inventory.containsItem(Item(bolt.unfinished))) {
            return true
        }
        sets--
        return sets <= 0
    }

    override fun message(type: Int) {
    }

}