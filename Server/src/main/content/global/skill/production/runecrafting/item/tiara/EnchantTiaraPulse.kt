package content.global.skill.production.runecrafting.item.tiara

import content.global.skill.production.runecrafting.data.Altar
import content.global.skill.production.runecrafting.data.Talisman
import content.global.skill.production.runecrafting.data.TalismanStaff
import core.api.consts.Items
import core.api.rewardXP
import core.api.sendMessage
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import kotlin.math.min

/**
 * Enchant tiara pulse
 *
 * @property talisman Represents the talisman used in the enchantment process.
 * @property altar Represents the altar where the enchantment takes place.
 * @property tiara Represents the tiara that is being enchanted.
 * @property amount Represents the number of times the enchantment will be applied.
 * @constructor
 *
 * @param player The player who is performing the enchantment.
 */
class EnchantTiaraPulse(
    player: Player?, // The player initiating the enchantment, can be null if not applicable.
    val talisman: Talisman, // The talisman used for the enchantment, must be provided.
    val altar: Altar, // The altar where the enchantment occurs, must be provided.
    val tiara: TalismanStaff, // The tiara that is being enchanted, must be provided.
    var amount: Int, // The number of enchantments to perform, can be modified.
) : SkillPulse<Item>(player, null) { // Inherits from SkillPulse, passing player and null as parameters.

    private val plainTiara = Item(Items.TIARA_5525)

    override fun checkRequirements(): Boolean {
        if (!player.inventory.containsItem(plainTiara)) {
            sendMessage(player, "You need a tiara.")
            return false
        }
        return true
    }

    override fun start() {
        super.start()
        val tiaraAmt = player.inventory.getAmount(plainTiara)
        val talismanAmt: Int = player.inventory.getAmount(talisman.talisman)
        amount = min(min(tiaraAmt, talismanAmt), amount)
    }

    override fun animate() {
    }

    override fun reward(): Boolean {
        if (player.inventory.remove(plainTiara) && player.inventory.remove(talisman.talisman)) {
            player.inventory.add(tiara.staff.item)
            rewardXP(player, Skills.RUNECRAFTING, talisman.getTiara()!!.experience)

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
