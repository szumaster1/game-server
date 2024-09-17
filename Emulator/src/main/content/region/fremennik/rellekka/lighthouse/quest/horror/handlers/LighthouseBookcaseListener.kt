package content.region.fremennik.rellekka.lighthouse.quest.horror.handlers

import core.api.*
import org.rs.consts.Items
import org.rs.consts.Scenery
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Handles the bookcase in Lighthouse.
 *
 * Related books
 * 1. [Lighthouse Manual][content.region.fremennik.rellekka.lighthouse.quest.horror.book.LighthouseManual]
 * 2. [Ancient Diary][content.region.fremennik.rellekka.lighthouse.quest.horror.book.AncientDiary]
 * 3. [Jossik Journal][content.region.fremennik.rellekka.lighthouse.quest.horror.book.JossikJournal]
 */
class LighthouseBookcaseListener : DialogueFile(), InteractionListener {

    override fun handle(componentID: Int, buttonID: Int) {
        when (stage) {
            0 -> {
                if (isQuestComplete(player!!, "Horror from the Deep")) {
                    end()
                    sendDialogue(player!!, "You have completed the Horror from the Deep quest. You probably don't need this book.")
                } else {
                    sendDialogue(player!!, "There are three books here that look important... What would you like to do?").also { stage++ }
                }
            }

            1 -> options("Take the Lighthouse Manual", "Take the ancient Diary", "Take Jossik's Journal", "Take all three books").also { stage++ }

            2 -> when (buttonID) {
                1 -> {
                    end()
                    if (freeSlots(player!!) < 1) {
                        sendDialogue(player!!, "You do not have enough room to take that.")
                    } else {
                        addItemOrDrop(player!!, Items.MANUAL_3847)
                    }
                }

                2 -> {
                    end()
                    if (freeSlots(player!!) < 1) {
                        sendDialogue(player!!, "You do not have enough room to take that.")
                    } else {
                        addItemOrDrop(player!!, Items.DIARY_3846)
                    }
                }

                3 -> {
                    end()
                    if (freeSlots(player!!) < 1) {
                        sendDialogue(player!!, "You do not have enough room to take that.")
                    } else {
                        addItemOrDrop(player!!, Items.JOURNAL_3845)
                    }
                }

                4 -> {
                    end()
                    if (freeSlots(player!!) < 3) {
                        sendDialogue(player!!, "You do not have enough room to take all three.")
                    } else {
                        addItemOrDrop(player!!, Items.MANUAL_3847)
                        addItemOrDrop(player!!, Items.DIARY_3846)
                        addItemOrDrop(player!!, Items.JOURNAL_3845)
                    }
                }
            }
        }
    }

    override fun defineListeners() {

        /*
         * Handle the search option on bookcase.
         */

        on(Scenery.BOOKCASE_4617, IntType.SCENERY, "search") { player, node ->
            openDialogue(player, LighthouseBookcaseListener(), node.asScenery())
            return@on true
        }
    }
}
