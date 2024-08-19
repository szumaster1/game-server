package content.minigame.pestcontrol.dialogue

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Squire Magic dialogue.
 */
@Initializable
class SquireMagicDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc("Hi, how can I help you?")
        stage = 0
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                options("What do you have for sale?", "I'm fine thanks.")
                stage = 1
            }

            1 -> when (buttonId) {
                1 -> {
                    end()
                    openNpcShop(player, npc.id)
                }

                2 -> {
                    player("I'm fine thanks.")
                    stage = 20
                }
            }

            10 -> end()
            20 -> end()
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SQUIRE_3796, NPCs.SQUIRE_3798, NPCs.SQUIRE_3799)
    }
}
