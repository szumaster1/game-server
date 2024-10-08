package content.global.skill.smithing

import content.global.skill.smithing.SmithingType.Companion.forButton
import core.api.sendInputDialogue
import core.api.submitIndividualPulse
import core.game.interaction.InterfaceListener
import core.game.node.item.Item
import org.rs.consts.Components

/**
 * Represents the smithing interface.
 */
class SmithingInterface : InterfaceListener {

    override fun defineInterfaceListeners() {

        /*
         * Handles the new smithing interface.
         */

        on(Components.SMITHING_NEW_300) { player, _, _, buttonID, _, _ ->
            val item = Bars.getItemId(buttonID, player.gameAttributes.getAttribute<Any>("smith-type") as BarType)
            val bar = Bars.forId(item) ?: return@on true
            val amount = forButton(player, bar, buttonID, bar.barType.bar)
            player.gameAttributes.setAttribute("smith-bar", bar)
            player.gameAttributes.setAttribute("smith-item", item)
            if (amount == -1) {
                sendInputDialogue(player, true, "Enter the amount:") { value: Any ->
                    submitIndividualPulse(player,
                        SmithingPulse(
                            player,
                            Item(player.gameAttributes.getAttribute<Any>("smith-item") as Int, value as Int),
                            player.gameAttributes.getAttribute<Any>("smith-bar") as Bars,
                            value
                        )
                    )
                }
                return@on true
            }
            submitIndividualPulse(player, SmithingPulse(player, Item(item, amount), Bars.forId(item)!!, amount))
            return@on true
        }

    }
}
