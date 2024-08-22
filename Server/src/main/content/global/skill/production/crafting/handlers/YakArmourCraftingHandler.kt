package content.global.skill.production.crafting.handlers

import content.global.skill.production.crafting.data.LeatherData
import content.global.skill.production.crafting.data.LeatherData.decayThread
import content.global.skill.production.crafting.data.LeatherData.isLastThread
import content.global.skill.production.crafting.data.LeatherData.removeThread
import core.api.*
import cfg.consts.Animations
import cfg.consts.Items
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Plugin

/**
 * Yak armour crafting handler.
 */
class YakArmourCraftingHandler : UseWithHandler(Items.NEEDLE_1733) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(Items.CURED_YAK_HIDE_10820, ITEM_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        val dialogue: SkillDialogueHandler =
            object : SkillDialogueHandler(player, SkillDialogue.TWO_OPTION, LEGS, BODY) {
                override fun create(amount: Int, index: Int) {
                    player.pulseManager.run(YakArmourPulse(player, if (index == 1) BODY else LEGS, index, amount))
                }

                override fun getAll(index: Int): Int {
                    return amountInInventory(event.player, event.usedItem.id)
                }
            }
        dialogue.open()
        return true
    }

    /**
     * Yak armour pulse
     *
     * @param index Represents the index of the yak armour pulse.
     * @param amount Represents the amount of yak armour.
     * @constructor Represents a YakArmourPulse instance.
     *
     * @param player The player associated with the yak armour pulse.
     * @param node The item node associated with the yak armour pulse.
     */
    inner class YakArmourPulse(player: Player?, node: Item?, private val index: Int, private var amount: Int) :
        SkillPulse<Item?>(player, node) {
        private var ticks = 0
        override fun checkRequirements(): Boolean {
            val level = if (index == 1) 46 else 43
            if (getStatLevel(player, Skills.CRAFTING) < level) {
                sendDialogue(player, "You need a Crafting level of at least $level in order to do this.")
                return false
            }
            if (!inInventory(player, LeatherData.NEEDLE, 1)) {
                return false
            }
            if (!inInventory(player, LeatherData.THREAD.id)) {
                sendDialogue(player, "You need some thread to make anything out of leather.")
                return false
            }
            val requiredAmount = if (index == 1) 2 else 1
            if (!inInventory(player, Items.CURED_YAK_HIDE_10820, requiredAmount)) {
                sendDialogue(player, "You don't have the required amount of yak-hide in order to do this.")
                return false
            }
            closeInterface(player)
            return true
        }

        override fun animate() {
            if (ticks % 5 == 0) {
                animate(player, Animations.CRAFT_LEATHER_1249)
            }
        }

        override fun reward(): Boolean {
            if (++ticks % 5 != 0) {
                return false
            }
            val requiredAmount = if (index == 1) 2 else 1
            if (removeItem(player, Item(Items.CURED_YAK_HIDE_10820, requiredAmount))) {
                player.inventory.add(node)
                rewardXP(player, Skills.CRAFTING, 32.0)
                decayThread(player)
                if (isLastThread(player)) {
                    removeThread(player)
                }
                sendMessage(player, "You make " + node!!.name.lowercase() + ".")
            }
            amount--
            return amount < 1
        }
    }

    companion object {
        private val BODY = Item(Items.YAK_HIDE_ARMOUR_10822)
        private val LEGS = Item(Items.YAK_HIDE_ARMOUR_10824)
    }
}
