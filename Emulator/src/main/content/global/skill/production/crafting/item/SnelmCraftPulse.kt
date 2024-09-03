package content.global.skill.production.crafting.item

import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Represents the pulse of crafting a snelm helmet.
 *
 * @param node the item id.
 * @param item the required item id.
 */
class SnelmCraftPulse(player: Player?, node: Item?, item: IntArray) : SkillPulse<Item?>(player, node) {

    private val item: IntArray

    init {
        delay = 1
        this.item = item
    }

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < 15) {
            sendDialogue(player, "You need a Crafting level of at least 15 in order to do this.")
            return false
        }
        return true
    }

    override fun animate() {}

    override fun reward(): Boolean {
        sendMessage(player, "You craft the shell into a helmet.")
        replaceSlot(player, Item(item[1]).index, Item(node!!.slot))
        rewardXP(player, Skills.CRAFTING, 32.5)
        return true
    }
}
