package content.region.fremennik.handlers.neitiznot

import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

/**
 * Represents a pulse for Fremennik shield.
 *
 * @param player The player associated with the pulse.
 * @param item The item associated with the pulse.
 * @param amount The amount of the item.
 * @constructor Initializes the FremennikShieldPulse.
 */
class FremennikShieldPulse(player: Player?, val item: Item, var amount: Int) : SkillPulse<Item>(player, Item(Items.FREMENNIK_ROUND_SHIELD_10826)) {

    override fun checkRequirements(): Boolean {
        if (!anyInInventory(player, Items.HAMMER_2347, Items.ARCTIC_PINE_LOGS_10810, Items.ROPE_954, Items.BRONZE_NAILS_4819)) {
            sendMessage(player, "You don't have required items in your inventory.")
            return false
        }
        if (amountInInventory(player, Items.ARCTIC_PINE_LOGS_10810) < 2) {
            sendMessage(player, "You need at least 2 arctic pine logs to do this.")
            return false
        }
        if (!inInventory(player, Items.ROPE_954)) {
            sendMessage(player, "You will need a rope in order to do this.")
            return false
        }
        if (!inInventory(player, Items.HAMMER_2347) && inInventory(player, Items.BRONZE_NAILS_4819)) {
            sendMessage(player, "You need a hammer to force the nails in with.")
            return false
        }
        if(!inInventory(player, Items.BRONZE_NAILS_4819)){
            sendMessage(player, "You need bronze nails for this.")
            return false
        }
        return true
    }

    override fun animate() {
        animate(player, Animation(Animations.HUMAN_SPLIT_LOGS_5755))
    }

    override fun reward(): Boolean {
        if (delay == 1) {
            delay = 3
            return false
        }

        if (player.inventory.remove(Item(Items.ARCTIC_PINE_LOGS_10810, 2)) && player.inventory.remove(Item(Items.ROPE_954, 1)) && player.inventory.remove(Item(Items.BRONZE_NAILS_4819, 1))) {
            rewardXP(player, Skills.CRAFTING, 34.0)
            player.inventory.add(Item(Items.FREMENNIK_ROUND_SHIELD_10826, 1))
            sendMessage(player, "You make a Fremennik round shield.")
            amount--
            return amount == 0
        }

        return true
    }

    override fun message(type: Int) {}
}