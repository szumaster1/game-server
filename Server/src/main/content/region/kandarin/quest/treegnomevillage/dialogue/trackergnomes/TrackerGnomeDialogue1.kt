package content.region.kandarin.quest.treegnomevillage.dialogue.trackergnomes

import core.api.consts.Items
import core.api.consts.NPCs
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
 * Represents the Tracker gnome dialogue1.
 */
@Initializable
class TrackerGnomeDialogue1(player: Player? = null) : Dialogue(player) {
    override fun handle(componentID: Int, buttonID: Int): Boolean {
        val questStage = getQuestStage(player!!, "Tree Gnome Village")
        when {
            questStage == 100 -> when(stage){
                0 -> sendDialogue(player, "Tracker Gnome seems too busy to talk.").also { stage = END_DIALOGUE }
            }

            questStage >= 40 -> {
                when (stage) {
                    0 -> playerl(FacialExpression.FRIENDLY, "Hello").also { stage++ }
                    1 -> npcl(FacialExpression.OLD_NORMAL, "When will this battle end? I feel like I've been fighting forever.").also { stage = END_DIALOGUE }
                }
            }

            questStage > 30 -> {
                if (inInventory(player!!, Items.ORB_OF_PROTECTION_587)) {
                    when (stage) {
                        0 -> playerl(FacialExpression.ASKING, "How are you tracker?").also { stage++ }
                        1 -> npcl(FacialExpression.OLD_NORMAL, "Now we have the orb I'm much better. They won't stand a chance without it.").also { stage = END_DIALOGUE }
                    }
                } else {
                    when (stage) {
                        0 -> playerl(FacialExpression.FRIENDLY, "Hello again.").also { stage++ }
                        1 -> npcl(FacialExpression.OLD_NORMAL, "Well done, you've broken down their defences. This battle must be ours.").also { stage = END_DIALOGUE }
                    }
                }
            }

            questStage == 30 -> {
                when (stage) {
                    0 -> playerl(FacialExpression.HALF_ASKING, "Do you know the coordinates of the Khazard stronghold?").also { stage++ }

                    1 -> npcl(FacialExpression.OLD_NORMAL, "I managed to get one, although it wasn't easy.").also { stage++ }

                    2 -> {
                        sendDialogue(player!!, "The gnome tells you the height coordinate.").also {
                            setAttribute(player!!, "/save:treegnome:tracker1", true)
                            stage++
                        }
                    }

                    3 -> player("Well done.").also { stage++ }
                    4 -> npcl(FacialExpression.OLD_NORMAL, "The other two tracker gnomes should have the other coordinates if they're still alive.").also { stage++ }
                    5 -> playerl(FacialExpression.FRIENDLY, "OK, take care.").also { stage = END_DIALOGUE }
                }
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.TRACKER_GNOME_1_481)
    }
}
