package content.region.misthalin.dialogue.varrock

import core.api.consts.Components
import core.api.consts.NPCs
import core.api.openInterface
import core.api.openNpcShop
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Sawmill operator dialogue
 *
 * @constructor Sawmill operator dialogue
 */
class SawmillOperatorDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.SAWMILL_OPERATOR_4250)
        when (stage) {
            0 -> npc(FacialExpression.NEUTRAL, "Do you want me to make some planks for you? Or", "would you be interested in some other housing supplies?").also { stage++ }1 -> options("Planks please!", "What kind of planks can you make?", "Can I buy some housing supplies?", "Nothing, thanks.").also { stage++ }
            2 -> when (buttonID) {
                1 -> player(FacialExpression.HAPPY, "Planks please!").also { stage = 10 }
                2 -> player(FacialExpression.ASKING, "What kind of planks can you make?").also { stage = 20 }
                3 -> {
                    end(); openNpcShop(player!!, NPCs.SAWMILL_OPERATOR_4250)
                }

                4 -> player(FacialExpression.FRIENDLY, "Nothing, thanks.").also { stage = 40 }
            }
            40 -> npc(FacialExpression.FRIENDLY, "Well come back when you want some. You can't get", "good quality planks anywhere but here!").also { stage = END_DIALOGUE }
            10 -> npc(FacialExpression.HALF_ASKING, "What kind of planks do you want?").also { stage++ }
            11 -> {
                end(); openInterface(player!!, Components.POH_SAWMILL_403)
            }
            20 -> npc(FacialExpression.NEUTRAL, "I can make planks from wood, oak, teak and mahogany.", "I don't make planks from other woods as they're no", "good for making furniture.").also { stage++ }
            21 -> npc(FacialExpression.NEUTRAL, "Wood and oak are all over the place, but teak and", "mahogany can only be found in a few places like", "Karamja and Etceteria.").also { stage = END_DIALOGUE }
        }
    }
}
