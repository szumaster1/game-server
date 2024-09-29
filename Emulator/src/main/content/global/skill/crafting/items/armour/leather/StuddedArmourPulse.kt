package content.global.skill.crafting.items.armour.leather

import content.global.skill.smithing.Bars
import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import org.rs.consts.Animations
import org.rs.consts.Items

/**
 * Represents a pulse for studded armor.
 */
class StuddedArmourPulse(player: Player?, node: Item?, private val armour: StuddedArmourListener.StuddedArmour?, private var amount: Int) : SkillPulse<Item?>(player, node) {
    private var ticks = 0

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < armour!!.level) {
            sendMessage(player, "You need a crafting level of at least " + armour.level + " to do this.")
            return false
        }
        if (!inInventory(player, Bars.STEEL_STUDS.product)) {
            sendMessage(player, "You need studs in order to make studded armour.")
            return false
        }
        return inInventory(player, armour.item.id)
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
        if (player.inventory.remove(armour!!.item, STEEL_STUDS)) {
            player.inventory.add(armour.studded)
            rewardXP(player, Skills.CRAFTING, armour.experience)
            sendMessage(player, "You make a " + armour.studded.name.lowercase() + ".")
        }
        amount--
        return amount < 1
    }

    override fun message(type: Int) {
        when (type) {
            0 -> sendMessage(player, "You use the studs with the " + node!!.name.lowercase() + ".")
        }
    }

    companion object {
        private const val ANIMATION = Animations.CRAFT_LEATHER_1249
        private val STEEL_STUDS = Item(Items.STEEL_STUDS_2370)
    }
}