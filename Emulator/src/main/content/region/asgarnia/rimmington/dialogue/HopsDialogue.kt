package content.region.asgarnia.rimmington.dialogue

import content.region.kandarin.ardougne.quest.biohazard.dialogue.HopsDialogueFile
import content.region.kandarin.ardougne.quest.biohazard.handlers.BiohazardUtils
import org.rs.consts.NPCs
import core.api.getAttribute
import core.api.getQuestStage
import core.api.openDialogue
import core.api.sendDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.START_DIALOGUE

/**
 * Represents the Hops dialogue.
 */
@Initializable
class HopsDialogue(player: Player? = null) : Dialogue(player) {

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            START_DIALOGUE -> {
                end()
                if (getQuestStage(player, "Biohazard") < 1) {
                    sendDialogue(player, "Hops doesn't feel like talking.")
                    return true
                }
                if (getAttribute(player, BiohazardUtils.THIRD_VIAL_CORRECT, true)) {
                    openDialogue(player, HopsDialogueFile())
                } else {
                    npcl(FacialExpression.NEUTRAL, "I suppose I'd better get going. I'll meet you at the Dancing Donkey Inn.")
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HOPS_340)
    }

}
