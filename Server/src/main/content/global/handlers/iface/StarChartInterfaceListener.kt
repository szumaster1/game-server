package content.global.handlers.iface

import core.api.consts.Components
import core.api.setInterfaceText
import core.game.interaction.InterfaceListener

class StarChartInterfaceListener : InterfaceListener {

    override fun defineInterfaceListeners() {
        on(Components.STAR_CHART_104) { player, _, _, buttonID, _, _ ->
            val button = (19..30)
            val zodiac = mapOf(
                19 to "Aquarius",
                20 to "Aries",
                21 to "Cancer",
                22 to "Capricorn",
                23 to "Gemini",
                24 to "Leo",
                25 to "Libra",
                26 to "Pisces",
                27 to "Sagittarius",
                28 to "Scorpio",
                29 to "Taurus",
                30 to "Virgo",
            )
            if (buttonID in button) {
                for (i in zodiac.entries) {
                    setInterfaceText(player, zodiac[buttonID].toString(), 104, 57)
                }
            }
            return@on true
        }
    }
}
