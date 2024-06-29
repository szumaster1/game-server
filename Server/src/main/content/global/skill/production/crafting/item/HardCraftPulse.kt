package content.global.skill.production.crafting.item

import content.global.skill.production.crafting.data.LeatherData
import content.global.skill.production.crafting.data.LeatherData.decayThread
import content.global.skill.production.crafting.data.LeatherData.isLastThread
import content.global.skill.production.crafting.data.LeatherData.removeThread
import core.api.*
import core.api.consts.Animations
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

class HardCraftPulse(player: Player?, node: Item?, var amount: Int) : SkillPulse<Item?>(player, node) {

    var ticks = 0

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < 28) {
            sendDialogue(player, "You need a crafting level of " + 28 + " to make a hardleather body.")
            return false
        }
        if (!inInventory(player, LeatherData.NEEDLE, 1)) {
            return false
        }
        if (!inInventory(player,LeatherData.HARD_LEATHER, 1)) {
            return false
        }
        if (!player.inventory.containsItem(LeatherData.THREAD)) {
            sendDialogue(player, "You need thread to make this.")
            return false
        }
        closeInterface(player)
        return true
    }

    override fun animate() {
        if (ticks % 5 == 0) {
            animate(player, ANIMATION)
        }
    }

    override fun reward(): Boolean {
        if (++ticks % 5 != 0) {
            return false
        }
        if (player.inventory.remove(Item(LeatherData.HARD_LEATHER))) {
            val item = Item(1131)
            player.inventory.add(item)
            rewardXP(player, Skills.CRAFTING, 35.0)
            decayThread(player)
            if (isLastThread(player)) {
                removeThread(player)
            }
        }
        amount--
        return amount < 1
    }

    companion object {
        private val ANIMATION = Animation.create(Animations.CRAFT_LEATHER_1249)
    }
}
