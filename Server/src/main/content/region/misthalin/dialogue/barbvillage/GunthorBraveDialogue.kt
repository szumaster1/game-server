package content.region.misthalin.dialogue.barbvillage

import cfg.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

/**
 * Represents the Gunthor brave dialogue.
 */
@Initializable
class GunthorBraveDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc("You look funny.").also {
            end()
            npc.properties.combatPulse.attack(player)
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return GunthorBraveDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.GUNTHOR_THE_BRAVE_199)
    }

}
