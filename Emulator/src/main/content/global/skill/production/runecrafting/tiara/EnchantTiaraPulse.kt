package content.global.skill.production.runecrafting.tiara

import org.rs.consts.Items
import content.global.skill.production.runecrafting.data.Talisman
import content.global.skill.production.runecrafting.data.TalismanStaff
import content.global.skill.production.runecrafting.data.Altar
import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import kotlin.math.min

/**
 * Represents the pulse of creating tiara.
 */
class EnchantTiaraPulse(player: Player?, val talisman: Talisman, val altar: Altar, val tiara: TalismanStaff, var amount: Int) : SkillPulse<Item>(player, null) {

    override fun checkRequirements(): Boolean {
        if (!inInventory(player, Items.TIARA_5525)) {
            sendMessage(player, "You need a tiara.")
            return false
        }
        return true
    }

    override fun start() {
        super.start()
        val tiaraAmt = amountInInventory(player, Items.TIARA_5525)
        val talismanAmt: Int = player.inventory.getAmount(talisman.talisman)
        amount = min(min(tiaraAmt, talismanAmt), amount)
    }

    override fun animate() {
    }

    override fun reward(): Boolean {
        if (removeItem(player, Items.TIARA_5525) && player.inventory.remove(talisman.talisman)) {
            player.inventory.add(Item(tiara.staff.item))
            rewardXP(player, Skills.RUNECRAFTING, talisman.tiara!!.experience)

            return --amount == 0
        }
        return true
    }

    override fun message(type: Int) {
        if (type == 1) {
            sendMessage(player, "You bind the power of the talisman into your tiara.")
        }
    }

}
