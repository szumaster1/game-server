package content.global.skill.production.crafting.item

import content.global.skill.production.crafting.data.LeatherData
import content.global.skill.production.crafting.data.LeatherData.decayThread
import content.global.skill.production.crafting.data.LeatherData.isLastThread
import content.global.skill.production.crafting.data.LeatherData.removeThread
import content.global.skill.production.crafting.data.SnakeskinData
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

class SnakeskinCraftPulse(player: Player?, node: Item?, var amount: Int, val skin: SnakeskinData) : SkillPulse<Item?>(player, node) {

    var ticks = 0

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < skin.level) {
            sendDialogue(player, "You need a crafting level of " + skin.level + " to make this.")
            return false
        }
        if (!inInventory(player, LeatherData.NEEDLE, 1)) {
            return false
        }
        if (!player.inventory.containsItem(LeatherData.THREAD)) {
            player.dialogueInterpreter.sendDialogue("You need thread to make this.")
            return false
        }
        if (!inInventory(player, Items.SNAKESKIN_6289, skin.requiredAmount)) {
            sendDialogue(player, "You need " + skin.requiredAmount + " snakeskins in order to do this.")
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
        if (removeItem(player, Item(Items.SNAKESKIN_6289, skin.requiredAmount))) {
            val item = skin.product
            player.inventory.add(item)
            rewardXP(player, Skills.CRAFTING, skin.experience)
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
