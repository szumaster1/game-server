package content.region.misc.keldagrim.dialogue.boatman

import core.api.*
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.map.Location
import core.plugin.Initializable
import core.tools.END_DIALOGUE
import core.tools.START_DIALOGUE
import org.rs.consts.Components
import org.rs.consts.NPCs

/**
 * Represents the Dwarven boatman back dialogue.
 */
@Initializable
class DwarvenBoatmanBackDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> npcl(FacialExpression.OLD_HAPPY, "Want me to take you back to the mines?").also { stage++ }
            1 -> showTopics(Topic(FacialExpression.FRIENDLY, "Yes, please take me.", 2), Topic(FacialExpression.FRIENDLY, "What, on your ship! No thanks!", 3))
            2 -> {
                end()
                submitWorldPulse(TravelBackPulse(player))
            }
            3 -> npcl(FacialExpression.OLD_NORMAL, "Hey now, it was only a slight accident!").also { stage++ }
            4 -> playerl(FacialExpression.FRIENDLY, "A slight accident?? Have you any idea how much time I spent rebuilding that statue?").also { stage++ }
            5 -> npcl(FacialExpression.OLD_NORMAL, "Calm down, calm down! You got what you paid for the trip, didn't you?").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DWARVEN_BOATMAN_2206)
    }
}

/**
 * Represents the Travel back pulse.
 */
class TravelBackPulse(val player: Player) : Pulse(1) {
    var counter = 0
    override fun pulse(): Boolean {
        when (counter++) {
            0 -> lock(player, 10).also {
                openInterface(player, Components.FADE_TO_BLACK_120)
            }
            3 -> teleport(player, Location.create(2838, 10127, 0))
            4 -> {
                closeInterface(player)
                openInterface(player, Components.FADE_FROM_BLACK_170)
            }
            6 -> unlock(player).also {
                closeInterface(player)
                return true
            }
        }
        return false
    }
}
