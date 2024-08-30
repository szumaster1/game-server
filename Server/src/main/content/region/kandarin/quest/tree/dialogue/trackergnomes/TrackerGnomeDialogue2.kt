package content.region.kandarin.quest.tree.dialogue.trackergnomes

import cfg.consts.Items
import cfg.consts.NPCs
import core.api.getQuestStage
import core.api.inInventory
import core.api.sendDialogue
import core.api.setAttribute
import core.game.dialogue.Dialogue
import core.game.dialogue.FacialExpression
import core.game.node.entity.player.Player
import core.plugin.Initializable
import core.tools.END_DIALOGUE

/**
 * Represents the Tracker gnome dialogue2.
 */
@Initializable
class TrackerGnomeDialogue2(player: Player? = null) : Dialogue(player) {
    override fun handle(componentID: Int, buttonID: Int): Boolean {
        val questStage = getQuestStage(player!!, "Tree Gnome Village")
        when {
            questStage == 100 -> when(stage){
                0 -> sendDialogue(player, "Tracker Gnome seems too busy to talk.").also { stage = END_DIALOGUE }
            }

            questStage == 30 -> {
                when (stage) {
                    0 -> playerl(FacialExpression.WORRIED, "Are you OK?").also { stage++ }
                    1 -> npcl(FacialExpression.OLD_NORMAL, "They caught me spying on the stronghold. They beat and tortured me.").also { stage++ }

                    2 -> npcl(FacialExpression.OLD_NORMAL, "But I didn't crack. I told them nothing. They can't break me!").also { stage++ }

                    3 -> playerl(FacialExpression.HALF_GUILTY, "I'm sorry little man.").also { stage++ }
                    4 -> npcl(FacialExpression.OLD_NORMAL, "Don't be. I have the position of the stronghold!").also { stage++ }

                    5 -> {
                        sendDialogue(player!!, "The gnome tells you the y coordinate.").also {
                            setAttribute(player!!, "/save:treegnome:tracker2", true)
                            stage++
                        }
                    }

                    6 -> playerl(FacialExpression.FRIENDLY, "Well done.").also { stage++ }
                    7 -> npcl(FacialExpression.OLD_NORMAL, "Now leave before they find you and all is lost.").also { stage++ }
                    8 -> playerl(FacialExpression.FRIENDLY, "Hang in there.").also { stage++ }
                    9 -> npcl(FacialExpression.OLD_NORMAL, "Go!").also { stage = END_DIALOGUE }
                }
            }

            questStage >= 40 -> {
                when (stage) {
                    0 -> playerl(FacialExpression.FRIENDLY, "Hello").also { stage++ }
                    1 -> npcl(FacialExpression.OLD_NORMAL, "When will this battle end? I feel like I've been locked up my whole life.").also { stage = END_DIALOGUE }
                }
            }

            questStage > 30 -> {
                if (inInventory(player!!, Items.ORB_OF_PROTECTION_587)) {
                    when (stage) {
                        0 -> playerl(FacialExpression.HALF_ASKING, "How are you tracker?").also { stage++ }
                        1 -> npcl(FacialExpression.OLD_NORMAL, "Now we have the orb I'm much better. Soon my comrades will come and free me.").also { stage = END_DIALOGUE }
                    }
                } else {
                    when (stage) {
                        0 -> playerl(FacialExpression.FRIENDLY, "Hello again.").also { stage++ }
                        1 -> npcl(FacialExpression.OLD_NORMAL, "Well done, you've broken down their defences. This battle must be ours.").also { stage = END_DIALOGUE }
                    }
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TRACKER_GNOME_2_482)
    }
}
