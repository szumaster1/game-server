package content.global.skill.crafting

import core.api.*
import org.rs.consts.Animations
import org.rs.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Represents the pulse used to craft snake skin.
 */
class SnakeskinCraftPulse(player: Player?, node: Item?, var amount: Int, val skin: Snakeskin) :
    SkillPulse<Item?>(player, node) {

    var ticks = 0

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < skin.level) {
            sendDialogue(player, "You need a crafting level of " + skin.level + " to make this.")
            return false
        }
        if (!inInventory(player, Leather.NEEDLE, 1)) {
            return false
        }
        if (!inInventory(player, Leather.THREAD.id)) {
            sendDialogue(player, "You need thread to make this.")
            return false
        }
        if (!inInventory(player, Items.SNAKESKIN_6289, skin.amount)) {
            sendDialogue(player, "You need " + skin.amount + " snakeskins in order to do this.")
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
        if (removeItem(player, Item(Items.SNAKESKIN_6289, skin.amount))) {
            val item = skin.product
            addItem(player, item.id)
            rewardXP(player, Skills.CRAFTING, skin.experience)
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
