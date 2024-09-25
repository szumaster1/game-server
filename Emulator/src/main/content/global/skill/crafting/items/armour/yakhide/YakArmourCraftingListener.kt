package content.global.skill.crafting.items.armour.yakhide

import core.api.amountInInventory
import core.api.submitIndividualPulse
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import org.rs.consts.Items

class YakArmourCraftingListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.NEEDLE_1733, Items.CURED_YAK_HIDE_10820) { player, used, _ ->
            val dialogue: SkillDialogueHandler =
                object : SkillDialogueHandler(player, SkillDialogue.TWO_OPTION, LEGS, BODY) {
                    override fun create(amount: Int, index: Int) {
                        submitIndividualPulse(
                            entity = player,
                            pulse = YakArmourCraftingPulse(player, if (index == 1) BODY else LEGS, index, amount)
                        )
                    }

                    override fun getAll(index: Int): Int {
                        return amountInInventory(player, used.id)
                    }
                }
            dialogue.open()
            return@onUseWith true
        }
    }

    companion object {
        private val BODY = Item(Items.YAK_HIDE_ARMOUR_10822)
        private val LEGS = Item(Items.YAK_HIDE_ARMOUR_10824)
    }
}
