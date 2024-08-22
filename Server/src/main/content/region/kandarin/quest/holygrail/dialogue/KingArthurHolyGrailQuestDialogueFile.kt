package content.region.kandarin.quest.holygrail.dialogue

import content.region.kandarin.quest.holygrail.HolyGrail
import core.api.*
import cfg.consts.Items
import cfg.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.npc.NPC
import core.game.node.item.Item
import core.tools.END_DIALOGUE

/**
 * Represents the King arthur holy grail quest dialogue file.
 */
class KingArthurHolyGrailQuestDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.KING_ARTHUR_251)

        when (stage) {
            0 -> {
                if (isQuestComplete(player!!, HolyGrail.HOLY_GRAIL)) {
                    npcl(FacialExpression.NEUTRAL, "Thank you for retrieving the Grail! You shall be long remembered as one of the greatest heroes amongst the Knights of the Round Table!")
                    stage = END_DIALOGUE
                } else if (getQuestStage(player!!, HolyGrail.HOLY_GRAIL) == 0) {
                    playerl(FacialExpression.NEUTRAL, "Now I am a knight of the round table, do you have any more quests for me?")
                    stage++
                } else if (getQuestStage(player!!, HolyGrail.HOLY_GRAIL) == 40) {
                    playerl(FacialExpression.NEUTRAL, "Hello, do you have a knight named Sir Percival?")
                    stage = 40
                } else {
                    npcl(FacialExpression.NEUTRAL, "How goes thy quest?")

                    if (getQuestStage(player!!, HolyGrail.HOLY_GRAIL) == 50 && player!!.hasItem(Item(Items.HOLY_GRAIL_19, 1))) {
                        stage = 60
                    } else {
                        stage = 30
                    }
                }
            }

            1 -> npcl(FacialExpression.NEUTRAL, "Aha! I'm glad you are here! I am sending out various knights on an important quest. I was wondering if you too would like to take up this quest?").also { stage++ }
            2 -> showTopics(
                    Topic(FacialExpression.NEUTRAL, "Tell me of this quest.", 13),
                    Topic(FacialExpression.NEUTRAL, "I am weary of questing for the time being...", 23))

            23 -> npcl(FacialExpression.NEUTRAL, "Maybe later then?").also { stage++ }
            24 -> playerl(FacialExpression.NEUTRAL, "Maybe so.").also { stage = END_DIALOGUE }

            13 -> npcl(FacialExpression.NEUTRAL, "Well, we recently found out that the Holy Grail has passed into Gielinor.").also { stage++ }
            14 -> npcl(FacialExpression.NEUTRAL, "This is most fortuitous!").also { stage++ }
            15 -> npcl(FacialExpression.NEUTRAL, "None of my knights ever did return with it last time. Now we have the opportunity to give it another go, maybe this time we will have more luck!").also {
                stage++
            }

            16 -> showTopics(
                    Topic("I'd enjoy trying that.", 17),
                    Topic("I may come back and try that later.", 20))

            17 -> npcl(FacialExpression.NEUTRAL, "Go speak to Merlin. He may be able to give a better clue as to where it is now you have freed him from that crystal.").also {
                setQuestStage(player!!, HolyGrail.HOLY_GRAIL, 10)
                setVarp(player!!, HolyGrail.VARP_INDEX, HolyGrail.VARP_SHOW_MERLIN_VALUE, true)
                stage++
            }

            18 -> npcl(FacialExpression.NEUTRAL, "He has set up his workshop in the room next to the library.").also { stage = END_DIALOGUE }

            20 -> npcl(FacialExpression.NEUTRAL, "Be sure that you come speak to me soon then.").also { stage = END_DIALOGUE }

            30 -> playerl(FacialExpression.NEUTRAL, "I am making progress, but I have not recovered the Grail yet.").also { stage++ }
            31 -> npcl(FacialExpression.NEUTRAL, "Well, the Grail IS very elusive, it may take some perseverance.").also { stage++ }
            32 -> npcl(FacialExpression.NEUTRAL, "As I said before, speak to Merlin in the workshop by the library.").also { stage = END_DIALOGUE }

            40 -> npcl(FacialExpression.NEUTRAL, "Ah yes. I remember young Percival. He rode off on a quest a couple of months ago. We are getting a bit worried, he's not back yet...").also { stage++ }
            41 -> npcl(FacialExpression.NEUTRAL, "He was going to try and recover the golden boots of Arkaneeses.").also { stage++ }
            42 -> playerl(FacialExpression.NEUTRAL, "Any idea which way that would be?").also { stage++ }
            43 -> npcl(FacialExpression.NEUTRAL, "Not exactly. We discovered some magic golden feathers that are said to point the way to the boots...").also { stage++ }
            44 -> npcl(FacialExpression.NEUTRAL, "They certainly point somewhere.").also { stage++ }
            45 -> npcl(FacialExpression.NEUTRAL, "Just blowing gently on them will supposedly show the way to go.").also { stage++ }
            46 -> {
                if (player!!.hasItem(Item(Items.MAGIC_GOLD_FEATHER_18, 1))) {
                    npcl(FacialExpression.NEUTRAL, "You've got one of the feathers somewhere - off you go.")
                } else {
                    addItemOrDrop(player!!, Items.MAGIC_GOLD_FEATHER_18, 1)
                    sendDialogue(player!!, "King Arthur gives you a feather.")
                }
                stage = END_DIALOGUE
            }

            60 -> playerl(FacialExpression.NEUTRAL, "I have retrieved the Grail!").also { stage++ }
            61 -> npcl(FacialExpression.NEUTRAL, "Wow! Incredible! You truly are a splendid knight!").also { stage++ }
            62 -> {
                end()
                stage = END_DIALOGUE
                getQuest(player!!, HolyGrail.HOLY_GRAIL).finish(player!!)
            }

        }
    }
}
