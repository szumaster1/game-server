package content.region.fremennik.handlers.neitiznot

import core.api.*
import core.api.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

class FremennikShieldPulse(player: Player?, val item: Item, var amount: Int) :
    SkillPulse<Item>(player, Item(Items.FREMENNIK_ROUND_SHIELD_10826)) {

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.WOODCUTTING) < 54) {
            sendDialogue(player, "You need a woodcutting level of at least 54 to make a Fremennik round shield.")
            return false
        }

        if (!inInventory(player, Items.HAMMER_2347) || !inInventory(player, Items.ARCTIC_PINE_LOGS_10810, 2) || !inInventory(player, Items.ROPE_954) || !inInventory(player, Items.BRONZE_NAILS_4819)) {
            sendDialogue(player, "You don't have required items in your inventory.")
            return false
        }

        return true
    }

    override fun animate() {
        animate(player, Animation(5755))
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