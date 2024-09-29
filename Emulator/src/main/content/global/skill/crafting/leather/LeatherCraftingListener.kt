package content.global.skill.crafting.leather

import content.global.skill.crafting.items.armour.leather.DragonCraftingPulse
import content.global.skill.crafting.items.armour.leather.HardLeatherCraftingPulse
import core.api.amountInInventory
import core.api.getStatLevel
import core.api.sendDialogue
import core.api.submitIndividualPulse
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import org.rs.consts.Items

class LeatherCraftingListener : InteractionListener {

    override fun defineListeners() {
        /*
         * Handles leather crafting.
         */

        onUseWith(IntType.ITEM, Leather.LEATHER, Leather.NEEDLE) { player, used, with ->
            Leather.SoftLeather.open(player)
            return@onUseWith true
        }

        /*
         * Handles hard leather crafting.
         */

        onUseWith(IntType.ITEM, Leather.HARD_LEATHER, Leather.NEEDLE) { player, used, _ ->
            if (getStatLevel(player, Skills.CRAFTING) < 28) {
                sendDialogue(player, "You need a crafting level of " + 28 + " to make a hardleather body.")
                return@onUseWith false
            }

            val handler: SkillDialogueHandler =
                object : SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, Item(Items.HARDLEATHER_BODY_1131)) {
                    override fun create(amount: Int, index: Int) {
                        submitIndividualPulse(
                            entity = player,
                            pulse = HardLeatherCraftingPulse(player, used.asItem(), amount)
                        )
                    }

                    override fun getAll(index: Int): Int {
                        return amountInInventory(player, Items.HARD_LEATHER_1743)
                    }
                }
            handler.open()
            return@onUseWith true
        }

        /*
         * Handles dragon leather crafting.
         */

        onUseWith(IntType.ITEM, DRAGON_LEATHER, Leather.NEEDLE) { player, used, _ ->
            val item = Leather.DragonHide.values()[used.id] ?: return@onUseWith true
            val handler: SkillDialogueHandler =
                object : SkillDialogueHandler(player, SkillDialogue.THREE_OPTION, Item(item.product)) {
                    override fun create(amount: Int, index: Int) {
                        submitIndividualPulse(
                            entity = player,
                            pulse = DragonCraftingPulse(player, used.asItem(), item, amount)
                        )
                    }

                    override fun getAll(index: Int): Int {
                        return amountInInventory(player, item.leather)
                    }
                }
            handler.open()
            return@onUseWith true
        }

    }


    companion object {
        private val DRAGON_LEATHER =
            intArrayOf(
                Leather.GREEN_LEATHER,
                Leather.BLUE_LEATHER,
                Leather.RED_LEATHER,
                Leather.BLACK_LEATHER
            )
    }
}
