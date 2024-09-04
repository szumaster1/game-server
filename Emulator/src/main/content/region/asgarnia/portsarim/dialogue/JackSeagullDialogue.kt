package content.region.asgarnia.portsarim.dialogue

import cfg.consts.NPCs
import core.api.sendDialogueOptions
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Jack seagull dialogue.
 */
@Initializable
class JackSeagullDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Jack Seagull is a pirate in The Rusty Anchor
     * in Port Sarim. When asked what he's doing,
     * he merely says that he is drinking.
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.HALF_GUILTY, "Arrr, matey!")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> sendDialogueOptions(player, "What would you like to say?", "What are you doing here?", "Have you got any quests I could do?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.HALF_GUILTY, "What are you doing here?").also { stage++ }
                2 -> player(FacialExpression.HALF_GUILTY, "Have you got any quests I could do?").also { stage = 4 }
            }
            2 -> npc(FacialExpression.HALF_GUILTY, "Drinking.").also { stage++ }
            3 -> player(FacialExpression.HALF_GUILTY, "Fair enough.").also { stage = END_DIALOGUE }
            4 -> npc(FacialExpression.HALF_GUILTY, "Nay, I've nothing for ye to do.").also { stage++ }
            5 -> player(FacialExpression.HALF_GUILTY, "Thanks.").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JACK_SEAGULL_2690)
    }
}
