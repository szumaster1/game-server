package content.global.skill.crafting.items.armour.leather

import core.api.*
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import org.rs.consts.Components
import org.rs.consts.Items

class HardLeatherCraftingListener : InteractionListener {


    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.HARD_LEATHER_1743, Items.NEEDLE_1733) { player, used, _ ->
            if (getStatLevel(player, Skills.CRAFTING) < 28) {
                sendDialogue(player, "You need a crafting level of " + 28 + " to make a hard leather body.")
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
            setInterfaceSprite(player, Components.SKILL_MULTI1_309, 2, 210, 16)
            return@onUseWith true
        }
    }
}