package content.global.skill.crafting.items.armour.leather

import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import org.rs.consts.Animations
import org.rs.consts.Items

/**
 * Represents a pulse used to craft hard leather.
 */
class HardLeatherCraftingPulse(player: Player?, node: Item?, var amount: Int) : SkillPulse<Item?>(player, node) {

    var ticks = 0

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < 28) {
            sendDialogue(player, "You need a crafting level of " + 28 + " to make a hardleather body.")
            return false
        }
        if (!inInventory(player, Leather.NEEDLE, 1)) {
            return false
        }
        if (!inInventory(player, Leather.HARD_LEATHER, 1)) {
            return false
        }
        if (!inInventory(player, Leather.THREAD.id)) {
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
        if (removeItem(player, Item(Leather.HARD_LEATHER))) {
            val item = Item(Items.HARDLEATHER_BODY_1131)
            addItem(player, item.id)
            rewardXP(player, Skills.CRAFTING, 35.0)
            Leather.decayThread(player)
            if (Leather.isLastThread(player)) {
                Leather.removeThread(player)
            }
        }
        amount--
        return amount < 1
    }

    companion object {
        private const val ANIMATION = Animations.CRAFT_LEATHER_1249
    }
}
