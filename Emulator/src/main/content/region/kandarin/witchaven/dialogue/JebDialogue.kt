package content.region.kandarin.witchaven.dialogue

import content.region.kandarin.witchaven.fishingplatform.handlers.FishingPlatform
import org.rs.consts.NPCs
import core.api.isQuestComplete
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Jeb dialogue.
 */
@Initializable
class JebDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Sea Slug")) {
            npc(FacialExpression.FRIENDLY, "Hello there.").also { stage = END_DIALOGUE }
        } else {
            playerl(FacialExpression.FRIENDLY,"I understand you can take me to the Fishing Platform.")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc("Yes, we can do that.").also { stage++ }
            1 -> player("Will you take me please?").also { stage++ }
            2 -> npc("Board the boat and we shall depart.").also { stage++ }
            3 -> {
                end()
                FishingPlatform.sail(player!!, FishingPlatform.Travel.WITCHAVEN_TO_FISHING_PLATFORM)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.JEB_4895)
    }
}
