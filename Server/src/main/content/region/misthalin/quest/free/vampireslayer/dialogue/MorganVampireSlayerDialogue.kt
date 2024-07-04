package content.region.misthalin.quest.free.vampireslayer.dialogue

import core.api.consts.NPCs
import core.api.getQuestStage
import core.api.isQuestComplete
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

class MorganVampireSlayerDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.MORGAN_755)
        val questStage = getQuestStage(player!!, "Vampire Slayer")
        when (stage) {
            0 -> if (!isQuestComplete(
                    player!!,
                    "Vampire Slayer"
                ) || questStage == 10 || questStage == 20 || questStage == 30
            ) {
                npc(FacialExpression.HALF_GUILTY, "How are you doing with the quest?").also { stage = 10 }
            } else {
                player(FacialExpression.HALF_GUILTY, "I have slain the foul creature!").also { stage = 101 }
            }

            10 -> player(FacialExpression.HALF_GUILTY, "I'm still working on it.").also { stage++ }
            11 -> npc(
                FacialExpression.HALF_GUILTY,
                "Please hurry! Every day we live in fear that we",
                "the vampire's next victim!"
            ).also { stage = END_DIALOGUE }

            101 -> npc(
                FacialExpression.HALF_GUILTY,
                "Thank you, thank you! You will always be a hero in",
                "our village!"
            ).also { stage = END_DIALOGUE }
        }
    }
}
