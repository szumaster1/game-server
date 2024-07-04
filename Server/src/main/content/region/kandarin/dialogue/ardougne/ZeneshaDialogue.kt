package content.region.kandarin.dialogue.ardougne

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class ZeneshaDialogue(player: Player? = null) : Dialogue(player) {
    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npcl(FacialExpression.SUSPICIOUS, "Hello there! I sell plate armour. Are you interested?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("I may be interested.", "Sorry, I'm not interested.").also { stage++ }
            1 -> when(buttonId){
                1 -> {
                    end()
                    openNpcShop(player, NPCs.ZENESHA_589)
                }
                2 -> player("Sorry, I'm not interested.").also { stage = END_DIALOGUE }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ZENESHA_589)
    }
}
