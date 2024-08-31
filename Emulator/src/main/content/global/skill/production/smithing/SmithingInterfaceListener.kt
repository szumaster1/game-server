package content.global.skill.production.smithing

import content.global.skill.production.smithing.data.BarType
import content.global.skill.production.smithing.data.Bars
import content.global.skill.production.smithing.data.SmithingType.Companion.forButton
import content.global.skill.production.smithing.item.SmithingPulse
import cfg.consts.Components
import core.api.sendInputDialogue
import core.game.interaction.InterfaceListener
import core.game.node.item.Item

/**
 * Smithing interface listener.
 */
class SmithingInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.SMITHING_NEW_300) { player, _, _, buttonID, _, _ ->
            val item = Bars.getItemId(buttonID, player.gameAttributes.getAttribute<Any>("smith-type") as BarType)
            val bar = Bars.forId(item) ?: return@on true
            val amount = forButton(player, bar, buttonID, bar.barType.bar)
            player.gameAttributes.setAttribute("smith-bar", bar)
            player.gameAttributes.setAttribute("smith-item", item)
            if (amount == -1) {
                sendInputDialogue(player, true, "Enter the amount:") { value: Any ->
                    player.pulseManager.run(
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
            player.pulseManager.run(SmithingPulse(player, Item(item, amount), Bars.forId(item)!!, amount))
            return@on true
        }

    }
}
