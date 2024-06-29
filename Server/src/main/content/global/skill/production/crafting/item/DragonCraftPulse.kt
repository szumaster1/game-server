package content.global.skill.production.crafting.item

import content.global.skill.production.crafting.data.LeatherData
import content.global.skill.production.crafting.data.LeatherData.DragonHide
import content.global.skill.production.crafting.data.LeatherData.decayThread
import content.global.skill.production.crafting.data.LeatherData.isLastThread
import content.global.skill.production.crafting.data.LeatherData.removeThread
import core.api.*
import core.api.consts.Animations
import core.cache.def.impl.ItemDefinition
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation
import core.utilities.StringUtils

class DragonCraftPulse(player: Player?, node: Item?, val hide: DragonHide, var amount: Int) : SkillPulse<Item?>(player, node) {

    var ticks = 0

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < hide.level) {
            sendDialogue(player, "You need a crafting level of " + hide.level + " to make " + ItemDefinition.forId(hide.product).name + ".")
            amount = 0
            return false
        }
        if (!player.inventory.contains(LeatherData.NEEDLE, 1)) {
            sendDialogue(player,"You need a needle to make this.")
            amount = 0
            return false
        }
        if (!player.inventory.containsItem(LeatherData.THREAD)) {
            sendDialogue(player,"You need thread to make this.")
            amount = 0
            return false
        }
        if (!player.inventory.contains(hide.leather, hide.amount)) {
            sendDialogue(player,"You need " + hide.amount + " " + ItemDefinition.forId(hide.leather).name.lowercase() + " to make this.")
            amount = 0
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
        if (removeItem(player, Item(hide.leather, hide.amount))) {
            if (hide.name.contains("VAMBS")) {
                sendMessage(player, "You make a pair of " + getItemName(hide.product).lowercase() + "'s.")
            } else {
                sendMessage(player, "You make " + (if (StringUtils.isPlusN(getItemName(hide.product).lowercase())) "an" else "a") + " " + getItemName(hide.product).lowercase() + ".")
            }
            val item = Item(hide.product)
            player.inventory.add(item)
            rewardXP(player, Skills.CRAFTING, hide.experience)
            decayThread(player)
            if (isLastThread(player)) {
                removeThread(player)
            }
            amount--
        }
        return amount < 1
    }

    override fun message(type: Int) {}

    companion object {
        private val ANIMATION = Animation.create(Animations.CRAFT_LEATHER_1249)
    }
}
