package content.region.misthalin.handlers.rcguild.dialogue

import core.api.consts.NPCs
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class WizardViefDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Member of the Runecrafting Guild. She is also the leader
     * of the yellow team in The Great Orb Project minigame.
     * Players may speak to her to join her team.
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.HAPPY, "Ah! You'll help me, won't you?").also { stage = END_DIALOGUE }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.WIZARD_VIEF_8030)
    }

}
