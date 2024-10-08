package content.region.kandarin.baxtorian.handlers.barbariantraining.smithing

import core.api.*
import org.rs.consts.Animations
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import kotlin.math.min

class BarbSmithingPulse(player: Player?, val weapon: BarbarianWeapon, var amount: Int, var button: Int) : SkillPulse<Item>(player, null) {

    override fun checkRequirements(): Boolean {
        if (!inInventory(player, weapon.requiredWood)) {
            sendDialogue(player, "You don't have the necessary logs for the weapon.")
            return false
        }

        if (!inInventory(player, weapon.requiredBar)) {
            return false
        }
        return true
    }

    override fun start() {
        super.start()
        val bar = Item(weapon.requiredBar)
        val wood = Item(weapon.requiredWood)
        val barsAmount = player.inventory.getAmount(bar)
        val woodAmount: Int = player.inventory.getAmount(wood)
        amount = min(min(barsAmount, woodAmount), amount)
    }

    override fun animate() {
        animate(player, Animations.HUMAN_ANVIL_HAMMER_SMITHING_898)
    }

    override fun reward(): Boolean {
        if (delay == 1) {
            delay = 4
            return false
        }
        val index = button
        if (player.inventory.remove(Item(weapon.requiredBar, 1)) && player.inventory.remove(Item(weapon.requiredWood, 1))) {
            sendMessage(player, "You make a ${getItemName(if (index != 2) weapon.hastaId else weapon.spearId)}.")
            rewardXP(player, Skills.SMITHING, weapon.experience)
            player.inventory.add(Item(if (index != 2) weapon.hastaId else weapon.spearId, 1))
            amount--
            return amount == 0
        }
        return true
    }

    override fun message(type: Int) {}

}