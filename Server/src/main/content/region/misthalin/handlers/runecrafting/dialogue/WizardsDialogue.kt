package content.region.misthalin.handlers.runecrafting.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Wizards dialogue.
 * @author Szumaster
 */
@Initializable
class WizardsDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.HAPPY, "Hello!").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("I want to join the orb project!", "Never mind.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player("I want to join the orb project!").also { stage = END_DIALOGUE }
                2 -> player("Never mind.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.WIZARD_8033, NPCs.WIZARD_8034, NPCs.WIZARD_8035, NPCs.WIZARD_8036, NPCs.WIZARD_8037, NPCs.WIZARD_8038, NPCs.WIZARD_8039, NPCs.WIZARD_8040)
    }

}
