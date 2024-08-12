package content.global.skill.production.fletching.item

import content.global.skill.production.fletching.data.KebbitBolt
import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.GameWorld.ticks

/**
 * Kebbit bolt pulse
 *
 * @property bolts Represents the type of Kebbit bolt used in the pulse.
 * @property amount Indicates the quantity of Kebbit bolts involved in the pulse.
 * @constructor Initializes a new instance of KebbitBoltPulse.
 *
 * @param player The player associated with this pulse, can be null.
 * @param node The item node that this pulse interacts with.
 */
class KebbitBoltPulse(player: Player?, node: Item, private val bolts: KebbitBolt, private var amount: Int) : SkillPulse<Item?>(player, node) {

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.FLETCHING) < bolts.level) {
            sendDialogue(player, "You need a fletching level of " + bolts.level + " to do this.")
            return false
        }
        if (!hasSpaceFor(player, Item(bolts.product))) {
            sendDialogue(player, "You do not have enough inventory space.")
            return false
        }

        if(!inInventory(player, bolts.base)){
            sendDialogue(player, "You don't have required items in your inventory.")
            return false
        }
        return true
    }

    override fun animate() {
        animate(player, 4433)
    }

    override fun reward(): Boolean {
        if (++ticks % 3 != 0) {
            return false
        }
        val base = Item(bolts.base, 1)
        val product = bolts.product
        if (player.inventory.remove(base)) {
            addItem(player, product,6)
            rewardXP(player, Skills.FLETCHING, bolts.experience)
        }
        amount--
        return amount == 0
    }

    override fun message(type: Int) {}

}