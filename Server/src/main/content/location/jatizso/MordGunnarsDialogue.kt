package content.location.jatizso

import content.region.fremennik.handlers.waterbirth.TravelDestination
import content.region.fremennik.handlers.waterbirth.WaterbirthTravel
import cfg.consts.NPCs
import core.api.requireQuest
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Mord Gunnars dialogue.
 */
@Initializable
class MordGunnarsDialogue(player: Player? = null): Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (npc.id == NPCs.MORD_GUNNARS_5481) {
            npcl(FacialExpression.FRIENDLY, "Would you like to sail to Jatizso?")
        } else {
            npcl(FacialExpression.FRIENDLY, "Would you like to sail back to Rellekka?")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> options("Yes, please.", "No, thanks.").also { stage++ }
            1 -> when (buttonId) {
                1 -> playerl(FacialExpression.FRIENDLY, "Yes, please!").also { stage++ }
                2 -> playerl(FacialExpression.FRIENDLY, "No, thank you.").also { stage = END_DIALOGUE }
            }

            2 -> {
                end()
                if (!requireQuest(player, "Fremennik Trials", "")) {
                    return true
                } else {
                    WaterbirthTravel.sail(player, if (npc.id == NPCs.MORD_GUNNARS_5481) TravelDestination.RELLEKKA_TO_JATIZSO else TravelDestination.JATIZSO_TO_RELLEKKA)
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.MORD_GUNNARS_5482, NPCs.MORD_GUNNARS_5481)
    }

}
