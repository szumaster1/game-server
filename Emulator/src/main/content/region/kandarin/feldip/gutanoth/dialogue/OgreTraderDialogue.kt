package content.region.kandarin.feldip.gutanoth.dialogue

import org.rs.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Ogre trader dialogue.
 */
@Initializable
class OgreTraderDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.OLD_ANGRY1,"Trade with me? Who do you think you are?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when(stage){
            0 -> options("Can I see what you are selling?","I don't need anything.").also { stage++ }
            1 -> when(buttonId){
                1 -> player("Can I see what you are selling?").also { stage = 3 }
                2 -> player("I don't need anything.").also { stage++ }
            }
            2 -> npcl(FacialExpression.OLD_CALM_TALK2,"As you wish.").also { stage = END_DIALOGUE }
            3 -> npc(FacialExpression.OLD_CALM_TALK2,"I suppose so.").also { stage++ }
            4 -> {
                end()
                openNpcShop(player, NPCs.OGRE_TRADER_873)
            }
        }
        return true
    }



    override fun getIds(): IntArray {
        return intArrayOf(NPCs.OGRE_TRADER_873)
    }
}
