package content.global.skill.fletching.items.crossobw

import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Represents the skill pulse of attaching limbs.
 */
class LimbPulse(player: Player?, node: Item, private val limb: Limb, private var amount: Int) :
    SkillPulse<Item?>(player, node) {

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.FLETCHING) < limb.level) {
            sendDialogue(player, "You need a fletching level of " + limb.level + " to attach these limbs.")
            return false
        }
        if (!inInventory(player, limb.limb)) {
            sendDialogue(player, "That's not the correct limb to attach.")
            return false
        }
        if (!inInventory(player, limb.stock)) {
            sendDialogue(player, "That's not the correct stock for that limb.")
            return false
        }
        return inInventory(player, limb.stock)
    }

    override fun animate() {
        animate(player, limb.animation)
    }

    override fun reward(): Boolean {
        if (delay == 1) {
            super.setDelay(6)
            return false
        }
        if (player.inventory.remove(Item(limb.stock), Item(limb.limb))) {
            addItem(player, limb.product)
            rewardXP(player, Skills.FLETCHING, limb.experience)
            sendMessage(player, "You attach the metal limbs to the stock.")
        }
        if (!inInventory(player, limb.limb)) {
            return true
        }
        amount--
        return amount == 0
    }

    override fun message(type: Int) {}
}