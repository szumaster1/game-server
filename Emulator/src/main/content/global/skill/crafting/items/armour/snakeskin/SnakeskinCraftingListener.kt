package content.global.skill.crafting.items.armour.snakeskin

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Components
import org.rs.consts.Items

class SnakeskinCraftingListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handles crafting snakeskin.
         * TODO: trim item names.
         */

        onUseWith(IntType.ITEM, Items.NEEDLE_1733, Items.SNAKESKIN_6289) { player, used, _ ->
            sendString(player, "Which snakeskin item would you like to make?", Components.SKILL_MAKE_306, 27)
            sendSkillDialogue(player) {
                withItems(
                    Items.SNAKESKIN_BODY_6322,
                    Items.SNAKESKIN_CHAPS_6324,
                    Items.SNAKESKIN_VBRACE_6330,
                    Items.SNAKESKIN_BANDANA_6326,
                    Items.SNAKESKIN_BOOTS_6328,
                )
                create { id, amount ->
                    val item = Snakeskin.forId(id)
                    submitIndividualPulse(
                        entity = player,
                        pulse = SnakeskinCraftingPulse(player, null, amount, item!!)
                    )
                }
                calculateMaxAmount {
                    amountInInventory(player, used.id)
                }

            }
            return@onUseWith true
        }
    }

}
