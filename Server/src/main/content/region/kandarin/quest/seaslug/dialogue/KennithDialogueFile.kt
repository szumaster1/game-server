package content.region.kandarin.quest.seaslug.dialogue

import core.api.consts.NPCs
import core.api.getQuestStage
import core.api.isQuestComplete
import core.api.setQuestStage
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE

/**
 * Kennith dialogue file
 *
 * @constructor Kennith dialogue file
 */
class KennithDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.KENNITH_4863)
        when (stage) {
            0 -> {
                // After Sea Slug.
                if (isQuestComplete(player!!, "Sea Slug")) {
                    player("Hello there, Kennith. How are you today?").also { stage = 105 }
                }
                // After kick the wall.
                else if (getQuestStage(player!!, "Sea Slug") == 25) {
                    player("Kennith, I've made an opening in the wall. You can", "come out through there.").also { stage = 100 }
                }
                // First talk.
                else {
                    player("Are you okay young one?").also { stage++ }
                }
            }
            1 -> npc(FacialExpression.CHILD_SAD, "No, I want daddy!").also { stage++ }
            2 -> player("Where is your father?").also { stage++ }
            3 -> npc(FacialExpression.CHILD_SAD, "He went to get help days ago.").also { stage++ }
            4 -> npc(FacialExpression.CHILD_SAD, "The nasty fishermen tried to throw me and daddy into", "the sea. So he told me to hide here.").also { stage++ }
            5 -> player("That's good advice, you stay here and I'll go try and", "find your father.").also { stage++ }
            6 -> {
                end()
                setQuestStage(player!!, "Sea Slug", 10)
            }
            100 -> npc(FacialExpression.CHILD_THINKING, "Are there any sea slugs on the other side?").also { stage++ }
            101 -> player("Not one.").also { stage++ }
            102 -> npc(FacialExpression.CHILD_NORMAL, "How will I get downstairs?").also { stage++ }
            103 -> player("I'll figure that out in a moment.").also { stage++ }
            104 -> npc(FacialExpression.CHILD_NORMAL, "Ok, when you have I'll come out.").also {
                setQuestStage(player!!, "Sea Slug", 30)
                stage = END_DIALOGUE
            }
            105 -> npc("Great " + if(player!!.isMale) "mister" else "miss" + "! Thanks again","for rescuing me.").also { stage++ }
            106 -> player("It was my pleasure Kennith. You take care now.").also { stage++ }
            107 -> npc("Sure " + if(player!!.isMale) "mister" else "miss" + ". Goodbye now.").also { stage = END_DIALOGUE }
        }
    }
}
