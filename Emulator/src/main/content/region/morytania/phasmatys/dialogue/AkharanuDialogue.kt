package content.region.morytania.phasmatys.dialogue

import org.rs.consts.Items
import org.rs.consts.NPCs
import content.region.morytania.phasmatys.quest.ahoy.dialogue.AkharanuDialogueFile
import core.api.*
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Akharanu dialogue.
 */
@Initializable
class AkharanuDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Info: Sells bolt racks, It is located on the eastern side of Port Phasmatys.
     * Location: 3709,3497
     */

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (inInventory(player!!, Items.SIGNED_OAK_BOW_4236) || getQuestStage(player, "Ghosts Ahoy") >= 5) {
            end()
            openDialogue(player, AkharanuDialogueFile())
        } else {
            npc(FacialExpression.FRIENDLY, "Hello, there, friend!")
        }
        return true
    }

    override fun handle(componentId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Why are you, errr, so stiff?", "Do you sell anything?").also { stage++ }
            1 -> when (buttonId) {
                1 -> player("Why are you, errr, so stiff?").also { stage++ }
                2 -> player("Do you sell anything?").also { stage = 5 }
            }
            2 -> npc(FacialExpression.FRIENDLY, "I have extremely severe arthritis. It really sucks.").also { stage++ }
            3 -> player("Oh. Well I'm sorry to hear that.").also { stage++ }
            4 -> npc(FacialExpression.FRIENDLY, "Yes, thank you for your concern.").also { stage = END_DIALOGUE }
            5 -> npc(FacialExpression.FRIENDLY, "Why, yes I do!").also { stage++ }
            6 -> {
                end()
                openNpcShop(player, NPCs.AK_HARANU_1688)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.AK_HARANU_1689)
    }

}
