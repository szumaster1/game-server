package content.region.kandarin.ardougne.quest.biohazard.dialogue

import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import content.region.kandarin.ardougne.quest.biohazard.BiohazardUtils
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.dialogue.Topic
import core.game.node.entity.npc.NPC
import core.tools.END_DIALOGUE
import org.rs.consts.QuestName

/**
 * Represents the Elena dialogue. (Biohazard quest)
 */
class ElenaDialogue : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        val questStage = getQuestStage(player!!, QuestName.BIOHAZARD)
        npc = NPC(NPCs.ELENA_3209)
        when {
            (questStage == 0) -> {
                when (stage) {
                    0 -> player(FacialExpression.HAPPY,"Good day to you, Elena.").also { stage++ }
                    1 -> npc(FacialExpression.HAPPY,"You too, thanks for freeing me.").also { stage++ }
                    2 -> npc(FacialExpression.STRUGGLE,"It's just a shame the mourners confiscated my", "equipment.").also { stage++ }
                    3 -> player(FacialExpression.HALF_ASKING,"What did they take?").also { stage++ }
                    4 -> npc(FacialExpression.HALF_GUILTY,"My distillator, I can't test any plague samples without it.", "They're holding it in the mourner quarters in West", "Ardougne.").also { stage++ }
                    5 -> npc(FacialExpression.HALF_GUILTY,"I must somehow retrieve that distillator if I am to find", "a cure for this awful affliction.").also { stage++ }
                    6 -> options("I'll try to retrieve it for you.", "Well, good luck.").also { stage++ }
                    7 -> when (buttonID) {
                        1 -> player(FacialExpression.NEUTRAL,"I'll try to retrieve it for you.").also { stage = 9 }
                        2 -> player(FacialExpression.NEUTRAL,"Well, good luck.").also { stage++ }
                    }
                    8 -> npc(FacialExpression.NEUTRAL,"Thanks traveller.").also { stage = END_DIALOGUE }
                    9 -> npc(FacialExpression.SAD,"I was hoping you would say that. Unfortunately they", "discovered the tunnel and filled it in. We need another", "way over the wall.").also { stage++ }
                    10 -> player(FacialExpression.HALF_ASKING,"Any ideas?").also { stage++ }
                    11 -> npc(FacialExpression.FRIENDLY,"My father's friend Jerico is in communication with", "West Ardougne. He might be able to help us, he lives", "next to the chapel.").also { stage++ }
                    12 -> {
                        end()
                        setQuestStage(player!!, QuestName.BIOHAZARD, 1)
                    }
                }
            }

            (questStage == 1) -> {
                when (stage) {
                    0 -> player(FacialExpression.HAPPY,"Hello Elena.").also { stage++ }
                    1 -> npc(FacialExpression.HAPPY,"Hello brave adventurer. Any luck finding my distillator?").also { stage++ }
                    2 -> player(FacialExpression.NOD_NO,"No, I'm afraid not.").also { stage++ }
                    3 -> npc(FacialExpression.HAPPY,"Speak to Jerico, he will help you to cross the", "wall. He lives next to the chapel.").also { stage++ }
                    4 -> end()
                }
            }

            (questStage == 4) -> {
                when (stage) {
                    0 -> player(FacialExpression.HAPPY,"I've distracted the guards at the watch tower.").also { stage++ }
                    1 -> npc(FacialExpression.HAPPY,"Yes, I saw. Quickly meet with Jerico's friends", "and cross the wall before the pigeons fly off.").also { stage++ }
                    2 -> end()
                }
            }

            (questStage in 5..7) -> {
                when (stage) {
                    0 -> player(FacialExpression.HAPPY,"Hello again.").also { stage++ }
                    1 -> npc(FacialExpression.HAPPY,"You're back, did you find the distillator?").also { stage++ }
                    2 -> player(FacialExpression.NEUTRAL,"I'm afraid not.").also { stage++ }
                    3 -> npc(FacialExpression.WORRIED,"I can't test the samples without the distillator.").also { stage++ }
                    4 -> npc(FacialExpression.HALF_WORRIED,"Please don't give up until you find it.").also { stage++ }
                    5 -> end()
                }
            }

            (questStage in 10..15) -> {
                when (stage) {
                    0 -> if(getAttribute(player!!, BiohazardUtils.ELENA_REPLACE, false)) {
                        npcl(FacialExpression.HALF_ASKING, "What are you doing back here?").also { stage = 99 }
                    } else {
                        npc(FacialExpression.CALM_TALK, "So, have you managed to retrieve my distillator?").also { stage++ }
                    }
                    1 -> {
                        if (!inInventory(player!!, Items.DISTILLATOR_420)) {
                            player(FacialExpression.NEUTRAL,"I'm afraid not.").also { stage++ }
                        } else {
                            player(FacialExpression.HAPPY,"Yes, here it is!").also { stage = 5 }
                        }
                    }
                    2 -> npc(FacialExpression.WORRIED,"I can't test the samples without the distillator.").also { stage++ }
                    3 -> npc(FacialExpression.HALF_WORRIED,"Please don't give up until you find it.").also { stage++ }
                    4 -> end()
                    5 -> {
                        npc(FacialExpression.HAPPY,"You have? That's great! Now can you pass me those", "reaction agents please?")
                        sendMessage(player!!, "You hand Elena the distillator and an assortment of vials.")
                        stage++
                    }
                    6 -> player(FacialExpression.HAPPY,"Those look pretty fancy.").also { stage++ }
                    7 -> npc(FacialExpression.HAPPY,"Well, yes and no. The liquid honey isn't worth much, ", "but the others are. Especially this colourless ethenea. Be", "careful with the sulphuric broline, it's highly poisonous.").also { stage++ }
                    8 -> player(FacialExpression.HAPPY,"You're not kidding, I can smell it from here!").also { stage++ }
                    9 -> {
                        npc(FacialExpression.STRUGGLE,"I don't understand... the touch paper hasn't changed", "colour at all...")
                        sendMessage(player!!, "Elena puts the agents through the distillator.")
                        stage++
                    }
                    10 -> npc(FacialExpression.THINKING,"You'll need to go and see my old mentor Guidor. He", "lives in Varrock. Take these vials and this sample to", "him.").also { stage++ }
                    11 -> npc(FacialExpression.NEUTRAL,"But first you'll need some more touch paper. Go and", "see the chemist in Rimmington.").also { stage++ }
                    12 -> npc(FacialExpression.NEUTRAL,"Just don't get into any fights, and be careful who", "you speak to.").also { stage++ }
                    13 -> npc(FacialExpression.NEUTRAL,"Those vials are fragile, and plague carriers don't", "tend to be too popular.").also { stage++ }
                    14 -> {
                        end()
                        if (removeItem(player!!, Items.DISTILLATOR_420)) {
                            addItemOrDrop(player!!, Items.LIQUID_HONEY_416)
                            addItemOrDrop(player!!, Items.ETHENEA_415)
                            addItemOrDrop(player!!, Items.SULPHURIC_BROLINE_417)
                            addItemOrDrop(player!!, Items.PLAGUE_SAMPLE_418)
                            sendMessage(player!!, "Elena gives you three vials and a sample in a tin container.")
                            setAttribute(player!!, BiohazardUtils.ELENA_REPLACE, true)
                        }
                    }

                    99 -> showTopics(Topic(FacialExpression.HALF_GUILTY, "I just find it hard to say goodbye sometimes.", 100), Topic(FacialExpression.GUILTY, "I'm afraid i've lost some of the stuff that you gave me...", 200), Topic(FacialExpression.HALF_ASKING, "I've forgotten what I need to do.", 300))
                    100 -> npc(FacialExpression.HALF_GUILTY, "Yes... I have feelings for you too...", "Now get to work!").also { stage = END_DIALOGUE }
                    200 -> {
                        if (!inInventory(player!!, Items.LIQUID_HONEY_416) || !inInventory(player!!, Items.ETHENEA_415) || !inInventory(player!!, Items.PLAGUE_SAMPLE_418) || !inInventory(player!!, Items.SULPHURIC_BROLINE_417)) {
                            stage++
                        } else {
                            npcl(FacialExpression.HALF_THINKING, "Are you sure? Looks like you have everything to me.").also { stage = END_DIALOGUE }
                        }
                    }

                    201 -> if (freeSlots(player!!) < 4) {
                        sendDialogue(player!!, "Elena tries to give you some items but you don't have enough room for them.").also { stage = END_DIALOGUE }
                    } else {
                        sendDialogue(player!!, "Elena replaces your items.")
                        if (!inInventory(player!!, Items.LIQUID_HONEY_416))
                            addItem(player!!, Items.LIQUID_HONEY_416)
                        if (!inInventory(player!!, Items.ETHENEA_415))
                            addItem(player!!, Items.ETHENEA_415)
                        if (!inInventory(player!!, Items.SULPHURIC_BROLINE_417))
                            addItem(player!!, Items.SULPHURIC_BROLINE_417)
                        if (!inInventory(player!!, Items.PLAGUE_SAMPLE_418))
                            addItem(player!!, Items.PLAGUE_SAMPLE_418)
                        stage++
                    }
                    202 -> npc(FacialExpression.HALF_THINKING, "Ok, so that's the colourless ethenea...", "Some highly toxic sulphuric broline...", "And some bog-standard liquid honey...").also { stage++ }
                    203 -> playerl(FacialExpression.FRIENDLY, "Great. I'll be on my way.").also {
                        //removeAttributes(player!!, BiohazardUtils.FIRST_VIAL_WRONG,BiohazardUtils.SECOND_VIAL_WRONG,BiohazardUtils.THIRD_VIAL_WRONG)
                        stage = END_DIALOGUE
                    }
                    300 -> npcl(FacialExpression.HALF_THINKING, "Go to Rimmington and get some touch paper from the chemist. Use his errand boys to smuggle the vials into Varrock, then go to Varrock and take the sample to Guidor, my old mentor.").also { stage++ }
                    301 -> playerl(FacialExpression.FRIENDLY, "Ok, I'll get to it.").also { stage = END_DIALOGUE }
                }
            }

            (questStage in 16..98) -> {
                when (stage) {
                    0 -> npc(FacialExpression.HALF_THINKING, "You're back! So what did Guidor say?").also { stage++ }
                    1 -> player(FacialExpression.DISGUSTED,"Nothing.").also { stage++ }
                    2 -> npc(FacialExpression.HALF_THINKING,"What?").also { stage++ }
                    3 -> player(FacialExpression.NEUTRAL,"He said that there is no plague.").also { stage++ }
                    4 -> npc(FacialExpression.HALF_WORRIED,"So what, this thing has all been a big hoax?").also { stage++ }
                    5 -> player(FacialExpression.NEUTRAL,"Or maybe we're about to uncover something huge.").also { stage++ }
                    6 -> npc(FacialExpression.NEUTRAL,"Then I think this thing may be bigger than both", "of us.").also { stage++ }
                    7 -> player(FacialExpression.HALF_ASKING,"What do you mean?").also { stage++ }
                    8 -> npc(FacialExpression.THINKING,"I mean you need to go right to the top...").also { stage++ }
                    9 -> npc(FacialExpression.HALF_GUILTY,"You need to see the King of East Ardougne!").also { stage++ }
                    10 -> {
                        end()
                        setQuestStage(player!!, QuestName.BIOHAZARD, 99)
                    }
                }
            }

            (questStage == 99) -> {
                when (stage) {
                    0 -> playerl(FacialExpression.FRIENDLY, "Hello Elena.").also { stage++ }
                    1 -> npcl(FacialExpression.ANNOYED, "You must go to King Lathas immediately!").also { stage++ }
                    2 -> end()
                }
            }

            (questStage == 100) -> {
                when (stage) {
                    0 -> player(FacialExpression.HAPPY,"Hello Elena.").also { stage++ }
                    1 -> npc(FacialExpression.HAPPY,"Hey, how are you?").also { stage++ }
                    2 -> player(FacialExpression.HALF_ASKING,"Good thanks, yourself?").also { stage++ }
                    3 -> npc(FacialExpression.HAPPY,"Not bad, let me know when you hear from", "King Lathas again.").also { stage++ }
                    4 -> player(FacialExpression.HAPPY,"Will do.").also { stage++ }
                    5 -> end()
                }
            }
        }
    }
}

