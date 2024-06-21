package content.global.skill.production.crafting.granite

import core.api.addDialogueAction
import core.api.consts.Items
import core.api.sendDialogueOptions
import core.api.setTitle
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item

class GraniteListeners : InteractionListener {

    val GRANITE = intArrayOf(Items.GRANITE_2KG_6981, Items.GRANITE_5KG_6983)

    override fun defineListeners() {

        onUseWith(IntType.ITEM, GRANITE, Items.CHISEL_1755) { player, used, _ ->
            setTitle(player, 2)
            sendDialogueOptions(player, "What would you like to do?", "Split the block into smaller pieces.", "Nothing.")
            addDialogueAction(player) { _, button ->
                if (button == 2) {
                    player.pulseManager.run(GranitePulse(player, Item(used.id)))
                }
                return@addDialogueAction
            }
            return@onUseWith true
        }
    }
}