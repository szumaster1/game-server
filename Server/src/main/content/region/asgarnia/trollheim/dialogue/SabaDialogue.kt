package content.region.asgarnia.trollheim.dialogue

import content.region.asgarnia.quest.death.dialogue.SabaDialogueFile
import cfg.consts.NPCs
import core.api.getQuestStage
import core.api.isQuestComplete
import core.api.openDialogue
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Saba dialogue.
 */
@Initializable
class SabaDialogue(player: Player? = null) : Dialogue(player) {

    /*
     * Saba was an old hermit who lived in
     * a cave on Death Plateau, north of the
     * Warriors' Guild.
     */

    override fun open(vararg args: Any?): Boolean {
        npc = args[0] as NPC
        if (!isQuestComplete(player, "Death Plateau")) {
            openDialogue(player!!, SabaDialogueFile(), npc)
        } else if(isQuestComplete(player, "Death Plateau")) {
            playerl(FacialExpression.HALF_GUILTY, "Hello.").also { stage = 1 }
        } else {
            playerl(FacialExpression.HALF_GUILTY, "Hi!").also { stage = 0 }
        }
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> npcl(FacialExpression.ANNOYED, "Why won't people leave me alone?!").also { stage = END_DIALOGUE }
            1 -> npcl(FacialExpression.HALF_ASKING, "Have you got rid of those pesky trolls yet?").also { stage++ }
            2 -> {
                if(getQuestStage(player, "Troll Stronghold") >= 1) {
                    playerl(FacialExpression.HALF_GUILTY, "I'm afraid there's been some trouble...").also { stage = 5 }
                } else {
                    playerl(FacialExpression.HALF_GUILTY, "They will be gone soon! The Imperial Guard will use a secret way that starts from the back of the Sherpa's hut to destroy the troll camp!").also { stage++ }
                }
            }
            3 -> npcl(FacialExpression.ANNOYED,"I shall have peace again at last!").also { stage++ }
            4 -> npcl(FacialExpression.ANNOYED,"If those pesky humans don't start trampling all over Death Plateau again that is!").also { stage = END_DIALOGUE }
            5 -> npcl(FacialExpression.ANNOYED, "You told me you'd get rid of the trolls!").also { stage++ }
            6 -> playerl(FacialExpression.HALF_GUILTY,"I'm sure the Imperial Guard will deal with them in due course.").also { stage++ }
            7 -> npcl(FacialExpression.ANNOYED,"Hmph! They'd better!").also { stage = END_DIALOGUE }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SABA_1070)
    }
}
