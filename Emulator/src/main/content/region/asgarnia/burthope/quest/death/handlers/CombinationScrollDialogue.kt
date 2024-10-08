package content.region.asgarnia.burthope.quest.death.handlers

import core.api.*
import org.rs.consts.Components
import org.rs.consts.Items
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.tools.END_DIALOGUE
import org.rs.consts.QuestName

/**
 * Represents the Combination scroll dialogue.
 */
class CombinationScrollDialogue : DialogueFile() {
    var a = 0
    override fun handle(componentID: Int, buttonID: Int) {
        when (getQuestStage(player!!, QuestName.DEATH_PLATEAU)) {
            in 15..16 -> {
                when (stage) {
                    0 -> player(FacialExpression.NEUTRAL, "The IOU says that Harold owes me some money.").also { stage++ }
                    1 -> player(FacialExpression.EXTREMELY_SHOCKED, "Wait just a minute!").also { stage++ }
                    2 -> playerl(FacialExpression.EXTREMELY_SHOCKED, "The IOU is written on the back of the combination! The stupid guard had it in his back pocket all the time!").also { stage++ }
                    3 -> {
                        if (removeItem(player!!, Items.IOU_3103)) {
                            addItemOrDrop(player!!, Items.COMBINATION_3102)
                            setQuestStage(player!!, QuestName.DEATH_PLATEAU, 16)
                            sendItemDialogue(player!!, Items.COMBINATION_3102, "You have found the combination!").also { stage++ }
                            sendMessage(player!!, "You have found the combination!")
                        }
                    }

                    4 -> {
                        end()
                        stage = END_DIALOGUE
                        openInterface(player!!, Components.BLANK_SCROLL_222).also {
                            CombinationScroll.CombinationScroll(
                                player!!
                            )
                        }
                    }
                }
            }
        }
    }

}
