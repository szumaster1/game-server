package content.global.skill.production.smithing

import content.global.skill.production.smithing.data.Bar
import content.global.skill.production.smithing.item.SmeltingPulse
import org.rs.consts.Components
import core.api.sendInputDialogue
import core.api.submitIndividualPulse
import core.game.interaction.InterfaceListener

/**
 * Represents the smelting interface.
 */
class SmeltingInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {

        /*
         * Open the smelting interface.
         */

        on(Components.SMELTING_311) { player, _, _, buttonID, _, _ ->
            val barType = BarButton.forId(buttonID) ?: return@on true
            if (barType.amount == -1) {
                player.interfaceManager.closeChatbox()
                sendInputDialogue(player, true, "Enter the amount:") { value: Any ->
                    if (value is String) {
                        submitIndividualPulse(player, SmeltingPulse(player, null, barType.bar, value.toInt()))
                    } else {
                        submitIndividualPulse(player, SmeltingPulse(player, null, barType.bar, value as Int))
                    }
                }
            } else {
                player.pulseManager.run(SmeltingPulse(player, null, barType.bar, barType.amount))
            }
            return@on true
        }
    }

    /**
     * Bar button.
     *
     * @param button    the button id.
     * @param bar       the bar.
     * @param amount    the amount.
     */
    enum class BarButton(val button: Int, val bar: Bar, val amount: Int) {
        BRONZE_1(
            button = 16,
            bar = Bar.BRONZE,
            amount = 1
        ),
        BRONZE_5(
            button = 15,
            bar = Bar.BRONZE,
            amount = 5
        ),
        BRONZE_10(
            button = 14,
            bar = Bar.BRONZE,
            amount = 10
        ),
        BRONZE_X(
            button = 13,
            bar = Bar.BRONZE,
            amount = -1
        ),
        BLURITE_1(
            button = 20,
            bar = Bar.BLURITE,
            amount = 1
        ),
        BLURITE_5(
            button = 19,
            bar = Bar.BLURITE,
            amount = 5
        ),
        BLURITE_10(
            button = 18,
            bar = Bar.BLURITE,
            amount = 10
        ),
        BLURITE_X(
            button = 17,
            bar = Bar.BLURITE,
            amount = -1
        ),
        IRON_1(
            button = 24,
            bar = Bar.IRON,
            amount = 1
        ),
        IRON_5(
            button = 23,
            bar = Bar.IRON,
            amount = 5
        ),
        IRON_10(
            button = 22,
            bar = Bar.IRON,
            amount = 10
        ),
        IRON_X(
            button = 21,
            bar = Bar.IRON,
            amount = -1
        ),
        SILVER_1(
            button = 28,
            bar = Bar.SILVER,
            amount = 1
        ),
        SILVER_5(
            button = 27,
            bar = Bar.SILVER,
            amount = 5
        ),
        SILVER_10(
            button = 26,
            bar = Bar.SILVER,
            amount = 10
        ),
        SILVER_X(
            button = 25,
            bar = Bar.SILVER,
            amount = -1
        ),
        STEEL_1(
            button = 32,
            bar = Bar.STEEL,
            amount = 1
        ),
        STEEL_5(
            button = 31,
            bar = Bar.STEEL,
            amount = 5
        ),
        STEEL_10(
            button = 30,
            bar = Bar.STEEL,
            amount = 10
        ),
        STEEL_X(
            button = 29,
            bar = Bar.STEEL,
            amount = -1
        ),
        GOLD_1(
            button = 36,
            bar = Bar.GOLD,
            amount = 1
        ),
        GOLD_5(
            button = 35,
            bar = Bar.GOLD,
            amount = 5
        ),
        GOLD_10(
            button = 34,
            bar = Bar.GOLD,
            amount = 10
        ),
        GOLD_X(
            button = 33,
            bar = Bar.GOLD,
            amount = -1
        ),
        MITHRIL_1(
            button = 40,
            bar = Bar.MITHRIL,
            amount = 1
        ),
        MITHRIL_5(
            button = 39,
            bar = Bar.MITHRIL,
            amount = 5
        ),
        MITHRIL_10(
            button = 38,
            bar = Bar.MITHRIL,
            amount = 10
        ),
        MITHRIL_X(
            button = 37,
            bar = Bar.MITHRIL,
            amount = -1
        ),
        ADAMANT_1(
            button = 44,
            bar = Bar.ADAMANT,
            amount = 1
        ),
        ADAMANT_5(
            button = 43,
            bar = Bar.ADAMANT,
            amount = 5
        ),
        ADAMANT_10(
            button = 42,
            bar = Bar.ADAMANT,
            amount = 10
        ),
        ADAMANT_X(
            button = 41,
            bar = Bar.ADAMANT,
            amount = -1
        ),
        RUNE_1(
            button = 48,
            bar = Bar.RUNITE,
            amount = 1
        ),
        RUNE_5(
            button = 47,
            bar = Bar.RUNITE,
            amount = 5
        ),
        RUNE_10(
            button = 46,
            bar = Bar.RUNITE,
            amount = 10
        ),
        RUNE_X(
            button = 45,
            bar = Bar.RUNITE,
            amount = -1
        );


        companion object {

            /**
             * Gets the [bar] for the [button].
             *
             * @param id the button id.
             * @return the [BarButton].
             */
            @JvmStatic
            fun forId(id: Int): BarButton? {
                for (button in BarButton.values()) {
                    if (button.button == id) {
                        return button
                    }
                }
                return null
            }
        }
    }
}
