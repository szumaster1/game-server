package content.global.skill.production.crafting.item

import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

class SnelmCraftPulse(player: Player?, node: Item?, data: IntArray) : SkillPulse<Item?>(player, node) {

    private val data: IntArray

    init {
        delay = 1
        this.data = data
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
        replaceSlot(player, Item(data[1]).index, Item(node!!.slot))
        rewardXP(player, Skills.CRAFTING, 32.5)
        return true
    }
}
