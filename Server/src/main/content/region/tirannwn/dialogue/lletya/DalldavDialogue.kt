package content.region.tirannwn.dialogue.lletya

import core.api.consts.NPCs
import core.api.openNpcShop
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

@Initializable
class DalldavDialogue(player: Player? = null): Dialogue(player) {

    /*
     *  Info: elf who runs the Lletya Archery Shop in the rebel elf settlement of Lletya.
     *  He sells arrows up to rune and bows up to willow, as well as a crossbow and bronze bolts.
     *  Location: 2323,3163
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc(FacialExpression.FRIENDLY, "Can I help you at all?").also { stage = 0 }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes please. What are you selling?", "Why do you sell this stuff?", "No thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> player(FacialExpression.FRIENDLY, "Yes please. What are you selling?").also { stage = 10 }
                2 -> playerl(FacialExpression.FRIENDLY, "Why do you sell this stuff? The Crystal Bow is so much better.").also { stage = 20 }
                3 -> player(FacialExpression.FRIENDLY, "No thanks.").also { stage = END_DIALOGUE }
            }
            10 -> {
                end()
                openNpcShop(player, NPCs.DALLDAV_7447)
            }
            20 -> npcl(FacialExpression.FRIENDLY, "We keep all these old toys to train our children with, but if people will part with coins for them, then they are theirs!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.DALLDAV_7447) // NPCs.DALLDAV_2356
    }

}
