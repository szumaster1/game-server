package content.location.witchaven

import content.region.kandarin.travel.FishingPlatform
import content.region.kandarin.quest.seaslug.dialogue.HolgartDialogueFile
import cfg.consts.NPCs
import core.api.isQuestComplete
import core.api.isQuestInProgress
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Holgart dialogue.
 */
@Initializable
class HolgartDialogue(player: Player? = null) : Dialogue(player) {

    fun gender(male: String = "Sir", female: String = "Madam") = if (player.isMale) male else female

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        when {
            // Talk after Sea Slug.
            isQuestComplete(player, "Sea Slug") -> player("Hello again Holgart.").also { stage = 4 }
            // Talk during Sea slug.
            isQuestInProgress(player, "Sea Slug", 2, 99) -> end().also { openDialogue(player, HolgartDialogueFile()) }
            // Talk before Sea Slug.
            else -> player(FacialExpression.FRIENDLY, "Hello there.")
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npc(FacialExpression.FRIENDLY, "Well hello good " + gender() + ", beautiful day isn't it?").also { stage++ }
            1 -> player(FacialExpression.FRIENDLY, "Not bad I suppose.").also { stage++ }
            2 -> npc(FacialExpression.FRIENDLY, "Just smell that sea air... beautiful.").also { stage++ }
            3 -> player(FacialExpression.FRIENDLY, "Hmm... lovely...").also { stage = END_DIALOGUE }
            4 -> npcl(FacialExpression.HALF_ASKING, "Well hello again m'hearty. Your land loving legs getting bored? Fancy some cold wet underfoot?").also { stage++ }
            5 -> player(FacialExpression.FRIENDLY, "Pardon?").also { stage++ }
            6 -> npc(FacialExpression.FRIENDLY,"Fancy going out to sea?").also { stage++ }
            7 -> options("I'll come back later.", "Okay, let's do it.").also { stage++ }
            8 -> when(buttonId) {
                1 -> player(FacialExpression.FRIENDLY,"I'll come back later.").also { stage++ }
                2 -> player(FacialExpression.FRIENDLY,"Okay, let's do it.").also { stage = 10 }
            }
            9 -> npc(FacialExpression.FRIENDLY,"Okay then. I'll wait here for you.").also { stage++ }
            10 -> npc(FacialExpression.FRIENDLY,"Hold on tight!").also { stage++ }
            11 -> {
                end()
                FishingPlatform.sail(player, FishingPlatform.Travel.WITCHAVEN_TO_FISHING_PLATFORM)
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.HOLGART_698)
    }

}
