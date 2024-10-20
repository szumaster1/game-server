package content.global.skill.construction.decoration.bedroom

import core.api.openChatbox
import core.api.sendItemDialogue
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Components
import org.rs.consts.Scenery
import java.text.SimpleDateFormat
import java.util.*

class ClockListener : InteractionListener {

    private val clockSpaceFurniture = intArrayOf(Scenery.CLOCK_13169, Scenery.CLOCK_13170, Scenery.CLOCK_13171)

    override fun defineListeners() {

        on(clockSpaceFurniture, IntType.SCENERY, "read") { player, node ->
            val format = SimpleDateFormat("mm")
            val minuteDisplay = format.format(Calendar.getInstance().time).toInt()
            val sb = StringBuilder("It's ")
            when (minuteDisplay) {
                0 -> {
                    sb.append("Rune o'clock.")
                }

                15 -> {
                    sb.append("a quarter past Rune.")
                }

                in 1..29 -> {
                    sb.append("$minuteDisplay past Rune.")
                }

                45 -> {
                    sb.append("a quarter till Rune.")
                }

                else -> {
                    sb.append((60 - minuteDisplay).toString() + " till Rune.")
                }
            }
            sendItemDialogue(player, node.asScenery().id - 5117, sb.toString())
            return@on true
        }
    }

}
