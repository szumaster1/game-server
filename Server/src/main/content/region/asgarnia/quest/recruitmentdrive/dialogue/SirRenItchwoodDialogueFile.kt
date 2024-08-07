package content.region.asgarnia.quest.recruitmentdrive.dialogue

import content.region.asgarnia.quest.recruitmentdrive.RecruitmentDrive
import content.region.asgarnia.quest.recruitmentdrive.RecruitmentDriveListeners
import core.api.getAttribute
import core.api.lock
import core.api.removeAttribute
import core.api.setAttribute
import core.game.dialogue.DialogueBuilder
import core.game.dialogue.DialogueBuilderFile
import core.game.dialogue.FacialExpression

/**
 * Sir ren itchwood dialogue file
 *
 * @property dialogueNum
 * @constructor Sir ren itchwood dialogue file
 */
class SirRenItchwoodDialogueFile(private val dialogueNum: Int = 0) : DialogueBuilderFile() {

    companion object {
        const val ATTRIBUTE_CLUE = "rd:cluenumber"
    }

    override fun create(b: DialogueBuilder) {
        b.onPredicate { player ->
            dialogueNum in 0..1 && !getAttribute(
                player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, false
            )
        }.betweenStage { _, player, _, _ ->
                if (getAttribute(player, ATTRIBUTE_CLUE, 6) == 6) {
                    setAttribute(player, ATTRIBUTE_CLUE, (0..5).random())
                }
            }.npc(
                "Greetings friend, and welcome here,",
                "you'll find my puzzle not so clear.",
                "Hidden amongst my words, it's true,",
                "the password for the door as a clue."
            ).options().let { optionBuilder ->

                optionBuilder

                    .option_playerl("Can I have the clue for the door?")
                    .branch { player -> getAttribute(player, ATTRIBUTE_CLUE, 0) }.let { branch ->
                        branch.onValue(0).npc(
                                "Better than me, you'll not find",
                                "In rhyming and in puzzles.",
                                "This clue so clear will tax your mind",
                                "Entirely as it confuzzles!"
                            ).end()
                        branch.onValue(1).npc(
                                "Feel the aching of your mind",
                                "In puzzlement, confused.",
                                "See the clue hidden behind",
                                "His words, as you perused."
                            ).end()
                        branch.onValue(2).npc(
                                "Look closely at the words i speak;",
                                "And study closely every part.",
                                "See for yourself the word you seek",
                                "Trapped for you if you're smart."
                            ).end()
                        branch.onValue(3).npc(
                                "More than words, i have not for you",
                                "Except the things i say today.",
                                "Aware are you, this is a clue?",
                                "Take note of what i say!"
                            ).end()
                        branch.onValue(4).npc(
                                "Rare it is that you will see",
                                "A puzzle such as this!",
                                "In many ways it tickles me",
                                "Now, watching you hit and miss!"
                            ).end()
                        branch.onValue(5).npc(
                                "This riddle of mine may confuse,",
                                "I am quite sure of that.",
                                "Mayhap you should closely peruse",
                                "Every word i have spat?"
                            ).end()
                    }
                return@let optionBuilder

                    .option("Can I have a different clue?")
                    .player("I don't get that riddle...", "Can I have a different one?")
                    .branch { player -> getAttribute(player, ATTRIBUTE_CLUE, 0) }.let { branch ->

                        branch.onValue(0).npc(
                                "Before you hurry through that door",
                                "Inspect the words i spoke.",
                                "There is a simple hidden flaw",
                                "Ere you think my rhyme a joke."
                            ).end()
                        branch.onValue(1).npc(
                                "First my clue you did not see,",
                                "I really wish you had.",
                                "Such puzzling wordplay devilry",
                                "Has left you kind of mad!"
                            ).end()
                        branch.onValue(2).npc(
                                "Last time my puzzle did not help",
                                "Apparently, so you've bidden.",
                                "Study my speech carefully, whelp",
                                "To find the answer, hidden."
                            ).end()
                        branch.onValue(3).npc(
                                "Many types have passed through here",
                                "Even such as you amongst their sort.",
                                "And in the end, the puzzles clear;",
                                "TThe hidden word you saught."
                            ).end()
                        branch.onValue(4).npc(
                                "Repetition, once again",
                                "Against good sense it goes.",
                                "In my words, the answers plain",
                                "Now that you see rhyme flows."
                            ).end()
                        branch.onValue(5).npc(
                                "Twice it is now, i have stated",
                                "In a rhyme, what is the pass.",
                                "Maybe my words obfuscated",
                                "Entirely beyond your class."
                            ).end()
                    }

                /*
                return@let optionBuilder.option("Can I have the final clue?")
                        .branch { player -> getAttribute(player, attributeClueNumber, 0) }
                        .let{ branch ->
                            branch.onValue(0)
                                    .npc("Betrayed by words the answer is", "In that what i say is the key", "There is no more help after this", "Especially no more from me.")
                                    .end()
                            branch.onValue(1)
                                    .npc("For the last time i will state", "In simple words, the clue.", "Such tricky words make you irate", "Having no idea what to do...")
                                    .end()
                            branch.onValue(2)
                                    .npc("Last time my puzzle did not help", "Apparently, so you've bidden.", "Study my speech carefully, whelp", "To find the answer, hidden.")
                                    .end()
                            branch.onValue(3)
                                    .npc("Many types have passed through here", "Even such as you amongst their sort.", "And in the end, the puzzles clear;", "TThe hidden word you saught.")
                                    .end()
                            branch.onValue(4)
                                    .npc("Repetition, once again", "Against good sense it goes.", "In my words, the answers plain", "Now that you see rhyme flows.")
                                    .end()
                            branch.onValue(5)
                                    .npc("Twice it is now, i have stated", "In a rhyme, what is the pass.", "Maybe my words obfuscated", "Entirely beyond your class.")
                                    .end()
                        }
                */

            }
        b.onPredicate { player ->
            dialogueNum == 2 || getAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, false)
        }.betweenStage { _, player, _, _ ->
            setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, true)
        }.npc(
                FacialExpression.SAD,
                "It's sad to say,",
                "this test beat you.",
                "I'll send you to Tiffy,",
                "what to do?"
            ).endWith { _, player ->
                lock(player, 10)
                removeAttribute(player, ATTRIBUTE_CLUE)
                setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_PASSED, false)
                setAttribute(player, RecruitmentDrive.ATTRIBUTE_RD_STAGE_FAILED, false)
                RecruitmentDriveListeners.FailTestCutscene(player).start()
            }

    }

}