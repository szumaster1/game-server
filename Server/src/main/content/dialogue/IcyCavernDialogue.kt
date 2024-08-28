package content.dialogue

import core.api.sendDialogueLines
import core.api.sendMessage
import core.game.dialogue.Dialogue
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Represents the Icy cavern dialogue.
 */
@Initializable
class IcyCavernDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        sendDialogueLines(player, "STOP! The creatures in this cave are VERY Dangerous. Are you", "sure you want to enter?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes, I'm not afraid of death!", "No thanks, I don't want to die!").also { stage++ }
            1 -> when (buttonId) {
                1 -> {
                    end()
                    player.properties.teleportLocation = LOCATION
                    sendMessage(player, "You venture into the icy cavern.")
                }
                2 -> end()
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(ID)
    }

    companion object {
        const val ID: Int = 238284
        private val LOCATION: Location = Location.create(3056, 9555, 0)
    }
}
