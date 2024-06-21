package content.region.asgarnia.dialogue.burthope

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable

@Initializable
class LidioDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.ASKING, "Greetings, warrior, how can I fill your stomach today?").also { stage++ }
        return true
    }
    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> playerl(FacialExpression.NEUTRAL, "With food preferable.").also { stage++ }
            1 -> {
                end()
                openNpcShop(player, NPCs.LIDIO_4293)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LIDIO_4293)
    }
}